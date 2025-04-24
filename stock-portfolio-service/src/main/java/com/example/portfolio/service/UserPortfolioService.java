package com.example.portfolio.service;

import com.example.portfolio.model.UserPortfolio;
import com.example.portfolio.repository.UserPortfolioRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserPortfolioService {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);
    private final UserPortfolioRepository userPortfolioRepository;

    public UserPortfolioService(UserPortfolioRepository userPortfolioRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
    }

    public UserPortfolio savePortfolio(UserPortfolio portfolio) {
        log.trace("Saving portfolio for email: {}", portfolio.getUserEmail());
        return userPortfolioRepository.save(portfolio);
    }

    public UserPortfolio getByEmail(String email) {
        log.trace("Retrieving portfolio for email: {}", email);
        return userPortfolioRepository.findByUserEmail(email);
    }
}

