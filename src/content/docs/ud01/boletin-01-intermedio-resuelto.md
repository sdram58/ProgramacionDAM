---
title: 📝 Boletín 01 Intermedio (Resuelto) — Algorítmica y Pseudocódigo
description: Soluciones completas, código PSeInt probado y explicaciones lógicas de los 10 ejercicios del Boletín Intermedio de la Unidad 1.
---

A continuación se detallan las soluciones exhaustivas de los ejercicios planteados en el [Boletín Intermedio](/ProgramacionDAM/ud01/boletin-01-intermedio/).

---

### Solución al Ejercicio 1: Validación estricta de entrada con bucle Repetir

```text
Algoritmo CalificacionCualitativa
    Definir nota Como Real
    
    // Filtro de validación mediante bucle con post-condición
    Repetir
        Escribir "Introduce la nota del alumno [0.0 - 10.0]:"
        Leer nota
        Si nota < 0 O nota > 10 Entonces
            Escribir "Error: La nota debe encontrarse entre 0 y 10. Inténtalo de nuevo."
        FinSi
    Hasta Que nota ≥ 0 Y nota ≤ 10
    
    // Clasificación cualitativa mediante condicionales anidadas
    Si nota < 5.0 Entonces
        Escribir "Calificación: Suspenso"
    SiNo
        Si nota < 6.0 Entonces
            Escribir "Calificación: Aprobado"
        SiNo
            Si nota < 7.0 Entonces
                Escribir "Calificación: Bien"
            SiNo
                Si nota < 9.0 Entonces
                    Escribir "Calificación: Notable"
                SiNo
                    Escribir "Calificación: Sobresaliente"
                FinSi
            FinSi
        FinSi
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 2: Intercambio de dos variables (Swap)

#### Método 1: Utilizando una variable auxiliar `TEMP` (Estándar y seguro)
```text
Algoritmo IntercambioAuxiliar
    Definir A, B, TEMP Como Real
    Escribir "Introduce A:"
    Leer A
    Escribir "Introduce B:"
    Leer B
    
    Escribir "Valores antes: A = ", A, " | B = ", B
    
    TEMP ← A    // 1. Respaldamos el valor original de A
    A ← B       // 2. Sobrescribimos A con el valor de B
    B ← TEMP    // 3. Restauramos en B el valor respaldado de A
    
    Escribir "Valores después: A = ", A, " | B = ", B
FinAlgoritmo
```

#### Método 2: Sin variable auxiliar (mediante operaciones aritméticas)
```text
Algoritmo IntercambioSinAuxiliar
    Definir A, B Como Real
    A ← 15
    B ← 40
    
    A ← A + B   // A contiene 55 (suma acumulada)
    B ← A - B   // B = 55 - 40 = 15 (B adopta el valor original de A)
    A ← A - B   // A = 55 - 15 = 40 (A adopta el valor original de B)
    
    Escribir "A = ", A, " | B = ", B  // Muestra: A = 40 | B = 15
FinAlgoritmo
```

---

### Solución al Ejercicio 3: Determinación del número mayor entre tres valores

```text
Algoritmo MayorDeTres
    Definir N1, N2, N3, mayor Como Real
    
    Escribir "Introduce el primer número:"
    Leer N1
    Escribir "Introduce el segundo número:"
    Leer N2
    Escribir "Introduce el tercer número:"
    Leer N3
    
    Si N1 ≥ N2 Y N1 ≥ N3 Entonces
        mayor ← N1
    SiNo
        Si N2 ≥ N1 Y N2 ≥ N3 Entonces
            mayor ← N2
        SiNo
            mayor ← N3
        FinSi
    FinSi
    
    Escribir "El mayor de los tres números introducidos es: ", mayor
FinAlgoritmo
```

---

### Solución al Ejercicio 4: Nómina semanal con horas extras e IRPF

```text
Algoritmo CalculoNominaSemanal
    Definir horasTrabajadas Como Real
    Definir PRECIO_ORDINARIA, PRECIO_EXTRA Como Real
    Definir salarioBruto, retencion, salarioNeto, exceso Como Real
    
    PRECIO_ORDINARIA ← 15.0
    PRECIO_EXTRA ← 15.0 × 1.5   // 22.50 €/hora
    
    Escribir "Introduce el número total de horas trabajadas en la semana:"
    Leer horasTrabajadas
    
    // Cálculo del salario bruto
    Si horasTrabajadas ≤ 35 Entonces
        salarioBruto ← horasTrabajadas × PRECIO_ORDINARIA
    SiNo
        salarioBruto ← (35 × PRECIO_ORDINARIA) + ((horasTrabajadas - 35) × PRECIO_EXTRA)
    FinSi
    
    // Cálculo de la retención fiscal
    Si salarioBruto > 500 Entonces
        exceso ← salarioBruto - 500
        retencion ← exceso × 0.12
    SiNo
        retencion ← 0
    FinSi
    
    salarioNeto ← salarioBruto - retencion
    
    Escribir "--- DESGLOSE DE NÓMINA ---"
    Escribir "Horas trabajadas: ", horasTrabajadas
    Escribir "Salario Bruto: ", salarioBruto, " €"
    Escribir "Retención IRPF (12% sobre exceso >500€): ", retencion, " €"
    Escribir "Salario Neto a percibir: ", salarioNeto, " €"
FinAlgoritmo
```

---

### Solución al Ejercicio 5: Generador de tablas de multiplicar anidadas

```text
Algoritmo TablasMultiplicar
    Definir tabla, factor, producto Como Entero
    
    Para tabla ← 1 Hasta 5 Con Paso 1 Hacer
        Escribir "=============================="
        Escribir "       TABLA DEL ", tabla
        Escribir "=============================="
        
        Para factor ← 1 Hasta 10 Con Paso 1 Hacer
            producto ← tabla × factor
            Escribir tabla, " × ", factor, " = ", producto
        FinPara
        
        Escribir "" // Línea en blanco separadora
    FinPara
FinAlgoritmo
```

---

### Solución al Ejercicio 6: Cálculo de potencias sin operador de exponenciación

```text
Algoritmo CalculoPotencia
    Definir base, exponente, i Como Entero
    Definir potencia Como Real
    
    Escribir "Introduce la base (número entero):"
    Leer base
    Escribir "Introduce el exponente (entero ≥ 0):"
    Leer exponente
    
    Si exponente < 0 Entonces
        Escribir "Error: Este algoritmo solo admite exponentes enteros mayores o iguales a cero."
    SiNo
        potencia ← 1  // Todo número elevado a 0 es 1
        
        Para i ← 1 Hasta exponente Con Paso 1 Hacer
            potencia ← potencia × base
        FinPara
        
        Escribir base, " elevado a ", exponente, " = ", potencia
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 7: Contador y sumador de dígitos de un número

```text
Algoritmo DescomposicionDigitos
    Definir numeroOriginal, numeroTemporal, digito, contadorDigitos, sumaDigitos Como Entero
    
    Escribir "Introduce un número entero positivo:"
    Leer numeroOriginal
    
    Si numeroOriginal < 0 Entonces
        numeroOriginal ← -numeroOriginal // Convertir a positivo si fuera negativo
    FinSi
    
    Si numeroOriginal = 0 Entonces
        Escribir "El número tiene 1 dígito y su suma es 0."
    SiNo
        numeroTemporal ← numeroOriginal
        contadorDigitos ← 0
        sumaDigitos ← 0
        
        Mientras numeroTemporal > 0 Hacer
            digito ← numeroTemporal MOD 10             // Extraemos el último dígito
            sumaDigitos ← sumaDigitos + digito         // Acumulamos el dígito
            contadorDigitos ← contadorDigitos + 1      // Contabilizamos el dígito
            numeroTemporal ← trunc(numeroTemporal / 10)// Descartamos el último dígito
        FinMientras
        
        Escribir "Número analizado: ", numeroOriginal
        Escribir "Cantidad total de dígitos: ", contadorDigitos
        Escribir "Suma de sus dígitos: ", sumaDigitos
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 8: Comprobador de números primos

```text
Algoritmo ComprobadorPrimo
    Definir N, divisor Como Entero
    Definir esPrimo Como Logico
    
    Escribir "Introduce un número entero mayor que 1:"
    Leer N
    
    Si N ≤ 1 Entonces
        Escribir "Por definición matemática, los números primos son mayores estrictos que 1."
    SiNo
        esPrimo ← Verdadero
        divisor ← 2
        
        // Optimización: Solo hace falta comprobar divisores hasta la raíz cuadrada de N
        Mientras divisor × divisor ≤ N Y esPrimo = Verdadero Hacer
            Si N MOD divisor = 0 Entonces
                esPrimo ← Falso // Encontramos un divisor exacto: no es primo
            FinSi
            divisor ← divisor + 1
        FinMientras
        
        Si esPrimo Entonces
            Escribir "El número ", N, " es PRIMO."
        SiNo
            Escribir "El número ", N, " es COMPUESTO (no es primo)."
        FinSi
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 9: Determinación de Año Bisiesto (Regla Gregoriana)

```text
Algoritmo AnioBisiesto
    Definir anio Como Entero
    Definir esBisiesto Como Logico
    
    Escribir "Introduce el año a comprobar:"
    Leer anio
    
    // Condición gregoriana unificada:
    // Divisible por 4 Y NO por 100, O BIEN divisible por 400
    esBisiesto ← (anio MOD 4 = 0 Y anio MOD 100 ≠ 0) O (anio MOD 400 = 0)
    
    Si esBisiesto Entonces
        Escribir "El año ", anio, " es BISIESTO (tiene 366 días, febrero cuenta con 29)."
    SiNo
        Escribir "El año ", anio, " es COMÚN (tiene 365 días, febrero cuenta con 28)."
    FinSi
FinAlgoritmo
```

---

### Solución al Ejercicio 10: Desglose óptimo de billetes en cajero automático

```text
Algoritmo CajeroAutomaticoBilletes
    Definir importe, b50, b20, b10, b5, resto Como Entero
    
    Escribir "Introduce el importe a retirar en euros (múltiplo de 5):"
    Leer importe
    
    Si importe ≤ 0 O importe MOD 5 ≠ 0 Entonces
        Escribir "Error: La cantidad debe ser un entero positivo y múltiplo de 5 €."
    SiNo
        b50 ← trunc(importe / 50)
        resto ← importe MOD 50
        
        b20 ← trunc(resto / 20)
        resto ← resto MOD 20
        
        b10 ← trunc(resto / 10)
        resto ← resto MOD 10
        
        b5 ← trunc(resto / 5)
        resto ← resto MOD 5
        
        Escribir "=========================================="
        Escribir " DESGLOSE ÓPTIMO DE BILLETES PARA: ", importe, " €"
        Escribir "=========================================="
        Escribir "Billetes de 50 €: ", b50
        Escribir "Billetes de 20 €: ", b20
        Escribir "Billetes de 10 €: ", b10
        Escribir "Billetes de 5 €:  ", b5
    FinSi
FinAlgoritmo
```
