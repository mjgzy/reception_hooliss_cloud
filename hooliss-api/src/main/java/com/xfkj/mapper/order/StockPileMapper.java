package com.xfkj.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.stock.StockPile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存管理
 */
public interface StockPileMapper extends BaseMapper<StockPile> {


    /**
     * 通过商品主键更新库存
     * @param watch_id:手表id
     * @param count:需要出库的数量
     */
    Integer update(@Param("watch_id") Integer watch_id, @Param("count") int count);

    /**
     * 生成库存信息
     */
    Integer stockGante(Integer watch_id);

    /**
     * 查询库存表未存在的主表信息
     */
    List<Integer> findWatchIdByTbWatchs();
}
