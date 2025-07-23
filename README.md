# 📡 Event Tracker Service

A Spring Boot microservice that tracks "live" sports events, periodically polls a mock external API, and publishes event updates to a Kafka topic.

---

## 🚀 Setup & Run Instructions

### ✅ Prerequisites

- **Java 17+**
- **Maven**
- **Docker & Docker Compose**

---

### 📦 Clone and Build

- git clone https://github.com/your-username/event-tracker-service.git
- cd event-tracker-service
- mvn clean install


---

### 🐳 Start Kafka + Zookeeper with Docker Compose

- docker-compose up -d (or run the docker-compose.yml)


This starts:
- Zookeeper on `localhost:2181`
- Kafka on `localhost:9092`

---

### 🎯 Create Kafka Topic 

`docker exec -it  kafka-topics 
–bootstrap-server localhost:9092 
–create 
–topic live-events 
–partitions 3 
–replication-factor 1`

---

### Confirm topic creation:

`docker exec -it  kafka-topics 
–bootstrap-server localhost:9092 
–list`


---

## ⚙️ Configuration

Configurations can be found in: `src/main/resources/application.yml`

---


- All test classes are automatically discovered in `src/test/java`
- Includes tests for:
  - REST status updates
  - Scheduled polling
  - Kafka publishing under success and error conditions

---

## 📝 Useful Commands

| Description                     | Command                                                                 |
|---------------------------------|-------------------------------------------------------------------------|
| Build the Application           | mvn clean install                                                  |
| Run the Service                 | `./mvnw spring-boot:run` or `java -jar target/event-tracker-service.jar`|
| Start Kafka and Zookeeper       | `docker-compose up -d`                                                 |
| Stop Kafka and Zookeeper        | `docker-compose down`                                                  |
| Create Kafka Topic (manual)     | See above under "Create Kafka Topic"                                   |
| List Kafka Topics               | `docker exec -it <kafka-container> kafka-topics --list ...`            |
| Run All Tests                   | mvn test                                                           |

---


## 🧠 Design Decisions

### 1. ✅ Input Validation with Validator Pattern

To perform robust request validation beyond simple `@Valid`, the **Custom Validator Pattern** is used.

- Each input type (like `EventStatusRequest`) is validated using a dedicated validator.
- This decouples validation logic from controllers and services, improving testability and separation of concerns.
- Makes it easier to plug in domain-specific or cross-field validation rules without cluttering controller code.

### 2. 🧩 Extensibility with Strategy + Factory Pattern

Event status (`live` / `not live`) handling is implemented using the **Strategy Pattern combined with a simple Factory**:

- Each event status has a dedicated **strategy implementation**.
- A **factory (via dynamic Spring Bean selection)** selects the proper strategy at runtime based on the `eventStatus` field.
- Promotes open/closed principle for easy extension (e.g., adding support for new statuses like `paused`, `ended`, etc.).
- Avoids `if-else` or `switch` logic and enforces clean architectural boundaries.

### 🔄 Benefits

- 🔍 Clear separation of `status`-driven behavior.
- 📦 Easily testable components (each validator + strategy independently).
- 📝 Readable and maintainable code aligned with SOLID principles.

---

## 🛡️ Notes

- Make sure ports `2181` (Zookeeper) and `9092` (Kafka) are free.
- For HTTPS Git pushes, use a [GitHub Personal Access Token](https://github.com/settings/tokens).
- Update your `.gitignore` to exclude `/target`, `.env`, and IDE-specific files.

---

## 👨‍💻 Author & Contributors

- Adithiya Guru Patham — [Github](https://github.com/adithiyagurupatham)

PRs welcome! 🙌

---




