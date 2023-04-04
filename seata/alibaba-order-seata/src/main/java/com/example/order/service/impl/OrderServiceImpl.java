package com.example.order.service.impl;

import com.example.order.api.StockService;
import com.example.order.mapper.OrderMapper;
import com.example.order.pojo.Order;
import com.example.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockService stockService;

    /**
     * 下单
     * @return
     */
    @GlobalTransactional
    public Order create(Order order) {
        // 插入能否成功？
        orderMapper.insert(order);

        String msg = stockService.reduce(order.getProductId());

        // 异常
        int a=1/0;

        return order;
    }

    @Trace
    @Tag(key="getAll",value="returnedObj")
    public List<Order> all() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return orderMapper.selectAll();
    }

    @Trace
    @Tags({@Tag(key="getId",value="returnedObj"),
            @Tag(key="id",value="arg[0]")})
    public Order get(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}
