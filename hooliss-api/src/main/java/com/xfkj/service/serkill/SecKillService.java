package com.xfkj.service.serkill;

import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.seckill.Seckill;

/**
 * 秒杀业务层
 */
public interface SecKillService {

    /**
     * 生成订单,此处需要先减掉库存,再添加订单,同一个事务,任何一个操作失败则回滚
     * @param watch_id:手表主键id
     */
    boolean generateOrder(Integer watch_id) throws XFException;

    /**
     * 查询所有秒杀商品
     */
    PageInfo<Seckill> findSecKillAll(Integer current_no, Integer page_size) throws XFException;

    /**
     * 通过秒杀主键查询秒杀信息
     * @param s_id:商品主键
     */
    Seckill findSecById(Integer s_id) throws  XFException;
}
