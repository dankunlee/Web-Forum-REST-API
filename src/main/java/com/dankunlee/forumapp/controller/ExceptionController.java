package com.dankunlee.forumapp.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

class Error {
    String errorMsg;
    int errorNumb;

    public Error(String msg, int numb) {
        this.errorMsg = msg;
        this.errorNumb = numb;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorNumb() {
        return errorNumb;
    }

    public void setErrorNumb(int errorNumb) {
        this.errorNumb = errorNumb;
    }
}

@ControllerAdvice // allows this class to handle all exceptions globally
public class ExceptionController {
    public static final int ITEM_DOES_NOT_EXIST = -1;
    public static final int INVALID_PATH = -2;
    public static final int INVALID_PARAMETER = -3;

    @ResponseBody // a method return value should be bound to the web response body (json format)
    @ExceptionHandler(NoSuchElementException.class)
    public Error noItemErrorHandler(NoSuchElementException e) {
        return new Error("Item Does Not Exist", ITEM_DOES_NOT_EXIST);
    }

    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    public Error noItemErrorHandler(NumberFormatException e) {
        return new Error("Path in Invalid Format", INVALID_PATH);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error invalidMethodHandler(MethodArgumentNotValidException e) {
        return new Error("Invalid Parameter is Given", INVALID_PARAMETER);
    }
}
