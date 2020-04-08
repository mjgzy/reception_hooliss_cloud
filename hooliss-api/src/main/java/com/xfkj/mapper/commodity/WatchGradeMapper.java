package com.xfkj.mapper.commodity;

import com.xfkj.pojo.commodity.WatchGrade;

import java.util.List;


/**
 * 手表分类(男,女,情侣)
 * @author Administrator
 *
 */
public interface WatchGradeMapper {
	/**
	 * 获得所有分类数据
	 * @return 
	 */
	List<WatchGrade> getAll();
}