---
title: Boletín U01 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre algoritmos y pseudocódigo
---

# ✅ Boletín U01 — Inicial (resuelto)

1. **El mayor de dos números**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo Mayor
       Leer a, b
       Si (a > b) Entonces
           Escribir "El mayor es ", a
       Sino
           Escribir "El mayor es ", b
       FinSi
   FinAlgoritmo
   ```
   </details>

2. **Traza de A/B**
   <details>
   <summary>💡 Solución</summary>

   | Paso | A | B |
   |---|---|---|
   | Inicio | 5 | 3 |
   | A = A + B | 8 | 3 |
   | B = A - B | 8 | 5 |
   | A = A - B | 3 | 5 |

   Curiosidad: es un truco clásico para **intercambiar dos variables sin usar una tercera**. Al final A vale lo que valía B, y B vale lo que valía A.
   </details>

3. **Mayoría de edad**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo MayoriaEdad
       Leer edad
       Si (edad >= 18) Entonces
           Escribir "Es mayor de edad"
       Sino
           Escribir "Es menor de edad"
       FinSi
   FinAlgoritmo
   ```
   </details>

4. **Del 1 al 10 con Mientras**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo Contar
       i = 1
       Mientras (i <= 10) Hacer
           Escribir i
           i = i + 1
       FinMientras
   FinAlgoritmo
   ```
   </details>

5. **Del 1 al 10 con Para**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo ContarPara
       Para i = 1 Hasta 10 Hacer
           Escribir i
       FinPara
   FinAlgoritmo
   ```

   Fíjate: cuando sabes de antemano cuántas repeticiones necesitas (aquí, exactamente 10), `Para` es más directo que `Mientras` — no hace falta que tú mismo lleves la cuenta del contador.
   </details>

6. **Subalgoritmo esPar**
   <details>
   <summary>💡 Solución</summary>

   ```
   SubAlgoritmo Verdadero <- esPar(numero)
       esPar = (numero % 2 == 0)
   FinSubAlgoritmo

   Algoritmo Principal
       Para i = 1 Hasta 5 Hacer
           Si (esPar(i)) Entonces
               Escribir i, " es par"
           Sino
               Escribir i, " es impar"
           FinSi
       FinPara
   FinAlgoritmo
   ```
   </details>

7. **Clasificación de lenguajes**
   <details>
   <summary>💡 Solución</summary>

   | Lenguaje | Nivel | Ejecución |
   |---|---|---|
   | Ensamblador | Bajo nivel | Se traduce con un ensamblador (similar a compilación 1:1) |
   | Python | Alto nivel | Se interpreta |
   | Java | Alto nivel | Compila a bytecode + la JVM lo interpreta/JIT-compila |
   </details>

8. **Orden del ciclo de vida**
   <details>
   <summary>💡 Solución</summary>

   Análisis → Diseño → Implementación → Pruebas → Despliegue y mantenimiento.
   </details>

9. **Precedencia de operadores**
   <details>
   <summary>💡 Solución</summary>

   - `3 + 4 * 2` = 3 + 8 = **11** (la multiplicación va antes que la suma)
   - `(3 + 4) * 2` = 7 * 2 = **14** (el paréntesis fuerza a sumar primero)
   - `10 mod 3 + 1` = 1 + 1 = **2** (`mod` tiene la misma precedencia que `*` y `/`, va antes que `+`)
   - `2 ^ 3 + 1` = 8 + 1 = **9** (la potencia es lo primero que se calcula, antes incluso que `*` y `/`)
   </details>

10. **Días de la semana con Según...Hacer**
    <details>
    <summary>💡 Solución</summary>

    ```
    Algoritmo DiaSemana
        Definir dia Como Entero;
        Leer dia;
        Según dia Hacer
            1: Escribir "Lunes";
            2: Escribir "Martes";
            3: Escribir "Miércoles";
            4: Escribir "Jueves";
            5: Escribir "Viernes";
            6: Escribir "Sábado";
            7: Escribir "Domingo";
            De Otro Modo: Escribir "Número inválido";
        FinSegun
    FinAlgoritmo
    ```
    </details>
