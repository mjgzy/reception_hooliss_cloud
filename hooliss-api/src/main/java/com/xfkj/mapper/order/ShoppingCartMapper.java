package com.xfkj.mapper.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.order.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 购物车,cart.html
 * @author Administrator
 *
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {


	/**
	 * 查询该用户是否有此条购物车信息
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
		Integer queryWatchContains(@Param("uId") Integer u_id, @Param("wId") Integer w_id);
		/**
		 * 通过用户id和手表获取购物车信息
		 * @param u_id :用户id,必选
		 * @param w_id :手表编号,可选参数
		 */
		ShoppingCart findShoppingCartByIdAndW_Id(@Param("uId") Integer u_id, @Param("wId") Integer w_id);
		/**
		 * 通过用户id和手表获取购物车信息
		 * @param u_id :用户id,必选
		 * @param w_id :手表编号,可选参数
		 */
		List<ShoppingCart> findShoppingCartsByIdAndW_Id(@Param("uId") Integer u_id, @Param("wId") Integer w_id);
		/**
		 * 添加购物车信息
		 * @param sc:购物车对象
		 */
		Integer addShopInfo(ShoppingCart sc);
		/**
		 * 删除购物车信息
		 * @param u_id:购物车id
		 */
		Integer deleteShopInfoById(@Param("uId") Integer u_id, @Param("wId") Integer w_id);
	/**
	 * 删除购物车信息
	 * @param watch_id:手表主键id
	 */
	Integer deleteShopInfoByWId(Integer watch_id);
		/**
		 * 添加物品数量
		 * @param u_id:用户�?
		 */
		Integer addCount(@Param("uId") Integer u_id, @Param("wId") Integer w_id, @Param("count") Integer count);
		/**
		 * 添加物品数量
		 * @param u_id:用户�?
		 */
		Integer addCountBuy(@Param("uId") Integer u_id, @Param("wId") Integer w_id, @Param("count") Integer count);

	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	Integer existenceCart(@Param("uId")Integer u_id, @Param("wId")Integer w_id);
}