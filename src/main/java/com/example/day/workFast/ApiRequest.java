package com.example.day.workFast;

import lombok.Data;

/**
 * @author: xiongchaohua
 * @Des : 公共请求包装类
 * @create: 2021-05-20 09:23
 **/
@Data
public class ApiRequest<T> {
    private T data;
    private Integer pageNum;
    private Integer pageSize;
}
