package com.goodguy.blog.config;

import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.goodguy.blog.result.ResultCode.INTERNAL_SERVER_ERROR;

@ControllerAdvice //该注解定义全局异常处理类
//@ControllerAdvice(basePackages ="com.example.demo.controller") 可指定包
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value=Exception.class) //该注解声明异常处理方法
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        //对于异常的处理
        return ResultFactory.buildResult(INTERNAL_SERVER_ERROR,"服务器请求失败",null);
    }
}
