package com.xfkj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.order.WatchOrder;
import com.xfkj.entity.seckill.Seckill;
import com.xfkj.exceptionHandling.XFException;
import java.util.HashMap;

/**
 * 秒杀业务层
 */
public interface SecKillService extends IService<Seckill> {

    /**
     * 生成订单,此处需要先减掉库存,再添加订单,同一个事务,任何一个操作失败则回滚
     * @param watch_id:手表主键id
     */
    boolean generateOrder(Integer watch_id) throws XFException;

    /**
     * 查询所有秒杀商品
     */
    IPage<Seckill> findSecKillAll(Integer current_no, Integer page_size) throws XFException;

    /**
     * 通过秒杀主键查询秒杀信息
     * @param s_id:商品主键
     */
    Seckill findSecById(Integer s_id) throws  XFException;

    /**
     * 查询订单
     * @param current_no:当前页
     * @param size:页数大小
     * @param param:参数
     */
    IPage<WatchOrder> getSeckillByParam(Integer current_no,
                                      Integer size, HashMap<String,Object> param) throws XFException;
}
