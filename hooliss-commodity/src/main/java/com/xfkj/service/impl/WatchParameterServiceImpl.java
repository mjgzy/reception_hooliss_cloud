package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.mapper.commodity.WatchParameterMapper;
import com.xfkj.entity.commodity.WatchParameter;
import com.xfkj.service.WatchParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
@Transactional
public class WatchParameterServiceImpl extends ServiceImpl<WatchParameterMapper, WatchParameter> implements WatchParameterService {

    @Resource
    private WatchParameterMapper watchParameterMapper;

    @Override
    public IPage<WatchParameter> getParameterByParam(Integer current_no, Integer size, HashMap<String, Object> param) throws Exception {
        IPage<WatchParameter> page = new Page<>(current_no,size);
        QueryWrapper<WatchParameter> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            wrapper.eq(next.getKey(),next.getValue());
        }
        if(current_no==-1){
            page.setRecords(watchParameterMapper.selectList(wrapper));
        }else{
            page = watchParameterMapper.selectPage(page,wrapper);
        }
        return page;
    }
}
