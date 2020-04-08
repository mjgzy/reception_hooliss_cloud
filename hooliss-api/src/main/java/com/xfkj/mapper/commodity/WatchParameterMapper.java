package com.xfkj.mapper.commodity;

import com.github.pagehelper.PageInfo;
import com.xfkj.pojo.commodity.WatchParameter;
import org.apache.ibatis.annotations.Param;

/**
 * 手表参数信息,index.html
 * @author Administrator
 *
 */
public interface WatchParameterMapper {

    /**
     * 通过手表id查询手表参数
     * @param parameter_id
     * @return
     */
   WatchParameter findWatchParameter(@Param("watch_id") Integer parameter_id);

    /**
     * 通过手表id查询手表参数
     */
    PageInfo findWatchParameterAll();
    Integer updateWatchId(@Param("parameter_id") Integer parameter_id, @Param("watch_id") Integer watch_id);
}