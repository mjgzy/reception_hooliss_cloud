package com.xfkj.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.xfkj.annotations.UserAttribute;
import com.xfkj.feign.commodity.EsServiceFeign;
import com.xfkj.feign.commodity.WatchDetails_Service_Feign;
import com.xfkj.feign.order.FeignCartController;
import com.xfkj.feign.order.FeignOrderController;
import com.xfkj.feign.user.FeignUserReceInfoController;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.order.WatchOrder;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 订单控制
 * @author Administrator
 *
 */
@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {
	@Resource
	private FeignOrderController feignOrderController;

	@Autowired
	private FeignUserReceInfoController feignUserReceInfoController;
	@Autowired
	private WatchDetails_Service_Feign watchDetails_service_feign;
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * 查找订单
	 * @param date_type:日期时间段
	 * @param order_status_id:订单状态
	 * @param order_id:订单号
	 */
	@RequestMapping("/orderFind.xf")
	@ResponseBody
	public Object orderFind(@RequestParam("date_type")String date_type,
							@RequestParam("order_status_id") String order_status_id,
							@RequestParam("order_id")String order_id,
							@RequestParam("offset")Integer current_no,
							@RequestParam("limit")Integer page_size, HttpSession httpSession){
		Wuser attribute = (Wuser) httpSession.getAttribute(Constants.USER_SESSION);
		JSONObject jsonObject = new JSONObject();
		ResultBody resultBody = feignOrderController.orderFind(date_type, order_status_id, order_id, attribute.getU_id(),current_no,page_size);
		PageInfo<WatchOrder> list = null;
		if (resultBody.getCode().equals("200")){
			list= objectMapper.convertValue( resultBody.getResult(),new TypeReference<PageInfo<WatchOrder>>(){});
			for (WatchOrder watchOrder: list.getList() ) {
				ResultBody watchById = watchDetails_service_feign.findById(watchOrder.getWatch_id());
				//从es检索手表信息,填充到订单对象
				TbWatchs o = objectMapper.convertValue(watchById.getResult(),TbWatchs.class);
				watchOrder.setLt(o);		//设置手表对象
				ResultBody receInfoById = feignUserReceInfoController.findReceInfoById(watchOrder.getW_receinfo_id());
				WatchReceinfo watchReceinfo = objectMapper.convertValue(receInfoById.getResult(),WatchReceinfo.class);
				watchOrder.setWatch_receinfo(watchReceinfo);	//设置地址对象
				log.info(watchOrder.getWatch_receinfo()+"");
			}
		}
		jsonObject.put("total",list.getTotal());
		return jsonObject.fluentPut("data",list.getList());
	}

	@RequestMapping("/frame/order")
	public String frameOrder(){
		return "frame/order";
	}
	/**
	 * 跳转到成功页面
	 */
	@RequestMapping("/doChengGong.xf")
	public String doChengGong(){
		return feignOrderController.doChengGong().getResult().toString();
	}


	/**
	 * 生成库存信息
	 */
	@RequestMapping("/ostockGanrte")
	@ResponseBody
	public void stockGante(){
		ResultBody resultBody = feignOrderController.stockGante();
		if (resultBody.getCode().equals("200")){
			List<Boolean> result= objectMapper.convertValue(resultBody.getResult(),new TypeReference<List<Boolean>>(){});
			result.forEach(item->{
				log.info("stockGante:生成库存结果:"+item);
			});
		}else{
			log.info(resultBody.getMessage());
		}
	}

	/**
	 * 通过订单号删除订单
	 * @param order_id:订单号
	 */
	@RequestMapping("delOrderById")
	@ResponseBody
	public ResultBody delOrder(String order_id){
		return feignOrderController.delOrder(order_id);
	}
	/**
	 * 生成订单
	 * @param str_WatchOrder:订单对象
	 */
	@RequestMapping(value = "/gengateOrder.xf")
	@ResponseBody
	public ResultBody gengateOrder(
			@RequestParam("watch_info") String str_WatchOrder,
			@RequestParam("buy_type_token")String buy_type_token,
			@RequestParam("w_receinfo_id")Integer w_receinfo_id,
			@UserAttribute Wuser wuser){
		//由于feign底层使用http请求,所以此处需要转换为json串
		String str_wuser = JSON.toJSONString(wuser);
		return feignOrderController.gengateOrder(str_WatchOrder,buy_type_token,str_wuser,w_receinfo_id);
	}

	@RequestMapping(value = "/findOrderById")
	public ModelAndView findOrderAndPay(@UserAttribute Wuser wuser, ModelAndView modelAndView,HttpServletRequest httpServletRequest){
		String str_wuser=JSON.toJSONString(wuser);
		ResultBody orderAndPay = feignOrderController.findOrderAndPay(str_wuser);
		if(orderAndPay.getCode().equals("200")){
				modelAndView.setViewName("forward:/getPagePay");
		}else{
			modelAndView.addObject("errorMessage",orderAndPay.getCode()+orderAndPay.getMessage());
			modelAndView.setViewName("err");
		}
		return modelAndView;
	}
	/**
	 * 修改订单状态
	 */
	@RequestMapping("updateOrderStatus")
	public void updateOrderStatus(){
		ResultBody resultBody = feignOrderController.updateOrderStatus();
		if (!resultBody.getCode().equals("200")){
			log.info(resultBody.getResult().toString());
		}
	}

}
