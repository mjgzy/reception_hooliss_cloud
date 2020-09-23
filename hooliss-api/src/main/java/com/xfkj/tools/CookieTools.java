package com.xfkj.tools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xfkj.entity.commodity.CookieTbWatchs;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CookieTools {

    /**
     * 获取cookie中的购物车列表
     *
     * @param request:请求
     * @return 购物车列表
     */
    public static List<CookieTbWatchs> getCartInCookie(HttpServletRequest request) throws
            UnsupportedEncodingException {
        // 定义空的购物车列表
        List<CookieTbWatchs> items = new ArrayList<>();
        // 购物cookie
        Cookie cart_cookie = getCookie(request);
        // 判断cookie是否为空
        if (cart_cookie!=null&&cart_cookie.getValue() != null) {
            // 获取cookie中String类型的value
            System.err.println("原值："+cart_cookie);
            String decode = URLDecoder.decode(cart_cookie.getValue(), "UTF-8");
            if (decode!=null&!decode.equals("")){
                items = JSON.parseObject(decode,new TypeReference<List<CookieTbWatchs>>(){});
            }

        }
        return items;
    }

    /**
     * 删除cookie信息
     * @param response:响应
     * @param cookieName:cookie名字
     */
    public static void removeCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(5);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, CookieTbWatchs cookieTbWatchs) throws
            UnsupportedEncodingException {
        boolean flag = false;       //判断是否增加了商品数量
        Cookie carts = getCookie(request);  //获取cookie中的购物车信息
        List<CookieTbWatchs> ltbs = new ArrayList<>();
        //如果cookie中还没有购物车信息,则添加list进cookie
        if (carts==null){
            ltbs.add(cookieTbWatchs);
            Cookie cookie = null;
            try {
                String string = JSON.toJSONString(ltbs);        //将list转化为json,存入cookie
                cookie = new Cookie("shopCarts", URLEncoder.encode(string,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            cookie.setPath("/");
            cookie.setMaxAge(-1);        //过期时间设置为0
            response.addCookie(cookie);  //添加list到cookie中
        }else{
            String decode = URLDecoder.decode(carts.getValue(), "UTF-8");
            ltbs = JSON.parseArray(decode, CookieTbWatchs.class);
            for (CookieTbWatchs ctb :ltbs) {
                if (ctb.getWatchId().equals(cookieTbWatchs.getWatchId())){
                    ctb.setAddCount(ctb.getAddCount()+cookieTbWatchs.getAddCount());   //将购物车商品数量相加
                    flag = true;    //表示已被修改
                }
            }
            //如果为false则没有找到对应的购物车信息,此时新增一条商品
            if (!flag){
                ltbs.add(cookieTbWatchs);    //添加一条购物车信息
            }
            Cookie shopcarts = new Cookie("shopCarts",URLEncoder.encode(JSON.toJSONString(ltbs),"UTF-8") );
            shopcarts.setPath("/");
            shopcarts.setMaxAge(-1);
            response.addCookie(shopcarts);      //替换cookie

        }
    }


    /**
     * 获取cookie
     * @param request:请求
     */
    private static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if(cookies!=null){
            for (Cookie c :  cookies) {

                if (c.getName().equals("shopCarts")){
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }


}
