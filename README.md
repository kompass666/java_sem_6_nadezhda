# Fruit Store API

Учебный проект на Spring Boot.  
REST API для работы с товарами (фруктами).

## Стек

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Hibernate  
- H2 / PostgreSQL  
- Spring Security  

## Что реализовано

- CRUD операции  
- Подключение к базе данных  
- REST-контроллер  
- Базовая авторизация  

## Запуск

```bash
mvn spring-boot:run
```

После запуска приложение доступно по адресу:

```
http://localhost:8080/items
```

## Основные запросы

### Получить все товары
```
GET /products
```

### Получить товар по id
```
GET /products/{id}
```

### Добавить товар
```
POST /products
```

### Обновить товар
```
PUT /products/{id}
```

### Удалить товар
```
DELETE /products/{id}
```

---

Проект выполнен в рамках изучения Spring Boot и REST API.
