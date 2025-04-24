package com.example.portfolio.dto;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioSummary {
    private BigDecimal totalInvestment;
    private BigDecimal totalCurrentValue;
    private BigDecimal totalGainLoss;
    private List<StockSummary> stockSummaries;

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public BigDecimal getTotalCurrentValue() {
        return totalCurrentValue;
    }

    public void setTotalCurrentValue(BigDecimal totalCurrentValue) {
        this.totalCurrentValue = totalCurrentValue;
    }

    public BigDecimal getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(BigDecimal totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public List<StockSummary> getStockSummaries() {
        return stockSummaries;
    }

    public void setStockSummaries(List<StockSummary> stockSummaries) {
        this.stockSummaries = stockSummaries;
    }

    public static class StockSummary {
        private String symbol;
        private BigDecimal investment;
        private BigDecimal currentValue;
        private BigDecimal gainLoss;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public BigDecimal getInvestment() {
            return investment;
        }

        public void setInvestment(BigDecimal investment) {
            this.investment = investment;
        }

        public BigDecimal getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(BigDecimal currentValue) {
            this.currentValue = currentValue;
        }

        public BigDecimal getGainLoss() {
            return gainLoss;
        }

        public void setGainLoss(BigDecimal gainLoss) {
            this.gainLoss = gainLoss;
        }
    }
}
