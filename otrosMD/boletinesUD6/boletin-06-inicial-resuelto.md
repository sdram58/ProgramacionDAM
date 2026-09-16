---
title: Boletín U06 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre genéricos y colecciones
---

# ✅ Boletín U06 — Inicial (resuelto)

1. **ArrayList de nombres**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> nombres = new ArrayList<>();
   nombres.add("Ada");
   nombres.add("Alan");
   nombres.add("Grace");
   nombres.add("Linus");
   nombres.add("Margaret");

   for (String n : nombres) {
       System.out.println(n);
   }
   ```
   </details>

2. **Ordenar alfabéticamente**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   Scanner sc = new Scanner(System.in);
   List<String> palabras = new ArrayList<>();
   for (int i = 0; i < 6; i++) {
       palabras.add(sc.nextLine());
   }
   Collections.sort(palabras);
   System.out.println(palabras);
   ```
   </details>

3. **HashSet sin duplicados**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Set<Integer> numeros = new HashSet<>();
   numeros.add(7);
   numeros.add(7);
   System.out.println(numeros.size());   // 1
   ```

   El `Set` ignora silenciosamente el segundo `add(7)`: un conjunto no admite duplicados, así que la segunda llamada no tiene ningún efecto (aunque `add` devuelve `false` esa vez, para quien quiera comprobarlo).
   </details>

4. **HashMap de productos**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Map<String, Integer> precios = new HashMap<>();
   precios.put("Teclado", 2499);
   precios.put("Ratón", 1250);
   precios.put("Monitor", 18900);
   precios.put("Cable USB", 599);

   for (Map.Entry<String, Integer> e : precios.entrySet()) {
       System.out.println(e.getKey() + ": " + e.getValue() + " céntimos");
   }
   ```
   </details>

5. **Clase genérica Par**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Par<A, B> {
       private A primero;
       private B segundo;

       public Par(A primero, B segundo) {
           this.primero = primero;
           this.segundo = segundo;
       }

       public A getPrimero() { return primero; }
       public B getSegundo() { return segundo; }
   }

   Par<String, Integer> p = new Par<>("Ada", 28);
   ```
   </details>

6. **Iterator seguro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<Integer> numeros = new ArrayList<>(List.of(1, 3, 6, 9, 10, 12, 15));
   Iterator<Integer> it = numeros.iterator();
   while (it.hasNext()) {
       int n = it.next();
       if (n % 3 == 0) {
           it.remove();
       }
   }
   System.out.println(numeros);   // [1, 10]
   ```
   </details>

7. **List vs. Set**
   <details>
   <summary>💡 Solución</summary>

   `List` mantiene el orden de inserción y permite duplicados y acceso por posición (`get(i)`) — útil, por ejemplo, para la lista de canciones de una playlist (importa el orden, y podría repetirse una canción). `Set` no permite duplicados y no garantiza posición — útil, por ejemplo, para guardar los DNI de las personas registradas en un evento: no tiene sentido que se repita un DNI, y no importa en qué orden se guardaron.
   </details>

8. **Deque como pila**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Deque<String> platos = new ArrayDeque<>();
   platos.push("Plato 1");
   platos.push("Plato 2");
   platos.push("Plato 3");

   while (!platos.isEmpty()) {
       System.out.println(platos.pop());   // Plato 3, Plato 2, Plato 1
   }
   ```

   El último plato apilado es el primero en retirarse — comportamiento LIFO, exactamente como una pila de platos de verdad.
   </details>
