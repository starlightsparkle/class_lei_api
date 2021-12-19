package com.znjz.class_lei;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan("com.znjz.class_lei")
@MapperScan("com.znjz.class_lei.mapper")
@EnableSwagger2
public class ClassLeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassLeiApplication.class, args);
    }

}
