package com.xfkj.mapper.commodity;

import com.xfkj.pojo.brand.WatchSeries;
import com.xfkj.pojo.commodity.Style;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 手表系列接口
 * @author Administrator
 *
 */
public interface WatchSeriesMapper {
	/**
	 * 根据品牌名显示所有系列
	 * @param series_brand_id
	 * @return
	 */
	List<WatchSeries> findSeriesByBrand_brandId(@Param("series_brand_id") Integer series_brand_id);

	/**
	 * 根据品牌id查询所有系列手表
	 * @param series_brand_id:品牌id,可选参数
	 */
	List<WatchSeries> findSeriesByBrand_Id(
            @Param("series_brand_id") Integer series_brand_id);
	
	
	/**
	 * 根据系列名模糊查询系列
	 * @param series_name:系列名,模糊
	 */
	List<WatchSeries> findSeriesByName(
            @Param("series_name") String series_name);

	/**
	 * 通过系列id查询款式,
	 * @param series_id:系列id
	 */
	List<Style> findStyleById(Integer series_id);

	/**
	 * 通过系列id查询系列
	 * @param series_id
	 * @return
	 */
	WatchSeries findSeriesById(Integer series_id);
}
