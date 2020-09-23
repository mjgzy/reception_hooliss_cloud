package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xfkj.entity.user.UserMessage;
import com.xfkj.exceptionHandling.XFException;

/**
 * 包含用户评论,消息,留言,等服务
 */
public interface UserMessageService extends IService<UserMessage> {


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
