package com.xfkj.mapper.user;

import com.xfkj.pojo.user.CommentImages;
import com.xfkj.pojo.user.UserComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户评论管理,huiyuan.html
 * @author Administrator
 *
 */
public interface UserCommentMapper {

	/**
	 * 根据用户id或者时间查找评论信息
	 * @param user_id:用户id,可选参数
	 * @param watch_id：手表id 可选参数
	 * @param comment_date:评论时间,可选参数,精确查找
	 * @param whether_image:是否为有图评价,0为是,1为否,可选参数
	 */
	List<UserComment> findCommentByName(
            @Param("user_id") Integer user_id,
            @Param("watch_id") Integer watch_id,
            @Param("comment_date") String comment_date,
            @Param("whether_image") int whether_image,
            @Param("current_no") Integer current_no,
            @Param("page_size") Integer page_size);


	/**
	 * 新增评论信息
	 * @param userComment:评论对象
	 */
	Integer addComment(UserComment userComment);

	/**
	 * 新增评论图片
	 * @param commentImages:评论图片
	 */
	Integer addCommentImage(CommentImages commentImages);

	/**
	 * 查询评论条数
	 */
	Integer count();
	/**
	 * 删除评论,需要首先删除评论图片
	 * @param Comment_id:主键
	 */
	Integer deleteComment(@Param("Comment_id") Integer Comment_id);
}
