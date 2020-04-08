package com.xfkj.mapper.order;

import com.xfkj.pojo.order.WatchOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单,对应页面huiyuan.html
 * @author Administrator
 *
 */
public interface WatchOrderMapper {


	/**
	 * 通过用户id或者order_id查询订单
	 * @param u_id:用户id:必选参数
	 * @param date_type:日期筛选,0表示全部,1表示近一个月,2表示近三个月,3表示近一年,可选参数
	 * @param order_status_id:订单状态表主键id:筛选订单状态,全部状态则不要此条件,可选参数
	 * @param order_id:订单id,模糊查询,可选参数
	 */
	List<WatchOrder> findWatchByCondition(
            @Param("user_id") Integer u_id,
            @Param("date_type") Integer date_type,
            @Param("order_status_id") Integer order_status_id,
            @Param("order_id") String order_id);

	/**
	 * 通过订单id查询订单
	 * @param order_id:订单id
	 */
	WatchOrder  findById(String order_id);

	/**
	 * 生成订单,此处需要先减掉库存,再添加订单,同一个事务,任何一个操作失败则回滚
	 * @param watchOrder:订单对象
	 */
	Integer generateOrder(WatchOrder watchOrder);
	/**
	 * 通过订单号删除订单
	 * @param order_id:订单号
	 */
	Boolean delOrderById(String order_id);
	/**
	 * 根据订单主键更改订单状态
	 * @param order_id:订单主键id
	 * @param status_id:状态id
	 */
	Integer updateOrderStatus(@Param("order_id") String order_id, @Param("status_id") Integer status_id);
}