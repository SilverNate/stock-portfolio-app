package com.example.portfolio.repository;

import com.example.portfolio.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol); // should return Optional<Stock>
}