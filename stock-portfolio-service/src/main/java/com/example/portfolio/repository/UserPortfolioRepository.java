package com.example.portfolio.repository;

import com.example.portfolio.model.UserPortfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPortfolioRepository extends MongoRepository<UserPortfolio, String> {
    UserPortfolio findByUserEmail(String email);
}
