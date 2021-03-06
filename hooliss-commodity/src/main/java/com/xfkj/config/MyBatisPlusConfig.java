package com.xfkj.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @描述：
 * @作者： zhouchaoxi
 * @日期：2019/12/19
 */
@Configuration
public class MyBatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
         paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
         paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    //pageHelper分页插件
//    @Bean
//    public PageHelper paginationInterceptor() {
//        System.err.println("分页插件注册>>>>>>>>>>>>>>>");
//        PageHelper page = new PageHelper();
//        Properties properties = new Properties();
//        //	 该参数默认为false
//        //	设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
//        //	和startPage中的pageNum效果一样
//        properties.setProperty("offsetAsPageNum","true");
//        //设置为true时，使用RowBounds分页会进行count查询
//        properties.setProperty("rowBoundvsWithCount","true");
//        // 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
//        //	（相当于没有执行分页查询，但是返回结果仍然是Page类型）
//        properties.setProperty("pageSizeZero","true");
//        //3.3.0版本可用 - 分页参数合理化，默认false禁用
//        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
//        //禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
//        properties.setProperty("reasonable","true");
//        //支持通过Mapper接口参数来传递分页参数
//        properties.setProperty("supportMethodsArguments","true");
//        //always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page
//        properties.setProperty("returnPageInfo","true");
//        page.setProperties(properties);
//        return page;
//    }
}
