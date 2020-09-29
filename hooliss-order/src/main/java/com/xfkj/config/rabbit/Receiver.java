package com.xfkj.config.rabbit;

import com.rabbitmq.client.Channel;
import com.xfkj.enums.CommonEnum;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.entity.order.OrderReserve;
import com.xfkj.entity.order.WatchOrder;
import com.xfkj.service.OrderReserveService;
import com.xfkj.service.OrderService;
import com.xfkj.service.ShoppingCartService;
import com.xfkj.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class Receiver {

    private final static Logger log = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MqSender mqSender;
    int i;
    @Autowired
    private OrderReserveService orderReserveService;

    @Resource
    private OrderService orderService;

//    @RabbitListener(queues = QueueConfig.QUEUE)
    public void receiverDirectQueue(String watch_id, Channel channel, Message message) throws IOException {
        log.info(">>>>>>>>>>>>>>>>>接收到秒杀请求，商品ID为："+watch_id+"检查Redis中库存是否为0");
        try {
            //执行递减,递减因子为1
            long num = redisUtils.decr(watch_id,1 );
            if(num < 0) {
                /**
                 * 此处不能判断等于0，因为当商品库存为1时，Redis执行递减返回为0
                 * 如果判断为0商品最后不能卖完也就是当库存为1时此处就抛异常了
                 */
                throw new XFException(CommonEnum.WATCH_INVENTORY_IS_NULL);
            }
            log.info("接收时>>>>>>>>>>>"+i++);
            Integer w_id = Integer.valueOf(watch_id);

            //根据手表对象同步数据到MySQL
//            if(!orderService.generateOrder(watchOrder)) {
//                throw new XFException("redis同步到商品表异常！");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            //消息必答,取消消息确认,未收到
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            throw new MessageConversionException("消息消费失败，移出消息队列，不再试错");
        }
    }
    @RabbitListener(queues = QueueConfig.ORDER_QUEUE)
    public void receiverDirectOrderQueue(WatchOrder watchOrder, Channel channel, Message message) throws  IOException{
        log.info(">>>>>>>>>>>>>>>>>接收到订单请求，处理订单");
            try {
////        根据手表对象同步数据到MySQL
                boolean b1 = orderService.generateOrder(watchOrder);
                boolean b = b1;
            if(!b) {
                throw new XFException("redis同步到商品表异常！");
            }else{
                Map<String,Object> mis = new HashMap<>();
                mis.put(watchOrder.getOrderId(), watchOrder.getId());
                boolean order_ids = redisUtils.hmset("order_ids",mis ,60000);//将订单主键存入redis
                log.info("购物车对象:"+watchOrder);
                Boolean aBoolean = shoppingCartService.deleteShopInfoById(watchOrder.getOUid(), watchOrder.getWatchId());
                log.info("订单生成成功!删除购物车信息:"+aBoolean);
                log.info("订单主键存入redis:"+order_ids);
                //增加用户积分,减少库存等资源预留
                OrderReserve orderReserve =new OrderReserve() ;
                orderReserve.setOrderId(watchOrder.getOrderId());
                orderReserve.setSpikeNum(watchOrder.getWatchCount());
                orderReserve.setUserIntegral(watchOrder.getOrderPrice().divide(BigDecimal.valueOf(10.0)).intValue());
                redisUtils.set(orderReserve.getOrderId(), orderReserve);//将订单资源预减放入redis
                log.info(">>>>>>>>>>>>>>>>>>>>订单资源预减成功");
                mqSender.sendDirectOrderOverTime(watchOrder.getOrderId());     //发送订单超时处理消息
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("队列消费,receiverDirectOrderQueue遇到异常>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e.getClass());
            //遇到异常,重新发送消息
                boolean b1=true;
                if (e.getClass()== DataIntegrityViolationException.class){
                    b1=false;
                }
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,b1 );
        }
    }

    /**
     * 根据订单主键id修改订单状态为已支付
     */
    @RabbitListener(queues = QueueConfig.UPDATE_ORDER_QUEUE)
    public void receiverDirectOrderStatusQueue(String order_id){
        boolean b = orderService.updateOrderStatus(order_id,2);
        if (b){
            redisUtils.hdel("order_ids",order_id);  //删除hash中指定订单号
        }
        log.info("订单状态修改>>>>>>>>>>>>>>>>>>>>>>>>"+b);
        log.info("开始更新库存,增加用户积分>>>>>>>>>>>>");
        //从缓存拿数据
        OrderReserve or = (OrderReserve)redisUtils.get(order_id);
        Boolean aBoolean = orderReserveService.OrderCommit(or);
        redisUtils.del(order_id);
        log.info("更新库存,积分结果:"+aBoolean);
    }

    /**
     * 处理订单失败状态
     * @param order_id:订单id
     */
    @RabbitListener(queues = QueueConfig.ORDER_OVERTIME_QUEUE)
    public void rectUpdateStatusQueue(String order_id, Channel channel, Message message){
        try {
            log.info("订单超时死信队列接收消息"+order_id);
            boolean b = orderService.updateOrderStatus(order_id, 6);
            log.info("订单状态修改为失败>>>>>>"+b);
            log.info(">>>>>>>>>>>>>>>资源回滚开始");
            Boolean aBoolean = orderReserveService.delReservce(order_id);
            log.info(">>>>>>>>>>>>>>>资源回滚"+aBoolean);
            //删除缓存
            redisUtils.del(order_id);
        } catch (XFException e) {
            log.info("rectUpdateStatusQueue:发现异常");
            e.printStackTrace();
            try {
                //消息消费失败,防止重复消费
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
