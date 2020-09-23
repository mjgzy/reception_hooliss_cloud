package com.xfkj.pojo.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.user.CommentImages;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户评论图片表
 * @author Administrator
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentImagesVo extends CommentImages {

}
