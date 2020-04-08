package com.xfkj.controller;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.commodity.WatchGrade;
import com.xfkj.service.commodity.TbWatchsService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 首页index.html
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/index-provider")
public class IndexController {

	@Autowired
	private TbWatchsService tbWatchsService;

	/**
	 * 跳转到首页
	 *
	 */
	@RequestMapping("/doIndex.xf")
	public ResultBody forIndex() {
		String watch_name = null;
		Map result = new HashMap();
		//初始化分类
		try {
			List<WatchGrade> gradelist = tbWatchsService.queryWatchGradeAll();
			result.put("gradelist", gradelist);
			//初始化男士(排行榜)
			PageInfo<TbWatchs> dd = tbWatchsService.queryWatchByVolume(1, 10, 1, 10);
			result.put(Constants.WATCHS_MAN_NAME,dd);
			//初始化最新（新品推荐）
			PageInfo<TbWatchs> products = tbWatchsService.queryWatchByinfo(1,0,0, watch_name, 0,0,4,1,10);
			System.err.println(products.getPages());
			result.put("products", products);
			//初始化款式(时尚)
			PageInfo<TbWatchs> grade = tbWatchsService.findWatchByGradeAndType(1, 8, 1, 10);
			result.put("grade", grade);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBody.error("500",e.getMessage());
		}
		return ResultBody.success(result);
	}

	@RequestMapping("/products.xf")
	public ResultBody products(@RequestParam("currentno")Integer fotindex,
							   @RequestParam("proindex")Integer proindex,
							   @RequestParam("current_no")Integer current_no,
							   @RequestParam("page_size")Integer page_size) {
		PageInfo<TbWatchs> pro = new PageInfo<>();
		proindex+=1;	//加1同步数据库
		try {
			if (fotindex == 1) {
				//排行榜
				pro = tbWatchsService.queryWatchByVolume(proindex, 10, 1, 10);
			} else if (fotindex == 2) {
				//新品腕表
				pro = tbWatchsService.queryWatchByinfo(proindex,  0,0, null, 0,0,4,current_no,page_size);
			} else if (fotindex == 3) {
				//时尚腕表
				pro = tbWatchsService.findWatchByGradeAndType(proindex, 8, current_no, page_size);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBody.error(e.getMessage());
		}
		return ResultBody.success(pro);
	}
}
