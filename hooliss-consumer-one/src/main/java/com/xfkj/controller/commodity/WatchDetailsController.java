package com.xfkj.controller.commodity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.feign.commodity.WatchDetails_Service_Feign;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 手表详情页控制
 */
@Controller
@RequestMapping("/watch")
public class WatchDetailsController {


    @Autowired
    private WatchDetails_Service_Feign watchDetails_service_feign;
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 去手表详情页面
     * @return
     */
    @RequestMapping("/doWatch.xf/{w_id}")
    public ModelAndView doWatchHtml(@PathVariable("w_id") String str_w_id, ModelAndView modelAndView){
        ResultBody resultBody = watchDetails_service_feign.doWatchHtml(str_w_id);
        System.err.println(resultBody);
        if (resultBody.getCode().equals("200")){
            Map<String,Object> result = objectMapper.convertValue(resultBody.getResult(),new TypeReference<Map<String,Object>>(){});
            modelAndView.addObject("styles", result.get("styles"));
            modelAndView.addObject("watch",  result.get("watch"));
//                获取手表品牌
            modelAndView.addObject("brand",  result.get("brand"));
            //                获取手表图片
            modelAndView.addObject("watch_images", result.get("watch_images") );
//                获取手表参数
            modelAndView.addObject("w_parameter",  result.get("w_parameter"));
//                获取手表系列
            //获取热销数据
            modelAndView.addObject(Constants.SELL_WELL_TBWATCHS_NAME, result.get(Constants.SELL_WELL_TBWATCHS_NAME));
            modelAndView.setViewName("xiangxi");
        }else {
            modelAndView.addObject(Constants.ERROR_MESSAGE, resultBody.getMessage());
            modelAndView.setViewName("error");
        }
                return modelAndView;
    }

    /**
     * 去购物车页面
     * @return
     */
    @RequestMapping("/doCart.xf")
    public String doCartHtml(){

        return "tijiao";
    }

}
