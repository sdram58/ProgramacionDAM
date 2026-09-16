---
title: Boletín U07 — Inicial
description: Ejercicios propuestos de nivel inicial sobre la API Stream
---

# 📝 Boletín U07 — Inicial

Usa esta lista en los ejercicios que la necesiten:
```java
List<String> nombres = List.of("Ana", "Roberto", "Eva", "Marcos", "Ada", "Luis", "Elena", "Bea");
```

1. Usa un stream para imprimir todos los nombres de la lista en mayúsculas, uno por línea.

2. Usa un stream para obtener una nueva lista solo con los nombres de más de 3 letras.

3. Usa un stream para contar cuántos nombres empiezan por una vocal.

4. Dado un array `int[] numeros = {5, 3, 8, 1, 9, 2, 7}`, usa un stream para obtener su suma y su valor máximo.

5. Usa un stream para comprobar si todos los nombres de la lista tienen al menos 3 letras (`allMatch`).

6. Usa `Stream.iterate()` para generar e imprimir los primeros 15 múltiplos de 4, empezando en 0.

7. Usa un stream para unir todos los nombres de la lista en un único `String` separados por " - " (pista: `Collectors.joining`).

8. Explica con tus propias palabras la diferencia entre una operación intermedia y una operación final en un stream, y pon un ejemplo de cada una.
