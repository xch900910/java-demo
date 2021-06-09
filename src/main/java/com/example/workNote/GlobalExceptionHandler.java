package com.example.workNote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiongchaohua
 * @Des : 全局异常处理类
 * @create: 2021-05-19 14:42
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Result bizExceptionHandler(HttpServletRequest request, ServiceException e) {
        log.error("业务异常,方法：{},{}", request.getRequestURI(), e.getMessage());
        return ResultGenerator.genFailResult(e.getMessage());
    }

    /**
     * 处理空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Result npeHandler(HttpServletRequest request, NullPointerException e) {
        log.error("空指针异常,方法：{},{}", request.getRequestURI(), e.toString());
        return ResultGenerator.genFailResult(e.getMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("发生异常：{}", e.toString());
        Result result = new Result();
        result.setData(e.getMessage());
        result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
        return result;
    }
}
