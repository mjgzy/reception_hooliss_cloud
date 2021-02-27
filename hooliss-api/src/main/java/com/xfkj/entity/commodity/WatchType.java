package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手表档次
 */
@Data
@TableName(value = "watch_type")
public class WatchType implements Serializable {

    @TableId(value = "type_id",type = IdType.AUTO)
    private Integer typeId;

    private String typeName;
}