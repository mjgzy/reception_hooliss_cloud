package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchImages;
import com.xfkj.entity.commodity.WatchParameter;
import com.xfkj.entity.commodity.WatchType;
import com.xfkj.exceptionHandling.XFException;

import java.util.List;

/**
 * 手表服务
 */
public interface TbWatchsService extends IService<TbWatchs> {

	/**
	 * 根据上新推荐和性别分类查询信息
	 * 上新推荐,根据grade_id,按照日期降序
	 * @param brand_id:grade表性别id
	 */
	PageInfo<TbWatchs> queryWatchByDate(Integer brand_id,
                                        Integer current_no,
                                        Integer page_size) throws XFException;



	/**
	 * 通过品牌id查询热销排行,以销量降序
	 * @param brand_id:品牌名
	 */
	PageInfo<TbWatchs> findWatchByBrandId(Integer brand_id,
                                          Integer current_no,
                                          Integer page_size) throws XFException;
	/**
	 * 根据男女士和手表档次查询手表,即首页的男士女士推荐
	 * @param grade_id:性别id
	 * @param type_id:档次id
	 */
	PageInfo<TbWatchs> findWatchByGradeAndType(
            Integer grade_id, Integer type_id, Integer current_no,
            Integer page_size) throws XFException;
	/**
	 * 根据男女士和手表档次查询手表,即首页的男士女士推荐
	 * @param grade_id:性别id
	 * @param price_range_min:价格区间
	 */
	PageInfo<TbWatchs> findWatchByKeyWord(
            String key_word, Integer grade_id, Integer price_range_min,
            Integer price_range_max,
            Integer brand_id, Integer condition, Integer current_no,
            Integer page_size) throws XFException;
	/**
	 * 通过手表编号获得单个手表信息
	 * @param w_id:手表编号
	 */
	TbWatchs queryWatchById(Integer w_id) throws XFException;

	/**
	 * 查询所有手表图片详情
	 * @param w_id 手表编号
	 * @return 手表图片路径数组
	 */
	List<WatchImages> queryImagesById(Integer w_id) throws XFException;

	/**
	 * 	 页面参照男士女士手表
	 * @param grade_id 可选参数,性别id
	 * @param brand_id 品牌id,可为空
	 * @param series_id  系列id
	 * @param watch_name 根据品牌id 进行模糊查询
	 * @param watch_priceMin 查询最小价格低至
	 * @param watch_priceMax 查询最高价格封顶
	 * @param condition
	 * 条件:0表示默认排序,1表示价格降序,2表示价格升序,3表示销量降序,4表示日期降序
	 * 	           根据价格升序或是降序,销量降序,或者最新日期,或者根据品牌名模糊查询
	 * @param current_no	分页 当前第几条开始
	 * @param page_size		分页显示的数量
	 * @return
	 */
	PageInfo<TbWatchs> queryWatchByinfo(Integer grade_id, Integer brand_id,
                                        Integer series_id, String watch_name, Integer watch_priceMin,
                                        Integer watch_priceMax,
                                        Integer condition, Integer current_no,
                                        Integer page_size) throws XFException;



	/**
	 * 根据性别id选出销量最高的手表
	 * @param grade_id:性别表主键
	 * @param size:要显示的手表数量
	 */
	PageInfo<TbWatchs>  queryWatchByVolume(Integer grade_id,
                                           Integer size,
                                           Integer current_no,
                                           Integer page_size) throws XFException;

	/**
	 * 通过手表id查询手表参数
	 * @param watch_id
	 */
	WatchParameter findWatchParameter(Integer watch_id) throws XFException;


	Boolean updateWatchId(Integer parameter_id, Integer watch_id) throws XFException;

	/**
	 * 获得所有手表档次
	 * @return:手表档次集合
	 */
	List<WatchType> queryWatchTypeAll() throws XFException;
}
