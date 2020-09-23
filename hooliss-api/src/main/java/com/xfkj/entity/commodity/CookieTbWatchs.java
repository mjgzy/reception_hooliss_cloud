package com.xfkj.entity.commodity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CookieTbWatchs implements Serializable {

    private String watchName;      //手表名称
    private String brandName;      //品牌名称
    private Integer brandId;       //品牌id
    private Integer watchId;       //手表id
    private Double watchPrice;     //手表价格
    private Integer addCount;              //添加数量
    private String watchImage;             //手表图片
}
