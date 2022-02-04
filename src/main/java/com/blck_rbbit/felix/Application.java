package com.blck_rbbit.felix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        ApiContextInitializer.init(); //?
        SpringApplication.run(Application.class, args);
    }
}
