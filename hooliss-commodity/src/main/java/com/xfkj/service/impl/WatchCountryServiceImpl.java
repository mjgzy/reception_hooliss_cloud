package com.xfkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfkj.entity.brand.Country;
import com.xfkj.mapper.commodity.WatchBrandMapper;
import com.xfkj.mapper.commodity.WatchCountryMapper;
import com.xfkj.service.WatchCountryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation= Propagation.REQUIRED)
public class WatchCountryServiceImpl extends ServiceImpl<WatchCountryMapper, Country> implements WatchCountryService {

}
