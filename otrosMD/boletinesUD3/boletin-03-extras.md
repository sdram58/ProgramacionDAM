---
title: Boletín U03 — Extras
description: Primeros retos de CodeWars y AceptaElReto, con pistas
---

# 🔥 Boletín U03 — Extras

> A partir de aquí ya tienes bucles, arrays, métodos y excepciones — las piezas suficientes para empezar con retos de plataformas reales de programación competitiva. Busca en [CodeWars](https://www.codewars.com/) katas de nivel **8 kyu** o **7 kyu** (son los niveles más fáciles: en CodeWars, cuanto más alto el número, más fácil) etiquetados como `Fundamentals` o `Arrays`, y en [AceptaElReto](https://www.acepta.com/reto/) busca por la categoría **"Iniciación"**. Elige a mano un par de retos que te llamen la atención y resuélvelos en Java.

Mientras tanto, aquí tienes tres retos en el mismo estilo, para calentar:

## Reto 1: FizzBuzz

Escribe un método que, para cada número del 1 al 100, imprima "Fizz" si es múltiplo de 3, "Buzz" si es múltiplo de 5, "FizzBuzz" si es múltiplo de ambos, y el propio número en cualquier otro caso.

:::tip[Pista]
Comprueba primero la condición más restrictiva (múltiplo de ambos) — si la comprobación del múltiplo de 3 fuera primera y no tuvieras cuidado, nunca llegarías a comprobar "los dos a la vez".
:::

<details>
<summary>💡 Solución</summary>

```java
for (int i = 1; i <= 100; i++) {
    if (i % 3 == 0 && i % 5 == 0) {
        System.out.println("FizzBuzz");
    } else if (i % 3 == 0) {
        System.out.println("Fizz");
    } else if (i % 5 == 0) {
        System.out.println("Buzz");
    } else {
        System.out.println(i);
    }
}
```
</details>

## Reto 2: El elemento que falta

Dado un array que contiene todos los números del 1 al `n` **excepto uno**, en cualquier orden, encuentra el número que falta — sin usar bucles anidados (debe funcionar en un único recorrido del array).

:::tip[Pista]
Si tuvieras todos los números del 1 a `n`, ¿cuánto sumarían? Compáralo con lo que realmente suman los números que sí están.
:::

<details>
<summary>💡 Solución</summary>

```java
static int encontrarFaltante(int[] array, int n) {
    int sumaEsperada = n * (n + 1) / 2;   // fórmula de la suma de 1 a n
    int sumaReal = 0;
    for (int numero : array) {
        sumaReal += numero;
    }
    return sumaEsperada - sumaReal;
}
```

Este truco (la fórmula de Gauss para la suma de los primeros `n` números) evita tener que ordenar el array o compararlo contra una lista completa — resuelve el problema en un único recorrido.
</details>

## Reto 3: Comprime la cadena

Escribe un método que comprima una cadena reemplazando cada secuencia de caracteres repetidos consecutivos por el carácter seguido del número de veces que se repite (por ejemplo, `"aaabbc"` → `"a3b2c1"`). Si la versión comprimida no es más corta que la original, devuelve la cadena original sin cambios.

<details>
<summary>💡 Solución</summary>

```java
static String comprimir(String texto) {
    StringBuilder resultado = new StringBuilder();
    int contador = 1;
    for (int i = 1; i <= texto.length(); i++) {
        if (i < texto.length() && texto.charAt(i) == texto.charAt(i - 1)) {
            contador++;
        } else {
            resultado.append(texto.charAt(i - 1)).append(contador);
            contador = 1;
        }
    }
    return resultado.length() < texto.length() ? resultado.toString() : texto;
}
```

Usamos `StringBuilder` en vez de concatenar con `+` en el bucle porque `String` es inmutable — cada concatenación con `+` crearía un `String` nuevo en cada vuelta, mientras que `StringBuilder` modifica el mismo objeto en memoria.
</details>
