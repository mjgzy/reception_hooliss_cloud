package com.xfkj.feign.commodity;

import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("es-provider")
@FeignClient(value = "hooliss-commodity-provider")
public interface EsServiceFeign {
    /**
     * 创建索引
     * @return:
     */
    @RequestMapping("createIndex")
    public boolean test1();

    @RequestMapping("updateSellWellTbWatchs")
    public void abc();

    @RequestMapping("queryTbwatchs")
    public Page<TbWatchs> testMatchQuery(@RequestParam("key_word") String key_word);

    @RequestMapping("findWatchById")
    public ResultBody findWatchById(@RequestParam("watch_id")Integer watch_id);
    @RequestMapping("/query2/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
    public Page<TbWatchs> testMatchQuery2(@PathVariable("key_word")String key_word,
                                          @PathVariable("grade_id") Integer grade_id,
                                          @PathVariable("current_no")Integer current_no,
                                          @PathVariable("page_size")Integer page_size,
                                          @PathVariable("type_id")String str_type_id,
                                          @PathVariable("brand_id")Integer brand_id,
                                          @PathVariable("condition")Integer condition);
}
