package com.example.order.controller;

import com.example.order.feign.ProductFeignService;
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
    @Autowired
    private ProductFeignService productFeignService;

    @PostMapping("/add")
    public String add() {
        System.out.println("下单成功");
        String msg = stockFeignService.reduce();

        String productMsg = productFeignService.get(1);
        return "下单成功" + " " + msg + " " + productMsg;
    }

}
