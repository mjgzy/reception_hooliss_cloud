package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.user.WReceinfoMapper;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.service.WReceinfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Component
public class WReceinfoServiceImpl extends ServiceImpl<WReceinfoMapper,WatchReceinfo> implements WReceinfoService {
    @Resource
    private WReceinfoMapper wReceinfoMapper;

    @Override
    public IPage<WatchReceinfo> getReceInfoByParam(Integer current_no, Integer size, HashMap<String, Object> param) throws Exception {
        IPage<WatchReceinfo> page = new Page<>(current_no,size);
        QueryWrapper<WatchReceinfo> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            wrapper.eq(next.getKey(),next.getValue());
        }
        if(current_no!=-1){
            return wReceinfoMapper.selectPage(page,wrapper);
        }else{
            page.setRecords(wReceinfoMapper.selectList(wrapper));
            return page;
        }
    }
}
