-- 1) Crear bases para cada microservicio
CREATE DATABASE clientes_db;
CREATE DATABASE cuentas_db;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

------------------------------------------------------------
-- 2) Configurar clientes_db
------------------------------------------------------------
\connect clientes_db;

-- Extensiones y schema
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE SCHEMA IF NOT EXISTS bank AUTHORIZATION bank;

-- Tablas base (opcional si usas JPA ddl-auto=update)
CREATE TABLE IF NOT EXISTS bank.persona (
  persona_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nombre         VARCHAR(120) NOT NULL,
  genero         VARCHAR(20)  NOT NULL,
  edad           SMALLINT     NOT NULL,
  identificacion VARCHAR(50)  NOT NULL UNIQUE,
  direccion      VARCHAR(200) NOT NULL,
  telefono       VARCHAR(30)  NOT NULL
);

CREATE TABLE IF NOT EXISTS bank.cliente (
  persona_id   UUID PRIMARY KEY REFERENCES bank.persona(persona_id),
  cliente_id   UUID NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  estado       SMALLINT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_persona_identificacion ON bank.persona(identificacion);

------------------------------------------------------------
-- 3) Configurar cuentas_db
------------------------------------------------------------
\connect cuentas_db;

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE SCHEMA IF NOT EXISTS bank AUTHORIZATION bank;

CREATE TABLE IF NOT EXISTS bank.cuenta (
  cuenta_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cliente_id    UUID NOT NULL, -- referencia lógica al ms-clientes
  numero_cuenta VARCHAR(30) NOT NULL UNIQUE,
  tipo_cuenta   VARCHAR(20) NOT NULL,      -- AHORROS | CORRIENTE
  saldo_inicial NUMERIC(18,2) NOT NULL,
  estado        SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS bank.movimiento (
  movimiento_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cuenta_id      UUID NOT NULL REFERENCES bank.cuenta(cuenta_id),
  fecha          TIMESTAMPTZ NOT NULL,
  tipo_movimiento VARCHAR(20) NOT NULL,    -- DEPOSITO | RETIRO
  valor          NUMERIC(18,2) NOT NULL,
  saldo          NUMERIC(18,2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_movimiento_cuenta ON bank.movimiento(cuenta_id);
CREATE INDEX IF NOT EXISTS idx_cuenta_numero ON bank.cuenta(numero_cuenta);