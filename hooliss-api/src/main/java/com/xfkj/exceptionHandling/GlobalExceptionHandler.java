package com.xfkj.exceptionHandling;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xfkj.enums.CommonEnum;
import com.xfkj.tools.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param e .
     * @return 1
     */
    @ExceptionHandler(value = XFException.class)
    @ResponseBody
    public static ResultBody<?> bizExceptionHandler(XFException e, BlockException e2){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        logger.error("发生Sentinel业务异常！原因是：{}",e2.getMessage());
        return new ResultBody<>(e.getErrorCode(),e.getErrorMsg());
    }
   public static ResultBody<?> findByIdException(@RequestParam("w_id") Integer w_id){
        return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,"请稍后再试!");
    }
    /**
     * 处理空指针的异常
     * @param e 1
     * @return 1
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultBody<?> exceptionHandler(NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return new ResultBody<>(CommonEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     * @param e 1
     * @return 1
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultBody<?> exceptionHandler(Exception e){
        logger.error("其他异常！原因是:",e);
        return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR,e.getMessage());
    }
}
