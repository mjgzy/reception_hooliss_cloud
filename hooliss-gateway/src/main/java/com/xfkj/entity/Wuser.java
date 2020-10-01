package com.xfkj.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户表
 */
@Data
public class Wuser implements Serializable {

    private Integer uId;

    private String uName;

    private String uPwd;

    private String uEmail;

    private String uPhone;

    private Integer vipId;

    private  Integer browseVolume;	//浏览量

    private Integer integral;		//积分

    private String headImage;		//用户头像

    private String role;            //用户角色

    private Long role_id;            //用户角色ID

    private Integer status;     //状态

    private String createDate;			//创建日期
    private WatchVip watchVip;        //用户会员对象

}