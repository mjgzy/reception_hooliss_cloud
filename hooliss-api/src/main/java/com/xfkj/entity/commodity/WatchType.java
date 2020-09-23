package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手表档次
 */
@Data
@TableName(value = "watch_type")
public class WatchType implements Serializable {
    private Integer typeId;

    private String typeName;
}