package com.xfkj.controller;

import com.xfkj.config.SaticScheduleTask;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.commodity.Style;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.pojo.commodity.WatchImages;
import com.xfkj.pojo.commodity.WatchParameter;
import com.xfkj.service.commodity.TbWatchsService;
import com.xfkj.service.commodity.WatchBrandService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private RedisUtils redisUtils;

    @Autowired
    private TbWatchsService tbWatchsService;

    @RequestMapping("findById")
    ResultBody findById(@RequestParam("w_id") Integer w_id){
        try {
            return ResultBody.success(tbWatchsService.queryWatchById(w_id));
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage());
        }
    };
    @RequestMapping("findBrandById")
    ResultBody findBrandById(@RequestParam("brand_id")Integer brand_id) throws XFException {
        try {
            return ResultBody.success(watchBrandService.queryBrandById(brand_id));
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage());
        }
    };
    /**
     * 去手表详情页面
     */
    @RequestMapping("/doWatch.xf/{w_id}")
    public ResultBody doWatchHtml(@PathVariable("w_id") String str_w_id){
        Map<String,Object> result = new HashMap<>();
        Integer w_id = null;
            try {
                w_id=Integer.valueOf(str_w_id);
            }catch (Exception e) {
                return ResultBody.error(e.getMessage());
            }
        TbWatchs tbWatchs = tbWatchsService.queryWatchById(w_id);
        List<Style> styles=null;
        WatchBrand watchBrand = null;
            if (tbWatchs!=null){
//                获取款式信息
                styles =watchBrandService.queryStyleById(tbWatchs.getSeries_id());
                result.put("styles", styles);
                result.put("watch", tbWatchs);
//                获取手表品牌
                watchBrand =  watchBrandService.queryBrandById(tbWatchs.getBrand_id());
                result.put("brand", watchBrand);
//                获取手表图片
                List<WatchImages> watchImages = tbWatchsService.queryImagesById(tbWatchs.getWatch_id());
                result.put("watch_images",watchImages );
//                获取手表参数
                WatchParameter watchParameter = tbWatchsService.findWatchParameter(tbWatchs.getWatch_id());
                result.put("w_parameter", watchParameter);
//                获取手表系列
                tbWatchs.setWatchSeries(watchBrandService.querySeriesById(tbWatchs.getSeries_id()));
                //获取热销数据
                List<TbWatchs> hget = (List<TbWatchs>) redisUtils.hget(Constants.SELL_WELL_TBWATCHS_NAME, watchBrand.getBrand_id().toString());
                result.put(Constants.SELL_WELL_TBWATCHS_NAME, hget);
            }
        return ResultBody.success(result);
    }
}
