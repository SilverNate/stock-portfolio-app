package com.example.portfolio.service;

import com.example.portfolio.dto.StockRealtimeDTO;
import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.portfolio.dto.PortfolioSummary;
import com.example.portfolio.dto.PortfolioSummary.StockSummary;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Service
public class StockService {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final StockRepository repository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PricePublisher publisher;

    public StockService(StockRepository repository, RedisTemplate<String, String> redisTemplate, PricePublisher publisher) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
        this.publisher = publisher;
    }

    public List<Stock> getAllStocks() {
        return repository.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        return repository.findById(id);
    }

    public Stock saveStock(Stock stock) {
        return repository.save(stock);
    }

    public void deleteStock(Long id) {
        repository.deleteById(id);
    }

    public PortfolioSummary getPortfolioSummary() {
        List<Stock> stocks = repository.findAll();

        List<StockSummary> summaries = stocks.stream().map(stock -> {
            BigDecimal investment = stock.getAveragePrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
            BigDecimal currentValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
            BigDecimal gainLoss = currentValue.subtract(investment);

            StockSummary summary = new StockSummary();
            summary.setSymbol(stock.getSymbol());
            summary.setInvestment(investment);
            summary.setCurrentValue(currentValue);
            summary.setGainLoss(gainLoss);
            return summary;
        }).collect(Collectors.toList());

        BigDecimal totalInvestment = summaries.stream()
                .map(StockSummary::getInvestment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCurrentValue = summaries.stream()
                .map(StockSummary::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGainLoss = summaries.stream()
                .map(StockSummary::getGainLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PortfolioSummary portfolioSummary = new PortfolioSummary();
        portfolioSummary.setStockSummaries(summaries);
        portfolioSummary.setTotalInvestment(totalInvestment);
        portfolioSummary.setTotalCurrentValue(totalCurrentValue);
        portfolioSummary.setTotalGainLoss(totalGainLoss);

        return portfolioSummary;
    }

    public BigDecimal getCurrentPrice(String symbol) {
        String redisKey = "stock:price:" + symbol.toUpperCase();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        log.trace("redisKey: {}", redisKey);

        String cachedPrice = ops.get(redisKey);
        System.out.println("value in redis: " + cachedPrice);

        if (cachedPrice != null) {
            return new BigDecimal(cachedPrice);
        }


        Stock stock = repository.findBySymbol(symbol.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        log.trace("get stock from repository: {}", stock);

        BigDecimal currentPrice = stock.getCurrentPrice();

        log.trace("set redis key within 5 minutes");

        ops.set(redisKey, currentPrice.toPlainString(), Duration.ofMinutes(5));

        return currentPrice;
    }

    public void updateStockPrice(String symbol, BigDecimal newPrice) {
        Stock stock = repository.findBySymbol(symbol.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        stock.setCurrentPrice(newPrice);
        repository.save(stock);

        log.trace("update stock price: {}", newPrice);

        String redisKey = "stock:price:" + symbol.toUpperCase();
        redisTemplate.opsForValue().set(redisKey, newPrice.toPlainString(), Duration.ofMinutes(5));

        publisher.publishPriceUpdate(symbol, newPrice);
    }

    public List<StockRealtimeDTO> getAllStocksRealtime() {
        List<Stock> stocks = repository.findAll();
        List<StockRealtimeDTO> result = new ArrayList<>();

        for (Stock stock : stocks) {
            String redisKey = "stock:price:" + stock.getSymbol().toUpperCase();
            String priceStr = redisTemplate.opsForValue().get(redisKey);
            BigDecimal price = priceStr != null
                    ? new BigDecimal(priceStr)
                    : stock.getCurrentPrice(); // fallback

            result.add(new StockRealtimeDTO(
                    stock.getSymbol(),
                    stock.getCompanyName(),
                    price
            ));
        }

        return result;
    }
}


