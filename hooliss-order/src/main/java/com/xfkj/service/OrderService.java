package com.xfkj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.order.WatchOrder;
import com.xfkj.exceptionHandling.XFException;

import java.util.HashMap;
import java.util.List;

/**
 * 订单服务
 */
public interface OrderService extends IService<WatchOrder> {

    /**
     * 通过用户id或者order_id查询订单
     * @param u_id:用户id:必选参数
     * @param date_type:日期筛选,0表示全部,1表示近一个月,2表示近三个月,3表示近一年,可选参数
     * @param order_status_id:订单状态表主键id:筛选订单状态,全部状态则不要此条件,可选参数
     * @param order_id:订单id,模糊查询,可选参数
     */
//    PageInfo<WatchOrder> findWatchByCondition(
//            Integer u_id, Integer date_type,
//            Integer order_status_id,
//            String order_id, Integer current_no, Integer page_size) throws XFException;

    /**
     * 通过订单id查询订单
     * @param order_id
     * @return
     */
    WatchOrder  findById(String order_id) throws XFException;

    /**
     * 生成订单,此处需要先减掉库存,再添加订单,同一个事务,任何一个操作失败则回滚
     * @param watchOrder:订单对象
     */
    boolean generateOrder(WatchOrder watchOrder) throws XFException;

    /**
     * 根据订单主键更改订单状态
     * @param order_id:订单主键id
     * @param status_id:状态id
     */
    boolean updateOrderStatus(String order_id, Integer status_id) throws XFException;


    /**
     * 生成库存信息
     */
    boolean stockGante(Integer watch_id) throws XFException;

    /**
     * 查询库存表未存在的主表信息
     */
    List<Integer> findWatchIdByTbWatchs() throws XFException;

    /**
     * 通过订单号删除订单
     * @param order_id:订单号
     */
    Boolean delOrderById(String order_id);

    /**
     * 查询订单
     * @param current_no:当前页
     * @param size:页数大小
     * @param param:参数
     */
    IPage<WatchOrder> getOrderByParam(Integer current_no,
                                    Integer size, HashMap<String,Object> param) throws XFException;
}
