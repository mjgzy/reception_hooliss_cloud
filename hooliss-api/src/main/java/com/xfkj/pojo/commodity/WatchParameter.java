package com.xfkj.pojo.commodity;

import lombok.Data;

import java.io.Serializable;

/**
 * 手表参数信息
 */
@Data
public class WatchParameter implements Serializable {

    private Integer parameter_id;

    private String w_case;  	//外壳材质

    private String shell_color;	//外壳颜色

    private String color;		//表带颜色

    private String style;		//款式

    private String listing_date;	//上市日期

    private String movement_type_no;	//机芯型号

    private String movement_type;	//机芯类型

    private String thickness;		//表盘厚度

    private String crown;			//表冠

    private String band_material;	//表带材质

    private String special_functions;	//特殊功能

    private String parameter_name;		//参数名

    private String width;				//表盘宽度

    private String watch_ear;			//表耳

    private String specular_shading;		//镜面材质

    private String waterproof;			//防水



}