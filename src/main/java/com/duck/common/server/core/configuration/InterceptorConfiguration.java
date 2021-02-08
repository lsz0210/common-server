package com.duck.common.server.core.configuration;

import com.duck.common.server.core.interceptors.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author lsz
 * @Date 2020-08-12 13:59
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    /**
     * @return
     */
    @Bean
    public HandlerInterceptor JwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.JwtInterceptor())
                .addPathPatterns("/**");
    }

}
