# BANKING - Full Stack Project

Aplicacion para el manejo de clientes, cuentas y transacciones, desarrollado con una arquitectura de microservicios con comunicacion asincrona usando **Spring Boot**, **RabbitMQ**, **Angular**, **MySQL** y **Docker**.

## 📦 Estructura del Proyecto

```
fullstack-Banking/
├── banking_backend/
│   ├── account_service/     # Manejo de cuentas y transacciones (consume el microservicio de customer_service)
│   ├── customer_service/    # Manejo de Clientes
├── banking_database/
│   └── sql/                 # Scripts y stored procedures
├── postman/
│   └── collection/          # Archivo para pruebas de api
├── banking_frontend/        # Interfaz de usuario (Angular)
├── docker-compose.yml
```

## 🚀 Tecnologías Usadas

- Spring Boot 3 (Java 21)
- RabbitMQ
- Angular
- MySQL 8
- Docker + Docker Compose

## 🐳 Cómo levantar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/jborbor/fullstack-BankingApp.git
cd fullstack-BankingApp
```

2. Levanta los contenedores:

```bash
docker compose up --build
```

Esto levantará:

- La base de datos MySQL
- Ejecutara automaticamente los scripts de creacion de BD, stored procedures, etc:
- Los microservicios backend
- El frontend Angular (vía NGINX)

## 📦 Documentacion de laS api con OpenApi

http://localhost:8080/api/v1/swagger-ui/index.html # Clientes
http://localhost:8081/api/v1/swagger-ui/index.html # Cuentas y movimientos

## 📬 Contacto

Proyecto desarrollado por [Jonathan Borbor].
