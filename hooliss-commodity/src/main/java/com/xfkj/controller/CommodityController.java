package com.xfkj.controller;

import com.github.pagehelper.PageInfo;
import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.service.commodity.EsTbWatchsService;
import com.xfkj.service.commodity.WatchBrandService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.PriceUtil;
import com.xfkj.annotations.UserLoginToken;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品控制,增加商品,删除商品,加入购物车
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/commodity-provider")
public class CommodityController {
    private Map<String,String> map = new HashMap<>();

	@Autowired
	private EsTbWatchsService esTbWatchsService;

	@Autowired
	private WatchBrandService watchBrandService;



    /**
     *
     * @param str_key_word:顶部搜索框
     * @param str_grade_id:适用人群
     * @param str_current_no:当前页
     * @param str_page_size:页数大小
     * @param str_type_id:价格区间
     */
    @UserLoginToken
	@RequestMapping("/doOs.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
	public ResultBody doOsHtml(
			@PathVariable("key_word")String str_key_word,
			@PathVariable("grade_id") String str_grade_id,
			@PathVariable("current_no")String str_current_no,
			@PathVariable("page_size")String str_page_size,
			@PathVariable("type_id")String str_type_id,
			@PathVariable("brand_id")String str_brand_id,
            @PathVariable("condition")String str_condition){
		Integer grade_id=null;
		Integer brand_id = null;
		Integer current_no=null;
		Integer page_size=null;
		Integer condition = null;
		String key_word = null;
		Map<String,Object> result = new HashMap<>();
		Map<String,String> conditions = new HashMap<>();
		Integer[] price = PriceUtil.priceformat(str_type_id);
		try {
			grade_id = Integer.valueOf(str_grade_id);
			current_no=Integer.valueOf(str_current_no);
			page_size = Integer.valueOf(str_page_size);
            brand_id = Integer.valueOf(str_brand_id);
            condition = Integer.valueOf(str_condition);
            if (!str_key_word.equals("0")&&!str_key_word.equals("")){
                key_word = str_key_word;
            }else{
                key_word = null;
            }
            switch (grade_id){
				case 1:
					conditions.put("适用人群:", "男士");
					break;
				case 2:
					conditions.put("适用人群:", "女士");
					break;
				case 3:
					conditions.put("适用人群:", "情侣");
					break;
				default:
                    conditions.put("适用人群:", null);
                    break;
			}
			conditions.put("搜索词:", key_word);		//搜索关键字
            if (!str_type_id.equals("0")){
                conditions.put("价格区间:", str_type_id);		//价格区间
            }else{
                conditions.put("价格区间:", null);		//价格区间
            }

            if (brand_id!=null&&brand_id!=0){
                conditions.put("品牌:", watchBrandService.queryNameById(brand_id));			//品牌
				result.put("brand_id", brand_id);
            }else{
                conditions.put("品牌:",null);			//品牌
            }

			result.put("conditions",conditions );		//保存条件筛选
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResultBody.error(e.getMessage());
		}
		//获取检索结果
		Page<TbWatchs> tbWatchs = null;
		try {
			tbWatchs = esTbWatchsService.queryWatchByinfo(grade_id, brand_id,
					key_word, price[0], price[1], condition, current_no, page_size);
		} catch (NoNodeAvailableException e) {
			e.printStackTrace();
			return ResultBody.error(e.getMessage());
		}
		result.put(Constants.WATCHS_NAME, tbWatchs);
//		加载所有品牌信息
		PageInfo<WatchBrand> watchBrand = watchBrandService.findWatchBrand();
		result.put(Constants.WATCH_BRAND_NAME, watchBrand);
		return ResultBody.success(result);
	}
	@RequestMapping("doPaixu.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
	public ResultBody doPaixu(
			@PathVariable("key_word")String str_key_word,
			@PathVariable("grade_id") String str_grade_id,
			@PathVariable("current_no")String str_current_no,
			@PathVariable("page_size")String str_page_size,
			@PathVariable("type_id")String str_type_id,
			@PathVariable("brand_id")String str_brand_id,
			@PathVariable("condition")String str_condition){
		Integer grade_id=null;
		Integer brand_id = null;
		Integer current_no=null;
		Integer page_size=null;
		Integer condition = null;
		String key_word = null;
		if (!str_key_word.equals("0")&&!str_key_word.equals("")){
			key_word = str_key_word;
		}
		Integer[] price = PriceUtil.priceformat(str_type_id);
		try {
			grade_id = Integer.valueOf(str_grade_id);
			current_no = Integer.valueOf(str_current_no);
			page_size = Integer.valueOf(str_page_size);
			brand_id = Integer.valueOf(str_brand_id);
			condition = Integer.valueOf(str_condition);
		}catch (NumberFormatException e){
			return ResultBody.error("500", e.getMessage());
		}
		System.err.println("grade_id"+grade_id);
		System.err.println("key_word"+key_word);
		System.err.println("brand_id"+brand_id);
		System.err.println("current_no"+current_no);
		System.err.println("page_size"+page_size);
		System.err.println("condition"+condition);
		//		加载手表信息
				Page<TbWatchs> tbWatchs = esTbWatchsService.queryWatchByinfo(grade_id, brand_id,
						key_word, price[0], price[1], condition, current_no, page_size);
		return ResultBody.success(tbWatchs);
	}
}
