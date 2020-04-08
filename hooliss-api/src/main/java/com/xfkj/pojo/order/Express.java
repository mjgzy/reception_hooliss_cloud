package com.xfkj.pojo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 快递信息类
 * @author Administrator
 *
 */
@Data
public class Express implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3478668479633620494L;

	private Integer id;
	
	private Integer order_id;	//订单id
	
	private String company;		//快递公司
	
	private String oddnumber;		//快递单号
	
	private String create_date;		//快递创建时间
}
