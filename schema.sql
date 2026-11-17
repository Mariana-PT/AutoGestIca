-- 1. Crear y usar la base de datos
CREATE DATABASE IF NOT EXISTS db_autigest_ica;
USE db_autigest_ica;

-- 2. Limpiar tablas si ya existen (para evitar conflictos al reiniciar)
DROP TABLE IF EXISTS OrdenRepuesto;
DROP TABLE IF EXISTS OrdenTrabajo;
DROP TABLE IF EXISTS Vehiculo;
DROP TABLE IF EXISTS Repuesto;
DROP TABLE IF EXISTS Cliente;

-- 3. Crear Tabla Cliente
CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NULL,
    correo VARCHAR(100) NULL,
    direccion VARCHAR(150) NULL
);

-- 4. Crear Tabla Vehiculo
CREATE TABLE Vehiculo (
    placa VARCHAR(10) PRIMARY KEY,
    id_cliente INT NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    kilometraje DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_vehiculo_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

-- 5. Crear Tabla Repuesto
CREATE TABLE Repuesto (
    id_repuesto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL
);

-- 6. Crear Tabla Orden de Trabajo
CREATE TABLE OrdenTrabajo (
    codigo_orden INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    fecha_ingreso DATETIME NOT NULL,
    problema TEXT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    costo_total DECIMAL(10,2) NULL,
    CONSTRAINT fk_orden_vehiculo FOREIGN KEY (placa) REFERENCES Vehiculo(placa)
);

-- 7. Crear Tabla OrdenRepuesto (Relación N a M entre Órdenes y Repuestos)
CREATE TABLE OrdenRepuesto (
    id_orden_repuesto INT AUTO_INCREMENT PRIMARY KEY,
    codigo_orden INT NOT NULL,
    id_repuesto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_or_orden FOREIGN KEY (codigo_orden) REFERENCES OrdenTrabajo(codigo_orden),
    CONSTRAINT fk_or_repuesto FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto)
);

-- =========================================
-- DATOS DE PRUEBA (INSERTS)
-- =========================================

-- Insertar Clientes de prueba
INSERT INTO Cliente (dni, nombres, telefono, correo, direccion) VALUES 
('45871234', 'Carlos Mendoza Silva', '955443322', 'carlos.mendoza@gmail.com', 'Av. San Martín 420, Ica'),
('71234589', 'Ana María Torres Rojas', '988112233', 'atorres@hotmail.com', 'Urb. La Angostura B-12, Ica'),
('40123987', 'Jorge Luis Huamán Pérez', '944556677', 'jhuaman@outlook.com', 'Calle Lima 310, Ica');

-- Insertar Vehículos de prueba asociados a los clientes
INSERT INTO Vehiculo (placa, id_cliente, marca, modelo, anio, kilometraje) VALUES 
('ABC-123', 1, 'Toyota', 'Corolla', 2019, 45500.00),
('M1-2345', 2, 'Nissan', 'Sentra', 2021, 28000.00),
('Z3K-890', 3, 'Hyundai', 'Tucson', 2018, 72000.00);

-- Insertar Repuestos de prueba
INSERT INTO Repuesto (nombre, precio, stock) VALUES 
('Filtro de Aceite Standard', 35.00, 25),
('Pastillas de Freno Delanteras', 120.00, 10),
('Correa / Kit de Distribución', 250.00, 6),
('Bujía de Iridio NGK', 45.00, 30),
('Aceite Sintético 5W-30 (Galón)', 160.00, 15);

-- Insertar Órdenes de Trabajo de prueba
INSERT INTO OrdenTrabajo (placa, fecha_ingreso, problema, estado, costo_total) VALUES 
('ABC-123', '2026-06-01 08:30:00', 'Mantenimiento periódico de 45 mil kilómetros y revisión de frenos.', 'En reparación', 275.00),
('M1-2345', '2026-06-02 10:15:00', 'Vehículo presenta rechinido al aplicar el freno delantero.', 'En diagnóstico', NULL);

-- Detalle de repuestos de la orden 1 (35.00 + 2 x 120.00 = 275.00, coincide con costo_total)
INSERT INTO OrdenRepuesto (codigo_orden, id_repuesto, cantidad, subtotal) VALUES
(1, 1, 1, 35.00),
(1, 2, 2, 240.00);
