package io.wisoft.tutorial_backend.config;

import io.wisoft.tutorial_backend.interceptor.JwtTokenVerifyInterceptor;
import lombok.RequiredArgsConstructor;
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
    private final JwtTokenVerifyInterceptor jwtTokenVerifyInterceptor;

    public WebMvcConfig(
            @Value("${frontend-url}") String url,
            JwtTokenVerifyInterceptor jwtTokenVerifyInterceptor
    ) {
        this.url = url;
        this.jwtTokenVerifyInterceptor = jwtTokenVerifyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenVerifyInterceptor)
                .addPathPatterns("/api/auth/**");
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
