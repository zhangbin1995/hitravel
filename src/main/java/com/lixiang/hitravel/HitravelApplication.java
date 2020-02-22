package com.lixiang.hitravel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.lixiang.hitravel.mapper")
public class HitravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitravelApplication.class, args);
    }

}
