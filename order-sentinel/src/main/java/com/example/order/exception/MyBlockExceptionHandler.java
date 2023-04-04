package com.example.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.example.domain.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("BlockExceptionHandler BlockException================"+e.getRule());

        Result r = null;

        if (e instanceof FlowException) {
            r = Result.error(100,"接口限流了");

        } else if (e instanceof DegradeException) {
            r = Result.error(101,"服务降级了");

        } else if (e instanceof ParamFlowException) {
            r = Result.error(102,"热点参数限流了");

        } else if (e instanceof SystemBlockException) {
            r = Result.error(103,"触发系统保护规则了");

        } else if (e instanceof AuthorityException) {
            r = Result.error(104,"授权规则不通过");
        }

        //返回json数据
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);
    }
}
