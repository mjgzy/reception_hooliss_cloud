package com.xfkj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.Style;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.exceptionHandling.XFException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 手表品牌信息
 */
@Service
public interface WatchBrandService extends IService<WatchBrand> {

    /**findWatchBrandByTid
     * 通过档次id寻找品牌
     * @param t_id:
     */


    /**findSeriesByBrand_brandId
     * 根据品牌id查找品牌下所有系列
     */


    /**findSeriesByBrand_Id
     * 根据品牌id查询所有系列手表
     * @param series_brand_id:品牌id,可选参数
     */


    /**findSeriesByName
     * 根据系列名模糊查询系列
     * @param series_name:系列名,模糊
     */

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
     * 通过条件查询品牌
     * @param current_no:当前页
     * @param size:页数大小
     * @param param:条件
     */
    IPage<WatchBrand> getWatchBrandByParam(Integer current_no,
                                    Integer size, HashMap<String,Object> param) throws Exception;
}
