package com.xfkj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchGrade;
import com.xfkj.enums.CommonEnum;
import com.xfkj.service.TbWatchsService;
import com.xfkj.service.WatchGradeService;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private WatchGradeService gradeService;

	@Value("${server.port}")
	private String port;

	@RequestMapping("getPort")
	public String getPort(){
		return port;
	}
	/**
	 * 跳转到首页
	 *
	 */
	@RequestMapping("/doIndex.xf")
	public ResultBody<?> forIndex() {
		Map<String,Object> result = new HashMap<>();
		//初始化分类
		try {
			List<WatchGrade> gradeList = gradeService.list();
			result.put("gradeList", gradeList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return new ResultBody<>(CommonEnum.SUCCESS,result);
	}
	@PostMapping("getWatchDataByType")
	public ResultBody<?> getWatchDataByType(Integer current_no,Integer size,
		@RequestBody HashMap<String,Object> param){
		try {
			return new ResultBody<IPage<TbWatchs>>().success(tbWatchsService.getWatchByParam(current_no,size, param));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,"系统异常!");
		}
	}
	@RequestMapping("/products.xf")
	public ResultBody<?> products(@RequestParam("currentno")Integer fotindex,
							   @RequestParam("proindex")Integer watch_grade_id,
							   @RequestParam("current_no")Integer current_no,
							   @RequestParam("page_size")Integer page_size) {
		IPage<TbWatchs> pro = new Page<>();
		HashMap<String,Object> param = new HashMap<>();
		param.put("watch_grade_id",watch_grade_id);
		watch_grade_id+=1;	//加1同步数据库
		try {
			if (fotindex == 1) {
				//排行榜
				pro = tbWatchsService.getWatchByParam(current_no,page_size,param);
			} else if (fotindex == 2) {
				param.put("condition",4);
				//新品腕表
				pro = tbWatchsService.getWatchByParam(current_no,page_size,param);
			} else if (fotindex == 3) {
				param.put("watch_type_id",8);
				//时尚腕表
				pro = tbWatchsService.getWatchByParam(current_no, page_size,param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return new ResultBody<>(CommonEnum.SUCCESS,pro);
	}
}
