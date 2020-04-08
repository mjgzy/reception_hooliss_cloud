package com.xfkj.pojo.brand;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌对应国家表
 */
@Data
public class Country implements Serializable {


    private Integer c_id;

    private String c_name;

    private String c_code;


}