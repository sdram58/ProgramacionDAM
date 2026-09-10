---
title: 🧠 Boletín 02 Extras — Retos y Plataformas Competitivas
description: Retos de algorítmica matemática y lógica en Java 21 LTS sin bucles complejos (DNI oficial, año bisiesto gregoriano, simulación probabilística y números gigantes con BigInteger).
---

En este boletín extra nos enfrentaremos a **problemas reales y retos de plataformas de programación competitiva** (CodeWars, Acepta el Reto, Project Euler) utilizando exclusivamente los conceptos aprendidos en la Unidad 2: aritmética modular, sentencias condicionales rigurosas, manipulación de `String` y tipos de precisión extendida (`BigInteger`).

---

### Reto 1: Algoritmo Oficial de Verificación de DNI Español
*(Inspirado en el procedimiento oficial del Ministerio del Interior y el problema clásico de Acepta el Reto)*

#### Enunciado
El Documento Nacional de Identidad (DNI) español consta de un número de 8 dígitos seguido de una letra de control calculada mediante un algoritmo matemático que detecta erratas al teclear.

La letra se obtiene dividiendo el número de 8 cifras entre `23` y utilizando el **resto de la división entera (módulo 23)** como índice (del 0 al 22) en la siguiente tabla alfanumérica fija:

```text
Resto:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22
Letra:  T  R  W  A  G  M  Y  F  P  D  X  B  N  J  Z  S  Q  V  H  L  C  K  E
Cadena: "TRWAGMYFPDXBNJZSQVHLCKE"
```

Escribe un programa en Java que:
1. Pida al usuario su número de DNI (como entero de 8 dígitos).
2. Calcule la letra oficial correspondiente utilizando el operador `%` y el método `charAt()` sobre la cadena de referencia.
3. Solicite que introduzca la letra que figura en su documento físico.
4. Compare si la letra coincide (ignorando mayúsculas y minúsculas).
5. Muestre un mensaje indicando si el DNI es **válido** o si existe una **incongruencia**.

:::tip[💡 Pistas para la resolución]
* Cadena clave: `String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";`
* Índice: `int indice = numeroDni % 23;`
* Letra esperada: `char letraCorrecta = LETRAS.charAt(indice);`
:::

---

### Reto 2: El Algoritmo Astronómico del Año Bisiesto Gregoriano
*(Clásico de CodeWars y entrevistas técnicas)*

#### Enunciado
El calendario gregoriano universal establece que un año de 365 días debe añadir un día bisiesto (29 de febrero) cada 4 años para compensar el desfase de la órbita de la Tierra alrededor del Sol. Sin embargo, para evitar un exceso acumulativo secular, la regla formal completa tiene tres condiciones:

1. Un año es bisiesto si es **divisible entre 4**.
2. **EXCEPTO** si es divisible entre **100**, en cuyo caso **NO** es bisiesto.
3. **A MENOS QUE** sea también divisible entre **400**, en cuyo caso **SÍ** vuelve a ser bisiesto.

*Ejemplos:*
* `2024`: Bisiesto (divisible por 4 y no por 100).
* `1900`: **No** bisiesto (divisible por 4 y por 100, pero no por 400).
* `2000`: Bisiesto (divisible por 400).
* `2100`: **No** bisiesto.

Escribe un programa que solicite un año cualquiera y determine si es bisiesto utilizando una **única expresión booleana** con operadores lógicos (`&&`, `||`, `!`).

:::tip[💡 Pistas para la resolución]
La condición matemática canónica se resume en:
`(anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)`
:::

---

### Reto 3: Distribución Probabilística de Dos Dados en Juegos de Tablero
*(Fundamentos de simulación Montecarlo y combinatoria)*

#### Enunciado
En juegos como *Catán*, *Monopoly* o *Craps*, se lanzan simultáneamente dos dados de 6 caras y se suman sus puntuaciones. Las sumas van desde `2` (1+1) hasta `12` (6+6).

No todas las sumas son equiprobables:
* La suma `7` tiene **6 combinaciones favorables** de 36 posibles (1+6, 2+5, 3+4, 4+3, 5+2, 6+1), con una probabilidad del `16.67%`.
* Las sumas `2` y `12` solo tienen **1 combinación favorable** (1+1 y 6+6), con una probabilidad de solo `2.78%`.

Escribe un programa que:
1. Simule el lanzamiento independiente de dos dados usando `Math.random()`.
2. Calcule la suma.
3. Mediante una expresión `switch` moderna, imprima la puntuación obtenida y su probabilidad teórica asociada en forma de porcentaje.

---

### Reto 4: Factoriales Astronómicos con `BigInteger`
*(Problema clásico de Project Euler / CodeWars 4-Kyu)*

#### Enunciado
El factorial de un número entero positivo `n` (representado como `n!`) es el producto de todos los números enteros desde 1 hasta `n`:
`n! = 1 × 2 × 3 × ... × n`

* `5! = 120`
* `10! = 3.628.800`
* `20! = 2.432.902.008.176.640.000` (ya casi satura el límite de un `long` de 64 bits)
* `21!` desborda totalmente la capacidad de cualquier tipo primitivo de Java (`Long.MAX_VALUE`).

Escribe un programa en Java que solicite al usuario calcular el factorial de un número como `50` o `100`.
Utiliza la clase `java.math.BigInteger` para realizar la multiplicación encadenada sin ningún límite de precisión numérica, mostrando la cifra gigantesca resultante en consola junto con el número total de dígitos que contiene su representación textual.

---

### Reto 5: Algoritmo Voraz para el Cambio Monetario con Céntimos Exactos
*(Problema de optimización financiera sin errores de coma flotante)*

#### Enunciado
Una de las peores trampas en programación financiera es operar con importes monetarios en variables `double` (debido a que `0.1 + 0.2` da `0.30000000000000004`).

Escribe un programa que pida el coste total de una compra en euros (por ejemplo `3.47 €`) y la cantidad entregada por el cliente (por ejemplo `10.00 €`).
1. Convierte inmediatamente ambos importes a **céntimos enteros** multiplicando por 100 y redondeando con `Math.round()` a tipo `int`.
2. Calcula la diferencia a devolver en céntimos enteros (en este caso, `653` céntimos).
3. Desglosa el cambio utilizando el menor número de monedas posible: `200` (2 €), `100` (1 €), `50`, `20`, `10`, `5`, `2` y `1` céntimo.\n