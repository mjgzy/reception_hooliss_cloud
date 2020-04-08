package com.xfkj.config.rabbit;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    //订单交换机
    public final static String ORDER_EXCHANGE = "order_exchange";

    //订单超时交换机
    public final static String ORDER_OVERTIME_EXCHANGE = "order_overtime_exchange";

    //订单超时死信交换机
    public final static String ORDER_OVERTIME_DEAD_LATTER_EXCHANGE = "order_status_dead_exchange";

    //用户操作交换机
    public final static String USER_EXCHANGE = "user_change";
    /**
     *   1.订单交换机,处理订单以及订单状态
     *   2.durable="true" 持久化交换机， rabbitmq重启的时候不需要创建新的交换机
     *   3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     *     fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     *     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     */
    @Bean
    public DirectExchange orderExchange(){
        DirectExchange directExchange = new DirectExchange(ORDER_EXCHANGE,true,false);
        return directExchange;
    }

    /**
     * 用户交换机
     */
    @Bean
    public DirectExchange userIntegralExchange(){
        DirectExchange dc = new DirectExchange(USER_EXCHANGE,true,false);
        return dc;
    }

    /**
     * 订单超时真正处理交换机
     */
    @Bean
    public DirectExchange orderOverTimeEXchange(){
        DirectExchange dc = new DirectExchange(ORDER_OVERTIME_EXCHANGE,true,false);
        return dc;
    }
    /**
     * 定义订单超时死信交换机,此交换机用来转发队列到死信消费队列,进行订单超时处理订单操作
     */
    @Bean
    public DirectExchange orderOverTimeDeadLetterExchange() {
        DirectExchange directExchange = new DirectExchange(ORDER_OVERTIME_DEAD_LATTER_EXCHANGE,true,false);
        return directExchange;
    }
}
