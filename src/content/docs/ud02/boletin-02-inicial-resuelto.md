---
title: 📝 Boletín 02 Inicial (Resuelto) — Primeros Pasos con Java
description: Soluciones completas, verificadas y comentadas línea a línea en Java 21 LTS con IntelliJ IDEA para los 13 ejercicios del Boletín Inicial de la Unidad 2.
draft: true
prev: false
next: false
---

A continuación se presentan las soluciones comentadas a los 13 ejercicios propuestos en el [Boletín Inicial](./boletin-02-inicial/). Cada programa ha sido verificado con Java 21 LTS en IntelliJ IDEA.

---

### Solución al Ejercicio 1: Saludo Personalizado y Formato de Ficha de Usuario

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class FichaUsuario {
    public static void main(String[] args) {
        // Configuramos Scanner con Locale.US para asegurar el punto decimal en la cuota
        Scanner teclado = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce tu nombre: ");
        String nombre = teclado.next();

        System.out.print("Introduce tu primer apellido: ");
        String apellido = teclado.next();

        System.out.print("Introduce tu edad: ");
        int edad = teclado.nextInt();

        System.out.print("Introduce tu grupo (A/B): ");
        char grupo = teclado.next().toUpperCase().charAt(0);

        System.out.print("Introduce la cuota mensual en euros: ");
        double cuota = teclado.nextDouble();

        System.out.println("\n" + "=".repeat(40));
        System.out.println("         FICHA DE MATRÍCULA DAM        ");
        System.out.println("=".repeat(40));

        // Formato tabulado con una única llamada a System.out.printf
        System.out.printf("Alumno:     %-15s, %-10s%n" +
                          "Edad:       %d años%n" +
                          "Grupo DAM:  %c%n" +
                          "Cuota mes:  %8.2f €%n", 
                          apellido, nombre, edad, grupo, cuota);
        
        System.out.println("=".repeat(40));
        teclado.close();
    }
}
```

---

### Solución al Ejercicio 2: Calculadora Geométrica de Rectángulo y Círculo

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class CalculadoraGeometrica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce la base del rectángulo (m): ");
        double base = sc.nextDouble();

        System.out.print("Introduce la altura del rectángulo (m): ");
        double altura = sc.nextDouble();

        if (base <= 0 || altura <= 0) {
            System.out.println("Error: Las dimensiones del rectángulo deben ser estrictamente positivas.");
            sc.close();
            return; // Detiene la ejecución del programa
        }

        double perimetro = 2 * (base + altura);
        double areaRectangulo = base * altura;

        System.out.printf("Rectángulo -> Perímetro: %.2f m | Área: %.2f m²%n", perimetro, areaRectangulo);

        System.out.print("\nIntroduce el radio de la circunferencia (m): ");
        double radio = sc.nextDouble();

        if (radio <= 0) {
            System.out.println("Error: El radio debe ser mayor que cero.");
            sc.close();
            return;
        }

        double longitud = 2 * Math.PI * radio;
        double areaCirculo = Math.PI * Math.pow(radio, 2);

        System.out.printf("Círculo    -> Longitud: %.2f m | Área: %.2f m²%n", longitud, areaCirculo);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 3: Conversor Bidireccional de Escalas Térmicas

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class ConversorTermico {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce la temperatura en grados Celsius (°C): ");
        double celsius = sc.nextDouble();

        // ¡IMPORTANTE!: Usar 9.0 / 5.0 en coma flotante para evitar división entera truncada a 1
        double fahrenheitCalculado = (celsius * 9.0 / 5.0) + 32.0;
        System.out.printf("%.2f °C equivalen a %.2f °F%n", celsius, fahrenheitCalculado);

        System.out.print("\nIntroduce la temperatura en grados Fahrenheit (°F): ");
        double fahrenheit = sc.nextDouble();

        double celsiusCalculado = (fahrenheit - 32.0) * 5.0 / 9.0;
        System.out.printf("%.2f °F equivalen a %.2f °C%n", fahrenheit, celsiusCalculado);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 4: Calificación Final Ponderada

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class CalificacionPonderada {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce la nota de teoría (0-10): ");
        double teoria = sc.nextDouble();

        System.out.print("Introduce la nota de prácticas (0-10): ");
        double practicas = sc.nextDouble();

        System.out.print("Introduce la nota de actitud (0-10): ");
        double actitud = sc.nextDouble();

        // Validación defensiva del rango de notas
        if (teoria < 0 || teoria > 10 || practicas < 0 || practicas > 10 || actitud < 0 || actitud > 10) {
            System.out.println("Error: Todas las calificaciones deben encontrarse en el intervalo [0.0, 10.0].");
            sc.close();
            return;
        }

        // Cálculo ponderado: 30% teoría + 50% prácticas + 20% actitud
        double notaFinal = (teoria * 0.30) + (practicas * 0.50) + (actitud * 0.20);

        String mencion;
        if (notaFinal < 5.0) {
            mencion = "Suspenso";
        } else if (notaFinal < 7.0) {
            mencion = "Aprobado";
        } else if (notaFinal < 9.0) {
            mencion = "Notable";
        } else {
            mencion = "Sobresaliente";
        }

        System.out.printf("%nNota final calculada: %.2f (%s)%n", notaFinal, mencion);
        sc.close();
    }
}
```

---

### Solución al Ejercicio 5: Facturación con Descuentos Escalonados e IVA

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class FacturacionComponentes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce el precio unitario del componente (€): ");
        double precioUnitario = sc.nextDouble();

        System.out.print("Introduce el número de unidades: ");
        int unidades = sc.nextInt();

        if (precioUnitario <= 0 || unidades <= 0) {
            System.out.println("Error: El precio y las unidades deben ser mayores que cero.");
            sc.close();
            return;
        }

        double importeBruto = precioUnitario * unidades;
        double porcentajeDescuento;

        if (importeBruto < 100.0) {
            porcentajeDescuento = 0.0;
        } else if (importeBruto < 300.0) {
            porcentajeDescuento = 0.05; // 5%
        } else {
            porcentajeDescuento = 0.12; // 12%
        }

        double eurosDescuento = importeBruto * porcentajeDescuento;
        double baseImponible = importeBruto - eurosDescuento;
        double iva = baseImponible * 0.21; // IVA general al 21%
        double totalFactura = baseImponible + iva;

        System.out.println("\n" + "-".repeat(45));
        System.out.println("             DESGLOSE DE FACTURA             ");
        System.out.println("-".repeat(45));
        System.out.printf("Importe Bruto:                 %8.2f €%n", importeBruto);
        System.out.printf("Descuento aplicado (%.0f%%):     -%8.2f €%n", porcentajeDescuento * 100, eurosDescuento);
        System.out.printf("Base Imponible:                %8.2f €%n", baseImponible);
        System.out.printf("IVA (21%%):                    +%8.2f €%n", iva);
        System.out.println("-".repeat(45));
        System.out.printf("TOTAL A PAGAR:                 %8.2f €%n", totalFactura);
        System.out.println("-".repeat(45));

        sc.close();
    }
}
```

---

### Solución al Ejercicio 6: Descomposición Temporal de Segundos

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class DescomposicionTiempo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el total de segundos: ");
        long segundosTotales = sc.nextLong();

        if (segundosTotales < 0) {
            System.out.println("Error: El tiempo no puede ser negativo.");
            sc.close();
            return;
        }

        // 1 hora = 3600 segundos
        long horas = segundosTotales / 3600;
        long segundosRestantes = segundosTotales % 3600;

        // 1 minuto = 60 segundos
        long minutos = segundosRestantes / 60;
        long segundos = segundosRestantes % 60;

        System.out.printf("%d segundos equivalen a: %d horas, %d minutos y %d segundos.%n", 
                          segundosTotales, horas, minutos, segundos);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 7: Detector de Paridad y Múltiplos

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class DetectorParidadMultiplos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce un número entero: ");
        int n = sc.nextInt();

        // 1. Signo
        if (n > 0) {
            System.out.println("-> Signo: Es un número POSITIVO.");
        } else if (n < 0) {
            System.out.println("-> Signo: Es un número NEGATIVO.");
        } else {
            System.out.println("-> Signo: Es el CERO neutro.");
        }

        // 2. Paridad mediante operador módulo
        if (n % 2 == 0) {
            System.out.println("-> Paridad: Es un número PAR.");
        } else {
            System.out.println("-> Paridad: Es un número IMPAR.");
        }

        // 3. Múltiplos de 3 y 5
        boolean mult3 = (n % 3 == 0);
        boolean mult5 = (n % 5 == 0);

        if (mult3 && mult5) {
            System.out.println("-> Divisibilidad: Es múltiplo simultáneo de 3 y de 5 (múltiplo de 15).");
        } else if (mult3) {
            System.out.println("-> Divisibilidad: Es múltiplo de 3.");
        } else if (mult5) {
            System.out.println("-> Divisibilidad: Es múltiplo de 5.");
        } else {
            System.out.println("-> Divisibilidad: No es múltiplo ni de 3 ni de 5.");
        }

        sc.close();
    }
}
```

---


### Solución al Ejercicio 8: Días del Mes con `switch` Moderno

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class DiasDelMesSwitch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número del mes (1 al 12): ");
        int mes = sc.nextInt();

        // Expresión switch moderna de Java (Java 14+):
        // Sin break, asignación directa a variable y múltiples casos separados por comas
        int dias = switch (mes) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11            -> 30;
            case 2                      -> 28; // Asumiendo año no bisiesto
            default                     -> -1; // Mes no válido
        };

        if (dias != -1) {
            System.out.printf("El mes %d tiene %d días.%n", mes, dias);
        } else {
            System.out.println("Error: El mes introducido debe estar entre 1 y 12.");
        }

        sc.close();
    }
}
```

---

### Solución al Ejercicio 9: El Misterio del Salto de Línea en Scanner

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class SolucionSaltoLineaScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número de bastidor del vehículo: ");
        int bastidor = sc.nextInt();

        // ¡ATENCIÓN CLAVE!
        // nextInt() lee únicamente los dígitos, pero deja el carácter '\n' (Enter) en el búfer.
        // Si no purgamos el búfer, el siguiente nextLine() se tragará ese '\n' residual
        // y devolverá una cadena vacía sin esperar a que el usuario escriba su nombre.
        sc.nextLine(); // Lectura de purga

        System.out.print("Introduce el nombre completo del titular: ");
        String titular = sc.nextLine();

        System.out.println("\n--- REGISTRO COMPLETADO CORRECTAMENTE ---");
        System.out.printf("Titular:           %s%n", titular);
        System.out.printf("Nº de Bastidor:    %d%n", bastidor);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 10: Simulador de Lanzamiento de Dados

```java
package es.iesperenxisa.primerospasos;

public class SimuladorDados {
    public static void main(String[] args) {
        // Fórmula general para entero aleatorio en [min, max]:
        // (int)(Math.random() * (max - min + 1)) + min
        
        int dado6 = (int)(Math.random() * 6) + 1;       // Rango [1, 6]
        int dado20 = (int)(Math.random() * 20) + 1;     // Rango [1, 20]
        int total = dado6 + dado20;

        System.out.println("🎲 Tirada de dados:");
        System.out.printf("-> Dado clásico (D6):   %d%n", dado6);
        System.out.printf("-> Dado de rol  (D20):  %d%n", dado20);
        System.out.printf("-> Suma total:          %d%n", total);

        // Comprobación de eventos especiales en el D20
        if (dado20 == 20) {
            System.out.println("🌟 ¡ÉXITO CRÍTICO! Impacto definitivo garantizado.");
        } else if (dado20 == 1) {
            System.out.println("💀 ¡PIFIA CRÍTICA! Fallo catastrófico en la acción.");
        }
    }
}
```

---

### Solución al Ejercicio 11: Traducción y Evaluación de Condiciones Lógicas Complejas

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class EvaluadorCondicionesLogicas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("=== 1. CONCESIÓN DE BECA DE ESTUDIOS ===");
        System.out.print("Nota media del alumno (0.0 - 10.0): ");
        double notaMedia = sc.nextDouble();
        System.out.print("Ingresos familiares anuales (€): ");
        double ingresos = sc.nextDouble();

        // Regla: nota >= 8.5 O (ingresos < 15000 Y nota >= 6.0)
        boolean becaConcedida = (notaMedia >= 8.5) || (ingresos < 15000.0 && notaMedia >= 6.0);
        System.out.printf("¿Beca concedida?: %b%n%n", becaConcedida);

        System.out.println("=== 2. AUTORIZACIÓN DE DESPEGUE ===");
        System.out.print("Velocidad del viento (m/s): ");
        double viento = sc.nextDouble();
        System.out.print("Longitud de pista disponible (m): ");
        double pista = sc.nextDouble();
        System.out.print("Visibilidad meteorológica (m): ");
        double visibilidad = sc.nextDouble();

        // Regla: viento < 20 Y pista >= 2500 Y visibilidad > 1000
        boolean despegueAutorizado = (viento < 20.0) && (pista >= 2500.0) && (visibilidad > 1000.0);
        System.out.printf("¿Despegue autorizado?: %b%n%n", despegueAutorizado);

        System.out.println("=== 3. CONTROL DE ACCESO A SALA EXCLUSIVA ===");
        System.out.print("Edad del visitante: ");
        int edad = sc.nextInt();
        System.out.print("¿Tiene invitación VIP? (true/false): ");
        boolean tieneVip = sc.nextBoolean();
        System.out.print("¿Acompañado por miembro premium? (true/false): ");
        boolean acompanadoPremium = sc.nextBoolean();

        // Regla: edad >= 18 Y (tieneVip O acompanadoPremium)
        boolean accesoPermitido = (edad >= 18) && (tieneVip || acompanadoPremium);
        System.out.printf("¿Acceso a la sala permitido?: %b%n", accesoPermitido);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 12: Valor Absoluto y Mayor de Números sin Sentencias de Control

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class ComparadorYValorAbsoluto {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce un número decimal (puede ser negativo): ");
        double x = sc.nextDouble();

        // 1. Cálculo con if-else
        double absIf;
        if (x < 0) {
            absIf = -x;
        } else {
            absIf = x;
        }

        // 2. Cálculo con operador ternario ?:
        double absTernario = (x < 0) ? -x : x;

        // 3. Cálculo con método de la biblioteca estándar
        double absMath = Math.abs(x);

        System.out.printf("Valor original: %.3f%n", x);
        System.out.printf("-> Valor absoluto (if-else):   %.3f%n", absIf);
        System.out.printf("-> Valor absoluto (ternario):  %.3f%n", absTernario);
        System.out.printf("-> Valor absoluto (Math.abs):  %.3f%n%n", absMath);

        System.out.print("Introduce el primer entero (a): ");
        int a = sc.nextInt();
        System.out.print("Introduce el segundo entero (b): ");
        int b = sc.nextInt();

        // Determinar el mayor sin if:
        int mayorMath = Math.max(a, b);
        String diagnostico = (a == b) ? "Ambos números son idénticos" : 
                             (a > b)  ? "El primero (a) es mayor que el segundo (b)" : 
                                        "El segundo (b) es mayor que el primero (a)";

        System.out.printf("Mayor determinado con Math.max: %d%n", mayorMath);
        System.out.printf("Diagnóstico con ternario:       %s%n", diagnostico);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 13: Resolución de la Ecuación de Primer Grado `ax + b = 0`

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class EcuacionPrimerGrado {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Resolución de la ecuación de primer grado: a·x + b = 0");
        System.out.print("Introduce el coeficiente 'a': ");
        double a = sc.nextDouble();

        System.out.print("Introduce el término independiente 'b': ");
        double b = sc.nextDouble();

        System.out.println("\n------------------------------------------------");
        if (a != 0.0) {
            // Caso 1: Solución única x = -b / a
            double x = -b / a;
            // Prevenimos el caso de -0.0 visual en coma flotante IEEE 754
            if (x == -0.0) {
                x = 0.0;
            }
            System.out.printf("Ecuación: %.2f·x + (%.2f) = 0%n", a, b);
            System.out.printf("-> Solución única: x = %.4f%n", x);
        } else if (b != 0.0) {
            // Caso 2: 0·x + b = 0 -> b = 0 (inconsistencia matemática)
            System.out.printf("Ecuación: 0·x + (%.2f) = 0%n", b);
            System.out.println("-> Sin solución: Inconsistencia matemática (la igualdad es falsa).");
        } else {
            // Caso 3: 0·x + 0 = 0 (identidad matemática)
            System.out.println("Ecuación: 0·x + 0 = 0");
            System.out.println("-> Infinitas soluciones: Cualquier valor real de x satisface la igualdad.");
        }
        System.out.println("------------------------------------------------");

        sc.close();
    }
}
```
