package com.example.demo.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class Category {
    Long id;
    String name;

}
