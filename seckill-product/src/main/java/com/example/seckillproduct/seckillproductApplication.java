package com.example.seckillproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.example.seckillproduct.mapper") // 扫描Mapper包
@EnableDiscoveryClient
public class seckillproductApplication {
    public static void main(String[] args) {
        SpringApplication.run(seckillproductApplication.class, args);
    }
}
