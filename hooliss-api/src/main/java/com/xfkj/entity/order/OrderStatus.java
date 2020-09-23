package com.xfkj.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单状态表
 */
@Data
@TableName("order_status")
public class OrderStatus implements Serializable {

    private Integer statusId;

    private String statudName;


    public OrderStatus(Integer statusId) {
        this.statusId = statusId;
    }
}