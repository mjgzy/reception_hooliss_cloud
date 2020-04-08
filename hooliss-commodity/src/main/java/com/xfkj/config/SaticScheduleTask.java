package com.xfkj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Configuration
//@EnableScheduling   //开启定时任务
public class SaticScheduleTask {

//    private mystatic final Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);
//

//    @Resource
//    private TbWatchsMapper tbWatchsMapper;
//
//    @Resource
//    private RedisUtils redisUtils;
//
//    @Resource
//    private WatchBrandService watchBrandService;
//    @Scheduled(cron = "0 0 0 * * ?")        //每天0点执行
//    private void sellWellSchduled(){
//        logger.info("定时任务执行,当前时间:"+new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").toString());
//        PageInfo<WatchBrand> watchBrand = watchBrandService.findWatchBrand();
//        watchBrand.getList().forEach(item->{
//            List<TbWatchsEs> sellWellTbWatchsByBrandId = tbWatchsMapper.findSellWellTbWatchsByBrandId(item.getBrand_id());
//            redisUtils.hset(SaticScheduleTask.SELL_WELL_TBWATCHS_NAME, item.getBrand_id().toString(), sellWellTbWatchsByBrandId);
//        });
//    }
}
