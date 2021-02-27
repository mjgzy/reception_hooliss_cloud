package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

/**
 * 用户表
 */
@Data
@TableName("wuser")
public class Wuser  implements Serializable {

    @TableId(value = "u_id")
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
    @TableField(exist = false)
    private WatchVip watchVip;        //用户会员对象

    public static void main(String[] args) {
        System.err.println(new BCryptPasswordEncoder().encode("mj296233"));
    }

}