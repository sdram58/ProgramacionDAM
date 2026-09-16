---
title: Boletín U04 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre clases y objetos en Java
---

# 📝 Boletín U04 — Intermedio

1. Sobrescribe `toString()` en la clase `Libro` del boletín inicial para que `System.out.println(libro)` muestre algo como `"Ficciones, de Borges (200 páginas)"`.

2. Sobrescribe `equals()` en la clase `Libro` para que dos libros se consideren iguales si tienen el mismo título y el mismo autor (aunque el número de páginas sea distinto). Prueba tu implementación con al menos 3 casos: dos libros iguales, dos libros distintos, y un libro comparado consigo mismo.

3. Diseña una clase inmutable `Coordenada` con atributos `x` e `y` (ambos `double`, `private final`), sin setters, y un método `distanciaA(Coordenada otra)` que calcule la distancia entre dos coordenadas.

4. Diseña una clase `ContadorGlobal` como Singleton, con un método `incrementar()` y otro `getValor()`. Demuestra en el `main` que dos referencias obtenidas con `getInstancia()` comparten el mismo contador.

5. Diseña una clase `CuentaBancaria` con atributos `titular`, `saldo` (privados), un constructor, y métodos `ingresar(double cantidad)` y `retirar(double cantidad)` — este último debe lanzar una excepción (`IllegalArgumentException`) si se intenta retirar más saldo del disponible.

6. Diseña una clase `Mochila` que contenga internamente un array de `String` (los objetos que lleva) con una copia defensiva en el constructor y en el getter, para evitar que se pueda modificar el contenido desde fuera sin pasar por los métodos de la clase.

7. Crea una clase `Circulo` con atributo `radio` y los métodos `getArea()` y `getPerimetro()`. Crea un array de 5 objetos `Circulo` con radios distintos, y escribe un método que recorra el array y devuelva el círculo con mayor área.

8. Explica con tus propias palabras por qué, si sobrescribes `equals()` en una clase, también deberías sobrescribir `hashCode()`. ¿Qué problema podría darte si no lo haces?
