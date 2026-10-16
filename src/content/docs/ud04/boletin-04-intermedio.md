---
title: "Boletín 4.2: Nivel Intermedio — Ejercicios Prácticos"
description: "Ejercicios avanzados de orientación a objetos: composición, contratos equals y hashCode, inmutabilidad, records, enums, patrón Singleton y factorías estáticas en Java."
draft: true
prev: false
next: false
sidebar:
  order: 3
  badge:
    text: "Intermedio"
    variant: "caution"
---

Bienvenido al boletín de nivel intermedio de la **Unidad 4: Clases y objetos**.

Este bloque de 12 ejercicios está diseñado para profundizar en la arquitectura y buenas prácticas del paradigma orientado a objetos: diseño de clases compuestas, implementación rigurosa de los contratos de `java.lang.Object`, clases inmutables con copias defensivas, los modernos `record`, enumeraciones complejas con comportamiento, el patrón de diseño Singleton y métodos de factoría estática.

:::tip[Enfoque Arquitectural]
Presta especial atención a la protección de datos: evita las fugas de encapsulamiento mediante copias defensivas y asegura que cualquier clase que sobrescriba `equals()` sobrescriba de forma coordinada `hashCode()`.
:::

---

### Ejercicio 1: Sobrescritura Canónica de `equals()`, `hashCode()` y `toString()`
**Enunciado:**  
Toma como base la clase `Libro` del boletín anterior, añadiendo un nuevo atributo inmutable `private final String isbn` (código identificativo único del libro).  
- Dos libros deben considerarse idénticos según `equals()` si y solo si poseen el mismo `isbn` (independientemente de si sus páginas o títulos difieren).  
- Implementa `equals(Object obj)` utilizando el operador `instanceof` con Pattern Matching de Java moderno.  
- Sobrescribe `hashCode()` de forma coherente utilizando `Objects.hash(this.isbn)`.  
- Sobrescribe `toString()` para mostrar un formato claro: `"Libro[ISBN='...', Título='...', Autor='...', Páginas=...]"`.  
- Realiza una clase de prueba verificando el contrato de simetría y reflexividad.

---

### Ejercicio 2: Composición de Objetos (`Motor` y `Vehiculo`)
**Enunciado:**  
La composición (*"un objeto tiene otro objeto"*) es una de las relaciones más potentes de la POO:  
- Crea la clase `Motor` con: `tipoCombustible` (String: Gasolina, Diésel, Eléctrico), `potenciaCv` (int) y `encendido` (boolean). Incluye métodos para encender, apagar y consultar estado.  
- Crea la clase `Vehiculo` que contenga: `matricula` (String), `marca` (String) y un atributo de tipo objeto `Motor`.  
- El constructor de `Vehiculo` debe instanciar o recibir su `Motor`.  
- Proporciona métodos en `Vehiculo` para arrancar, acelerar y consultar la información completa delegando en su motor interno.

---

### Ejercicio 3: La Clase Inmutable `Fraccion`
**Enunciado:**  
Diseña una clase completamente inmutable llamada `Fraccion` para operar con números racionales exactos:  
- Atributos `private final int numerador` y `private final int denominador`.  
- El constructor debe rechazar denominadores iguales a cero (`IllegalArgumentException`). Si el denominador es negativo, debe normalizar el signo pasando el negativo al numerador.  
- Debe simplificar automáticamente la fracción en el constructor dividiendo numerador y denominador por su Máximo Común Divisor (MCD) mediante el algoritmo de Euclides.  
- Métodos aritméticos: `sumar(Fraccion otra)`, `restar(Fraccion otra)`, `multiplicar(Fraccion otra)` y `dividir(Fraccion otra)`.  
- Cada operación aritmética debe devolver una **nueva instancia inmutable** de `Fraccion`.

---

### Ejercicio 4: Transportador Inmutable con `record CoordenadaGPS`
**Enunciado:**  
Utilizando la característica de Java moderno, declara un `record` llamado `CoordenadaGPS` con componentes `double latitud` y `double longitud`:  
- Añade un constructor compacto que valide que la latitud se encuentre en `[-90.0, 90.0]` y la longitud en `[-180.0, 180.0]`.  
- Implementa un método de instancia `public double distanciaKm(CoordenadaGPS destino)` que calcule la distancia geodésica aproximada entre dos puntos utilizando la fórmula simplificada del semiverseno (Haversine).  
- Demuestra que el `record` genera automáticamente métodos accesores (`latitud()`, `longitud()`), `toString()` legible y comparación por valor en `equals()`.

---

### Ejercicio 5: El Patrón Singleton (`GestorConfiguracion`)
**Enunciado:**  
Implementa el patrón de diseño Singleton para centralizar la configuración de una aplicación:  
- La clase `GestorConfiguracion` debe tener un constructor estrictamente `private`.  
- Debe contener una instancia estática única privada y un método estático público `public static GestorConfiguracion getInstance()`.  
- Debe gestionar internamente un mapa asociativo o matriz de propiedades clave-valor (`String clave`, `String valor`).  
- Métodos: `setPropiedad(String clave, String valor)` y `getPropiedad(String clave, String valorPorDefecto)`.  
- Demuestra en el `main` que dos variables que invoquen `getInstance()` hacen referencia exactamente al mismo objeto en el Heap (`ref1 == ref2` es `true`).

---

### Ejercicio 6: La Clase `Mochila` y Copias Defensivas
**Enunciado:**  
Diseña una clase `Mochila` con una capacidad máxima fija de objetos (`maxElementos`) y un array interno `String[] contenido`:  
- El constructor recibe un array `String[] itemsIniciales`. Para evitar que el invocador modifique el contenido de la mochila desde fuera, realiza una **copia defensiva** con `Arrays.copyOf()`.  
- El getter `public String[] getContenido()` debe devolver un clon del array interno, jamás la referencia directa.  
- Añade métodos `guardar(String item)` y `contiene(String item)`.  
- En un programa de prueba, demuestra que modificar el array exterior antes o después no altera el estado interno de la mochila.

---

### Ejercicio 7: Enumeración Rica `NivelSeveridad`
**Enunciado:**  
Modela una enumeración para un sistema de logging profesional llamada `NivelSeveridad`:  
- Constantes: `DEBUG(1, "Depuración"), INFO(2, "Informativo"), WARN(3, "Advertencia"), ERROR(4, "Error Grave"), FATAL(5, "Fallo Crítico")`.  
- Atributos privados: `int nivelNumerico` y `String etiqueta`.  
- Constructor de enum parametrizado.  
- Método `public boolean esCritico()`: devuelve `true` si el nivel es `ERROR` o `FATAL`.  
- Método `public boolean esMayorOIgualQue(NivelSeveridad otro)`: compara si la severidad actual es igual o más prioritaria que otra.

---

### Ejercicio 8: Cuenta Bancaria con Historial de Transacciones
**Enunciado:**  
Amplía la clase `CuentaBancaria`:  
- Cada vez que se realice un ingreso o una retirada con éxito, debe registrarse un objeto inmutable de tipo `Transaccion` (con `fechaHora`, `tipo` [INGRESO/RETIRADA], `importe` y `saldoPosterior`).  
- La cuenta mantiene un array con las últimas 10 transacciones realizadas.  
- Método `public Transaccion[] getUltimosMovimientos()` que devuelva una copia del historial registrado.

---

### Ejercicio 9: Validación Compacta con `record Usuario`
**Enunciado:**  
Crea un `record Usuario(String username, String email, int edad)`:  
- En el constructor compacto, valida:  
  1. `username`: alfanumérico entre 4 y 15 caracteres (mediante regex `^[a-zA-Z0-9_]{4,15}$`).  
  2. `email`: formato de correo válido con regex.  
  3. `edad`: mayor o igual a 18 años.  
- Si algún dato no cumple, lanza `IllegalArgumentException` con un mensaje explicativo.

---

### Ejercicio 10: Enumeración de Divisas con Conversión Monetaria
**Enunciado:**  
Crea un `enum Divisa`:  
- Constantes: `EUR(1.0, "€"), USD(1.08, "$"), GBP(0.85, "£"), JPY(162.50, "¥")`.  
- Cada divisa almacena su tasa de cambio respecto a 1.0 Euro base y su símbolo monetario.  
- Diseña un método en el enum `public double convertirA(double cantidad, Divisa divisaDestino)` que transforme cualquier importe entre dos divisas cualesquiera pasando por el Euro como pivote.

---

### Ejercicio 11: Encapsulación de Matriz con la Clase `Matriz2D`
**Enunciado:**  
Crea una clase `Matriz2D` que encapsule una matriz bidimensional `double[][]`:  
- Constructor con número de filas y columnas, rellenando con ceros.  
- Constructor con datos `double[][] datosOriginales` aplicando copia defensiva profunda fila por fila.  
- Métodos:  
  - `public Matriz2D sumar(Matriz2D otra)`: devuelve una nueva matriz resultado (si las dimensiones coinciden).  
  - `public Matriz2D transponer()`: devuelve una nueva matriz transpuesta.  
  - `public double traza()`: suma de los elementos de la diagonal principal (solo si es cuadrada).  
  - `toString()` formateando la matriz con saltos de línea y tabuladores.

---

### Ejercicio 12: Factorías Estáticas (*Static Factory Methods*) con `ColorRGB`
**Enunciado:**  
Crea una clase `ColorRGB`:  
- Atributos `private final int rojo`, `verde`, `azul` (todos en el rango [0, 255]).  
- **Constructor privado** para impedir la instanciación descontrolada con `new`.  
- Constantes predefinidas: `public static final ColorRGB ROJO`, `VERDE`, `AZUL`, `BLANCO`, `NEGRO`.  
- Métodos de factoría estáticos:  
  - `public static ColorRGB fromRGB(int r, int g, int b)`: valida rangos [0, 255] y devuelve una instancia.  
  - `public static ColorRGB fromHex(String hex)`: parsea cadenas como `"#FF5733"` o `"FF5733"`.  
  - `public String toHex()`: devuelve la representación hexadecimal en formato `"#RRGGBB"`.

---

:::note[¿Quieres revisar el código completo?]
Consulta todas las implementaciones detalladas en el [Boletín 4.2 Resuelto](./boletin-04-intermedio-resuelto).
:::
