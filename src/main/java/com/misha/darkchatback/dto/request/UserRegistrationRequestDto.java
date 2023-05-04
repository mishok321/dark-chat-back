package com.misha.darkchatback.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationRequestDto {
    @NotEmpty(message = "The username couldn't be empty")
    @NotNull(message = "The username couldn't be null")
    @Size(min = 1, max = 20, message = "Username should be less than 20 and more than 1 character")
    private String username;
    @NotEmpty(message = "The login couldn't be empty")
    @NotNull(message = "The login couldn't be null")
    @Size(min = 6, max = 20, message = "Login should be less than 20 and more than 6 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*$", message
            = "The login must contain only English letters and numbers")
    private String login;

    @NotEmpty(message = "The password couldn't be empty")
    @NotNull(message = "The password couldn't be null")
    @Size(min = 6, max = 20, message = "Password should be less than 20 and more than 6 characters")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+={}\\[\\]\\\\|:;\\\"'<>,.?/\\s-]*$",
            message = "The password must contain only"
                    + "English letters, numbers and special characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
