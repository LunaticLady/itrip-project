package com.cskt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cskt.mapper")
public class ItripDaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItripDaoApplication.class, args);
    }
}