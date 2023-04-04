package com.example.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Value("${server.port}")
    private String port;

    @PostMapping("/reduce")
    public String reduce() {
        System.out.println("扣减库存");
        return "扣减库存" + port;
    }

    @PostMapping("/reduce2")
    public String reduce2() {
        int a = 1 / 0;
        System.out.println("扣减库存");
        return "扣减库存" + port;
    }

}
