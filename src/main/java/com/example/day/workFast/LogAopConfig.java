package com.example.day.workFast;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.example.day.workFast.Constants.DATE_UNIFIED_FORMATTER;


/**
 * @author: xiongchaohua
 * @Des : controller统一日志处理
 * @create: 2021-05-20 14:12
 **/
@Aspect
@Slf4j
@Component
public class LogAopConfig {
    @Pointcut("execution(public * com.*.base.controller.*Controller.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();//1、开始时间
        //利用RequestContextHolder获取request对象
        ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String uri = requestAttr.getRequest().getRequestURI();
        log.info("接口开始时间: {},  URI: {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_UNIFIED_FORMATTER)), uri);
        //访问目标方法的参数 可动态改变参数值
        Object[] args = joinPoint.getArgs();
        //方法名获取
        String methodName = joinPoint.getSignature().getName();
        log.info("请求方法：{}, 请求参数: {}", methodName, Arrays.toString(args));
        //可能在反向代理请求进来时，获取的IP存在不正确行 这里直接摘抄一段来自网上获取ip的代码
        log.info("请求ip：{}", getIpAddr(requestAttr.getRequest()));

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("暂不支持非方法注解");
        }
        //调用实际方法
        Object object = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("接口结束时间: {},  URI: {},耗时：{}ms", LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_UNIFIED_FORMATTER))
                , uri, endTime - beginTime);
        return object;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("获取ip异常：{}", e.getMessage());
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }
}
