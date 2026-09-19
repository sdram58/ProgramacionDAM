---
title: "Boletín 3.1: Nivel Inicial — Soluciones Resueltas"
description: "Soluciones completas y comentadas de los 12 ejercicios iniciales de la Unidad 3 en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 2
  badge:
    text: "Soluciones"
    variant: "success"
---

A continuación se presentan las soluciones comentadas de los 12 ejercicios del boletín inicial, desarrolladas siguiendo las directrices de robustez, legibilidad y buenas prácticas en Java 25 LTS.

---

### Solución Ejercicio 1: Tabla de Multiplicar Segura

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio01TablaSegura {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce un número entero (1 - 10): ");
                numero = sc.nextInt();

                if (numero >= 1 && numero <= 10) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: El número debe estar entre 1 y 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes introducir un número entero válido.");
                sc.nextLine(); // Limpiar el token no numérico del buffer
            }
        }

        System.out.println("=== TABLA DE MULTIPLICAR DEL " + numero + " ===");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%2d × %2d = %3d%n", numero, i, (numero * i));
        }
    }
}
```

---

### Solución Ejercicio 2: Contador de Dígitos y Suma de Cifras

```java
import java.util.Scanner;

public class Ejercicio02DigitosSuma {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un número entero positivo: ");
        long numero = sc.nextLong();

        if (numero < 0) {
            numero = Math.abs(numero);
        }

        if (numero == 0) {
            System.out.println("Tiene 1 dígito y la suma de sus cifras es 0.");
            return;
        }

        long copia = numero;
        int cantidadDigitos = 0;
        long sumaCifras = 0;

        while (copia > 0) {
            long ultimaCifra = copia % 10;
            sumaCifras += ultimaCifra;
            cantidadDigitos++;
            copia /= 10; // Desplaza una cifra a la derecha
        }

        System.out.println("Número analizado: " + numero);
        System.out.println("Total de dígitos: " + cantidadDigitos);
        System.out.println("Suma de sus cifras: " + sumaCifras);
    }
}
```

---

### Solución Ejercicio 3: Menú Interactivo de Gestión de Saldo

```java
import java.util.Scanner;

public class Ejercicio03MenuSaldo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double saldo = 100.0;
        int opcion = 0;

        do {
            System.out.println("\n--- CAJERO AUTOMÁTICO ---");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Ingresar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("Opción no válida. Introduzca un número de 1 a 4.");
                sc.nextLine();
                continue;
            }

            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> System.out.printf("Saldo actual: %.2f€%n", saldo);
                case 2 -> {
                    System.out.print("Importe a ingresar: ");
                    double ingreso = sc.nextDouble();
                    if (ingreso > 0) {
                        saldo += ingreso;
                        System.out.printf("Ingreso realizado. Nuevo saldo: %.2f€%n", saldo);
                    } else {
                        System.out.println("Error: El importe a ingresar debe ser estrictamente positivo.");
                    }
                }
                case 3 -> {
                    System.out.print("Importe a retirar: ");
                    double retirada = sc.nextDouble();
                    if (retirada <= 0) {
                        System.out.println("Error: El importe a retirar debe ser mayor que 0.");
                    } else if (retirada > saldo) {
                        System.out.println("Error: Saldo insuficiente. Saldo disponible: " + saldo + "€");
                    } else {
                        saldo -= retirada;
                        System.out.printf("Retirada completada. Saldo restante: %.2f€%n", saldo);
                    }
                }
                case 4 -> System.out.println("Gracias por utilizar nuestros servicios. ¡Hasta pronto!");
                default -> System.out.println("Opción desconocida. Elija entre 1 y 4.");
            }
        } while (opcion != 4);
    }
}
```

---

### Solución Ejercicio 4: Dibujando Patrones con Bucles Anidados

```java
import java.util.Scanner;

public class Ejercicio04TrianguloAsteriscos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la altura del triángulo (2 - 15): ");
        int altura = sc.nextInt();

        if (altura < 2 || altura > 15) {
            System.out.println("Altura fuera de límites. Ajustando a 5 por defecto.");
            altura = 5;
        }

        System.out.println("\nTriángulo normal:");
        for (int fila = 1; fila <= altura; fila++) {
            for (int col = 1; col <= fila; col++) {
                System.out.print("*");
            }
            System.out.println();
        }

        System.out.println("\nTriángulo invertido:");
        for (int fila = altura; fila >= 1; fila--) {
            for (int col = 1; col <= fila; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
```

---

### Solución Ejercicio 5: La Conjetura de Collatz

```java
import java.util.Scanner;

public class Ejercicio05Collatz {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un número entero positivo: ");
        long n = sc.nextLong();

        if (n <= 0) {
            System.out.println("El número debe ser estrictamente positivo.");
            return;
        }

        int pasos = 0;
        System.out.print("Secuencia: " + n);

        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = (3 * n) + 1;
            }
            System.out.print(" -> " + n);
            pasos++;
        }

        System.out.println();
        System.out.println("Proceso concluido en " + pasos + " pasos.");
    }
}
```

---

### Solución Ejercicio 6: Análisis de Temperaturas en un Vector

```java
import java.util.Scanner;

public class Ejercicio06AnalisisTemperaturas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        double[] temp = new double[7];

        for (int i = 0; i < temp.length; i++) {
            System.out.print("Temperatura del " + dias[i] + ": ");
            temp[i] = sc.nextDouble();
        }

        double max = temp[0];
        double min = temp[0];
        int diaMax = 0;
        double suma = 0;

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] > max) {
                max = temp[i];
                diaMax = i;
            }
            if (temp[i] < min) {
                min = temp[i];
            }
            suma += temp[i];
        }

        double media = suma / temp.length;

        System.out.println("=== RESUMEN CLIMATOLÓGICO ===");
        System.out.printf("Temperatura Máxima: %.1f°C (registrada el %s)%n", max, dias[diaMax]);
        System.out.printf("Temperatura Mínima: %.1f°C%n", min);
        System.out.printf("Temperatura Media:  %.2f°C%n", media);
    }
}
```

---

### Solución Ejercicio 7: Registro de Calificaciones con `for-each`

```java
public class Ejercicio07Calificaciones {
    public static void main(String[] args) {
        double[] notas = {4.5, 7.8, 9.2, 3.0, 5.0, 8.4, 6.2, 9.6, 4.9, 10.0};

        int sobresalientes = 0;
        int aprobados = 0;
        int suspensos = 0;

        for (double nota : notas) {
            if (nota >= 9.0) {
                sobresalientes++;
            }
            if (nota >= 5.0) {
                aprobados++;
            } else {
                suspensos++;
            }
        }

        System.out.println("Total alumnos analizados: " + notas.length);
        System.out.println("Aprobados: " + aprobados);
        System.out.println("Suspensos: " + suspensos);
        System.out.println("Sobresalientes (≥ 9.0): " + sobresalientes);
    }
}
```

---

### Solución Ejercicio 8: Inversión In-Place de un Vector

```java
import java.util.Arrays;

public class Ejercicio08InversionVector {
    public static void main(String[] args) {
        int[] datos = {10, 20, 30, 40, 50, 60, 70};

        System.out.println("Vector original: " + Arrays.toString(datos));

        int izquierda = 0;
        int derecha = datos.length - 1;

        while (izquierda < derecha) {
            // Intercambio sin crear un segundo vector
            int aux = datos[izquierda];
            datos[izquierda] = datos[derecha];
            datos[derecha] = aux;

            izquierda++;
            derecha--;
        }

        System.out.println("Vector invertido in-place: " + Arrays.toString(datos));
    }
}
```

---

### Solución Ejercicio 9: Contabilizador de Vocales y Consonantes

```java
import java.util.Scanner;

public class Ejercicio09VocalesConsonantes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una frase: ");
        String frase = sc.nextLine().toLowerCase();

        int vocales = 0;
        int consonantes = 0;
        int otros = 0;

        for (int i = 0; i < frase.length(); i++) {
            char c = frase.charAt(i);

            if (c >= 'a' && c <= 'z') {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vocales++;
                } else {
                    consonantes++;
                }
            } else {
                otros++;
            }
        }

        System.out.println("Vocales: " + vocales);
        System.out.println("Consonantes: " + consonantes);
        System.out.println("Espacios y otros símbolos: " + otros);
    }
}
```

---

### Solución Ejercicio 10: Métodos Sobrecargados para Áreas

```java
public class Ejercicio10AreasSobrecarga {

    // Cuadrado
    public static double calcularArea(double lado) {
        if (lado <= 0) {
            throw new IllegalArgumentException("El lado debe ser estrictamente positivo.");
        }
        return lado * lado;
    }

    // Rectángulo
    public static double calcularArea(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException("La base y la altura deben ser positivas.");
        }
        return base * altura;
    }

    // Círculo
    public static double calcularArea(double radio, boolean esCirculo) {
        if (radio <= 0) {
            throw new IllegalArgumentException("El radio debe ser positivo.");
        }
        return Math.PI * Math.pow(radio, 2);
    }

    public static void main(String[] args) {
        System.out.printf("Área de cuadrado (lado 4): %.2f%n", calcularArea(4.0));
        System.out.printf("Área de rectángulo (base 5, altura 3): %.2f%n", calcularArea(5.0, 3.0));
        System.out.printf("Área de círculo (radio 2.5): %.2f%n", calcularArea(2.5, true));
    }
}
```

---

### Solución Ejercicio 11: Potencia Recursiva

```java
public class Ejercicio11PotenciaRecursiva {

    public static long calcularPotencia(int base, int exponente) {
        if (exponente < 0) {
            throw new IllegalArgumentException("Exponente negativo no soportado para enteros.");
        }
        // Caso Base
        if (exponente == 0) {
            return 1;
        }
        // Caso Recursivo
        return base * calcularPotencia(base, exponente - 1);
    }

    public static void main(String[] args) {
        System.out.println("2^0 = " + calcularPotencia(2, 0));
        System.out.println("2^5 = " + calcularPotencia(2, 5));
        System.out.println("3^4 = " + calcularPotencia(3, 4));
        System.out.println("5^3 = " + calcularPotencia(5, 3));
    }
}
```

---

### Solución Ejercicio 12: Validador de Código Postal Español con Regex

```java
public class Ejercicio12ValidadorCP {

    public static boolean esCodigoPostalValido(String cp) {
        if (cp == null) {
            return false;
        }

        // Exactamente 5 dígitos
        if (!cp.matches("^\\d{5}$")) {
            return false;
        }

        // Las dos primeras cifras corresponden a la provincia (01 a 52)
        int provincia = Integer.parseInt(cp.substring(0, 2));
        return provincia >= 1 && provincia <= 52;
    }

    public static void main(String[] args) {
        String[] pruebas = {"46001", "28013", "08001", "53001", "1234", "ABCDE", "00123"};

        for (String test : pruebas) {
            System.out.printf("Código '%s' -> ¿Válido? %b%n", test, esCodigoPostalValido(test));
        }
    }
}
```
