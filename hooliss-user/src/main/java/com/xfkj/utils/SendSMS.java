package com.xfkj.utils;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author imlee
 * @Date 2019-08-31 14:49
 *
 * 调用阿里云短信验证码 API
 * 进行手机号码的验证
 */

public class SendSMS {

    //  自己账号的 accessKeyId
    private static final String accessKeyId = "LTAI4FcUussE6SgKJkSxEKat";
    //  自己账号的 accessKeySecret
    private static final String accessKeySecret = "5hvwsQiNrzLwCbbTYF7HXQaDybiVFz";
    //  自己账号的   短信签名名称
    private static final String signName = "豌豆商城";
    //  自己账号的   短信模板ID
    private static final String templateCode = "SMS_177243108";

    /**
     *         发送短信验证码
     *
     * @param telephoneNumber  用户填写的手机号码
     * @return validateCode     该用户此次操作的短信验证码
     */
    public static Map<String,String> sendMessage(String telephoneNumber) {
        Map<String,String> result= new HashMap<>();
        //  随机生成 6位 验证码
        String validateCode = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //  请求方式  POST（默认）
        request.setMethod(MethodType.POST);
        //  阿里云短信服务器（默认）
        request.setDomain("dysmsapi.aliyuncs.com");
        //  版本号（默认）
        request.setVersion("2017-05-25");
        //  执行动作  发送短信 （默认）
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telephoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":" + validateCode + "}");
        String data=null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            data = response.getData();
            result.put("data",data);
            System.out.println(data);

        } catch (ServerException e) {
            e.printStackTrace();
            result.put("error","抱歉，您的当前获取次数已达上限，请于下一个时段或明天再试！");
            return result;
        } catch (ClientException e) {
            e.printStackTrace();
            result.put("error","系统异常，请重新获取");
            return result;
        }
        result.put("error","");
        result.put("result",validateCode);
        return result ;
    }

}



