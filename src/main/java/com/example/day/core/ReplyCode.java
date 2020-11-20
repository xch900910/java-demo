package com.example.day.core;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-07-09 14:51
 **/
public enum ReplyCode {
    OK(0, "success"),
    Fail(1, "fail");
    private Integer code;
    private String message;

    ReplyCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
