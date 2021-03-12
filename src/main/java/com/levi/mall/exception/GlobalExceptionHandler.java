package com.levi.mall.exception;

import com.levi.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理系统异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        log.error("Default Exception",e);
        return ApiRestResponse.error(MallExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 处理业务异常
     * @return
     */
    @ExceptionHandler(MallException.class)
    @ResponseBody
    public Object handlerException(MallException e) {
        log.error("Mall Exception",e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handlerMethodArgumentNoValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: "+e);
        return handlerBindingResult(e.getBindingResult());
    }

    /**
     * 将异常信息暴露给用户
     * @param result
     * @return
     */
    private ApiRestResponse handlerBindingResult(BindingResult result) {
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                list.add(objectError.getDefaultMessage());
            }
        }
        System.out.println("error_list"+list);
        if (list.size() == 0) {
            return ApiRestResponse.error(MallExceptionEnum.REQUEST_ARGUMENT_ERROR);
        }
        return ApiRestResponse.error(MallExceptionEnum.REQUEST_ARGUMENT_ERROR.getCode(),list.toString());
    }
}
