package com.passingtest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                // Request URLs
                .addResourceHandler("/**" /*, ... */)
                // Path inside application (refers to src/main/resources directory)
                // TRAILING SLASH IS IMPORTANT
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix("/api",
                HandlerTypePredicate.forBasePackage("com.passingtest.controller")
                        .and(HandlerTypePredicate.forAnnotation(RestController.class)));
    }

}
