---
title: Boletín U03 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre excepciones, arrays, métodos y expresiones regulares
---

# 📝 Boletín U03 — Intermedio

1. Escribe un método `boolean esPalindromo(String texto)` que compruebe si una palabra se lee igual del derecho que del revés, recorriéndola carácter a carácter (sin usar `StringBuilder.reverse()`).

2. Escribe un programa que pida un número entero por teclado y lo divida entre otro también pedido por teclado, atrapando con `try-catch` tanto el caso de que el segundo número sea 0 (`ArithmeticException`) como el caso de que el usuario no introduzca un número válido (`InputMismatchException`).

3. Declara una matriz `int[3][3]` que represente un tablero de 3 en raya, rellénala con ceros, y escribe un método que la recorra con dos bucles `for` anidados y la muestre por pantalla en forma de cuadrícula.

4. Escribe un método `int contarOcurrencias(int[] array, int valor)` que devuelva cuántas veces aparece `valor` dentro de `array`, y otro método `main` que lo pruebe con un array de tu elección.

5. Escribe un método recursivo `long potencia(int base, int exponente)` que calcule `base` elevado a `exponente` sin usar `Math.pow`. Pista: el caso base es `exponente == 0`.

6. Escribe un programa que valide, usando una expresión regular, si un texto introducido por el usuario tiene el formato de un email sencillo (texto + `@` + texto + `.` + texto, sin espacios).

7. Escribe un método `void vaciar(int[] array)` que ponga todos los elementos de un array a 0, y demuestra en el `main` que el array original **sí** queda modificado tras llamarlo (aunque el parámetro se pase "por valor").

8. Escribe un programa que use `try-catch-finally` para leer dos números y dividirlos, mostrando siempre por el `finally` el mensaje "Operación finalizada", ocurra o no un error.
