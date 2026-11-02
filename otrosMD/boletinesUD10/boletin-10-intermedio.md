---
title: Boletín U10 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre JDBC, transacciones y el patrón DAO
---

# 📝 Boletín U10 — Intermedio

Usa la base de datos `tienda` del boletín inicial.

1. Crea la clase `Producto` (id, nombre, precio, stock) y la clase `ProductoDao` con los métodos `insertar`, `buscarPorId`, `buscarTodos`, `actualizar` y `borrar`, todos con `PreparedStatement`. Escribe un `main` que pruebe cada uno.

2. Añade a `ProductoDao` un método `buscarPorNombre(String texto)` que devuelva los productos cuyo nombre **contenga** el texto (pista: `LIKE ?` y pasar `"%" + texto + "%"` como valor del parámetro).

3. Crea una tabla `venta (id, id_producto, unidades)`. Escribe un método `vender(int idProducto, int unidades)` que, **dentro de una transacción**, inserte la venta y reste las unidades del stock. Si no hay stock suficiente, debe deshacer todo (`rollback`) y lanzar una excepción.

4. Escribe un programa que, sin conocer de antemano las columnas, ejecute cualquier `SELECT` que se le pase como texto y lo muestre como una tabla de texto, con una primera línea con los nombres de las columnas (pista: `ResultSetMetaData`).

5. Escribe un programa que calcule, con una única consulta SQL (`SUM`, `COUNT`, `AVG`), el valor total del inventario (precio × stock), el número de productos y el precio medio, y los muestre formateados.

6. Escribe un programa que inserte 1.000 productos de prueba de dos formas: (a) una sentencia por producto, con autocommit activado, y (b) todas dentro de una sola transacción, usando `addBatch()` y `executeBatch()`. Mide el tiempo de cada una y explica la diferencia.

7. Crea una ventana Swing con un `JTable` que muestre los productos, un campo de texto para filtrar por nombre y un botón "Buscar" que recargue la tabla con el resultado.

8. Explica con tus propias palabras qué ventajas aporta el patrón DAO y qué problema aparece si se comparte una única `Connection` entre varios hilos.
