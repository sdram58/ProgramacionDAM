---
draft: true
prev: false
next: false
title: "📝 Unidad 10 — Boletín Inicial (Resuelto)"
description: "Soluciones completas, comentadas y explicadas paso a paso para el boletín inicial de bases de datos relacionales y JDBC en Java."
---

:::note[Configuración del Entorno de Pruebas]
Para que los ejemplos sean inmediatamente reproducibles sin necesidad de instalar o arrancar un servidor MySQL local, se proporciona una clase de utilidad de conexión `ConexionBD` compatible con **SQLite** (fichero local autónomo) o fácilmente conmutable a **MySQL**.
:::

---

### Clase de Conexión de Utilidad

```java
package com.dam.boletin10.inicial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Para SQLite embebido (sin instalación externa)
    private static final String URL = "jdbc:sqlite:empresa_inicial.db";
    
    // Si se utiliza MySQL, bastaría con descomentar:
    // private static final String URL = "jdbc:mysql://localhost:3306/empresa?serverTimezone=UTC";
    // private static final String USUARIO = "root";
    // private static final String PASSWORD = "root";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
```

---

### Solución Ejercicio 1: Conexión de Prueba y Metadatos del Servidor

```java
package com.dam.boletin10.inicial;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Ejercicio01MetadatosConexion {
    public static void main(String[] args) {
        System.out.println("Comprobando conexión y extrayendo metadatos...");

        try (Connection con = ConexionBD.conectar()) {
            DatabaseMetaData meta = con.getMetaData();

            System.out.println("• Motor de Base de Datos : " + meta.getDatabaseProductName());
            System.out.println("• Versión del SGBD        : " + meta.getDatabaseProductVersion());
            System.out.println("• Driver JDBC            : " + meta.getDriverName());
            System.out.println("• Versión del Driver      : " + meta.getDriverVersion());
            System.out.println("• Usuario de Sesión       : " + meta.getUserName());
            System.out.println("• Estado de Conexión     : ACTIVA Y SEGURA");

        } catch (SQLException e) {
            System.err.println("Fallo al conectar con la base de datos: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 2: Creación de Esquema de Tablas (DDL)

```java
package com.dam.boletin10.inicial;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio02CrearTablas {
    public static void main(String[] args) {
        String sql = """
            CREATE TABLE IF NOT EXISTS departamentos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50) NOT NULL UNIQUE,
                presupuesto DECIMAL(12,2) NOT NULL
            );
            """;

        try (Connection con = ConexionBD.conectar();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla 'departamentos' creada o verificada con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al ejecutar DDL: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 3: Inserción Segura de Registros con `PreparedStatement`

```java
package com.dam.boletin10.inicial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio03InsertarDepartamentos {

    public static boolean insertar(String nombre, BigDecimal presupuesto) {
        String sql = "INSERT INTO departamentos (nombre, presupuesto) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setBigDecimal(2, presupuesto);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar '" + nombre + "': " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        insertar("Informática y Desarrollo", new BigDecimal("120000.00"));
        insertar("Recursos Humanos", new BigDecimal("45000.50"));
        insertar("Marketing y Ventas", new BigDecimal("75000.00"));
        System.out.println("Proceso de inserción inicial completado.");
    }
}
```

---

### Solución Ejercicio 4: Listado Completo de Registros con `ResultSet`

```java
package com.dam.boletin10.inicial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ejercicio04ListarDepartamentos {
    public static void main(String[] args) {
        String sql = "SELECT id, nombre, presupuesto FROM departamentos ORDER BY nombre ASC";

        System.out.println(String.format("%-5s | %-25s | %-12s", "ID", "DEPARTAMENTO", "PRESUPUESTO"));
        System.out.println("-----------------------------------------------------");

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                BigDecimal presupuesto = rs.getBigDecimal("presupuesto");

                System.out.println(String.format("%-5d | %-25s | %10.2f €", id, nombre, presupuesto));
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar datos: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 5: Búsqueda Filtrada por Rango de Presupuesto

```java
package com.dam.boletin10.inicial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ejercicio05BuscarPorPresupuesto {

    public static void buscarPorPresupuestoMinimo(BigDecimal minimo) {
        String sql = "SELECT id, nombre, presupuesto FROM departamentos WHERE presupuesto >= ? ORDER BY presupuesto DESC";

        System.out.println("Buscando departamentos con presupuesto >= " + minimo + " €:");

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, minimo);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hayResultados = false;
                while (rs.next()) {
                    hayResultados = true;
                    System.out.println("• [" + rs.getInt("id") + "] " + rs.getString("nombre") 
                                       + " -> " + rs.getBigDecimal("presupuesto") + " €");
                }
                if (!hayResultados) {
                    System.out.println("No se encontraron departamentos con dicho presupuesto.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la búsqueda: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        buscarPorPresupuestoMinimo(new BigDecimal("50000.00"));
    }
}
```

---

### Solución Ejercicio 6: Actualización de Datos con Comprobación de Filas Afectadas

```java
package com.dam.boletin10.inicial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio06ActualizarPresupuesto {

    public static boolean actualizar(int id, BigDecimal nuevoPresupuesto) {
        String sql = "UPDATE departamentos SET presupuesto = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, nuevoPresupuesto);
            ps.setInt(2, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Presupuesto del departamento ID " + id + " actualizado a " + nuevoPresupuesto + " €");
                return true;
            } else {
                System.out.println("Aviso: No se encontró ningún departamento con el ID " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar departamento: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        actualizar(1, new BigDecimal("135000.00"));
        actualizar(999, new BigDecimal("10000.00")); // ID inexistente
    }
}
```

---

### Solución Ejercicio 7: Eliminación de Registros por Identificador

```java
package com.dam.boletin10.inicial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio07EliminarDepartamento {

    public static boolean eliminar(int id) {
        String sql = "DELETE FROM departamentos WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Departamento con ID " + id + " eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el departamento con ID " + id + " para eliminar.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar departamento: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        eliminar(2);
    }
}
```

---

### Solución Ejercicio 8: Inserción con Recuperación de Clave Autoincremental

```java
package com.dam.boletin10.inicial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio08ClaveAutogenerada {

    public static int insertarYDevolverId(String nombre, BigDecimal presupuesto) {
        String sql = "INSERT INTO departamentos (nombre, presupuesto) VALUES (?, ?)";

        // Solicitamos explícitamente a JDBC que capture las claves generadas
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setBigDecimal(2, presupuesto);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet claves = ps.getGeneratedKeys()) {
                    if (claves.next()) {
                        int nuevoId = claves.getInt(1);
                        System.out.println("Departamento registrado con nuevo ID generado: " + nuevoId);
                        return nuevoId;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar con clave generada: " + e.getMessage());
        }
        return -1;
    }

    public static void main(String[] args) {
        int idGenerado = insertarYDevolverId("Innovación y Calidad", new BigDecimal("95000.00"));
        System.out.println("ID final devuelto: " + idGenerado);
    }
}
```
