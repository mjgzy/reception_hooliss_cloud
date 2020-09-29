package com.xfkj.config;

//import com.www.common.base.PageData;
//import com.www.common.enums.Constant;
//import com.www.common.enums.RoleType;
//import com.www.common.pojo.User;
//import com.www.common.utils.CommUtil;
//import com.www.service.SysConfigService;
//import com.www.service.SysLogService;
//import com.www.service.UserService;
import com.xfkj.entity.User;
import com.xfkj.enums.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限验证成功
 * @author 
 */
@Component
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

//    @Resource
//    private SysLogService sysLogService;
//    @Resource
//    private SysConfigService sysConfigService;
//    @Resource
//    private UserService userService;
    private Logger log= LoggerFactory.getLogger(SuccessHandler.class);
    /**
      *登录成功后的操作
     * @Author: zhouchaoxi
     * @Date: Created in 2018/8/4 18:24
     * @Modified By: 
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse rep, Authentication auths) throws IOException, ServletException {
        log.info("=========================登录成功==============================");
        String s = "\n"+
                "                   _ooOoo_"+"\n"+
                "                  o8888888o"+"\n"+
                "                  88\" . \"88"+"\n"+
                "                  (| -_- |)"+"\n"+
                "                   O\\ = /O"+"\n"+
                "               ____/`---'\\____"+"\n"+
                "                . ' \\| |// `."+"\n"+
                "              / \\\\||| : |||// \\"+"\n"+
                "            / _||||| -:- |||||- \\"+"\n"+
                "             | | | \\\\\\ - /// | |"+"\n"+
                "             | \\_| ''\\---/'' | |"+"\n"+
                "             \\ .-\\__ `-` ___/-. /"+"\n"+
                "           ___\"`. .' /--.--\\ `. . __"+"\n"+
                "        .\"\" '< `.___\\_<|>_/___.' >'\"\"."+"\n"+
                "       | | : `- \\`.;`\\ _ /`;.`/ - ` : | |"+"\n"+
                "         \\ \\ `-. \\_ __\\ /__ _/ .-` / /"+"\n"+
                " ======`-.____`-.___\\_____/___.-`____.-'======";
        log.error(s);
        clearAuthenticationAttributes(req);
        User user=SecurityUserHolder.getCurrentUser();
        String targetUrl = "/index-provider/doIndex.xf";
        req.getSession().removeAttribute(Constant.CODE);
//        if (RoleType.ADMIN().equals(user.getRole())){
//            targetUrl="/admin/index.do";
//        }else if (RoleType.SELLER().equals(user.getRole())){
//            targetUrl="/seller/index.do";
//        }else if (RoleType.BUSINESS().equals(user.getRole())){
//            targetUrl="/business/index.do";
//        }
        req.getSession(false).setAttribute(Constant.LOGIN_USER,user);
        req.getSession(false).setAttribute(Constant.LOGIN_ROLE,user.getRole());
        getRedirectStrategy().sendRedirect(req, rep, targetUrl);
//        try {
//            PageData loginUser=new PageData();
//            loginUser.put("user_id",user.getId());
//            loginUser.put("login_count","login_count");
//            loginUser.put("login_ip",CommUtil.getIpAddr(req));
//            loginUser.put("login_date","login_date");
//            userService.updateUserLogin(loginUser);
//
//            PageData log=new PageData();
//            log.put("ip",CommUtil.getIpAddr(req));
//            log.put("title",user.getUsername()+"登录成功");
//            log.put("user_id",user.getId());
//            log.put("type","login");
//            log.put("content",user.getUsername()+"以"+user.getRole()+"角色成功登录");
//            sysLogService.saveObj(log);
//            getRedirectStrategy().sendRedirect(req, rep, targetUrl);
//            req.getSession().setAttribute(Constant.SYSCONFIG,sysConfigService.getConfig());
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException();
//        }

    }
}
