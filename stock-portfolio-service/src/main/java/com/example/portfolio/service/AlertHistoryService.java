package com.example.portfolio.service;

import com.example.portfolio.elastic.AlertHistoryDocument;
import com.example.portfolio.elastic.AlertHistoryElasticRepository;
import com.example.portfolio.model.AlertHistory;
import com.example.portfolio.repository.AlertHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertHistoryService {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final AlertHistoryRepository alertHistoryRepo;
    private final AlertHistoryElasticRepository elasticRepo;

    public AlertHistoryService (AlertHistoryRepository alertHistoryRepo, AlertHistoryElasticRepository elasticRepo) {
        this.alertHistoryRepo = alertHistoryRepo;
        this.elasticRepo = elasticRepo;
    }

    public void log(AlertHistory history) {
        alertHistoryRepo.save(history);

        elasticRepo.save(AlertHistoryDocument.builder()
                .symbol(history.getSymbol())
                .price(history.getPrice())
                .condition(history.getCondition())
                .threshold(history.getThreshold())
                .triggeredAt(history.getTriggeredAt())
                .build()
        );

        log.trace("save history log: {}", history);
    }
}
