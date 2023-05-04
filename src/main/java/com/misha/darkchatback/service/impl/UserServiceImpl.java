package com.misha.darkchatback.service.impl;

import com.misha.darkchatback.dao.UserRepository;
import com.misha.darkchatback.model.User;
import com.misha.darkchatback.service.UserService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.add(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
