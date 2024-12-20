package com.example.demo.controller;

import com.example.demo.dto.ProductReportDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/report/popular-products")
    public List<ProductReportDTO> getPopularProducts(@RequestParam String startDate, @RequestParam String endDate) {
        return orderService.getMostPopularProducts(startDate, endDate);
    }
}
