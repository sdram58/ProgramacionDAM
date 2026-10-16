---
draft: true
prev: false
next: false
title: "📝 Unidad 10 — Boletín Intermedio (Propuesto)"
description: "Ejercicios de nivel intermedio sobre JDBC: arquitectura DAO completa, consultas relacionales JOIN, transacciones atómicas, metadatos y enlace con JTable."
---

:::tip[Objetivos Pedagógicos]
* Diseñar arquitecturas desacopladas mediante el patrón DAO (Data Access Object) y POJOs.
* Garantizar la integridad transaccional (ACID) mediante rollback ante excepciones.
* Manejar metadatos dinámicos, procedimientos almacenados y optimización por lotes (*batch*).
:::

---

### Ejercicio 1: Arquitectura DAO Completa para la Entidad Empleado
Diseña una capa de persistencia completa para la entidad `Empleado`:
* Clase modelo `Empleado`: atributos `id`, `nif`, `nombre`, `puesto`, `salario` (`BigDecimal`), `departamentoId`.
* Interfaz `EmpleadoDAO`:
  * `boolean insertar(Empleado e)`
  * `boolean actualizar(Empleado e)`
  * `boolean eliminar(int id)`
  * `Optional<Empleado> buscarPorId(int id)`
  * `List<Empleado> listarTodos()`
* Implementación `EmpleadoDAOImpl` con sentencias `PreparedStatement` y `try-with-resources`.

---

### Ejercicio 2: Consulta Relacional con `INNER JOIN` y Objeto DTO
Crea un método en el DAO que realice una consulta cruzada entre las tablas `empleados` y `departamentos`:
* Sentencia SQL:
  `SELECT e.id, e.nombre, e.puesto, e.salario, d.nombre AS nombre_departamento FROM empleados e INNER JOIN departamentos d ON e.departamento_id = d.id`
* Mapea cada fila a un objeto de transferencia de datos `EmpleadoDetalleDTO`.
* Muestra el listado de empleados indicando claramente el nombre del departamento al que pertenecen.

---

### Ejercicio 3: Detección y Tratamiento de Valores `NULL`
Añade a la tabla `empleados` una columna opcional `comision DECIMAL(6,2)` que puede contener valores `NULL`:
* Al consultar empleados con `ResultSet`, recupera la comisión con `rs.getBigDecimal("comision")` o `rs.getDouble("comision")`.
* Utiliza el método `rs.wasNull()` para determinar de forma fehaciente si el empleado no tiene comisión asignada.
* Imprime por consola: `"Comisión: Sin comisión asignada"` cuando el campo sea `NULL`, o el importe en euros en caso contrario.

---

### Ejercicio 4: Inspector Genérico de Tablas con `ResultSetMetaData`
Desarrolla una clase utilitaria `InspectorTablas` con un método estático:
`public static void inspeccionarTabla(String nombreTabla)`
* Ejecuta una consulta límite vacía: `SELECT * FROM " + nombreTabla + " WHERE 1=0`.
* Obtén el objeto `ResultSetMetaData`.
* Imprime un informe que detalle:
  1. Número total de columnas.
  2. Nombre de cada columna.
  3. Tipo de dato SQL (`getColumnTypeName`).
  4. Tamaño o precisión (`getColumnDisplaySize`).
  5. Si admite o no valores nulos (`isNullable`).

---

### Ejercicio 5: Transacción Bancaria Atómica (ACID)
Simula un sistema de transferencias entre dos cuentas bancarias:
* Tabla `cuentas` con campos `id`, `titular`, `saldo` (`DECIMAL(12,2)`).
* Implementa el método `transferir(int cuentaOrigen, int cuentaDestino, BigDecimal importe)`:
  * Inicia la transacción desactivando el autocommit (`con.setAutoCommit(false)`).
  * Comprueba que la cuenta de origen disponga de saldo suficiente. Si no es así, lanza una excepción.
  * Resta el importe en la cuenta origen y súmalo en la cuenta destino.
  * Si ambas operaciones tienen éxito, ejecuta `con.commit()`.
  * Si se produce cualquier fallo o excepción, ejecuta `con.rollback()` en el bloque catch para asegurar que no se pierda dinero.

---

### Ejercicio 6: Invocación de Procedimientos Almacenados (`CallableStatement`)
En un servidor MySQL (o script simulado), define un procedimiento almacenado:
```sql
CREATE PROCEDURE sp_resumen_departamento(
    IN p_depto_id INT,
    OUT p_total_empleados INT,
    OUT p_masa_salarial DECIMAL(12,2)
)
```
* Desde Java, utiliza `CallableStatement` para invocar el procedimiento.
* Registra los dos parámetros de salida (`OUT`) con los tipos correspondientes de `java.sql.Types`.
* Ejecuta la llamada y recupera los valores calculados por el motor para mostrarlos por pantalla.

---

### Ejercicio 7: Optimización con Inserciones por Lotes (*Batch Processing*)
Escribe un experimento para comparar el rendimiento de inserción de 1.000 registros:
* Método A: Inserta los 1.000 registros mediante ejecuciones individuales de `ps.executeUpdate()`.
* Método B: Utiliza procesamiento por lotes con `ps.addBatch()` y ejecuta el lote en bloque cada 200 registros mediante `ps.executeBatch()`.
* Mide con `System.currentTimeMillis()` el tiempo empleado por cada método y muestra la comparativa de eficiencia en milisegundos.

---

### Ejercicio 8: Mantenimiento Visual Swing Conectado a Base de Datos
Construye una ventana `JFrame` que integre el patrón DAO con Swing:
* Una tabla `JTable` en el centro envuelta en un `JScrollPane`.
* Un formulario superior para introducir NIF, Nombre, Puesto y Salario.
* Tres botones: `"Cargar Datos"`, `"Nuevo Empleado"` y `"Eliminar Seleccionado"`.
* Toda operación sobre la base de datos se realiza a través de la interfaz `EmpleadoDAO`. Al insertar o eliminar, la tabla se actualiza visualmente reflejando el nuevo estado de la base de datos.
