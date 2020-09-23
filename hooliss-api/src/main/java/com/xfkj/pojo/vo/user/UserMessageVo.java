package com.xfkj.pojo.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.user.UserMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户留言表
 * @author Administrator
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserMessageVo extends UserMessage {

}
