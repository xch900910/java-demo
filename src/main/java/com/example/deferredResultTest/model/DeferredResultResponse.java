package com.example.deferredResultTest.model;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-07 14:27
 **/

import lombok.Data;
import lombok.Getter;

@Data
public class DeferredResultResponse {
    private Integer code;
    private String msg;

    public enum Msg {
        TIMEOUT("超时"),
        FAILED("失败"),
        SUCCESS("成功");

        @Getter
        private String desc;

        Msg(String desc) {
            this.desc = desc;
        }
    }
}