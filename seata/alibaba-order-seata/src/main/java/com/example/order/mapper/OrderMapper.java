package com.example.order.mapper;

import com.example.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}
