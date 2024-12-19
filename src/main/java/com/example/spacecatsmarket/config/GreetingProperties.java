package com.example.spacecatsmarket.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application")
public class GreetingProperties {

    Map<String, CatGreeting> greetings;


    @Data
    @NoArgsConstructor
    public static class CatGreeting {

        String name;
        String message;

    }

}
