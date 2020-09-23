package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfkj.mapper.user.UserMessageMapper;
import com.xfkj.entity.user.UserMessage;
import com.xfkj.service.UserMessageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper,UserMessage> implements UserMessageService {


    @Resource
    private UserMessageMapper userMessageMapper;




    @Override
    public PageInfo<UserMessage> findMessagesById(Integer user_id, Integer current_no, Integer page_size) {
       PageHelper.startPage(current_no,page_size);
       List<UserMessage> findMessagesByIdlist=userMessageMapper.findMessagesById(user_id);
        return new PageInfo<UserMessage>(findMessagesByIdlist) ;
    }

    @Override
    public Integer messageCount() {
        int messageCount=userMessageMapper.count();
        return messageCount;
    }

    @Override
    public PageInfo<UserMessage> findMessagesByInfo(String info, String info_date, Integer current_no, Integer page_size) {
        PageHelper.startPage(current_no,page_size);
        List<UserMessage> findMessagesByInfoListt=userMessageMapper.findMessagesByInfo(info,info_date);
        return new PageInfo<UserMessage>(findMessagesByInfoListt) ;
    }

    @Override
    public Integer deleteMessage(Integer user_message_id) {
        int delete=userMessageMapper.deleteMessage(user_message_id);
        return delete;
    }




}
