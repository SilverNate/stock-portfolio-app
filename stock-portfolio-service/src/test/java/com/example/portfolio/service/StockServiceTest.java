package com.example.portfolio.service;

import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private PricePublisher pricePublisher;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        stockService = new StockService(stockRepository, redisTemplate, pricePublisher);
    }

    @Test
    void testGetAllStocks() {
        List<Stock> mockStocks = List.of(new Stock());
        when(stockRepository.findAll()).thenReturn(mockStocks);

        List<Stock> result = stockService.getAllStocks();

        assertEquals(1, result.size());
        verify(stockRepository).findAll();
    }

    @Test
    void testGetCurrentPrice_FromRedis() {
        when(valueOperations.get("stock:price:AAPL")).thenReturn("150.00");

        BigDecimal result = stockService.getCurrentPrice("AAPL");

        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    void testGetCurrentPrice_FromDatabase() {
        when(valueOperations.get("stock:price:GOOGL")).thenReturn(null);

        Stock stock = new Stock();
        stock.setSymbol("GOOGL");
        stock.setCurrentPrice(new BigDecimal("2500"));

        when(stockRepository.findBySymbol("GOOGL")).thenReturn(Optional.of(stock));

        BigDecimal result = stockService.getCurrentPrice("GOOGL");

        assertEquals(new BigDecimal("2500"), result);
        verify(valueOperations).set(eq("stock:price:GOOGL"), eq("2500"), any());
    }

    @Test
    void testUpdateStockPrice() {
        Stock stock = new Stock();
        stock.setSymbol("MSFT");
        stock.setCurrentPrice(new BigDecimal("100"));

        when(stockRepository.findBySymbol("MSFT")).thenReturn(Optional.of(stock));

        stockService.updateStockPrice("MSFT", new BigDecimal("120"));

        verify(stockRepository).save(stock);
        verify(pricePublisher).publishPriceUpdate("MSFT", new BigDecimal("120"));
    }
}
