# TicketOrderingService

Сервис для учета заказа билетов с REST API, базой данных и аутентификацией.

## Технологии
- **Java 17** + **Spring Boot 3**
- **PostgreSQL** (хранение данных)
- **Redis** (кэш)
- **Liquibase** (миграции БД)
- **Docker** (контейнеризация)
- **Swagger** (документация API)
- **Kafka**

## Быстрый старт
### Подготовка
- Установите [Docker](https://docs.docker.com/get-docker/) и [Docker Compose](https://docs.docker.com/compose/install/)
- Клонируйте репозиторий:
  ```bash
  git clone https://github.com/{username}/{}
  cd {}
  ```
### Запуск через Docker Compose
```bash
docker-compose up --build
```

После запуска сервис будет доступен по адресам:
- API: http://localhost:8080/api/v1/
- Swagger UI: http://localhost:8080/swagger-ui.html (Базовый админ: логин: "admin@mail.ru", пароль: "admin". Базовый пользователь : логин: "user@mail.ru", пароль: "user")

## Остановка сервиса
```bash
docker-compose down
```
Для полной очистки (с удалением томов):
```bash
docker-compose down -v
```
