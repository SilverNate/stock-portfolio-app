package com.example.portfolio.controller;

import com.example.portfolio.model.PriceAlert;
import com.example.portfolio.model.StockPriceHistory;
import com.example.portfolio.service.StockPriceHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockPriceHistoryController {
    private final StockPriceHistoryService stockHistoryService;

    public StockPriceHistoryController(StockPriceHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping("/{symbol}/history")
    public List<StockPriceHistory> getHistory(@PathVariable String symbol) {
        return stockHistoryService.getStockPriceHistory(symbol);
    }

    @GetMapping("/alerts")
    public List<PriceAlert> getAlerts() {
        return stockHistoryService.generatePriceAlerts();
    }
}
