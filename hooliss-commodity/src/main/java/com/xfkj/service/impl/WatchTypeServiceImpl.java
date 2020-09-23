package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.commodity.WatchType;
import com.xfkj.mapper.commodity.WatchTypeMapper;
import com.xfkj.service.WatchTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatchTypeServiceImpl extends ServiceImpl<WatchTypeMapper, WatchType> implements WatchTypeService {

    @Resource
    private WatchTypeMapper WatchTypeMapper;

    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存




}
