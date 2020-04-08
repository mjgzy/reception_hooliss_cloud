package com.xfkj.pojo.stock;

import lombok.Data;

import java.util.Date;

/**
 * 库存表
 */
@Data
public class StockPile {
    private Integer id; //主键

    private Integer watch_id;   //手表id

    private Integer quantity;   //库存数量

    private Date firstDate;     //第一次进库存时间

    private Date lastDate;      //最后一次进库存时间
}
