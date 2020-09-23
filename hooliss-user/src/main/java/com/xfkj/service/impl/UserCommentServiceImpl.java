package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.user.UserCommentMapper;
import com.xfkj.entity.user.CommentImages;
import com.xfkj.entity.user.UserComment;
import com.xfkj.service.UserCommentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper,UserComment> implements UserCommentService {


    @Resource
    private UserCommentMapper userCommentMapper;


    @Override
    public List<UserComment> findCommentByName(Integer user_id, Integer watch_id, String comment_date, int whether_image, Integer current_no, Integer page_size) throws XFException {
        return userCommentMapper.findCommentByName(user_id,watch_id,comment_date,whether_image,current_no,page_size);
    }

    @Override
    public Integer deleteComment(Integer comment_id) throws XFException {
        return userCommentMapper.deleteComment(comment_id);
    }

    @Override
    public Integer addCommentImage(CommentImages commentImages) throws XFException {
        return null;
    }

    @Override
    public boolean releaseComment(UserComment userComment) throws XFException {
        return false;
    }
}
