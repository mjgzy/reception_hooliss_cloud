package com.xfkj.service.commodity;

import com.xfkj.pojo.commodity.TbWatchs;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface EsTbWatchsService {

    /**
     * 	 页面参照男士女士手表
     * @param grade_id 可选参数,性别id
     * @param brand_id 品牌id,可为空
     * @param watch_name 根据品牌id 进行模糊查询
     * @param watch_priceMin 查询最小价格低至
     * @param watch_priceMax 查询最高价格封顶
     * @param condition
     * 条件:0表示默认排序,1表示价格降序,2表示价格升序,3表示销量降序,4表示日期降序
     * 	           根据价格升序或是降序,销量降序,或者最新日期,或者根据品牌名模糊查询
     * @param current_no	分页 当前第几条开始
     * @param page_size		分页显示的数量
     */
    Page<TbWatchs> queryWatchByinfo(Integer grade_id, Integer brand_id, String watch_name, Integer watch_priceMin,
                                    Integer watch_priceMax, Integer condition, Integer current_no, Integer page_size) throws NoNodeAvailableException;

    /**
     * 通过手表id查讯手表
     * @param watch_id:手表信息
     */
    Optional<TbWatchs> queryWatchById(Integer watch_id);

    /**
     * 保存数据到es
     * @param tbWatchs:数据集合
     */
    void saveAll(List<TbWatchs> tbWatchs);

    /**
     * 根据手表名模糊查询
     * @param column_name:字段名
     */
    Page<TbWatchs> searchByName(String column_name,String key_word);
}
