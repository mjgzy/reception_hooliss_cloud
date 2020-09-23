package com.xfkj.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfkj.entity.user.Wuser;
import org.apache.ibatis.annotations.Param;


/**
 * 用户,对应页面:huiyuan.html
 * @author Administrator
 *
 */
public interface WuserMapper extends BaseMapper<Wuser> {
	/**
	 * 登录验证
	 * @return 返回用户对象
	 */
	Wuser validateLogin(@Param("uName") String name, @Param("uPwd") String pwd);
	/**
	 * 注册
	 * @return 返回值必需为1,否则回滚
	 */
	Integer register(Wuser rn);
	/**
	 * 根据用户姓名查找用户编号
	 * @return  用户编号
	 */
	Integer findIdByName(String u_name);
	/**
	 * 用户名唯一性验证
	 * @return 返回1则代表有用户
	 */
	Integer contains(String name);

	/**
	 * 修改个人资料
	 * @return  返回值必需为1,请在业务层判断,不为1则抛出异常,回滚处理
	 */
    Integer modify(Wuser wuser);

	/**
	 * 通过手机号返回用户对象
	 * @param phone:手机号
	 */
	Wuser findUserByPhone(String phone);

	/**
	 * 查找用户名
	 * @param u_name:用户名
	 */
    Integer count(String u_name);

	/**
	 * 通过用户表主键获取用户对象
	 * @param u_id:用户id
	 */
	Wuser findById(Integer u_id);

	/**
	 * 判断手机号唯一性
	 * @param phone:手机号
	 */
	Integer phoneOnlyVa(String phone);

	/**
	 * 增加用户积分
	 * @param integral:要增加的用户积分
	 */
	Integer addUserIntegral(@Param("uId") Integer user_id, @Param("integral") Integer integral);
}