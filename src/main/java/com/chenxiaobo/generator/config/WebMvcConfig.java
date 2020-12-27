package com.chenxiaobo.generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: WebMvcConfig
 * @Description: WebMvcConfig
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-05-13 11:13
 * @Version V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源映射
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}
