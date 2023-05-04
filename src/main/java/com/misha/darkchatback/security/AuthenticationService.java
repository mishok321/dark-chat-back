package com.misha.darkchatback.security;

import com.misha.darkchatback.exception.AuthenticationException;
import com.misha.darkchatback.model.User;

public interface AuthenticationService {
    User register(String login, String username, String password);

    User login(String login, String password) throws AuthenticationException;
}
