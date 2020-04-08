package com.xfkj.controller;

import com.github.pagehelper.PageInfo;
import com.xfkj.config.SaticScheduleTask;
import com.xfkj.mapper.commodity.EsTbWatchsRepository;
import com.xfkj.mapper.commodity.TbWatchsMapper;
import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.commodity.TbWatchs;
import com.xfkj.service.commodity.EsTbWatchsService;
import com.xfkj.service.commodity.TbWatchsService;
import com.xfkj.service.commodity.WatchBrandService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.PriceUtil;
import com.xfkj.utils.RedisUtils;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("es-provider")
public class EsController {


    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private EsTbWatchsService esTbWatchsService;

    @Autowired
    private WatchBrandService watchBrandService;

    @Resource
    private TbWatchsMapper tbWatchsMapper;

    @Autowired
    private RedisUtils redisUtils;


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
            List<TbWatchs> sellWellTbWatchsByBrandId = tbWatchsMapper.findSellWellTbWatchsByBrandId(item.getBrand_id());
            redisUtils.hset(Constants.SELL_WELL_TBWATCHS_NAME, item.getBrand_id().toString(), sellWellTbWatchsByBrandId);
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
        Page<TbWatchs> tbWatchs = esTbWatchsService.queryWatchByinfo(grade_id, brand_id,
                key_word, price[0], price[1], condition, current_no, page_size);
        return tbWatchs;
    }
    @RequestMapping("findWatchById")
    public ResultBody findWatchById(@RequestParam("watch_id")Integer watch_id){
            return ResultBody.success(esTbWatchsService.queryWatchById(watch_id));
    }
}
