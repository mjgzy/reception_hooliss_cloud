package com.xfkj.mapper.commodity;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchImages;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


/**
 * 手表信息,index.html
 * @author Administrator
 *
 */
public interface TbWatchsMapper extends BaseMapper<TbWatchs> {


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
	List<TbWatchs> findWatchByGradeAndType(@Param("gradeId") Integer grade_id, @Param("typeId") Integer type_id);

	/**
	 * 通过手表参数id查询手表id
	 */
	Integer findWatchIdByParameterId(@Param("parameterId") Integer parameter_id);
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
            @Param("keyWord") String key_word,
            @Param("gradeId") Integer grade_id,
            @Param("priceRangeMin") Integer price_range_min,
            @Param("priceRangeMax") Integer price_range_max,
            @Param("brandId") Integer brand_id,
            @Param("condition") Integer condition
    );
	/**
	 * 通过手表编号获得单个手表信息
	 * @param w_id:手表编号
	 */
	TbWatchs findWatchById(Integer w_id);

	/**
	 * e
	 * @param w_id 手表编号
	 * @return 手表图片路径数组
	 */
	List<WatchImages> findImagesById(@Param("wId") Integer w_id);

	/**
	 * 根据价格升序或是降序,销量降序,或者最新日期,或者根据品牌名模糊查询
	 * 页面参照男士女士手表
	 * @param grade_id:必选参数,性别id
     * @param brand_id :品牌id,可为空
	 * @param condition:条件:1表示价格降序,2表示价格升序,3表示销量降序,4表示日期降序
	 */
	List<TbWatchs> findWatchByinfo(
            @Param("gradeId") Integer grade_id,
            @Param("brandId") Integer brand_id,
            @Param("seriesId") Integer series_id,
            @Param("watchName") String watch_name,
            @Param("watchPriceMin") Integer watch_priceMin,
            @Param("watchPriceMax") Integer watch_priceMax,
            @Param("condition") Integer condition);

    /**
     * 根据性别id选出销量最高的手表
     * @param grade_id:性别表id
     *
     */
	List<TbWatchs> findWatchByVolume(@Param("gradeId") Integer grade_id);

	/**
	 * 通过品牌id查询该品牌热销手表信息
	 * @param brand_id:品牌id
	 */
	List<TbWatchs> findSellWellTbWatchsByBrandId(Integer brand_id);

	/**
	 * 根据条件查询
	 * 页面参照男士女士手表
	 * @param map.grade_id: 性别id
	 * @param map.brand_id :品牌id
	 * @param map.watch_type_id :手表类别id
	 * @param map.watch_name :手表名称,模糊查询
	 * @param map.price_range_min :价格区间
	 * @param map.condition:条件:1表示价格降序,2表示价格升序,3表示销量降序,4表示日期降序
	 */
	IPage<TbWatchs> findWatchByParam(IPage<TbWatchs> page, @Param(Constants.WRAPPER) Wrapper<TbWatchs> wrapper);
}