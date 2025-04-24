package com.example.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "stock_news")
public class StockNews {

    @Id
    private String id;
    private String symbol;
    private String headline;
    private String content;
    private LocalDateTime publishedAt;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getHeadline() {
        return headline;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
