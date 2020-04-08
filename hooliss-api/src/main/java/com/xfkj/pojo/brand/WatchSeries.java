package com.xfkj.pojo.brand;

import lombok.Data;

import java.io.Serializable;

/**
 * 手表系列
 */
@Data
public class WatchSeries implements Serializable {

    private Integer id;

    private String series_name;		//系列名

    private Integer series_brand_id; //品牌id

    private Integer watch_id;       //手表id

}
