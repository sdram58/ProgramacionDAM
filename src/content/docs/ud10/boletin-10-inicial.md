---
draft: true
prev: false
next: false
title: "📝 Unidad 10 — Boletín Inicial (Propuesto)"
description: "Ejercicios introductorios de acceso a bases de datos relacionales con JDBC: conexiones, sentencias preparadas, consultas con ResultSet y operaciones DML."
---

:::tip[Objetivos Pedagógicos]
* Configurar correctamente conexiones JDBC con gestión automática de recursos (`try-with-resources`).
* Ejecutar sentencias seguras con `PreparedStatement` previniendo inyecciones de código.
* Navegar por cursores `ResultSet` y recuperar claves primarias autogeneradas.
:::

---

### Ejercicio 1: Conexión de Prueba y Metadatos del Servidor
Desarrolla un programa que:
* Establezca una conexión con una base de datos MySQL (o SQLite/H2 local) mediante `DriverManager.getConnection`.
* Utilice `con.getMetaData()` para obtener el objeto `DatabaseMetaData`.
* Muestre por consola el nombre del producto de base de datos, su versión oficial, el nombre del driver JDBC utilizado y el nombre de usuario de la sesión actual.
* Asegura que la conexión se cierre de forma garantizada mediante `try-with-resources`.

---

### Ejercicio 2: Creación de Esquema de Tablas (DDL)
Escribe una clase `GestorTablas` con un método que ejecute sentencias DDL mediante `Statement.executeUpdate`:
* Cree una tabla llamada `departamentos` si no existe (`CREATE TABLE IF NOT EXISTS departamentos ...`), con los campos:
  * `id`: entero, clave primaria, autoincremental.
  * `nombre`: cadena de 50 caracteres, no nulo y único.
  * `presupuesto`: número decimal con 2 posiciones decimales (`DECIMAL(12,2)`).
* Muestra por consola un mensaje confirmando que la tabla ha sido verificada o creada con éxito.

---

### Ejercicio 3: Inserción Segura de Registros con `PreparedStatement`
Crea un método `insertarDepartamento(String nombre, BigDecimal presupuesto)`:
* Utiliza una sentencia SQL parametrizada con marcadores de posición `?`:
  `INSERT INTO departamentos (nombre, presupuesto) VALUES (?, ?)`
* Asigna los parámetros tipados mediante `setString(1, ...)` y `setBigDecimal(2, ...)`.
* Ejecuta la instrucción con `executeUpdate()` y comprueba que el valor devuelto sea mayor que 0.
* Inserta al menos tres departamentos de prueba (ej. "Informática", "Recursos Humanos", "Ventas").

---

### Ejercicio 4: Listado Completo de Registros con `ResultSet`
Implementa un programa que consulte todos los departamentos ordenados alfabéticamente por su nombre:
* Ejecuta la consulta `SELECT id, nombre, presupuesto FROM departamentos ORDER BY nombre ASC` con `executeQuery()`.
* Itera sobre las filas devueltas mediante el cursor `while (rs.next())`.
* Imprime por consola los registros con un formato alineado y legible en columnas.

---

### Ejercicio 5: Búsqueda Filtrada por Rango de Presupuesto
Diseña un método `buscarPorPresupuestoMinimo(BigDecimal minimo)`:
* Solicita por consola un presupuesto mínimo al usuario.
* Ejecuta una consulta parametrizada:
  `SELECT id, nombre, presupuesto FROM departamentos WHERE presupuesto >= ?`
* Muestra las filas coincidentes o un mensaje indicando que no se han encontrado departamentos con ese presupuesto.

---

### Ejercicio 6: Actualización de Datos con Comprobación de Filas Afectadas
Crea un método `actualizarPresupuesto(int id, BigDecimal nuevoPresupuesto)`:
* Ejecuta la sentencia:
  `UPDATE departamentos SET presupuesto = ? WHERE id = ?`
* Evalúa el valor devuelto por `executeUpdate()`:
  * Si es igual a 1, muestra `"Presupuesto del departamento actualizado con éxito"`.
  * Si es igual a 0, informa al usuario de que `"No se encontró ningún departamento con el ID proporcionado"`.

---

### Ejercicio 7: Eliminación de Registros por Identificador
Implementa un método `eliminarDepartamento(int id)`:
* Ejecuta una sentencia `DELETE FROM departamentos WHERE id = ?`.
* Muestra un mensaje informativo indicando cuántos registros fueron eliminados.
* Captura y gestiona las posibles excepciones de integridad referencial si existieran registros hijos asociados.

---

### Ejercicio 8: Inserción con Recuperación de Clave Autoincremental
Modifica o crea un método de inserción que devuelva el ID numérico asignado por la base de datos:
* Prepara la sentencia con el flag especial:
  `con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)`
* Tras ejecutar `executeUpdate()`, extrae el conjunto de claves con `ps.getGeneratedKeys()`.
* Si existe un resultado con `rs.next()`, recupera el ID con `rs.getInt(1)` y devuélvelo. Muestra por consola `"Departamento registrado con el nuevo ID generado: [id]"`.
