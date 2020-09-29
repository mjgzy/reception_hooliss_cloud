package com.xfkj.enums;

/**
  *
 * @Author: zhouchaoxi
 * @Date: Created in 2018/8/11 13:11
 * @Modified By: 
 */
public class Constant {

    /**===============金币类型===================================*/
//  余额
    public static final String BALANCE="balance";
//  购买总额
    public static final String BUYTOTAL="buy_total";
//  提现总额
    public static final String CASHTOTAL="cash_total";
//  冻结余额
    public static final String FREEZEBLANCE="freeze_blance";
//  平台积分
    public static final String INTEGRAL="integral";
//  商家积分
    public static final String STOREINTEGRAL="store_integral";
    public static final String ADD="+";
    public static final String SUB="-";
    public static final String IFNULL="空";

    /**===============系统常量===================================*/
//  登录用户
    public static final String LOGIN_USER="loginUser";
//  登录角色
    public static final String LOGIN_ROLE="login_role";
//  验证码
    public static final String CODE="verify_code";
//  菜单
    public static final String MENUS="menus";
//  操作成功/失败跳转的url
    public static final String URL="url";
//  标题
    public static final String TITLE="title";
    public static final String MSG="msg";
//  系统设置
    public static final String SYSCONFIG="systemConfig";
//  分页条数
    public static final int PAGESIZE=10;

/**===============来源方式===================================*/
//  登录获得
    public static final String FROM_LOGIN="login";
//  注册获得
    public static final String FROM_REGISTER="register";
//  邀请人获得
    public static final String FROM_INVITER="inviter";
//  点击获得
    public static final String FROM_CLICK="clickInviter";
    public static final String ADD_TASK="addTask";

/**===============异常信息===============================*/
//  金额修改并发
    public static final String MONEYEXCEPTION="用户金额修改并发";

/**===============页面路径===============================*/
    public static final int QUERY=0;
    public static final int WRITE=1;
    public static final String PAGEADMIN="system/admin/";
    public static final String PAGE_SELLER="system/seller/";
    public static final String PAGE_BUSINESS="system/business/";
    public static final String PAGEUSER="system/user/web/";
    public static final String PAGEALL="all/";
    public static final String PAGEWAP="system/user/wap/";

/**===============时间格式===============================*/
    public final static String FORMAT_YEAR = "yyyy";
    public final static String TIME_PATTERN = "HHmmss";
    public final static String STANDARD_TIME_PATTERN = "HH:mm:ss";
    public final static String DATETIME_PATTERN = "yyyyMMddHHmmss";
    public final static String TIME_STAMP_PATTERN = "yyyyMMddHHmmssSSS";
    public final static String DATE_PATTERN = "yyyyMMdd";
    public final static String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_HM = "yyyy-MM-dd HH:mm";
    public final static String STANDARD_DATE_PATTERN = "yyyy-MM-dd";
    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS = "yyyy/MM/dd HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS_HM = "yyyy/MM/dd HH:mm";
    public final static String STANDARD_DATE_PATTERN_SOLIDUS = "yyyy/MM/dd";



/**===============编号前缀===============================*/
    public final static String RW = "RW";
    public final static String DD = "7B";
    public final static String CZ = "CZ";
    public final static String CG = "CG";
    public final static String PS = "PS";
    public final static String YH = "YH";
    public final static String MD = "MD";
    public final static String DL = "DL";
    public final static String RG = "RG";
    public final static String TK = "TK";

}
