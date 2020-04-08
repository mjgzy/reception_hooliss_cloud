package com.xfkj.mapper.commodity;

import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.commodity.WatchImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 手表信息,index.html
 * @author Administrator
 *
 */
public interface TbWatchsMapper {


	/**
	 * 根据上新推荐和性别分类查询信息
	 * 上新推荐,即首页的特价推荐,不需要传入参数,根据grade_id查询男女士手表离当前日期最近的8条即可
	 * @param brand_id:grade表性别id
	 */
	List<TbWatchs> findWatchByDate(Integer brand_id);

	/**
	 * 通过品牌名称查询热销排行,以销量降序,查询5条,固定
	 * @param brand_id:品牌id
	 */
	List<TbWatchs> findWatchByBrandId(Integer brand_id);
	/**
	 * 根据男女士和手表档次查询手表,即首页的男士女士推荐
	 * @param grade_id:性别id
	 * @param type_id:档次id
	 */
	List<TbWatchs> findWatchByGradeAndType(@Param("grade_id") Integer grade_id, @Param("type_id") Integer type_id);

	/**
	 * 通过手表参数id查询手表id
	 */
	Integer findWatchIdByParameterId(@Param("parameter_id") Integer parameter_id);
	/**
	 *
	 * @param key_word:搜索条件
	 * @param grade_id:性别id
	 * @param price_range_min:价格区间,起始
	 * @param price_range_max:价格区间,最高
	 * @param brand_id:品牌id
	 * @param condition:排序条件
	 */
	List<TbWatchs> findWatchByKeyWord(
            @Param("key_word") String key_word,
            @Param("grade_id") Integer grade_id,
            @Param("price_range_min") Integer price_range_min,
            @Param("price_range_max") Integer price_range_max,
            @Param("brand_id") Integer brand_id,
            @Param("condition") Integer condition
    );
	/**
	 * 通过手表编号获得单个手表信息
	 * @param w_id:手表编号
	 */
	TbWatchs findWatchById(@Param("w_id") Integer w_id);

	/**
	 * e
	 * @param w_id 手表编号
	 * @return 手表图片路径数组
	 */
	List<WatchImages> findImagesById(@Param("w_id") Integer w_id);

	/**
	 * 根据价格升序或是降序,销量降序,或者最新日期,或者根据品牌名模糊查询
	 * 页面参照男士女士手表
	 * @param grade_id:必选参数,性别id
     * @param brand_id :品牌id,可为空
	 * @param condition:条件:1表示价格降序,2表示价格升序,3表示销量降序,4表示日期降序
	 */
	List<TbWatchs> findWatchByinfo(
            @Param("grade_id") Integer grade_id,
            @Param("brand_id") Integer brand_id,
            @Param("series_id") Integer series_id,
            @Param("watch_name") String watch_name,
            @Param("watch_priceMin") Integer watch_priceMin,
            @Param("watch_priceMax") Integer watch_priceMax,
            @Param("condition") Integer condition);

    /**
     * 根据性别id选出销量最高的手表
     * @param grade_id:性别表id
     *
     */
	List<TbWatchs> findWatchByVolume(@Param("grade_id") Integer grade_id);

	/**
	 * 通过品牌id查询该品牌热销手表信息
	 * @param brand_id:品牌id
	 */
	List<TbWatchs> findSellWellTbWatchsByBrandId(Integer brand_id);

}