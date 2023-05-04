package com.misha.darkchatback.controller;

import com.misha.darkchatback.dto.request.UserLoginRequestDto;
import com.misha.darkchatback.dto.request.UserRegistrationRequestDto;
import com.misha.darkchatback.exception.AuthenticationException;
import com.misha.darkchatback.model.User;
import com.misha.darkchatback.security.AuthenticationService;
import com.misha.darkchatback.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    public static final String TOKEN_FIELD_NAME = "token";
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public void register(
            @RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto
    ) {
        authenticationService.register(userRegistrationRequestDto.getLogin(),
                userRegistrationRequestDto.getUsername(),
                userRegistrationRequestDto.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginRequestDto.getLogin(),
                userLoginRequestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getLogin());
        return new ResponseEntity<>(Map.of(TOKEN_FIELD_NAME, token), HttpStatus.OK);
    }
}
