package com.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "stock-service", path = "/stock", fallback = StockFeignServiceFallback.class)
public interface StockFeignService {
    @PostMapping("/reduce2")
    String reduce2();
}
