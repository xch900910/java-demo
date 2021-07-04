package com.example.mybatisTest.model;

import lombok.Data;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 13:45
 */
@Data
public class Result<T> {
    private Integer code;
    private T data;
    private String message;
}
