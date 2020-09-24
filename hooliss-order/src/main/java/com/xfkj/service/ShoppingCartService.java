package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.order.ShoppingCart;
import com.xfkj.exceptionHandling.XFException;

import java.util.List;


/**
 * 购物车业务层
 * @author Administrator
 *
 */

public interface ShoppingCartService extends IService<ShoppingCart> {

	/**
	 * 通过用户id获取购物车信息
	 * @param u_id :用户id
	 * @param w_id :手表编号
	 */
	ShoppingCart queryShoppingCartByIdAndW_Id(Integer u_id, Integer w_id) throws XFException;
	/**
	 * 通过用户id获取购物车信息
	 * @param u_id :用户id
	 * @param w_id :手表编号
	 */
	List<ShoppingCart> queryShoppingCartsByIdAndW_Id(Integer u_id, Integer w_id) throws XFException;
	/**
	 * 添加购物车信息
	 * @param sc:购物车对象
	 */
	Boolean addShopInfo(ShoppingCart sc) throws XFException;
	/**
	 * 删除购物车信息
	 * @param u_id:用户id
	 */
	Boolean deleteShopInfoById(Integer u_id, Integer w_id) throws XFException;
	/**
	 * 添加物品数量
	 * @param u_id:用户�?
	 */
	Boolean addCount(Integer u_id, Integer w_id, Integer count) throws XFException;

	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	Boolean existenceCart(Integer u_id, Integer w_id);
	
	
}
