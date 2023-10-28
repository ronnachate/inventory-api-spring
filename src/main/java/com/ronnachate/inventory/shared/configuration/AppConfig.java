package com.ronnachate.inventory.shared.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ronnachate.inventory.shared.interceptor.RequestHeaderInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private ConfigurableEnvironment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHeaderInterceptor(env))
        .addPathPatterns("/**")
        .excludePathPatterns("/swagger-ui/**")
        .excludePathPatterns("/v3/api-docs/**");
    }
}
