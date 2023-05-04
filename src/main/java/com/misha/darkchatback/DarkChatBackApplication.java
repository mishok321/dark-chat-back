package com.misha.darkchatback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DarkChatBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkChatBackApplication.class, args);
    }

}
