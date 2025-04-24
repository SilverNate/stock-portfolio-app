package com.example.portfolio.controller;

import com.example.portfolio.model.StockNews;
import com.example.portfolio.service.PriceListener;
import com.example.portfolio.service.StockNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class StockNewsController {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final StockNewsService stockNewsService;

    public StockNewsController(StockNewsService stockNewsService) {
        this.stockNewsService = stockNewsService;
    }

    @PostMapping
    public ResponseEntity<StockNews> add(@RequestBody StockNews news) {
        log.trace("POST /api/news - Adding news: {}", news);
        return ResponseEntity.ok(stockNewsService.addNews(news));
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockNews>> get(@PathVariable("symbol") String symbol) {
        log.trace("GET /api/news/{} - Fetching news", symbol);
        log.info("already place compiler args -parameter, but use this path variable instead");
        return ResponseEntity.ok(stockNewsService.getNewsForSymbol(symbol));
    }
}

