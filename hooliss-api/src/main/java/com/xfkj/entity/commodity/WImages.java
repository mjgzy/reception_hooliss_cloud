package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "w_images")
public class WImages {
    private Integer idd;

    private Integer wId;

    private String images;

   
}