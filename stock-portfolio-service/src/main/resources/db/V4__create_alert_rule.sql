CREATE TABLE alert_rule (
                            id SERIAL PRIMARY KEY,
                            stock_symbol VARCHAR(10) NOT NULL,
                            price_above DOUBLE PRECISION,
                            price_below DOUBLE PRECISION,
                            email VARCHAR(255)
);
