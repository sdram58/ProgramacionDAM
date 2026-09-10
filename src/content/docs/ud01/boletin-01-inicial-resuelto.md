---
title: 📝 Boletín 01 Inicial (Resuelto) — Algorítmica y Pseudocódigo
description: Soluciones completas, razonadas y verificadas paso a paso para los 10 ejercicios prácticos del Boletín Inicial de la Unidad 1.
---

A continuación se detallan las soluciones exhaustivas de los 10 ejercicios propuestos en el [Boletín Inicial](./boletin-01-inicial/).

---

### Solución al Ejercicio 1: Traza manual con variables en memoria y truco del intercambio (*Swap*)

#### 1. Traza con tres variables (`A = 5`, `B = 2`, `C = 10`):

| Paso | Instrucción ejecutada | `A` | `B` | `C` | Explicación detallada |
|:---:|---|:---:|:---:|:---:|---|
| **0** | *Estado inicial* | **5** | **2** | **10** | Valores de partida en memoria |
| **1** | `A ← A + B` | **7** | 2 | 10 | `5 + 2 = 7` (se actualiza `A`) |
| **2** | `B ← C - A` | 7 | **3** | 10 | `10 - 7 = 3` (se actualiza `B`) |
| **3** | `C ← A × B` | 7 | 3 | **21** | `7 × 3 = 21` (se actualiza `C`) |
| **4** | `A ← C MOD B` | **0** | 3 | 21 | `21 MOD 3 = 0` (21 es múltiplo exacto de 3, resto 0) |
| **5** | `B ← B + 1` | 0 | **4** | 21 | `3 + 1 = 4` (se incrementa `B`) |

* **Resultado final en memoria:** `A = 0`, `B = 4`, `C = 21`.

#### 2. Traza del truco del intercambio sin variable auxiliar (`X = 7`, `Y = 4`):

| Paso | Instrucción | `X` | `Y` | Explicación matemática |
|:---:|---|:---:|:---:|---|
| **0** | *Inicio* | **7** | **4** | Valores iniciales |
| **1** | `X ← X + Y` | **11** | 4 | `X` almacena la suma combinada: `7 + 4 = 11` |
| **2** | `Y ← X - Y` | 11 | **7** | `Y` resta su propio valor a la suma: `11 - 4 = 7` (**`Y` adopta el valor original de `X`**) |
| **3** | `X ← X - Y` | **4** | 7 | `X` resta el nuevo `Y` (el 7 original) a la suma: `11 - 7 = 4` (**`X` adopta el valor original de `Y`**) |

* **Conclusión:** Al concluir las tres operaciones, los valores se han **intercambiado exitosamente** (`X = 4`, `Y = 7`) sin haber reservado ninguna variable auxiliar `TEMP` en memoria RAM. Es un truco clásico de optimización en sistemas de memoria muy restringida.

---

### Solución al Ejercicio 2: Evaluación de expresiones aritméticas y precedencia

* **a) `25 MOD 4`**
  * División entera: `25 = 4 × 6 + 1` → Resto: **`1`**.
* **b) `7 / 2 + 2.5`**
  * La división `7 / 2` produce `3.5`. Luego: `3.5 + 2.5 =` **`6.0`**.
* **c) `(4 + 6) × 3 + 2 × (5 - 1)`**
  * Paréntesis primero: `10 × 3 + 2 × 4`
  * Multiplicaciones: `30 + 8 =` **`38`**.
* **d) `5 / 2 + 17 MOD 3`**
  * `5 / 2 = 2.5`
  * `17 MOD 3 = 2` (porque `17 = 3 × 5 + 2`)
  * Suma final: `2.5 + 2 =` **`4.5`**.
* **e) `3 + 4 × 2`**
  * La multiplicación prima sobre la suma: `3 + 8 =` **`11`**.
* **f) `(3 + 4) × 2`**
  * El paréntesis obliga a sumar primero: `7 × 2 =` **`14`**.
* **g) `10 MOD 3 + 1`**
  * El operador `MOD` tiene la misma jerarquía que la multiplicación: `10 MOD 3 = 1`. Luego: `1 + 1 =` **`2`**.
* **h) `2 ^ 3 + 1`**
  * La potenciación es prioritaria sobre el resto de operaciones aritméticas: `2³ = 8`. Luego: `8 + 1 =` **`9`**.
* **i) `37 / 4 × 4 - 2`**
  * Asociatividad de izquierda a derecha para operadores de igual precedencia (`/` y `×`):
  * `(37 / 4) × 4 - 2 = 9.25 × 4 - 2 = 37 - 2 =` **`35`**.

---

### Solución al Ejercicio 3: Evaluación de expresiones lógicas y álgebra booleana

* **a) `7 ≥ 5 O 27 ≠ 8`**
  * `7 ≥ 5` es `Verdadero`. Por evaluación en cortocircuito del operador `O`, toda la expresión es **`Verdadero`**.
* **b) `(45 ≤ 7) O NO (5 ≥ 7)`**
  * `45 ≤ 7` es `Falso`.
  * `5 ≥ 7` es `Falso` → `NO Falso` es `Verdadero`.
  * `Falso O Verdadero` → **`Verdadero`**.
* **c) `(25 ≥ 7) Y NO (7 ≤ 2)`**
  * `25 ≥ 7` es `Verdadero`.
  * `7 ≤ 2` es `Falso` → `NO Falso` es `Verdadero`.
  * `Verdadero Y Verdadero` → **`Verdadero`**.
* **d) `('H' < 'J') Y ('9' ≠ '7')`**
  * En orden de codificación ASCII, `'H'` precede a `'J'` (`Verdadero`).
  * El carácter `'9'` es distinto de `'7'` (`Verdadero`).
  * `Verdadero Y Verdadero` → **`Verdadero`**.
* **e) `10 + 4 < 15 - 3 O 2 × 5 + 1 > 14 - 2 × 2`**
  * Izquierda: `14 < 12` → `Falso`.
  * Derecha: `10 + 1 > 14 - 4` → `11 > 10` → `Verdadero`.
  * `Falso O Verdadero` → **`Verdadero`**.
* **f) `4 × 2 ≤ 8 O 2 × 2 < 5 Y 4 > 3 + 1`**
  * Aritmética: `8 ≤ 8 O 4 < 5 Y 4 > 4`
  * Relacionales: `Verdadero O Verdadero Y Falso`
  * Como `Y` tiene mayor precedencia que `O`: `Verdadero O (Verdadero Y Falso)` → `Verdadero O Falso` → **`Verdadero`**.
* **g) `10 ≤ 2 × 5 Y 3 < 4 O NO (8 > 7) Y 3 × 2 ≤ 4 × 2 - 1`**
  * Sub-bloque 1: `10 ≤ 10` (`V`) `Y 3 < 4` (`V`) → `Verdadero`.
  * Sub-bloque 2: `NO (8 > 7)` → `NO (Verdadero)` → `Falso`.
  * Evaluación: `Verdadero O (Falso Y ...)` → **`Verdadero`**.

---

### Solución al Ejercicio 4: Algoritmo de la vida cotidiana en lenguaje estructurado

```text
Algoritmo CambiarRuedaVehiculo
    // 1. Fase de Seguridad y Señalización
    1. Detener el vehículo fuera de la calzada en terreno plano, firme y seguro.
    2. Aplicar el freno de estacionamiento (freno de mano) e insertar la primera marcha (o posición P).
    3. Encender las luces de emergencia, colocarse el chaleco reflectante y situar el dispositivo V-16 o triángulos.
    
    // 2. Preparación de Herramientas
    4. Extraer del maletero: rueda de repuesto, gato mecánico y llave de tuercas.
    5. Retirar el embellecedor y aflojar media vuelta los tornillos de la rueda con la llave, SIN extraerlos.
    
    // 3. Elevación y Sustitución
    6. Colocar el gato en el punto de anclaje reforzado del chasis más cercano a la rueda averiada.
    7. Accionar la palanca del gato hasta que el neumático quede elevado 4 cm sobre el asfalto.
    8. Desenroscar completamente los tornillos y retirar la rueda pinchada.
    9. Montar la rueda de repuesto haciendo coincidir los orificios del buje.
    10. Enroscar manualmente los tornillos hasta hacer tope y ajustarlos ligeramente con la llave.
    
    // 4. Descenso y Ajuste Final
    11. Bajar el gato lentamente hasta que el neumático apoye con firmeza en el suelo y retirarlo.
    12. Apretar enérgicamente los tornillos con la llave siguiendo un patrón en cruz (estrella).
    13. Guardar las herramientas y la rueda pinchada en el maletero.
FinAlgoritmo
```

---

### Solución al Ejercicio 5: Cálculo de área y perímetro de un rectángulo

```text
Algoritmo RectanguloAreaPerimetro
    Definir baseRect, alturaRect, perimetro, area Como Real
    
    Escribir "Introduce la base del rectángulo (en metros):"
    Leer baseRect
    Escribir "Introduce la altura del rectángulo (en metros):"
    Leer alturaRect
    
    Si baseRect > 0 Y alturaRect > 0 Entonces
        perimetro ← 2 × (baseRect + alturaRect)
        area ← baseRect × alturaRect
        
        Escribir "=============================="
        Escribir "    RESULTADOS GEOMÉTRICOS    "
        Escribir "=============================="
        Escribir "Perímetro total: ", perimetro, " m"
        Escribir "Área de superficie: ", area, " m²"
    SiNo
        Escribir "Error: Las dimensiones del rectángulo deben ser estrictamente positivas."
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 6: Estructuras condicionales simples y dobles (`Si - Entonces - SiNo`)

#### 1. Mayor de dos números:
```text
Algoritmo DeterminarMayor
    Definir num1, num2 Como Real
    
    Escribir "Introduce el primer número:"
    Leer num1
    Escribir "Introduce el segundo número:"
    Leer num2
    
    Si num1 > num2 Entonces
        Escribir "El número mayor es: ", num1
    SiNo
        Si num2 > num1 Entonces
            Escribir "El número mayor es: ", num2
        SiNo
            Escribir "Ambos números son idénticos: ", num1
        FinSi
    FinSi
FinAlgoritmo
```

#### 2. Control de mayoría de edad:
```text
Algoritmo ControlMayoriaEdad
    Definir edad Como Entero
    
    Escribir "Introduce la edad de la persona:"
    Leer edad
    
    Si edad ≥ 18 Entonces
        Escribir "Es mayor de edad (acceso permitido)."
    SiNo
        Escribir "Es menor de edad (acceso restringido)."
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 7: Selección múltiple (`Según...Hacer`) para días de la semana

```text
Algoritmo DiaDeLaSemana
    Definir dia Como Entero
    
    Escribir "Introduce el número de día de la semana [1-7]:"
    Leer dia
    
    Segun dia Hacer
        1:
            Escribir "Día 1: Lunes"
        2:
            Escribir "Día 2: Martes"
        3:
            Escribir "Día 3: Miércoles"
        4:
            Escribir "Día 4: Jueves"
        5:
            Escribir "Día 5: Viernes"
        6:
            Escribir "Día 6: Sábado"
        7:
            Escribir "Día 7: Domingo"
        De Otro Modo:
            Escribir "Error: El valor introducido (", dia, ") no corresponde a ningún día válido [1-7]."
    FinSegun
FinAlgoritmo
```

---

### Solución al Ejercicio 8: Comparativa de bucles (`Mientras` vs `Para`)

#### 1. Conteo del 1 al 10 con bucle `Mientras`:
```text
Algoritmo ContarConMientras
    Definir i Como Entero
    i ← 1
    Mientras i ≤ 10 Hacer
        Escribir "Número: ", i
        i ← i + 1 // Incremento manual indispensable
    FinMientras
FinAlgoritmo
```

#### 2. Conteo del 1 al 10 con bucle `Para`:
```text
Algoritmo ContarConPara
    Definir i Como Entero
    Para i ← 1 Hasta 10 Con Paso 1 Hacer
        Escribir "Número: ", i
    FinPara
FinAlgoritmo
```
* **Ventaja didáctica:** Con `Para`, la inicialización, la condición de parada y el incremento de la variable de control están unificados en una sola línea compacta, eliminando por completo el riesgo de olvidar la instrucción de avance y caer en un bucle infinito.

#### 3. Suma acumulada de 1 a N:
```text
Algoritmo SumatorioHastaN
    Definir N, i, sumaAcumulada Como Entero
    
    Escribir "Introduce el valor de N (entero positivo):"
    Leer N
    
    Si N > 0 Entonces
        sumaAcumulada ← 0
        Para i ← 1 Hasta N Con Paso 1 Hacer
            sumaAcumulada ← sumaAcumulada + i
        FinPara
        Escribir "La suma de 1 hasta ", N, " es: ", sumaAcumulada
    SiNo
        Escribir "Error: N debe ser mayor que cero."
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 9: Diseño modular y funciones (`esPar`)

```text
// SUBALGORITMO / FUNCIÓN PURA
Funcion resultado ← esPar(n)
    Definir resultado Como Logico
    resultado ← (n MOD 2 = 0)
FinFuncion

// ALGORITMO PRINCIPAL CONSUMIDOR
Algoritmo ComprobadorPares
    Definir i Como Entero
    
    Escribir "=== COMPROBACIÓN DE PARIDAD [1 - 5] ==="
    Para i ← 1 Hasta 5 Con Paso 1 Hacer
        Si esPar(i) Entonces
            Escribir "El número ", i, " es PAR."
        SiNo
            Escribir "El número ", i, " es IMPAR."
        FinSi
    FinPara
FinAlgoritmo
```

---

### Solución al Ejercicio 10: Fundamentos de ingeniería del software

#### 1. Clasificación de lenguajes:

| Lenguaje | Nivel de Abstracción | Modelo de Traducción y Ejecución |
|---|---|---|
| **Ensamblador (*Assembly*)** | **Bajo Nivel (2GL)** | **Ensamblado:** Un programa ensamblador traduce directamente los nemotécnicos a código máquina nativo 1:1, estrechamente acoplado al procesador. |
| **Python** | **Alto Nivel (3GL)** | **Interpretado:** Un intérprete analiza y ejecuta el código fuente instrucción por instrucción en tiempo real. |
| **Java** | **Alto Nivel (3GL)** | **Híbrido:** El compilador (`javac`) genera **Bytecode** neutro (`.class`), y la Máquina Virtual de Java (**JVM**) lo ejecuta combinando interpretación y compilación dinámica **JIT**. |

#### 2. Orden cronológico de las fases del ciclo de vida del software:
1. **Análisis de Requisitos:** Descubrir qué necesita el cliente y definir las especificaciones.
2. **Diseño y Arquitectura:** Planificar la solución técnica, estructuras de datos y diagramas UML.
3. **Implementación (Codificación):** Escribir el código fuente en el lenguaje de programación elegido.
4. **Pruebas y Verificación (QA):** Detectar y corregir fallos mediante casos de prueba exhaustivos.
5. **Despliegue y Mantenimiento:** Poner en producción el software y mantenerlo operativo en el tiempo.
