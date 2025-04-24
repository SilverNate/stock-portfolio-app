package com.example.portfolio.service;

import com.example.portfolio.model.AlertHistory;
import com.example.portfolio.model.AlertRule;
import com.example.portfolio.model.StockPriceHistory;
import com.example.portfolio.repository.StockPriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceListener {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StockPriceHistoryRepository historyRepository;

    private AlertHistoryService alertHistoryService;
    private final AlertRuleService alertRuleService;

    public PriceListener(AlertRuleService alertRuleService, AlertHistoryService alertHistoryService) {
        this.alertRuleService = alertRuleService;
        this.alertHistoryService = alertHistoryService;
    }

    @KafkaListener(topics = "stock-price-updates", groupId = "stock-price-consumers")
    public void listen(String message) {
        try {
            String[] parts = message.split(":");
            String symbol = parts[0].trim().toUpperCase();
            BigDecimal price = new BigDecimal(parts[1].trim());

            List<AlertRule> rules =
                    alertRuleService.findBySymbol(symbol);

            for (var rule : rules) {
                boolean triggered = false;
                String condition = null;
                Double threshold = null;

                if (rule.getPriceAbove() != null && price.doubleValue() > rule.getPriceAbove()) {
                    triggered = true;
                    condition = "ABOVE";
                    threshold = rule.getPriceAbove();
                } else if (rule.getPriceBelow() != null && price.doubleValue() < rule.getPriceBelow()) {
                    triggered = true;
                    condition = "BELOW";
                    threshold = rule.getPriceBelow();
                }
                if (triggered) {
                    log.info("🔔 ALERT TRIGGERED: {} is {} ({} {})", symbol, price.doubleValue(), condition, threshold);

                    alertHistoryService.log(AlertHistory.builder()
                            .symbol(symbol)
                            .price(price.doubleValue())
                            .condition(condition)
                            .threshold(threshold)
                            .triggeredAt(LocalDateTime.now())
                            .build()
                    );

                    log.trace("Success save alert history");
                }
            }

            String redisKey = "stock:price:" + symbol;
            redisTemplate.opsForValue().set(redisKey, price.toString(), Duration.ofMinutes(5));

            log.trace("Success save data redis: {}", redisKey);

            System.out.println("📨 Received Kafka price update: " + price);

            StockPriceHistory history = new StockPriceHistory(
                    symbol,
                    price,
                    LocalDateTime.now()
            );
            historyRepository.save(history);

        } catch (Exception e) {
            log.error("Error parsing Kafka message: {}", message, e);
        }

    }
}

