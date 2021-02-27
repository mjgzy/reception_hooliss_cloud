package com.xfkj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchType;
import com.xfkj.enums.CommonEnum;
import com.xfkj.pojo.vo.brand.WatchBrandVo;
import com.xfkj.pojo.vo.commodity.WatchTypeVo;
import com.xfkj.service.TbWatchsService;
import com.xfkj.service.WatchBrandService;
import com.xfkj.service.WatchSeriesService;
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
    private WatchSeriesService watchSeriesService;
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
    public ResultBody<?> doBrandHtml() {
        List<WatchBrand> list = watchBrandService.list();
        if (list.size()!=0){
            return new ResultBody<>(CommonEnum.SUCCESS,list);
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
        HashMap<String,Object> param  = new HashMap<>();
        //获取该品牌下的品牌名和logo
        try {
            series_brand_id = Integer.valueOf(str_series_brand_id);
            WatchBrand brand =  watchBrandService.getById(series_brand_id);
            WatchBrandVo watchBrandVo = BeanUtil.toBean(brand, WatchBrandVo.class);
            result.put(Constants.WATCH_BRAND_NAME,watchBrandVo);
            //获取该品牌下的总销量
            Integer Number = watchBrandService.countSalesVolume(series_brand_id);
            result.put("Number",Number);
            //获取该品牌下所有系列
            param.put("series_brand_id",str_series_brand_id);
            IPage<WatchSeries>  lWatchSeries = watchSeriesService.getListByParam(null,param);
            result.put(Constants.WATCH_SERIES_NAME, lWatchSeries.getRecords());
            param.put("brand_id",str_series_brand_id);
            //获取该品牌下所有手表
            IPage<TbWatchs> watchByParam = tbWatchsService.getWatchByParam(1, 20, param);
            result.put(Constants.WATCHS_NAME,watchByParam);
            //获取上新推荐手表
            param.put("condition",4);
            IPage<TbWatchs> watchByBrandId = tbWatchsService.getWatchByParam(1,8,param);
            result.put(Constants.HOT_WATCHS_NAME, watchByBrandId);  //保存热销手表
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return new ResultBody<>(CommonEnum.SUCCESS,result);
    }

    @RequestMapping("/click.xf/{brand_id}/{grade_id}/{watch_name}/{watch_priceMin}/{watch_priceMax}/{current_no}/{page_size}/{condition}")
    public ResultBody<?> ajax4(
            @PathVariable("brand_id") Integer series_brand_id,
            @PathVariable("grade_id") Integer grade_id,
            @PathVariable("watch_name") String watch_name,
            @PathVariable("watch_priceMin") Integer watch_price_min,
            @PathVariable("watch_priceMax") Integer watch_price_max,
            @PathVariable("current_no") Integer current_no,
            @PathVariable("page_size") Integer page_size,
            @PathVariable("condition") String str_condition
    ){
        HashMap<String,Object> param = new HashMap<>();
        param.put("watch_grade_id",grade_id);
        param.put("brand_id",series_brand_id);
        param.put("watch_name",watch_name);
        param.put("watch_price_min",watch_price_min);
        param.put("watch_price_max",watch_price_max);
        Integer condition = null;          //查询条件
        if(str_condition.equals("p_all")){
            str_condition = "2";
        }else if(str_condition.equals("p_up")){
            str_condition = "2";
        }else if(str_condition.equals("p_down")){
            str_condition = "5";
        }
        IPage<TbWatchs> pageInfo=null;
        try {
            condition = Integer.valueOf(str_condition);
            param.put("condition",condition);
            pageInfo = tbWatchsService.getWatchByParam(current_no,page_size,param);
        } catch (Exception e) {
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return new ResultBody<>(CommonEnum.SUCCESS,pageInfo);
    }



}
