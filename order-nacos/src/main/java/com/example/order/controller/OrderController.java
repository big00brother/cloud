package com.example.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public String add() {
        System.out.println("下单成功");
        String msg = restTemplate.postForObject("http://stock-service/stock/reduce", null, String.class);
        return "下单成功" + " " + msg;
    }

    @RequestMapping("/header")
    public String header(@RequestHeader("X-Request-color") String color){
        return color;
    }

    @RequestMapping("/flow")
    //@SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String flow() throws InterruptedException {
        return "正常访问";
    }



    @RequestMapping("/flowThread")
    //@SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问");
        return "正常访问";
    }


    // 关联流控  访问/add 触发/get
    @RequestMapping("/get")
    public String get() throws InterruptedException {
        return "查询订单";
    }



    @RequestMapping("/err")
    public String err()  {
        int a=1/0;
        return "hello";
    }


    /**
     * 热点规则，必须使用@SentinelResource
     * @param id
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/get/{id}")
    public String getById(@PathVariable("id") Integer id) throws InterruptedException {

        System.out.println("正常访问");
        return "正常访问";
    }

}
