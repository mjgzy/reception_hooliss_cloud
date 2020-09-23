package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.List;

@Service
public class TbWatchsServiceImpl extends ServiceImpl<TbWatchsMapper,TbWatchs> implements TbWatchsService {

    @Resource
    private TbWatchsMapper tbWatchsMapper ;

    @Resource
    private WatchParameterMapper watchParameterMapper;


    @Resource
    private WatchTypeMapper watchTypeMapper;

    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存


    /**
     * store
     *      watchsByDate
     */
    @Cacheable(cacheNames = "watchsByDate",unless = "#result==null")
    @Override
    public PageInfo<TbWatchs> queryWatchByDate(Integer grade_id, Integer current_no, Integer page_size) throws XFException {
        if (ObjectUtils.isEmpty(grade_id)){
            throw new XFException(400,grade_id+" is null!" );
        }
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = tbWatchsMapper.findWatchByDate(grade_id);
        return new PageInfo<TbWatchs>(list);
    }

    @Override
    public PageInfo<TbWatchs> findWatchByBrandId(Integer brand_id, Integer current_no, Integer page_size) throws XFException  {
        if (ObjectUtils.isEmpty(brand_id)){
            throw new XFException(400,brand_id+" is null!" );
        }
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = null;
            list = tbWatchsMapper.findWatchByBrandId(brand_id);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TbWatchs> findWatchByGradeAndType(Integer grade_id, Integer type_id, Integer current_no, Integer page_size) throws  XFException {
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = tbWatchsMapper.findWatchByGradeAndType(grade_id,type_id);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TbWatchs> findWatchByKeyWord(
            String key_word, Integer grade_id, Integer price_range_min, Integer price_range_max,
            Integer brand_id, Integer condition, Integer current_no, Integer page_size) throws XFException{
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = tbWatchsMapper.findWatchByKeyWord(key_word, grade_id, price_range_min,price_range_max
                ,brand_id  ,condition );
        return new PageInfo<TbWatchs>(list);
    }

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



    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存
    @Cacheable(cacheNames = "watchsByInfo",unless = "#result==null")
    @Override
    public PageInfo<TbWatchs> queryWatchByinfo(
            Integer grade_id, Integer brand_id,Integer series_id,
            String watch_name, Integer watch_priceMin,Integer watch_priceMax,
            Integer condition, Integer current_no, Integer page_size) throws XFException {
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = tbWatchsMapper.findWatchByinfo(
                grade_id,brand_id,series_id, watch_name, watch_priceMin, watch_priceMax,condition);
        return new PageInfo<TbWatchs>(list);
    }

    @Override
    @Cacheable(cacheNames = "watchByVolume",unless = "#result==null")
    public PageInfo<TbWatchs> queryWatchByVolume(Integer grade_id, Integer size, Integer current_no, Integer page_size) {
        PageHelper.startPage(current_no,page_size);
        List<TbWatchs> list = tbWatchsMapper.findWatchByVolume(grade_id);
        return new PageInfo<TbWatchs>(list);
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
    public List<WatchType> queryWatchTypeAll() {
        return watchTypeMapper.getAll();
    }

}
