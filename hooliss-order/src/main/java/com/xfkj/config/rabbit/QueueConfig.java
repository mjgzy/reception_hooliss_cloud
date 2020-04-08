package com.xfkj.config.rabbit;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列配置  可以配置多个队列
 * @author zhuzhe
 * @date 2018/5/25 13:25
 * @email 1529949535@qq.com
 */
@Configuration
public class QueueConfig {

    /*对列名称*/
    static final String SECOND_KILL_QUEUE = "product_secondsKill";      //处理秒杀订单
    static final String ORDER_QUEUE = "product_order";      //处理订单
    static final String UPDATE_ORDER_QUEUE = "update_order_status";      //处理订单状态
    static final String ORDER_DEAD_LETTER_OVERTIME_QUEUE = "order_overtime_dead_queue";          //处理订单超时状态死信队列
    static final String ORDER_OVERTIME_QUEUE = "order_overtime_queue";
    static final String USER_INTEGRAL_QUEUE = "user_integral_queue";      //用户积分队列



    /**
     * 死信队列(中间队列,没有消费,仅做消息中间存储功能),配置死信交换机,此
     * 队列过期后通过此死信交换机转发到订单失败处理队列
     */
    @Bean
    public Queue orderOverTimeDeadQueue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", ExchangeConfig.ORDER_OVERTIME_DEAD_LATTER_EXCHANGE);  //队列绑定到死信交换机
//       x-dead-letter-routing-key    声明 死信路由键
        arguments.put("x-dead-letter-routing-key",RabbitMQConfig.ORDER_OVERTIME_DEAD_LATTER_KEY);
        return new Queue(ORDER_DEAD_LETTER_OVERTIME_QUEUE,true,false,false,arguments);
    }



    /**
     * 订单超时处理队列
     */
    @Bean
    public Queue overtimeOrderQueue() {
        return new Queue(ORDER_OVERTIME_QUEUE,true,false,false);
    }

    /**
     * 用户积分队列
     */
    @Bean
    public Queue userIntegralQueue() {
        return new Queue(USER_INTEGRAL_QUEUE,true,false,false);
    }


    //订单队列,生成订单
    @Bean
    public Queue orderGenerate(){
        return new Queue(ORDER_QUEUE,true,false,false);
    }

    //订单状态队列
    @Bean
    public Queue orderStatusQueue(){
        return new Queue(UPDATE_ORDER_QUEUE,true,false,false);
    }
//    @Bean
//    public Queue thirdQueue() {
//        // 配置 自动删除
//        Map<String, Object> arguments = new HashMap<>();
////        x-dead-letter-exchange    声明  死信交换机
//        arguments.put("x-dead-letter-exchange", ExchangeConfig.DEAL_EXCHANGE);  //队列绑定到死信交换机
////       x-dead-letter-routing-key    声明 死信路由键
//        arguments.put("x-dead-letter-routing-key",DLR_KEY);
//        return new Queue(UPDATE_ORDER_QUEUE,true,false,true,arguments);
//    }
}
