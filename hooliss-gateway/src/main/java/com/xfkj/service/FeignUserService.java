package com.xfkj.service;

import com.xfkj.entity.Wuser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户登录,注册,会员中心,huiyuan.html
 * @author Administrator
 *
 */

//@RequestMapping("user-provider")
@FeignClient(value = "hooliss-user-provider",contextId = "user")
public interface FeignUserService {


	@PostMapping("findUserByUsername")
	Wuser findUserByUsername(@RequestParam("userName") String userName);

}
