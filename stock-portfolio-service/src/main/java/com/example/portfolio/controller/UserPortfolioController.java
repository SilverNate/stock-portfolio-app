package com.example.portfolio.controller;

import com.example.portfolio.model.UserPortfolio;
import com.example.portfolio.service.PriceListener;
import com.example.portfolio.service.UserPortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portofolios")
public class UserPortfolioController {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final UserPortfolioService portfolioService;

    public UserPortfolioController(UserPortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public ResponseEntity<UserPortfolio> save(@RequestBody UserPortfolio portfolio) {
        log.info("POST /api/portfolios - Saving: {}", portfolio);
        return ResponseEntity.ok(portfolioService.savePortfolio(portfolio));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserPortfolio> get(@PathVariable("email") String email) {
        log.info("GET /api/portfolios/{} - Fetching portfolio", email);
        return ResponseEntity.ok(portfolioService.getByEmail(email));
    }
}

