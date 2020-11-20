package com.example.day.core;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-07-09 14:48
 **/

public class LayuiReplay<T> {
    private int code;
    private String msg;
    private int count;
    private List<T> data;

    public LayuiReplay(int code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public String toJson() {
        String json = JSON.toJSONString(this);
        return json;
    }

    public static <T> String toJson(int count, List<T> data) {
        LayuiReplay<T> replay = new LayuiReplay<>(ReplyCode.OK.getCode(), ReplyCode.OK.getMessage(), count, data);
        return replay.toJson();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}