package com.xfkj.mapper.user;

import com.xfkj.pojo.user.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息管理,留言,意见反馈,huiyuan.html
 * @author Administrator
 *
 */
public interface UserMessageMapper {

	/**
	 * 通过用户id查询留言信息
	 * @param user_id:
	 */
	List<UserMessage> findMessagesById(Integer user_id);
	/**
	 * 查询用户总留言数
	 * @return:
	 */
	Integer count();
	/**
	 * 根据留言信息或时间查询留言
	 * @param info:可选参数.留言信息,模糊查询
	 * @param info_date:时间,精确查找
	 * @return:
	 */
	List<UserMessage> findMessagesByInfo(
            @Param("info") String info,
            @Param("info_date") String info_date);

	/**
	 * 删除留言
	 * @param user_message_id
	 * @return
	 */
	Integer deleteMessage(Integer user_message_id);
}
