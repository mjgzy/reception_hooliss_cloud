package com.xfkj.pojo.commodity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CookieTbWatchs implements Serializable {

    private String watch_name;      //手表名称
    private String brand_name;      //品牌名称
    private Integer brand_id;       //品牌id
    private Integer watch_id;       //手表id
    private Double watch_price;     //手表价格
    private Integer add_count;              //添加数量
    private String watch_image;             //手表图片
}
