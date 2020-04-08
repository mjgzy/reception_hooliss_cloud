package com.xfkj.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfkj.mapper.user.UserCommentMapper;
import com.xfkj.mapper.user.UserMessageMapper;
import com.xfkj.pojo.user.CommentImages;
import com.xfkj.pojo.user.UserComment;
import com.xfkj.pojo.user.UserMessage;
import com.xfkj.service.user.UserMessageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserMessageServiceImpl implements UserMessageService {

    @Resource
    private UserCommentMapper userCommentMapper;

    @Resource
    private UserMessageMapper userMessageMapper;
    @Override
    public List<UserComment> findCommentByName(Integer user_id, Integer watch_id, String comment_date, int whether_image, Integer current_no, Integer page_size) {
//        PageHelper.startPage(current_no,page_size);
        List<UserComment> findCommentByNamelist=userCommentMapper.findCommentByName(
                user_id,watch_id,comment_date,whether_image,current_no,page_size);
        return findCommentByNamelist;
    }

    @Override
    public Integer commentCount() {
        int num=userCommentMapper.count();
        return num;
    }

    @Override
    public Integer deleteComment(Integer Comment_id) {
        int delete=userCommentMapper.deleteComment(Comment_id);
        return delete;
    }

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

    @Override
    public Integer addCommentImage(CommentImages commentImages) {
        return null;
    }

    @Override
    public boolean releaseComment(UserComment userComment) {
        return false;
    }
}
