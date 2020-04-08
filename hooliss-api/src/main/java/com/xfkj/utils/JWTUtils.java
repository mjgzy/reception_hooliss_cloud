package com.xfkj.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xfkj.pojo.user.Wuser;
import org.springframework.util.DigestUtils;

import java.util.Date;

public class JWTUtils {

    public static String TOKEN_HEADER = "user_token";
    private static final String[] dictionaries={"a","B","C","d","e","F","g","h","I","j","k","L","m","n","O","p","Q","r",
    "s","t","U","v","W","x","Y","z"};
    /**
     * 获取token
     * @param user:
     */
    public static String getToken(Wuser user) {
        String token="";
        token= JWT.create().withAudience(user.getU_id().toString())
                .sign(Algorithm.HMAC256(user.getU_pwd()));
        return token;
    }

    public static String getBuyTypeToken(String phone,Integer buyType){
        Long aLong =null;
        String yyyyMMdd = DateFormatUtil.dateToString(new Date(), "yyyyMMdd");
        try {
            aLong=Long.valueOf(phone);
            aLong+=buyType;       //将购买类型相加,0购买/1购物车
            Long dateLong=Long.valueOf(yyyyMMdd);
            aLong+=dateLong;        //将当天日期相加
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return DigestUtils.md5DigestAsHex(aLong.toString().getBytes());
    }
}
