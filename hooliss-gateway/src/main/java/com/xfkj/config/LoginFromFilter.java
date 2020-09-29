package com.xfkj.config;

import com.xfkj.enums.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @描述：
 * @作者: zhouChaoXi
 * @时间: 2018/7/3 21:20
 */
@Component
public class LoginFromFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_VALID_CODE = "code";
    private String codeParameter = SPRING_SECURITY_FORM_VALID_CODE;
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;
    private String privateKey = "xxxxxxxxxxxxxxxxxxx";


    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    /**
     * 如果是通过页面进来验证码效验
     * 非页面进入不效验
      * 并拼接用户名和登录角色
     * @Author: zhouchaoxi
     * @Date: Created in 2018/8/4 17:44
     * @Modified By: 
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String code = (String)request.getSession().getAttribute(Constant.CODE);
        String inputCode = obtainValidCode(request);
        String login_role=request.getParameter(Constant.LOGIN_ROLE);
        request.getSession().setAttribute(Constant.LOGIN_ROLE,login_role);
        String username=obtainUsername(request);
        String password=obtainPassword(request);
        if (username == null)username = "";
        if (password == null)password = "";
        username=username+"__"+login_role;
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        if (code==null||inputCode==null){
            return super.getAuthenticationManager().authenticate(authRequest);
        }else if(inputCode!=null&&code.toUpperCase().equals(inputCode.toUpperCase())){
            return super.getAuthenticationManager().authenticate(authRequest);
        }else{
            throw new AuthenticationServiceException("验证码输入错误");
        }
    }

    protected String obtainValidCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    public String getCodeParameter() {
        return codeParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Valid code parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter).replaceAll(" ", "+");
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


}

