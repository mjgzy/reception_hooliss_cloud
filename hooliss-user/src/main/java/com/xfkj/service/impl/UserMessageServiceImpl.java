package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.mapper.user.UserMessageMapper;
import com.xfkj.entity.user.UserMessage;
import com.xfkj.service.UserMessageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper,UserMessage> implements UserMessageService {
    @Resource
    private UserMessageMapper userMessageMapper;


    @Override
    public Integer messageCount() {
        int messageCount=userMessageMapper.count();
        return messageCount;
    }

//    @Override
//    public IPage<UserMessage> findMessagesByInfo(String info, String info_date, Integer current_no, Integer page_size) {
//        PageHelper.startPage(current_no,page_size);
//        List<UserMessage> findMessagesByInfoListt=userMessageMapper.findMessagesByInfo(info,info_date);
//        return new PageInfo<UserMessage>(findMessagesByInfoListt) ;
//    }


    @Override
    public IPage<UserMessage> getWatchByParam(Integer current_no, Integer size, HashMap<String, Object> param) throws Exception {
        IPage<UserMessage> page = new Page<>(current_no,size);
        QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            wrapper.eq(next.getKey(),next.getValue());
        }
        if(current_no!=-1){
            return userMessageMapper.selectPage(page,wrapper);
        }else{
            page.setRecords(userMessageMapper.selectList(wrapper));
            return page;
        }
    }
}
