package com.example.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/{id}")
    public String get(@PathVariable Integer id) throws InterruptedException {
//        Thread.sleep(4000);
        System.out.println("查询商品" + id);
        return "查询商品" + id + ": " + port;
    }

}
