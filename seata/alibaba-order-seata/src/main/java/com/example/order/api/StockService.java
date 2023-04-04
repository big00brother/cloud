package com.example.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="alibaba-stock-seata",path = "/stock")
public interface StockService {

    @RequestMapping("/reduce")
    public String reduce(@RequestParam(value="productId") Integer productId);
}
