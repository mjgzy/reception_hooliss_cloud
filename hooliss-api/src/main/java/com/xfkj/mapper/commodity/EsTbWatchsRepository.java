package com.xfkj.mapper.commodity;

import com.xfkj.entity.commodity.TbWatchs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * es操作tb的dao
 */
@NoRepositoryBean
public interface EsTbWatchsRepository extends ElasticsearchRepository<TbWatchs,Integer> {



}