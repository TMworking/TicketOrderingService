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
  git clone https://github.com/TMworking/TicketOrderingService.git
  cd TicketOrderingService
  ```
- Создайте файл .env в корневой директории проекта с переменными окружения. Пример содержания .env находится в файле env-example. Для локального запуска можете скопировать указанную конфигурацию:
  ```dotenv
    POSTGRES_HOST=localhost
    POSTGRES_USERNAME=postgres
    POSTGRES_PASSWORD=postgres
    POSTGRES_DATABASE=postgres
    POSTGRES_PORT=5432
    REDIS_HOST=redis
    REDIS_PORT=6379
    REDIS_TTL=24h
    POSTGRES_KAFKA_HOST=localhost
    POSTGRES_KAFKA_USERNAME=postgres2
    POSTGRES_KAFKA_PASSWORD=postgres2
    POSTGRES_KAFKA_DATABASE=postgres
    POSTGRES_KAFKA_PORT=5433
  ```
   
### Запуск через Docker Compose

- Соберите Docker образ и запустите контейнеры с помощью Docker Compose. Для этого в корневой директории проекта выполните:
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
