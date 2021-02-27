package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手表参数信息
 */
@Data
@TableName("watch_parameter")
public class WatchParameter implements Serializable {

    @TableId(value = "parameter_id",type = IdType.AUTO)
    private Integer parameterId;

    private Integer watchId;  //手表主键id

    private String wCase;  	//外壳材质+

    private String shellColor;	//外壳颜色+

    private String color;		//表带颜色+

    private String style;		//款式+

    private String listingDate;	//上市日期+

    private String movementTypeNo;	//机芯型号+

    private String movementType;	//机芯类型+

    private String thickness;		//表盘厚度+

    private String crown;			//表冠+

    private String bandMaterial;	//表带材质+

    private String specialFunctions;	//特殊功能+

    private String parameterName;		//参数名

    private String width;				//表盘宽度+

    private String watchEar;			//表耳

    private String specularShading;		//镜面材质+

    private String waterproof;			//防水+



}