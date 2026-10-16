---
draft: true
prev: false
next: false
title: "⚡ Unidad 10 — Retos, Proyectos y Extras"
description: "Retos y proyectos avanzados de bases de datos relacionales con Java y JDBC: control transaccional de stock, Connection Pool casero e importador masivo CSV."
---

:::tip[Retos de Nivel Profesional]
Estos retos abordan problemas reales de ingeniería de software: concurrencia, prevención de inconsistencias en inventarios, diseño de pools de conexiones y migraciones de datos masivas.
:::

---

## 🛒 Proyecto 1: Sistema de Ventas con Control Transaccional de Stock

### Descripción del Escenario
En un comercio electrónico, cuando un cliente finaliza una compra, deben ocurrir dos acciones estrictamente ligadas:
1. **Registrar la venta** en la tabla `ventas` con el total a pagar y la fecha actual.
2. **Descontar las unidades vendidas** de la tabla `productos`.

Si el cliente intenta comprar 5 unidades de un producto pero solo quedan 3 en el almacén, la venta **no debe realizarse**, no debe descontarse nada y debe abortarse toda la operación mediante `con.rollback()`.

### Estructura de Tablas (DDL)
```sql
CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL CHECK (stock >= 0)
);

CREATE TABLE IF NOT EXISTS ventas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);
```

### Código de la Transacción Segura

```java
package com.dam.boletin10.extras;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.*;

public class GestorVentasTransaccional {

    public static boolean procesarVenta(int productoId, int cantidadSolicitada) {
        String sqlVerificarYDescontar = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String sqlObtenerPrecio = "SELECT precio FROM productos WHERE id = ?";
        String sqlInsertarVenta = "INSERT INTO ventas (producto_id, cantidad, total) VALUES (?, ?, ?)";

        Connection con = null;
        try {
            con = ConexionBD.conectar();
            con.setAutoCommit(false); // 1. INICIO DE TRANSACCIÓN ATÓMICA

            // Paso A: Intentar descontar stock
            try (PreparedStatement psStock = con.prepareStatement(sqlVerificarYDescontar)) {
                psStock.setInt(1, cantidadSolicitada);
                psStock.setInt(2, productoId);
                psStock.setInt(3, cantidadSolicitada); // Condición atómica en el WHERE

                int filas = psStock.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("Stock insuficiente o producto inexistente para ID: " + productoId);
                }
            }

            // Paso B: Obtener precio unitario actual
            BigDecimal precioUnitario;
            try (PreparedStatement psPrecio = con.prepareStatement(sqlObtenerPrecio)) {
                psPrecio.setInt(1, productoId);
                try (ResultSet rs = psPrecio.executeQuery()) {
                    if (rs.next()) {
                        precioUnitario = rs.getBigDecimal("precio");
                    } else {
                        throw new SQLException("No se pudo obtener el precio del producto.");
                    }
                }
            }

            // Paso C: Registrar venta
            BigDecimal totalVenta = precioUnitario.multiply(new BigDecimal(cantidadSolicitada));
            try (PreparedStatement psVenta = con.prepareStatement(sqlInsertarVenta)) {
                psVenta.setInt(1, productoId);
                psVenta.setInt(2, cantidadSolicitada);
                psVenta.setBigDecimal(3, totalVenta);
                psVenta.executeUpdate();
            }

            // 2. CONFIRMAR LA OPERACIÓN AL COMPLETO
            con.commit();
            System.out.println("Venta procesada con éxito: " + cantidadSolicitada + " unidades. Total: " + totalVenta + " €");
            return true;

        } catch (SQLException ex) {
            System.err.println("Venta rechazada. Ejecutando ROLLBACK: " + ex.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // 3. DESHACER CUALQUIER MODIFICACIÓN PARCIAL
                } catch (SQLException rollbackEx) {
                    System.err.println("Error grave en rollback: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    // Cierre
                }
            }
        }
    }
}
```

---

## ⚡ Proyecto 2: Implementación de un Mini Pool de Conexiones Casero

### ¿Cómo Funciona un Pool por Dentro?
Los pools profesionales (como HikariCP) mantienen una cola de conexiones reutilizables en memoria. Podemos construir un simulador pedagógico utilizando una estructura concurrente `ArrayBlockingQueue<Connection>`:

```java
package com.dam.boletin10.extras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MiniConnectionPool {

    private final String url;
    private final BlockingQueue<Connection> pool;
    private final int capacidad;

    public MiniConnectionPool(String url, int capacidad) throws SQLException {
        this.url = url;
        this.capacidad = capacidad;
        this.pool = new ArrayBlockingQueue<>(capacidad);

        // Abrimos por adelantado las conexiones al arrancar
        for (int i = 0; i < capacidad; i++) {
            pool.offer(DriverManager.getConnection(url));
        }
        System.out.println("MiniConnectionPool inicializado con " + capacidad + " conexiones activas.");
    }

    public Connection prestarConexion() throws InterruptedException {
        // take() se bloquea de forma eficiente si todas las conexiones están en uso
        return pool.take();
    }

    public void devolverConexion(Connection con) {
        if (con != null) {
            pool.offer(con);
        }
    }

    public void cerrarTodo() {
        for (Connection c : pool) {
            try {
                c.close();
            } catch (SQLException ex) {
                // Silencioso
            }
        }
        pool.clear();
        System.out.println("Todas las conexiones del pool han sido destruidas.");
    }
}
```

---

## 🚀 Reto Algorítmico: Importador Masivo CSV a Base de Datos con Lotes

### Enunciado
Construye una utilidad `ImportadorCSV` que lea un fichero de clientes con miles de líneas (`nif,nombre,ciudad,saldo`) e inserte los datos en la base de datos aplicando:
1. Lectura en streaming mediante `Files.lines(Path)`.
2. `PreparedStatement` con lotes de 500 registros (`addBatch()` y `executeBatch()`).
3. Bloqueo transaccional desactivando el autocommit para lograr velocidades superiores a 10.000 inserciones por segundo.
4. Registro de líneas defectuosas o con errores de formato sin abortar el resto del proceso.
