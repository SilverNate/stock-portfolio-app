package com.example.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockSymbol;
    private Double priceAbove;
    private Double priceBelow;

    public Double getPriceAbove() {
        return priceAbove;
    }

    public void setPriceAbove(Double priceAbove) {
        this.priceAbove = priceAbove;
    }

    public Double getPriceBelow() {
        return priceBelow;
    }

    public void setPriceBelow(Double priceBelow) {
        this.priceBelow = priceBelow;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
