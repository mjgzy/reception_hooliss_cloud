package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.List;

@Component
@Transactional(propagation= Propagation.REQUIRED)
public class WatchBrandServiceImpl extends ServiceImpl<WatchBrandMapper,WatchBrand> implements WatchBrandService {
    @Resource
    private WatchBrandMapper watchBrandMapper;

    @Resource
    private WatchSeriesMapper watchSeriesMapper;

    @Override
    public PageInfo<WatchBrand> findWatchBrandByTid(Integer t_id, Integer current_no, Integer page_size) {
        PageHelper.startPage(current_no,page_size);
        List<WatchBrand> list=watchBrandMapper.findWatchBrandByTid(t_id);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<WatchBrand> findWatchBrand() {
        List<WatchBrand> list = watchBrandMapper.findWatchBrand();
        return new PageInfo<>(list);
    }

    @Override
    public String queryNameById(Integer brand_id) {
        if (brand_id!=null&&brand_id!=0){
            return watchBrandMapper.findNameById(brand_id);
        }
        return null;
    }

    @Override
    public PageInfo<WatchSeries> findSeriesByBrand_brandId(Integer series_brand_id) {
        List<WatchSeries> serieslist = watchSeriesMapper.findSeriesByBrand_brandId(series_brand_id);
        return new PageInfo<>(serieslist);
    }

    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存
    @Cacheable(cacheNames = "seriesByBrandId",unless = "#result==null")
    @Override
    public PageInfo<WatchSeries> findSeriesByBrand_Id(Integer series_brand_id, Integer current_no, Integer page_size) {
        System.err.println("findSeriesByBrand_Id进入业务层");
        PageHelper.startPage(current_no,page_size);
        List<WatchSeries> list=watchSeriesMapper.findSeriesByBrand_Id(series_brand_id);
        System.err.println("findSeriesByBrand_Id进入持久层");
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<WatchSeries> findSeriesByName(String series_name, Integer current_no, Integer page_size) {
        PageHelper.startPage(current_no,page_size);
        List<WatchSeries> list=watchSeriesMapper.findSeriesByName(series_name);
        return new PageInfo<>(list);
    }

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
    @Cacheable(value = "brand",unless = "#result==null")
    @Override
    public WatchBrand queryBrandById(Integer brand_id) throws XFException {
        WatchBrand brandById = watchBrandMapper.findBrandById(brand_id);
        if (brandById==null||brand_id==0){
            throw new XFException(400,brand_id+" is null!" );
        }
        return  brandById;

    }

    @Override
    public WatchSeries querySeriesById(Integer series_id)throws XFException {
        if (series_id!=null && series_id!=0){
            WatchSeries seriesById = watchSeriesMapper.findSeriesById(series_id);
            if (seriesById==null){
                throw new XFException(500,"手表系列信息为空!");
            }
            return seriesById;
        }
        return null;
    }
}
