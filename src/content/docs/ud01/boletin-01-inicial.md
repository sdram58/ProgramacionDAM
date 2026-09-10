---
title: 📝 Boletín 01 Inicial — Algorítmica y Pseudocódigo
description: Colección de 8 ejercicios prácticos fundamentales para dominar la lógica booleana, el trazado de variables y las estructuras básicas en pseudocódigo.
---

Este boletín contiene **8 ejercicios prácticos** diseñados para afianzar los conceptos fundamentales de la Unidad 1. Resuélvelos sobre el papel o en la herramienta PSeInt antes de consultar las soluciones.

---

### Ejercicio 1: Traza manual con variables en memoria
Dadas tres variables enteras `A`, `B` y `C`, cuyos valores iniciales son `A ← 5`, `B ← 2` y `C ← 10`:
Indica el valor que contendrá cada una de las tres variables tras ejecutar secuencialmente las siguientes instrucciones:

1. `A ← A + B`
2. `B ← C - A`
3. `C ← A × B`
4. `A ← C MOD B`
5. `B ← B + 1`

Presenta tu respuesta rellenando una tabla de traza de variables paso a paso.

---

### Ejercicio 2: Evaluación de expresiones aritméticas y precedencia
Evalúa manualmente el resultado exacto de las siguientes expresiones aritméticas respetando la jerarquía de operadores:

* a) `25 % 4`
* b) `7 / 2 + 2.5`
* c) `(4 + 6) × 3 + 2 × (5 - 1)`
* d) `5 / 2 + 17 % 3`
* e) `27 % 4 + 15 / 4`
* f) `37 / 4 × 4 - 2`

---

### Ejercicio 3: Evaluación de expresiones lógicas y booleanas
Determina si cada una de las siguientes expresiones lógicas se evalúa a `Verdadero` o `Falso`:

* a) `7 ≥ 5 O 27 ≠ 8`
* b) `(45 ≤ 7) O NO (5 ≥ 7)`
* c) `(25 ≥ 7) Y NO (7 ≤ 2)`
* d) `('H' < 'J') Y ('9' ≠ '7')`
* e) `10 + 4 < 15 - 3 O 2 × 5 + 1 > 14 - 2 × 2`
* f) `4 × 2 ≤ 8 O 2 × 2 < 5 Y 4 > 3 + 1`
* g) `10 ≤ 2 × 5 Y 3 < 4 O NO (8 > 7) Y 3 × 2 ≤ 4 × 2 - 1`

---

### Ejercicio 4: Algoritmo de la vida cotidiana en lenguaje natural estructurado
Describe un algoritmo detallado, preciso y finito para **cambiar una rueda pinchada de un vehículo**. 
El algoritmo debe contemplar medidas de seguridad (freno de mano, triángulos/luz de emergencia), el uso de herramientas (gato, llave de cruz, rueda de repuesto) y el orden correcto de aflojar y apretar los tornillos.

---

### Ejercicio 5: Cálculo de área y perímetro de un rectángulo
Diseña un algoritmo en pseudocódigo (PSeInt) que solicite al usuario por teclado la base y la altura de un rectángulo (números reales). El algoritmo debe calcular y mostrar por pantalla:
1. El perímetro del rectángulo: `2 × (base + altura)`.
2. El área del rectángulo: `base × altura`.

---

### Ejercicio 6: Clasificador de número par o impar
Diseña un algoritmo en pseudocódigo que pida un número entero al usuario. Utilizando el operador módulo (`MOD`), debe determinar si el número introducido es **par** o **impar**, mostrando el mensaje correspondiente por pantalla. Si el número introducido es cero, debe indicar además que es neutro.

---

### Ejercicio 7: Conversor de tiempo (segundos a horas, minutos y segundos)
Diseña un algoritmo en pseudocódigo que reciba una cantidad total de segundos (entero positivo) y la descomponga en su equivalente en **horas**, **minutos** y **segundos sobrantes**.
*Ejemplo:* Si el usuario introduce `3665` segundos, el programa debe mostrar: `1 hora, 1 minuto y 5 segundos`.

---

### Ejercicio 8: Contador y acumulador con bucle Mientras
Diseña un algoritmo en pseudocódigo que calcule la suma de los primeros `N` números enteros positivos (desde `1` hasta `N`), donde `N` es un valor solicitado al usuario por teclado. Debes utilizar obligatoriamente una estructura iterativa `Mientras`.
*Ejemplo:* Si `N = 5`, el resultado debe ser `1 + 2 + 3 + 4 + 5 = 15`.
