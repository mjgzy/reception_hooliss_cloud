package com.xfkj.pojo.vo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.order.OrderReserve;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单资源预留
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderReserveVo extends OrderReserve {

}
