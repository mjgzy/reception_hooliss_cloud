package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 手表图片表
 */
@Data
@TableName(value = "watch_images")
public class WatchImages {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer wId;

    private  String images;     //图片

    private String imagesType; //图片类型
}
