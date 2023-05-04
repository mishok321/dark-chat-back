package com.misha.darkchatback.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserLoginRequestDto {
    @NotEmpty(message = "The login couldn't be empty")
    @NotNull(message = "The login couldn't be null")
    private String login;

    @NotEmpty(message = "The password couldn't be empty")
    @NotNull(message = "The password couldn't be null")
    private String password;

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
