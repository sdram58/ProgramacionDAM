---
title: Boletín U01 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre algoritmos y pseudocódigo
---

# 📝 Boletín U01 — Intermedio

1. Escribe en pseudocódigo un algoritmo que pida números por teclado (hasta que el usuario introduzca un 0) y al final muestre cuántos de ellos eran pares y cuántos impares.

2. Escribe un subalgoritmo `factorial(n)` que calcule el factorial de un número usando una estructura iterativa (no recursiva).

3. Traza a mano el siguiente algoritmo con `n = 4` y anota el valor que devuelve:
   ```
   SubAlgoritmo r <- misterio(n)
       r = 1
       Para i = 1 Hasta n Hacer
           r = r * i
       FinPara
   FinSubAlgoritmo
   ```
   ¿Qué calcula realmente `misterio`?

4. Escribe un algoritmo que pida 5 números y muestre el mayor y el menor de todos ellos, sin usar arrays (solo variables sueltas y una estructura `Para`).

5. Escribe un subalgoritmo `esPrimo(n)` que devuelva `Verdadero` si `n` es un número primo. Pista: un número es primo si no es divisible por ningún número entre 2 y `n-1`.

6. Diseña (en pseudocódigo, con subalgoritmos) un programa modular que calcule el IMC (índice de masa corporal) de una persona: un subalgoritmo que pida y valide el peso (>0), otro que pida y valide la altura (>0), y un tercero que calcule y muestre el IMC y su categoría (bajo peso / normal / sobrepeso / obesidad).

7. Explica con tus propias palabras la diferencia entre un algoritmo **compilado** y uno **interpretado**, y qué papel juega el bytecode de Java en ese esquema — ¿es Java 100% compilado, 100% interpretado, o ninguna de las dos cosas?

8. Un subalgoritmo puede llamarse a sí mismo — eso se llama **recursividad**. Escribe (en pseudocódigo) una versión **recursiva** del `factorial(n)` del ejercicio 2, y compárala con tu versión iterativa: ¿cuál te resulta más fácil de leer?

9. Clasifica cada uno de estos fallos como error **sintáctico**, **semántico/lógico** o **de ejecución**, y justifica por qué: (a) olvidar un `FinSi` de cierre; (b) un algoritmo que calcula el área de un círculo usando la fórmula del área de un cuadrado; (c) un algoritmo que divide un total entre el número de participantes, cuando ese número puede llegar a valer 0.

10. Java es un lenguaje **híbrido** (orientado a objetos, con soporte para el paradigma funcional a través de la API Stream) y usa una **máquina virtual** en vez de compilar directamente a código máquina nativo. Explica, con tus propias palabras, qué ventaja práctica tiene esto frente a un lenguaje puramente compilado como C, y qué ventaja tiene frente a uno puramente interpretado como Python.
