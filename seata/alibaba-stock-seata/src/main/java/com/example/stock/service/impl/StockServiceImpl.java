package com.example.stock.service.impl;

import com.example.stock.service.StockService;
import com.example.stock.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;

    public void reduce(Integer productId) {
        System.out.println("更新商品:"+productId);
        stockMapper.reduct(productId);
        System.out.println("更新商品end:"+productId);
    }
}
