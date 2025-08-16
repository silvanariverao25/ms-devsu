Banco Demo – Microservicios

Ejemplo con dos microservicios:

ms-clientes → gestiona clientes y publica eventos.
ms-cuentas → gestiona cuentas y consume eventos de clientes.

Infraestructura con PostgreSQL y RabbitMQ en Docker.

Levantar proyecto

En la raíz:

docker compose build --no-cache
docker compose up -d

Servicios disponibles:

Postgres: localhost:5432
RabbitMQ UI: http://localhost:15672 (guest / guest)
ms-clientes: http://localhost:8081
ms-cuentas: http://localhost:8082

Notas

Código organizado con DDD ligero + Clean Architecture.
Eventos de clientes viajan por RabbitMQ y son consumidos por ms-cuentas.
Validación de saldo 0.
Pruebas disponibles con JUnit y Karate. (Karate -> Ejecutar las pruebas una vez levantados los servicios).
Se usa clase en memoria para cliente en microservicio de cuenta (demo).
