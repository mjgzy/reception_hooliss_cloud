package com.xfkj.pojo.vo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.order.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 订单状态表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderStatusVo extends OrderStatus {

    public OrderStatusVo(Integer statusId) {
        super(statusId);
    }
}