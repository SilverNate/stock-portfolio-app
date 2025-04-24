package com.example.portfolio.repository;

import com.example.portfolio.model.StockNews;
import com.example.portfolio.model.UserPortfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockNewsRepository extends MongoRepository<StockNews, String> {
}

