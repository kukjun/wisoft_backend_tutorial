package io.wisoft.tutorial_backend.config;

import io.wisoft.tutorial_backend.interceptor.AdminVerifyInterceptor;
import io.wisoft.tutorial_backend.interceptor.VerifyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String url;
    private final AdminVerifyInterceptor adminVerifyInterceptor;
    private final VerifyInterceptor verifyInterceptor;

    public WebMvcConfig(
            @Value("${frontend-url}") String url,
            AdminVerifyInterceptor adminVerifyInterceptor,
            VerifyInterceptor verifyInterceptor
    ) {
        this.url = url;
        this.adminVerifyInterceptor = adminVerifyInterceptor;
        this.verifyInterceptor = verifyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminVerifyInterceptor)
                .addPathPatterns("/api/lectures")
                .addPathPatterns("/api/lectures/**");
        registry.addInterceptor(verifyInterceptor)
                .addPathPatterns("/api/posts/**")
                .addPathPatterns("/api/comments/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(url)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }
}
