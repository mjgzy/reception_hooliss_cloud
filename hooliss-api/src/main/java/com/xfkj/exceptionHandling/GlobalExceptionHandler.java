package com.xfkj.exceptionHandling;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xfkj.tools.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = XFException.class)
    @ResponseBody
    public static ResultBody bizExceptionHandler(HttpServletRequest req, XFException e, BlockException e2){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        logger.error("发生Sentinel业务异常！原因是：{}",e2.getMessage());
        return ResultBody.error(e.getErrorCode(),e.getErrorMsg());
    }
   public static ResultBody findByIdException(@RequestParam("w_id") Integer w_id){
        return ResultBody.error("500","请稍后再试!");
    };
    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("其他异常！原因是:",e);
        return ResultBody.error(e.getMessage());
    }
}
