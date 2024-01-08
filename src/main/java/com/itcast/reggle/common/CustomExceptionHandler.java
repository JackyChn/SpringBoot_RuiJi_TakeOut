package com.itcast.reggle.common;

public class CustomExceptionHandler extends RuntimeException{
    public CustomExceptionHandler(String msg) {
        super(msg);
    }
}
