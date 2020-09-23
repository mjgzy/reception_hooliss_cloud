package com.xfkj.config.rabbit;

import com.xfkj.entity.order.OrderReserve;
import com.xfkj.entity.order.WatchOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息入队
 */
@Component
public class MqSender {

    private final static Logger log = LoggerFactory.getLogger(MqSender.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDirectQueueOrder(WatchOrder order){
        log.info(">>>>>>>>>>>>>>>>>>订单处理请求已发送");
        try {
            rabbitTemplate.convertAndSend( ExchangeConfig.ORDER_EXCHANGE,RabbitMQConfig.ORDER_KEY,order);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }
    public void sendDirectOrderOverTime(String order){
        log.info(">>>>>>>>>>>>>>>>>>>>>>订单超时处理请求已发送");
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
//            设置编码
            messageProperties.setContentEncoding("utf-8");
//            设置队列过期时间
            messageProperties.setExpiration("1800000");
            return message;
        };
        try {
            rabbitTemplate.convertAndSend(ExchangeConfig.ORDER_OVERTIME_EXCHANGE,RabbitMQConfig.
                    ORDER_OVERTIME_BASIC_KEY,(Object)order,messagePostProcessor );
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }
    public void sendDirectOrderStatus(String order){
        log.info(">>>>>>>>>>>>>>>>>>订单状态处理请求已发送");
        try {
            rabbitTemplate.convertAndSend(ExchangeConfig.ORDER_EXCHANGE,
                    RabbitMQConfig.ORDER_STATUS_KEY,(Object)order);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }

    public void sendDirectUserIntegral(OrderReserve orderReserve){
        log.info(">>>>>>>>>>>>>>>>>>用户积分处理请求已发送");
        rabbitTemplate.convertAndSend(ExchangeConfig.USER_EXCHANGE, RabbitMQConfig.USER_INTEGRAL_KEY,
                orderReserve );
    }
}
