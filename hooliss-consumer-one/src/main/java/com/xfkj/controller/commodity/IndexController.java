package com.xfkj.controller.commodity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.feign.commodity.Index_Service_Feign;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.annotations.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 首页index.html
 * @author Administrator
 *
 */
@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private Index_Service_Feign index_service_feign;

	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * 跳转到首页
	 *
	 * @return
	 */
	@PassToken    //不需要验证用户
	@RequestMapping("/doIndex.xf")
	public String forIndex(Model model) {
		ResultBody resultBody = index_service_feign.forIndex();
		log.info("成功输出>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (resultBody.getCode().equals("200")){
			Map result = (Map) resultBody.getResult();
			model.addAttribute("gradelist", result.get("gradelist"));
			//初始化男士(排行榜)
			model.addAttribute(Constants.WATCHS_MAN_NAME, result.get(Constants.WATCHS_MAN_NAME));
			//初始化最新（新品推荐）
			model.addAttribute("products", result.get(Constants.WATCHS_MAN_NAME));
			//初始化款式(时尚)
			model.addAttribute("grade", result.get(Constants.WATCHS_MAN_NAME));
		}
		return "index";
	}

	@RequestMapping("/products.xf")
	@ResponseBody
	public Object products(Integer fotindex, Integer proindex, Integer current_no, Integer page_size, Model model) {
		ResultBody resultBody = index_service_feign.products(fotindex,proindex ,current_no ,page_size );
		if (resultBody.getCode().equals("200")){
			return resultBody.getResult();
		}else{
			return resultBody.getMessage();
		}

	}






}
