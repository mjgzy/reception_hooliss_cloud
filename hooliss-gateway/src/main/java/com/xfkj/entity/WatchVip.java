package com.xfkj.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class WatchVip implements Serializable {
    private Integer id;

    private String vipName;//会员名

    private String vipDiscount;//会员折扣

}
