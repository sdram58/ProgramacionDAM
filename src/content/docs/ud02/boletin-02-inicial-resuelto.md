--- 
title: 📝 Boletín 02 Inicial (Resuelto) — Primeros Pasos con Java
description: Soluciones completas, verificadas y comentadas línea a línea en Java 21 LTS con IntelliJ IDEA para los 10 ejercicios del Boletín Inicial de la Unidad 2.
draft: true

---

A continuación se presentan las soluciones comentadas a los 10 ejercicios propuestos en el [Boletín Inicial](./boletin-02-inicial/). Cada programa ha sido verificado con Java 21 LTS en IntelliJ IDEA.

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

        System.out.println("--- CÁLCULO DEL RECTÁNGULO ---");
        System.out.print("Introduce la base (m): ");
        double base = sc.nextDouble();
        System.out.print("Introduce la altura (m): ");
        double altura = sc.nextDouble();

        if (base <= 0 || altura <= 0) {
            System.out.println("Error: Las dimensiones del rectángulo deben ser estrictamente positivas (> 0).");
            sc.close();
            return; // Abortamos la ejecución de forma limpia
        }

        double perimetroRect = 2 * (base + altura);
        double areaRect = base * altura;

        System.out.printf("Rectángulo -> Perímetro: %.2f m | Área: %.2f m²%n%n", perimetroRect, areaRect);

        System.out.println("--- CÁLCULO DEL CÍRCULO ---");
        System.out.print("Introduce el radio de la circunferencia (m): ");
        double radio = sc.nextDouble();

        if (radio <= 0) {
            System.out.println("Error: El radio debe ser estrictamente positivo (> 0).");
            sc.close();
            return;
        }

        double longitudCirc = 2 * Math.PI * radio;
        double areaCirc = Math.PI * Math.pow(radio, 2);

        System.out.printf("Círculo    -> Longitud: %.2f m | Área: %.2f m²%n", longitudCirc, areaCirc);
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

public class ConversorTemperatura {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        // 1. De Celsius a Fahrenheit
        System.out.print("Introduce la temperatura en grados Celsius (°C): ");
        double celsius = sc.nextDouble();
        // Usamos 9.0 / 5.0 para evitar que Java aplique división entera (9/5 = 1)
        double fahrenheit = (celsius * (9.0 / 5.0)) + 32.0;
        System.out.printf("%.2f °C equivalen a %.2f °F%n%n", celsius, fahrenheit);

        // 2. De Fahrenheit a Celsius
        System.out.print("Introduce la temperatura en grados Fahrenheit (°F): ");
        double tempF = sc.nextDouble();
        // Usamos 5.0 / 9.0 porque 5 / 9 daría 0 en división entera
        double tempC = (tempF - 32.0) * (5.0 / 9.0);
        System.out.printf("%.2f °F equivalen a %.2f °C%n", tempF, tempC);

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

        System.out.print("Introduce la nota de Exámenes teóricos (0-10): ");
        double teorico = sc.nextDouble();

        System.out.print("Introduce la nota de Prácticas en IntelliJ (0-10): ");
        double practicas = sc.nextDouble();

        System.out.print("Introduce la nota de Actitud y Asistencia (0-10): ");
        double actitud = sc.nextDouble();

        // Validación de rango defensiva
        if (teorico < 0 || teorico > 10 || practicas < 0 || practicas > 10 || actitud < 0 || actitud > 10) {
            System.out.println("Error: Todas las calificaciones deben estar comprendidas entre 0.0 y 10.0.");
        } else {
            // Ponderación: 30% teoría + 50% prácticas + 20% actitud
            double notaFinal = (teorico * 0.30) + (practicas * 0.50) + (actitud * 0.20);
            
            String calificacion;
            if (notaFinal < 5.0) {
                calificacion = "Suspenso";
            } else if (notaFinal < 7.0) {
                calificacion = "Aprobado";
            } else if (notaFinal < 9.0) {
                calificacion = "Notable";
            } else {
                calificacion = "Sobresaliente";
            }

            System.out.printf("Nota final ponderada: %.2f -> %s%n", notaFinal, calificacion);
        }

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

public class Facturacion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce el precio unitario del producto (€): ");
        double precioUnitario = sc.nextDouble();

        System.out.print("Introduce el número de unidades compradas: ");
        int unidades = sc.nextInt();

        if (precioUnitario <= 0 || unidades <= 0) {
            System.out.println("Error: El precio y las unidades deben ser mayores a 0.");
            sc.close();
            return;
        }

        double importeBruto = precioUnitario * unidades;
        double porcentajeDescuento = 0.0;

        if (importeBruto >= 300.0) {
            porcentajeDescuento = 0.12; // 12%
        } else if (importeBruto >= 100.0) {
            porcentajeDescuento = 0.05; // 5%
        }

        double descuentoEuros = importeBruto * porcentajeDescuento;
        double baseImponible = importeBruto - descuentoEuros;
        final double TIPO_IVA = 0.21; // Constante 21%
        double importeIva = baseImponible * TIPO_IVA;
        double totalPagar = baseImponible + importeIva;

        System.out.println("\n" + "-".repeat(45));
        System.out.println("               DESGLOSE DE FACTURA           ");
        System.out.println("-".repeat(45));
        System.out.printf("Importe bruto (%d uds × %.2f €):  %8.2f €%n", unidades, precioUnitario, importeBruto);
        System.out.printf("Descuento aplicado (%.0f%%):          -%8.2f €%n", porcentajeDescuento * 100, descuentoEuros);
        System.out.printf("Base imponible:                    %8.2f €%n", baseImponible);
        System.out.printf("IVA (21%%):                        +%8.2f €%n", importeIva);
        System.out.println("-".repeat(45));
        System.out.printf("TOTAL A PAGAR:                     %8.2f €%n", totalPagar);
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

        System.out.print("Introduce una cantidad total de segundos: ");
        int totalSegundos = sc.nextInt();

        if (totalSegundos < 0) {
            System.out.println("Error: La cantidad de segundos no puede ser negativa.");
        } else {
            // 1 hora = 3600 segundos
            int horas = totalSegundos / 3600;
            int segundosRestantes = totalSegundos % 3600;

            // 1 minuto = 60 segundos
            int minutos = segundosRestantes / 60;
            int segundosFinales = segundosRestantes % 60;

            System.out.printf("%d segundos equivalen a: %d horas, %d minutos y %d segundos.%n",
                              totalSegundos, horas, minutos, segundosFinales);
        }

        sc.close();
    }
}
```

---

### Solución al Ejercicio 7: Detector de Paridad y Múltiplos

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class DetectorNumero {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce un número entero: ");
        int n = sc.nextInt();

        // 1. Signo
        if (n > 0) {
            System.out.println("* Signo: El número es POSITIVO.");
        } else if (n < 0) {
            System.out.println("* Signo: El número es NEGATIVO.");
        } else {
            System.out.println("* Signo: El número es CERO (neutro).");
        }

        // 2. Paridad
        if (n % 2 == 0) {
            System.out.println("* Paridad: Es un número PAR.");
        } else {
            System.out.println("* Paridad: Es un número IMPAR.");
        }

        // 3. Múltiplos
        boolean esMult3 = (n % 3 == 0);
        boolean esMult5 = (n % 5 == 0);

        if (esMult3 && esMult5) {
            System.out.println("* Múltiplos: Es múltiplo de 3 y de 5 simultáneamente (múltiplo de 15).");
        } else if (esMult3) {
            System.out.println("* Múltiplos: Es múltiplo de 3 pero no de 5.");
        } else if (esMult5) {
            System.out.println("* Múltiplos: Es múltiplo de 5 pero no de 3.");
        } else {
            System.out.println("* Múltiplos: No es múltiplo ni de 3 ni de 5.");
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

public class DiasMesSwitch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número de mes (1 al 12): ");
        int mes = sc.nextInt();

        // Expresión switch moderna con flecha -> y casos agrupados
        int dias = switch (mes) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> 28;
            default -> -1; // Mes no válido
        };

        if (dias == -1) {
            System.out.println("Error: El mes debe estar comprendido entre 1 y 12.");
        } else {
            System.out.printf("El mes %d tiene %d días.%n", mes, dias);
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

public class LimpiezaBufferScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número de bastidor (entero): ");
        int bastidor = sc.nextInt();

        // ¡PASO CRUCIAL! Consumimos el salto de línea '
' pendiente en el buffer
        sc.nextLine();

        System.out.print("Introduce el nombre completo del titular: ");
        String titular = sc.nextLine(); // Ahora sí espera la entrada del usuario

        System.out.println("\n--- DATOS DEL VEHÍCULO REGISTRADO ---");
        System.out.println("Número de Bastidor: " + bastidor);
        System.out.println("Titular del Vehículo: " + titular);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 10: Simulador de Lanzamiento de Dados

```java
package es.iesperenxisa.primerospasos;

public class LanzadorDados {
    public static void main(String[] args) {
        // Dado clásico de 6 caras: rango [1, 6]
        int dado6 = (int)(Math.random() * 6) + 1;

        // Dado de rol de 20 caras (D20): rango [1, 20]
        int dado20 = (int)(Math.random() * 20) + 1;

        int suma = dado6 + dado20;

        System.out.println("=== LANZAMIENTO DE DADOS ===");
        System.out.println("Resultado Dado 6 caras (D6):   " + dado6);
        System.out.println("Resultado Dado 20 caras (D20): " + dado20);
        System.out.println("Suma total de la tirada:       " + suma);

        // Comprobación de eventos críticos en D20
        if (dado20 == 20) {
            System.out.println("🔥 ¡¡ÉXITO CRÍTICO!! Obtienes la máxima bonificación.");
        } else if (dado20 == 1) {
            System.out.println("💀 ¡¡PIFIA CRÍTICA!! Ocurre un fallo catastrófico.");
        }
    }
}
```\n