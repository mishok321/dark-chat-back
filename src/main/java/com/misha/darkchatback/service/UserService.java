package com.misha.darkchatback.service;

import com.misha.darkchatback.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    Optional<User> findByLogin(String login);
}
