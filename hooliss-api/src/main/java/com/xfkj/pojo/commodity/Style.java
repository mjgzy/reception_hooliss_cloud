package com.xfkj.pojo.commodity;

import lombok.Data;

/**
 * 手表详情页款式实体
 */
@Data
public class Style {

    private Integer series_id;

    private Integer watch_id;

    private String watch_image;     //手表图片

    private String watch_style;     //手表参数


}
