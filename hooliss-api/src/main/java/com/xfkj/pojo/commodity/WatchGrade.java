package com.xfkj.pojo.commodity;

import lombok.Data;

import java.io.Serializable;

/**
 * 手表性别表
 */
@Data
public class WatchGrade  implements Serializable {
    private Integer grade_id;

    private String grade_name;



}