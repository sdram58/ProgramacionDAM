---
title: Boletín U05 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre polimorfismo, interfaces y lambdas
---

# 📝 Boletín U05 — Intermedio

1. Diseña una jerarquía `Figura` (abstracta, con `calcularArea()` y `calcularPerimetro()` abstractos) con tres subclases: `Circulo`, `Rectangulo` y `Triangulo`. Crea un array de `Figura` con varias formas distintas y recórrelo mostrando el área de cada una (polimorfismo).

2. A partir del ejercicio anterior, añade un método `static Figura mayorArea(Figura[] figuras)` que devuelva la figura de mayor área del array, sin importar de qué subclase concreta sea cada una.

3. Diseña una interface `Comparador3D<T>` con un método `int comparar(T a, T b)` (que devuelva negativo, cero o positivo, igual que `Comparable`). Impleméntala con una expresión lambda para comparar objetos `Circulo` por su radio.

4. Diseña una jerarquía con una clase abstracta `CuentaBancaria` (atributo `saldo`, método abstracto `calcularInteres()`) y dos subclases `CuentaAhorro` (interés del 2%) y `CuentaCorriente` (interés del 0.5%, pero permite saldo negativo hasta -500). Escribe un `main` que cree varias cuentas de ambos tipos y muestre el interés de cada una.

5. Usa `instanceof` y *downcasting* para escribir un método que, dado un array de `Figura` (de la jerarquía del ejercicio 1), cuente cuántos `Circulo` hay en él.

6. Crea una lista de `String` con al menos 8 nombres, y usa expresiones lambda con `removeIf` para: (a) eliminar los que tengan menos de 4 letras; (b) en una copia distinta de la lista original, eliminar los que no empiecen por una letra concreta que tú elijas.

7. Usando `Predicate<Integer>`, `Function<Integer,Integer>` y `Consumer<Integer>`, escribe tres lambdas distintas: una que compruebe si un número es primo, una que lo eleve al cuadrado, y una que lo imprima con un mensaje personalizado. Combínalas en un mismo `main`.

8. Explica con un ejemplo propio la diferencia entre *upcasting* (siempre seguro, implícito) y *downcasting* (necesita casting explícito y puede fallar en tiempo de ejecución con `ClassCastException` si el objeto no es realmente de ese tipo).
