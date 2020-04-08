package com.xfkj.mapper.order;

import com.xfkj.pojo.order.OrderReserve;
import org.apache.ibatis.annotations.Param;

/**
 * 订单资源预减
 */
public interface OrderReserveMapper {

    /**
     * 新增记录
     */
    Integer addReservce(OrderReserve orderReserve);

    /**
     * 删除记录
     */
    Integer delReservce(@Param("order_id") String order_id);
}
