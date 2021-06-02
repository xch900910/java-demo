package com.example.day.workFast;

import com.alibaba.fastjson.JSON;

import com.example.day.workFast.util.SignUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

import static com.example.day.workFast.Constants.*;
import static com.example.day.workFast.util.CommonUtil.sort;
import static com.example.day.workFast.util.SignUtil.decodeBase64;
import static com.example.day.workFast.util.SignUtil.getPublicKey;


/**
 * @author: xiongchaohua
 * @Des : 接口统一认证入口
 * @create: 2021-05-19 15:19
 **/
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value("${auth.enable:false}")
    private boolean authEnable;
    @Value("${appKey:test}")
    private String appKey;
    @Value("${publicKey:test}")
    private String publicKey;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //全局控制
        if (!authEnable) {
            return true;
        }
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有PassToken注释，有则跳过认证
        if (method.isAnnotationPresent(SkipAuthVerify.class)) {
            SkipAuthVerify skipAuthVerify = method.getAnnotation(SkipAuthVerify.class);
            if (skipAuthVerify.required()) {
                return true;
            }
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        boolean signResult = verifyApiSign(headerMap, appKey, publicKey);
        if (!signResult) {
            Result result = new Result();
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(result));
        }
        return signResult;
    }

    /**
     * 验证签名算法
     * 将请求头参数拼接，appKey=zt001&charset=UTF-8&sign_type=RSA2&timeStamp=1589279314 （参数按照字典顺序固定）
     * 使用公钥验签
     *
     * @param params
     * @param appKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    private boolean verifyApiSign(Map<String, String> params, String appKey, String publicKey) throws Exception {
        // 1. 验证参数
        if (null == params || params.isEmpty() || Strings.isNullOrEmpty(appKey) || Strings.isNullOrEmpty(publicKey)) {
            return false;
        }
        //第三方传入的签名
        String sign = null != params.get(SIGN) ? params.get(SIGN) : "";
        //2. 验证是否缺少验签所需参数
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        //缺少随机时间戳参数
        if (!keys.contains(TIME_STAMP)) {
            return false;
        }
        //判断时间戳
        if (System.currentTimeMillis() - Long.parseLong(params.get(TIME_STAMP)) >= SIGN_TTL
                || Long.parseLong(params.get(TIME_STAMP)) > System.currentTimeMillis()) {
            return false;
        }
        //循环校验参数的值是否为空
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            Object paramValue = params.get(paramName);
            if ("".equals(paramName) && null == paramValue) {
                return false;
            }
        }
        //nginx默认忽略带下划线参数
        params.putIfAbsent(SIGN_TYPE, SIGN_TYPE_VALUE);
        String[] signatureParamsKeys = new String[]{APP_KEY_NAME, CHAR_SET, SIGN_TYPE, TIME_STAMP};
        // 3.对参数进行排序
        sort(signatureParamsKeys);

        //组装参与签名生成的字符串(使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串)
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < signatureParamsKeys.length; i++) {
            //拼接随机字符串、时间戳参数
            String paramValue = null != params.get(signatureParamsKeys[i]) ? params.get(signatureParamsKeys[i]) : "";
            string.append(signatureParamsKeys[i]).append("=").append(paramValue);

            if (i != signatureParamsKeys.length - 1) {
                string.append("&");
            }
        }
        return SignUtil.verify256(string.toString(), decodeBase64(sign.getBytes()), getPublicKey(publicKey));
    }

}
