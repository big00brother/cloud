package com.example.order.service;

import com.example.order.pojo.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> all() throws InterruptedException;

    Order get(Integer id);
}
