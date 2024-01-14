package Honzapda.Honzapda_server;

import Honzapda.Honzapda_server.intercepter.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/checkId",
                        "/auth/findId",
                        "/auth/findPassword",
                        "/auth/register",
                        "/auth/login",
                        "/swagger-ui/**",
                        "/api-docs/**",
                        "/v3/api-docs/**");
    }

}