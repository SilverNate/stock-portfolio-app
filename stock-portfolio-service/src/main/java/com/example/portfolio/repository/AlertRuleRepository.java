package com.example.portfolio.repository;

import com.example.portfolio.model.AlertRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRuleRepository extends JpaRepository<AlertRule, Long> {
    List<AlertRule> findByStockSymbol(String stockSymbol);
}
