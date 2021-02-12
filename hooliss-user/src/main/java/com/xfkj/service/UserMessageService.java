package com.xfkj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.user.UserMessage;

import java.util.HashMap;

/**
 * 包含用户评论,消息,留言,等服务
 */
public interface UserMessageService extends IService<UserMessage> {


    /**
     * 通过用户id查询留言信息
     * @param user_id:findMessagesById
     */
//    PageInfo<UserMessage> findMessagesById(
//            Integer user_id, Integer current_no,
//            Integer page_size) throws XFException;
    /**
     * 查询用户总留言数
     */
    Integer messageCount();
    /**
     * 根据留言信息或时间查询留言
     * @param info:可选参数.留言信息,模糊查询
     * @param info_date:时间,精确查找
     */
//    PageInfo<UserMessage> findMessagesByInfo(
//            String info, String info_date,
//            Integer current_no, Integer page_size) throws XFException;

    /**
     * 根据条件查询用户地址
     * @param current_no:当前页
     * @param size:页数大小
     * @param param:查询条件
     * @return
     * @throws Exception
     */
    IPage<UserMessage> getWatchByParam(Integer current_no,
                                         Integer size, HashMap<String,Object> param) throws Exception;
}
