package com.xfkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfkj.entity.user.Wuser;
import com.xfkj.exceptionHandling.XFException;


/**
 * 用户业务层
 * @author Administrator
 *
 */
public interface UserService extends IService<Wuser> {
	/**
	 * 登录验证
	 * @param name
	 * @param pwd
	 */
	Wuser validateLogin(String name, String pwd) throws XFException;
	
	/**
	 * 注册
	 * @param register
	 */
	boolean register(Wuser register) throws XFException;
	/**
	 * 根据用户姓名查找用户编号
	 * @param u_name
	 */
	Integer findIdByName(String u_name) throws XFException;
	
	/**
	 * 用户名唯一性验证
	 * @param name
	 * @return
	 */
	boolean contains(String name) throws XFException;


	/**
	 * 修改个人资料
	 * @param wuser
	 * @return  返回值必需为1,请在业务层判断,不为1则抛出异常,回滚处理
	 */
	boolean modify(Wuser wuser) throws XFException;

	/**
	 * 通过用户表主键获取用户对象
	 * @param u_id:用户id
	 */
	Wuser findById(Integer u_id) throws XFException;

	/**
	 * 查找姓名
	 * @param u_name
	 * @return
	 */
	boolean count(String u_name) throws XFException;

	/**
	 * 通过手机号返回用户对象
	 * @param phone:手机号
	 */
	Wuser findUserByPhone(String phone) throws XFException;

	/**
	 * 用户手机号唯一性验证
	 * @param phone:手机号
	 */
	Boolean phoneOnlyVa(String phone) throws XFException;
	/**
	 * 增加用户积分
	 * @param integral:要增加的用户积分
	 */
	Integer addUserIntegral(Integer user_id, Integer integral) throws XFException;
}
