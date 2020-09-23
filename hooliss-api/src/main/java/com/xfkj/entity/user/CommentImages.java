package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户评论图片表
 * @author Administrator
 *
 */
@Data
@TableName("comment_images")
public class CommentImages implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9083270518654852227L;

	private Integer id;
	
	private Integer userCommentId;	//用户评论表主键id
	
	private String imageUrl;		//图片地址
}
