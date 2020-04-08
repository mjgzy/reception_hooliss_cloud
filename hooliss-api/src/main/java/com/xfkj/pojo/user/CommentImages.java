package com.xfkj.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户评论图片表
 * @author Administrator
 *
 */
@Data
public class CommentImages implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9083270518654852227L;

	private Integer id;
	
	private Integer user_comment_id;	//用户评论表主键id	
	
	private String image_url;		//图片地址
}
