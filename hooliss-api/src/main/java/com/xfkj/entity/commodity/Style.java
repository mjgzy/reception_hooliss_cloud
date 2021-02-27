package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 手表详情页款式实体
 */
@Data
public class Style {

    @TableId(value = "series_id",type = IdType.AUTO)
    private Integer seriesId;

    private Integer watchId;

    private String watchImage;     //手表图片

    private String watchStyle;     //手表参数


}
