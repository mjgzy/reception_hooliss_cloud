package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手表性别表
 */
@Data
@TableName(value ="watch_grade")
public class WatchGrade  implements Serializable {
    @TableId(value = "grade_id")
    private Integer gradeId;

    @TableField(value = "grade_name")
    private String gradeName;



}