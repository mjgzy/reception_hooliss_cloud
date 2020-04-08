package com.xfkj.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.xfkj.config.rabbit.MqSender;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.feign.commodity.WatchDetails_Service_Feign;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.order.WatchOrder;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.order.OrderService;
import com.xfkj.service.order.ShoppingCartService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.JWTUtils;
import com.xfkj.utils.OrderUtils;
import com.xfkj.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;


/**
 * 订单控制
 * @author Administrator
 *
 */
@Slf4j
@RestController
@RequestMapping("order-provider")
public class OrderController {

	@Autowired
	private OrderService orderservice;

	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WatchDetails_Service_Feign watchDetails_service_feign;

	@Autowired
	private MqSender mqSender;

	@Autowired
	private RedisUtils redisUtils;
	/**
	 * 查找订单
	 * @param date_type:日期时间段
	 * @param order_status_id:订单状态
	 * @param order_id:订单号
	 * @param user_id:用户id
	 */
	@RequestMapping("/orderFind.xf/{date_type}/{order_status_id}/{order_id}/{user_id}/{current_no}/{page_size}")
	public ResultBody orderFind(@PathVariable("date_type")String date_type,
							@PathVariable("order_status_id") String order_status_id,
							@PathVariable("order_id")String order_id,
							@PathVariable("user_id")Integer user_id,
							@PathVariable("current_no")Integer current_no,
							@PathVariable("page_size")Integer page_size){
		Integer date = Integer.valueOf(date_type);
		Integer status_id =Integer.valueOf(order_status_id);
		PageInfo<WatchOrder> info;
		try {
			info = orderservice.findWatchByCondition(user_id,date,status_id,order_id,current_no,page_size);
		} catch (XFException e) {
			e.printStackTrace();
			return ResultBody.error(e.getErrorCode(),e.getMessage() );
		}
		return ResultBody.success(info);
	}

	/**
	 * 查找订单
	 * @param order_id:订单号
	 */
	@RequestMapping("findOrderById")
	public ResultBody findOrderById(@RequestParam("order_id") String order_id){
		return ResultBody.success(orderservice.findById(order_id));
	};

	/**
	 * 跳转到成功页面
	 */
	@RequestMapping("/doChengGong.xf")
	public ResultBody doChengGong(){
		try {
			Map<Object, Object> order_ids = redisUtils.hmget(Constants.REDIS_ORDERID_NAME);
			order_ids.forEach((k,v)->{
				System.err.println("key:"+k);
				System.err.println("value:"+v);
				mqSender.sendDirectOrderStatus(((String)k));	//发送消息
			});
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBody.error("500", e.getMessage());
		}
		return ResultBody.success( "chenggong");
	}
	@RequestMapping(value = "/findOrderAndPay")
	public ResultBody findOrderAndPay(@RequestParam("wuser") String str_wuser){
		Wuser wuser=JSON.parseObject(str_wuser,Wuser.class);
		String key=(wuser.getU_id()+wuser.getU_pwd());
		WatchOrder watchOrder= (WatchOrder) redisUtils.get(DigestUtils.md5DigestAsHex(key.getBytes()));
			if(watchOrder!=null){
				ResultBody byId = watchDetails_service_feign.findById(watchOrder.getWatch_id());
				if(byId.getCode().equals("200")){
					TbWatchs tbWatchs = objectMapper.convertValue(byId.getResult(),TbWatchs.class);
					watchOrder.setLt(tbWatchs);
				}else{
					return ResultBody.error("errorMessage","未获取到订单对应商品信息!");
				}
			}else{
				ResultBody.error("errorMessage","未获取到订单信息!");
			}
		return ResultBody.success(watchOrder);
	}
	/**
	 * 通过订单号删除订单
	 * @param order_id:订单号
	 */
	@RequestMapping("delOrderById")
	public ResultBody delOrder(@RequestParam("order_id") String order_id){
		return ResultBody.success(orderservice.delOrderById(order_id));
	}
	/**
	 * 生成库存信息
	 */
	@RequestMapping("/ostockGanrte")
	public ResultBody stockGante(){
		List<Boolean> result = new ArrayList<>();
		try {
			List<Integer> watchIdByTbWatchs = orderservice.findWatchIdByTbWatchs();
			watchIdByTbWatchs.forEach(item->{
				boolean b = orderservice.stockGante(item);
				result.add(b);
			});
		} catch (XFException e) {
			e.printStackTrace();
			return ResultBody.error("500",e.getMessage() );
		}
		return ResultBody.success(result);
	}

	/**
	 * 购物车生成订单,来自tijiao.js
	 * @param str_watch_info:手表对象
	 */
	@RequestMapping(value = "/gengateOrder.xf")
	public ResultBody gengateOrder(@RequestParam("watch_info") String str_watch_info,
								   @RequestParam("buy_type_token")String buy_type_token,
								   @RequestParam("wuser") String str_wuser,
								   @RequestParam("w_receinfo_id")Integer w_receinfo_id){
		List<WatchOrder> watchOrder = JSON.parseArray(str_watch_info,WatchOrder.class);
		Wuser wuser = JSON.parseObject(str_wuser, Wuser.class);
		String orderIdByUUId = OrderUtils.getOrderIdByUUId();//生成订单号
		WatchOrder totalOrder=new WatchOrder();
		boolean selectType=false;
//			依次发送队列,交给消费方处理
			if(buy_type_token.equals(JWTUtils.getBuyTypeToken(wuser.getU_phone(),1))){
				selectType=true;
			}
			try {
				WatchOrder item;
				boolean first=true;
				for (WatchOrder entry:watchOrder
					 ) {
					if(selectType){
						//判断是否是购物车信息
						if(!shoppingCartService.existenceCart(wuser.getU_id(),entry.getWatch_id() )){
							throw new XFException("500", "商品不存在!");
						}
					}
					item = new WatchOrder();
					ResultBody byId1 = watchDetails_service_feign.findById(entry.getWatch_id());
					if (!byId1.getCode().equals("200")) {
						throw new XFException("500", "无法查询商品信息,请稍后再试!");
					}
					item.setW_receinfo_id(w_receinfo_id);
					item.setWatch_id(entry.getWatch_id());
					item.setO_uid(wuser.getU_id());        //设置用户id
					item.setWatch_count(entry.getWatch_count());
					TbWatchs tbWatchs = objectMapper.convertValue(byId1.getResult(), TbWatchs.class);
					if (tbWatchs != null) {
						BigDecimal bigDecimal = BigDecimal.valueOf(item.getWatch_count());
						item.setOrder_price(tbWatchs.getSo_price().multiply(bigDecimal));
						item.setLt(tbWatchs);
					} else {
						return ResultBody.error("500", "无法查询订单商品信息,请稍后再试!");
					}
					mqSender.sendDirectQueueOrder(item);        //向队列发送消息
					//计算总金额
					if(totalOrder.getOrder_price()==null){
						totalOrder.setOrder_price(new BigDecimal(0.00));
					}
					BigDecimal totalPrice=totalOrder.getOrder_price().add(item.getOrder_price());
					totalOrder.setOrder_price(totalPrice);
					if(first){
						totalOrder.setLt(item.getLt());
						totalOrder.getLt().setWatch_name(item.getLt().getWatch_name()+"...等多件商品");
						first=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultBody.error("500", e.getMessage());
			}
			//将订单信息放入缓存,支付时取出
		totalOrder.setOrder_id(orderIdByUUId);
		String s = DigestUtils.md5DigestAsHex((wuser.getU_id() + wuser.getU_phone()).getBytes());
		boolean set = redisUtils.set(s, totalOrder);//将订单信息放入redis,支付时取出
		return ResultBody.success();
	}
	@RequestMapping("updateOrderStatus")
	public ResultBody updateOrderStatus(){
		List<Object> order_ids = redisUtils.lGet(Constants.REDIS_ORDERID_NAME, 0, -1);		//获取缓存中的订单
		try {
			order_ids.forEach(item->{
				mqSender.sendDirectOrderStatus(((String)item));	//发送消息
			});
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBody.error("500",e.getMessage() );
		}
		return ResultBody.success();
	}

}
