package com.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.service.OrderService;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //RestTemplate   --  http
    @RequestMapping("query")
    public Object query(String customerId, int pageNum, int pageSize) {
        return this.orderService.pageQuery(customerId, pageNum, pageSize);
    }
}
