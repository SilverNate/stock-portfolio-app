package com.example.portfolio.dto;

import java.math.BigDecimal;

public class UpdatePriceRequest {
    private String symbol;
    private BigDecimal newPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
