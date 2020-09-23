package com.xfkj.entity.seckill;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.commodity.TbWatchs;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 限时秒杀实体类
 */
@Data
@TableName("seckill")
public class Seckill implements Serializable {

    private Integer id;     //秒杀主键

    private Integer seckillId;     //手表主键id

    private Integer number;         //商品库存数
    private Date startTime;        //开始时间

    private Date endTime;          //结束时间

    private Date createTime;       //创建时间

    private TbWatchs tbWatchs;      //秒杀商品对象

    private double discount;        //折扣

    private Double presentPrice;        //现价

    private Integer salesVolume;            //销量
}
