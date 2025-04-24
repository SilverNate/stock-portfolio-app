package com.example.portfolio.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_alerts")
public class PriceAlert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private BigDecimal percentageChange;
    private LocalDateTime timestamp;

    public PriceAlert() {}

    public PriceAlert(String symbol, BigDecimal oldPrice, BigDecimal newPrice, BigDecimal percentageChange, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.percentageChange = percentageChange;
        this.timestamp = timestamp;
    }
}

