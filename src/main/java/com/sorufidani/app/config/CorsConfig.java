package com.sorufidani.app.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // API yolunuzun başlangıcı burada belirtildi.
                        .allowedOrigins("http://localhost:3000") // İzin vermek istediğiniz kökenleri buraya ekleyin.
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin vermek istediğiniz HTTP yöntemleri
                        .allowedHeaders("*"); // İzin vermek istediğiniz başlıklar (* tüm başlıkları ifade eder)
            }
        };
    }
}