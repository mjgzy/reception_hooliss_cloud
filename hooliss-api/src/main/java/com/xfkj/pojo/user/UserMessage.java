package com.xfkj.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户留言表
 * @author Administrator
 *
 */
@Data
public class UserMessage implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4016097572058521565L;

	private Integer id;
	
	private Integer user_id;	
	
	private String content;	//留言内容
	
	private String create_date;	//留言时间
	
	private Integer status;	  //浏览状态,0为未浏览
}
