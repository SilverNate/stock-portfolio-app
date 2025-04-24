package com.example.portfolio.repository;

import com.example.portfolio.model.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<StockPriceHistory, Long> {

    @Query(value = """
        SELECT 
            sph.symbol,
            MAX(CASE WHEN sph.timestamp >= now() - interval '1 minute' THEN sph.price END) as current_price,
            MAX(CASE WHEN sph.timestamp BETWEEN now() - interval '1 hour' AND now() - interval '50 minutes' THEN sph.price END) as past_price
        FROM stock_price_history sph
        GROUP BY sph.symbol
        HAVING 
            MAX(CASE WHEN sph.timestamp BETWEEN now() - interval '1 hour' AND now() - interval '50 minutes' THEN sph.price END) IS NOT NULL
        """, nativeQuery = true)
    List<Object[]> findPriceMovementsForAlerts();
}
