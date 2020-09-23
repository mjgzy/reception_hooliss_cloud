package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.user.WReceinfoMapper;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.service.WReceinfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WReceinfoServiceImpl extends ServiceImpl<WReceinfoMapper,WatchReceinfo> implements WReceinfoService {
    @Resource
    private WReceinfoMapper wReceinfoMapper;

    @Override
    public PageInfo<WatchReceinfo> queryAddressById(
            Integer user_id, Integer current_no, Integer page_size) throws XFException {
        if (ObjectUtils.isEmpty(user_id)){
            throw new XFException(400,user_id+"is null!" );
        }
        PageHelper.startPage(current_no,page_size);
        List<WatchReceinfo> queryAddressById=wReceinfoMapper.findAddressById(user_id);
        if (queryAddressById==null){
            throw new XFException(200, "地址数据为空!");
        }
        return new PageInfo<WatchReceinfo>(queryAddressById);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean add(WatchReceinfo addInfo) throws  XFException{
        if (ObjectUtils.isEmpty(addInfo)){
            throw new XFException(400, addInfo+"is null!");
        }
       int num=wReceinfoMapper.add(addInfo);
       if(num>0){
           return  true;
       }else{
           return  false;
       }
    }

    @Override
    public boolean modify(WatchReceinfo update_info) {
        int count=wReceinfoMapper.update(update_info);
        if(count>0){
            return  true;
        }else{
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean del(Integer i_id) throws  XFException{
        if (ObjectUtils.isEmpty(i_id)){
            throw new XFException(400, i_id+"is null!");
        }
        int delete=wReceinfoMapper.del(i_id);
        if(delete>0){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public WatchReceinfo queryAddressById(Integer address_id) throws XFException {
        if (ObjectUtils.isEmpty(address_id)){
           throw new XFException(400,address_id+"is null" );
        }
        return wReceinfoMapper.queryAddressById(address_id);

    }
}
