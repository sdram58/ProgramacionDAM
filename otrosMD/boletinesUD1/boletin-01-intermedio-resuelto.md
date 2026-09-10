---
title: Boletín U01 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre algoritmos y pseudocódigo
---

# ✅ Boletín U01 — Intermedio (resuelto)

1. **Contar pares e impares hasta el 0**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo ContarParesImpares
       pares = 0
       impares = 0
       Leer n
       Mientras (n <> 0) Hacer
           Si (n % 2 == 0) Entonces
               pares = pares + 1
           Sino
               impares = impares + 1
           FinSi
           Leer n
       FinMientras
       Escribir "Pares: ", pares
       Escribir "Impares: ", impares
   FinAlgoritmo
   ```
   </details>

2. **Factorial iterativo**
   <details>
   <summary>💡 Solución</summary>

   ```
   SubAlgoritmo r <- factorial(n)
       r = 1
       Para i = 1 Hasta n Hacer
           r = r * i
       FinPara
   FinSubAlgoritmo
   ```
   </details>

3. **Traza de "misterio"**
   <details>
   <summary>💡 Solución</summary>

   | i | r |
   |---|---|
   | (inicio) | 1 |
   | 1 | 1 |
   | 2 | 2 |
   | 3 | 6 |
   | 4 | 24 |

   `misterio(n)` calcula el **factorial** de `n` — es exactamente el mismo subalgoritmo del ejercicio 2, solo que con otro nombre.
   </details>

4. **Mayor y menor de 5 números**
   <details>
   <summary>💡 Solución</summary>

   ```
   Algoritmo MayorMenor
       Leer n1
       mayor = n1
       menor = n1
       Para i = 2 Hasta 5 Hacer
           Leer n
           Si (n > mayor) Entonces
               mayor = n
           FinSi
           Si (n < menor) Entonces
               menor = n
           FinSi
       FinPara
       Escribir "Mayor: ", mayor
       Escribir "Menor: ", menor
   FinAlgoritmo
   ```

   La clave: inicializa `mayor` y `menor` con el **primer** valor leído, no con 0 (si todos los números fueran negativos, arrancar en 0 daría un resultado incorrecto).
   </details>

5. **esPrimo**
   <details>
   <summary>💡 Solución</summary>

   ```
   SubAlgoritmo esPrimo <- esPrimo(n)
       primo = Verdadero
       Si (n < 2) Entonces
           primo = Falso
       Sino
           Para i = 2 Hasta n - 1 Hacer
               Si (n % i == 0) Entonces
                   primo = Falso
               FinSi
           FinPara
       FinSi
   FinSubAlgoritmo
   ```

   No es la versión más eficiente (se podría parar en cuanto se encuentre un divisor, o solo comprobar hasta la raíz cuadrada de `n`), pero es correcta y fácil de entender — en programación, "correcto y claro" siempre va antes que "rápido pero incomprensible".
   </details>

6. **IMC modular**
   <details>
   <summary>💡 Solución</summary>

   ```
   SubAlgoritmo peso <- pedirPeso()
       Repetir
           Leer peso
       Hasta Que (peso > 0)
   FinSubAlgoritmo

   SubAlgoritmo altura <- pedirAltura()
       Repetir
           Leer altura
       Hasta Que (altura > 0)
   FinSubAlgoritmo

   Algoritmo CalcularIMC
       p = pedirPeso()
       a = pedirAltura()
       imc = p / (a * a)
       Escribir "IMC: ", imc
       Si (imc < 18.5) Entonces
           Escribir "Bajo peso"
       Sino
           Si (imc < 25) Entonces
               Escribir "Normal"
           Sino
               Si (imc < 30) Entonces
                   Escribir "Sobrepeso"
               Sino
                   Escribir "Obesidad"
               FinSi
           FinSi
       FinSi
   FinAlgoritmo
   ```

   Fíjate en el uso de `Repetir...Hasta Que` para validar la entrada: como queremos pedir el dato **al menos una vez**, encaja mejor que `Mientras`.
   </details>

7. **Compilado vs. interpretado, y Java**
   <details>
   <summary>💡 Solución</summary>

   Un lenguaje compilado traduce todo el código fuente a código máquina **antes** de ejecutarlo (rápido en ejecución, pero específico de cada plataforma). Uno interpretado lo va traduciendo y ejecutando **línea a línea, en el momento** (más lento, pero portable). Java no es puramente ninguna de las dos cosas: primero se **compila** a bytecode (independiente de la plataforma), y después la JVM **interpreta** ese bytecode (con optimizaciones JIT que compilan en caliente las partes más usadas). Es un modelo híbrido, y es justo lo que le da a Java su portabilidad ("write once, run anywhere").
   </details>

8. **Factorial recursivo**
   <details>
   <summary>💡 Solución</summary>

   ```
   SubAlgoritmo r <- factorialRecursivo(n)
       Si (n <= 1) Entonces
           r = 1
       Sino
           r = n * factorialRecursivo(n - 1)
       FinSi
   FinSubAlgoritmo
   ```

   Fíjate en el **caso base** (`n <= 1`): sin él, la recursividad no tendría dónde parar y el programa nunca terminaría. Cuál versión es "más fácil de leer" es opinable — muchas personas ven la iterativa más intuitiva al principio, y la recursiva más elegante una vez que le coges el truco. Ambas son igual de válidas.
   </details>

9. **Tipos de error**
   <details>
   <summary>💡 Solución</summary>

   - **(a) Olvidar un `FinSi`** → error **sintáctico**: el código no respeta la gramática del pseudocódigo/lenguaje, y se detecta al compilar (o, en pseudocódigo, al intentar ejecutarlo en PSeInt), antes de que el algoritmo llegue a hacer nada.
   - **(b) Fórmula equivocada (área de cuadrado en vez de círculo)** → error **semántico/lógico**: el programa compila y se ejecuta sin quejarse, pero el resultado que calcula es incorrecto porque el algoritmo en sí está mal planteado.
   - **(c) División por un número de participantes que puede ser 0** → error **de ejecución**: el código es correcto en general, pero falla solo para ciertos datos concretos (cuando participantes vale 0) — no se detecta hasta que el programa se ejecuta con esos datos exactos.
   </details>

10. **Ventajas del modelo híbrido de Java**
    <details>
    <summary>💡 Solución</summary>

    Frente a un lenguaje **puramente compilado** como C: Java no genera un ejecutable atado a una plataforma concreta. El bytecode es el mismo `.class` en Windows, Linux o Mac — solo necesitas la JVM correspondiente instalada, no recompilar el programa para cada sistema.

    Frente a un lenguaje **puramente interpretado** como Python: no hace falta tener el código fuente disponible para ejecutar el programa (basta con el `.class`/`.jar` ya compilado), y el bytecode ya viene parcialmente optimizado desde la compilación, en vez de traducirse línea a línea cada vez que se ejecuta.

    Es la combinación de ambas cosas —portabilidad + rendimiento razonable— lo que hizo de Java una opción tan popular para aplicaciones empresariales que tenían que correr en máquinas muy distintas entre sí.
    </details>
