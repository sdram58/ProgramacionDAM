---
title: Boletín U05 — Inicial
description: Ejercicios propuestos de nivel inicial sobre herencia, clases abstractas e interfaces
---

# 📝 Boletín U05 — Inicial

1. Crea una clase `Vehiculo` con atributo `marca` y método `arrancar()`, y una clase `Coche` que herede de ella y añada el atributo `numPuertas`. Crea un objeto `Coche` y demuestra que puede usar tanto sus propios métodos como los heredados.

2. Para cada par de clases, indica si la relación correcta es herencia o composición, y por qué: (a) `Biblioteca` y `Libro`; (b) `Gato` y `Mamifero`; (c) `Ordenador` y `TarjetaGrafica`; (d) `Cuadrado` y `Figura`.

3. Añade a `Vehiculo` un constructor que reciba la marca, y a `Coche` un constructor que reciba marca y número de puertas, usando `super(...)` correctamente.

4. Sobrescribe (`@Override`) el método `arrancar()` en `Coche` para que muestre un mensaje distinto al de `Vehiculo`, y demuestra con un array de `Vehiculo` (que contenga objetos `Coche` y `Vehiculo`) que se ejecuta la versión correcta según el tipo real de cada objeto.

5. Crea una clase abstracta `Empleado` con atributo `nombre` y método abstracto `calcularNomina()`. Crea dos subclases, `EmpleadoFijo` (nómina = un salario fijo) y `EmpleadoComercial` (nómina = salario base + comisión), cada una con su propia implementación.

6. Crea una interface `Imprimible` con un método `imprimir()`. Haz que dos clases sin relación entre sí (por ejemplo, `Factura` y `Etiqueta`) la implementen cada una a su manera.

7. Explica con tus propias palabras qué diferencia hay entre `extends` e `implements`, y en qué se diferencia conceptualmente heredar de una clase de implementar una interface.

8. Declara una clase `Constante` con un método marcado como `final`. Intenta sobrescribirlo desde una subclase y explica qué error da el compilador y por qué.
