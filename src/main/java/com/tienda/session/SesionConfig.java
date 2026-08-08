package com.tienda.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class SesionConfig implements WebMvcConfigurer {

    @Autowired
    private SesionInterceptor sesionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sesionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/login",
                    "/",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/webjars/**"
                );
    }
}