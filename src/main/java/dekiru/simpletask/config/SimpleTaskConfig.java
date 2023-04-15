package dekiru.simpletask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The root config of the SimpleTask app,
 * notice that some configs are extracted as standalone class for convenience.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "simpletask")
public class SimpleTaskConfig {
    private int passwordStrength;

    private String allowedOrigin;

    public int getPasswordStrength() {
        return passwordStrength;
    }

    public void setPasswordStrength(int passwordStrength) {
        this.passwordStrength = passwordStrength;
    }

    public String getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }
}
