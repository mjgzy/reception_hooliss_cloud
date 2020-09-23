package com.xfkj.controller;

import com.github.pagehelper.PageInfo;
import com.xfkj.enums.CommonEnum;
import com.xfkj.mapper.commodity.TbWatchsMapper;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.service.EsTbWatchsService;
import com.xfkj.service.WatchBrandService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.PriceUtil;
import com.xfkj.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("es")
public class EsController {


    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private EsTbWatchsService esTbWatchsService;

    @Autowired
    private WatchBrandService watchBrandService;

    @Resource
    private TbWatchsMapper tbWatchsMapper;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 同步数据
     */
    @RequestMapping("saveAll")
    public void saveall(){
        List<TbWatchs> watchByGradeAndType = tbWatchsMapper.findWatchByGradeAndType(null, null);
        esTbWatchsService.saveAll(watchByGradeAndType);
    }
    /**
     * 创建索引
     * @return:
     */
    @RequestMapping("createIndex")
    public boolean test1(){
        boolean index = esTemplate.createIndex(TbWatchs.class);
        return index;
    }
    @RequestMapping("updateSellWellTbWatchs")
    public void abc(){
        System.err.println("定时任务执行");
        PageInfo<WatchBrand> watchBrand = watchBrandService.findWatchBrand();
        watchBrand.getList().forEach(item->{
            List<TbWatchs> sellWellTbWatchsByBrandId = tbWatchsMapper.findSellWellTbWatchsByBrandId(item.getBrandId());
            redisUtils.hset(Constants.SELL_WELL_TBWATCHS_NAME, item.getBrandId().toString(), sellWellTbWatchsByBrandId);
        });
    }
    @RequestMapping("queryTbwatchs")
    public Page<TbWatchs> testMatchQuery(@RequestParam("key_word") String key_word) {
        return esTbWatchsService.searchByName("watch_name",key_word);
    }

    @RequestMapping("/query2/{key_word}/{grade_id}/{type_id}/{current_no}/{page_size}/{brand_id}/{condition}")
    public Page<TbWatchs> testMatchQuery2(@PathVariable("key_word")String key_word,
                                          @PathVariable("grade_id") Integer grade_id,
                                          @PathVariable("current_no")Integer current_no,
                                          @PathVariable("page_size")Integer page_size,
                                          @PathVariable("type_id")String str_type_id,
                                          @PathVariable("brand_id")Integer brand_id,
                                          @PathVariable("condition")Integer condition){
        Integer[] price = PriceUtil.priceformat(str_type_id);//拆分价格
        //获取检索结果
        return esTbWatchsService.queryWatchByinfo(grade_id, brand_id,
                key_word, price[0], price[1], condition, current_no, page_size);
    }
    @RequestMapping("findWatchById")
    public ResultBody<Optional<TbWatchs>> findWatchById(@RequestParam("watch_id")Integer watch_id){
            return new ResultBody<>(CommonEnum.SUCCESS,esTbWatchsService.queryWatchById(watch_id));
    }
}
