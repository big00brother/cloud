package com.example.order.feign;

import com.example.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


/*
 * name 指定调用rest接口所对应的服务名
 * path 指定调用rest接口所在的StockController指定的RequestMapping
 */
@FeignClient(name = "stock-service", path = "/stock", configuration = FeignConfig.class)
public interface StockFeignService {

    //声明需要调用的rest接口对应的方法
    @PostMapping("/reduce")
    String reduce();
}


/*
@RestController
@RequestMapping("/stock")
public class StockController {

    @PostMapping("/reduce")
    public String reduce() {
        System.out.println("扣减库存");
        return "扣减库存";
    }

}
*/
