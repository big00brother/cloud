package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
public class AlibabaOrderSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaOrderSeataApplication.class,args);
    }

}
