package com.xfkj.pojo.commodity;

import lombok.Data;

import java.io.Serializable;

/**
 * 手表档次
 */
@Data
public class WatchType implements Serializable {
    private Integer type_id;

    private String type_name;

	@Override
	public String toString() {
		return "WatchType [type_id=" + type_id + ", type_name=" + type_name + "]";
	}

    
}