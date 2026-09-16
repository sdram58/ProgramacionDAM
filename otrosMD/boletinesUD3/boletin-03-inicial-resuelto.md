---
title: Boletín U03 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre bucles y arrays en Java
---

# ✅ Boletín U03 — Inicial (resuelto)

1. **Números del 1 al 50**
   <details>
   <summary>💡 Solución</summary>

   ```java
   for (int i = 1; i <= 50; i++) {
       System.out.println(i);
   }
   ```
   </details>

2. **Contar números hasta el 0**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   public class ContarHastaCero {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           int contador = 0;
           int n;
           do {
               n = sc.nextInt();
               if (n != 0) contador++;
           } while (n != 0);
           System.out.println("Números introducidos: " + contador);
       }
   }
   ```
   </details>

3. **Tabla de multiplicar**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   public class TablaMultiplicar {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           int n = sc.nextInt();
           for (int i = 1; i <= 10; i++) {
               System.out.println(n + " x " + i + " = " + (n * i));
           }
       }
   }
   ```
   </details>

4. **Suma de un array del 1 al 10**
   <details>
   <summary>💡 Solución</summary>

   ```java
   int[] numeros = new int[10];
   int suma = 0;
   for (int i = 0; i < numeros.length; i++) {
       numeros[i] = i + 1;
       suma += numeros[i];
   }
   System.out.println("Suma: " + suma);
   ```
   </details>

5. **Nota más alta y más baja**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Scanner;

   public class NotasExtremas {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           double[] notas = new double[5];
           for (int i = 0; i < notas.length; i++) {
               notas[i] = sc.nextDouble();
           }
           double max = notas[0], min = notas[0];
           for (double nota : notas) {
               if (nota > max) max = nota;
               if (nota < min) min = nota;
           }
           System.out.println("Más alta: " + max + " - Más baja: " + min);
       }
   }
   ```
   </details>

6. **Ordenar con Arrays.sort**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import java.util.Arrays;
   import java.util.Scanner;

   public class OrdenarNumeros {
       public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
           int[] numeros = new int[6];
           for (int i = 0; i < numeros.length; i++) {
               numeros[i] = sc.nextInt();
           }
           Arrays.sort(numeros);
           System.out.println(Arrays.toString(numeros));
       }
   }
   ```
   </details>

7. **Contar vocales**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String texto = "Programación en Java";
   int vocales = 0;
   String vocalesValidas = "aeiouAEIOU";
   for (int i = 0; i < texto.length(); i++) {
       if (vocalesValidas.indexOf(texto.charAt(i)) != -1) {
           vocales++;
       }
   }
   System.out.println("Vocales: " + vocales);
   ```

   `indexOf` sobre `vocalesValidas` devuelve `-1` si el carácter no está en esa cadena — una forma compacta de comprobar "¿este carácter es una vocal?" sin encadenar cinco comparaciones con `||`.
   </details>

8. **while vs. do-while**
   <details>
   <summary>💡 Solución</summary>

   La diferencia está en **cuándo se evalúa la condición**: `while` la comprueba **antes** de cada vuelta (puede que el cuerpo no se ejecute nunca), y `do-while` la comprueba **después** (el cuerpo se ejecuta siempre, al menos una vez). Se elige `do-while` cuando por la propia lógica del programa necesitas ejecutar algo obligatoriamente una primera vez antes de poder comprobar la condición — el caso típico es pedir un dato al usuario y luego validar si hay que seguir pidiéndolo (como en el ejercicio 2 de este boletín).
   </details>
