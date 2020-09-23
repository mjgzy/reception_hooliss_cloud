package com.xfkj.feign.commodity;


import com.xfkj.tools.ResultBody;
import com.xfkj.annotations.UserLoginToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("commodity-provider")
@FeignClient(value = "hooliss-commodity-provider",contextId = "cs")
public interface Commodity_Service_Feign {


    /**
     *
     * @param str_key_word:顶部搜索框
     * @param str_grade_id:适用人群
     * @param str_current_no:当前页
     * @param str_page_size:页数大小
     * @param str_type_id:价格区间
     */
    @UserLoginToken
    @RequestMapping("commodity-provider/doOs.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
    public ResultBody doOsHtml(
            @PathVariable("key_word")String str_key_word,
            @PathVariable("grade_id") String str_grade_id,
            @PathVariable("current_no")String str_current_no,
            @PathVariable("page_size")String str_page_size,
            @PathVariable("type_id")String str_type_id,
            @PathVariable("brand_id")String str_brand_id,
            @PathVariable("condition")String str_condition);
    @RequestMapping("commodity-provider/doPaixu.xf/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
    public ResultBody doPaixu(
            @PathVariable("key_word")String str_key_word,
            @PathVariable("grade_id") String str_grade_id,
            @PathVariable("current_no")String str_current_no,
            @PathVariable("page_size")String str_page_size,
            @PathVariable("type_id")String str_type_id,
            @PathVariable("brand_id")String str_brand_id,
            @PathVariable("condition")String str_condition);
}
