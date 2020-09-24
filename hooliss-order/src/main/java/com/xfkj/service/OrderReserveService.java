package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.order.OrderReserve;
import com.xfkj.exceptionHandling.XFException;

public interface OrderReserveService extends IService<OrderReserve> {
    /**
     * 提交订单,执行库存,积分等操作
     */
    Boolean OrderCommit(OrderReserve orderReserve) throws XFException;

    /**
     * 新增订单预留记录
     */
    Boolean addReservce(OrderReserve orderReserve) throws XFException;

    /**
     * 删除订单预留记录
     */
    Boolean delReservce(String order_id) throws XFException;


}
