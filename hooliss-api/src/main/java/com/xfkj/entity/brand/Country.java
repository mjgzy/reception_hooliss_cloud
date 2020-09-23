package com.xfkj.entity.brand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌对应国家表
 */
@Data
@TableName(value="country")
public class Country implements Serializable {

    @TableField(value = "c_id")
    private Integer cId;
    @TableField(value = "c_name")
    private String cName;
    @TableField(value = "c_code")
    private String cCode;


}