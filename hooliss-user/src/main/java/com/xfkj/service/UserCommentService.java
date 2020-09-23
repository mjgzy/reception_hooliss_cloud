package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.user.CommentImages;
import com.xfkj.entity.user.UserComment;
import com.xfkj.exceptionHandling.XFException;

import java.util.List;

/**
 * 包含用户评论,消息,留言,等服务
 */
public interface UserCommentService extends IService<UserComment> {

    /**
     * 查询用户评价
     * @param user_id:用户id,可选参数
     * @param comment_date:评论时间,可选参数,精确查找
     * @param whether_image:是否为有图评价,0为是,1为否,可选参数
     */
    List<UserComment> findCommentByName(Integer user_id,
                                        Integer watch_id, String comment_date, int whether_image,
                                        Integer current_no,
                                        Integer page_size) throws XFException;

    /**
     * 删除评论,需要首先删除评论图片
     * @param comment_id:评论表主键
     */
    Integer deleteComment(Integer comment_id) throws XFException;

    /**
     * 新增评论图片
     * @param commentImages:评论图片
     */
    Integer addCommentImage(CommentImages commentImages) throws XFException;

    /**
     * 发表评论
     * @param userComment:评论对象,
     */
    boolean releaseComment(UserComment userComment) throws XFException;



}
