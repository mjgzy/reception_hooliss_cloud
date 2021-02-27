package com.xfkj.entity.brand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value="watch_brand")
public class WatchBrand implements Serializable {

    @TableId(value = "brand_id")
    private Integer brandId;		//品牌id

    private String brandName;	//品牌名

    private String bDate;		//品牌添加日期

    private String bDect;		//品牌介绍

    private String bLogo;		//品牌logo路径

    private String brandCode;  //品牌编号

    private Integer tId;			//档次id

    private Integer countryId;     //国家id
}