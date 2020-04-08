package com.xfkj.feign.order;

import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 订单控制
 * @author Administrator
 *
 */
@FeignClient("hooliss-order-provider")
@RequestMapping("order-provider")
public interface FeignOrderController {
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
							@PathVariable("page_size")Integer page_size);

	@RequestMapping(value = "/findOrderAndPay")
	ResultBody findOrderAndPay(@RequestParam("wuser") String str_wuser);

	/**
	 * 跳转到成功页面
	 */
	@RequestMapping("/doChengGong.xf")
	public ResultBody doChengGong();
	/**
	 * 生成库存信息
	 */
	@RequestMapping("/ostockGanrte")
	public ResultBody stockGante();
	/**
	 * 通过订单号删除订单
	 * @param order_id:订单号
	 */
	@RequestMapping("delOrderById")
	public ResultBody delOrder(@RequestParam("order_id") String order_id);
	/**
	 * 生成订单
	 */
	@RequestMapping(value = "/gengateOrder.xf")
	public ResultBody gengateOrder(
			@RequestParam("watch_info") String str_WatchOrder,
			@RequestParam("buy_type_token")String buy_type_token,
			@RequestParam("wuser") String wuser,	@RequestParam("w_receinfo_id")Integer w_receinfo_id);

	@RequestMapping("updateOrderStatus")
	public ResultBody updateOrderStatus();

	/**
	 * 查找订单
	 * @param order_id:订单号
	 */
	@RequestMapping("findOrderById")
	public ResultBody findOrderById(@RequestParam("order_id")String order_id);
}
