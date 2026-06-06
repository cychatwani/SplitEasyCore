package com.splitEasy.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
    public class CoreApplication {

    public static void main(String[] args) {
        System.out.println("Spring app started now ... ");
        SpringApplication.run(CoreApplication.class, args);
    }


}

