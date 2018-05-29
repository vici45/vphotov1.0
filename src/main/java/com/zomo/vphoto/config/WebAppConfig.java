package com.zomo.vphoto.config;

import com.zomo.vphoto.common.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public AuthorityInterceptor getAuthorityInterceptor(){
        return new AuthorityInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorityInterceptor()).addPathPatterns("/**").excludePathPatterns("/login","/login.do","/success","/error");
        super.addInterceptors(registry);
    }
}
