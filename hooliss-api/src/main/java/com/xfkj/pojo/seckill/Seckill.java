package com.xfkj.pojo.seckill;

import com.xfkj.pojo.commodity.TbWatchs;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 限时秒杀实体类
 */
@Data
public class Seckill implements Serializable {

    private Integer id;     //秒杀主键

    private Integer seckill_id;     //手表主键id

    private Integer number;         //商品库存数
    private Date start_time;        //开始时间

    private Date end_time;          //结束时间

    private Date create_time;       //创建时间

    private TbWatchs tbWatchs;      //秒杀商品对象

    private double discount;        //折扣

    private Double presentPrice;        //现价

    private Integer salesVolume;            //销量
}
