package com.xfkj.pojo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单资源预留
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReserve implements Serializable {
    Integer id;
    String order_id;   //订单id
    Integer user_integral;  //用户积分
    Integer spike_num;      //购买库存
    String create_date;     //创建时间
    Integer watch_id;       //手表主键id
    Integer user_id;        //用户id
}
