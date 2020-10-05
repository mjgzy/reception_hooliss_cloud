package com.xfkj.enums;

import com.xfkj.exceptionHandling.BaseErrorInfoInterface;

public enum CommonEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS(1, "操作成功!"),
    ERROR_EMPTY(0, "数据为空!"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),
    WATCH_INVENTORY_IS_NULL(500,"产品库存为空!"),
    SECKILL_FEILD(200,"秒杀失败"),
    SECKILL_NULL(500,"未找到此手表信息!"),
    PASSWORD_FAILD(402,"用户名或者密码有误!");

    /** 错误码 */
    private Integer resultCode;

    /** 错误描述 */
    private String resultMsg;

    CommonEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Integer getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}
