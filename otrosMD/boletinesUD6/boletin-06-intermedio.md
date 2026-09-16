---
title: Boletín U06 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre genéricos y colecciones
---

# 📝 Boletín U06 — Intermedio

1. Diseña una clase `Persona` (nombre, edad) que implemente `Comparable<Persona>` ordenando por edad. Crea una `ArrayList<Persona>` con al menos 5 personas, ordénala con `Collections.sort()`, y muéstrala.

2. A partir del ejercicio anterior, crea un `Comparator<Persona>` que ordene por nombre en vez de por edad, y ordena la misma lista con él (sin tocar la clase `Persona`).

3. Usa un `HashMap<String, ArrayList<String>>` para agrupar una lista de palabras por su primera letra (la clave es la letra, el valor es la lista de palabras que empiezan por ella).

4. Diseña una clase genérica `Pila<T>` (tu propia implementación, usando internamente un `ArrayList<T>`) con métodos `apilar(T dato)`, `desapilar()` y `estaVacia()`. No uses `Deque` ni `Stack` de Java, impleméntala tú.

5. Dado un `Map<String, Integer>` con las ventas de varios productos, escribe un método que devuelva el nombre del producto más vendido (el de valor más alto), recorriendo el mapa con `entrySet()`.

6. Compara experimentalmente `ArrayList` y `LinkedList`: crea 100.000 elementos en cada una, y mide (con `System.nanoTime()`) cuánto tarda insertar un elemento al principio de cada una. Explica el resultado según lo que sabes de cómo funciona cada estructura por dentro.

7. Diseña una clase genérica `Caja<T extends Number>` con un método `duplicar()` que devuelva el doble del valor almacenado (como `double`). Pruébala con `Caja<Integer>` y `Caja<Double>`.

8. Usa un `TreeSet<Integer>` para almacenar 10 números introducidos por el usuario en cualquier orden, y muestra que al recorrerlo salen automáticamente ordenados.
