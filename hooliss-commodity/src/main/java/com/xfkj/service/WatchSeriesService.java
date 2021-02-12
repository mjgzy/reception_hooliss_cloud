package com.xfkj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import com.xfkj.entity.commodity.Style;
import com.xfkj.exceptionHandling.XFException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 手表品牌信息
 */
@Service
public interface WatchSeriesService extends IService<WatchSeries> {

    IPage<WatchSeries> getListByParam(IPage<WatchSeries> page,HashMap<String,Object> param);
}
