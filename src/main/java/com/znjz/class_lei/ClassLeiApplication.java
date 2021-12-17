package com.znjz.class_lei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.znjz.class_lei")
public class ClassLeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassLeiApplication.class, args);
    }

}
