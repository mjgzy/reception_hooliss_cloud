package com.xfkj.entity.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.brand.WatchSeries;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Document(indexName = "tbwatchs",type = "docs", shards = 1, replicas = 0)
@TableName(value = "tb_watchs")
public class TbWatchs implements Serializable {

	@Id
	@TableId(value = "watch_id",type = IdType.AUTO)
	private Integer watchId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String watchName;		//手表名

	private String watchDescript;		//手表介绍
	@Field(type = FieldType.Double)
	private BigDecimal watchPrice;		//手表价格
	@Field(index = false, type = FieldType.Keyword)
	private String watchImage;			//手表图片

	@Field(type = FieldType.Date,
			format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private String watchDate;			//手表添加日期

	private Integer watchSellCount;	//卖出数量

	private String specialOffer;		//是否特价

	@Field(type = FieldType.Double)
    private BigDecimal soPrice;        //特价

	private String examineStatus;	//审核状态

	private int status;			//启用状态

	@Field(type = FieldType.Integer,store=true)
	private Integer seriesId;		//手表系列id

	@Field(type = FieldType.Integer,store=true)
	private Integer brandId;				//品牌id

	@Field(type = FieldType.Integer,store=true)
	private Integer watchGradeId;		//性别分类id

	@Field(type = FieldType.Integer,store=true)
	private Integer watchTypeId;		//档次id

	@Field(type = FieldType.Object)
	@TableField(exist = false)
	private WatchParameter watchParameter; //手表参数对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	@TableField(exist = false)
	private WatchSeries watchSeries;       //手表系列对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	@TableField(exist = false)
	private WatchBrand watchBrand;		//品牌对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	@TableField(exist = false)
	private WatchGrade watchGrade;		//手表性别对象

	@Field(type = FieldType.Object)
	@TableField(exist = false)
	private WatchType watchType;		//手表档次对象
}