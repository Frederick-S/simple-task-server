package dekiru.simpletask.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The cors config for the app.
 */
@Configuration
public class CorsConfig {
    @Autowired
    private SimpleTaskConfig simpleTaskConfig;

    /**
     * The concrete cors config.
     *
     * @return {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(simpleTaskConfig.getAllowedOrigin())
                        .allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "HEAD")
                        .allowCredentials(true);
            }
        };
    }
}
