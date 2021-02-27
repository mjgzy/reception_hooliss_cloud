package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "w_images")
public class WImages {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer wId;

    private String images;

   private String imagesType;
}