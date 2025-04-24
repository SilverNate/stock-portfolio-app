package com.example.portfolio.controller;

import com.example.portfolio.dto.StockRealtimeDTO;
import com.example.portfolio.dto.UpdatePriceRequest;
import com.example.portfolio.model.Stock;
import com.example.portfolio.model.StockPriceHistory;
import com.example.portfolio.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.portfolio.dto.PortfolioSummary;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return service.getAllStocks();
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return service.getStockById(id).orElse(null);
    }

    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return service.saveStock(stock);
    }

    @PutMapping("/{id}")
    public Stock updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        stock.setId(id);
        return service.saveStock(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        service.deleteStock(id);
    }

    @GetMapping("/summary")
    public PortfolioSummary getPortfolioSummary() {
        return service.getPortfolioSummary();
    }

    @GetMapping("/{symbol}/price")
    public ResponseEntity<BigDecimal> getCurrentPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(service.getCurrentPrice(symbol));
    }

    @PutMapping("/price")
    public ResponseEntity<String> updatePrice(@RequestBody UpdatePriceRequest request) {
        service.updateStockPrice(request.getSymbol(), request.getNewPrice());
        return ResponseEntity.ok("Stock price updated and cached in Redis");
    }

    @GetMapping("/realtime")
    public List<StockRealtimeDTO> getRealtimePrices() {
        return service.getAllStocksRealtime();
    }

}
