package com.xfkj.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.commodity.WatchType;

import java.util.List;


/**
 * 档次
 * @author Administrator
 *
 */
public interface WatchTypeMapper extends BaseMapper<WatchType> {
	/**
	 * 获得所有手表档次
	 * @return
	 */
	List<WatchType> getAll();
}