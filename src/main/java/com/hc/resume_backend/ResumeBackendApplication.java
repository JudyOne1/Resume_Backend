package com.hc.resume_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hc.resume_backend.mapper")
public class ResumeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBackendApplication.class, args);
    }

}
