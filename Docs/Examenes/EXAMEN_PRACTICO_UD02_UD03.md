# Exámenes Prácticos Oficiales: Unidad 2 y Unidad 3
**Módulo:** Programación (0485) | **CFGS:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Duración Estimada:** 50 minutos por ejercicio  
**Calificación:** 0 a 10 puntos cada ejercicio  
**Entorno Oficial:** IntelliJ IDEA / Java LTS  

---

## 📌 Guía de Evaluación y Corrección Automática

Este documento contiene dos ejercicios prácticos diseñados para pruebas de evaluación continua o exámenes de evaluación, con cobertura íntegra de los Criterios de Evaluación (CE) y Resultados de Aprendizaje (RA) de la Programación Didáctica:

* **Ejercicio 1 (Unidad 2):** Facturación Eléctrica y Fidelización *"EcoVolt Solutions"*.
* **Ejercicio 2 (Unidad 3):** Auditoría y Clasificación Logística de Paquetes *"LogistiTrack"*.

Ambos ejercicios cuentan con un sistema de corrección automática disponible en el script:
```bash
# Corrección del ejercicio de la Unidad 2:
python scripts/evaluar_examen.py --unidad 2 --archivo ruta/al/AlumnoEcoVolt.java

# Corrección del ejercicio de la Unidad 3:
python scripts/evaluar_examen.py --unidad 3 --archivo ruta/al/AlumnoLogistiTrack.java
```
El script realiza compilación, pruebas dinámicas con casos de prueba, análisis estático de código, detección de "horrores" de programación y emisión del desglose oficial de la rúbrica sobre 10.00 puntos.

---

# ⚡ EJERCICIO 1 (UNIDAD 2): Simulador de Facturación Eléctrica "EcoVolt"

### Contexto Profesional
La compañía comercializadora de energía renovable **EcoVolt Solutions S.L.** necesita un programa de terminal en Java que permita simular y desglosar la factura eléctrica mensual de sus abonados, aplicando tarifas horarias, bonificaciones sociales y un sistema de cálculo de puntos de fidelización ecológicos (*EcoPuntos*).

### Tiempo Estimado
**45 - 50 minutos.**

### Criterios de Evaluación Evaluados
* **RA 1:** `1.a` (estructura de programa), `1.b` / `1.c` (proyecto en IDE), `1.d` (tipos de datos primitivos y referencias), `1.e` (variables), `1.f` (constantes simbólicas y literales), `1.g` (expresiones aritméticas y lógicas), `1.h` (conversiones explícitas de tipo / *casting*), `1.i` (comentarios y documentación), `1.j` (entrada por `Scanner` y salida tabular con `printf`).
* **RA 4:** Sintaxis y estructura de clase en Java.

---

### Requisitos Técnicos del Enunciado

1. **Constantes del Sistema (`final`):**
   * Tarifa base de consumo: `0.1475` € por kWh.
   * Término de potencia diario: `0.1120` € por kW al día.
   * Impuesto Especial sobre la Electricidad: `5.1127%` (`0.051127`).
   * IVA aplicable: `21.0%` (`0.21`).
   * Descuento Bono Social: `25.0%` (`0.25`) sobre el término de energía.
   * Factor de conversión EcoPuntos: `8.5` puntos por cada kWh consumido.

2. **Entrada de Datos por Consola (`Scanner`):**
   * `nombreTitular` (`String`): Nombre completo del cliente.
   * `diasFacturados` (`int`): Número de días del ciclo de facturación (ej: 30).
   * `potenciaContratadaKW` (`double`): Potencia contratada en kW (ej: 4.60).
   * `consumoKWh` (`double`): Energía consumida en el periodo (ej: 235.80).
   * `tipoTarifa` (`char`): Modalidad horaria:
     * `'V'` (Valle): 15% de descuento sobre el precio base (factor `0.85`).
     * `'P'` (Punta): 20% de recargo sobre el precio base (factor `1.20`).
     * `'E'` (Estándar) u otra: sin variación (factor `1.00`).
   * `tieneBonoSocial` (`boolean`): Indica si el abonado es beneficiario de bono social (`true`/`false`).

3. **Cálculos y Operaciones Aritmético-Lógicas:**
   * **Coste de potencia:** `diasFacturados × potenciaContratadaKW × COSTE_POTENCIA_DIA`.
   * **Coste de energía bruto:** `consumoKWh × TARIFA_BASE_KWH × factorTarifa`.
   * **Descuento bono social:** Si `tieneBonoSocial` es `true`, aplicar el 25% sobre el coste de energía bruto; en caso contrario, `0.0`.
   * **Coste de energía neto:** `costeEnergiaBruto - descuentoBono`.
   * **Base imponible:** `costePotencia + costeEnergiaNeto`.
   * **Impuesto Eléctrico:** `baseImponible × IMPUESTO_ELECTRICIDAD`.
   * **IVA (21%):** `(baseImponible + impuestoElectrico) × IVA_PORCENTAJE`.
   * **Total Factura:** `baseImponible + impuestoElectrico + IVA`.
   * **EcoPuntos (Casting explícito):** Se deben calcular los puntos multiplicando `consumoKWh × FACTOR_ECOPUNTOS` y **convertir obligatoriamente el resultado a un entero (`int`) mediante *casting* explícito**, truncando la parte decimal.

4. **Salida Formateada Tabular (`System.out.printf`):**
   * La factura debe mostrarse con alineación de columnas, dos decimales en importes monetarios (`%.2f EUR`) y formato claro.

---

### Ejemplo de Ejecución por Terminal
```text
==================================================
   ECOVOLT SOLUTIONS S.L. - SIMULADOR DE FACTURA  
==================================================
Introduzca el nombre del titular: Carlos Garcia
Dias del periodo de facturacion (ej. 30): 30
Potencia contratada en kW (ej. 4.60): 4.60
Energia total consumida en kWh (ej. 235.80): 235.80
Tipo de tarifa horaria [V = Valle, P = Punta, E = Estandar]: V
Dispone de Bono Social activo [true/false]: true

--------------------------------------------------
               DESGLOSE DE FACTURA                
--------------------------------------------------
Titular del contrato     : Carlos Garcia
Modalidad de tarifa      : Tarifa 'V' (Factor: 0.85)
Periodo facturado        : 30 dias
Potencia ( 4.60 kW)      :    15.46 EUR
Consumo  (235.80 kWh)    :    29.56 EUR
Descuento Bono Social    : -   7.39 EUR
--------------------------------------------------
Base imponible           :    37.63 EUR
Impuesto Electricidad    :     1.92 EUR
IVA (21.00%)             :     8.31 EUR
==================================================
TOTAL A PAGAR            :    47.86 EUR
==================================================
Fidelizacion EcoPuntos   : 2004 pts (Canjeables)
==================================================
```

---

### Rúbrica de Evaluación — Ejercicio Unidad 2 (Total: 10.00 Puntos)

| Bloque / Criterios Evaluados | Indicadores de Calificación | Puntuación |
| :--- | :--- | :---: |
| **1. Estructura, Sintaxis y Documentación**<br>*(RA 1: CE 1.a, 1.b, 1.c, 1.i)* | • **Excelente (1.00 p):** Clase y método `main` bien declarados. Código ordenado, indentación correcta y comentarios pertinentes.<br>• **Mejorable (0.50 p):** Compila pero falta sangrado o comentarios.<br>• **Insuficiente (0.00 p):** No compila o faltan elementos canónicos. | **1.00 pt** |
| **2. Tipos de Datos y Constantes**<br>*(RA 1: CE 1.d, 1.f)* | • **Excelente (2.50 p):** Uso idóneo de los 5 tipos requeridos (`int`, `double`, `boolean`, `char`, `String`). Declaración de constantes con `final` en mayúsculas.<br>• **Aceptable (1.50 p):** Tipos correctos pero omite constantes o usa tipos inadecuados.<br>• **Insuficiente (0.00 p):** Variables sin tipar correctamente. | **2.50 pts** |
| **3. Entrada y Conversión Explícita (Casting)**<br>*(RA 1: CE 1.e, 1.h, 1.j)* | • **Excelente (2.00 p):** Uso fluido de `Scanner` con `Locale.US`. Conversión explícita `(int)` para truncar los EcoPuntos.<br>• **Aceptable (1.00 p):** Lee datos pero omite el casting o usa redondeo estándar.<br>• **Insuficiente (0.00 p):** Errores en la lectura o falta el casting. | **2.00 pts** |
| **4. Expresiones y Cálculos Aritmético-Lógicos**<br>*(RA 1: CE 1.g)* | • **Excelente (2.50 p):** Todos los cálculos (potencia, energía con factor, bono social, impuestos e IVA) son matemáticamente exactos.<br>• **Aceptable (1.50 p):** Errores en la precedencia de operadores o en la aplicación de porcentajes.<br>• **Insuficiente (0.00 p):** Fórmulas no implementadas o erróneas. | **2.50 pts** |
| **5. Salida Formateada y Presentación**<br>*(RA 1: CE 1.j)* | • **Excelente (2.00 p):** Salida limpia con `System.out.printf`, especificadores (`%.2f`, `%d`, `%s`, `%n`) y alineación en columnas.<br>• **Aceptable (1.00 p):** Usa `printf` sin dominar la anchura de columna o usa `print`/`println`.<br>• **Insuficiente (0.00 p):** Salida ilegible o desalineada. | **2.00 pts** |

---

# 📦 EJERCICIO 2 (UNIDAD 3): Auditoría y Clasificación Logística "LogistiTrack"

### Contexto Profesional
La empresa de paquetería urgente **LogistiTrack S.A.** precisa un módulo de terminal en Java para auditar la recepción de lotes de paquetes en su centro de distribución. El software debe validar códigos de seguimiento mediante **expresiones regulares**, clasificar el tipo de envío con estructuras de control múltiple (`switch`), recopilar los pesos de los paquetes en un **array unidimensional** protegiendo la lectura contra **excepciones de entrada**, y calcular estadísticas clave del lote mediante **métodos estáticos modularizados**.

### Tiempo Estimado
**50 minutos.**

### Criterios de Evaluación Evaluados
* **RA 1:** `1.a`, `1.b`, `1.c` (modularidad y funciones auxiliares).
* **RA 2:** `2.a`, `2.b`, `2.c` (instanciación de clases del API estándar, llamada a métodos).
* **RA 3:** `3.a` (estructuras `if-else` y `switch`), `3.b` / `3.c` (bucles `while`/`do-while` y `for`), `3.d` (sentencias de salto `return`/`break`), `3.e` (gestión de excepciones `try-catch`), `3.f` (ejecutable robusto), `3.g` (depuración y código documentado).
* **RA 6:** `6.a` (arrays unidimensionales para procesar series de datos), `6.g` / `6.f` (validación de cadenas con expresiones regulares).

---

### Requisitos Técnicos del Enunciado

1. **Validación de Código de Lote con Expresiones Regulares (`Regex`):**
   * El código debe cumplir el patrón exacto: `PKG-` seguido de **4 dígitos** y **2 letras mayúsculas** (ej: `PKG-4821-ES`, `PKG-0012-FR`).
   * Se debe solicitar en bucle `do-while` o `while` mediante `.matches("^PKG-\\d{4}-[A-Z]{2}$")` hasta que el usuario introduzca un código válido.

2. **Categorización del Envío con `switch`:**
   * Se solicita el tipo de servicio:
     * `'E'` (Estándar): sin recargo (`+0.00` €).
     * `'U'` (Urgente): recargo de `+8.00` €.
     * `'F'` (Frágil): recargo de `+5.50` €.
     * `'P'` (Peligroso): recargo de `+12.00` €.
   * El suplemento debe resolverse mediante un método con estructura `switch`.

3. **Lectura Segura con `try-catch` y Array de Pesos:**
   * Pedir la cantidad de paquetes a auditar `N` (entre 1 y 10). La lectura debe estar protegida con un bloque `try-catch (InputMismatchException e)` para evitar que el programa aborte si se escriben letras.
   * Crear un array de números decimales `double[] pesos = new double[N];`.
   * Llenar el array solicitando el peso de cada paquete (entre 0.1 kg y 50.0 kg). Cada lectura debe estar blindada con `try-catch` y validación de rango.

4. **Modularización Obligatoria (Métodos Estáticos):**
   El cálculo no puede acumularse en el método `main`. Se deben implementar obligatoriamente métodos específicos:
   * `public static double calcularPesoTotal(double[] pesos)`: Retorna la suma total de kilogramos.
   * `public static double calcularPesoMedio(double[] pesos)`: Retorna la media aritmética de pesos.
   * `public static double obtenerPesoMaximo(double[] pesos)`: Algoritmo que encuentra y retorna el paquete más pesado.
   * `public static int contarPaquetesPesados(double[] pesos, double umbral)`: Retorna cuántos paquetes superan el umbral (fijado en 10.0 kg).

5. **Facturación y Descuento por Volumen:**
   * Tarifa base: `6.50` € por paquete (`N × 6.50`).
   * Tarifa por peso: `1.25` € por cada kg del peso total.
   * Coste bruto: `(N × 6.50) + suplementoCategoria + (pesoTotal × 1.25)`.
   * Si el lote contiene **5 o más paquetes**, se aplica un descuento comercial del **10%** sobre el coste bruto.

6. **Salida Formateada por Consola:**
   * Mostrar el informe final de auditoría mediante `System.out.printf`.

---

### Ejemplo de Ejecución por Terminal
```text
==================================================
   LOGISTITRACK - SISTEMA DE AUDITORIA DE ENVIOS  
==================================================
Introduce codigo de lote (Formato PKG-0000-XX): paquete123
  [ERROR] Formato invalido. Debe ser PKG seguido de 4 digitos y 2 letras mayusculas.
Introduce codigo de lote (Formato PKG-0000-XX): PKG-8492-ES
Tipo de envio [E=Estandar, U=Urgente, F=Fragil, P=Peligroso]: X
  [ERROR] Opcion no reconocida. Selecciona E, U, F o P.
Tipo de envio [E=Estandar, U=Urgente, F=Fragil, P=Peligroso]: U
Cantidad de paquetes a auditar (1-10): tres
  [ERROR] Entrada no numerica. Debe introducir un numero entero.
Cantidad de paquetes a auditar (1-10): 3

--- REGISTRO DE PESOS POR PAQUETE (en kg) ---
Peso del paquete #1 (kg): peso_alto
  [ERROR] Valor decimal no valido. Introduzca un numero (ej. 3.45).
Peso del paquete #1 (kg): 4.5
Peso del paquete #2 (kg): 12.8
Peso del paquete #3 (kg): 6.2

==================================================
            INFORME FINAL DE AUDITORIA            
==================================================
Codigo de lote           : PKG-8492-ES
Modalidad de envio       : U (Suplemento: 8.00 EUR)
Total de paquetes        : 3 unidades
--------------------------------------------------
Peso total del lote      :    23.50 kg
Peso medio por paquete   :     7.83 kg
Paquete mas pesado       :    12.80 kg
Paquetes pesados (>10kg) : 1 unidades
--------------------------------------------------
COSTE TOTAL FACTURADO    :    56.88 EUR
==================================================
```

---

### Rúbrica de Evaluación — Ejercicio Unidad 3 (Total: 10.00 Puntos)

| Bloque / Criterios Evaluados | Indicadores de Calificación | Puntuación |
| :--- | :--- | :---: |
| **1. Modularidad y Expresiones Regulares**<br>*(RA 1, RA 6: CE 1.a, 6.g)* | • **Excelente (2.00 p):** Métodos estáticos bien definidos y desacoplados. Expresión regular compilada o ejecutada con `.matches()` que valida de forma estricta el patrón `^PKG-\\d{4}-[A-Z]{2}$`.<br>• **Aceptable (1.00 p):** Métodos implementados pero con validación de cadenas incompleta (substrings o sin regex).<br>• **Insuficiente (0.00 p):** No hay modularidad ni regex. | **2.00 pts** |
| **2. Estructuras de Selección**<br>*(RA 3: CE 3.a)* | • **Excelente (1.50 p):** Uso combinado de sentencia `switch` para la categoría de envío y bifurcaciones `if-else` para descuentos o validaciones.<br>• **Aceptable (0.75 p):** Solo usa `if-else` encadenados omitiendo el `switch`.<br>• **Insuficiente (0.00 p):** Lógica condicional deficiente. | **1.50 pts** |
| **3. Bucles y Control de Excepciones**<br>*(RA 3: CE 3.b, 3.c, 3.e)* | • **Excelente (2.50 p):** Uso idóneo de bucle `for` (recorrido de arrays) y `while`/`do-while` (interacción de usuario). Bloques `try-catch` robustos que limpian el buffer del `Scanner` ante `InputMismatchException` sin abortar el programa.<br>• **Aceptable (1.50 p):** Hay bucles pero el `try-catch` está ausente o mal implementado.<br>• **Insuficiente (0.00 p):** El programa crashea ante cualquier entrada no numérica. | **2.50 pts** |
| **4. Procesamiento y Algoritmia sobre Arrays**<br>*(RA 6: CE 6.a)* | • **Excelente (2.50 p):** Array unidimensional `double[]` correctamente dimensionado y recorrido. Algoritmos de acumulador (suma total), promedio, máximo relativo y contador condicional correctamente resueltos.<br>• **Aceptable (1.50 p):** El array funciona pero falla en la búsqueda del máximo o en el conteo.<br>• **Insuficiente (0.00 p):** No usa arrays o comete errores de índice (`ArrayIndexOutOfBoundsException`). | **2.50 pts** |
| **5. Salida Formateada y Robustez Global**<br>*(RA 1, RA 3: CE 1.j, 3.f)* | • **Excelente (1.50 p):** Salida limpia con `printf`. Tolerancia a fallos demostrada: el programa no aborta ante secuencias de entradas inválidas sucesivas.<br>• **Aceptable (0.75 p):** Salida con `print`/`println` o desalineada.<br>• **Insuficiente (0.00 p):** Salida desorganizada o programa frágil. | **1.50 pts** |

---

## 🚫 Catálogo de "Horrores de Programación" (Penalizaciones Automáticas)

El script de corrección automática y la rúbrica aplican deducciones directas sobre la nota final en caso de detectarse las siguientes malas prácticas:

| Horror / Antipatrón | Descripción del Fallo Técnico | Penalización |
| :--- | :--- | :---: |
| **Bloque `catch` vacío** | Escribir `catch (Exception e) {}` ignorando el fallo sin alertar al usuario ni registrar el error. | **-1.50 pts** |
| **Comparación de cadenas con `==`** | Comparar objetos `String` usando `s == "texto"` en lugar de `s.equals("texto")` o `equalsIgnoreCase()`. | **-1.00 pt** |
| **Variables crípticas de 1 letra** | Declarar variables como `int a, b, c, x, z;` para representar conceptos de negocio (peso, días, consumo). | **-0.75 pt** |
| **Números mágicos (*hardcoded*)** | Usar valores numéricos dispersos en las fórmulas (`0.21`, `0.1475`) en vez de constantes `final`. | **-0.50 pt** |
| **Falta absoluta de comentarios** | Código sin documentar ni explicar (incumplimiento de CE 1.i / 3.g). | **-0.50 pt** |
| **Fuga de recursos (`Scanner`)** | Instanciar múltiples `Scanner` sobre `System.in` sin cerrarlos. | **-0.50 pt** |
