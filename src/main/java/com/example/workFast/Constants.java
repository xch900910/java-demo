package com.example.workFast;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-19 17:52
 **/
public final class Constants {
    /**
     * 接口认证参数
     */
    public static final String CHAR_SET_VALUE = "UTF-8";
    public static final String TIME_STAMP = "timestamp";
    public static final String SIGN_TYPE = "signtype";
    public static final String SIGN_TYPE_VALUE = "RSA2";
    public static final String APP_KEY_NAME = "appkey";
    public static final String CHAR_SET = "charset";
    public static final String SIGN = "sign";
    /**
     * 签名有效时间
     */
    public static final long SIGN_TTL = 1000 * 60 * 60 * 8;//8小时
    /**
     * 日期统一格式
     */
    public static final String DATE_UNIFIED_FORMATTER = "yyyy-MM-dd HH:mm:ss";

}
