package com.xfkj.feign.commodity;

import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("watch-provider")
@FeignClient(value = "hooliss-commodity-provider")
public interface WatchDetails_Service_Feign {

    @RequestMapping("findById")
    ResultBody findById(@RequestParam("w_id") Integer w_id);

    @RequestMapping("findBrandById")
    ResultBody findBrandById(@RequestParam("brand_id")Integer brand_id);
    /**
     * 去手表详情页面
     */
    @RequestMapping("/doWatch.xf/{w_id}")
    public ResultBody doWatchHtml(@PathVariable("w_id") String str_w_id);
}
