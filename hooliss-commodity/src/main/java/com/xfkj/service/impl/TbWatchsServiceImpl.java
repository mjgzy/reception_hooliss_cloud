package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.commodity.TbWatchsMapper;
import com.xfkj.mapper.commodity.WatchParameterMapper;
import com.xfkj.mapper.commodity.WatchTypeMapper;
import com.xfkj.entity.commodity.*;
import com.xfkj.service.TbWatchsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TbWatchsServiceImpl extends ServiceImpl<TbWatchsMapper,TbWatchs> implements TbWatchsService {

    @Resource
    private TbWatchsMapper tbWatchsMapper ;

    @Resource
    private WatchParameterMapper watchParameterMapper;


    @Resource
    private WatchTypeMapper watchTypeMapper;

    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存





    @Override
    public TbWatchs queryWatchById(Integer w_id) throws XFException {
        if(w_id==null||w_id==0){
            throw new XFException("w_id is null!");
        }
        TbWatchs watchById = tbWatchsMapper.findWatchById(w_id);
        return watchById;
    }

    @Override
    public List<WatchImages> queryImagesById(Integer w_id) throws XFException  {
        if (ObjectUtils.isEmpty(w_id)){
            throw new XFException(400, w_id+"is null!");
        }
        return tbWatchsMapper.findImagesById(w_id);
    }





    @Override
    public WatchParameter findWatchParameter(Integer watch_id) throws XFException{
         if (ObjectUtils.isEmpty(watch_id)){
          throw new XFException(400, watch_id+"is null!");
         }
        return watchParameterMapper.findWatchParameter(watch_id);
    }
    @Cacheable(cacheNames = "watchParameterAll",unless = "#result==null")

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateWatchId(Integer parameter_id, Integer watch_id) throws  XFException{
        if (ObjectUtils.isEmpty(parameter_id)||ObjectUtils.isEmpty(watch_id)){
            throw new XFException(400, parameter_id+" or "+watch_id+" is null!");
        }
        Integer integer = watchParameterMapper.updateWatchId(parameter_id, watch_id);
        if (ObjectUtils.isEmpty(integer)){
            throw new XFException(500,"更新失败!" );
        }
        return true;
    }

    @Override
    public IPage<TbWatchs> getWatchByParam(Integer current_no, Integer size, HashMap<String, Object> param) throws Exception{
        param.put("status",1);
        IPage<TbWatchs> page = new Page<>(current_no,size);
        QueryWrapper<TbWatchs> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            if(next.getKey().equals("watch_name")){
                wrapper.like("w.watch_name",next.getValue());
            }else if(next.getKey().equals("price_range_min")){
                wrapper.between("w.price_range_min",next.getValue(),param.get("price_range_max"));
            }else if(next.getKey().equals("condition")){
                  int condition = (int)next.getValue();
                  if(condition==1){
                      wrapper.orderByDesc("w.so_price");
                  }else if(condition==2){
                      wrapper.orderByAsc("w.so_price");
                  }else if(condition==3){
                      wrapper.orderByDesc("w.watch_sell_count");
                  }else if(condition==4){
                      wrapper.orderByDesc("w.watch_date");
                  }else if(condition==5){
                      wrapper.orderByDesc("w.watch_price");
                  }else{
                      wrapper.orderByDesc("w.watch_id");
                  }
            }
            else{
                wrapper.eq("w."+next.getKey(),next.getValue());
            }
        }
        return tbWatchsMapper.findWatchByParam(page,wrapper);
    }

    @Override
    public List<WatchType> queryWatchTypeAll() {
        return watchTypeMapper.getAll();
    }

}
