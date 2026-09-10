---
title: 📝 Boletín 01 Intermedio — Algorítmica y Pseudocódigo
description: 10 retos algorítmicos aplicados que combinan bucles anidados, validaciones robustas, acumuladores, descomposición de dígitos, años bisiestos y algoritmos numéricos clásicos.
---

Este boletín contiene **10 ejercicios de nivel intermedio** para poner a prueba tu capacidad de estructurar algoritmos más complejos y eficientes. Intenta resolverlos antes de consultar el boletín resuelto.

---

### Ejercicio 1: Validación estricta de entrada con bucle Repetir
Diseña un algoritmo en pseudocódigo que solicite al usuario una calificación académica. El programa debe obligar al usuario a repetir la introducción de la nota mientras el valor introducido esté fuera del rango válido `[0.0, 10.0]`. Una vez introducida una nota correcta, el algoritmo debe clasificarla e imprimir su calificación cualitativa:
* `[0.0, 5.0)`: Suspenso
* `[5.0, 6.0)`: Aprobado
* `[6.0, 7.0)`: Bien
* `[7.0, 9.0)`: Notable
* `[9.0, 10.0]`: Sobresaliente

---

### Ejercicio 2: Intercambio de dos variables (Swap)
Dadas dos variables numéricas `A` y `B` introducidas por el usuario:
1. Diseña un algoritmo que intercambie sus contenidos utilizando una variable auxiliar `TEMP`.
2. Como reto adicional: ¿serías capaz de intercambiar el valor de `A` y `B` **sin utilizar ninguna variable auxiliar**, empleando únicamente sumas y restas?

---

### Ejercicio 3: Determinación del número mayor entre tres valores
Diseña un algoritmo que lea tres números reales diferentes por teclado (`N1`, `N2`, `N3`) y determine cuál de ellos es el **mayor**. Si el usuario introduce valores repetidos que compiten por el máximo, el programa debe indicarlo adecuadamente.

---

### Ejercicio 4: Nómina semanal con horas extras e IRPF
Una empresa paga a sus trabajadores a razón de `15 €` por hora ordinaria. La jornada laboral estándar es de `35 horas` semanales. Cualquier hora trabajada por encima de las 35 se considera hora extraordinaria y se abona con un incremento del `50%` sobre la tarifa ordinaria (`22.50 €/hora`).
* Si el salario bruto resultante es menor o igual a `500 €`, la retención fiscal de IRPF es del `0%`.
* Si supera los `500 €`, se aplica una retención del `12%` sobre el importe que exceda de esos 500 €.
Diseña el algoritmo que reciba las horas semanales trabajadas y muestre: salario bruto, retención aplicada y salario neto a percibir.

---

### Ejercicio 5: Generador de tablas de multiplicar anidadas
Diseña un algoritmo que muestre por pantalla las tablas de multiplicar del `1` al `5`, desde el factor `1` hasta el `10`, utilizando **dos bucles `Para` anidados**. Debe presentarse una cabecera clara para cada tabla.

---

### Ejercicio 6: Cálculo de potencias sin usar el operador de exponenciación
Dados dos números enteros positivos pedidos al usuario: la `base` y el `exponente`:
Diseña un algoritmo que calcule el valor de `base^exponente` utilizando únicamente multiplicaciones sucesivas mediante un bucle, sin recurrir a la función de potencia interna del sistema. Ten en cuenta que cualquier base elevada a `0` es igual a `1`.

---

### Ejercicio 7: Contador y sumador de dígitos de un número
Diseña un algoritmo que pida un número entero positivo cualquiera (por ejemplo, `4825`) y calcule:
1. La cantidad total de dígitos que tiene (en el ejemplo: `4` dígitos).
2. La suma de todos sus dígitos (`4 + 8 + 2 + 5 = 19`).
*Pista:* Emplea sucesivas divisiones enteras entre `10` y el operador módulo `MOD 10`.

---

### Ejercicio 8: Comprobador de números primos
Un número entero mayor que 1 es **primo** si y solo si es divisible únicamente por 1 y por sí mismo.
Diseña un algoritmo que pida un número entero positivo `N` (mayor que 1) y determine mediante un bucle si es primo o compuesto, optimizando las comprobaciones para no realizar iteraciones innecesarias.

---

### Ejercicio 9: Determinación de Año Bisiesto (Regla Gregoriana)
En el calendario gregoriano, un año es **bisiesto** (tiene 366 días) si es divisible entre `4`, excepto si es divisible entre `100`, a menos que también sea divisible entre `400`.
*Ejemplos:*
* El año `2024` es bisiesto (divisible por 4 y no por 100).
* El año `1900` **no** fue bisiesto (divisible por 100 pero no por 400).
* El año `2000` **sí** fue bisiesto (divisible por 400).

Diseña un algoritmo que pida un año al usuario y determine mediante una única condición lógica combinada (usando `MOD`, `Y`, `O`) si el año es bisiesto o común.

---

### Ejercicio 10: Desglose óptimo de billetes en cajero automático
Un cajero automático dispone de billetes de `50 €`, `20 €`, `10 €` y `5 €`. Diseña un algoritmo que pida al usuario una cantidad entera en euros a retirar (que debe ser múltiplo de 5) y calcule el número mínimo de billetes de cada denominación necesarios para entregar dicha cantidad. Si el usuario introduce una cantidad que no termine en 0 o 5, debe advertir del error.
*Ejemplo:* Para `285 €`, el resultado debe ser: 5 billetes de 50 €, 1 billete de 20 €, 1 billete de 10 € y 1 billete de 5 €.
