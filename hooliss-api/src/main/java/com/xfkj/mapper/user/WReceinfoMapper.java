package com.xfkj.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.user.WatchReceinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户地址,对应cart.html
 * @author Administrator
 *
 */
public interface WReceinfoMapper extends BaseMapper<WatchReceinfo> {
	/**
	 * 按用户id查找其下所有地址
	 * @param user_id 用户id type:按用户id查找还是地址id查找
	 * @return
	 */
	List<WatchReceinfo> findAddressById(@Param(("user_id")) Integer user_id);

	/**
	 * 通过主键id查询地址信息
	 * @param address_id
	 * @return
	 */
	WatchReceinfo queryAddressById(Integer address_id);
	/**
	 * 添加地址信息
	 * @param add_info
	 * @return
	 */
	Integer add(WatchReceinfo add_info);
	/**
	 * 修改地址
	 * @param updateInfo
	 * @return
	 */
	Integer update(WatchReceinfo updateInfo);
	/**
	 * 删除地址信息
	 * @param i_id
	 * @return
	 */
	Integer del(@Param("i_id") Integer i_id);
}