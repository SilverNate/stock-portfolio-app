package com.example.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_portfolios")
public class UserPortfolio {

    @Id
    private String id;
    private String userEmail;
    private List<Asset> assets;

    public String getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public static class Asset {
        private String symbol;
        private double quantity;
        private double averagePrice;

        public String getSymbol() {
            return symbol;
        }

        public double getQuantity() {
            return quantity;
        }

        public double getAveragePrice() {
            return averagePrice;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public void setAveragePrice(double averagePrice) {
            this.averagePrice = averagePrice;
        }
    }
}
