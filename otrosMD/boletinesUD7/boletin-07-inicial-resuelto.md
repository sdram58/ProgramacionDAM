---
title: Boletín U07 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre la API Stream
---

# ✅ Boletín U07 — Inicial (resuelto)

1. **Mayúsculas**
   <details>
   <summary>💡 Solución</summary>

   ```java
   nombres.stream()
       .map(String::toUpperCase)
       .forEach(System.out::println);
   ```
   </details>

2. **Filtrar por longitud**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> largos = nombres.stream()
       .filter(n -> n.length() > 3)
       .collect(Collectors.toList());
   ```
   </details>

3. **Contar los que empiezan por vocal**
   <details>
   <summary>💡 Solución</summary>

   ```java
   long cuenta = nombres.stream()
       .filter(n -> "AEIOU".indexOf(n.charAt(0)) != -1)
       .count();
   ```
   </details>

4. **Suma y máximo de un array**
   <details>
   <summary>💡 Solución</summary>

   ```java
   int[] numeros = {5, 3, 8, 1, 9, 2, 7};
   int suma = Arrays.stream(numeros).sum();
   OptionalInt max = Arrays.stream(numeros).max();
   System.out.println("Suma: " + suma + ", Máximo: " + max.getAsInt());
   ```
   </details>

5. **allMatch**
   <details>
   <summary>💡 Solución</summary>

   ```java
   boolean todosLargos = nombres.stream().allMatch(n -> n.length() >= 3);
   ```
   </details>

6. **Múltiplos de 4 con iterate**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Stream.iterate(0, n -> n + 4)
       .limit(15)
       .forEach(System.out::println);
   ```
   </details>

7. **joining**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String unidos = nombres.stream().collect(Collectors.joining(" - "));
   System.out.println(unidos);
   ```
   </details>

8. **Intermedia vs. final**
   <details>
   <summary>💡 Solución</summary>

   Una operación **intermedia** (como `filter` o `map`) recibe un stream y devuelve **otro stream** — se puede encadenar con más operaciones, y no hace nada por sí sola hasta que llega una operación final. Una operación **final** (como `collect`, `forEach` o `count`) recibe el stream y produce un resultado que **no** es un stream (una lista, un número, nada...) — es la que de verdad dispara la ejecución de todo el pipeline acumulado hasta ese momento.
   </details>
