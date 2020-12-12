package com.xfkj.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfkj.annotations.PassToken;
import com.xfkj.enums.CommonEnum;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.entity.user.UserComment;
import com.xfkj.entity.user.Wuser;
import com.xfkj.service.UserCommentService;
import com.xfkj.service.UserService;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
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
	private UserCommentService commentService;

	/**
	 * 用户登录
	 */
	@PassToken        //跳过密码验证
	@RequestMapping("/doLogin.xf")
	public ResultBody<?> doLogin(
            @RequestParam("u_Name") String u_Name, @RequestParam("u_Pwd") String u_Pwd) {
		HashMap<String,Object> modelMap = new HashMap<String,Object>();
		if (	userService.count(u_Name)){
			try {
				String s = DigestUtils.md5DigestAsHex((u_Name + u_Pwd).getBytes());	//md5加密
				Wuser users = userService.validateLogin(u_Name, s);
				modelMap.put("users",users );
			} catch (XFException e) {
				e.printStackTrace();
				return new ResultBody<>(e.getErrorCode(),e.getMessage() );
			}
			return new ResultBody<>(CommonEnum.SUCCESS,modelMap);
		}else{
			return new ResultBody<>(500,"用户不存在!" );
		}
	}

	/**
	 * 通过用户名查询用户
	 * @param userName:用户名
	 */
	@PostMapping("findUserByUsername")
	public Wuser findUserByUsername(@RequestParam("userName") String userName){
		LambdaQueryWrapper<Wuser> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(!StringUtils.isEmpty(userName),Wuser::getUName,userName);
		return userService.getOne(wrapper);
	};
	/**
	 * 判断用户手机号唯一性
	 * @param phone:手机号
	 */
	@RequestMapping("phoneOnlyVerification.xf")
	public ResultBody<?> phone(@RequestParam("phone") String phone){
		Boolean aBoolean = null;
		try {
			aBoolean = userService.phoneOnlyVa(phone);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(), e.getMessage());
		}
		return new ResultBody<>(CommonEnum.SUCCESS,aBoolean);
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
			return new ResultBody<>(400,"手机验证码输入错误,请重新输入");
		}
		if (!userService.count(wuser.getUName())) {
			return new ResultBody<>(500,"注册失败,该昵称已存在!" );
		} else  {
			String s = DigestUtils.md5DigestAsHex((wuser.getUName() + wuser.getUPwd()).getBytes());
			wuser.setUPwd(s);
			boolean register = userService.register(wuser);
			return new ResultBody<>(CommonEnum.SUCCESS,register);
		}
	}
	@RequestMapping("/yan.xf")
	public ResultBody<?> result(@RequestParam("textname") String u_name){
		boolean count = false;
		try {
			count = userService.count(u_name);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage() );
		}
		return  new ResultBody<>(CommonEnum.SUCCESS,count);
	}

	@RequestMapping("/doLogin2.xf")
	public ResultBody<?> dologin2(
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
				userByPhone.setUPhone(telephoneNumber);
				userByPhone.setUPwd("123456");
				Integer uuid = new Random().nextInt(99999);
				userByPhone.setUName("好利时"+uuid.toString());		//设置随机用户名
				userByPhone.setVipId(1);	//设置会员等级
				boolean register = userService.register(userByPhone);//注册用户
				if (!register){
					return new ResultBody<>(500, "注册用户失败!");
				}
			}
			modelMap.put("userByPhone", userByPhone)	;//将用户信息存入会话
			return new ResultBody<>(CommonEnum.SUCCESS,modelMap);
		}else {
			return new ResultBody<>(500, "验证码不正确");
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
		JSONObject object = new JSONObject();
		try {
			LambdaQueryWrapper<UserComment> wrapper = new LambdaQueryWrapper<>();
			Integer allCount = commentService.getBaseMapper().selectCount(wrapper);	//查询评价总条数
			wrapper.clear();		//清除条件
			wrapper.eq(UserComment::getHavePicture,true);
			Integer pictureCount = commentService.getBaseMapper().selectCount(wrapper);//查询有图评价总条数
			list = commentService.findCommentByName(0,w_id,null,whether_image,current_no,page_size);
			object.put("list",list);
			object.put("allCount",allCount);
			object.put("pictureCount",pictureCount);
		} catch (XFException e) {
			e.printStackTrace();
			return new ResultBody<>(e.getErrorCode(),e.getMessage() );
		}
		return new ResultBody<>(CommonEnum.SUCCESS,object);

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
			return new ResultBody<>(CommonEnum.SUCCESS,result);
		}
		return  new ResultBody<>(500,result.get("error") );
	}

	/**
	 * 生成图形验证码
	 * @throws IOException
	 */
	@RequestMapping({"/verify"})
	public void verify(HttpServletRequest request, HttpServletResponse response, String name,HttpSession session) throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
//		HttpSession session = request.getSession(false);

		int width = 73;
		int height = 27;
		BufferedImage image = new BufferedImage(width, height,
				1);

		Graphics g = image.getGraphics();

		Random random = new Random();

		Random rd = new Random();
		int r = rd.nextInt(50);
		int g1 = rd.nextInt(50);
		int b = rd.nextInt(50);
		g.setColor(new Color(r,g1,b));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", 0, 24));
		r = rd.nextInt(50);
		g1 = rd.nextInt(50);
		b = rd.nextInt(50);
		g.setColor(new Color(r,g1,b));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = Objects.requireNonNull(CommUtil.randomInt(1)).toUpperCase();
			sRand = sRand + rand;
			g.setColor(
					new Color(20 + random.nextInt(110), 20 + random
							.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 24);
		}

		if ("".equals(CommUtil.null2String(name))) {
			session.setAttribute("verify_code", sRand);
		} else {
			session.setAttribute(name, sRand);
		}
		g.dispose();
		ServletOutputStream responseOutputStream = response.getOutputStream();

		ImageIO.write(image, "JPEG", responseOutputStream);

		responseOutputStream.flush();
		responseOutputStream.close();
	}
	/**
	 * 通过用户id查找用户
	 * @param user_id:用户id
	 */
	@PostMapping("findUserById")
	ResultBody<?> findById(@RequestParam("user_id") Integer user_id){
		return new ResultBody<>(CommonEnum.SUCCESS,userService.findById(user_id));
	};

}
