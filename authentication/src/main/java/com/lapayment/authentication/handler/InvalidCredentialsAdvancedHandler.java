package com.lapayment.authentication.handler;

import com.lapayment.authentication.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;

@RestControllerAdvice
public class InvalidCredentialsAdvancedHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidCredentialsException.class)
    public HashMap<Object, Object> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        HashMap<Object, Object> hashMap = new HashMap<>();

        hashMap.put("timestamp", new Date());
        hashMap.put("status", 409);
        hashMap.put("error", "Conflict");
        hashMap.put("message", invalidCredentialsException.getMessage());

        return hashMap;
    }
}
