package com.xfkj.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户表
 */
@Data
public class Wuser implements UserDetails {

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

    private List<GrantedAuthority> auths;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    @Override
    public String getPassword() {
        return this.uPwd;
    }

    @Override
    public String getUsername() {
        return this.uName;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.status==-1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.status==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.status==1)return true;
        return false;
    }
}