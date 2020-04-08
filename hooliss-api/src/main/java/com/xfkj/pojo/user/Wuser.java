package com.xfkj.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 */
@Data
public class Wuser  implements Serializable {

    private Integer u_id;

    private String u_name;

    private String u_pwd;

    private String u_email;

    private String u_phone;

    private Integer vip_id;

    private  Integer browsevolume;	//浏览量

    private Integer integral;		//积分

    private String head_image;		//用户头像

    private String create_date;			//创建日期

    private Watch_vip watch_vip;        //用户会员对象

}