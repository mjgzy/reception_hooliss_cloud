package com.xfkj.pojo.order;

import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.pojo.user.Wuser;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表
 * @author Administrator
 *
 */
@Data
public class WatchOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -832454167599115287L;

	private Integer id;	//主键id
	
	private String order_id;	//订单编号
	
	private Integer o_uid;

	private Integer watch_id;		//手表id

	private BigDecimal order_price;			//订单总金额
	
	private String order_date;
	
	private Integer watch_count;


	private double discount;				//优惠

	private Wuser wuser; //用户信息

	private TbWatchs lt;		//订单商品集合

	private Express express;	//快递信息

	private Integer w_receinfo_id;	//地址id

	private WatchReceinfo watch_receinfo;	//地址信息

	private OrderStatus order_status;		//订单状态

	private String order_type;				//订单类型,普通/秒杀
	
}
