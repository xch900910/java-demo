package com.example.workNote;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-19 15:18
 **/
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;

    public MyWebMvcConfigurer(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
    }

}
