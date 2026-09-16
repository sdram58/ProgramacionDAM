---
title: Boletín U06 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre genéricos y colecciones
---

# ✅ Boletín U06 — Intermedio (resuelto)

1. **Persona Comparable**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Persona implements Comparable<Persona> {
       String nombre;
       int edad;
       Persona(String nombre, int edad) { this.nombre = nombre; this.edad = edad; }

       @Override
       public int compareTo(Persona otra) {
           return Integer.compare(this.edad, otra.edad);
       }

       @Override
       public String toString() { return nombre + " (" + edad + ")"; }
   }

   List<Persona> personas = new ArrayList<>(List.of(
       new Persona("Ada", 28), new Persona("Alan", 41),
       new Persona("Grace", 35), new Persona("Linus", 19), new Persona("Margaret", 52)
   ));
   Collections.sort(personas);
   System.out.println(personas);
   ```
   </details>

2. **Comparator por nombre**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Comparator<Persona> porNombre = (p1, p2) -> p1.nombre.compareTo(p2.nombre);
   personas.sort(porNombre);
   System.out.println(personas);
   ```
   </details>

3. **Agrupar por letra inicial**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> palabras = List.of("casa", "coche", "barco", "bicicleta", "avión", "árbol");
   Map<String, ArrayList<String>> grupos = new HashMap<>();

   for (String palabra : palabras) {
       String letra = palabra.substring(0, 1);
       grupos.computeIfAbsent(letra, k -> new ArrayList<>()).add(palabra);
   }
   System.out.println(grupos);
   ```

   `computeIfAbsent` crea la lista vacía la primera vez que aparece una letra, y la reutiliza las siguientes veces — evita tener que comprobar "¿ya existe esta clave?" a mano.
   </details>

4. **Pila propia con ArrayList**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Pila<T> {
       private List<T> datos = new ArrayList<>();

       public void apilar(T dato) {
           datos.add(dato);
       }

       public T desapilar() {
           if (estaVacia()) throw new IllegalStateException("Pila vacía");
           return datos.remove(datos.size() - 1);
       }

       public boolean estaVacia() {
           return datos.isEmpty();
       }
   }
   ```

   El "final" de la lista hace de tope de la pila: apilar es `add` al final, desapilar es `remove` de la última posición — así ambas operaciones son igual de rápidas.
   </details>

5. **Producto más vendido**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static String masVendido(Map<String, Integer> ventas) {
       String mejor = null;
       int maxVentas = -1;
       for (Map.Entry<String, Integer> e : ventas.entrySet()) {
           if (e.getValue() > maxVentas) {
               maxVentas = e.getValue();
               mejor = e.getKey();
           }
       }
       return mejor;
   }
   ```
   </details>

6. **ArrayList vs. LinkedList**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<Integer> arrayList = new ArrayList<>();
   List<Integer> linkedList = new LinkedList<>();
   for (int i = 0; i < 100_000; i++) {
       arrayList.add(i);
       linkedList.add(i);
   }

   long inicio = System.nanoTime();
   arrayList.add(0, -1);
   System.out.println("ArrayList: " + (System.nanoTime() - inicio) + " ns");

   inicio = System.nanoTime();
   linkedList.add(0, -1);
   System.out.println("LinkedList: " + (System.nanoTime() - inicio) + " ns");
   ```

   `ArrayList` tarda notablemente más: insertar al principio obliga a desplazar los 100.000 elementos existentes una posición. `LinkedList` solo tiene que crear un nodo nuevo y enlazarlo al principio — no mueve nada más, por eso es casi instantáneo en esta operación concreta.
   </details>

7. **Caja con tipo limitado**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Caja<T extends Number> {
       private T valor;
       public Caja(T valor) { this.valor = valor; }
       public double duplicar() { return valor.doubleValue() * 2; }
   }

   Caja<Integer> c1 = new Caja<>(5);
   Caja<Double> c2 = new Caja<>(3.5);
   System.out.println(c1.duplicar());  // 10.0
   System.out.println(c2.duplicar());  // 7.0
   ```
   </details>

8. **TreeSet ordenado**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   Set<Integer> numeros = new TreeSet<>();
   Scanner sc = new Scanner(System.in);
   for (int i = 0; i < 10; i++) {
       numeros.add(sc.nextInt());
   }
   System.out.println(numeros);   // aparecen ordenados, sin importar el orden de entrada
   ```
   </details>
