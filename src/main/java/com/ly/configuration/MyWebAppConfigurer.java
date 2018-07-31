package com.ly.configuration;

/**
 * Created by zhongwei on 30/03/2017.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        registry.addInterceptor( webInterceptor() ).addPathPatterns( "/**" )
                .excludePathPatterns( "/v1/login/**" )
                .excludePathPatterns( "/defaultKaptcha" )
                .excludePathPatterns( "/v1/register/**" );
        super.addInterceptors( registry );
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "swagger-ui.html" )
                .addResourceLocations( "classpath:/META-INF/resources/" );

        registry.addResourceHandler( "/webjars/**" )
                .addResourceLocations( "classpath:/META-INF/resources/webjars/" );
    }

}
