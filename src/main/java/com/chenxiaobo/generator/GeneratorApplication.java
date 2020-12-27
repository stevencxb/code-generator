package com.chenxiaobo.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chenxiaobo.generator.mapper"})
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }

}
