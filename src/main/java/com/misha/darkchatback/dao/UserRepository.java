package com.misha.darkchatback.dao;

import com.misha.darkchatback.model.User;
import java.util.Optional;

public interface UserRepository {
    User add(User user);

    Optional<User> findByLogin(String login);
}
