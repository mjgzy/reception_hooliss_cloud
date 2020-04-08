package com.xfkj.pojo.brand;

import lombok.Data;

import java.io.Serializable;

@Data
public class WatchBrand implements Serializable {

    private Integer brand_id;		//品牌id

    private String brand_name;	//品牌名

    private String b_date;		//品牌添加日期

    private String b_dect;		//品牌介绍

    private String b_logo;		//品牌logo路径

    private String brand_code;  //品牌编号

    private Integer t_id;			//档次id

    private Integer country_id;     //国家id


    private Country country;	//国家对象
}