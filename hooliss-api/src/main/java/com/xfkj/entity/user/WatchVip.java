package com.xfkj.entity.user;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("watch_vip")
public class WatchVip implements Serializable {
    @TableId(value = "id")
    private Integer id;

    private String vipName;//会员名

    private String vipDiscount;//会员折扣

}
