---
title: Boletín U10 — Inicial
description: Ejercicios propuestos de nivel inicial sobre JDBC y bases de datos relacionales
---

# 📝 Boletín U10 — Inicial

Para estos ejercicios necesitas MySQL (o MariaDB) funcionando y el driver `mysql-connector-j` añadido al proyecto. Usa esta base de datos:

```sql
CREATE DATABASE tienda;
USE tienda;
CREATE TABLE producto (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    nombre    VARCHAR(50) NOT NULL,
    precio    DECIMAL(8,2) NOT NULL,
    stock     INT NOT NULL DEFAULT 0
);
INSERT INTO producto (nombre, precio, stock) VALUES
    ('Teclado', 24.99, 15), ('Ratón', 12.50, 30), ('Monitor', 189.00, 4), ('Cable USB', 5.99, 100);
```

1. Escribe un programa que se conecte a la base de datos `tienda` y muestre por pantalla un mensaje de "Conexión correcta" o el mensaje de error si falla. Usa `try-with-resources`.

2. Escribe un programa que muestre todos los productos (id, nombre, precio y stock), uno por línea.

3. Escribe un programa que muestre solo los productos con un precio superior a 20 €, ordenados de más caro a más barato.

4. Escribe un programa que inserte un nuevo producto usando `PreparedStatement` (con los valores guardados en variables) y muestre cuántas filas se han insertado.

5. Escribe un programa que pida por teclado el id de un producto y muestre su nombre y precio, o "No existe" si no hay ninguno con ese id. Usa un `PreparedStatement`.

6. Escribe un programa que aumente en 10 unidades el stock de todos los productos con menos de 5 unidades, y muestre cuántos productos se han modificado.

7. Escribe un programa que pida un id y borre el producto correspondiente. Muestra un mensaje distinto si se ha borrado o si no existía.

8. Explica con tus propias palabras qué es una inyección SQL, y cómo evita ese problema un `PreparedStatement` frente a concatenar el texto en la sentencia.
