package com.example.wetube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WeTubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeTubeApplication.class, args);
    }

}
