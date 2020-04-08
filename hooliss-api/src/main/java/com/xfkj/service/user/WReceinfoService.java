package com.xfkj.service.user;

import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.user.WatchReceinfo;

public interface WReceinfoService {
	/**
	 * 按用户id查找其下所有地址
	 * @param user_id 用户id type:按用户id查找还是地址id查找
	 */
	PageInfo<WatchReceinfo> queryAddressById(
			Integer user_id, Integer current_no,
			Integer page_size) throws XFException;

	/**
	 * 通过主键id查询地址信息
	 * @param address_id
	 * @return
	 */
	WatchReceinfo queryAddressById(Integer address_id) throws XFException;
	/**
	 * 添加地址信息
	 * @param addInfo
	 */
	boolean add(WatchReceinfo addInfo) throws XFException;
	/**
	 * 修改地址
	 * @param update_info
	 */
	boolean modify(WatchReceinfo update_info) throws XFException;
	/**
	 * 删除地址信息
	 * @param i_id:地址id
	 */
	boolean del(Integer i_id) throws XFException;
}
