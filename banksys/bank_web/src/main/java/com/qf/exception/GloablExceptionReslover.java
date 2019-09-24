package com.qf.exception;

import com.qf.common.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice
@RestControllerAdvice
public class GloablExceptionReslover {

    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e){
        return new JsonResult(1,e.getMessage());
    }

}
