CREATE TABLE alert_history (
                               id SERIAL PRIMARY KEY,
                               symbol VARCHAR(10),
                               price DOUBLE PRECISION,
                               condition VARCHAR(10),
                               threshold DOUBLE PRECISION,
                               triggered_at TIMESTAMP,
                               email VARCHAR(255)
);
