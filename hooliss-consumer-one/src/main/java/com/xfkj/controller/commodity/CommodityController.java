package com.xfkj.controller.commodity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.feign.commodity.Commodity_Service_Feign;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.annotations.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 商品控制,增加商品,删除商品,加入购物车
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

	@Autowired
	private Commodity_Service_Feign commodity_service_feign;

	@Autowired
	private ObjectMapper objectMapper;
    /**
     * 去购物车页面
     * @param u_name
     * @return
     */
	@RequestMapping("/doShopcart.xf")
	public String doShopCart(String u_name) {


		return "car";
	}

    /**
     *
     * @param str_key_word:顶部搜索框
     * @param str_grade_id:适用人群
     * @param str_current_no:当前页
     * @param str_page_size:页数大小
     * @param str_type_id:价格区间
     * @param modelAndView:视图数据
     */
    @UserLoginToken
	@RequestMapping("/doOs.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
	public ModelAndView doOsHtml(
			@PathVariable("key_word")String str_key_word,
			@PathVariable("grade_id") String str_grade_id,
			@PathVariable("current_no")String str_current_no,
			@PathVariable("page_size")String str_page_size,
			@PathVariable("type_id")String str_type_id,
			@PathVariable("brand_id")String str_brand_id,
            @PathVariable("condition")String str_condition,
            ModelAndView modelAndView){
		ResultBody resultBody = commodity_service_feign.doOsHtml(str_key_word, str_grade_id, str_current_no
				, str_page_size, str_type_id, str_brand_id, str_condition);

		if (resultBody.getCode().equals("200")){
			Map<String,String> result =objectMapper.convertValue(resultBody.getResult(),new TypeReference<Map<String,String>>(){});

           modelAndView.addObject("brand_id", result.get("brand_id"));
			modelAndView.addObject("conditions",result.get("conditions") );		//保存条件筛选
			modelAndView.addObject(Constants.WATCHS_NAME, result.get(Constants.WATCHS_NAME));
			//		加载所有品牌信息
			modelAndView.addObject(Constants.WATCH_BRAND_NAME, result.get(Constants.WATCH_BRAND_NAME));
			modelAndView.setViewName("os");
			return modelAndView;
		}else if (resultBody.getCode().equals("500")){
			modelAndView.addObject(Constants.ERROR_MESSAGE,resultBody.getMessage() );
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	@RequestMapping("doPaixu.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
	@ResponseBody
	public ResultBody doPaixu(
			@PathVariable("key_word")String str_key_word,
			@PathVariable("grade_id") String str_grade_id,
			@PathVariable("current_no")String str_current_no,
			@PathVariable("page_size")String str_page_size,
			@PathVariable("type_id")String str_type_id,
			@PathVariable("brand_id")String str_brand_id,
			@PathVariable("condition")String str_condition){
		ResultBody resultBody = commodity_service_feign.doPaixu(str_key_word, str_grade_id, str_current_no,
				str_page_size, str_type_id, str_brand_id, str_condition);
		if (resultBody.getCode().equals("200")){
			return ResultBody.success(resultBody.getResult());
		}else{
			return ResultBody.error(resultBody.getCode(), resultBody.getMessage());
		}
	}
}
