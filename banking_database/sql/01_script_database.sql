-- Crear base de datos
CREATE DATABASE IF NOT EXISTS db_banco;
USE db_banco;

-- Tabla: persona
CREATE TABLE persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INT,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla: cliente
CREATE TABLE cliente (
    id INT PRIMARY KEY,
    contrasenia VARCHAR(100) NOT NULL,
    estado VARCHAR(10) NOT NULL,
    FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE
);

-- Tabla: cuenta
CREATE TABLE cuenta (
    numero BIGINT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado VARCHAR(10) NOT NULL,
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

-- Tabla: movimiento
CREATE TABLE movimiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_numero BIGINT NOT NULL,
    FOREIGN KEY (cuenta_numero) REFERENCES cuenta(numero) ON DELETE CASCADE
);