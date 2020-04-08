package com.xfkj.service.user;

import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.user.CommentImages;
import com.xfkj.pojo.user.UserComment;
import com.xfkj.pojo.user.UserMessage;

import java.util.List;

/**
 * 包含用户评论,消息,留言,等服务
 */
public interface UserMessageService {

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
     * 查询评论条数
     */
    Integer commentCount();
    /**
     * 删除评论,需要首先删除评论图片
     * @param Comment_id:评论表主键
     */
    Integer deleteComment(Integer Comment_id) throws XFException;

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

    /**
     * 通过用户id查询留言信息
     * @param user_id:findMessagesById
     */
    PageInfo<UserMessage> findMessagesById(
            Integer user_id, Integer current_no,
                                           Integer page_size) throws XFException;
    /**
     * 查询用户总留言数
     */
    Integer messageCount();
    /**
     * 根据留言信息或时间查询留言
     * @param info:可选参数.留言信息,模糊查询
     * @param info_date:时间,精确查找
     */
    PageInfo<UserMessage> findMessagesByInfo(
            String info, String info_date,
            Integer current_no, Integer page_size) throws XFException;

    /**
     * 删除留言
     * @param user_message_id
     * @return
     */
    Integer deleteMessage(Integer user_message_id) throws XFException;

}
