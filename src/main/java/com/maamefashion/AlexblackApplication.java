package com.maamefashion;

import com.maamefashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlexblackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AlexblackApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.initAdminUser();
    }
}
