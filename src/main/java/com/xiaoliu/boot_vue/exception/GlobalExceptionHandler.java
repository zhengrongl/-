package com.xiaoliu.boot_vue.exception;

import com.xiaoliu.boot_vue.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ControllerAdvice 说明是异常处理器
 * ExceptionHandler 要处理什么异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 如果抛出的是ServiceException则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
