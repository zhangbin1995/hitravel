package com.lixiang.hitravel.interceptor;

/**
 * @author binzhang
 * @date 2020-02-08
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("file:D:/temp-rainy/");
        registry.addResourceHandler("/img/**").addResourceLocations("file:/images/");
    }
}

