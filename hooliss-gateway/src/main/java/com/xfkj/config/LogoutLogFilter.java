package com.xfkj.config;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogoutLogFilter extends GenericFilterBean {

//    private String logoutUrl;
//    @Resource
//    private SysLogService sysLogService;
//    @Resource
//    private UserService userService;
//    public LogoutLogFilter(String logoutUrl){
//        this.logoutUrl=logoutUrl;
//    }
    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        if (this.requiresLogout(req) && SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            PageData loginUser= new PageData();
//            try {
//                loginUser.put("user_id",user.getId());
//                loginUser.put("last_login_date","last_login_date");
//                loginUser.put("last_login_ip",loginUser.get("login_ip"));
//                userService.updateUserLogin(loginUser);
//
//                PageData log=new PageData();
//                log.put("ip",CommUtil.getIpAddr(req));
//                log.put("title",user.getUsername()+"退出登录");
//                log.put("user_id",user.getId());
//                log.put("type","logout");
//                log.put("content",user.getUsername()+"退出系统");
//                sysLogService.saveObj(log);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * TODO   判断是否是退出
     * @作者: zhouChaoXi
     * @时间: 2019/3/12 23:36
     */
//    private boolean requiresLogout(HttpServletRequest request) {
//        if (CommUtil.isNull(logoutUrl))return false;
//        String path = request.getRequestURI();
//        return path.contains(logoutUrl);
//    }

//    public void setLogoutUrl(String logoutUrl) {
//        this.logoutUrl = logoutUrl;
//    }
}