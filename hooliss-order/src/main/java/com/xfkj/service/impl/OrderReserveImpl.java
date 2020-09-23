package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.config.rabbit.MqSender;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.order.OrderReserveMapper;
import com.xfkj.mapper.order.StockPileMapper;
import com.xfkj.entity.order.OrderReserve;
import com.xfkj.service.OrderReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class OrderReserveImpl extends ServiceImpl<OrderReserveMapper,OrderReserve> implements OrderReserveService {


    @Autowired
    private MqSender mqSender;
    @Resource
    private OrderReserveMapper  orderReserveMapper;

    @Resource
    private StockPileMapper stockPileMapper;

    @Override
    public Boolean addReservce(OrderReserve orderReserve) throws XFException {
        if (orderReserve==null){
            throw new XFException(500,"空指针异常,orderReserve is null");
        }
        Integer integer = orderReserveMapper.addReservce(orderReserve);
        return integer>0;
    }

    @Override
    public Boolean delReservce(String order_id) throws XFException {
        if (order_id==null||order_id.equals("")){
            throw new XFException(500, "参数错误,order_id is null");
        }
        return orderReserveMapper.delReservce(order_id)>0;
    }

    @Override
    public Boolean OrderCommit(OrderReserve orderReserve) throws XFException{
        if (orderReserve==null){
            throw new XFException(400, "空指针异常,orderReserve is null");
        }
        //更新库存
        Integer update = stockPileMapper.update(orderReserve.getWatchId(), orderReserve.getSpikeNum());
        if (update!=1){
            throw new XFException(500,"更新库存失败!" );
        }
        //更新用户积分
        mqSender.sendDirectUserIntegral(orderReserve);
        return true;
    }
}
