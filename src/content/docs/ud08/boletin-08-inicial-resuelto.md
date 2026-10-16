---
title: "Boletín 8 — Nivel Inicial (Resuelto)"
description: "Soluciones completas y comentadas en Java 25 LTS del Boletín Inicial de la Unidad 8: inspección de rutas con File, creación y borrado recursivo, lectura y escritura con Scanner, BufferedReader y PrintWriter, copia binaria con búfer y persistencia tipada con DataStreams."
slug: ud08/boletin-08-inicial-resuelto
draft: true
prev: false
next: false
---

A continuación se presentan las soluciones completas en **Java 25 LTS** para los 12 ejercicios del Boletín Inicial de la Unidad 8. Cada solución aplica el patrón **`try-with-resources`** y las mejores prácticas de codificación en Java moderno.

---

## Ejercicio 1: Inspector de rutas del sistema (`muestraInfoRuta` v1)

```java
package ud08.inicial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ejercicio01InspectorRutas {

    public static void muestraInfoRuta(File ruta) throws FileNotFoundException {
        if (!ruta.exists()) {
            throw new FileNotFoundException("La ruta especificada no existe: " + ruta.getAbsolutePath());
        }

        if (ruta.isFile()) {
            System.out.println("[A] " + ruta.getName());
        } else if (ruta.isDirectory()) {
            File[] elementos = ruta.listFiles();
            if (elementos != null) {
                // Primero mostramos los directorios
                for (File e : elementos) {
                    if (e.isDirectory()) {
                        System.out.println("[*] " + e.getName());
                    }
                }
                // Después mostramos los archivos
                for (File e : elementos) {
                    if (e.isFile()) {
                        System.out.println("[A] " + e.getName());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nIntroduce una ruta (Intro vacío para terminar): ");
                String entrada = sc.nextLine().trim();
                if (entrada.isEmpty()) {
                    System.out.println("Fin del programa.");
                    break;
                }

                try {
                    muestraInfoRuta(new File(entrada));
                } catch (FileNotFoundException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
```

---

## Ejercicio 2: Inspector detallado y ordenado (`muestraInfoRuta` v2)

```java
package ud08.inicial;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class Ejercicio02InspectorDetallado {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void muestraInfoRuta(File ruta, boolean info) throws FileNotFoundException {
        if (!ruta.exists()) {
            throw new FileNotFoundException("Ruta no encontrada: " + ruta.getAbsolutePath());
        }

        if (ruta.isFile()) {
            imprimirElemento(ruta, info);
        } else if (ruta.isDirectory()) {
            File[] elementos = ruta.listFiles();
            if (elementos != null) {
                // Separamos y ordenamos alfabéticamente
                File[] dirs = Arrays.stream(elementos)
                        .filter(File::isDirectory)
                        .sorted(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER))
                        .toArray(File[]::new);

                File[] arx = Arrays.stream(elementos)
                        .filter(File::isFile)
                        .sorted(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER))
                        .toArray(File[]::new);

                for (File d : dirs) imprimirElemento(d, info);
                for (File a : arx) imprimirElemento(a, info);
            }
        }
    }

    private static void imprimirElemento(File f, boolean info) {
        String prefijo = f.isDirectory() ? "[*] " : "[A] ";
        if (!info) {
            System.out.println(prefijo + f.getName());
        } else {
            LocalDateTime fechaMod = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault());
            long bytes = f.isFile() ? f.length() : 0L;
            System.out.printf("%s%-25s | %10d bytes | %s%n",
                    prefijo, f.getName(), bytes, fechaMod.format(FORMATO_FECHA));
        }
    }

    public static void main(String[] args) {
        try {
            File rutaPrueba = new File(".");
            System.out.println("=== Inspección detallada del directorio actual (info=true) ===");
            muestraInfoRuta(rutaPrueba, true);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
```

---

## Ejercicio 3: Reorganización masiva y eliminación de extensiones

```java
package ud08.inicial;

import java.io.File;

public class Ejercicio03ReorganizarYQuitarExtensiones {

    public static void renombrarCarpeta(File origen, File destino) {
        if (origen.exists() && origen.isDirectory()) {
            boolean exito = origen.renameTo(destino);
            System.out.println("Renombrada carpeta '" + origen.getName() + "' a '" + destino.getName() + "': " + exito);
        }
    }

    public static void quitarExtensionesEnCarpeta(File carpeta) {
        if (!carpeta.exists() || !carpeta.isDirectory()) return;

        File[] archivos = carpeta.listFiles(File::isFile);
        if (archivos == null) return;

        for (File a : archivos) {
            String nombreOriginal = a.getName();
            int posPunto = nombreOriginal.lastIndexOf('.');
            if (posPunto > 0) {
                String nombreSinExt = nombreOriginal.substring(0, posPunto);
                File nuevoArchivo = new File(carpeta, nombreSinExt);
                boolean exito = a.renameTo(nuevoArchivo);
                System.out.println("   " + nombreOriginal + " -> " + nombreSinExt + " (" + (exito ? "OK" : "FALLO") + ")");
            }
        }
    }

    public static void main(String[] args) {
        File docDir = new File("Documentos");
        File fotoDir = new File("Fotografias");
        File librosDir = new File("Libros");

        // 1. Renombramos las carpetas
        renombrarCarpeta(docDir, new File("DOCS"));
        renombrarCarpeta(fotoDir, new File("FOTOS"));
        renombrarCarpeta(librosDir, new File("LECTURAS"));

        // 2. Quitamos extensiones
        System.out.println("\nQuitando extensiones en FOTOS:");
        quitarExtensionesEnCarpeta(new File("FOTOS"));

        System.out.println("\nQuitando extensiones en LECTURAS:");
        quitarExtensionesEnCarpeta(new File("LECTURAS"));
    }
}
```

---

## Ejercicio 4: Creación de estructura y carpetas del alfabeto

```java
package ud08.inicial;

import java.io.File;

public class Ejercicio04CarpetasAlfabeto {

    public static void main(String[] args) {
        File almacen = new File("Almacen");
        File misCosas = new File(almacen, "Mis Cosas");
        File alfabeto = new File(almacen, "Alfabeto");

        // 1. Creación de directorios base
        misCosas.mkdirs();
        alfabeto.mkdirs();
        System.out.println("Directorios base creados en: " + almacen.getAbsolutePath());

        // 2. Mover carpetas FOTOS y LECTURAS dentro de Mis Cosas si existen
        File fotos = new File("FOTOS");
        File lecturas = new File("LECTURAS");

        if (fotos.exists()) fotos.renameTo(new File(misCosas, "FOTOS"));
        if (lecturas.exists()) lecturas.renameTo(new File(misCosas, "LECTURAS"));

        // 3. Crear 26 carpetas A-Z usando sus códigos ASCII (65 a 90)
        int creadas = 0;
        for (int codigo = 65; codigo <= 90; codigo++) {
            char letra = (char) codigo;
            File subCarpetaLetra = new File(alfabeto, String.valueOf(letra));
            if (subCarpetaLetra.mkdir()) {
                creadas++;
            }
        }

        System.out.println("Se han creado " + creadas + " carpetas de letras dentro de " + alfabeto.getName());
    }
}
```

---

## Ejercicio 5: Borrado recursivo completo (`borraTodo`)

```java
package ud08.inicial;

import java.io.File;
import java.io.FileNotFoundException;

public class Ejercicio05BorradoRecursivo {

    public static boolean borraTodo(File f) throws FileNotFoundException {
        if (!f.exists()) {
            throw new FileNotFoundException("No existe el archivo o directorio a borrar: " + f.getAbsolutePath());
        }

        if (f.isDirectory()) {
            File[] contenido = f.listFiles();
            if (contenido != null) {
                for (File hijo : contenido) {
                    borraTodo(hijo); // Borramos recursivamente los elementos hijos
                }
            }
        }
        return f.delete(); // Borramos el fichero o la carpeta ahora que está vacía
    }

    public static void main(String[] args) {
        File objetivo = new File("Almacen");
        try {
            boolean exito = borraTodo(objetivo);
            System.out.println("¿Se ha eliminado completamente '" + objetivo.getName() + "'?: " + exito);
        } catch (FileNotFoundException e) {
            System.err.println("Aviso: " + e.getMessage());
        }
    }
}
```

---

## Ejercicio 6: Estadísticas de un fichero de números enteros

```java
package ud08.inicial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio06EstadisticasNumeros {

    public static void main(String[] args) {
        File archivo = new File("numeros.txt");

        int cantidad = 0;
        int suma = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                int n = sc.nextInt();
                cantidad++;
                suma += n;
                if (n < min) min = n;
                if (n > max) max = n;
            }

            if (cantidad == 0) {
                System.out.println("El archivo 'numeros.txt' no contiene ningún número.");
            } else {
                double media = (double) suma / cantidad;
                System.out.println("=== ESTADÍSTICAS DE 'numeros.txt' ===");
                System.out.println("Total de números leídos: " + cantidad);
                System.out.println("Valor mínimo: " + min);
                System.out.println("Valor máximo: " + max);
                System.out.println("Suma total: " + suma);
                System.out.printf("Media aritmética: %.2f%n", media);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo 'numeros.txt' no existe.");
        } catch (InputMismatchException e) {
            System.err.println("Error de formato: El archivo contiene datos no numéricos.");
        }
    }
}
```

---

## Ejercicio 7: Procesamiento de notas de alumnos con longitud variable

```java
package ud08.inicial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ejercicio07NotasAlumnos {

    public record AlumnoNota(String nombreCompleto, double notaMedia) {}

    public static void main(String[] args) {
        File fichero = new File("alumnos_notas.txt");
        List<AlumnoNota> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichero, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\s+");
                if (partes.length >= 3) {
                    String nombre = partes[0] + " " + partes[1];
                    int suma = 0;
                    int numNotas = partes.length - 2;

                    for (int i = 2; i < partes.length; i++) {
                        suma += Integer.parseInt(partes[i]);
                    }
                    double media = (double) suma / numNotas;
                    lista.add(new AlumnoNota(nombre, media));
                }
            }

            // Ordenamos de mayor a menor nota media
            lista.sort(Comparator.comparingDouble(AlumnoNota::notaMedia).reversed());

            System.out.println("=== CALIFICACIONES GLOBALES ORDENADAS ===");
            for (AlumnoNota a : lista) {
                System.out.printf("[%.2f] %s%n", a.notaMedia(), a.nombreCompleto());
            }

        } catch (IOException e) {
            System.err.println("Error procesando fichero de notas: " + e.getMessage());
        }
    }
}
```

---

## Ejercicio 8: Ordenación alfabética de ficheros de texto

```java
package ud08.inicial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ejercicio08OrdenarFicheroTexto {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Nombre de archivo de entrada: ");
            String entrada = sc.nextLine().trim();
            System.out.print("Nombre de archivo de salida: ");
            String salida = sc.nextLine().trim();

            List<String> lineas = new ArrayList<>();

            // 1. Lectura
            try (BufferedReader br = new BufferedReader(new FileReader(entrada, StandardCharsets.UTF_8))) {
                String l;
                while ((l = br.readLine()) != null) {
                    lineas.add(l);
                }
            }

            // 2. Ordenación
            Collections.sort(lineas, String.CASE_INSENSITIVE_ORDER);

            // 3. Escritura
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter(salida, StandardCharsets.UTF_8)))) {
                for (String linea : lineas) {
                    pw.println(linea);
                }
            }

            System.out.println("Se han ordenado y guardado con éxito " + lineas.size() + " líneas en " + salida);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
```

---

## Ejercicio 9: Generador aleatorio de identidades

```java
package ud08.inicial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio09GeneradorIdentidades {

    private static List<String> leerLineas(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta, StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (!l.trim().isEmpty()) lineas.add(l.trim());
            }
        }
        return lineas;
    }

    public static void main(String[] args) {
        try {
            List<String> nombres = leerLineas("usa_nombres.txt");
            List<String> apellidos = leerLineas("usa_apellidos.txt");

            if (nombres.isEmpty() || apellidos.isEmpty()) {
                System.err.println("Los archivos de nombres o apellidos están vacíos.");
                return;
            }

            int cantidad = 25;
            String archivoSalida = "identidades_generadas.txt";

            try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter(archivoSalida, StandardCharsets.UTF_8)))) {
                
                for (int i = 1; i <= cantidad; i++) {
                    String nom = nombres.get(ThreadLocalRandom.current().nextInt(nombres.size()));
                    String ape = apellidos.get(ThreadLocalRandom.current().nextInt(apellidos.size()));
                    pw.printf("%03d: %s %s%n", i, nom, ape);
                }
            }

            System.out.println("Se generaron " + cantidad + " identidades en: " + archivoSalida);

        } catch (IOException e) {
            System.err.println("Error procesando identidades: " + e.getMessage());
        }
    }
}
```

---

## Ejercicio 10: Distribución de diccionario alfabético

```java
package ud08.inicial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Ejercicio10DiccionarioAlfabetico {

    public static void main(String[] args) {
        File dirDiccionario = new File("Diccionario");
        dirDiccionario.mkdir();

        Map<Character, PrintWriter> escritores = new HashMap<>();

        try {
            // Abrimos un PrintWriter para cada letra A-Z
            for (char c = 'A'; c <= 'Z'; c++) {
                File archivoLetra = new File(dirDiccionario, c + ".txt");
                PrintWriter pw = new PrintWriter(new BufferedWriter(
                        new FileWriter(archivoLetra, StandardCharsets.UTF_8)));
                escritores.put(c, pw);
            }

            // Leemos el archivo diccionario.txt y distribuimos
            try (BufferedReader br = new BufferedReader(new FileReader("diccionario.txt", StandardCharsets.UTF_8))) {
                String palabra;
                int totalPalabras = 0;
                while ((palabra = br.readLine()) != null) {
                    palabra = palabra.trim();
                    if (!palabra.isEmpty()) {
                        char inicial = Character.toUpperCase(palabra.charAt(0));
                        PrintWriter pw = escritores.get(inicial);
                        if (pw != null) {
                            pw.println(palabra);
                            totalPalabras++;
                        }
                    }
                }
                System.out.println("Distribuidas " + totalPalabras + " palabras en la carpeta 'Diccionario/'.");
            }

        } catch (IOException e) {
            System.err.println("Error procesando diccionario: " + e.getMessage());
        } finally {
            // Cerramos todos los escritores abiertos
            for (PrintWriter pw : escritores.values()) {
                pw.close();
            }
        }
    }
}
```

---

## Ejercicio 11: Benchmark de copia binaria (Byte a byte vs. Búfer)

```java
package ud08.inicial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio11BenchmarkCopiaBinaria {

    public static long copiarByteAByte(File origen, File destino) throws IOException {
        long inicio = System.currentTimeMillis();
        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        }
        return System.currentTimeMillis() - inicio;
    }

    public static long copiarConBuffer(File origen, File destino) throws IOException {
        long inicio = System.currentTimeMillis();
        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[4096]; // Búfer de 4 KB
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }
        }
        return System.currentTimeMillis() - inicio;
    }

    public static void main(String[] args) {
        File origen = new File("muestra_grande.bin");
        File copia1 = new File("copia_lenta.bin");
        File copia2 = new File("copia_rapida.bin");

        try {
            // Si no existe, creamos un archivo de prueba de 2 MB
            if (!origen.exists()) {
                try (FileOutputStream fos = new FileOutputStream(origen)) {
                    byte[] bloque = new byte[1024];
                    for (int i = 0; i < 2048; i++) fos.write(bloque);
                }
            }

            System.out.println("Iniciando copia byte a byte...");
            long tLenta = copiarByteAByte(origen, copia1);
            System.out.println("Copia byte a byte: " + tLenta + " ms.");

            System.out.println("Iniciando copia con búfer de 4 KB...");
            long tRapida = copiarConBuffer(origen, copia2);
            System.out.println("Copia con búfer: " + tRapida + " ms.");

            if (tRapida > 0) {
                System.out.printf("La copia con búfer fue %.1f veces más rápida.%n", (double) tLenta / tRapida);
            }

        } catch (IOException e) {
            System.err.println("Error en benchmark: " + e.getMessage());
        } finally {
            copia1.delete();
            copia2.delete();
            origen.delete();
        }
    }
}
```

---

## Ejercicio 12: Registro binario de mediciones climáticas (`DataStream`)

```java
package ud08.inicial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio12MedicionesDataStream {

    public record Medicion(int idEstacion, String provincia, double temperatura, double humedad, boolean alertaViento) {}

    public static void guardarMediciones(File f, List<Medicion> lista) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            for (Medicion m : lista) {
                dos.writeInt(m.idEstacion());
                dos.writeUTF(m.provincia());
                dos.writeDouble(m.temperatura());
                dos.writeDouble(m.humedad());
                dos.writeBoolean(m.alertaViento());
            }
        }
    }

    public static List<Medicion> cargarMediciones(File f) throws IOException {
        List<Medicion> recuperadas = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(f)))) {
            while (true) {
                try {
                    int id = dis.readInt();
                    String provincia = dis.readUTF();
                    double temp = dis.readDouble();
                    double hum = dis.readDouble();
                    boolean alerta = dis.readBoolean();
                    recuperadas.add(new Medicion(id, provincia, temp, hum, alerta));
                } catch (EOFException e) {
                    break; // Fin de fichero alcanzado de forma limpia
                }
            }
        }
        return recuperadas;
    }

    public static void main(String[] args) {
        File fichero = new File("estacion.dat");

        List<Medicion> mediciones = List.of(
                new Medicion(1, "Valencia", 24.5, 68.0, false),
                new Medicion(2, "Alicante", 26.2, 55.4, true),
                new Medicion(3, "Castellón", 21.0, 72.1, false)
        );

        try {
            guardarMediciones(fichero, mediciones);
            System.out.println("Mediciones guardadas en formato binario.");

            List<Medicion> leidas = cargarMediciones(fichero);
            System.out.println("Mediciones leídas del fichero:");
            for (Medicion m : leidas) {
                System.out.printf("   [Estación %d - %s] Temp: %.1f C | Hum: %.1f %% | Alerta Viento: %s%n",
                        m.idEstacion(), m.provincia(), m.temperatura(), m.humedad(), m.alertaViento());
            }
        } catch (IOException e) {
            System.err.println("Error con DataStreams: " + e.getMessage());
        } finally {
            fichero.delete();
        }
    }
}
```
