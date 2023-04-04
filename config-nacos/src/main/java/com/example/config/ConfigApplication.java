package com.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigApplication.class);
        while(true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String userSex = applicationContext.getEnvironment().getProperty("user.sex");
            System.out.println("user name :"+userName+"; age: "+userAge + "; sex: " + userSex);
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
