package com.goodguy.blog.config;

import com.goodguy.blog.interceptor.AuthorityInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;

@SpringBootConfiguration
public class WebConfigurer implements WebMvcConfigurer {

    @Bean
    public AuthorityInterceptor getLoginInterceptor() {
        return new AuthorityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/js/**","/css/**","/img/**","/fonts/**","/favicon.ico");
                //vue单页面应用,所有路径应用拦截器，除了 /index.html
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        File upload = new File(path,"static/images/upload/");
        if(!upload.exists())
            upload.mkdirs();
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + upload.getAbsolutePath()+'/');
    }

}
