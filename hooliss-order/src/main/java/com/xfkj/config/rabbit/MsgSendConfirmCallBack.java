package com.xfkj.config.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 消息发送到交换机确认机制
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    /**
     * 当消息发送到交换机（exchange）时，该方法被调用.
     * 1.如果消息没有到exchange,则 ack=false
     * 2.如果消息到达exchange,则 ack=true
     * @param correlationData:
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("MsgSendConfirmCallBack, 回调id:" + correlationData);
        if (b) {
            System.out.println("消息发送到exchange成功");
            // TODO 删除 msgId 与 Message 的关系
        } else {
            System.err.println("消息发送到exchange失败");
            // TODO 消息发送到exchange失败 ， 重新发送
        }
    }
}
