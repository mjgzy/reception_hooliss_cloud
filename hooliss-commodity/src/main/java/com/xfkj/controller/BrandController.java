package com.xfkj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchType;
import com.xfkj.enums.CommonEnum;
import com.xfkj.pojo.vo.brand.WatchBrandVo;
import com.xfkj.pojo.vo.commodity.WatchTypeVo;
import com.xfkj.service.TbWatchsService;
import com.xfkj.service.WatchBrandService;
import com.xfkj.service.WatchTypeService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌馆控制
 */
@RestController
@Slf4j
@RequestMapping("brand-provider")
public class BrandController {

    @Autowired
    private WatchBrandService watchBrandService;
    @Autowired
    private TbWatchsService tbWatchsService;
    @Autowired
    private WatchTypeService watchTypeService;
    /**
     * 跳转到品牌馆选择页面
     */
    @RequestMapping("/doBrand.xf")
    public ResultBody<PageInfo<WatchBrand>> doBrandHtml() {
        PageInfo<WatchBrand> pageInfo = watchBrandService.findWatchBrand();
        if (pageInfo.getList()!=null){
            return new ResultBody<>(CommonEnum.SUCCESS,pageInfo);
        }
      return new ResultBody<>(CommonEnum.ERROR_EMPTY);
    }

    @RequestMapping("/doBrandById.xf")
    public ResultBody<List<WatchTypeVo>> doBrandById(){
        try {
            List<WatchType> watchTypes = watchTypeService.list();
            List<WatchTypeVo> typeVos = new ArrayList<>();
            LambdaQueryWrapper<WatchBrand> wrapper = new LambdaQueryWrapper<>();
            watchTypes.forEach(item->{
                wrapper.clear();
                WatchTypeVo watchTypeVo = BeanUtil.toBean(item, WatchTypeVo.class);
                wrapper.eq(WatchBrand::getTId,item.getTypeId());
                watchTypeVo.setWatchBrandVo(watchBrandService.list(wrapper));
                typeVos.add(BeanUtil.toBean(watchTypeVo,WatchTypeVo.class));
            });
            return new ResultBody<>(CommonEnum.SUCCESS,typeVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 跳转到品牌馆详情页面
     */
    @RequestMapping("/doShop.xf/{brand_id}")
    public ResultBody<Object> doShopHtml(@PathVariable("brand_id") String str_series_brand_id) {
        Integer series_brand_id = null;     //品牌id
        Map<String,Object> result  = new HashMap<>();
        //获取该品牌下的品牌名和logo
        try {
            series_brand_id = Integer.valueOf(str_series_brand_id);
            WatchBrand brand =  watchBrandService.queryBrandById(series_brand_id);
            WatchBrandVo watchBrandVo = BeanUtil.toBean(brand, WatchBrandVo.class);
            System.err.println(watchBrandVo.getCountry());
            result.put(Constants.WATCH_BRAND_NAME,watchBrandVo);
            //获取该品牌下的总销量
            Integer Number = watchBrandService.countSalesVolume(series_brand_id);
            result.put("Number",Number);
            //获取该品牌下所有系列
            PageInfo<WatchSeries> lWatchSeries = watchBrandService.findSeriesByBrand_brandId(series_brand_id);
            result.put(Constants.WATCH_SERIES_NAME, lWatchSeries);
            //获取该品牌下所有手表
            PageInfo<TbWatchs> tbWatchsPageInfo = tbWatchsService.queryWatchByinfo(0, series_brand_id,0,"",0,0, 0, 1, 20);
            result.put(Constants.WATCHS_NAME,tbWatchsPageInfo);

            //获取上新推荐手表
            PageInfo<TbWatchs> watchByBrandId = tbWatchsService.queryWatchByDate(series_brand_id,1,8);
            result.put(Constants.HOT_WATCHS_NAME, watchByBrandId);  //保存热销手表
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return new ResultBody<>(CommonEnum.SUCCESS,result);
    }

    @RequestMapping("/click.xf/{brand_id}/{grade_id}/{watch_name}/{watch_priceMin}/{watch_priceMax}/{current_no}/{page_size}/{condition}")
    public ResultBody<PageInfo<TbWatchs>> ajax4(
            @PathVariable("brand_id") String str_series_brand_id,
            @PathVariable("grade_id") String str_grade_id,
            @PathVariable("watch_name") String str_watch_name,
            @PathVariable("watch_priceMin") String str_watch_priceMin,
            @PathVariable("watch_priceMax") String str_watch_priceMax,
            @PathVariable("current_no") String str_current_no,
            @PathVariable("page_size") String str_page_size,
            @PathVariable("condition") String str_condition
    ){
        Integer series_brand_id = null;     //品牌id
        Integer grade_id = null;            //性别id
        Integer tbseries_id = null;
        String tbswatch_name = null;         //手表名
        Integer tbwatch_priceMin = null;     //最低价
        Integer tbwatch_priceMax = null;     //最高价
        Integer current_no = null;          //当前页
        Integer page_size = null;          //页数大小
        Integer condition = null;          //查询条件
        if(str_condition.equals("p_all")){
            str_condition = "2";
        }else if(str_condition.equals("p_up")){
            str_condition = "2";
        }else if(str_condition.equals("p_down")){
            str_condition = "5";
        }
        PageInfo<TbWatchs> pageinfo=null;
        try {
            series_brand_id = Integer.valueOf(str_series_brand_id);
            grade_id = Integer.valueOf(str_grade_id);
            if(str_watch_name.equals("0")){
                tbswatch_name = null;
            }else{
                tbswatch_name = str_watch_name;
            }
            if(str_watch_priceMin.equals("0")){
                tbwatch_priceMin = null;
            }else{
                tbwatch_priceMin = Integer.valueOf(str_watch_priceMin);
            }
            if(str_watch_priceMax.equals("0")){
                tbwatch_priceMax = null;
            }else{
                tbwatch_priceMax = Integer.valueOf(str_watch_priceMax);
            }
            current_no = Integer.valueOf(str_current_no);
            page_size = Integer.valueOf(str_page_size);
            condition = Integer.valueOf(str_condition);
            pageinfo = tbWatchsService.queryWatchByinfo(grade_id, series_brand_id,tbseries_id,tbswatch_name,tbwatch_priceMin,tbwatch_priceMax, condition, current_no, page_size);
        } catch (Exception e) {
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return new ResultBody<>(CommonEnum.SUCCESS,pageinfo);
    }



}
