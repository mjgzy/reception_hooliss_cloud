package com.xfkj.mapper.commodity;

import com.xfkj.pojo.commodity.WatchType;

import java.util.List;


/**
 * 档次
 * @author Administrator
 *
 */
public interface WatchTypeMapper {
	/**
	 * 获得所有手表档次
	 * @return
	 */
	List<WatchType> getAll();
}