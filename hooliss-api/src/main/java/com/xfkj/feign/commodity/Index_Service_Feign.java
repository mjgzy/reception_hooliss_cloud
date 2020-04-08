package com.xfkj.feign.commodity;

import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("index-provider")
@FeignClient(value = "hooliss-commodity-provider")
public interface Index_Service_Feign {
    /**
     * 跳转到首页
     *
     */
    @RequestMapping("/doIndex.xf")
    public ResultBody forIndex() ;

    @RequestMapping("/products.xf")
    public ResultBody products(@RequestParam("currentno")Integer fotindex,
                               @RequestParam("proindex")Integer proindex,
                               @RequestParam("current_no")Integer current_no,
                               @RequestParam("page_size")Integer page_size);
}
