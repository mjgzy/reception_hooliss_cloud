package com.xfkj.pojo.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.user.UserComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 用户评价表
 * @author Administrator
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCommentVo extends UserComment {

}
