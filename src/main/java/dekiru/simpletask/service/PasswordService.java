package dekiru.simpletask.service;

import dekiru.simpletask.config.SimpleTaskConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * The service that manages user's password.
 */
@Service
public class PasswordService {
    @Autowired
    private SimpleTaskConfig simpleTaskConfig;

    /**
     * Check if the raw password matches the encrypted password.
     *
     * @param rawPassword       The raw password
     * @param encryptedPassword The encrypted password
     * @return True if matches, false otherwise
     */
    public boolean matches(String rawPassword, String encryptedPassword) {
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

        return cryptPasswordEncoder.matches(rawPassword, encryptedPassword);
    }

    /**
     * Encrypt password.
     *
     * @param password The password to encrypt
     * @return Encrypted password
     */
    public String encrypt(String password) {
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder(
                simpleTaskConfig.getPasswordStrength(), new SecureRandom());

        return cryptPasswordEncoder.encode(password);
    }
}
