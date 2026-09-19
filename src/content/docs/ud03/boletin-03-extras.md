---
title: "Boletín 3.3: Retos Algorítmicos y Programación Competitiva"
description: "Retos de alta intensidad algorítmica inspirados en plataformas de programación competitiva (CodeWars, Acepta el Reto, LeetCode) para la Unidad 3 en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 5
  badge:
    text: "Retos Extras"
    variant: "danger"
---

Bienvenido al boletín de retos competitivos de la **Unidad 3: Excepciones, bucles, arrays y métodos**.

Los siguientes 6 desafíos plantean problemas de optimización computacional, complejidad temporal y espacial, manipulación avanzada de memoria y algoritmos clásicos de la informática. Cada reto incluye su planteamiento formal, análisis de complejidad, pistas estratégicas y la solución íntegra comentada en Java 25 LTS.

---

### Reto 1: La Criba de Eratóstenes (Generador de Números Primos)

#### Contexto y Planteamiento
Encontrar todos los números primos hasta un límite `N` comprobando la primalidad de cada número uno por uno mediante divisiones sucesivas tiene una complejidad temporal inasumible `O(N × √N)`. El sabio griego Eratóstenes diseñó un método de filtrado que tacha sistemáticamente los múltiplos de cada primo encontrado.

Implementa un método `public static int[] cribaEratostenes(int n)` que devuelva un array con todos los números primos menores o iguales a `n`.

- **Complejidad requerida:** `O(N log log N)` en tiempo.
- **Pista algorítmica:** Usa un array booleano `esPrimo` de tamaño `n + 1` inicializado a `true`. Empieza en `p = 2`; tacha todos sus múltiplos comenzando directamente desde `p × p`. El bucle exterior solo necesita iterar hasta `√n`.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Arrays;

public class Reto01CribaEratostenes {

    public static int[] cribaEratostenes(int n) {
        if (n < 2) return new int[0];

        boolean[] esPrimo = new boolean[n + 1];
        Arrays.fill(esPrimo, true);
        esPrimo[0] = false;
        esPrimo[1] = false;

        // Bucle exterior hasta la raíz cuadrada de n
        for (int p = 2; p * p <= n; p++) {
            if (esPrimo[p]) {
                // Tachar múltiplos empezando en p * p
                for (int multiplo = p * p; multiplo <= n; multiplo += p) {
                    esPrimo[multiplo] = false;
                }
            }
        }

        // Conteo de primos para dimensionar el vector de salida
        int totalPrimos = 0;
        for (int i = 2; i <= n; i++) {
            if (esPrimo[i]) totalPrimos++;
        }

        int[] resultado = new int[totalPrimos];
        int pos = 0;
        for (int i = 2; i <= n; i++) {
            if (esPrimo[i]) {
                resultado[pos++] = i;
            }
        }

        return resultado;
    }

    public static void main(String[] args) {
        int limite = 50;
        int[] primos = cribaEratostenes(limite);
        System.out.println("Primos hasta " + limite + ": " + Arrays.toString(primos));
    }
}
```
</details>

---

### Reto 2: Compresión de Cadenas por Racha (Run-Length Encoding)

#### Contexto y Planteamiento
El algoritmo **RLE** es una técnica clásica de compresión de datos sin pérdida. Consiste en sustituir secuencias repetidas consecutivas del mismo carácter por el número de repeticiones seguido del carácter en cuestión.  
*Ejemplo:* La cadena `"AAAABBBCCDAA"` se comprime como `"4A3B2C1D2A"`.

Diseña dos métodos complementarios:
1. `public static String comprimirRLE(String texto)`
2. `public static String descomprimirRLE(String textoComprimido)`

- **Requisito:** Utiliza `StringBuilder` para garantizar una complejidad lineal `O(N)` en tiempo y evitar la sobrecarga del Garbage Collector.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto02CompresionRLE {

    public static String comprimirRLE(String texto) {
        if (texto == null || texto.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int contador = 1;

        for (int i = 0; i < texto.length(); i++) {
            // Si el siguiente carácter es igual, incrementamos la racha
            if (i + 1 < texto.length() && texto.charAt(i) == texto.charAt(i + 1)) {
                contador++;
            } else {
                // Al cambiar de carácter o llegar al final, volcamos la racha
                sb.append(contador).append(texto.charAt(i));
                contador = 1;
            }
        }
        return sb.toString();
    }

    public static String descomprimirRLE(String comprimido) {
        if (comprimido == null || comprimido.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        StringBuilder numBuffer = new StringBuilder();

        for (int i = 0; i < comprimido.length(); i++) {
            char c = comprimido.charAt(i);

            if (Character.isDigit(c)) {
                numBuffer.append(c);
            } else {
                int repeticiones = Integer.parseInt(numBuffer.toString());
                numBuffer.setLength(0); // Vaciar buffer numérico
                for (int r = 0; r < repeticiones; r++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String original = "AAAABBBCCDAA";
        String comprimido = comprimirRLE(original);
        String restaurado = descomprimirRLE(comprimido);

        System.out.println("Original:      " + original);
        System.out.println("Comprimido:    " + comprimido);
        System.out.println("Restaurado:    " + restaurado);
        System.out.println("¿Es idéntico? " + original.equals(restaurado));
    }
}
```
</details>

---

### Reto 3: El Número Perdido en Tiempo Lineal y Memoria O(1)

#### Contexto y Planteamiento
Se te proporciona un array desordenado de tamaño `N - 1` que contiene números enteros del `1` al `N` sin ningún duplicado, excepto **exactamente un número que ha desaparecido**.

Diseña un método `public static int encontrarFaltante(int[] arr, int n)` que localice el número omitido cumpliendo:
- Complejidad Temporal: `O(N)`.
- Espacio Adicional: `O(1)` (está prohibido crear nuevos arrays o estructuras intermedias).

- **Pista matemática:** La suma de los primeros `N` números naturales viene dada por la fórmula de Gauss `Suma = N × (N + 1) / 2`. La diferencia entre la suma teórica y la suma real de los elementos del array es el número que falta.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto03NumeroPerdido {

    public static int encontrarFaltante(int[] arr, int n) {
        // Usamos long para prevenir cualquier desbordamiento de enteros en sumas grandes
        long sumaEsperada = (long) n * (n + 1) / 2;
        long sumaReal = 0;

        for (int num : arr) {
            sumaReal += num;
        }

        return (int) (sumaEsperada - sumaReal);
    }

    // Solución alternativa elegante usando el operador XOR (evita totalmente overflow de enteros)
    public static int encontrarFaltanteXOR(int[] arr, int n) {
        int xorTotal = 0;
        for (int i = 1; i <= n; i++) {
            xorTotal ^= i;
        }
        for (int num : arr) {
            xorTotal ^= num;
        }
        return xorTotal;
    }

    public static void main(String[] args) {
        int n = 8;
        // Falta el número 5
        int[] datos = {3, 7, 1, 2, 8, 4, 6};

        System.out.println("Faltante (Gauss): " + encontrarFaltante(datos, n)); // 5
        System.out.println("Faltante (XOR):   " + encontrarFaltanteXOR(datos, n)); // 5
    }
}
```
</details>

---

### Reto 4: Rotación Cíclica de un Vector In-Place (Algoritmo de las Tres Inversiones)

#### Contexto y Planteamiento
Dado un array de enteros y un valor `k`, rota los elementos del array hacia la derecha `k` posiciones.  
*Ejemplo:* Si `arr = {1, 2, 3, 4, 5, 6, 7}` y `k = 3`, el array resultante debe ser `{5, 6, 7, 1, 2, 3, 4}`.

- **Requisito estricto:** El algoritmo debe modificar el array in-place, con memoria adicional `O(1)` y tiempo `O(N)`.
- **Estrategia (Las 3 Inversiones):**
  1. Invierte el array completo: `{7, 6, 5, 4, 3, 2, 1}`.
  2. Invierte los primeros `k` elementos: `{5, 6, 7, 4, 3, 2, 1}`.
  3. Invierte los elementos restantes desde `k` hasta el final: `{5, 6, 7, 1, 2, 3, 4}`.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Arrays;

public class Reto04RotacionVectorInPlace {

    public static void rotarDerecha(int[] arr, int k) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        k = k % n; // Si k >= n, reducimos al equivalente modular
        if (k == 0) return;

        // 1. Invertir todo el array
        invertirRango(arr, 0, n - 1);
        // 2. Invertir los primeros k elementos
        invertirRango(arr, 0, k - 1);
        // 3. Invertir el resto
        invertirRango(arr, k, n - 1);
    }

    private static void invertirRango(int[] arr, int inicio, int fin) {
        while (inicio < fin) {
            int temp = arr[inicio];
            arr[inicio] = arr[fin];
            arr[fin] = temp;
            inicio++;
            fin--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;

        System.out.println("Antes:  " + Arrays.toString(nums));
        rotarDerecha(nums, k);
        System.out.println("Después:" + Arrays.toString(nums));
    }
}
```
</details>

---

### Reto 5: Las Torres de Hanói (Recursividad Pura)

#### Contexto y Planteamiento
Las Torres de Hanói es un rompecabezas clásico donde se deben trasladar `N` discos de radios decrecientes desde la varilla `Origen` hasta la varilla `Destino`, utilizando una varilla `Auxiliar` intermedia.  
- Solo se puede mover un disco por turno.  
- Ningún disco mayor puede descansar sobre uno menor en ningún momento.

Diseña un método recursivo `public static void resolverHanoi(int n, char origen, char auxiliar, char destino)` que imprima cada movimiento elemental y devuelva la cantidad total de movimientos (`2^N - 1`).

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto05TorresHanoi {

    private static int totalMovimientos = 0;

    public static void resolverHanoi(int n, char origen, char auxiliar, char destino) {
        // Caso Base: trasladar 1 disco directamente al destino
        if (n == 1) {
            totalMovimientos++;
            System.out.printf("Paso %2d: Mover disco 1 desde [%c] hasta [%c]%n", totalMovimientos, origen, destino);
            return;
        }

        // Paso 1: Mover n - 1 discos de Origen a Auxiliar (usando Destino como apoyo)
        resolverHanoi(n - 1, origen, destino, auxiliar);

        // Paso 2: Mover el disco más grande (n) directamente a Destino
        totalMovimientos++;
        System.out.printf("Paso %2d: Mover disco %d desde [%c] hasta [%c]%n", totalMovimientos, n, origen, destino);

        // Paso 3: Mover los n - 1 discos de Auxiliar a Destino (usando Origen como apoyo)
        resolverHanoi(n - 1, auxiliar, origen, destino);
    }

    public static void main(String[] args) {
        int discos = 3;
        System.out.println("=== RESOLUCIÓN TORRES DE HANÓI (" + discos + " DISCOS) ===");
        resolverHanoi(discos, 'A', 'B', 'C');
        System.out.println("Movimientos totales ejecutados: " + totalMovimientos);
        System.out.println("Mínimo teórico (2^n - 1):        " + ((1 << discos) - 1));
    }
}
```
</details>

---

### Reto 6: Validador de Delimitadores Equilibrados con Simulación de Pila

#### Contexto y Planteamiento
En compiladores e intérpretes, es crucial verificar que los delimitadores de apertura `(`, `[`, `{` se cierran en el orden correcto con sus respectivos cierres `)`, `]`, `}`.  
*Ejemplos:*
- `"{ [ ( ) ] }"` -> **Válido** (anidamiento perfecto).
- `"( [ ) ]"` -> **Inválido** (cruce de delimitadores).
- `"((()"` -> **Inválido** (delimitadores sin cerrar).

Implementa un método `public static boolean sonDelimitadoresValidos(String codigo)` empleando un array de caracteres `char[]` para simular internamente la estructura de una pila (LIFO) y un puntero de tope `top`.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto06DelimitadoresEquilibrados {

    public static boolean sonDelimitadoresValidos(String expresion) {
        if (expresion == null) return true;

        // Pila simulada sobre un array con un puntero de cima (top)
        char[] pila = new char[expresion.length()];
        int tope = -1;

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si es apertura, lo apilamos (push)
            if (c == '(' || c == '[' || c == '{') {
                pila[++tope] = c;
            } 
            // Si es cierre, comprobamos el tope
            else if (c == ')' || c == ']' || c == '}') {
                if (tope == -1) {
                    return false; // Intento de cierre con pila vacía
                }

                char abierto = pila[tope--]; // Desapilar (pop)

                if (c == ')' && abierto != '(') return false;
                if (c == ']' && abierto != '[') return false;
                if (c == '}' && abierto != '{') return false;
            }
        }

        // Si la pila quedó vacía, todos los delimitadores se emparejaron con éxito
        return tope == -1;
    }

    public static void main(String[] args) {
        String[] pruebas = {
            "{ [ a * (b + c) ] }",
            "( [ ) ]",
            "((((",
            "{ public static void main(String[] args) { System.out.println(1); } }",
            " } "
        };

        for (String p : pruebas) {
            System.out.printf("Expresión: '%s' -> ¿Válida? %b%n", p, sonDelimitadoresValidos(p));
        }
    }
}
```
</details>
