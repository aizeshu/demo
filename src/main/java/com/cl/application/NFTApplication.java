package com.cl.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.cl"})
@MapperScan(value = "com.cl.mapper")
public class NFTApplication {
    public static void main(String[] args) {
        SpringApplication.run(NFTApplication.class, args);
    }


}
