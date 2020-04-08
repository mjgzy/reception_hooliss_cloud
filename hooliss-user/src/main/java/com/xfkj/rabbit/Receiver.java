package com.xfkj.rabbit;

import com.xfkj.pojo.order.OrderReserve;
import com.xfkj.pojo.order.WatchOrder;
import com.xfkj.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

    @Autowired
    private UserService userService;
    static final String USER_INTEGRAL_QUEUE = "user_integral_queue";    //用户积分队列

    @RabbitListener(queues = Receiver.USER_INTEGRAL_QUEUE)
    public void setUserIntegralQueue(OrderReserve orderReserve){
        log.info("接收到用户积分处理请求,开始处理>>>>>>>>>>>>>>");
        Integer integer = userService.addUserIntegral(orderReserve.getUser_id(), orderReserve.getUser_integral());
        log.info("用户积分处理结果:"+(integer>0));
    }
}
