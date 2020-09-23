package com.xfkj.service.impl;

import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.commodity.EsTbWatchsRepository;
import com.xfkj.entity.commodity.TbWatchs;
import com.xfkj.service.EsTbWatchsService;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EsTbWatchsImpl implements EsTbWatchsService {


    @Autowired
    private EsTbWatchsRepository esTbWatchsRepository;

    private NativeSearchQueryBuilder queryBuilder;
    @Override
    public Page<TbWatchs> queryWatchByinfo(Integer grade_id, Integer brand_id, String watch_name, Integer watch_priceMin,
                                           Integer watch_priceMax, Integer condition, Integer current_no, Integer page_size) throws NoNodeAvailableException {
        // 构建查询条件
        queryBuilder = new NativeSearchQueryBuilder();
        //构建多条件查询
        BoolQueryBuilder boolQuery =  QueryBuilders.boolQuery();
         List<QueryBuilder> musts = new ArrayList<>();
        //关键字不为空则加入条件
        if (watch_name!=null&&!watch_name.equals("")){
            musts.add(QueryBuilders.matchPhraseQuery("watch_name", watch_name));
        }
        //性别不为空则加入条件
        if (grade_id!=null&&grade_id!=0){
            musts.add(QueryBuilders.matchPhraseQuery("watch_grade_id", grade_id));
        }
        //品牌不为空加入条件
        if (brand_id!=null&&brand_id!=0){
            musts.add(QueryBuilders.matchPhraseQuery("brand_id", brand_id));
        }
        //金额区间不为空加入条件
        if (watch_priceMin!=null&&watch_priceMax!=null&&watch_priceMin<watch_priceMax){
            musts.add(QueryBuilders.rangeQuery("so_price").from(watch_priceMin).to(watch_priceMax));
        }
        //排序不为空加入条件
        if (condition!=0){
            switch(condition){
                case 1:
                    //价格降序
                    queryBuilder.withSort(SortBuilders.fieldSort("so_price").order(SortOrder.DESC));
                    break;
                case 2:
                    //价格升序
                    queryBuilder.withSort(SortBuilders.fieldSort("so_price").order(SortOrder.ASC));
                    break;
                case 3:
                    //热销降序
                    queryBuilder.withSort(SortBuilders.fieldSort("watch_sellcount").order(SortOrder.DESC));
                    break;
                case 4:
                    //日期降序
                    queryBuilder.withSort(SortBuilders.fieldSort("watch_date").order(SortOrder.DESC));
            }
        }
        //分页不为空加入条件
        if (page_size>0){
            queryBuilder.withPageable(PageRequest.of(current_no,page_size ));
        }
        //多条件存入boolQuery
        musts.stream().forEach(item->{
            boolQuery.must(item);
        });
        //设置条件
        queryBuilder.withQuery(boolQuery);
        //获取结果
        Page<TbWatchs> search = esTbWatchsRepository.search(queryBuilder.build());
        return search;
    }

    @Override
    public Optional<TbWatchs> queryWatchById(Integer watch_id) {
        if(ObjectUtils.isEmpty(watch_id)){
            throw new XFException(400,"watch_id is null");
        }
        return esTbWatchsRepository.findById(watch_id);
    }

    @Override
    public void saveAll(List<TbWatchs> tbWatchs) {
        esTbWatchsRepository.saveAll(tbWatchs);
    }

    @Override
    public Page<TbWatchs> searchByName(String column_name,String key_word) {
        if(StringUtils.isEmpty(column_name)||StringUtils.isEmpty(key_word)){
            throw new XFException(400,"字段名为空或者关键字为空!");
        }
        // 构建查询条件
        queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery(column_name, key_word));
        // 搜索，获取结果
        Page<TbWatchs> items = esTbWatchsRepository.search(queryBuilder.build());
        return items;
    }
}
