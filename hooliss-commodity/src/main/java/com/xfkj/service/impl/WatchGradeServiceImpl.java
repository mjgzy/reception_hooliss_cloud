package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.mapper.commodity.WatchGradeMapper;
import com.xfkj.entity.commodity.*;
import com.xfkj.service.WatchGradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatchGradeServiceImpl extends ServiceImpl<WatchGradeMapper, WatchGrade> implements WatchGradeService {

    @Resource
    private WatchGradeMapper WatchGradeMapper;

    //,结果为空时不使用缓存,使用参数性别id作为key存入缓存




}
