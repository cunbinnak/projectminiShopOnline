package com.example.shopingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = { "com.example.shopingcart" })

public class ShopingcartApplication  {





    public static void main(String[] args) {
        SpringApplication.run(ShopingcartApplication.class, args);
    }


    @Bean// để angular gọi đc ko bị trùng cổng
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST","PUT","DELETE","PATCH","OPTIONS")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }
}
