package com.example.demo.service;

import com.example.demo.annotation.FeatureToggle;
import org.springframework.stereotype.Service;

@Service
public class CosmoCatService {
    @FeatureToggle
    public String getCosmoCats() {
        return "Here are the cosmic cats!";
    }
}
