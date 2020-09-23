package com.xfkj.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.entity.user.Wuser;
import com.xfkj.pojo.vo.order.OrderStatusVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表
 * @author Administrator
 *
 */
@Data
@TableName("watch_order")
public class WatchOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -832454167599115287L;

	private Integer id;	//主键id
	
	private String orderId;	//订单编号
	
	private Integer oUid;

	private Integer watchId;		//手表id

	private BigDecimal orderPrice;			//订单总金额
	
	private String orderDate;
	
	private Integer watchCount;


	private double discount;				//优惠

	@TableField(exist = false)
	private Wuser wuser; //用户信息
	@TableField(exist = false)
	private TbWatchs lt;		//订单商品集合
	@TableField(exist = false)
	private Express express;	//快递信息

	private Integer wReceinfoId;	//地址id
	@TableField(exist = false)
	private WatchReceinfo watchReceinfo;	//地址信息
	@TableField(exist = false)
	private OrderStatus orderStatus;		//订单状态

	private String orderType;				//订单类型,普通/秒杀
	
}
