package com.xfkj.feign.user;

import com.xfkj.pojo.user.Wuser;
import com.xfkj.tools.ResultBody;
import com.xfkj.annotations.PassToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录,注册,会员中心,huiyuan.html
 * @author Administrator
 *
 */
@FeignClient("hooliss-user-provider")
@RequestMapping("user-provider")
public interface FeignUserController {

	/**
	 * 用户登录
	 */
	@PassToken        //跳过密码验证
	@RequestMapping("/doLogin.xf")
	public ResultBody doLogin(
            @RequestParam("u_Name") String u_Name, @RequestParam("u_Pwd") String u_Pwd) ;

	/**
	 * 判断用户手机号唯一性
	 * @param phone:手机号
	 */
	@RequestMapping("phoneOnlyVerification.xf")
	public ResultBody phone(@RequestParam("phone") String phone);

	/**
	 * 通过用户id查找用户
	 * @param user_id:用户id
	 */
	@PostMapping("findUserById")
	ResultBody findById(@RequestParam("user_id") Integer user_id);
	/**
	 * 注册实现
	 */
	@RequestMapping(value = "reg.xf")
	public ResultBody register(
			@RequestParam(value = "wuser") Wuser wuser,
			@RequestParam(value="u_name",required = true)String u_name,
			@RequestParam(value="u_phone",required = true) String u_phone,
			@RequestParam(value="checkCode",required = true) String checkCode
	);
	@RequestMapping("yan.xf")
	public ResultBody result(@RequestParam("textname") String u_name);

	@RequestMapping("doLogin2.xf")
	public ResultBody dologin2(
			@RequestParam("telephoneNumber") String telephoneNumber,
			@RequestParam("messageCode")String messageCode,
			@RequestParam("verifyCode")String verifyCode,
			@RequestParam("name")String name);
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
                              @RequestParam("whether_image")int whether_image);
	@RequestMapping(value = "getCode/{code}")
	public ResultBody getCode(@PathVariable("code") String telephoneNumber);
}
