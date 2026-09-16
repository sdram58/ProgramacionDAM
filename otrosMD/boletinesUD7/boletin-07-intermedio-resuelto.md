---
title: Boletín U07 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre la API Stream
---

# ✅ Boletín U07 — Intermedio (resuelto)

1. **Precio medio**
   <details>
   <summary>💡 Solución</summary>

   ```java
   double mediaPrecios = productos.stream()
       .collect(Collectors.averagingDouble(Producto::getPrecio));
   ```
   </details>

2. **Agrupar por categoría**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Map<String, List<Producto>> porCategoria = productos.stream()
       .collect(Collectors.groupingBy(Producto::getCategoria));
   ```
   </details>

3. **flatMap sin duplicados**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<List<Integer>> listas = List.of(List.of(1, 2, 3), List.of(3, 4, 5), List.of(5, 6));
   List<Integer> aplanada = listas.stream()
       .flatMap(List::stream)
       .distinct()
       .collect(Collectors.toList());
   // [1, 2, 3, 4, 5, 6]
   ```
   </details>

4. **Producto con reduce**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<Integer> numeros = List.of(2, 3, 4, 5);
   int producto = numeros.stream()
       .reduce(1, (acumulado, n) -> acumulado * n);
   // 120
   ```
   </details>

5. **Producto más caro con Optional**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Optional<Producto> masCaro = productos.stream()
       .max(Comparator.comparing(Producto::getPrecio));

   masCaro.ifPresentOrElse(
       p -> System.out.println("El más caro es: " + p.getNombre()),
       () -> System.out.println("No hay productos")
   );
   ```
   </details>

6. **Partición pares/impares**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
   Map<Boolean, List<Integer>> particion = numeros.stream()
       .collect(Collectors.partitioningBy(n -> n % 2 == 0));

   System.out.println("Pares: " + particion.get(true));
   System.out.println("Impares: " + particion.get(false));
   ```
   </details>

7. **Agrupamiento a dos niveles**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Map<String, Map<Boolean, List<Producto>>> agrupado = productos.stream()
       .collect(Collectors.groupingBy(
           Producto::getCategoria,
           Collectors.groupingBy(p -> p.getPrecio() > 50)
       ));
   ```
   </details>

8. **for vs. stream vs. parallelStream**
   <details>
   <summary>💡 Solución</summary>

   ```java
   long inicio = System.nanoTime();
   long sumaFor = 0;
   for (int i = 0; i < 1_000_000; i++) sumaFor += i;
   System.out.println("for: " + (System.nanoTime() - inicio) + " ns");

   inicio = System.nanoTime();
   long sumaStream = IntStream.range(0, 1_000_000).sum();
   System.out.println("stream: " + (System.nanoTime() - inicio) + " ns");

   inicio = System.nanoTime();
   long sumaParalela = IntStream.range(0, 1_000_000).parallel().sum();
   System.out.println("parallel: " + (System.nanoTime() - inicio) + " ns");
   ```

   Resultado típico: el `for` clásico suele ser el más rápido de los tres para una operación tan simple como sumar — el stream añade cierta sobrecarga (crear el pipeline, las llamadas a lambdas), y la versión paralela añade además el coste de repartir el trabajo entre hilos y combinar los resultados, que para una operación tan barata y una cantidad de datos no tan enorme puede no compensar. El paralelismo brilla más cuanto más "cara" es la operación que se repite por cada elemento, no cuanto más simple.
   </details>
