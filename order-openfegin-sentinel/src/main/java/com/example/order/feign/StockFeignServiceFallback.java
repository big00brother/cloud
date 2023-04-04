package com.example.order.feign;

import org.springframework.stereotype.Component;

@Component
public class StockFeignServiceFallback implements StockFeignService {
    public String reduce2() {
        return "降级啦！！！";
    }
}
