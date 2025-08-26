// src/main/java/com/hongchelin/community/config/WebMvcConfig.java
package com.hongchelin.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig_c implements WebMvcConfigurer {

    // 업로드 폴더 경로(없으면 기본값 ./uploads 사용) — 빈 생성 시 프로퍼티 없어서 터지는 걸 방지
    @Value("${app.upload-dir:./uploads}")
    private String uploadDir;

    // CORS: 프론트 로컬(5173)에서 /api/** 접근 허용
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")   // 필요시 도메인 추가
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Location")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 정적 리소스 매핑: 업로드된 파일을 /uploads/** 로 서빙
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String location = uploadPath.toUri().toString(); // 예: file:/C:/.../uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location)
                .setCachePeriod(3600);
    }
}
