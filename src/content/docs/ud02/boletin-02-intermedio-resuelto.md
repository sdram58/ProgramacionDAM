--- 
title: 📝 Boletín 02 Intermedio (Resuelto) — Primeros Pasos con Java
description: Soluciones completas, verificadas y comentadas línea a línea en Java 21 LTS con IntelliJ IDEA para los 10 ejercicios del Boletín Intermedio de la Unidad 2.
draft: true

---

A continuación se presentan las soluciones comentadas a los 10 ejercicios del [Boletín Intermedio](./boletin-02-intermedio/).

---

### Solución al Ejercicio 1: Desglose Óptimo de Billetes y Monedas (Cajero Automático)

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class CajeroAutomatico {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el importe a retirar en euros (entero): ");
        int cantidad = sc.nextInt();

        if (cantidad <= 0) {
            System.out.println("Error: La cantidad a retirar debe ser mayor que 0 €.");
            sc.close();
            return;
        }

        System.out.println("\nDispensando " + cantidad + " € en el mínimo número de billetes y monedas:");
        int restante = cantidad;

        // Billetes de 50
        int b50 = restante / 50;
        restante %= 50;
        if (b50 > 0) System.out.printf("  * %d billete(s) de 50 €%n", b50);

        // Billetes de 20
        int b20 = restante / 20;
        restante %= 20;
        if (b20 > 0) System.out.printf("  * %d billete(s) de 20 €%n", b20);

        // Billetes de 10
        int b10 = restante / 10;
        restante %= 10;
        if (b10 > 0) System.out.printf("  * %d billete(s) de 10 €%n", b10);

        // Billetes de 5
        int b5 = restante / 5;
        restante %= 5;
        if (b5 > 0) System.out.printf("  * %d billete(s) de 5 €%n", b5);

        // Monedas de 2
        int m2 = restante / 2;
        restante %= 2;
        if (m2 > 0) System.out.printf("  * %d moneda(s) de 2 €%n", m2);

        // Monedas de 1
        int m1 = restante;
        if (m1 > 0) System.out.printf("  * %d moneda(s) de 1 €%n", m1);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 2: El Envasado de Huevos de la Granja Avícola

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class EnvasadoHuevos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("¿Cuántos huevos han puesto hoy las gallinas?: ");
        int totalHuevos = sc.nextInt();

        if (totalHuevos < 0) {
            System.out.println("Error: El número de huevos no puede ser negativo.");
            sc.close();
            return;
        }

        // 1 gruesa = 144 huevos
        int gruesas = totalHuevos / 144;
        int resto1 = totalHuevos % 144;

        // 1 docena = 12 huevos
        int docenas = resto1 / 12;
        int resto2 = resto1 % 12;

        // 1 media docena = 6 huevos
        int mediasDocenas = resto2 / 6;
        int sueltos = resto2 % 6;

        System.out.println("\n--- DISTRIBUCIÓN DE ENVASES ---");
        System.out.printf("Total recolectado: %d huevos%n", totalHuevos);
        System.out.printf("  - Envases gigantes (144 uds): %d%n", gruesas);
        System.out.printf("  - Envases de docena (12 uds): %d%n", docenas);
        System.out.printf("  - Envases de media docena (6 uds): %d%n", mediasDocenas);
        System.out.printf("  - Huevos sueltos sin envasar: %d%n", sueltos);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 3: Teorema de Pitágoras con Validación Dinámica

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class TeoremaPitagoras {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce la longitud del cateto A (m): ");
        double a = sc.nextDouble();

        System.out.print("Introduce la longitud del cateto B (m): ");
        double b = sc.nextDouble();

        if (a <= 0 || b <= 0) {
            System.out.println("Error: Los catetos de un triángulo deben tener dimensiones estrictamente positivas (> 0).");
            sc.close();
            return;
        }

        // Método 1: Math.sqrt y Math.pow
        double hipotenusa1 = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

        // Método 2: Math.hypot (más preciso numéricamente para valores extremos)
        double hipotenusa2 = Math.hypot(a, b);

        System.out.println("\n--- RESULTADOS DEL CÁLCULO ---");
        System.out.printf("Cateto A:        %.3f m%n", a);
        System.out.printf("Cateto B:        %.3f m%n", b);
        System.out.printf("Hipotenusa (1):  %.3f m (vía sqrt + pow)%n", hipotenusa1);
        System.out.printf("Hipotenusa (2):  %.3f m (vía Math.hypot)%n", hipotenusa2);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 4: Analizador y Parser de Nombres y Apellidos con `String`

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class AnalizadorNombre {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce tu nombre y dos apellidos (ej: Alan Mathison Turing): ");
        String linea = sc.nextLine().trim();

        // Localizamos el primer espacio y el último espacio
        int primerEspacio = linea.indexOf(' ');
        int ultimoEspacio = linea.lastIndexOf(' ');

        if (primerEspacio == -1 || primerEspacio == ultimoEspacio) {
            System.out.println("Error: Debes introducir un nombre y dos apellidos separados por espacios.");
            sc.close();
            return;
        }

        // Extracción de las 3 subcadenas
        String nombre = linea.substring(0, primerEspacio);
        String apellido1 = linea.substring(primerEspacio + 1, ultimoEspacio);
        String apellido2 = linea.substring(ultimoEspacio + 1);

        // Iniciales
        char iniNom = Character.toUpperCase(nombre.charAt(0));
        char iniAp1 = Character.toUpperCase(apellido1.charAt(0));
        char iniAp2 = Character.toUpperCase(apellido2.charAt(0));

        System.out.println("\n--- ANÁLISIS MORFOLÓGICO DE LA CADENA ---");
        System.out.printf("Nombre:            %-15s (%d letras)%n", nombre.toUpperCase(), nombre.length());
        System.out.printf("Primer Apellido:   %-15s (%d letras)%n", apellido1.toUpperCase(), apellido1.length());
        System.out.printf("Segundo Apellido:  %-15s (%d letras)%n", apellido2.toUpperCase(), apellido2.length());
        System.out.printf("Iniciales Oficiales: %c%c%c%n", iniNom, iniAp1, iniAp2);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 5: Conversor de Potencia de Motores (CV a kW)

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class ConversorPotencia {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("=== CONVERSOR DE POTENCIA MECÁNICA ===");
        System.out.println("1. Convertir de Caballos de Vapor (CV) a Kilovatios (kW)");
        System.out.println("2. Convertir de Kilovatios (kW) a Caballos de Vapor (CV)");
        System.out.print("Seleccione opción (1 o 2): ");
        int opcion = sc.nextInt();

        final double FACTOR_CV_A_KW = 0.73549875;

        switch (opcion) {
            case 1 -> {
                System.out.print("Introduce la potencia en CV: ");
                double cv = sc.nextDouble();
                if (cv <= 0) {
                    System.out.println("Error: La potencia debe ser mayor a 0.");
                } else {
                    double kw = cv * FACTOR_CV_A_KW;
                    System.out.printf("%.2f CV equivalen a %.2f kW de potencia.%n", cv, kw);
                }
            }
            case 2 -> {
                System.out.print("Introduce la potencia en kW: ");
                double kw = sc.nextDouble();
                if (kw <= 0) {
                    System.out.println("Error: La potencia debe ser mayor a 0.");
                } else {
                    double cv = kw / FACTOR_CV_A_KW;
                    System.out.printf("%.2f kW equivalen a %.2f CV de potencia.%n", kw, cv);
                }
            }
            default -> System.out.println("Opción no válida.");
        }

        sc.close();
    }
}
```

---

### Solución al Ejercicio 6: Tarificación Eléctrica con Tipos Enumerados (`enum`)

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.util.Locale;

public class FacturaElectrica {
    // Definición de la enumeración de tramos horarios
    public enum TramoHorario {
        VALLE, LLANO, PUNTA
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce el consumo eléctrico en kWh: ");
        double consumo = sc.nextDouble();

        System.out.print("Introduce el tramo horario (VALLE / LLANO / PUNTA): ");
        String tramoTexto = sc.next().toUpperCase();

        TramoHorario tramo;
        try {
            tramo = TramoHorario.valueOf(tramoTexto);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Tramo horario no reconocido. Opciones: VALLE, LLANO, PUNTA.");
            sc.close();
            return;
        }

        double precioKwh = switch (tramo) {
            case VALLE -> 0.11;
            case LLANO -> 0.18;
            case PUNTA -> 0.28;
        };

        double costeEnergia = consumo * precioKwh;
        double impuestoElectrico = costeEnergia * 0.0511; // 5.11%
        double baseImponible = costeEnergia + impuestoElectrico;
        double iva = baseImponible * 0.21; // 21%
        double totalFactura = baseImponible + iva;

        System.out.println("\n" + "=".repeat(45));
        System.out.println("           FACTURA ELÉCTRICA OFICIAL         ");
        System.out.println("=".repeat(45));
        System.out.printf("Tramo seleccionado:        %s%n", tramo);
        System.out.printf("Consumo registrado:        %.2f kWh a %.2f €/kWh%n", consumo, precioKwh);
        System.out.printf("Coste energía activa:      %8.2f €%n", costeEnergia);
        System.out.printf("Impuesto electricidad:     %8.2f €%n", impuestoElectrico);
        System.out.printf("Subtotal base imponible:   %8.2f €%n", baseImponible);
        System.out.printf("IVA (21%%):                 %8.2f €%n", iva);
        System.out.println("=".repeat(45));
        System.out.printf("TOTAL A PAGAR:             %8.2f €%n", totalFactura);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 7: Clasificador de Caracteres Unicode y Métodos de `Character`

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class ClasificadorCaracteres {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce un único carácter o tecla y pulsa Enter: ");
        String entrada = sc.next();
        char c = entrada.charAt(0);

        System.out.println("\n--- INFORME DEL CARÁCTER '" + c + "' ---");
        System.out.printf("Código Unicode / ASCII numérico: %d (Hexadecimal: \\u%04X)%n", (int)c, (int)c);

        if (Character.isDigit(c)) {
            System.out.printf("Categoría: DÍGITO NUMÉRICO (Valor entero: %d)%n", Character.getNumericValue(c));
        } else if (Character.isLetter(c)) {
            String caja = Character.isUpperCase(c) ? "MAYÚSCULA" : "MINÚSCULA";
            char lower = Character.toLowerCase(c);
            boolean esVocal = (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u' ||
                               lower == 'á' || lower == 'é' || lower == 'í' || lower == 'ó' || lower == 'ú');

            if (esVocal) {
                System.out.printf("Categoría: LETRA VOCAL (%s)%n", caja);
            } else {
                System.out.printf("Categoría: LETRA CONSONANTE (%s)%n", caja);
            }
        } else if (Character.isWhitespace(c)) {
            System.out.println("Categoría: ESPACIO EN BLANCO O SEPARADOR");
        } else {
            System.out.println("Categoría: SÍMBOLO O SIGNO DE PUNTUACIÓN");
        }

        sc.close();
    }
}
```

---

### Solución al Ejercicio 8: Evaluación de Expediente con Expresión `switch` y `yield`

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;

public class EvaluacionExpediente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce la calificación entera del alumno (1-10): ");
        int nota = sc.nextInt();

        // Expresión switch moderna empleando 'yield' en bloques multilínea
        String informe = switch (nota) {
            case 1, 2, 3, 4 -> {
                System.out.println("-> Alerta pedagógica: Se activará el plan de refuerzo.");
                yield "Insuficiente - Debe presentarse a la convocatoria extraordinaria";
            }
            case 5, 6 -> {
                yield "Suficiente / Bien - Supera los mínimos exigidos en el módulo";
            }
            case 7, 8 -> {
                yield "Notable - Demuestra un sólido dominio técnico de Java";
            }
            case 9, 10 -> {
                System.out.println("-> Alerta de excelencia: Expediente destacado.");
                yield "Sobresaliente - Candidato a Matrícula de Honor";
            }
            default -> {
                yield "Calificación no válida: La nota debe ser un número entero entre 1 y 10";
            }
        };

        System.out.println("\nResultado del dictamen:");
        System.out.println(informe);

        sc.close();
    }
}
```

---

### Solución al Ejercicio 9: Calculadora de Edad Exacta con `java.time`

```java
package es.iesperenxisa.primerospasos;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class CalculadoraEdadExacta {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce tu año de nacimiento (ej: 2004): ");
        int anio = sc.nextInt();

        System.out.print("Introduce tu mes de nacimiento (1-12): ");
        int mes = sc.nextInt();

        System.out.print("Introduce tu día de nacimiento (1-31): ");
        int dia = sc.nextInt();

        LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
        LocalDate fechaHoy = LocalDate.now();

        if (fechaNacimiento.isAfter(fechaHoy)) {
            System.out.println("Error: La fecha de nacimiento no puede estar en el futuro.");
            sc.close();
            return;
        }

        Period periodo = Period.between(fechaNacimiento, fechaHoy);

        System.out.println("\n--- CÓMPUTO EXACTO DE TIEMPO TRANSCURRIDO ---");
        System.out.printf("Edad cronológica: %d años, %d meses y %d días.%n",
                          periodo.getYears(), periodo.getMonths(), periodo.getDays());

        // Comprobamos si hoy es su cumpleaños
        if (fechaNacimiento.getMonthValue() == fechaHoy.getMonthValue() && 
            fechaNacimiento.getDayOfMonth() == fechaHoy.getDayOfMonth()) {
            System.out.println("🎉 ¡¡FELICIDADES!! Hoy es el día de tu cumpleaños. ¡Que pases un gran día!");
        }

        sc.close();
    }
}
```

---

### Solución al Ejercicio 10: Laboratorio de Desbordamiento Numérico (*Overflow*) y Casting

```java
package es.iesperenxisa.primerospasos;

public class LaboratorioOverflow {
    public static void main(String[] args) {
        System.out.println("=== EXPERIMENTO 1: DESBORDAMIENTO DE BYTE ===");
        int original = 130;
        byte convertido = (byte) original;

        System.out.println("Valor entero original (32 bits): " + original);
        System.out.println("Valor forzado a byte (8 bits):   " + convertido);
        System.out.println("EXPLICACIÓN: El tipo byte solo admite de -128 a +127.");
        System.out.println("Al sobrepasar 127 en 3 unidades, el bit de signo de 8 bits");
        System.out.println("se activa produciendo el valor en complemento a dos: -126.\n");

        System.out.println("=== EXPERIMENTO 2: DESBORDAMIENTO DE MULTIPLICACIÓN INT ===");
        int unMillon = 1_000_000;
        
        // Multiplicación en tipo int (32 bits)
        int calculoErroneo = unMillon * unMillon; 
        System.out.println("1_000_000 * 1_000_000 en int:   " + calculoErroneo + " (¡ERRÓNEO!)");

        // Multiplicación corregida en tipo long (64 bits)
        // Forzamos al menos uno de los operandos a long con el sufijo 'L'
        long calculoCorrecto = (long) unMillon * unMillon;
        System.out.println("1_000_000L * 1_000_000 en long:  " + calculoCorrecto + " (¡UN BILLÓN EXACTO!)");
    }
}
```\n