package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户评价表
 * @author Administrator
 *
 */
@Data
@TableName("user_comment")
public class UserComment implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6390788460047678576L;

	@TableId(value = "id")
	private Integer id;
	
	private Integer userId;
	
	private Integer watchId;	//手表id
	
	private String content;		//用户评价
	
	private int star;	//评论星级

	private Integer imageId; //图片ID
	
	private String video;	//视频路径
	
	private Integer browseStatus;	//管理员浏览状态,0表示未浏览
	
	private Integer replyStatus;		//客服是否回复
	
	private String replyContent;	//回复内容

	private String replyDate;			//客服回复日期
	
	private String publishDate;	//发表日期

	@TableField(exist = false)
	private List<CommentImages> commentImages;	//评论图片表
	@TableField(exist = false)
	private WatchVip watchVip;
	@TableField(exist = false)
    private Wuser wuser;

    private Boolean havePicture;		//是否有图
}
