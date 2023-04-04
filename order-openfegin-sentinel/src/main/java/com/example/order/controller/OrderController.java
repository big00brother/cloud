package com.example.order.controller;

import com.example.order.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private StockFeignService stockFeignService;

    @PostMapping("/add")
    public String add() {
        System.out.println("下单成功");
        String s = stockFeignService.reduce2();
        return "下单成功" + " " + s;
    }

}
