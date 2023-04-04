package com.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/product")
public interface ProductFeignService {

    @GetMapping("/{id}")  //@RequestLine
    String get(@PathVariable("id") Integer id);  //@Param
}
