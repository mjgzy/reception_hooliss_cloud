package com.xfkj.pojo.seckill;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户秒杀记录实体
 *
 */
@Data
public class SpikeRecord implements Serializable {

    private Integer id; //主键

    private Integer seckill_id;//商品id

    private Integer user_id;    //用户id

    private int status;     //秒杀状态

    private Date create_time;   //秒杀时间
}
