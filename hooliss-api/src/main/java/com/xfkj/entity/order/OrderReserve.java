package com.xfkj.entity.order;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单资源预留
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_reserve")
public class OrderReserve implements Serializable {

    @TableId(value = "id")
    Integer id;
    String orderId;   //订单id
    Integer userIntegral;  //用户积分
    Integer spikeNum;      //购买库存
    String createDate;     //创建时间
    Integer watchId;       //手表主键id
    Integer userId;        //用户id
}
