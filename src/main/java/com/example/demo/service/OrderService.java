package com.example.demo.service;

import com.example.demo.dto.ProductReportDTO;
import com.example.demo.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<ProductReportDTO> getMostPopularProducts(String startDate, String endDate) {
        return orderRepository.findMostPopularProducts(startDate, endDate);
    }
}
