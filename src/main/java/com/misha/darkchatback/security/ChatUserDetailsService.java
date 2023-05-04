package com.misha.darkchatback.security;

import com.misha.darkchatback.model.User;
import com.misha.darkchatback.service.UserService;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChatUserDetailsService implements UserDetailsService {
    public static final String ROLE_USER = "USER";
    private final UserService userService;

    public ChatUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByLogin(login);
        if (userOptional.isPresent()) {
            return org.springframework.security.core.userdetails.User.withUsername(login)
                    .password(userOptional.get().getPassword())
                    .roles(ROLE_USER)
                    .build();
        }
        throw new UsernameNotFoundException("User not found by login: " + login);
    }
}
