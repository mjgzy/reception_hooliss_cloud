package com.xfkj.entity.order;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 快递信息类
 * @author Administrator
 *
 */
@Data
@TableName(value = "express")
public class Express implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3478668479633620494L;

	@TableId(value = "id")
	private Integer id;

	private Integer orderId;	//订单id
	
	private String company;		//快递公司
	
	private String oddNumber;		//快递单号
	
	private String createDate;		//快递创建时间
}
