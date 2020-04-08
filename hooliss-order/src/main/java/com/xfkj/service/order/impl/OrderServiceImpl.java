package com.xfkj.service.order.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.order.StockPileMapper;
import com.xfkj.mapper.order.WatchOrderMapper;
import com.xfkj.pojo.order.OrderStatus;
import com.xfkj.pojo.order.WatchOrder;
import com.xfkj.service.order.OrderService;
import com.xfkj.utils.DateFormatUtil;
import com.xfkj.utils.OrderUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Transactional(propagation=Propagation.REQUIRED)	//开启事务
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private WatchOrderMapper watchOrderMapper;


    @Resource
    private StockPileMapper stockPileMapper;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)  //查询不需要事物
    public PageInfo<WatchOrder> findWatchByCondition(
            Integer u_id, Integer date_type, Integer order_status_id, String order_id
            ,Integer current_no,Integer page_size) {
        if(u_id==null||u_id==0){
            throw new XFException("400","用户id不得为空!");
        }
        int pageNum=(current_no/10)+1;
        System.err.println("pageNum:"+pageNum);
        System.err.println("current_no:"+current_no);
        PageHelper.startPage(pageNum,page_size);
        List<WatchOrder> ww = watchOrderMapper.findWatchByCondition(u_id,date_type,order_status_id,order_id);
        return new PageInfo<WatchOrder>(ww);
    }

    @Override
    public boolean generateOrder(WatchOrder watchOrder) throws XFException {
        if(ObjectUtils.isEmpty(watchOrder)){
            throw new XFException("400",watchOrder+" is null" );
        }
        String orderIdByUUId = OrderUtils.getOrderIdByUUId();		//生成订单号
        watchOrder.setOrder_id(orderIdByUUId);		//设置订单号
        watchOrder.setOrder_status(new OrderStatus(1));		//设置为待付款状态
        watchOrder.setOrder_date(DateFormatUtil.dateToString(new Date()));  //设置订单时间
        watchOrder.setOrder_type("ordinary");       //设置订单类型
        System.err.println("来自generateOrder,订单对象:"+watchOrder);
        if(watchOrderMapper.generateOrder(watchOrder)>0){
            //减少库存
            System.err.println("订单对象:"+watchOrder);
           int result=  stockPileMapper.update(watchOrder.getWatch_id(), watchOrder.getWatch_count());
           if(result!=1){
               throw new XFException("500","订单生成异常!");
           }
            return true;
        }
        return false;
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)  //查询不需要事物
    @Override
    public WatchOrder  findById(String order_id) {
        if(StringUtils.isEmpty(order_id)){
            throw new XFException("400","order_id is null!");
        }
        return watchOrderMapper.findById(order_id);
    }

    @Override
    public boolean updateOrderStatus(String order_id, Integer status_id) throws XFException {
        if (order_id!=null&&!order_id.equals("")){
            Integer integer = watchOrderMapper.updateOrderStatus(order_id, status_id);
            return integer==1;
        }else{
            throw new XFException("500","无法修改订单状态!" );
        }
    }

    @Override
    public boolean stockGante(Integer watch_id) {
        return stockPileMapper.stockGante(watch_id)==0;
    }

    @Override
    public List<Integer> findWatchIdByTbWatchs() {
        return stockPileMapper.findWatchIdByTbWatchs();
    }

    @Override
    public Boolean delOrderById(String order_id) {
        return watchOrderMapper.delOrderById(order_id);
    }
}
