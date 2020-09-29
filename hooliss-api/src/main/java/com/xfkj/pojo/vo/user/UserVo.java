package com.xfkj.pojo.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.user.Wuser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends Wuser {

    private String roleName;
}