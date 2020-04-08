package com.xfkj.controller;

import com.xfkj.annotations.PassToken;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.user.UserComment;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.user.UserMessageService;
import com.xfkj.service.user.UserService;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录,注册,会员中心,huiyuan.html
 * @author Administrator
 *
 */
@RestController
@RequestMapping("user-provider")
public class UserController {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Resource
	private UserService userService;

	@Autowired
	private UserMessageService umService;


	/**
	 * 用户登录
	 */
	@PassToken        //跳过密码验证
	@RequestMapping("/doLogin.xf")
	public ResultBody doLogin(
            @RequestParam("u_Name") String u_Name, @RequestParam("u_Pwd") String u_Pwd) {
		HashMap<String,Object> modelMap = new HashMap<String,Object>();
		if (	userService.count(u_Name)){
			try {
				String s = DigestUtils.md5DigestAsHex((u_Name + u_Pwd).getBytes());	//md5加密
				Wuser users = userService.validateLogin(u_Name, s);
				modelMap.put("users",users );
			} catch (XFException e) {
				e.printStackTrace();
				return ResultBody.error(e.getErrorCode(),e.getMessage() );
			}
			return ResultBody.success(modelMap);
		}else{
			return ResultBody.error("500","用户不存在!" );
		}
	}

	/**
	 * 判断用户手机号唯一性
	 * @param phone:手机号
	 */
	@RequestMapping("phoneOnlyVerification.xf")
	public ResultBody phone(@RequestParam("phone") String phone){
		Boolean aBoolean = null;
		try {
			aBoolean = userService.phoneOnlyVa(phone);
		} catch (XFException e) {
			e.printStackTrace();
			return ResultBody.error(e.getErrorCode(), e.getMessage());
		}
		return ResultBody.success(aBoolean);
	}


	/**
	 * 注册实现
	 */
	@RequestMapping(value = "/reg.xf")
	public ResultBody register( @RequestParam(value = "wuser") Wuser wuser,
								@RequestParam(value="u_phone",required = true) String u_phone,
								@RequestParam(value="checkCode",required = true) String checkCode) {
		String code=redisTemplate.opsForValue().get(u_phone);	//从缓存中获取该手机号的验证码
		if (!checkCode.equals(code)){
			return ResultBody.error("400","手机验证码输入错误,请重新输入");
		}
		if (!userService.count(wuser.getU_name())) {
			return ResultBody.error("500","注册失败,该昵称已存在!" );
		} else  {
			String s = DigestUtils.md5DigestAsHex((wuser.getU_name() + wuser.getU_pwd()).getBytes());
			wuser.setU_pwd(s);
			boolean register = userService.register(wuser);
			return ResultBody.success(register);
		}
	}
	@RequestMapping("/yan.xf")
	public ResultBody result(@RequestParam("textname") String u_name){
		boolean count = false;
		try {
			count = userService.count(u_name);
		} catch (XFException e) {
			e.printStackTrace();
			return ResultBody.error(e.getErrorCode(),e.getMessage() );
		}
		return  ResultBody.success(count);
	}

	@RequestMapping("/doLogin2.xf")
	public ResultBody dologin2(
			@RequestParam("telephoneNumber") String telephoneNumber,
			@RequestParam("messageCode")String messageCode,
			@RequestParam("verifyCode")String verifyCode,
			@RequestParam("name")String name){
		HashMap<String,Object> modelMap = new HashMap<String,Object>();
		//从缓存中获取验证码
		String code=redisTemplate.opsForValue().get(telephoneNumber);
		if(verifyCode.equalsIgnoreCase(name)&&messageCode.equals(code)){
			Wuser userByPhone = userService.findUserByPhone(telephoneNumber);
			//为空则没有此用户,注册
			if (userByPhone==null){
				userByPhone = new Wuser();
				userByPhone.setU_phone(telephoneNumber);
				userByPhone.setU_pwd("123456");
				Integer uuid = new Random().nextInt(99999);
				userByPhone.setU_name("好利时"+uuid.toString());		//设置随机用户名
				userByPhone.setVip_id(1);	//设置会员等级
				boolean register = userService.register(userByPhone);//注册用户
				if (!register){
					return ResultBody.error("500", "注册用户失败!");
				}
			}
			modelMap.put("userByPhone", userByPhone)	;//将用户信息存入会话
			return ResultBody.success(modelMap);
		}else {
			return ResultBody.error("500", "验证码不正确");
		}
	}
	/**
	 *加载不同表的评论信息
	 * w_id  手表id
	 * u_id	 用户id
	 * whether_image  0有 1没有
	 * */
	@RequestMapping("comment.xf")
	public ResultBody comment(@RequestParam("w_id") Integer w_id,
                              @RequestParam("current_no") Integer current_no,
                              @RequestParam("page_size")  Integer page_size,
                              @RequestParam("whether_image")int whether_image){
		//查询全部评价
		List<UserComment> list= null;
		try {
			list = umService.findCommentByName(0,w_id,null,whether_image,current_no,page_size);
		} catch (XFException e) {
			e.printStackTrace();
			return ResultBody.error(e.getErrorCode(),e.getMessage() );
		}
		return ResultBody.success(list);

	}
	@RequestMapping(value = "/getCode/{code}")
	@ResponseBody
	public ResultBody getCode(@PathVariable("code") String telephoneNumber) {
		Map<String,String> result = null;
		result = SendSMS.sendMessage(telephoneNumber);
		//如果没有报错
		if (ObjectUtils.isEmpty(result.get("error"))){
			//  将短信验证码保存到 Redis 中，有效期为5分钟
			//  key  ： 手机号码
			//  value： 验证码
			redisTemplate.opsForValue().set(telephoneNumber, result.get("result"));
			redisTemplate.expire(telephoneNumber, 180, TimeUnit.SECONDS);
			return ResultBody.success(result);
		}
		return  ResultBody.error("500",result.get("error") );
	}
	/**
	 * 通过用户id查找用户
	 * @param user_id:用户id
	 */
	@PostMapping("findUserById")
	ResultBody findById(@RequestParam("user_id") Integer user_id){
		return ResultBody.success(userService.findById(user_id));
	};

}
