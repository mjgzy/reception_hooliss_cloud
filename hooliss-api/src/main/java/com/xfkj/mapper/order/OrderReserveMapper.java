package com.xfkj.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.order.OrderReserve;
import org.apache.ibatis.annotations.Param;

/**
 * 订单资源预减
 */
public interface OrderReserveMapper extends BaseMapper<OrderReserve> {

    /**
     * 新增记录
     */
    Integer addReservce(OrderReserve orderReserve);

    /**
     * 删除记录
     */
    Integer delReservce(@Param("orderId") String orderId);
}
