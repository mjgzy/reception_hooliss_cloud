package com.xfkj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.xfkj.entity.order.WatchOrder;
import com.xfkj.entity.user.Wuser;
import com.xfkj.enums.CommonEnum;
import com.xfkj.service.AlipayService;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 支付宝
 * @author lcc
 * @data :2018年6月4日 上午10:55:46
 */
@Slf4j
@RestController
@RequestMapping("alipay")
public class AlipayController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;

    /**
     * web 订单支付
     */
    @RequestMapping("getPagePay")
    public ResultBody<?> getPagePay(@RequestParam("wuser") String str_user) throws Exception {
        Wuser wuser = JSON.parseObject(str_user, Wuser.class);
        String s = DigestUtils.md5DigestAsHex((wuser.getUId() + wuser.getUPhone()).getBytes());
        WatchOrder watchOrder= (WatchOrder) redisUtils.get(s);
        if(watchOrder==null){
            return new ResultBody<>(500,"未查询到订单对象");
        }
        log.info("查找订单>>>>>>>>>>>>>");
        String pay = alipayService.webPagePay(watchOrder.getOrderId(), watchOrder.getOrderPrice().doubleValue(), watchOrder.getLt().getWatchName());
        return new ResultBody<>(CommonEnum.SUCCESS,pay);
    }
    /**
     * app 订单支付
     */
    @GetMapping("getAppPagePay")
    public String getAppPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "131233";
        Integer totalAmount = 1000;
        String subject = "天猫超市012";
        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);
        String json = JSONObject.toJSONString(pay);
        System.out.println(json);
        return json;
    }

    /**
     * 交易查询
     */
    @PostMapping("aipayQuery")
    public String alipayQuery() throws Exception {
        /**调取支付订单号*/
        String outTradeNo = "13123";

        String query = alipayService.query(outTradeNo);

        String json = JSONObject.toJSONString(query);

        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
        return json;
    }
        /**
         * 退款
         * @throws AlipayApiException
         */
        @GetMapping("alipayRefund")
        public String alipayRefund (
                @RequestParam("outTradeNo") String outTradeNo,
                @RequestParam(value = "outRequestNo", required = false) String outRequestNo,
                @RequestParam(value = "refundAmount", required = false) Integer refundAmount) throws AlipayApiException
        {

            /** 调取数据*/
            //String outTradeNo = "15382028806591197";
            String refundReason = "用户不想购买";
            //refundAmount = 1;
            //outRequestNo = "22";
            String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
            return refund;
        }

        /**
         * 退款查询
         * @throws AlipayApiException
         */
        @PostMapping("refundQuery")
        public String refundQuery () throws AlipayApiException {

            /** 调取数据*/
            String outTradeNo = "13123";
            String outRequestNo = "2";

            String refund = alipayService.refundQuery(outTradeNo, outRequestNo);

            return refund;

        }

        /**
         * 交易关闭
         * @throws AlipayApiException
         */
        @PostMapping("alipayclose")
        public String alipaycolse (@RequestParam("outTradeNo")String outTradeNo) throws AlipayApiException {


            String close = alipayService.close(outTradeNo);

            return close;
        }
}