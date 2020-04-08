package com.xfkj.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.xfkj.annotations.UserAttribute;
import com.xfkj.feign.order.FeignAlipayController;
import com.xfkj.pojo.order.WatchOrder;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.order.AlipayService;
import com.xfkj.tools.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 支付宝
 * @author lcc
 * @data :2018年6月4日 上午10:55:46
 */
@Slf4j
@Controller
public class AlipayController {

    @Autowired
    private FeignAlipayController feignAlipayController;

    /**
     * web 订单支付
     */
    @RequestMapping("getPagePay")
    public ModelAndView getPagePay(@UserAttribute Wuser wuser,HttpServletRequest httpServletRequest, ModelAndView modelAndView, HttpServletResponse httpServletResponse) throws Exception{
        String str_wuser= JSON.toJSONString(wuser);
        ResultBody pagePay = feignAlipayController.getPagePay(str_wuser);
        if(pagePay.getCode().equals("200")){
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write((String)pagePay.getResult());
            modelAndView.setViewName("pay");
        }else{
            modelAndView.addObject("errorMessage","未      查询到订单对象");
            modelAndView.setViewName("err");
        }
        return modelAndView;
    }
//    /**
//     * app 订单支付
//     */
//    @GetMapping("getAppPagePay")
//    public String getAppPagePay() throws Exception{
//        /** 模仿数据库，从后台调数据*/
//        String outTradeNo = "131233";
//        Integer totalAmount = 1000;
//        String subject = "天猫超市012";
//        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);
//        String json = JSONObject.toJSONString(pay);
//        System.out.println(json);
//        return json;
//    }
//
//    /**
//     * 交易查询
//     */
//    @PostMapping("aipayQuery")
//    public String alipayQuery() throws Exception {
//        /**调取支付订单号*/
//        String outTradeNo = "13123";
//
//        String query = alipayService.query(outTradeNo);
//
//        String json = JSONObject.toJSONString(query);
//
//        /*JSONObject jObject = new JSONObject();
//        jObject.get(query);*/
//        return json;
//    }
//        /**
//         * 退款
//         * @throws AlipayApiException
//         */
//        @GetMapping("alipayRefund")
//        public String alipayRefund (
//                @RequestParam("outTradeNo") String outTradeNo,
//                @RequestParam(value = "outRequestNo", required = false) String outRequestNo,
//                @RequestParam(value = "refundAmount", required = false) Integer refundAmount) throws AlipayApiException
//        {
//
//            /** 调取数据*/
//            //String outTradeNo = "15382028806591197";
//            String refundReason = "用户不想购买";
//            //refundAmount = 1;
//            //outRequestNo = "22";
//
//            String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
//
//            System.out.println(refund);
//
//            return refund;
//        }
//
//        /**
//         * 退款查询
//         * @throws AlipayApiException
//         */
//        @PostMapping("refundQuery")
//        public String refundQuery () throws AlipayApiException {
//
//            /** 调取数据*/
//            String outTradeNo = "13123";
//            String outRequestNo = "2";
//
//            String refund = alipayService.refundQuery(outTradeNo, outRequestNo);
//
//            return refund;
//
//        }
//
//        /**
//         * 交易关闭
//         * @throws AlipayApiException
//         */
//        @PostMapping("alipayclose")
//        public String alipaycolse (@RequestParam("outTradeNo")String outTradeNo) throws AlipayApiException {
//
//
//            String close = alipayService.close(outTradeNo);
//
//            return close;
//        }
}