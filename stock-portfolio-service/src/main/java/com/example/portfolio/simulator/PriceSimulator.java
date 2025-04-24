package com.example.portfolio.simulator;

import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import com.example.portfolio.service.PriceListener;
import com.example.portfolio.service.PricePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Random;

@Component
public class PriceSimulator {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PricePublisher publisher;

    private final Random random = new Random();

    @Scheduled(fixedRate = 10000) // every 10s
    public void simulatePriceChange() {
        List<Stock> stocks = stockRepository.findAll();

        for (Stock stock : stocks) {
            BigDecimal current = stock.getCurrentPrice();
            BigDecimal change = BigDecimal.valueOf(random.nextDouble() * 5 - 2.5); // ±2.5
            BigDecimal updated = current.add(change).setScale(2, RoundingMode.HALF_UP);
            stock.setCurrentPrice(updated);
            stockRepository.save(stock);

            String symbol = stock.getSymbol().toUpperCase();
            redisTemplate.opsForValue()
                    .set("stock:price:" + symbol, updated.toPlainString(), Duration.ofMinutes(5));

            publisher.publishPriceUpdate(symbol, updated);

            log.trace("💹 Simulated: {}", symbol + " → $" + updated);
        }
    }
}
