package com.xfkj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfkj.entity.brand.Country;
import com.xfkj.enums.CommonEnum;
import com.xfkj.exceptionHandling.GlobalExceptionHandler;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.commodity.Style;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.entity.commodity.WatchImages;
import com.xfkj.entity.commodity.WatchParameter;
import com.xfkj.mapper.commodity.WatchCountryMapper;
import com.xfkj.pojo.vo.brand.CountryVo;
import com.xfkj.pojo.vo.brand.WatchBrandVo;
import com.xfkj.service.*;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手表详情页控制
 */
@Slf4j
@RestController
@RequestMapping("watch-provider")
public class WatchDetailsController {

    @Autowired
    private WatchBrandService watchBrandService;
    @Autowired
    private WatchCountryService watchCountryService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WatchSeriesService watchSeriesService;

    @Autowired
    private TbWatchsService tbWatchsService;

    @Autowired
    private WatchParameterService watchParameterService;

    @RequestMapping("findById")
    @SentinelResource(value="findById",blockHandlerClass = GlobalExceptionHandler.class,
            blockHandler = "findByIdException")
    ResultBody<?> findById(@RequestParam("w_id") Integer w_id){
        try {
            return new ResultBody<>(CommonEnum.SUCCESS,tbWatchsService.queryWatchById(w_id));
        } catch (XFException e) {
            e.printStackTrace();
            return new ResultBody<>(e.getErrorCode(),e.getMessage());
        }
    }

    @RequestMapping("findBrandById")
    ResultBody<?> findBrandById(@RequestParam("brand_id")Integer brand_id) throws XFException {
        try {
            return new ResultBody<>(CommonEnum.SUCCESS,watchBrandService.getById(brand_id));
        } catch (XFException e) {
            e.printStackTrace();
            return new ResultBody<>(e.getErrorCode(),e.getMessage());
        }
    }
    /**
     * 去手表详情页面
     */
    @PostMapping("/doWatch")
    public ResultBody<?> doWatchHtml(Integer watch_id){
        Map<String,Object> result = new HashMap<>();
        TbWatchs tbWatchs = tbWatchsService.queryWatchById(watch_id);
        List<Style> styles=null;
        WatchBrandVo watchBrand = null;
        if (tbWatchs!=null){
//                获取款式信息
            styles =watchBrandService.queryStyleById(tbWatchs.getSeriesId());
            result.put("styles", styles);
            result.put("watch", tbWatchs);
//                获取手表品牌
            watchBrand = BeanUtil.toBean(watchBrandService.getById(tbWatchs.getBrandId()), WatchBrandVo.class);
            Country byId = watchCountryService.getById(Long.valueOf(watchBrand.getCountryId()));
            watchBrand.setCountry(BeanUtil.toBean(byId, CountryVo.class));
            tbWatchs.setWatchBrand(watchBrand);
//                获取手表图片
            List<WatchImages> watchImages = tbWatchsService.queryImagesById(tbWatchs.getWatchId());
            result.put("watchImages",watchImages );
//                获取手表系列
            tbWatchs.setWatchSeries(watchSeriesService.getById(tbWatchs.getSeriesId()));
//                获取热销数据
            List<TbWatchs> hget = (List<TbWatchs>) redisUtils.hget(Constants.SELL_WELL_TBWATCHS_NAME, watchBrand.getBrandId().toString());
            result.put(Constants.SELL_WELL_TBWATCHS_NAME, hget);
        }
        return new ResultBody<>(CommonEnum.SUCCESS,result);
    }
}
