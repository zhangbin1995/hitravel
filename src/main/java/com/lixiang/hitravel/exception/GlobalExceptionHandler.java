package com.lixiang.hitravel.exception;

import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhang
 * @date 2019-12-09
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @SuppressWarnings("all")
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {

        if (e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR, msg);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            BindingResult result = ex.getBindingResult();
            List<ObjectError> errors = result.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR, msg);
        } else if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(CodeMsg.SERVER_ERROR, ex.getMessage());
        } else {
            return Result.error(CodeMsg.SERVER_ERROR, e.getMessage());
        }
    }
}
