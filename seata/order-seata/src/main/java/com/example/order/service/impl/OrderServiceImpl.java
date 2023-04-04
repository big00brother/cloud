package com.example.order.service.impl;

import com.example.order.mapper.OrderMapper;
import com.example.order.pojo.Order;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 下单
     * @return
     */
    @Transactional
    public Order create(Order order) {
        orderMapper.selectByPrimaryKey(1);
        // 插入能否成功？
        orderMapper.insert(order);


        // 扣减库存 能否成功？
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("productId", order.getProductId());

        String msg = restTemplate.postForObject("http://localhost:8071/stock/reduce", paramMap,String.class );

        // 异常
        int a=1/0;

        return order;
    }
}
