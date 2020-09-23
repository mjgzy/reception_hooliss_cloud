package com.xfkj.controller;

import com.alibaba.fastjson.JSON;
import com.xfkj.enums.CommonEnum;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.entity.commodity.CookieTbWatchs;
import com.xfkj.entity.order.ShoppingCart;
import com.xfkj.service.ShoppingCartService;
import com.xfkj.service.ShoppingCartService;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.DateFormatUtil;
import com.xfkj.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 购物车控制层,包含用户地址管理,加载购物车信息
 * @author Administrator
 *
 */
@RestController
@Slf4j
@RequestMapping("shopcart-provider")
public class CartController {


	@Resource
	private ShoppingCartService shoppingCartService;


	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	@RequestMapping("existenceCart")
	Boolean existenceCart(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id){
		return shoppingCartService.existenceCart(u_id, w_id);
	};
	/**
	 * 购物车跳转到提交页面
	 */
	@RequestMapping("/cartTijiao.xf")
	public ResultBody<?> cartTijiao(@RequestParam("user_id")Integer user_id){
		//获取该用户下的手表信息
		List<ShoppingCart> shoppingCarts = null;
		try {
			shoppingCarts = shoppingCartService.queryShoppingCartsByIdAndW_Id(user_id, null);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(500,e.getMessage() );
		}
		return new ResultBody<>(CommonEnum.SUCCESS,shoppingCarts);
	}

	/**
	 *
	 * @param u_id:用户id
	 * @param watch_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping(value = "/addCart.xf/{u_id}/{watch_id}/{add_count}",method = RequestMethod.POST)
	public ResultBody addCart(
            @PathVariable("u_id") Integer u_id,
            @PathVariable("watch_id") Integer watch_id,
            @PathVariable("add_count") Integer add_count
            ) {
		//如果为空则保存手表信息到cookie
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setWId(watch_id);
			shoppingCart.setAddCount(add_count);
			shoppingCart.setUId(u_id);
			shoppingCart.setAddDate(DateFormatUtil.dateToString(new Date()));
			Boolean result;
			try {
				result = shoppingCartService.addShopInfo(shoppingCart);//保存购物车信息
			} catch (XFException e) {
				e.printStackTrace();
				return new ResultBody<>(e.getErrorCode(), e.getMessage());
			}
		return new ResultBody<>(CommonEnum.SUCCESS,result);
	}

	@RequestMapping("/doCart.xf")
	public ResultBody<?> doCart(@RequestParam("user_id")Integer user_id){
			//查询购物车手表信息
		List<ShoppingCart> shoppingCarts = null;
		try {
			shoppingCarts = shoppingCartService.queryShoppingCartsByIdAndW_Id(user_id, null);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage() );
		}
		return new ResultBody<>(CommonEnum.SUCCESS,shoppingCarts);
}
	/**
	 * 合并未登录状态的购物车信息到用户
	 */
	@RequestMapping("/cartMerge.xf")
	public ResultBody<?> cartMerge(@RequestParam("user_id") Integer user_id,String cartInCookies){
		HashMap<String,Object> modelMap = new HashMap<String,Object>();
			//查询该用户下所有购物车手表信息
		List<ShoppingCart> shoppingCarts = null;
		try {
			List<CookieTbWatchs> cartInCookie=JSON.parseArray(cartInCookies, CookieTbWatchs.class);
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUId(user_id);
			//将cookie的手表信息添加至购物车
			cartInCookie.forEach(item->{
				shoppingCart.setWId(item.getWatchId());
				shoppingCart.setAddDate(DateFormatUtil.dateToString(new Date()));
				shoppingCart.setAddCount(item.getAddCount());
				Boolean aBoolean = shoppingCartService.addShopInfo(shoppingCart);//保存购物车信息
				log.info("cartMerge:cookie添加到数据库:"+aBoolean);
			});
			shoppingCarts = shoppingCartService.queryShoppingCartsByIdAndW_Id(user_id, null);
		} catch (XFException e1) {
			e1.printStackTrace();
			return new ResultBody<>(e1.getErrorCode(),e1.getMessage() );
		}
		modelMap.put("w_infos", shoppingCarts);
		return new ResultBody<>(CommonEnum.SUCCESS,modelMap);
	}

	/**
	 * 添加商品数量方法
	 * @param user_id:用户id
	 * @param w_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping("/addCount.xf")
	public ResultBody<?> addCount(
            @RequestParam("user_id") Integer user_id,
            @RequestParam("w_id")Integer w_id,
            @RequestParam("addCount")Integer add_count
            ){
		Boolean aBoolean =null;
		try {
			 aBoolean = shoppingCartService.addCount(user_id, w_id, add_count);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage() );
		}
		return new ResultBody<>(CommonEnum.SUCCESS,aBoolean);
	}


	/**
	 * 删除购物车
	 * @param u_id:商品id
	 */
	@RequestMapping("/delShop.xf")
	public ResultBody<?> delWatch(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id){
		Boolean aBoolean = null;
		try {
			aBoolean = shoppingCartService.deleteShopInfoById(u_id,w_id);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage());
		}
		log.info("删除购物车信息:"+aBoolean);
		return new ResultBody<>(CommonEnum.SUCCESS,  aBoolean);
	}

	@RequestMapping(value = "/addOrder")
	public ResultBody<?> add(@RequestParam("shoppingCart") ShoppingCart shoppingCart){
		Boolean aBoolean = null;
		try {
			aBoolean = shoppingCartService.addShopInfo(shoppingCart);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage() );
		}
		return new  ResultBody<>(CommonEnum.SUCCESS,aBoolean);
	}
}
