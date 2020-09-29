package com.xfkj.config;

import com.xfkj.entity.User;
import com.xfkj.enums.Constant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SecurityUserHolder {

    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)) {
                return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        User user = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession(false);
            if (session != null)
                user = session.getAttribute(Constant.LOGIN_USER) != null ? (User) session.getAttribute(Constant.LOGIN_USER) : null;

        }
        return user;
    }
    public static HttpSession getSession(){
        RequestAttributes ra= RequestContextHolder.getRequestAttributes();
        System.out.println(ra.getSessionId());
        if (ra!= null) {
            HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
            HttpSession session = request.getSession(false);
            return session;
        }
        return null;
    }
    public static HttpServletRequest getRequest(){
        RequestAttributes ra= RequestContextHolder.getRequestAttributes();
        if (ra!= null) {
            HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
            return request;
        }
        return null;
    }
}

