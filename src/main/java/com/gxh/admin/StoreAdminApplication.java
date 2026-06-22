package com.gxh.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreAdminApplication {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.specification.version"));
        SpringApplication.run(StoreAdminApplication.class, args);
    }

}
