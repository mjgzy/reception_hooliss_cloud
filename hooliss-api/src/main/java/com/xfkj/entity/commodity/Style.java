package com.xfkj.entity.commodity;

import lombok.Data;

/**
 * 手表详情页款式实体
 */
@Data
public class Style {

    private Integer seriesId;

    private Integer watchId;

    private String watchImage;     //手表图片

    private String watchStyle;     //手表参数


}
