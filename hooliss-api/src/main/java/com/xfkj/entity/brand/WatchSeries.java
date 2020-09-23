package com.xfkj.entity.brand;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手表系列
 */
@Data
@TableName(value = "watch_series")
public class WatchSeries implements Serializable {

    private Integer id;

    private String seriesName;		//系列名

    private Integer seriesBrandId; //品牌id

    private Integer watchId;       //手表id

}
