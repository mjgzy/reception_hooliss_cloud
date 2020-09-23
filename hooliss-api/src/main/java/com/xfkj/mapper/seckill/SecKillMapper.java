package com.xfkj.mapper.seckill;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.seckill.Seckill;

import java.util.List;

/**
 * 秒杀商品接口
 */
public interface SecKillMapper extends BaseMapper<Seckill> {

    /**
     * 查询所有秒杀商品
     */
    List<Seckill> findSecKillAll();

    /**
     * 通过秒杀主键查询秒杀信息
     * @param s_id:秒杀主键
     */
    Seckill findSecById(Integer s_id);
}
