# 📈 Stock Portofolio Monitoring System

A Spring Boot-based mini project to simulate stock portofolio management with real-time price updates, alerts, historical tracking, and clean microservice-style architecture.

---

## ✅ Features Covered

| Feature                             | Description                                                                 |
|-------------------------------------|-----------------------------------------------------------------------------|
| Spring IoC                          | Dependency injection and component-based architecture                       |
| Java Streams                        | Aggregation and summarization of user portfolio using Streams API          |
| Advanced Native SQL Queries         | Custom queries for reporting and performance                               |
| Containerization & Microservices    | Docker + docker-compose + modular structure                                 |
| Kafka & Stream-based Application    | Kafka listeners and producers for real-time updates                         |
| Redis, Caching & Data Grid          | Redis to cache current stock prices                                         |
| Elastic & Non-Relational DB (Mongo) | Elasticsearch for logging and MongoDB for additional storage (optional)     |
| SOLID & Clean Architecture          | Controller → Service → Repository pattern with interfaces and separation    |

---

## 📚 Architecture

Follows Clean Architecture principles with **SOLID** design:
- **S**ingle Responsibility: services separated for each domain
- **O**pen/Closed: Interfaces abstract repositories/services
- **L**iskov Substitution: Interfaces used across services/controllers
- **I**nterface Segregation: Specific, minimal contracts
- **D**ependency Inversion: Service/repo interfaces injected by Spring IoC

---

## 📁 Project Structure
```
stock-portfolio-app/
├── stock-portfolio-service/
│   ├──  src/
│   │    ├── main/
│   │       ├── java/com/example/
│   │       │   ├──portfolio/
│   │       │   │   ├── config/                      # config
│   │       │   │   ├── controller/                  # API controllers (Portfolio, Prices, Alerts, News)
│   │       │   │   ├── dto/                         # DTO
│   │       │   │   ├── model/                       # Models
│   │       │   │   ├── repository/                  # JPA + native SQL repositories
│   │       │   │   ├── service/                     # Business logic interfaces
│   │       │   │   ├── simulator/                   # Simulator
│   │       │   │   ├── elastic/                     # elastic search
│   │       │   │   └── PortfolopApplication.java    # Spring Boot application entry point
│   │       │   ├── resources/
│   │       │       └── application.yml              # Spring Boot app config (dev/prod)
│   │       │       └── db/                          # db migration
│   │       └── test/java/com/example/portfolio/     # Unit tests
│   │
│   ├── pom.xml                                  # Maven dependencies
├── .github/
│   └── workflows/
│       └── ci.yml                           # GitHub Actions CI pipeline
├── docker-compose.yml                       # Docker setup for PostgreSQL, Redis, Kafka
└── README.md                                # Project overview and documentation
```

---

## 🚀 API Endpoints

### 🔹 Portofolio

- `GET /api/portofolio/{userId}/summary`  
  Returns total investment and value using Java Stream.

### 🔹 Price & Historical

- `GET /api/price/{symbol}`  
  Get latest price from Redis.

- `POST /api/price/update`  
  Manually update stock price in Redis (simulates external feed).

- `GET /api/history/{symbol}`  
  Get historical stock prices.

### 🔹 Alerts

- `POST /api/alerts/rules`  
  Add alert rule (e.g., notify when AAPL > 200).

- Triggered by Kafka and stored in alert history.

### 🔹 News

- `POST /api/news`  
  Add latest stock-related news.

- `GET /api/news`  
  Retrieve latest news for frontend/mobile.

### 🔹 User portofolio

- `POST /api/user-portofolio`  
  Link user with stocks and quantities.

---

## 🧪 Test & Run Locally
1. **Clone the Repo**
    ```bash
   git clone https://github.com/your-username/stock-portfolio-app.git
    cd stock-portfolio-app

2. **Run containers:**
   ```bash
   docker-compose up -d
3. **Build the APP**
    ```bash
    cd portfolio-service
    ./mvnw clean install

4. **Run Spring Boot App (JetBrains or Terminal):**
    ```bash
    ./mvnw spring-boot:run

## TESTING
```bash
    ./mvnw test
```
## KIBANA
```bash
  http://localhost:5601
  
GET alert-history/_search
{
  "query": {
    "match": {
      "symbol": "AAPL"
    }
  }
}
```



🤝 Contributing
1. Fork
2. Create feature branch
3. Submit pull request

🧠 Author
Built with ❤️ by @SilverNate





