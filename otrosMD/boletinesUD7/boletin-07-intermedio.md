---
title: Boletín U07 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre la API Stream
---

# 📝 Boletín U07 — Intermedio

1. Diseña una clase `Producto` (nombre, categoría, precio). Con una lista de al menos 8 productos, usa streams para obtener el precio medio de todos los productos.

2. A partir de la lista del ejercicio 1, usa `Collectors.groupingBy` para agrupar los productos por categoría en un `Map<String, List<Producto>>`.

3. Usa `flatMap` para, dada una `List<List<Integer>>` (una lista de listas de números), obtener una única lista aplanada con todos los números, sin duplicados.

4. Usa `reduce` (sin usar `sum()`) para calcular el producto de todos los números de una lista de enteros.

5. Dada la lista de productos del ejercicio 1, usa un stream para encontrar el producto más caro, devolviendo un `Optional<Producto>` y gestionando con `ifPresentOrElse` (o `isPresent`/`get`) el caso de que la lista esté vacía.

6. Usa `Collectors.partitioningBy` para separar una lista de números en dos grupos: pares e impares (el resultado es un `Map<Boolean, List<Integer>>`).

7. Usa `Collectors.groupingBy` con dos niveles para agrupar la lista de productos del ejercicio 1 primero por categoría, y dentro de cada categoría, por si el precio es mayor o menor de 50€.

8. Compara con `System.nanoTime()` cuánto tarda sumar un millón de números con un `for` normal frente a con `IntStream.range(0, 1_000_000).sum()`, y con la versión `.parallel()` de ese mismo stream. ¿Los resultados te sorprenden?
