package com.xfkj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.commodity.WatchParameter;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface WatchParameterService extends IService<WatchParameter> {

    /**
     * 通过条件查询手表参数
     * @param current_no:当前页
     * @param size:页数大小
     * @param param:条件
     */
    IPage<WatchParameter> getParameterByParam(Integer current_no,
                                           Integer size, HashMap<String,Object> param) throws Exception;
}
