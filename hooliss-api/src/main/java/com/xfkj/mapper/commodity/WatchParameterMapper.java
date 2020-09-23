package com.xfkj.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.commodity.WatchParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 手表参数信息,index.html
 * @author Administrator
 *
 */
public interface WatchParameterMapper extends BaseMapper<WatchParameter> {

    /**
     * 通过手表id查询手表参数
     * @param parameter_id
     * @return
     */
   WatchParameter findWatchParameter(@Param("watchId") Integer parameter_id);

    /**
     * 通过手表id查询手表参数
     */
    List<WatchParameter> findWatchParameterAll();

    Integer updateWatchId(@Param("parameterId") Integer parameter_id, @Param("watchId") Integer watch_id);
}