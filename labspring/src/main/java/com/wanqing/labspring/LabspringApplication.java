package com.wanqing.labspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = {"com.wanqing"})
@SpringBootApplication
public class LabspringApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabspringApplication.class, args);
    }

}
