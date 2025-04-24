package com.example.portfolio.service;

import com.example.portfolio.model.PriceAlert;
import com.example.portfolio.model.StockPriceHistory;
import com.example.portfolio.repository.PriceAlertRepository;
import com.example.portfolio.repository.StockPriceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockPriceHistoryService
{
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final StockPriceHistoryRepository historyRepository;
    private final PriceAlertRepository priceAlertRepository;

    public StockPriceHistoryService(StockPriceHistoryRepository historyRepository, PriceAlertRepository priceAlertRepository) {
        this.historyRepository = historyRepository;
        this.priceAlertRepository = priceAlertRepository;
    }

    public List<StockPriceHistory> getStockPriceHistory(String symbol) {
        return historyRepository.findBySymbolOrderByTimestampDesc(symbol.toUpperCase());
    }

    public List<PriceAlert> generatePriceAlerts() {
        List<Object[]> results = priceAlertRepository.findPriceMovementsForAlerts();
        List<PriceAlert> alerts = new ArrayList<>();

        log.trace("Number of alerts found: {}", results.size());

        for (Object[] row : results) {
            String symbol = (String) row[0];
            BigDecimal currentPrice = (BigDecimal) row[1];
            BigDecimal pastPrice = (BigDecimal) row[2];

            if (pastPrice != null && currentPrice != null && pastPrice.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal change = currentPrice.subtract(pastPrice)
                        .divide(pastPrice, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                if (change.abs().compareTo(BigDecimal.valueOf(5)) > 0) {
                    alerts.add(new PriceAlert(symbol, pastPrice, currentPrice, change, LocalDateTime.now()));
                }
            }
        }
        return alerts;
    }


}
