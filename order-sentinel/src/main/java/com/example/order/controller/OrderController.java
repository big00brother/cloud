package com.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/add")
    public String add() {
        System.out.println("下单成功");
        return "下单成功";
    }

    @GetMapping("/get")
    public String get() {
        System.out.println("查询订单");
        return "查询订单";
    }


    @GetMapping("/flow")
//    @SentinelResource(value = "flow", blockHandler = "flowBlockHandler")
    public String flow() {
        return "正常访问";
    }

    @GetMapping("/flowThread")
    @SentinelResource(value = "flowThread", blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "正常访问";
    }

    public String flowBlockHandler(BlockException e) {
        return "流控";
    }

    @Autowired
    private IOrderService orderService;

    // 关联流控   访问/add 触发/get
    @RequestMapping("/test1")
    public String test1(){
        return orderService.getUser();
    }
    // 关联流控  访问/add 触发/get
    @RequestMapping("/test2")
    public String test2() throws InterruptedException {
        return orderService.getUser();
    }

    @RequestMapping("/err")
    public String err() {
        int a = 1 / 0;
        return "hello";
    }

    /**
     * 热点规则，必须使用@SentinelResource
     * @param id
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/get/{id}")
    @SentinelResource(value = "getById",blockHandler = "HotBlockHandler")
    public String getById(@PathVariable("id") Integer id) throws InterruptedException {

        System.out.println("正常访问");
        return "正常访问";
    }

    public String HotBlockHandler(@PathVariable("id") Integer id,BlockException e) throws InterruptedException {

        return "热点异常处理";
    }

}
