package com.xfkj.feign.commodity;

import com.xfkj.tools.ResultBody;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("brand-provider")
@FeignClient(value = "hooliss-commodity-provider",contextId = "bs")
public interface BrandServiceFeign {
    /**
     * 跳转到品牌馆选择页面
     */
    @RequestMapping("brand-provider/doBrand.xf")
     ResultBody doBrandHtml();

    @RequestMapping("brand-provider/doBrandbyid.xf")
     ResultBody doBrandbyid();

    /**
     * 跳转到品牌馆详情页面
     */
    @RequestMapping("brand-provider/doShop.xf/{brand_id}")
     ResultBody doShopHtml(@PathVariable("brand_id") String str_series_brand_id);
        //获取该品牌下的品牌名和logo

    @RequestMapping("brand-provider/click.xf/{brand_id}/{grade_id}/{watch_name}/{watch_priceMin}/{watch_priceMax}/{current_no}/{page_size}/{condition}")
    public ResultBody ajax4(
            @PathVariable("brand_id") String str_series_brand_id,
            @PathVariable("grade_id") String str_grade_id,
            @PathVariable("watch_name") String str_watch_name,
            @PathVariable("watch_priceMin") String str_watch_priceMin,
            @PathVariable("watch_priceMax") String str_watch_priceMax,
            @PathVariable("current_no") String str_current_no,
            @PathVariable("page_size") String str_page_size,
            @PathVariable("condition") String str_condition
    );

}
