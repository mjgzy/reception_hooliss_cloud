package com.xfkj.controller.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfkj.feign.user.FeignUserController;
import com.xfkj.pojo.user.UserComment;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.JWTUtils;
import com.xfkj.annotations.PassToken;
import com.xfkj.utils.RandomValidateCode;
import com.xfkj.annotations.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录,注册,会员中心,huiyuan.html
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private FeignUserController feignUserController;

	/**
	 * 跳转到登录页
	 */
	@PassToken        //跳过密码验证
	@RequestMapping("/login.xf")
	public String login() {
		return "login";
	}

	/**
	 * 跳转到注册页
	 */
	@RequestMapping("/forRegister.xf")
	public String forRegister() {
		return "reg";
	}

	/**
	 * 用户登录
	 * @param field_url:登录失败跳转的url
	 * @param success_url:登录成功跳转的url
	 */
	@PassToken        //跳过密码验证
	@RequestMapping("/doLogin.xf")
	public ModelAndView doLogin(
            @RequestParam("u_Name") String u_Name, @RequestParam("u_Pwd") String u_Pwd,
            @RequestParam("field_url")String field_url,
            @RequestParam("success_url")String success_url,
            ModelAndView modelAndView, HttpSession httpSession,
            HttpServletResponse response) {
		Wuser user = null;
		ResultBody resultBody = feignUserController.doLogin(u_Name, u_Pwd);
		if(resultBody.getCode().equals("200")){
			HashMap<String,Object> modelMap = objectMapper.convertValue(resultBody.getResult(),new TypeReference<HashMap<String,Object>>(){});
			user= objectMapper.convertValue(modelMap.get("users"), new TypeReference<Wuser>() {});
				if (user != null) {
					//解决办法就是加上这一行代码，跨域访问允许访问的响应头的内容
					response.setHeader("Access-Control-Expose-Headers", JWTUtils.TOKEN_HEADER);
					//设置token到请求头
					String token = JWTUtils.getToken(user);
					httpSession.setAttribute(JWTUtils.TOKEN_HEADER,token);
					httpSession.setAttribute(Constants.USER_SESSION, user);
					modelAndView.setViewName(success_url);
					return modelAndView;
				} else {
					modelAndView.addObject("error", "密码不正确");
					modelAndView.setViewName(field_url);
					return modelAndView;
				}
		}else{
			modelAndView.addObject("error", resultBody.getMessage());
			modelAndView.setViewName(field_url);
			return modelAndView;
		}

	}
	/**
	 * 跳转到会员中心页面
	 * @param httpSession:会话
	 * @param modelAndView:视图
	 */
	@UserLoginToken        //进入用户中心必须验证用户
	@RequestMapping("/doVip.xf")
	public ModelAndView doVipHtml(HttpSession httpSession, ModelAndView modelAndView) {
		Wuser wuser = (Wuser)httpSession.getAttribute(Constants.USER_SESSION);
		//未登录则返回到登录页面
		if (wuser==null){
			modelAndView.setViewName("login");
			return modelAndView;
		}
		System.err.println("会员对象"+wuser);
		modelAndView.addObject("user", wuser);
		modelAndView.setViewName("huiyuan");
		return modelAndView;
	}

	/**
	 * 注销登录
	 */
	@RequestMapping("/logout.xf")
	public String logout(HttpSession httpSession) {
		if (httpSession.getAttribute(Constants.USER_SESSION) != null) {
			httpSession.removeAttribute(Constants.USER_SESSION);
			httpSession.removeAttribute(JWTUtils.TOKEN_HEADER);	//删除用户token
		}
		return "redirect:login.xf";
	}

	/**
	 * 判断用户手机号唯一性
	 * @param phone:手机号
	 */
	@RequestMapping("phoneOnlyVerification.xf")
	@ResponseBody
	public ResultBody phone(@RequestParam("phone") String phone){
		ResultBody resultBody = feignUserController.phone(phone);
		if(resultBody.getCode().equals("200")){
			Boolean aBoolean = objectMapper.convertValue( resultBody.getResult(),Boolean.class);
			return ResultBody.success(aBoolean);
		}else{
			  return ResultBody.success(false);
		}

	}

	/**
	 * 经过拦截器,转发到错误页面
	 */
	@RequestMapping("/error.xf")
	public String errorHandler(HttpServletRequest request) {
		System.err.println("aa");
		return "err";
	}

	/**
	 * 注册实现
	 */
	@RequestMapping(value = "/reg.xf")
	public ModelAndView register(@RequestParam("wuser") Wuser user,
                                 @RequestParam(value="u_name",required = true)String u_name,
                                 @RequestParam(value="u_phone",required = true) String u_phone,
                                 @RequestParam(value="checkCode",required = true) String checkCode,
								 ModelAndView modelAndView
	) {
		ResultBody register = feignUserController.register(user, u_name, u_phone, checkCode);
		if(register.getCode().equals("200")){
			modelAndView.addObject("success", "注册成功");
			modelAndView.setViewName("login");
			return modelAndView;
		}else{
			modelAndView.addObject("err", register.getMessage());
			modelAndView.setViewName("reg");
			return modelAndView;
		}
	}

	/**
	 * 验证用户名是否存在
	 * @param u_name:用户名
	 */
	@RequestMapping("/yan.xf")
	@ResponseBody
	public boolean result(@RequestParam("textname") String u_name, HttpServletRequest request){
		ResultBody result = feignUserController.result(u_name);
		if(u_name==null || u_name.equals(""))return  false;
		return (boolean) result.getResult();
	}

	/**
	 * 获取生成验证码显示到 UI 界面
	 * @param request：请求
	 * @param response：响应
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg");

		//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);//输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * 用户通过手机验证码登录
	 * @param telephoneNumber:手机号
	 * @param messageCode:填写的验证码
	 * @param verifyCode:图形验证码
	 * @param field_url:失败跳转地址
	 * @param success_url:成功跳转地址
	 */
	@RequestMapping("/doLogin2.xf")
	public ModelAndView dologin2(ModelAndView modelAndView,
                                 HttpSession session,
                                 HttpServletRequest request,
                                 @RequestParam("telephoneNumber") String telephoneNumber,
								 @RequestParam("messageVerifyCode")String messageCode,
								 @RequestParam("verifyCode")String verifyCode,
                                 @RequestParam("field_url")String field_url,
								 @RequestParam("success_url")String success_url){
		String name=(String)session.getAttribute("randomcode_key");
		ResultBody resultBody = feignUserController.dologin2(telephoneNumber, messageCode, verifyCode, name);
		if(resultBody.getCode().equals("200")){
			HashMap<String,Object> modelMap = objectMapper.convertValue(resultBody.getResult(),new TypeReference<HashMap<String,Object>>(){});
			Wuser wuser = objectMapper.convertValue(modelMap.get("userByPhone"),Wuser.class);
			session.setAttribute(Constants.USER_SESSION,wuser);	//将用户信息存入会话
			modelAndView.addObject("success", "登录成功");
			modelAndView.setViewName(success_url);
			return modelAndView;
		}else{
			modelAndView.addObject("error",resultBody.getMessage());
			modelAndView.setViewName(field_url);
			return modelAndView;
		}
	}
	/**
	 *加载不同表的评论信息
	 * w_id  手表id
	 * u_id	 用户id
	 * whether_image  0有 1没有
	 * */
	@RequestMapping("comment.xf")
	@ResponseBody
	public ResultBody comment(@RequestParam("w_id") Integer w_id,
                              @RequestParam("current_no") Integer current_no,
                              @RequestParam("page_size")  Integer page_size,
                              @RequestParam("whether_image")int whether_image){
		ResultBody comment = feignUserController.comment(w_id, current_no, page_size, whether_image);
		//查询全部评价
		List<UserComment> list=null;
		if(comment.getCode().equals("200")){
			list= objectMapper.convertValue(comment.getResult(),new TypeReference<ArrayList<UserComment>>(){});
			return ResultBody.success(list);
		}else{
			return  ResultBody.error(comment.getCode(),comment.getMessage());
		}
	}

	/**
	 * 通过手机号获取验证码(阿里云短信)
	 * @param telephoneNumber:手机号
	 */
	@RequestMapping(value = "/getCode/{code}")
	@ResponseBody
	public Map<String,String> getCode(@PathVariable("code") String telephoneNumber) {
		ResultBody code = feignUserController.getCode(telephoneNumber);
		Map<String,String> result=null;
		if(code.getCode().equals("200")){
			result= objectMapper.convertValue(code.getResult(),new TypeReference<Map<String,String>>(){});
			return result;
		}else{
			result.put("Message",code.getMessage());
		}
		return result;
	}

	/**
	 * 验证用户是否登录
	 */
	@RequestMapping(value = "/have")
	@ResponseBody
	public String ifhave(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION)==null){
			System.err.println("session为"+session.getAttribute(Constants.USER_SESSION));
			return "NotOK";
		}else{
			return "OK";
		}
	}

}
