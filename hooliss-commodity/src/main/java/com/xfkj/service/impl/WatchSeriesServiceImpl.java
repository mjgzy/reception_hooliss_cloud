package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.mapper.commodity.WatchSeriesMapper;
import com.xfkj.service.WatchSeriesService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional(propagation= Propagation.REQUIRED)
public class WatchSeriesServiceImpl extends ServiceImpl<WatchSeriesMapper,WatchSeries> implements WatchSeriesService {

    @Resource
    private WatchSeriesMapper seriesMapper;
    @Override
    public IPage<WatchSeries> getListByParam(IPage<WatchSeries> page,HashMap<String,Object> param) {
        QueryWrapper<WatchSeries> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            if(key.equals("series_name")){
                wrapper.like(key,next.getValue());
            }else{
                wrapper.eq(key,next.getValue());
            }
        }
        if(page!=null){
            return seriesMapper.selectPage(page,wrapper);
        }else{
            page = new Page<>(-1,-1);
            page.setRecords(seriesMapper.selectList(wrapper));
            return page;
        }
    }
}
