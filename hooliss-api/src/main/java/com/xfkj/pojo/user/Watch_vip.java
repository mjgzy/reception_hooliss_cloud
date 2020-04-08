package com.xfkj.pojo.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class Watch_vip implements Serializable {
    private Integer id;

    private String vip_name;//会员名

    private String vip_discount;//会员折扣

}
