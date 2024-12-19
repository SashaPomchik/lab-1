package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class ProductReportDTO {
    String productName;
    Long totalQuantity;
}
