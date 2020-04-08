package com.xfkj.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APP_ID = "2016092700606385";

    // 商户私钥，您的PKCS8格式RSA2私钥，这些就是我们刚才设置的
    public static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCw1otifZfGHUiJRURsaGuRPKz3xh+pVvWL4LRr3OcV2yHNAiaT6Phcl9wcHWK4fwCoWT6unpOGnSlOu45cwNAMbD1GgYzpIjp+pcBi781kGcd1UpZnWTNjY/lboDEi3nPZMWfI7icDidCq6nnh0jhx/97NzOU+3eyBJ6U8/pIbVmIgLHVKOZGGYLYLspQacNx0mJ9ztX5GU+mVEBgc89+BrbFoGD5AJZ1yePaQG+fLYZt+sRhTAEPa+X3PwzfdZSHSitLCJfJjwdCSScMhZoH91mgYTtW/OqpIv7Ovl/Legzo+WFifEVfUN3GFn7/IR0/IikO8/NqY4kmkYeYJy0E3AgMBAAECggEBAJyZ0iOtXxvV0OenTLnIGwIqPm7JqD45QQfXPU+L96S2Qw+eyPURSegNn6HSMBt8dOOg7M6Mpc5/HRaZJ7BiG5v0uqrWjSfj5PNE68Nx42yzG5x9P65UIxrwbOHEUd80Kll71BHvPON4Ry8kqDzgApaJ5hBQU+9BbnDN4BnMwyyq7gJ6DvkgXB80iO7Nbevk0J9krnWGVWxdHW5tFvEsXRwRqFmPi5Qo/LPsj5nfkUThbGajekw5BXJZ45TjEJ9eOyCnFAWjjFXIPexSXEs4ueY8l00oZ+CdrtSGQqmLneo36I17kw/TYAeIX76LuY2lYvOECJyokX/zw1EgAv2IhdECgYEA8kM0sojSf3JX5QWzeZjEOcS4jRrWWJjFYd9HcgI7YXhA9MHVLx0PTJH2cIwcR7lh9dOAUf1pUbefd73yT5GfRpFefYosjPhp85TjBrBD3o7QeJvQSrZjkk/VBWICT3R/mWZo2/w1rqK+VMXJyoYe5rmLRwshU+6XTp5ygf6eR68CgYEAut2ba1niYOHf0fvIkmL/Rd2o72Fiwf7dyrpmWBl2Um2PNhZQhDOKwxB8pnF3Dmr0yTvyN/C/s0nv+sqtZZMgIj/zTyZ/8uQk5405dYdJgwfiLX84udkg6xFRXz71/V9SnwG+AIkxa3iIPaEQDfr3BJy4iw5ZvbiDQlF+Zg16+PkCgYAo1glA+u04jGaidTaOjaCt+Cn/pwFwh7flyoQ5hITL5kBUwYOU4Hoetw/qYsO1l1sa297qcyPYoXIg9gStQuetNn3N/YeK76i0ZWlrTDT0g/BCFEmk/l+EXy6SLLYk/IcxrWCJjFbkLAyXL4vXNopo6+e9PvQMONCKcQzuOqQlgwKBgCwRHJi5TrBoM98SEF+tJuPz/OXOwnpbPzSStpXqE0QnABHSO1ShzwRnO+4IbfohcO6Py3QzuxFty0EKUM/WXUqWaCtvOdcuxCEy5az4pXZYApeWG+t/78pfgCAE+f+xXguPXp5DvDrK5t4ZwG57k4sr5j0sui8KdzHJhBRO84QJAoGAXKzW/fTlBgcwiYg1ze91AaETYNh2oSPOWmRuh2Vwhb7L0rY/LanRAf16dp95ITFMMrLNU8pQ/49rhGfiiDPA3MAG7pNtrKtHpV6WryFj7k882rGK9NamIv16BZGZMci2TX/6AKwqp+TtSXlROw86U96ezTLgXkI/sqKSla6Q7so=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。，这些就是我们刚才设置的
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1X2UGB02ezipfXPfZe2XQoMjk5P3Ljdm1vmgKXLfZlwwlhqKift3T5zUy3hr8BrtNy4aSTUnosS9HvAFBhA2EfE5U9/aOyQ4rzD3AxG3wvkvHDqIZIDxFu38M+s4ipr1l1/EpGMk3+7hM+F8GK/FD7SjBt9xb9uCcVCWIE7ccLIV3YoBJrkSwF65Mu4UebpBt296bLpVUpnrqPU72WPm5510cPp7mmAWk1VKBzB4y/w0dr0lbMeALcH0ndPFklm3jOvVpuC2tLWgVREG3pwgXLKvKC20zJ2yqGyaoK05+WzThJybFP/qR/hzgq2wth0U3JlcxMpy716TT1WzuxQD/wIDAQAB";

    //异步通知，再这里我们设计自己的后台代码
    public static String notify_url = "http://localhost:5120/order/updateOrderStatus";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:5120/order/doChengGong.xf";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String LOG_PATH = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        System.err.println("支付宝支付日志:"+sWord);
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
