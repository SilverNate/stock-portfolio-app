package com.example.portfolio.repository;

import com.example.portfolio.model.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, Long> {
    List<StockPriceHistory> findBySymbolOrderByTimestampDesc(String symbol);
}

