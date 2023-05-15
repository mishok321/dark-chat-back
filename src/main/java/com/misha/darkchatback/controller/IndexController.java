package com.misha.darkchatback.controller;

import com.misha.darkchatback.model.User;
import com.misha.darkchatback.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class IndexController {

    public static final String INDEX_HTML = "index.html";
    public static final String USERNAME_FIELD_NAME = "username";
    public static final String ERRORS_FIELD = "errors";
    public static final List<String> ERRORS = List.of("Can't fetch current user");
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index() {
        return INDEX_HTML;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> user() {
        org.springframework.security.core.userdetails.User userDetails
                = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userService.findByLogin(userDetails.getUsername());
        User user = userOptional.get();
        Map<String, Object> body = new HashMap<>();
        body.put(USERNAME_FIELD_NAME, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
