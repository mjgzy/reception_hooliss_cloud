package com.xfkj.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.ingest.IngestStats;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.springframework.beans.factory.annotation.Autowired;

public class EsUtils {

    // 引入 Ela 连接实列化对象
    @Autowired
    private TransportClient client;

    /**
     * 最大值
     * @param index  索引
     * @param field  文档属性
     */
    public double max(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("max_").append(field);
        String name = builder.toString();

        AggregationBuilder agg= AggregationBuilders.max(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Max max=response.getAggregations().get(name);
        return max.getValue();
    }

    /**
     * 最小值统计
     * @param index  索引
     * @param field  文档属性
     * @return
     */
    public double min(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("min_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.min(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Min min=response.getAggregations().get(name);
        return min.getValue();
    }

    /**
     * 合计统计
     * param index  索引
     * @param field 文档属性
     * @return
     */
    public double sum(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("sum_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.sum(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Sum sum=response.getAggregations().get(name);
        return sum.getValue();
    }


    /**
     * 平均值统计
     * param index  索引
     * @param field 文档属性
     * @return
     */
    public double avg(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("avg_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.avg(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Avg avg=response.getAggregations().get(name);
        return avg.getValue();
    }

    /**
     * 基本统计
     * @param index  索引
     * @param field 文档属性
     * @return
     */
    public IngestStats.Stats stats(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("stats_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.stats(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get(name);
    }


    /**
     * 高级统计
     * @param index  索引
     * @param field  文档属性
     * @return
     */
    public ExtendedStats extendedStats(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("extendedStats_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.extendedStats(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get(name);
    }


    /**
     * 基数统计
     * @param index  索引
     * @param field  文档属性
     * @return
     */
    public double cardinality(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("cardinality_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.cardinality(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Cardinality c=response.getAggregations().get(name);
        return c.getValue();
    }

    /**
     * 百分位统计
     * @param index  索引
     * @param field  文档属性
     * @return
     */
    public Percentiles percentiles(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("percentiles_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.percentiles(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get(name);
    }


    /**
     * 文档数量统计
     * @param index  索引
     * @param field  文档属性
     * @return
     */
    public double valueCount(String index, String field){
        StringBuilder builder = new StringBuilder();
        builder.append("valueCount_").append(field);
        String name = builder.toString();

        AggregationBuilder agg=AggregationBuilders.count(name).field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        ValueCount count=response.getAggregations().get(name);
        return count.getValue();
    }

}
