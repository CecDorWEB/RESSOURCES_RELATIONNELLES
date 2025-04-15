package com.RESSOURCES_RELATIONNELLES.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                //.addPathPatterns("/**") // TODO Enable this if u want to block by login
                .excludePathPatterns(
                    "/signup",  // Autorise inscription
                    "/login", // Autorise connexion
                    "/notAccess", // Autorise notaccess
                    "/", "/home", // Autorise home

                    "/css/**", // Autorise fichiers css
                    "/js/**", // Autorise fichiers js
                    "/img/**", // Autorise images
                    "/favicon.ico",

                    "/**" //TODO Disable this if u want to block by login
                );
    }
}
