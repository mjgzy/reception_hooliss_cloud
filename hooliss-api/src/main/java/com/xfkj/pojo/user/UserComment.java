package com.xfkj.pojo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户评价表
 * @author Administrator
 *
 */
@Data
public class UserComment implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6390788460047678576L;

	private Integer id;
	
	private Integer user_id;
	
	private Integer watch_id;	//手表id
	
	private String content;		//用户评价
	
	private int star;	//评论星级

	private Integer image_id; //图片ID
	
	private String video;	//视频路径
	
	private Integer browse_status;	//管理员浏览状态,0表示未浏览
	
	private Integer reply_status;		//客服是否回复
	
	private String reply_content;	//回复内容

	private String replyDate;			//客服回复日期
	
	private String publish_date;	//发表日期
	
	private List<CommentImages> comment_Images;	//评论图片表

    private Watch_vip watch_vip;

    private Wuser wuser;
}
