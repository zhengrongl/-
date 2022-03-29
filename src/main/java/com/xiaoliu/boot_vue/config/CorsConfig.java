package com.xiaoliu.boot_vue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    //设置当前跨域请求最大有效时间，这里默认为1天
    private static final long MAX_AGE=24 * 60 * 60;


    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");//1.设置访问源地址
        corsConfiguration.addAllowedHeader("*");//2.设置访问源请求
        corsConfiguration.addAllowedMethod("*");//3.设置源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);//4.对接口配置跨域设置
        return new CorsFilter(source);
    }
}
