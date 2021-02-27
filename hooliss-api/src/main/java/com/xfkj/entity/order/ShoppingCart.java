package com.xfkj.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.user.Wuser;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车
 */
@Data
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {

	@TableId(value = "id")
	 	private Integer id;

	 	private Integer uId;

	 	private Integer wId;


		@TableField(exist = false)
		private Wuser wUser;		//用户对象
		@TableField(exist = false)
	    private TbWatchs tbWatchs;		//手表对象

	    private Integer addCount;		//商品添加数量

	    private String addDate;			//添加时间



}
