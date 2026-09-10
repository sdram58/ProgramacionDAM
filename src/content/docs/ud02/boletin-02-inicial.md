---
title: 📝 Boletín 02 Inicial — Primeros Pasos con Java
description: 10 ejercicios fundamentales en Java LTS con IntelliJ IDEA sobre tipos primitivos, variables, operadores aritméticos y lógicos, entrada/salida por consola y sentencias condicionales.
---

Este boletín reúne **10 ejercicios prácticos fundamentales** diseñados para familiarizarte con el entorno de desarrollo **IntelliJ IDEA**, la sintaxis del lenguaje **Java** (versión 21 LTS), el uso de la clase `Scanner`, la salida formateada con `printf` y las bifurcaciones condicionales `if-else` y `switch`.

Antes de consultar el [Boletín Inicial Resuelto](./boletin-02-inicial-resuelto/), implementa y ejecuta cada solución en tu propio proyecto de IntelliJ IDEA.

---

### Ejercicio 1: Saludo Personalizado y Formato de Ficha de Usuario
Escribe un programa en Java que solicite al usuario a través del teclado:
1. Su nombre de pila.
2. Su primer apellido.
3. Su edad (número entero).
4. La inicial de su grupo de DAM (un solo carácter: 'A', 'B', etc.).
5. La cuota mensual del centro en euros (número con decimales).

El programa debe imprimir una ficha resumen limpia y tabulada empleando **únicamente una llamada a `System.out.printf()`** con los especificadores de formato `%s`, `%d`, `%c` y `%.2f`.

---

### Ejercicio 2: Calculadora Geométrica de Rectángulo y Círculo
Escribe un programa que:
1. Pida al usuario la **base** y la **altura** de un rectángulo en metros (valores decimales) y calcule su perímetro y su superficie (área).
2. Pida el **radio** de una circunferencia y calcule la longitud de la circunferencia y el área del círculo utilizando la constante `Math.PI` y el método `Math.pow()`.
3. Si el usuario introduce cualquier dimensión menor o igual a cero (`<= 0`), el programa debe emitir un mensaje de advertencia y abortar el cálculo.

---

### Ejercicio 3: Conversor Bidireccional de Escalas Térmicas
Escribe un programa que implemente la conversión entre grados Celsius (°C) y grados Fahrenheit (°F):
* Fórmula de conversión a Fahrenheit: `F = (C × 9 / 5) + 32`
* Fórmula de conversión a Celsius: `C = (F - 32) × 5 / 9`

El programa debe:
1. Pedir una temperatura en Celsius y mostrar su equivalente en Fahrenheit con 2 decimales.
2. Pedir una temperatura en Fahrenheit y mostrar su equivalente en Celsius con 2 decimales.

:::tip[¡Cuidado con la trampa de la división entera!]
En Java, si escribes `9 / 5` o `5 / 9`, el compilador realiza una división entera truncada (`5 / 9 = 0`). Asegúrate de forzar literales de coma flotante (`9.0 / 5.0` o `5.0f / 9.0f`).
:::

---

### Ejercicio 4: Calificación Final Ponderada
En el módulo de Programación DAM, la nota final de una evaluación se desglosa según las siguientes ponderaciones:
* Exámenes teóricos: **30%**
* Prácticas y proyectos en IntelliJ IDEA: **50%**
* Actitud, asistencia y participación: **20%**

Diseña un programa que solicite las tres notas (valores decimales entre `0.0` y `10.0`), calcule la nota final ponderada e imprima la calificación cualitativa oficial:
* Menor que 5.0: **Suspenso**
* De 5.0 a 6.9: **Aprobado**
* De 7.0 a 8.9: **Notable**
* De 9.0 a 10.0: **Sobresaliente**

Si alguna de las notas introducidas no se encuentra en el rango `[0.0, 10.0]`, el programa debe avisar del error y no mostrar ninguna media.

---

### Ejercicio 5: Facturación con Descuentos Escalonados e IVA
Una tienda de componentes informáticos aplica descuentos en función del importe total bruto de la compra:
* Si la compra es menor de 100 €, no hay descuento (0%).
* Si la compra es de 100 € o más pero menor de 300 €, se aplica un **5% de descuento**.
* Si la compra es de 300 € o más, se aplica un **12% de descuento**.

Escribe un programa que solicite el precio unitario del producto y el número de unidades compradas. A continuación, debe calcular y mostrar:
1. Importe bruto inicial.
2. Porcentaje y euros de descuento aplicado.
3. Base imponible con descuento.
4. Importe de IVA (al 21%).
5. Importe final a pagar por el cliente.

Todos los importes monetarios deben mostrarse alineados y con exactamente 2 cifras decimales.

---

### Ejercicio 6: Descomposición Temporal de Segundos
Escribe un programa que solicite al usuario una cantidad entera de segundos (por ejemplo, `7534` segundos) y la descomponga en sus unidades equivalentes:
* **Horas completas**
* **Minutos restantes**
* **Segundos finales**

*Ejemplo de salida:* `7534 segundos equivalen a: 2 horas, 5 minutos y 34 segundos.`
Debes utilizar exclusivamente los operadores de división entera (`/`) y módulo (`%`).

---

### Ejercicio 7: Detector de Paridad y Múltiplos
Escribe un programa que solicite un número entero por teclado e informe:
1. Si el número es **positivo**, **negativo** o **cero**.
2. Si el número es **par** o **impar** (utilizando el operador `%`).
3. Si el número es **múltiplo de 3**, **múltiplo de 5** o **múltiplo de ambos a la vez** (como el 15, 30, etc.).

---

### Ejercicio 8: Días del Mes con `switch` Moderno
Escribe un programa que pida al usuario el número de un mes (un entero del 1 al 12) y devuelva cuántos días tiene ese mes (asumiendo un año no bisiesto de 28 días para febrero).
1. Resuelve el problema utilizando una **expresión `switch` moderna** con sintaxis de flecha (`->`) y agrupación de casos por comas (`case 1, 3, 5, 7, 8, 10, 12 -> 31;`).
2. Si el número introducido no está entre 1 y 12, debe devolver `-1` y mostrar un mensaje de mes inválido.

---

### Ejercicio 9: El Misterio del Salto de Línea en Scanner
Escribe un programa para registrar la matrícula de un vehículo y el nombre completo de su titular:
1. Solicita primero el número de bastidor (un número entero `int` leído con `nextInt()`).
2. A continuación, solicita el nombre completo del titular (una cadena leída con `nextLine()`).

Implementa el programa prestando atención a limpiar el carácter de salto de línea residual (`
`) que queda en el buffer del teclado tras leer el número. Muestra por pantalla los datos leídos para verificar que el nombre no se ha saltado.

---

### Ejercicio 10: Simulador de Lanzamiento de Dados
Escribe un programa que simule el lanzamiento de dos dados de juego:
* Dado 1: Un dado tradicional de 6 caras (valores del 1 al 6).
* Dado 2: Un dado de rol de 20 caras (D20, valores del 1 al 20).

Utiliza la función `Math.random()` con la fórmula general `(int)(Math.random() * (max - min + 1)) + min`.
El programa debe:
1. Generar la tirada de ambos dados.
2. Calcular y mostrar la suma total de ambos.
3. Si en el dado de 20 caras se obtiene un `20`, debe imprimir: *"¡Éxito Crítico!"*. Si se obtiene un `1`, debe imprimir: *"¡Pifia Crítica!"*.\n