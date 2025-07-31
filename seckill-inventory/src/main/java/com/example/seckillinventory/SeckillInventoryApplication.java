package com.example.seckillinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SeckillInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillInventoryApplication.class, args);
    }

}