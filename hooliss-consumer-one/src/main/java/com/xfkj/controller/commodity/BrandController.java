package com.xfkj.controller.commodity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.feign.commodity.Brand_Service_Feign;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 品牌馆控制
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private Brand_Service_Feign brand_service_feign;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 跳转到品牌馆选择页面
     */
    @RequestMapping("/doBrand.xf")
    public ModelAndView doBrandHtml(ModelAndView modelAndView) {
        ResultBody resultBody = brand_service_feign.doBrandHtml();
        System.err.println(resultBody);
        if (resultBody.getCode().equals("200")){
            modelAndView.addObject(Constants.WATCH_BRAND_NAME, resultBody.getResult());  //保存热销手表
            modelAndView.setViewName("brand");
        }else if (resultBody.getCode().equals("500")){
            modelAndView.addObject(Constants.ERROR_MESSAGE, resultBody.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping("/doBrandbyid.xf")
    @ResponseBody
    public Object doBrandbyid(){
        ResultBody resultBody = brand_service_feign.doBrandbyid();
        if (resultBody.getCode().equals("200")){
            return resultBody.getResult();
        }
        return null;
    }

    /**
     * 跳转到品牌馆详情页面
     */
    @RequestMapping("/doShop.xf/{brand_id}")
    public ModelAndView doShopHtml(
            @PathVariable("brand_id") String str_series_brand_id,
            ModelAndView modelAndView) {
        ResultBody resultBody = brand_service_feign.doShopHtml(str_series_brand_id);
        if (resultBody.getCode().equals("200")){
            Map<String,Object> result = objectMapper.convertValue(resultBody.getResult(),new TypeReference<HashMap<String,Object>>(){});
            modelAndView.addObject(Constants.WATCH_BRAND_NAME,result.get(Constants.WATCH_BRAND_NAME));
            modelAndView.addObject("Number",result.get("Number"));
            //获取该品牌下所有系列
            modelAndView.addObject(Constants.WATCH_SERIES_NAME, result.get(Constants.WATCH_SERIES_NAME));
            //获取该品牌下所有手表
            modelAndView.addObject(Constants.WATCHS_NAME,result.get(Constants.WATCHS_NAME));
            //获取上新推荐手表
            modelAndView.addObject(Constants.HOT_WATCHS_NAME, result.get(Constants.HOT_WATCHS_NAME));  //保存热销手表
            modelAndView.setViewName("shop");
        }else if (resultBody.getCode().equals("500")){
            modelAndView.addObject(Constants.ERROR_MESSAGE, resultBody.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping("/click.xf/{brand_id}/{grade_id}/{watch_name}/{watch_priceMin}/{watch_priceMax}/{current_no}/{page_size}/{condition}")
    @ResponseBody
    public Object ajax4(
            @PathVariable("brand_id") String str_series_brand_id,
            @PathVariable("grade_id") String str_grade_id,
            @PathVariable("watch_name") String str_watch_name,
            @PathVariable("watch_priceMin") String str_watch_priceMin,
            @PathVariable("watch_priceMax") String str_watch_priceMax,
            @PathVariable("current_no") String str_current_no,
            @PathVariable("page_size") String str_page_size,
            @PathVariable("condition") String str_condition
    ){

        ResultBody resultBody = brand_service_feign.ajax4(str_series_brand_id, str_grade_id, str_watch_name,
                str_watch_priceMin, str_watch_priceMax, str_current_no, str_page_size, str_condition);
        if (resultBody.getCode().equals("200")){
            return resultBody.getResult();
        }
        return ResultBody.error(resultBody.getCode(),resultBody.getMessage());
    }



}
