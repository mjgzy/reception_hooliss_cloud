package com.xfkj.feign.order;

import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hooliss-order-provider",contextId = "alipay")
@RequestMapping("alipay")
public interface FeignAlipayController {
    /**
     * web 订单支付
     */
    @RequestMapping("getPagePay")
    ResultBody getPagePay(@RequestParam("wuser") String str_user);
}
