package dekiru.simpletask.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * The request entity to login.
 */
public class UserLoginRequest {
    @NotBlank(message = "name could not be empty")
    private String name;

    @NotBlank(message = "password could not be empty")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
