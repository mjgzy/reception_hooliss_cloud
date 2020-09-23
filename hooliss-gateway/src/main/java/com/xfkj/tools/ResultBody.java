package com.xfkj.tools;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

@Data
public class ResultBody<T> {
    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应结果
     */
    private T data;

    public ResultBody() {
    }

    /**
     * 返回时间
     */
    private Date creatTime;
    public ResultBody(CommonEnum apiEnum) {
        this.code = apiEnum.getResultCode();
        this.msg = apiEnum.getResultMsg();
        this.creatTime=new Date();
    }
    public ResultBody(CommonEnum apiEnum, String msg) {
        this.code = apiEnum.getResultCode();
        this.msg = msg;
        this.creatTime=new Date();
    }
    public ResultBody(Integer resultCode, String msg) {
        this.code = resultCode;
        this.msg = msg;
        this.creatTime=new Date();
    }
    public ResultBody(CommonEnum apiEnum, T data) {
        this.code = apiEnum.getResultCode();
        this.msg = apiEnum.getResultMsg();
        this.data = data;
        this.creatTime=new Date();
    }
    public ResultBody(CommonEnum apiEnum, String msg, T data) {
        this.code = apiEnum.getResultCode();
        this.msg = msg;
        this.data=data;
        this.creatTime=new Date();
    }



    /**
     * 成功
     *
     */
    public ResultBody<T> success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public ResultBody<T> success(T data) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMsg(CommonEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public ResultBody<T> error(BaseErrorInfoInterface errorInfo) {
        ResultBody<T> rb = new ResultBody<T>();
        rb.setCode(errorInfo.getResultCode());
        rb.setMsg(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }


    /**
     * 失败
     */
    public ResultBody<T> error(String message) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(-1);
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
