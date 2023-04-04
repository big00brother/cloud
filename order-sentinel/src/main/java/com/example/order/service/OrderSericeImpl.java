package com.example.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class OrderSericeImpl implements IOrderService {
    @SentinelResource(value="getUser",blockHandler = "blockHandlerGetUser")
    public String getUser() {
        return "查询用户";
    }

    public String blockHandlerGetUser(BlockException e) {
        return "流控用户";
    }
}
