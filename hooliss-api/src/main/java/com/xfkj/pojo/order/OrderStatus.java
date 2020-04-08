package com.xfkj.pojo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 订单状态表
 */
@Data
public class OrderStatus implements Serializable {

    private Integer statusId;

    private String statudName;

    public OrderStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public OrderStatus() {
    }
}