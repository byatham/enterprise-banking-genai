# Enterprise Banking GenAI Platform

Enterprise-grade Banking GenAI platform
using Java, Python, RAG and AWS Bedrock.

## Architecture

- Java 21 / Spring Boot
- Python 3.12 / FastAPI
- PostgreSQL / pgvector
- Redis
- Kafka-compatible messaging
- AWS Bedrock / Claude

## Services

| Service | Port |
|---------|------|
| Java API | 8080 |
| Python AI | 8001 |
| PostgreSQL | 5432 |
| Redis | 6379 |
| Kafka | 19092 |

## Local setup

1. Copy .env.example to .env
2. Start Docker Desktop
3. Run docker compose up -d
4. Start Spring Boot
5. Start FastAPI

## Java

cd backend-java
mvn spring-boot:run

## Python

cd ai-service
source .venv/bin/activate
uvicorn app.main:app --reload --port 8001