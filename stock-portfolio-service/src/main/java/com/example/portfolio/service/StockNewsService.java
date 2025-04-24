package com.example.portfolio.service;

import com.example.portfolio.model.StockNews;
import com.example.portfolio.repository.StockNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockNewsService {
    private static final Logger log = LoggerFactory.getLogger(PriceListener.class);

    private final StockNewsRepository newsRepository;

    public StockNewsService(StockNewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public StockNews addNews(StockNews news) {
        log.trace("Adding news for symbol: {}", news.getSymbol());
        return newsRepository.save(news);
    }

    public List<StockNews> getNewsForSymbol(String symbol) {
        log.trace("Retrieving news for symbol: {}", symbol);
        return newsRepository.findAll().stream()
                .filter(news -> news.getSymbol().equalsIgnoreCase(symbol))
                .collect(Collectors.toList());
    }
}

