package com.xfkj.controller.order;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.annotations.UserAttribute;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.feign.commodity.WatchDetails_Service_Feign;
import com.xfkj.feign.order.FeignCartController;
import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.commodity.CookieTbWatchs;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.order.ShoppingCart;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.tools.Constants;
import com.xfkj.tools.CookieTools;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * 购物车控制层,包含用户地址管理,加载购物车信息
 * @author Administrator
 *
 */
@Slf4j
@Controller
@RequestMapping("/shopcart")
public class CartController {

	@Resource
	private FeignCartController feignCartController;

	@Resource
	private WatchDetails_Service_Feign watchDetails_service_feign;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 跳转到提交页面
	 * @param httpSession:会话
	 */
	@RequestMapping("/doTijiao.xf")
	public ModelAndView doTijiao(ModelAndView modelAndView,
								 @UserAttribute Wuser wuser,
								 @RequestParam("watch_id")Integer watch_id,
								 @RequestParam("add_count") Integer add_count){
		log.info("doTijiao执行>>>>>>>>>>>>>>");
		try {
			ShoppingCart shoppingCart =new ShoppingCart();
			shoppingCart.setAdd_count(add_count);
			shoppingCart.setW_id(watch_id);
			shoppingCart.setU_id(wuser.getU_id());
					ResultBody byId = watchDetails_service_feign.findById(shoppingCart.getW_id());
					if(byId.getCode().equals("200")){
						shoppingCart.setTb_watchs(objectMapper.convertValue(byId.getResult(),new TypeReference<TbWatchs>(){}));
					}else{
						modelAndView.setViewName("err");
						modelAndView.addObject("errorMessage",byId.getCode()+":"+byId.getMessage());
						return modelAndView;
					}
					if(shoppingCart.getTb_watchs()!=null){
						ResultBody brandById = watchDetails_service_feign.findBrandById(shoppingCart.getTb_watchs().getBrand_id());
						if(brandById.getCode().equals("200")){
							shoppingCart.getTb_watchs().setWatchBrand(objectMapper.convertValue(byId.getResult(),new TypeReference<WatchBrand>(){}) );
						}else{
							modelAndView.setViewName("err");
							modelAndView.addObject("errorMessage",brandById.getCode()+":"+brandById.getMessage());
							return modelAndView;
						}
					}
				modelAndView.addObject("w_infos", shoppingCart);
				modelAndView.addObject("buy_type_token", JWTUtils.getBuyTypeToken(wuser.getU_phone(),0));
		} catch (XFException e) {
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("err");
			return modelAndView;
		}
		modelAndView.setViewName("tijiao");
		return modelAndView;
	}

	/**
	 * 购物车跳转到提交页面
	 * @param httpSession:会话
	 */
	@RequestMapping("/cartTijiao.xf")
	public ModelAndView cartTijiao(HttpSession httpSession, ModelAndView modelAndView){
		Wuser wuser = (Wuser) httpSession.getAttribute(Constants.USER_SESSION);
		if (wuser==null){
			modelAndView.setViewName("car");
			modelAndView.addObject("errorMessage","无法提交，用户认证失败！" );
			return modelAndView;
		}
		ResultBody resultBody = feignCartController.cartTijiao(wuser.getU_id());
		if (resultBody.getCode().equals("200")){
			List<ShoppingCart> shoppingCarts=objectMapper.convertValue(resultBody.getResult(),new TypeReference<List<ShoppingCart>>(){});
			for (ShoppingCart shoppingCart:
				 shoppingCarts ) {
				ResultBody byId = watchDetails_service_feign.findById(shoppingCart.getW_id());
				if(byId.getCode().equals("200")){
					TbWatchs tbWatchs = objectMapper.convertValue(byId.getResult(),TbWatchs.class);
					shoppingCart.setTb_watchs(tbWatchs);
				}else{
					throw new XFException("400","无法查询商品信息,请稍后再试!");
				}
			}
			modelAndView.setViewName("tijiao");
			modelAndView.addObject("w_infos", shoppingCarts);
			modelAndView.addObject("buy_type_token", JWTUtils.getBuyTypeToken(wuser.getU_phone(),1));
		}else{
			modelAndView.addObject("errorMessage",resultBody.getMessage() );
			modelAndView.setViewName("err");
		}
		return modelAndView;
	}
	/**
	 * 保存购物信息到cookie
	 */
	@RequestMapping("doCookieCart.xf")
	@ResponseBody
	public ResultBody addCookieCart(CookieTbWatchs cookieTbWatchs, HttpServletRequest request, HttpServletResponse response){
		//保存信息到cookie
		if (request.getSession().getAttribute(Constants.USER_SESSION)!=null){
			return ResultBody.error("500","用户已登录,保存到购物车失败!" );
		}
		try {
			CookieTools.setCookie(request, response, cookieTbWatchs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ResultBody.success();
	}
	/**
	 *
	 * @param u_id:用户id
	 * @param watch_id:手表id
	 * @param add_count:添加数量
	 * @param httpSession:会话
	 */
	@RequestMapping(value = "/addCart.xf/{u_id}/{watch_id}/{add_count}",method = RequestMethod.POST)
	@ResponseBody
	public ResultBody addCart(
            @PathVariable("u_id") Integer u_id,
            @PathVariable("watch_id") Integer watch_id,
            @PathVariable("add_count") Integer add_count,
            HttpSession httpSession) {
		//如果为空则保存手表信息到cookie
		if (httpSession.getAttribute(Constants.USER_SESSION)==null){
				return ResultBody.error("500","请登录后再保存!" );
		}else{		//不为空则保存信息到数据库
			ResultBody resultBody = feignCartController.addCart(u_id, watch_id, add_count);
			Boolean result = (Boolean) resultBody.getResult();
			if (!resultBody.getCode().equals("200")||!result){
				return ResultBody.error(resultBody.getCode(), resultBody.getMessage());
			}
		}
		return ResultBody.success();
	}

	@RequestMapping("/doShopping.xf")
	public ModelAndView doShoping(HttpServletRequest request){
		List<CookieTbWatchs> ls =null;
		//未登录则从cookie拿数据
		if (request.getSession().getAttribute(Constants.USER_SESSION)==null){
			try {
				ls = CookieTools.getCartInCookie(request);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 去购物车
	 * @param request
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/doCart.xf")
	public ModelAndView doCart(HttpServletRequest request, HttpSession session, ModelAndView modelAndView){
		Wuser wuser = (Wuser) request.getSession().getAttribute(Constants. USER_SESSION);
		//未登录则获取cookie的购物车信息
		if (wuser==null){
			try {
				List<CookieTbWatchs> cartInCookie = CookieTools.getCartInCookie(request);
				modelAndView.setViewName("car");
				modelAndView.addObject("cookieCarts", cartInCookie);
				return modelAndView;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return modelAndView;
		}else{
			ResultBody resultBody = feignCartController.doCart(wuser.getU_id());
			if (resultBody.getCode().equals("200")){
				List<ShoppingCart> shoppingCarts=objectMapper.convertValue(resultBody.getResult(),
						new TypeReference<List<ShoppingCart>>(){});
				for (ShoppingCart shoppingCart:
				shoppingCarts) {
					ResultBody byId = watchDetails_service_feign.findById(shoppingCart.getW_id());
					if(byId.getCode().equals("200")){
						TbWatchs tbWatchs= objectMapper.convertValue(byId.getResult(),TbWatchs.class);
						shoppingCart.setTb_watchs(tbWatchs);
					}else{
						throw new XFException("400","查询商品信息失败,请稍后再试!");
					}
				}
				modelAndView.addObject("w_infos", shoppingCarts);
				modelAndView.setViewName("car");
			}else{
				modelAndView.addObject("errorMessage",resultBody.getMessage());
				modelAndView.setViewName("err");
			}
		}
		return modelAndView;
	}

	/**
	 * 合并未登录状态的购物车信息到用户
	 * @param request:
	 */
	@RequestMapping("/cartMerge.xf")
	public ModelAndView cartMerge(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView){
		Wuser wuser = ((Wuser)request.getSession().getAttribute(Constants.USER_SESSION));
			if (wuser==null){
			modelAndView.setViewName("redirect:/shopcart/doCart.xf");
			return modelAndView;
		}
		List<CookieTbWatchs> cartInCookie = null;
		try {
			cartInCookie = CookieTools.getCartInCookie(request);
			ResultBody resultBody = feignCartController.cartMerge(wuser.getU_id(), JSON.toJSONString(cartInCookie));
			if (resultBody.getCode().equals("200")){
				HashMap<String,Object> modelMap = objectMapper.convertValue(resultBody.getResult(),new TypeReference<HashMap<String,Object>>(){});
				List<ShoppingCart> shoppingCarts =objectMapper.convertValue( modelMap.get("w_infos"),new TypeReference<List<ShoppingCart>>(){});
				for (ShoppingCart shoppingCart:
						shoppingCarts) {
					ResultBody byId = watchDetails_service_feign.findById(shoppingCart.getW_id());
					if(byId.getCode().equals("200")){
						TbWatchs tbWatchs=objectMapper.convertValue(byId.getResult(),TbWatchs.class);
						shoppingCart.setTb_watchs(tbWatchs);
					}else{
						throw new XFException("500","商品数据查询失败,请稍后再试!");
					}
				}
				modelAndView.addObject("w_infos",shoppingCarts);
				modelAndView.setViewName("car");
			}
			//将cookie的手表信息添加至购物车
			CookieTools.removeCookie(response,"shopCarts" );	//删除cookie信息
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			modelAndView.addObject("erroeMessage",e.getMessage() );
			modelAndView.setViewName("err");
		}
		return modelAndView;
	}

	/**
	 * 添加商品数量方法
	 * @param w_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping("/addCount.xf")
	@ResponseBody
	public String addCount(
			@UserAttribute Wuser wUser,
            @RequestParam("w_id")Integer w_id,
            @RequestParam("addCount")Integer add_count,
            @RequestParam("buy_type_token")String buy_type_token,
            HttpSession httpSession, HttpServletRequest request, HttpServletResponse response){
		//未登录则添加数量至cookie
		if (wUser==null){
			CookieTbWatchs cookieTbWatchs = new CookieTbWatchs();
			cookieTbWatchs.setWatch_id(w_id);
			cookieTbWatchs.setAdd_count(add_count);
			try {
				CookieTools.setCookie(request, response, cookieTbWatchs);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "true";
		}else{
			if(JWTUtils.getBuyTypeToken(wUser.getU_phone(),1).equals(buy_type_token)){
				ResultBody resultBody = feignCartController.addCount(wUser.getU_id(), w_id, add_count);
				return resultBody.getResult().toString();
			}else if(JWTUtils.getBuyTypeToken(wUser.getU_phone(),0).equals(buy_type_token)){
				return ResultBody.success(true).toString();
			}else{
				return ResultBody.error("500","非法参数").toString();
			}
		}
	}
	/**
	 * 删除购物车
	 */
	@RequestMapping("/delShop.xf")
	@ResponseBody
	public String delWatch(@RequestParam("buy_type_token")String buy_type_token, @RequestParam("w_id")Integer w_id,@UserAttribute Wuser wuser){
		if(JWTUtils.getBuyTypeToken(wuser.getU_phone(),1).equals(buy_type_token)){
			ResultBody resultBody = feignCartController.delWatch(wuser.getU_id(), w_id);
			return resultBody.getResult().toString();
		}else if(JWTUtils.getBuyTypeToken(wuser.getU_phone(),0).equals(buy_type_token)){
			return ResultBody.success(true).toString();
		}else{
			return ResultBody.error("500","非法参数").toString();
		}
	}


	@RequestMapping(value = "/addOrder")
	@ResponseBody
	public String add(HttpSession session, ShoppingCart shoppingCart) {
		if (session.getAttribute(Constants.USER_SESSION) != null) {
			ResultBody add = feignCartController.add(shoppingCart);
			if (add.getCode().equals("200")) {
				return "添加成功";
			}
		}
		return "添加失败";
	}
}
