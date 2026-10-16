---
title: "Boletín 8 — Nivel Intermedio (Resuelto)"
description: "Soluciones completas y comentadas en Java 25 LTS del Boletín Intermedio de la Unidad 8: serialización, MiObjectOutputStream, RandomAccessFile con borrado lógico y compactación, búsqueda en Pi sin librerías, parsers XML DOM y SAX, compresión ZIP y utilidades con Java NIO.2."
slug: ud08/boletin-08-intermedio-resuelto
draft: true
prev: false
next: false
---

A continuación se presentan las soluciones completas en **Java 25 LTS** para los 12 ejercicios del Boletín Intermedio de la Unidad 8.

---

## Ejercicio 1: Persistencia de objetos y serialización de colecciones

```java
package ud08.intermedio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio01PersistenciaMascotas {

    public static class Mascota implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private final String chip;
        private final String nombre;
        private final String especie;
        private final int edad;
        private final double pesoKg;

        public Mascota(String chip, String nombre, String especie, int edad, double pesoKg) {
            this.chip = chip;
            this.nombre = nombre;
            this.especie = especie;
            this.edad = edad;
            this.pesoKg = pesoKg;
        }

        @Override
        public String toString() {
            return String.format("Mascota[chip='%s', nombre='%s', especie='%s', edad=%d, peso=%.1f kg]",
                    chip, nombre, especie, edad, pesoKg);
        }
    }

    public static class ClinicaVeterinaria {
        private List<Mascota> censo = new ArrayList<>();

        public void agregarMascota(Mascota m) {
            censo.add(m);
        }

        public List<Mascota> getCenso() {
            return censo;
        }

        public void guardarCenso(File archivo) throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(archivo)))) {
                oos.writeObject(censo);
            }
        }

        @SuppressWarnings("unchecked")
        public void cargarCenso(File archivo) throws IOException, ClassNotFoundException {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(archivo)))) {
                censo = (List<Mascota>) ois.readObject();
            }
        }
    }

    public static void main(String[] args) {
        ClinicaVeterinaria clinica = new ClinicaVeterinaria();
        clinica.agregarMascota(new Mascota("CHP001", "Toby", "Perro", 4, 12.5));
        clinica.agregarMascota(new Mascota("CHP002", "Misi", "Gato", 2, 4.2));
        clinica.agregarMascota(new Mascota("CHP003", "Luna", "Perro", 7, 25.0));
        clinica.agregarMascota(new Mascota("CHP004", "Simba", "Gato", 1, 3.8));

        File archivo = new File("censo_mascotas.obj");

        try {
            clinica.guardarCenso(archivo);
            System.out.println("Censo guardado (" + clinica.getCenso().size() + " mascotas).");

            // Limpiamos la memoria para asegurar que la recuperación es real
            ClinicaVeterinaria clinicaRecuperada = new ClinicaVeterinaria();
            clinicaRecuperada.cargarCenso(archivo);

            System.out.println("Censo recuperado con éxito desde el archivo:");
            for (Mascota m : clinicaRecuperada.getCenso()) {
                System.out.println("   - " + m);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en persistencia: " + e.getMessage());
        } finally {
            archivo.delete();
        }
    }
}
```

---

## Ejercicio 2: Control de campos sensibles con `transient` y versionado

```java
package ud08.intermedio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Ejercicio02CamposTransient {

    public static class CuentaUsuario implements Serializable {
        @Serial
        private static final long serialVersionUID = 100L;

        private final String username;
        private final String email;
        private final transient String passwordHash; // Omitido en serialización
        private final transient String tokenSesion;   // Omitido en serialización
        private final double saldoMonedero;

        public CuentaUsuario(String username, String email, String passwordHash, String tokenSesion, double saldo) {
            this.username = username;
            this.email = email;
            this.passwordHash = passwordHash;
            this.tokenSesion = tokenSesion;
            this.saldoMonedero = saldo;
        }

        @Override
        public String toString() {
            return String.format("Usuario: %s (%s) | PassHash: %s | Token: %s | Saldo: %.2f EUR",
                    username, email, passwordHash, tokenSesion, saldoMonedero);
        }
    }

    public static void main(String[] args) {
        File destino = new File("usuarios_seguros.obj");

        List<CuentaUsuario> usuarios = List.of(
                new CuentaUsuario("juan99", "juan@mail.com", "hashSecret_123", "TOKEN_ABC_1", 150.0),
                new CuentaUsuario("marta_dev", "marta@mail.com", "hashSecret_456", "TOKEN_ABC_2", 340.50)
        );

        System.out.println("=== ANTES DE SERIALIZAR (EN MEMORIA) ===");
        usuarios.forEach(u -> System.out.println("   " + u));

        // 1. Serializamos a disco
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(destino)))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Deserializamos de disco
        System.out.println("\n=== DESPUÉS DE DESERIALIZAR (DESDE DISCO) ===");
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(destino)))) {
            
            @SuppressWarnings("unchecked")
            List<CuentaUsuario> leidos = (List<CuentaUsuario>) ois.readObject();
            
            leidos.forEach(u -> System.out.println("   " + u));
            System.out.println("\nNota: passwordHash y tokenSesion son 'null' porque fueron marcados como 'transient'.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            destino.delete();
        }
    }
}
```

---

## Ejercicio 3: El problema de la cabecera repetida en `append` y `MiObjectOutputStream`

```java
package ud08.intermedio;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;

public class Ejercicio03AppendObjectStream {

    public record Producto(int id, String nombre, double precio) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
    }

    /**
     * Subclase personalizada de ObjectOutputStream que evita escribir la cabecera
     * en ficheros que ya contienen datos previos.
     */
    public static class MiObjectOutputStream extends ObjectOutputStream {
        public MiObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // No escribimos la cabecera mágica (0xaced 0x0005) para no corromper el stream
            reset();
        }
    }

    public static ObjectOutputStream abrirParaEscritura(File f) throws IOException {
        if (f.exists() && f.length() > 0) {
            return new MiObjectOutputStream(new FileOutputStream(f, true));
        } else {
            return new ObjectOutputStream(new FileOutputStream(f));
        }
    }

    public static void main(String[] args) {
        File archivo = new File("productos_append.obj");

        try {
            // Fase 1: Creación y escritura de los primeros 2 productos
            try (ObjectOutputStream oos1 = abrirParaEscritura(archivo)) {
                oos1.writeObject(new Producto(1, "Teclado", 45.0));
                oos1.writeObject(new Producto(2, "Ratón", 25.0));
            }
            System.out.println("Fase 1 completada: 2 productos guardados.");

            // Fase 2: Modo append con MiObjectOutputStream añadiendo el 3er producto
            try (ObjectOutputStream oos2 = abrirParaEscritura(archivo)) {
                oos2.writeObject(new Producto(3, "Monitor", 180.0));
            }
            System.out.println("Fase 2 completada: 1 producto añadido en modo append.");

            // Fase 3: Lectura secuencial de todos los objetos
            System.out.println("\nLeyendo todos los productos del archivo con ObjectInputStream:");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                while (true) {
                    try {
                        Producto p = (Producto) ois.readObject();
                        System.out.println("   - " + p);
                    } catch (EOFException e) {
                        break; // Fin del archivo
                    }
                }
            }
            System.out.println("¡Lectura finalizada sin StreamCorruptedException!");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            archivo.delete();
        }
    }
}
```

---

## Ejercicio 4: Fichero de acceso aleatorio (`RandomAccessFile`) — CRUD de registros fijos

```java
package ud08.intermedio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio04AlmacenAleatorioCRUD {

    // Tamaño total = 4 + 60 + 8 + 4 + 1 = 77 bytes
    public static final int TAMA_REGISTRO = 77;
    public static final int LONG_DESC = 30;

    public static class GestorAlmacenAleatorio implements AutoCloseable {
        private final RandomAccessFile raf;

        public GestorAlmacenAleatorio(File f) throws IOException {
            this.raf = new RandomAccessFile(f, "rw");
        }

        public void insertarArticulo(int numReg, int id, String descripcion, double precio, int stock) throws IOException {
            raf.seek((long) numReg * TAMA_REGISTRO);
            raf.writeInt(id);

            // Ajustamos descripción a exactamente 30 caracteres
            StringBuilder sb = new StringBuilder(descripcion);
            sb.setLength(LONG_DESC);
            for (int i = 0; i < LONG_DESC; i++) {
                if (sb.charAt(i) == '\0') sb.setCharAt(i, ' ');
            }
            raf.writeChars(sb.toString());

            raf.writeDouble(precio);
            raf.writeInt(stock);
            raf.writeBoolean(true); // Activo
        }

        public void consultarArticulo(int numReg) throws IOException {
            long pos = (long) numReg * TAMA_REGISTRO;
            if (pos >= raf.length()) {
                System.out.println("Reg #" + numReg + " -> Fuera de los límites del fichero.");
                return;
            }

            raf.seek(pos);
            int id = raf.readInt();

            char[] chars = new char[LONG_DESC];
            for (int i = 0; i < LONG_DESC; i++) chars[i] = raf.readChar();
            String desc = new String(chars).trim();

            double precio = raf.readDouble();
            int stock = raf.readInt();
            boolean activo = raf.readBoolean();

            if (!activo) {
                System.out.println("Reg #" + numReg + " -> [BORRADO LÓGICAMENTE]");
            } else {
                System.out.printf("Reg #%d -> ID: %d | Desc: '%s' | Precio: %.2f EUR | Stock: %d%n",
                        numReg, id, desc, precio, stock);
            }
        }

        public void modificarPrecioYStock(int numReg, double nuevoPrecio, int nuevoStock) throws IOException {
            // Posicionamos el puntero directamente sobre los campos a modificar:
            // Desplazamiento: ID (4) + Descripcion (60) = 64 bytes
            long posCampos = (long) numReg * TAMA_REGISTRO + 64;
            if (posCampos >= raf.length()) return;

            raf.seek(posCampos);
            raf.writeDouble(nuevoPrecio);
            raf.writeInt(nuevoStock);
        }

        public void borrarLogico(int numReg) throws IOException {
            // Desplazamiento: 4 + 60 + 8 + 4 = 76 bytes (el último byte booleano)
            long posBooleano = (long) numReg * TAMA_REGISTRO + 76;
            if (posBooleano >= raf.length()) return;

            raf.seek(posBooleano);
            raf.writeBoolean(false); // Desactivar
        }

        @Override
        public void close() throws IOException {
            raf.close();
        }
    }

    public static void main(String[] args) {
        File f = new File("almacen.dat");

        try (GestorAlmacenAleatorio gestor = new GestorAlmacenAleatorio(f)) {
            // Insertamos 3 artículos en las posiciones 0, 1 y 2
            gestor.insertarArticulo(0, 1001, "Tornillo M4 20mm", 0.15, 500);
            gestor.insertarArticulo(1, 1002, "Tuerca M4", 0.08, 450);
            gestor.insertarArticulo(2, 1003, "Arandela M4", 0.05, 300);

            System.out.println("--- Consulta inicial ---");
            gestor.consultarArticulo(0);
            gestor.consultarArticulo(1);
            gestor.consultarArticulo(2);

            // Modificamos el precio y stock del registro 1 de forma directa
            gestor.modificarPrecioYStock(1, 0.10, 800);

            // Borramos lógicamente el registro 0
            gestor.borrarLogico(0);

            System.out.println("\n--- Consulta tras modificación y borrado lógico ---");
            gestor.consultarArticulo(0);
            gestor.consultarArticulo(1);

        } catch (IOException e) {
            System.err.println("Error RAF: " + e.getMessage());
        } finally {
            f.delete();
        }
    }
}
```

---

## Ejercicio 5: Compactación de ficheros aleatorios (*Pack*)

```java
package ud08.intermedio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio05CompactacionPack {

    public static final int TAMA_REGISTRO = Ejercicio04AlmacenAleatorioCRUD.TAMA_REGISTRO;

    public static void compactar(File archivoOriginal) throws IOException {
        File archivoTemp = new File(archivoOriginal.getParent(), "temp_" + archivoOriginal.getName());

        long tamOriginal = archivoOriginal.length();
        int regsConservados = 0;

        try (RandomAccessFile in = new RandomAccessFile(archivoOriginal, "r");
             RandomAccessFile out = new RandomAccessFile(archivoTemp, "rw")) {

            byte[] bufferRegistro = new byte[TAMA_REGISTRO];
            while (in.getFilePointer() < in.length()) {
                in.readFully(bufferRegistro);

                // El último byte del búfer indica el booleano 'activo' (1 = true, 0 = false)
                boolean activo = bufferRegistro[TAMA_REGISTRO - 1] != 0;
                if (activo) {
                    out.write(bufferRegistro);
                    regsConservados++;
                }
            }
        }

        // Reemplazamos el fichero original por el temporal
        archivoOriginal.delete();
        archivoTemp.renameTo(archivoOriginal);

        long tamCompactado = archivoOriginal.length();
        System.out.printf("Compactación completada: %d registros activos conservados.%n", regsConservados);
        System.out.printf("Tamaño previo: %d bytes -> Tamaño final: %d bytes (Ahorro: %d bytes)%n",
                tamOriginal, tamCompactado, (tamOriginal - tamCompactado));
    }

    public static void main(String[] args) throws IOException {
        File f = new File("almacen_prueba.dat");
        try (Ejercicio04AlmacenAleatorioCRUD.GestorAlmacenAleatorio gestor =
                     new Ejercicio04AlmacenAleatorioCRUD.GestorAlmacenAleatorio(f)) {
            gestor.insertarArticulo(0, 1, "Artículo A", 10.0, 5);
            gestor.insertarArticulo(1, 2, "Artículo B (borrado)", 20.0, 0);
            gestor.insertarArticulo(2, 3, "Artículo C", 30.0, 8);
            gestor.borrarLogico(1); // Borrado
        }

        compactar(f);
        f.delete();
    }
}
```

---

## Ejercicio 6: Algoritmo de búsqueda de patrones en buffer sin librerías (Búsqueda en Pi)

```java
package ud08.intermedio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Ejercicio06BusquedaPiSinLibrerias {

    public static long buscarEnPi(File archivoPi, String secuenciaBuscada) throws IOException {
        char[] patron = secuenciaBuscada.toCharArray();
        if (patron.length == 0) return -1;

        try (FileReader reader = new FileReader(archivoPi, StandardCharsets.UTF_8)) {
            char[] buffer = new char[8192];
            int leidos;
            long posicionGlobal = 0;
            int indiceCoincidencia = 0;
            boolean pasoPuntoDecimal = false;

            while ((leidos = reader.read(buffer)) != -1) {
                for (int i = 0; i < leidos; i++) {
                    char c = buffer[i];

                    // Ignoramos el "3." inicial para contar sólo los decimales
                    if (!pasoPuntoDecimal) {
                        if (c == '.') pasoPuntoDecimal = true;
                        continue;
                    }

                    posicionGlobal++;

                    if (c == patron[indiceCoincidencia]) {
                        indiceCoincidencia++;
                        if (indiceCoincidencia == patron.length) {
                            // Encontrado: devolvemos la posición donde empezó el patrón (1-indexada)
                            return posicionGlobal - patron.length + 1;
                        }
                    } else {
                        // Si falla, reiniciamos el índice del patrón
                        // (Nota: para patrones complejos se aplicaría KMP; para dígitos numéricos sencillos comprobamos inicio)
                        indiceCoincidencia = (c == patron[0]) ? 1 : 0;
                    }
                }
            }
        }
        return -1; // No encontrado
    }

    public static void main(String[] args) throws IOException {
        File archivo = new File("pi_muestra.txt");
        // Creamos una muestra con 3.14159265358979323846...
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("3.141592653589793238462643383279502884197169399375105820974944592307816406286");
        }

        String patron = "9265";
        long pos = buscarEnPi(archivo, patron);

        if (pos != -1) {
            System.out.println("La secuencia '" + patron + "' se encontró en el decimal #" + pos);
        } else {
            System.out.println("Secuencia no encontrada.");
        }

        archivo.delete();
    }
}
```

---

## Ejercicio 7: Contador estadístico de texto con `Map` (Líneas, Palabras y Top 10)

```java
package ud08.intermedio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ejercicio07EstadisticasTextoConMap {

    public static void main(String[] args) {
        File archivo = new File("quijote.txt");
        if (!archivo.exists()) {
            System.out.println("Archivo de prueba no encontrado. Creando muestra...");
            // Creamos archivo de muestra
            try {
                java.nio.file.Files.writeString(archivo.toPath(), """
                        En un lugar de la Mancha, de cuyo nombre no quiero acordarme,
                        no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero,
                        adarga antigua, rocín flaco y galgo corredor.
                        Una olla de algo más vaca que carnero, salpicón las más noches,
                        duelos y quebrantos los sábados, lantejas los viernes...
                        """);
            } catch (IOException ignored) {}
        }

        int totalLineas = 0;
        int totalPalabras = 0;
        int caracteresConEspacios = 0;
        int caracteresSinEspacios = 0;
        Map<String, Integer> frecuencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                totalLineas++;
                caracteresConEspacios += linea.length();

                for (char c : linea.toCharArray()) {
                    if (!Character.isWhitespace(c)) caracteresSinEspacios++;
                }

                // Separamos por cualquier conjunto de caracteres no alfabéticos
                String[] palabras = linea.toLowerCase().split("[^\\p{L}]+");
                for (String p : palabras) {
                    if (p.length() >= 3) { // Descartamos palabras muy cortas
                        totalPalabras++;
                        frecuencias.put(p, frecuencias.getOrDefault(p, 0) + 1);
                    }
                }
            }

            System.out.println("=== ESTADÍSTICAS DEL TEXTO ===");
            System.out.println("Total de líneas: " + totalLineas);
            System.out.println("Total de palabras (>= 3 letras): " + totalPalabras);
            System.out.println("Caracteres (con espacios): " + caracteresConEspacios);
            System.out.println("Caracteres (sin espacios): " + caracteresSinEspacios);

            System.out.println("\nTop 10 palabras más frecuentes:");
            List<Map.Entry<String, Integer>> top10 = frecuencias.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .toList();

            for (int i = 0; i < top10.size(); i++) {
                Map.Entry<String, Integer> e = top10.get(i);
                System.out.printf("   %2d. '%s' -> %d apariciones%n", i + 1, e.getKey(), e.getValue());
            }

        } catch (IOException e) {
            System.err.println("Error leyendo texto: " + e.getMessage());
        } finally {
            archivo.delete();
        }
    }
}
```

---

## Ejercicio 8: Parseo y modificación de XML con DOM

```java
package ud08.intermedio;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Ejercicio08ParseoDomXML {

    public static void main(String[] args) throws Exception {
        File xmlEntrada = new File("empleados.xml");
        // Muestra inicial de XML
        java.nio.file.Files.writeString(xmlEntrada.toPath(), """
                <?xml version="1.0" encoding="UTF-8"?>
                <empresa>
                    <empleado id="101">
                        <nombre>Ana Martinez</nombre>
                        <departamento>Ventas</departamento>
                        <sueldo>24000.0</sueldo>
                    </empleado>
                    <empleado id="102">
                        <nombre>Carlos Gomez</nombre>
                        <departamento>Informatica</departamento>
                        <sueldo>32000.0</sueldo>
                    </empleado>
                    <empleado id="103">
                        <nombre>Lucia Sanz</nombre>
                        <departamento>Informatica</departamento>
                        <sueldo>38000.0</sueldo>
                    </empleado>
                </empresa>
                """);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlEntrada);
        doc.getDocumentElement().normalize();

        NodeList empleados = doc.getElementsByTagName("empleado");
        double sumaSueldos = 0;
        int totalEmpleados = empleados.getLength();

        System.out.println("Empleados del departamento de Informática:");
        for (int i = 0; i < totalEmpleados; i++) {
            Element emp = (Element) empleados.item(i);
            String dep = emp.getElementsByTagName("departamento").item(0).getTextContent();
            double sueldo = Double.parseDouble(emp.getElementsByTagName("sueldo").item(0).getTextContent());
            sumaSueldos += sueldo;

            if ("Informatica".equalsIgnoreCase(dep)) {
                String nombre = emp.getElementsByTagName("nombre").item(0).getTextContent();
                System.out.printf("   - %s (Sueldo actual: %.2f EUR)%n", nombre, sueldo);

                // Aplicamos incremento del 10 %
                double nuevoSueldo = sueldo * 1.10;
                emp.getElementsByTagName("sueldo").item(0).setTextContent(String.format("%.2f", nuevoSueldo));
            }
        }

        System.out.printf("Sueldo medio global inicial: %.2f EUR%n", sumaSueldos / totalEmpleados);

        // Guardamos el DOM modificado con Transformer
        File xmlSalida = new File("empleados_actualizado.xml");
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(doc), new StreamResult(xmlSalida));

        System.out.println("Archivo actualizado guardado en: " + xmlSalida.getName());
        xmlEntrada.delete();
        xmlSalida.delete();
    }
}
```

---

## Ejercicio 9: Parseo de XML basado en eventos con SAX

```java
package ud08.intermedio;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Ejercicio09ParseoSaxXML {

    public static class ManejadorEmpleadosSAX extends DefaultHandler {
        private boolean enNombre = false;
        private boolean enSueldo = false;
        private String idActual;
        private String nombreActual;
        private double masaSalarialTotal = 0;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if ("empleado".equalsIgnoreCase(qName)) {
                idActual = attributes.getValue("id");
            } else if ("nombre".equalsIgnoreCase(qName)) {
                enNombre = true;
            } else if ("sueldo".equalsIgnoreCase(qName)) {
                enSueldo = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String valor = new String(ch, start, length).trim();
            if (valor.isEmpty()) return;

            if (enNombre) {
                nombreActual = valor;
            } else if (enSueldo) {
                double sueldo = Double.parseDouble(valor);
                masaSalarialTotal += sueldo;
                System.out.printf("[SAX] Empleado ID=%s: %s | Sueldo=%.2f EUR%n", idActual, nombreActual, sueldo);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ("nombre".equalsIgnoreCase(qName)) enNombre = false;
            if ("sueldo".equalsIgnoreCase(qName)) enSueldo = false;
        }

        @Override
        public void endDocument() {
            System.out.printf("Masa salarial total acumulada (SAX): %.2f EUR%n", masaSalarialTotal);
        }
    }

    public static void main(String[] args) throws Exception {
        File xml = new File("empleados_sax.xml");
        java.nio.file.Files.writeString(xml.toPath(), """
                <empresa>
                    <empleado id="1"><nombre>Laura</nombre><sueldo>30000</sueldo></empleado>
                    <empleado id="2"><nombre>Marcos</nombre><sueldo>28000</sueldo></empleado>
                </empresa>
                """);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xml, new ManejadorEmpleadosSAX());

        xml.delete();
    }
}
```

---

## Ejercicio 10: Compresión y extracción de archivos ZIP (`ZipStream`)

```java
package ud08.intermedio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Ejercicio10CompresionZip {

    public static void comprimirEnZip(List<File> archivos, File archivoZipDestino) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archivoZipDestino))) {
            byte[] buffer = new byte[4096];
            for (File archivo : archivos) {
                ZipEntry entry = new ZipEntry(archivo.getName());
                zos.putNextEntry(entry);

                try (FileInputStream fis = new FileInputStream(archivo)) {
                    int leidos;
                    while ((leidos = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, leidos);
                    }
                }
                zos.closeEntry();
            }
        }
    }

    public static void descomprimirZip(File archivoZip, File carpetaDestino) throws IOException {
        carpetaDestino.mkdirs();
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip))) {
            ZipEntry entry;
            byte[] buffer = new byte[4096];
            while ((entry = zis.getNextEntry()) != null) {
                File destino = new File(carpetaDestino, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(destino)) {
                    int leidos;
                    while ((leidos = zis.read(buffer)) != -1) {
                        fos.write(buffer, 0, leidos);
                    }
                }
                zis.closeEntry();
                System.out.println("   Extraído: " + destino.getName());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File f1 = new File("nota1.txt");
        File f2 = new File("nota2.txt");
        java.nio.file.Files.writeString(f1.toPath(), "Contenido nota 1");
        java.nio.file.Files.writeString(f2.toPath(), "Contenido nota 2");

        File zip = new File("paquete.zip");
        comprimirEnZip(List.of(f1, f2), zip);
        System.out.println("Archivos comprimidos en: " + zip.getName());

        File carpetaExtraer = new File("extraidos");
        descomprimirZip(zip, carpetaExtraer);

        // Limpieza
        f1.delete(); f2.delete(); zip.delete();
        for (File hijo : carpetaExtraer.listFiles()) hijo.delete();
        carpetaExtraer.delete();
    }
}
```

---

## Ejercicio 11: Inspector de proyectos con Java NIO.2 (`Files.walk` y `Files.lines`)

```java
package ud08.intermedio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Ejercicio11AuditoriaCodigoNIO2 {

    public static void auditarProyecto(Path raiz) throws IOException {
        System.out.println("=== AUDITORÍA DEL PROYECTO: " + raiz.toAbsolutePath() + " ===");

        try (Stream<Path> arbol = Files.walk(raiz)) {
            List<Path> archivosJava = arbol
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .toList();

            System.out.println("Total de archivos fuente Java encontrados: " + archivosJava.size());

            // Archivo más grande
            archivosJava.stream()
                    .max(Comparator.comparingLong(p -> {
                        try { return Files.size(p); } catch (IOException e) { return 0L; }
                    }))
                    .ifPresent(p -> {
                        try {
                            System.out.println("Archivo más pesado: " + p.getFileName() + " (" + Files.size(p) + " bytes)");
                        } catch (IOException ignored) {}
                    });

            long lineasTotales = 0;
            long lineasEfectivas = 0;

            for (Path arch : archivosJava) {
                try (Stream<String> lineas = Files.lines(arch, StandardCharsets.UTF_8)) {
                    List<String> todas = lineas.map(String::trim).toList();
                    lineasTotales += todas.size();
                    lineasEfectivas += todas.stream()
                            .filter(l -> !l.isEmpty() && !l.startsWith("//") && !l.startsWith("*") && !l.startsWith("/*"))
                            .count();
                } catch (Exception ignored) {}
            }

            System.out.println("Total de líneas físicas: " + lineasTotales);
            System.out.println("Líneas efectivas de código (sin comentarios/vacías): " + lineasEfectivas);
        }
    }

    public static void main(String[] args) throws IOException {
        auditarProyecto(Path.of("src"));
    }
}
```

---

## Ejercicio 12: Selector gráfico con `JFileChooser` y visor de propiedades

```java
package ud08.intermedio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ejercicio12SelectorGraficoFileChooser {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Documentos e Imágenes", "txt", "csv", "png", "jpg", "pdf");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Seleccione un fichero para inspeccionar");

        int respuesta = chooser.showOpenDialog(null);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            System.out.println("=== PROPIEDADES DEL ARCHIVO SELECCIONADO ===");
            System.out.println("Nombre: " + f.getName());
            System.out.println("Ruta absoluta: " + f.getAbsolutePath());
            System.out.printf("Tamaño: %.2f KB (%d bytes)%n", f.length() / 1024.0, f.length());
            System.out.println("Permisos: Lectura=" + f.canRead() + " | Escritura=" + f.canWrite());

            try {
                String mime = Files.probeContentType(f.toPath());
                System.out.println("Tipo MIME detectado: " + (mime != null ? mime : "Desconocido"));
            } catch (IOException e) {
                System.out.println("Tipo MIME: Error al detectar");
            }
        } else {
            System.out.println("Selección cancelada por el usuario.");
        }
    }
}
```
