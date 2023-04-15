package dekiru.simpletask.config;

import dekiru.simpletask.interceptor.AuthenticationInterceptor;
import dekiru.simpletask.support.CurrentUserHandlerMethodArgumentResolver;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Some custom configs for Spring Web Mvc.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        registry.addInterceptor(authenticationInterceptor);
    }

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);

        resolvers.add(currentUserHandlerMethodArgumentResolver);
    }
}
