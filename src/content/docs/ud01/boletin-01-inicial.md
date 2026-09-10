---
title: 📝 Boletín 01 Inicial — Algorítmica y Pseudocódigo
description: Colección de 10 ejercicios prácticos fundamentales para dominar la lógica booleana, el trazado de variables en memoria, estructuras de control básicas y modularidad en pseudocódigo.
---

Este boletín contiene **10 ejercicios prácticos** diseñados para afianzar los conceptos fundamentales de la Unidad 1. Resuélvelos sobre el papel o en la herramienta **PSeInt** antes de consultar el [Boletín Inicial Resuelto](./boletin-01-inicial-resuelto/).

:::tip[📥 Descarga en PDF]
Puedes [descargar este boletín en PDF](/ProgramacionDAM/pdf/ud01/Boletin-01-Inicial.pdf) para imprimirlo o trabajar sin conexión. También dispones del [PDF con las soluciones completas](/ProgramacionDAM/pdf/ud01/Boletin-01-Inicial-Resuelto.pdf).
:::

---

### Ejercicio 1: Traza manual con variables en memoria y truco del intercambio (*Swap*)
Dadas tres variables enteras `A`, `B` y `C`, cuyos valores iniciales son `A ← 5`, `B ← 2` y `C ← 10`:

1. Indica el valor que contendrá cada una de las tres variables tras ejecutar secuencialmente las siguientes instrucciones rellenando una tabla de traza de variables paso a paso:
   ```text
   A ← A + B
   B ← C - A
   C ← A × B
   A ← C MOD B
   B ← B + 1
   ```

2. Analiza ahora el siguiente algoritmo de tres pasos con dos variables iniciales `X ← 7` e `Y ← 4`:
   ```text
   X ← X + Y
   Y ← X - Y
   X ← X - Y
   ```
   * Elabora la tabla de traza indicando el valor de `X` e `Y` en cada línea.
   * ¿Qué efecto final ha tenido esta secuencia sobre los valores de ambas variables? ¿Por qué es un truco célebre en la historia de la algorítmica?

---

### Ejercicio 2: Evaluación de expresiones aritméticas y precedencia
Evalúa manualmente el resultado exacto de las siguientes expresiones aritméticas respetando la jerarquía universal de operadores (sin utilizar calculadora ni ordenador):

* a) `25 MOD 4`
* b) `7 / 2 + 2.5`
* c) `(4 + 6) × 3 + 2 × (5 - 1)`
* d) `5 / 2 + 17 MOD 3`
* e) `3 + 4 × 2`
* f) `(3 + 4) × 2`
* g) `10 MOD 3 + 1`
* h) `2 ^ 3 + 1`
* i) `37 / 4 × 4 - 2`

---

### Ejercicio 3: Evaluación de expresiones lógicas y álgebra booleana
Determina si cada una de las siguientes expresiones lógicas se evalúa a `Verdadero` o `Falso`, indicando el orden de evaluación:

* a) `7 ≥ 5 O 27 ≠ 8`
* b) `(45 ≤ 7) O NO (5 ≥ 7)`
* c) `(25 ≥ 7) Y NO (7 ≤ 2)`
* d) `('H' < 'J') Y ('9' ≠ '7')`
* e) `10 + 4 < 15 - 3 O 2 × 5 + 1 > 14 - 2 × 2`
* f) `4 × 2 ≤ 8 O 2 × 2 < 5 Y 4 > 3 + 1`
* g) `10 ≤ 2 × 5 Y 3 < 4 O NO (8 > 7) Y 3 × 2 ≤ 4 × 2 - 1`

---

### Ejercicio 4: Algoritmo de la vida cotidiana en lenguaje estructurado
Describe un algoritmo en lenguaje natural estructurado, no ambiguo, preciso y finito para realizar una de las siguientes tareas cotidianas:
* **Opción A:** Cambiar una rueda pinchada de un vehículo (contemplando seguridad, elevación con gato y orden de afloje/apriete en cruz).
* **Opción B:** La preparación completa de una tortilla de patatas tradicional.

Identifica con claridad cuáles son los datos de **Entrada (Input)**, los pasos ordenados del **Procesamiento** y el resultado de **Salida (Output)**.

---

### Ejercicio 5: Cálculo de área y perímetro de un rectángulo
Diseña un algoritmo en pseudocódigo (compatible con PSeInt con perfil estricto) que solicite al usuario por teclado la base y la altura de un rectángulo (números reales positivos). El algoritmo debe calcular y mostrar por pantalla:
1. El perímetro del rectángulo: `2 × (base + altura)`.
2. El área del rectángulo: `base × altura`.

---

### Ejercicio 6: Estructuras condicionales simples y dobles (`Si - Entonces - SiNo`)
Diseña dos algoritmos en pseudocódigo:
1. **Mayor de dos números:** Solicita dos números distintos por teclado (`num1` y `num2`) y muestra por pantalla cuál de los dos es el mayor.
2. **Control de mayoría de edad:** Solicita la edad de una persona (entero) y muestra por pantalla `"Es mayor de edad"` si tiene 18 años o más, o `"Es menor de edad"` en caso contrario.

---

### Ejercicio 7: Selección múltiple (`Según...Hacer`) para días de la semana
Diseña un algoritmo en pseudocódigo que solicite al usuario un número entero del `1` al `7` correspondiente a un día de la semana y muestre su nombre en texto:
* `1` → Lunes
* `2` → Martes
* `3` → Miércoles
* `4` → Jueves
* `5` → Viernes
* `6` → Sábado
* `7` → Domingo

Si el usuario introduce cualquier otro número fuera de ese rango, el bloque `De Otro Modo` debe mostrar el mensaje de error: `"Error: El valor introducido no corresponde a ningún día válido [1-7]"`.

---

### Ejercicio 8: Comparativa de bucles: Conteo y acumulador (`Mientras` vs `Para`)
1. Escribe un algoritmo en pseudocódigo que, utilizando un bucle `Mientras`, muestre por pantalla los números correlativos del `1` al `10`.
2. Reescribe el mismo algoritmo sustituyendo el bucle `Mientras` por un bucle `Para`. Reflexiona: ¿qué ventajas ofrece el bucle `Para` cuando conocemos de antemano el número exacto de iteraciones?
3. Amplía el algoritmo para que calcule la **suma acumulada** de los primeros `N` números enteros positivos (desde `1` hasta `N`), solicitando el valor de `N` al usuario.

---

### Ejercicio 9: Diseño modular y funciones (`esPar`)
Diseña un programa modular en pseudocódigo compuesto por:
1. Una función o subalgoritmo llamado `esPar` que reciba como parámetro formal un número entero `n` y devuelva el valor booleano `Verdadero` si el número es par o `Falso` si es impar (empleando el operador `MOD`).
2. El algoritmo principal consumidor que, mediante un bucle `Para`, recorra los números del `1` al `5`, invoque a la función `esPar` en cada vuelta y muestre por pantalla mensajes del estilo:
   * `"El número 1 es impar"`
   * `"El número 2 es par"`

---

### Ejercicio 10: Fundamentos de ingeniería del software
Responde brevemente a las siguientes cuestiones conceptuales:
1. **Clasificación de lenguajes:** Clasifica los siguientes tres lenguajes según su nivel de abstracción (alto o bajo nivel) y según su modelo de ejecución (compilado, interpretado o híbrido): **Ensamblador**, **Python** y **Java**.
2. **Ciclo de vida del software:** Ordena cronológicamente las cinco fases clásicas del desarrollo de software: *Pruebas (QA)*, *Análisis de Requisitos*, *Despliegue y Mantenimiento*, *Diseño y Arquitectura*, *Implementación (Codificación)*.
