package com.misha.darkchatback.security;

import com.misha.darkchatback.exception.AuthenticationException;
import com.misha.darkchatback.model.User;
import com.misha.darkchatback.service.UserService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String login, String username, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setUsername(username);
        user = userService.add(user);
        return user;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByLogin(login);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password");
        }
        return user.get();
    }
}
