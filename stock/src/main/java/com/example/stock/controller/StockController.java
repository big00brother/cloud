package com.example.stock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @PostMapping("/reduce")
    public String reduce() {
        System.out.println("扣减库存");
        return "扣减库存";
    }

}
