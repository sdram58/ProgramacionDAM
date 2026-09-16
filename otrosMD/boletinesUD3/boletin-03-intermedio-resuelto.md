---
title: Boletín U03 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre excepciones, arrays, métodos y expresiones regulares
---

# ✅ Boletín U03 — Intermedio (resuelto)

1. **Palíndromo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static boolean esPalindromo(String texto) {
       int izquierda = 0;
       int derecha = texto.length() - 1;
       while (izquierda < derecha) {
           if (texto.charAt(izquierda) != texto.charAt(derecha)) {
               return false;
           }
           izquierda++;
           derecha--;
       }
       return true;
   }
   ```

   Comparamos desde los dos extremos hacia el centro; en cuanto un par de caracteres no coincide, ya sabemos que no es un palíndromo y podemos devolver `false` sin seguir comprobando.
   </details>

2. **División con dos excepciones distintas**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.InputMismatchException;
   import java.util.Scanner;

   public class DivisionSegura {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           try {
               System.out.print("Numerador: ");
               int a = sc.nextInt();
               System.out.print("Denominador: ");
               int b = sc.nextInt();
               System.out.println("Resultado: " + (a / b));
           } catch (ArithmeticException e) {
               System.out.println("No se puede dividir entre 0");
           } catch (InputMismatchException e) {
               System.out.println("Debes introducir números válidos");
           }
       }
   }
   ```
   </details>

3. **Tablero 3x3**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static void mostrarTablero(int[][] tablero) {
       for (int fila = 0; fila < tablero.length; fila++) {
           for (int columna = 0; columna < tablero[fila].length; columna++) {
               System.out.print(tablero[fila][columna] + " ");
           }
           System.out.println();
       }
   }

   public static void main(String[] args) {
       int[][] tablero = new int[3][3];
       mostrarTablero(tablero);
   }
   ```

   El bucle exterior recorre las filas, y el interior recorre las columnas de esa fila — el patrón estándar para procesar cualquier matriz.
   </details>

4. **Contar ocurrencias**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static int contarOcurrencias(int[] array, int valor) {
       int contador = 0;
       for (int elemento : array) {
           if (elemento == valor) contador++;
       }
       return contador;
   }

   public static void main(String[] args) {
       int[] numeros = {3, 7, 3, 2, 3, 9};
       System.out.println(contarOcurrencias(numeros, 3));  // 3
   }
   ```
   </details>

5. **Potencia recursiva**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static long potencia(int base, int exponente) {
       if (exponente == 0) return 1;             // caso base
       return base * potencia(base, exponente - 1);  // caso recursivo
   }
   ```
   </details>

6. **Validar email con regex**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   public class ValidarEmail {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           String email = sc.nextLine();
           boolean valido = email.matches("\\w+@\\w+\\.\\w+");
           System.out.println(valido ? "Email válido" : "Email no válido");
       }
   }
   ```

   Es una validación deliberadamente simple (no cubre todos los formatos de email reales, que son mucho más complejos) — pero ya demuestra el patrón básico: texto, arroba, texto, punto, texto.
   </details>

7. **Array mutable pasado por valor**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static void vaciar(int[] array) {
       for (int i = 0; i < array.length; i++) {
           array[i] = 0;
       }
   }

   public static void main(String[] args) {
       int[] datos = {1, 2, 3, 4, 5};
       vaciar(datos);
       System.out.println(java.util.Arrays.toString(datos));  // [0, 0, 0, 0, 0]
   }
   ```

   El parámetro `array` es una copia de la **referencia**, pero apunta al mismo objeto en memoria que `datos` — por eso modificar su contenido dentro del método sí se refleja fuera, aunque técnicamente el paso siga siendo "por valor" (el valor que se copia es la dirección, no el array entero).
   </details>

8. **try-catch-finally**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   public class DivisionConFinally {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           try {
               int a = sc.nextInt();
               int b = sc.nextInt();
               System.out.println("Resultado: " + (a / b));
           } catch (ArithmeticException e) {
               System.out.println("No se puede dividir entre 0");
           } finally {
               System.out.println("Operación finalizada");
           }
       }
   }
   ```
   </details>
