package com.xfkj.mapper.order;


import com.xfkj.pojo.order.ShoppingCart;
import com.xfkj.tools.ResultBody;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 购物车,cart.html
 * @author Administrator
 *
 */
public interface ShoppingCartMapper {


	/**
	 * 查询该用户是否有此条购物车信息
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
		Integer queryWatchContains(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id);
		/**
		 * 通过用户id和手表获取购物车信息
		 * @param u_id :用户id,必选
		 * @param w_id :手表编号,可选参数
		 */
		ShoppingCart findShoppingCartByIdAndW_Id(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id);
		/**
		 * 通过用户id和手表获取购物车信息
		 * @param u_id :用户id,必选
		 * @param w_id :手表编号,可选参数
		 */
		List<ShoppingCart> findShoppingCartsByIdAndW_Id(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id);
		/**
		 * 添加购物车信息
		 * @param sc:购物车对象
		 */
		Integer addShopInfo(ShoppingCart sc);
		/**
		 * 删除购物车信息
		 * @param u_id:购物车id
		 */
		Integer deleteShopInfoById(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id);
	/**
	 * 删除购物车信息
	 * @param watch_id:手表主键id
	 */
	Integer deleteShopInfoByWId(Integer watch_id);
		/**
		 * 添加物品数量
		 * @param u_id:用户�?
		 */
		Integer addCount(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id, @Param("count") Integer count);		/**
		 * 添加物品数量
		 * @param u_id:用户�?
		 */
		Integer addCountBuy(@Param("u_id") Integer u_id, @Param("w_id") Integer w_id, @Param("count") Integer count);

	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	Integer existenceCart(@Param("u_id")Integer u_id, @Param("w_id")Integer w_id);
}