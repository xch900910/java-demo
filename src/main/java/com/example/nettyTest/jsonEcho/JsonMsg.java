package com.example.nettyTest.jsonEcho;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Data
@AllArgsConstructor
public class JsonMsg {
    //id Field(域)
    private int id;
    //content Field(域)
    private String content = "疯狂创客圈-Java高并发社群";

    //在通用方法中，使用阿里FastJson转成Java对象
    public static JsonMsg parseFromJson(String json) {
        return JsonUtil.jsonToPojo(json, JsonMsg.class);
    }

    //在通用方法中，使用谷歌Gson转成字符串
    public String convertToJson() {
        return JsonUtil.pojoToJson(this);
    }

    public JsonMsg(int id) {
        this.id = id;
    }

    public JsonMsg() {
        this.id = RandomUtil.randInMod(100);
    }

    public static void main(String[] args) {
        JsonMsg jsonMsg = new JsonMsg();
        System.out.println(JSON.toJSONString(jsonMsg));
    }
}
