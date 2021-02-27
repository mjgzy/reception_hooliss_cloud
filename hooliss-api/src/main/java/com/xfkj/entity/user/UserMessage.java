package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户留言表
 * @author Administrator
 *
 */
@Data
@TableName("user_message")
public class UserMessage implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4016097572058521565L;

	@TableId(value = "id")
	private Integer id;
	
	private Integer userId;
	
	private String content;	//留言内容
	
	private String createDate;	//留言时间
	
	private Integer status;	  //浏览状态,0为未浏览
}
