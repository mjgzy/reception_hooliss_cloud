package com.xfkj.entity.stock;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 库存表
 */
@Data
@TableName("stock_pile")
public class StockPile {
    @TableId(value = "id")
    private Integer id; //主键

    private Integer watchId;   //手表id

    private Integer quantity;   //库存数量

    private Date firstDate;     //第一次进库存时间

    private Date lastDate;      //最后一次进库存时间
}
