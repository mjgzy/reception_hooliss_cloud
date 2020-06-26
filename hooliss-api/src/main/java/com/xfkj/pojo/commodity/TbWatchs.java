package com.xfkj.pojo.commodity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xfkj.pojo.brand.WatchBrand;
import com.xfkj.pojo.brand.WatchSeries;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(indexName = "tbwatchs",type = "docs", shards = 1, replicas = 0)
public class TbWatchs implements Serializable {

	@Id
	private Integer watch_id;
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String watch_name;		//手表名

	private String watch_descript;		//手表介绍
	@Field(type = FieldType.Double)
	private BigDecimal watch_price;		//手表价格
	@Field(index = false, type = FieldType.Keyword)
	private String watch_image;			//手表图片

	@Field(type = FieldType.Date,
			format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private String watch_date;			//手表添加日期

	private Integer watch_sellcount;	//卖出数量

	private String specialoffer;		//是否特价

	@Field(type = FieldType.Double)
    private BigDecimal so_price;        //特价

	private String examine_status;	//审核状态

	private int status;			//启用状态

	@Field(type = FieldType.Integer)
	private Integer wp_Id;		//手表参数id

	@Field(type = FieldType.Integer,store=true)
	private Integer series_id;		//手表系列id

	@Field(type = FieldType.Integer,store=true)
	private Integer brand_id;				//品牌id

	@Field(type = FieldType.Integer,store=true)
	private Integer watch_grade_id;		//性别分类id

	@Field(type = FieldType.Integer,store=true)
	private Integer watch_type_id;		//档次id

	@Field(type = FieldType.Object)
	private WatchParameter watchParameter; //手表参数对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	private WatchSeries watchSeries;       //手表系列对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	private WatchBrand watchBrand;		//品牌对象,通过方法获取,无需高级映射

	@Field(type = FieldType.Object)
	private WatchGrade watchGrade;		//手表性别对象

	@Field(type = FieldType.Object)
	private WatchType watchType;		//手表档次对象
}