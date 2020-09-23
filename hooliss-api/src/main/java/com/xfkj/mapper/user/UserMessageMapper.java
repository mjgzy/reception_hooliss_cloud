package com.xfkj.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.user.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息管理,留言,意见反馈,huiyuan.html
 * @author Administrator
 *
 */
public interface UserMessageMapper extends BaseMapper<UserMessage> {

	/**
	 * 通过用户id查询留言信息
	 * @param userId:
	 */
	List<UserMessage> findMessagesById(Integer userId);
	/**
	 * 查询用户总留言数
	 * @return:
	 */
	Integer count();
	/**
	 * 根据留言信息或时间查询留言
	 * @param info:可选参数.留言信息,模糊查询
	 * @param infoDate:时间,精确查找
	 * @return:
	 */
	List<UserMessage> findMessagesByInfo(
            @Param("info") String info,
            @Param("infoDate") String infoDate);

	/**
	 * 删除留言
	 * @param userMessageId
	 * @return
	 */
	Integer deleteMessage(Integer userMessageId);
}
