package com.example.portfolio.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class AlertHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Double price;
    private String condition;
    private Double threshold;
    private LocalDateTime triggeredAt;

    public AlertHistory() {}

    public AlertHistory(String symbol, Double price, String condition, Double threshold, LocalDateTime triggeredAt) {
        this.symbol = symbol;
        this.price = price;
        this.condition = condition;
        this.threshold = threshold;
        this.triggeredAt = triggeredAt;
    }

    public Long getId() { return id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }

    public LocalDateTime getTriggeredAt() { return triggeredAt; }
    public void setTriggeredAt(LocalDateTime triggeredAt) { this.triggeredAt = triggeredAt; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String symbol;
        private Double price;
        private String condition;
        private Double threshold;
        private LocalDateTime triggeredAt;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder threshold(Double threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder triggeredAt(LocalDateTime triggeredAt) {
            this.triggeredAt = triggeredAt;
            return this;
        }

        public AlertHistory build() {
            return new AlertHistory(symbol, price, condition, threshold, triggeredAt);
        }
    }
}
