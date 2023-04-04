package com.example.stock.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.stock.mapper")
public class MyBatisConfig {
}
