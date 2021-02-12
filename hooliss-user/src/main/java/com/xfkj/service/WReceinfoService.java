package com.xfkj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.exceptionHandling.XFException;
import java.util.HashMap;

public interface WReceinfoService  extends IService<WatchReceinfo> {
	/**
	 * 按用户id查找其下所有地址
	 * @param user_id 用户id type:按用户id查找还是地址id查找
	 */
//	IPage<WatchReceinfo> queryAddressById(
//            Integer user_id, Integer current_no,
//            Integer page_size) throws XFException;

	/**
	 * 通过主键id查询地址信息
	 * @param address_id
	 * @return
	 */
//	WatchReceinfo queryAddressById(Integer address_id) throws XFException;
	/**
	 * 添加地址信息
	 * @param addInfo
	 */
//	boolean add(WatchReceinfo addInfo) throws XFException;
	/**
	 * 修改地址
	 * @param update_info
	 */
//	boolean modify(WatchReceinfo update_info) throws XFException;
	/**
	 * 删除地址信息
	 * @param i_id:地址id
	 */
//	boolean del(Integer i_id) throws XFException;

	/**
	 * 根据条件查询用户地址
	 * @param current_no:当前页
	 * @param size:页数大小
	 * @param param:查询条件
	 * @return
	 * @throws Exception
	 */
	IPage<WatchReceinfo> getReceInfoByParam(Integer current_no,
									Integer size, HashMap<String,Object> param) throws Exception;
}
