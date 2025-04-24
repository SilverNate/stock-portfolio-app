package com.example.portfolio.service;

import com.example.portfolio.model.AlertRule;
import com.example.portfolio.repository.AlertRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertRuleService {
    private final AlertRuleRepository alertRepo;

    public AlertRuleService(AlertRuleRepository alertRepo) {
        this.alertRepo = alertRepo;
    }
    public AlertRule create(AlertRule rule) {
        return alertRepo.save(rule);
    }

    public List<AlertRule> getAll() {
        return alertRepo.findAll();
    }

    public void delete(Long id) {
        alertRepo.deleteById(id);
    }

    public List<AlertRule> findBySymbol(String symbol) {
        return alertRepo.findByStockSymbol(symbol);
    }
}
