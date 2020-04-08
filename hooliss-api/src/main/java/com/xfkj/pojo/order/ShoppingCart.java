package com.xfkj.pojo.order;

import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.utils.DateFormatUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车
 */
@Data
public class ShoppingCart implements Serializable {

	 	private Integer id;

	 	private Integer u_id;

	 	private Integer w_id;



		private Wuser w_user;		//用户对象

	    private TbWatchs tb_watchs;		//手表对象

	    private Integer add_count;		//商品添加数量

	    private String add_date;			//添加时间



}
