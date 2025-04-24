package com.example.portfolio.dto;

import java.math.BigDecimal;

public class StockRealtimeDTO {
    private String symbol;
    private String companyName;
    private BigDecimal realTimePrice;

    public StockRealtimeDTO() {}
    public StockRealtimeDTO(String symbol, String companyName, BigDecimal realTimePrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.realTimePrice = realTimePrice;
    }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public BigDecimal getRealTimePrice() { return realTimePrice; }
    public void setRealTimePrice(BigDecimal realTimePrice) { this.realTimePrice = realTimePrice; }
}
