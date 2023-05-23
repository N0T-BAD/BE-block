package com.blockpage.blockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class BlockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockServiceApplication.class, args);
    }

}