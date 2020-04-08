package com.xfkj.mapper.commodity;

import com.xfkj.pojo.brand.WatchBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌信息
 * @author Administrator
 *
 */
public interface WatchBrandMapper {
	/**
	 * 通过档次id寻找品牌
	 * @param t_id
	 * @return
	 */
	List<WatchBrand> findWatchBrandByTid(@Param("t_id") Integer t_id);

	/**
	 * 查出所有品牌
	 */
	List<WatchBrand> findWatchBrand();

	/**
	 * 查询该品牌下的所有手表的销量总数
	 * @param brand_id:品牌id
	 */
	Integer countSalesVolume(Integer brand_id);

	/**
	 * 通过品牌id查找品牌
	 * @param brand_id:
	 */
	String findNameById(Integer brand_id);

	/**
	 * 通过品牌id查找品牌
	 * @param brand_id:
	 */
	WatchBrand findBrandById(Integer brand_id);


}