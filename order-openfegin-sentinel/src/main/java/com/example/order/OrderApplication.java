package com.example.order;

import com.example.ribbon.RibbonRandomRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RibbonClients(value = {
        @RibbonClient(name = "stock-service", configuration = RibbonRandomRuleConfig.class)
})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
