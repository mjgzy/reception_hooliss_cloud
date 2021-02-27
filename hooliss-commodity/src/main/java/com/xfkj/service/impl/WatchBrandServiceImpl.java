package com.xfkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.commodity.WatchBrandMapper;
import com.xfkj.mapper.commodity.WatchSeriesMapper;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.Style;
import com.xfkj.service.WatchBrandService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional(propagation= Propagation.REQUIRED)
public class WatchBrandServiceImpl extends ServiceImpl<WatchBrandMapper,WatchBrand> implements WatchBrandService {
    @Resource
    private WatchBrandMapper watchBrandMapper;

    @Resource
    private WatchSeriesMapper watchSeriesMapper;


    @Override
    public String queryNameById(Integer brand_id) {
        if (brand_id!=null&&brand_id!=0){
            return watchBrandMapper.findNameById(brand_id);
        }
        return null;
    }


//    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存
//    @Cacheable(cacheNames = "seriesByBrandId",unless = "#result==null")
//    @Override
//    public PageInfo<WatchSeries> findSeriesByBrand_Id(Integer series_brand_id, Integer current_no, Integer page_size) {
//        System.err.println("findSeriesByBrand_Id进入业务层");
//        PageHelper.startPage(current_no,page_size);
//        List<WatchSeries> list=watchSeriesMapper.findSeriesByBrand_Id(series_brand_id);
//        System.err.println("findSeriesByBrand_Id进入持久层");
//        return new PageInfo<>(list);
//    }

//    @Override
//    public PageInfo<WatchSeries> findSeriesByName(String series_name, Integer current_no, Integer page_size) {
//        PageHelper.startPage(current_no,page_size);
//        List<WatchSeries> list=watchSeriesMapper.findSeriesByName(series_name);
//        return new PageInfo<>(list);
//    }

    @Override
    public Integer countSalesVolume(Integer series_brand_id) {
        return watchBrandMapper.countSalesVolume(series_brand_id);
    }


    @Cacheable(value = "style",unless = "#result==null")
    @Override
    public List<Style> queryStyleById(Integer series_id) {
        if (series_id!=null&&series_id!=0){
         return   watchSeriesMapper.findStyleById(series_id);
        }
        return null;
    }


    @Override
    public IPage<WatchBrand> getWatchBrandByParam(Integer current_no, Integer size, HashMap<String, Object> param) throws Exception {
        IPage<WatchBrand> page = new Page<>(current_no,size);
        QueryWrapper<WatchBrand> wrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            wrapper.eq(next.getKey(),next.getValue());
        }
        if(current_no==-1){
            page.setRecords(watchBrandMapper.selectList(wrapper));
        }else{
            page = watchBrandMapper.selectPage(page,wrapper);
        }
        return page;
    }
}
