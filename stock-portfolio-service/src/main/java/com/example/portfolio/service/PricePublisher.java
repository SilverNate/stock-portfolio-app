package com.example.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricePublisher {
    private static final String TOPIC = "stock-price-updates";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishPriceUpdate(String symbol, BigDecimal price) {
        String message = symbol + ":" + price;
        kafkaTemplate.send(TOPIC, message);
    }
}
