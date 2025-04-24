package com.example.portfolio.controller;

import com.example.portfolio.service.PricePublisher;
import com.example.portfolio.model.StockPriceHistory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulate")
public class StockPriceSimulationController {

    private final PricePublisher publisher;

    public StockPriceSimulationController(PricePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/price")
    public ResponseEntity<String> simulatePrice(@RequestBody StockPriceHistory stockPrice) {
        publisher.publishPriceUpdate(stockPrice.getSymbol(), stockPrice.getPrice());
        return ResponseEntity.ok("Simulated price sent to Kafka");
    }
}
