package com.xfkj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.Style;
import com.xfkj.exceptionHandling.XFException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 手表品牌信息
 */
@RestController
public interface WatchBrandService extends IService<WatchBrand> {

    /**
     * 通过档次id寻找品牌
     * @param t_id:
     */
    PageInfo<WatchBrand> findWatchBrandByTid(Integer t_id,
                                             Integer current_no,
                                             Integer page_size) throws XFException;

    /**
     * 查出所有品牌
     */
    PageInfo<WatchBrand> findWatchBrand() throws XFException;

    /**
     * 根据品牌id查找品牌下所有系列
     */
    PageInfo<WatchSeries> findSeriesByBrand_brandId(Integer series_brand_id) throws XFException;


    /**
     * 根据品牌id查询所有系列手表
     * @param series_brand_id:品牌id,可选参数
     */
    PageInfo<WatchSeries> findSeriesByBrand_Id(Integer series_brand_id,
                                               Integer current_no,
                                               Integer page_size) throws XFException;


    /**
     * 根据系列名模糊查询系列
     * @param series_name:系列名,模糊
     */
    PageInfo<WatchSeries> findSeriesByName(String series_name,
                                           Integer current_no,
                                           Integer page_size) throws XFException;

    /**
     * 查询该品牌下的所有手表的销量总数
     */
    Integer countSalesVolume(Integer series_brand_id) throws XFException;


    /**
     * 通过系列id查询款式,
     * @param series_id:系列id
     */
    List<Style> queryStyleById(Integer series_id) throws XFException;

    /**
     * 通过品牌id查找品牌名
     * @param brand_id:品牌id
     */
    String queryNameById(Integer brand_id) throws XFException;

    /**
     * 通过品牌id查找品牌
     * @param brand_id:
     */
    WatchBrand queryBrandById(Integer brand_id) throws XFException;

    /**
     * 通过系列id查询id
     * @param series_id:
     */
    WatchSeries querySeriesById(Integer series_id) throws XFException;
}
