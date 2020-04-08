package com.xfkj.mapper.commodity;

import com.xfkj.pojo.commodity.TbWatchs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * es操作tb的dao
 */
public interface EsTbWatchsRepository extends ElasticsearchRepository<TbWatchs,Integer> {



}