package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区
 *
 * @author: Mr.Mou
 */
@TableName(value = "hooliss_area")
@Data
public class Area implements Serializable {

    @Id
    @TableId(value = "id")
    private Long id;
    // 地区名称
    private String areaName;

    // 地区名称拼音
    private String areaNamePy;
    // 地区简写
    private String areaNameEasy;
    // 地区序列
    private int sequence;
    // 地区等级
    private int level;
    //父ID
    private Long parentId;
}