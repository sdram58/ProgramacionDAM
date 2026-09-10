---
title: 📝 Boletín 01 Inicial (Resuelto) — Algorítmica y Pseudocódigo
description: Soluciones detalladas y paso a paso para los 8 ejercicios del Boletín Inicial de la Unidad 1.
---

A continuación se detallan las soluciones completas y razonadas de cada uno de los ejercicios propuestos en el [Boletín Inicial](/ProgramacionDAM/ud01/boletin-01-inicial/).

---

### Solución al Ejercicio 1: Traza manual con variables en memoria

Valores iniciales: `A = 5`, `B = 2`, `C = 10`.

| Paso | Instrucción ejecutada | `A` | `B` | `C` | Explicación del cálculo |
|:---:|---|:---:|:---:|:---:|---|
| **0** | *Estado inicial* | **5** | **2** | **10** | Asignaciones iniciales |
| **1** | `A ← A + B` | **7** | 2 | 10 | `5 + 2 = 7` |
| **2** | `B ← C - A` | 7 | **3** | 10 | `10 - 7 = 3` |
| **3** | `C ← A × B` | 7 | 3 | **21** | `7 × 3 = 21` |
| **4** | `A ← C MOD B` | **0** | 3 | 21 | `21 MOD 3 = 0` (21 es divisible exactamente entre 3) |
| **5** | `B ← B + 1` | 0 | **4** | 21 | `3 + 1 = 4` |

**Resultado final:** `A = 0`, `B = 4`, `C = 21`.

---

### Solución al Ejercicio 2: Evaluación de expresiones aritméticas y precedencia

* **a) `25 % 4`**
  * `25 = 4 × 6 + 1` → Resto = **`1`**.
* **b) `7 / 2 + 2.5`**
  * Si la división es real: `3.5 + 2.5 =` **`6.0`**. Si es división entera entera truncada: `3 + 2.5 = 5.5`. En PSeInt/lenguajes estándar con `/` real el resultado es **`6.0`**.
* **c) `(4 + 6) × 3 + 2 × (5 - 1)`**
  * Paréntesis primero: `10 × 3 + 2 × 4`
  * Multiplicaciones: `30 + 8 =` **`38`**.
* **d) `5 / 2 + 17 % 3`**
  * `5 / 2 = 2.5`
  * `17 % 3 = 2` (porque 17 = 3×5 + 2)
  * Suma: `2.5 + 2 =` **`4.5`**.
* **e) `27 % 4 + 15 / 4`**
  * `27 % 4 = 3` (27 = 4×6 + 3)
  * `15 / 4 = 3.75`
  * Suma: `3 + 3.75 =` **`6.75`**.
* **f) `37 / 4 × 4 - 2`**
  * De izquierda a derecha para operadores con misma precedencia (`/` y `×`):
  * `(37 / 4) × 4 - 2 = 9.25 × 4 - 2 = 37 - 2 =` **`35`**.

---

### Solución al Ejercicio 3: Evaluación de expresiones lógicas y booleanas

* **a) `7 ≥ 5 O 27 ≠ 8`**
  * `7 ≥ 5` es `Verdadero`. Por cortocircuito en el operador `O`, la expresión es **`Verdadero`**.
* **b) `(45 ≤ 7) O NO (5 ≥ 7)`**
  * `45 ≤ 7` es `Falso`.
  * `5 ≥ 7` es `Falso` → `NO Falso` es `Verdadero`.
  * `Falso O Verdadero` → **`Verdadero`**.
* **c) `(25 ≥ 7) Y NO (7 ≤ 2)`**
  * `25 ≥ 7` es `Verdadero`.
  * `7 ≤ 2` es `Falso` → `NO Falso` es `Verdadero`.
  * `Verdadero Y Verdadero` → **`Verdadero`**.
* **d) `('H' < 'J') Y ('9' ≠ '7')`**
  * En orden alfabético/ASCII, la `'H'` va antes que la `'J'` (`Verdadero`).
  * El carácter `'9'` es distinto de `'7'` (`Verdadero`).
  * `Verdadero Y Verdadero` → **`Verdadero`**.
* **e) `10 + 4 < 15 - 3 O 2 × 5 + 1 > 14 - 2 × 2`**
  * Izquierda: `14 < 12` → `Falso`.
  * Derecha: `10 + 1 > 14 - 4` → `11 > 10` → `Verdadero`.
  * `Falso O Verdadero` → **`Verdadero`**.
* **f) `4 × 2 ≤ 8 O 2 × 2 < 5 Y 4 > 3 + 1`**
  * `8 ≤ 8` es `Verdadero`.
  * Como el operador `O` tiene menor precedencia que `Y`, evaluamos:
    `8 ≤ 8 O (4 < 5 Y 4 > 4)`
    `Verdadero O (Verdadero Y Falso)` → `Verdadero O Falso` → **`Verdadero`**.
* **g) `10 ≤ 2 × 5 Y 3 < 4 O NO (8 > 7) Y 3 × 2 ≤ 4 × 2 - 1`**
  * `10 ≤ 10` es `Verdadero`. `3 < 4` es `Verdadero`. Bloque 1 (`A Y B`): `Verdadero Y Verdadero = Verdadero`.
  * Bloque 2: `NO(Verdadero)` es `Falso`.
  * `Verdadero O Falso` → **`Verdadero`**.

---

### Solución al Ejercicio 4: Algoritmo para cambiar una rueda pinchada

```text
Algoritmo CambiarRuedaVehiculo
    1. Estacionar el vehículo en un lugar plano, firme y seguro.
    2. Poner el freno de estacionamiento (freno de mano) y engranar la 1ª marcha o posición P.
    3. Colocar la señalización de emergencia (triángulos o baliza V-16) y chaleco reflectante.
    4. Sacar del maletero la rueda de repuesto, el gato y la llave de tornillos.
    5. Aflojar levemente los tornillos de la rueda pinchada con la llave (media vuelta en cruz), SIN quitarlos.
    6. Colocar el gato en el punto de apoyo reforzado del chasis indicado en el manual.
    7. Accionar el gato hasta elevar el coche de modo que la rueda quede a 3-5 cm del suelo.
    8. Retirar completamente los tornillos y retirar la rueda pinchada.
    9. Colocar la rueda de repuesto en el eje alineando los orificios.
    10. Enroscar los tornillos manualmente hasta el tope y apretarlos ligeramente con la llave.
    11. Bajar el gato cuidadosamente hasta que la rueda toque firmemente el suelo y retirar el gato.
    12. Apretar fuertemente los tornillos con la llave aplicando fuerza en forma de estrella/cruz.
    13. Guardar la rueda averiada y las herramientas en el maletero.
FinAlgoritmo
```

---

### Solución al Ejercicio 5: Cálculo de área y perímetro de un rectángulo

```text
Algoritmo RectanguloAreaPerimetro
    Definir base, altura, perimetro, area Como Real
    
    Escribir "Introduce la base del rectángulo:"
    Leer base
    Escribir "Introduce la altura del rectángulo:"
    Leer altura
    
    Si base > 0 Y altura > 0 Entonces
        perimetro ← 2 × (base + altura)
        area ← base × altura
        
        Escribir "--- RESULTADOS ---"
        Escribir "Perímetro: ", perimetro
        Escribir "Área: ", area
    Sino
        Escribir "Error: La base y la altura deben ser valores positivos mayores que cero."
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 6: Clasificador de número par o impar

```text
Algoritmo ParImparCero
    Definir num Como Entero
    
    Escribir "Introduce un número entero:"
    Leer num
    
    Si num = 0 Entonces
        Escribir "El número introducido es CERO (neutro y par)."
    Sino
        Si num MOD 2 = 0 Entonces
            Escribir "El número ", num, " es PAR."
        Sino
            Escribir "El número ", num, " es IMPAR."
        FinSi
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 7: Conversor de tiempo (segundos a horas, minutos y segundos)

```text
Algoritmo ConversorSegundos
    Definir totalSegundos, horas, minutos, segundosRestantes Como Entero
    
    Escribir "Introduce la cantidad total de segundos:"
    Leer totalSegundos
    
    horas ← totalSegundos / 3600             // División entera: 1 hora = 3600 segundos
    segundosRestantes ← totalSegundos MOD 3600
    
    minutos ← segundosRestantes / 60         // División entera: 1 minuto = 60 segundos
    segundosRestantes ← segundosRestantes MOD 60
    
    Escribir totalSegundos, " segundos equivalen a:"
    Escribir horas, " hora(s), ", minutos, " minuto(s) y ", segundosRestantes, " segundo(s)."
FinAlgoritmo
```

---

### Solución al Ejercicio 8: Contador y acumulador con bucle Mientras

```text
Algoritmo SumaPrimerosN
    Definir N, contador, acumulador Como Entero
    
    Escribir "Introduce un número entero positivo N:"
    Leer N
    
    Si N > 0 Entonces
        acumulador ← 0
        contador ← 1
        
        Mientras contador ≤ N Hacer
            acumulador ← acumulador + contador
            contador ← contador + 1
        FinMientras
        
        Escribir "La suma de los primeros ", N, " números enteros es: ", acumulador
    Sino
        Escribir "Error: Debes introducir un valor entero positivo mayor que cero."
    FinSi
FinAlgoritmo
```
