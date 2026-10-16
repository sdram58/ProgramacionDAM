---
title: "Boletín 8 — Retos Extras y Proyectos Integradores"
description: "Proyectos avanzados de la Unidad 8 con soluciones completas en Java 25 LTS: MiniTerminal & MiniFileManager, Base de Datos persistente por serialización con navegación interactiva, Motor aleatorio con RandomAccessFile e índice en memoria, Conversor XML a JSON, Procesador binario de imágenes BMP y Capturador automático con Robot y NIO.2."
slug: ud08/boletin-08-extras
draft: true
prev: false
next: false
---

Este boletín reúne **6 proyectos integradores de nivel profesional** que consolidan todas las competencias del módulo en persistencia, flujos de entrada/salida, estructuración binaria y manipulación del sistema de archivos. Cada proyecto incluye su enunciado formal, requerimientos técnicos y la **solución completa desplegable en Java 25 LTS**. Al final se ofrece una selección de retos algorítmicos de **Acepta el Reto** y **CodeWars** centrados en el control de flujos y fin de fichero (*EOF*).

---

## Proyecto 1: Emulador de Terminal y Gestor de Archivos (`MiniTerminal` & `MiniFileManager`)

Diseña una shell interactiva por línea de comandos que permita a un usuario navegar y administrar el sistema de archivos local de forma modular y desacoplada mediante dos clases:

1. **`MiniFileManager`:** Clase de servicio que encapsula el directorio de trabajo activo (`File carpetaActual`) y proporciona métodos robustos de gestión:
   - `String getPWD()`: Devuelve la ruta absoluta de la carpeta de trabajo actual.
   - `boolean changeDir(String nuevoDir)`: Cambia la carpeta actual (soporta `..` para subir al padre y nombres relativos o absolutos).
   - `void printList(boolean detallado)`: Lista el contenido de la carpeta actual mostrando primero los directorios y luego los archivos, ambos ordenados alfabéticamente. Si `detallado` es `true`, muestra además el tamaño en KB y la fecha de última modificación.
   - `boolean makeDir(String nombreCarpeta)`: Crea un subdirectorio en la carpeta actual.
   - `boolean removeFile(String nombre)`: Si es un archivo lo borra; si es una carpeta, borra sus archivos interiores y la carpeta (si tiene subcarpetas, avisa y aborta por seguridad).
   - `boolean moveOrRename(String origen, String destino)`: Traslada o renombra un archivo o directorio.
   - `void showContent(String nombreArchivo)`: Muestra el contenido de texto de un archivo en pantalla.
2. **`MiniTerminal`:** Clase principal con bucle de interpretación interactivo (*REPL: Read-Eval-Print Loop*) que solicita comandos al usuario (`pwd`, `cd <DIR>`, `ls`, `ll`, `mkdir <DIR>`, `rm <NAME>`, `mv <SRC> <DST>`, `cat <FILE>`, `help`, `exit`), parsea los argumentos y maneja todas las excepciones sin interrumpir la ejecución.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Proyecto01MiniTerminal {

    public static class MiniFileManager {
        private File carpetaActual;
        private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        public MiniFileManager() {
            this.carpetaActual = new File(System.getProperty("user.dir"));
        }

        public String getPWD() {
            return carpetaActual.getAbsolutePath();
        }

        public boolean changeDir(String dir) {
            File destino;
            if ("..".equals(dir)) {
                destino = carpetaActual.getParentFile();
                if (destino == null) return false;
            } else {
                destino = new File(carpetaActual, dir);
            }

            if (destino.exists() && destino.isDirectory()) {
                carpetaActual = destino;
                return true;
            }
            return false;
        }

        public void printList(boolean detallado) {
            File[] elementos = carpetaActual.listFiles();
            if (elementos == null) return;

            Arrays.sort(elementos, Comparator.comparing(File::isFile)
                    .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER));

            for (File f : elementos) {
                String tipo = f.isDirectory() ? "[DIR]" : "[ARX]";
                if (!detallado) {
                    System.out.println("  " + tipo + " " + f.getName());
                } else {
                    LocalDateTime fecha = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault());
                    long tamKb = f.isFile() ? (f.length() + 1023) / 1024 : 0L;
                    System.out.printf("  %s %-25s | %6d KB | %s%n",
                            tipo, f.getName(), tamKb, fecha.format(FORMATO));
                }
            }
        }

        public boolean makeDir(String nombre) {
            File nuevo = new File(carpetaActual, nombre);
            return nuevo.mkdir();
        }

        public boolean removeFile(String nombre) {
            File objetivo = new File(carpetaActual, nombre);
            if (!objetivo.exists()) return false;

            if (objetivo.isFile()) {
                return objetivo.delete();
            } else {
                // Directorio: verificar si tiene subcarpetas
                File[] hijos = objetivo.listFiles();
                if (hijos != null) {
                    for (File h : hijos) {
                        if (h.isDirectory()) {
                            System.err.println("Aviso: El directorio contiene subcarpetas. Operación cancelada.");
                            return false;
                        }
                    }
                    for (File h : hijos) h.delete();
                }
                return objetivo.delete();
            }
        }

        public boolean moveOrRename(String origen, String destino) {
            File fOrig = new File(carpetaActual, origen);
            File fDest = new File(carpetaActual, destino);
            if (!fOrig.exists()) return false;
            return fOrig.renameTo(fDest);
        }

        public void showContent(String nombre) throws IOException {
            File f = new File(carpetaActual, nombre);
            if (!f.exists() || !f.isFile()) {
                System.err.println("El archivo no existe o no es un fichero de texto.");
                return;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(f, StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }
        }
    }

    public static void main(String[] args) {
        MiniFileManager mfm = new MiniFileManager();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== MiniTerminal v1.0 (Escribe 'help' para ver comandos) ===");

        while (true) {
            System.out.print("\n" + mfm.getPWD() + " > ");
            String linea = sc.nextLine().trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\s+");
            String comando = partes[0].toLowerCase();

            try {
                switch (comando) {
                    case "pwd" -> System.out.println(mfm.getPWD());
                    case "cd" -> {
                        if (partes.length < 2) System.out.println("Uso: cd <DIRECTORIO>");
                        else if (!mfm.changeDir(partes[1])) System.out.println("Directorio no encontrado.");
                    }
                    case "ls" -> mfm.printList(false);
                    case "ll" -> mfm.printList(true);
                    case "mkdir" -> {
                        if (partes.length < 2) System.out.println("Uso: mkdir <NOMBRE>");
                        else System.out.println(mfm.makeDir(partes[1]) ? "Directorio creado." : "Error al crear.");
                    }
                    case "rm" -> {
                        if (partes.length < 2) System.out.println("Uso: rm <NOMBRE>");
                        else System.out.println(mfm.removeFile(partes[1]) ? "Elemento borrado." : "Error al borrar.");
                    }
                    case "mv" -> {
                        if (partes.length < 3) System.out.println("Uso: mv <ORIGEN> <DESTINO>");
                        else System.out.println(mfm.moveOrRename(partes[1], partes[2]) ? "Operación completada." : "Error al mover/renombrar.");
                    }
                    case "cat" -> {
                        if (partes.length < 2) System.out.println("Uso: cat <FICHERO>");
                        else mfm.showContent(partes[1]);
                    }
                    case "help" -> {
                        System.out.println("Comandos disponibles:");
                        System.out.println("  pwd             -> Muestra la ruta actual");
                        System.out.println("  cd <DIR>        -> Cambia de directorio (.. para subir)");
                        System.out.println("  ls / ll         -> Lista elementos (simple / detallado)");
                        System.out.println("  mkdir <DIR>     -> Crea un nuevo directorio");
                        System.out.println("  rm <NAME>       -> Borra un archivo o carpeta vacía");
                        System.out.println("  mv <SRC> <DST>  -> Mueve o renombra");
                        System.out.println("  cat <FILE>      -> Muestra el contenido del archivo");
                        System.out.println("  exit            -> Sale de la terminal");
                    }
                    case "exit" -> {
                        System.out.println("Cerrando sesión de MiniTerminal...");
                        return;
                    }
                    default -> System.out.println("Comando no reconocido. Escribe 'help'.");
                }
            } catch (Exception e) {
                System.err.println("Error ejecutando comando: " + e.getMessage());
            }
        }
    }
}
```

</details>

---

## Proyecto 2: Base de Datos Persistente por Serialización con Navegación Interactiva

Implementa el sistema interactivo de gestión de registros basado en serialización completa de colecciones (`ArrayList<Registro>`) propuesto en la autoevaluación oficial (EA28):

1. **Modelo `Registro`:** Implementa `Serializable` con atributos `int id`, `String nombre`, `String telefono`, `double deuda`.
2. **Navegación e interfaz de usuario:** Carga el archivo `agenda.bin` al iniciar la aplicación. Permite al usuario desplazarse con un cursor activo mediante el menú:
   `[<<] Primero | [<] Anterior | [>] Siguiente | [>>] Último | [A]ñadir | [B]orrar | [M]odificar | [O]rdenar | [S]alir`
3. Al pulsar `[S]alir`, serializa automáticamente la lista completa al fichero binario para garantizar la persistencia de los cambios.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Proyecto02BaseDatosSerializada {

    public static class Registro implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private int id;
        private String nombre;
        private String telefono;
        private double deuda;

        public Registro(int id, String nombre, String telefono, double deuda) {
            this.id = id;
            this.nombre = nombre;
            this.telefono = telefono;
            this.deuda = deuda;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        public double getDeuda() { return deuda; }
        public void setDeuda(double deuda) { this.deuda = deuda; }

        @Override
        public String toString() {
            return String.format("[ID: %d] %-18s | Tel: %-10s | Deuda: %.2f EUR", id, nombre, telefono, deuda);
        }
    }

    private static final File ARCHIVO_DATOS = new File("agenda_contactos.bin");
    private static List<Registro> datos = new ArrayList<>();
    private static int posActual = -1;

    @SuppressWarnings("unchecked")
    private static void cargarDatos() {
        if (!ARCHIVO_DATOS.exists() || ARCHIVO_DATOS.length() == 0) return;
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(ARCHIVO_DATOS)))) {
            datos = (List<Registro>) ois.readObject();
            if (!datos.isEmpty()) posActual = 0;
        } catch (Exception e) {
            System.err.println("Error al cargar datos previos: " + e.getMessage());
        }
    }

    private static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(ARCHIVO_DATOS)))) {
            oos.writeObject(datos);
            System.out.println("Datos guardados correctamente en " + ARCHIVO_DATOS.getName());
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        cargarDatos();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--------------------------------------------------------------");
            if (datos.isEmpty() || posActual < 0) {
                System.out.println("Base de datos vacía. No hay registros seleccionados.");
            } else {
                System.out.printf("Registro %d de %d:%n   %s%n", posActual + 1, datos.size(), datos.get(posActual));
            }
            System.out.println("--------------------------------------------------------------");
            System.out.print("[<<] [<] [>] [>>] [A]ñadir [B]orrar [M]odificar [O]rdenar [S]alir: ");
            String op = sc.nextLine().trim().toUpperCase();

            switch (op) {
                case "<<" -> posActual = datos.isEmpty() ? -1 : 0;
                case "<" -> { if (posActual > 0) posActual--; }
                case ">" -> { if (posActual < datos.size() - 1) posActual++; }
                case ">>" -> posActual = datos.isEmpty() ? -1 : datos.size() - 1;
                case "A" -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Nombre: ");
                    String nom = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = sc.nextLine();
                    System.out.print("Deuda: ");
                    double deuda = Double.parseDouble(sc.nextLine());
                    datos.add(new Registro(id, nom, tel, deuda));
                    posActual = datos.size() - 1;
                    System.out.println("Registro añadido.");
                }
                case "B" -> {
                    if (posActual >= 0 && posActual < datos.size()) {
                        datos.remove(posActual);
                        if (posActual >= datos.size()) posActual = datos.size() - 1;
                        System.out.println("Registro eliminado.");
                    }
                }
                case "M" -> {
                    if (posActual >= 0 && posActual < datos.size()) {
                        Registro r = datos.get(posActual);
                        System.out.print("Nuevo nombre [" + r.getNombre() + "]: ");
                        String nom = sc.nextLine();
                        if (!nom.isEmpty()) r.setNombre(nom);
                        System.out.print("Nuevo teléfono [" + r.getTelefono() + "]: ");
                        String tel = sc.nextLine();
                        if (!tel.isEmpty()) r.setTelefono(tel);
                        System.out.print("Nueva deuda [" + r.getDeuda() + "]: ");
                        String d = sc.nextLine();
                        if (!d.isEmpty()) r.setDeuda(Double.parseDouble(d));
                        System.out.println("Registro actualizado.");
                    }
                }
                case "O" -> {
                    datos.sort(Comparator.comparing(Registro::getNombre, String.CASE_INSENSITIVE_ORDER));
                    posActual = datos.isEmpty() ? -1 : 0;
                    System.out.println("Registros ordenados por nombre.");
                }
                case "S" -> {
                    guardarDatos();
                    ARCHIVO_DATOS.delete(); // Limpieza para tests
                    System.out.println("¡Hasta pronto!");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
```

</details>

---

## Proyecto 3: Motor de Registros Aleatorios con Tabla de Índices en RAM

Para manejar millones de registros donde la RAM es insuficiente para almacenar todos los objetos, se combinan dos mundos:
- Los registros residen en disco en un archivo `RandomAccessFile` con longitud fija.
- En memoria RAM solo reside una **tabla de índices ligera (`Map<Integer, Long> indiceIdAPosicion`)** que asocia la clave primaria `id` con la posición de byte exacta del registro en disco, permitiendo búsquedas, lecturas y modificaciones en tiempo constante `O(1)` sin escanear el fichero.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Proyecto03MotorAleatorioIndexado implements AutoCloseable {

    public record RegistroCliente(int id, String nif, double saldo, boolean activo) {}

    // int id (4) + char[9] nif (18) + double saldo (8) + boolean activo (1) = 31 bytes
    public static final int TAMA_REGISTRO = 31;
    private final RandomAccessFile raf;
    private final Map<Integer, Long> indice = new HashMap<>();

    public Proyecto03MotorAleatorioIndexado(File archivo) throws IOException {
        this.raf = new RandomAccessFile(archivo, "rw");
        construirIndice();
    }

    private void construirIndice() throws IOException {
        indice.clear();
        long pos = 0;
        long totalBytes = raf.length();

        while (pos + TAMA_REGISTRO <= totalBytes) {
            raf.seek(pos);
            int id = raf.readInt();
            raf.skipBytes(18 + 8); // Saltamos nif y saldo
            boolean activo = raf.readBoolean();

            if (activo) {
                indice.put(id, pos);
            }
            pos += TAMA_REGISTRO;
        }
    }

    public void guardarOActualizar(int id, String nif, double saldo) throws IOException {
        long pos;
        if (indice.containsKey(id)) {
            pos = indice.get(id); // Sobrescribir en su posición
        } else {
            pos = raf.length();   // Escribir al final del archivo
            indice.put(id, pos);
        }

        raf.seek(pos);
        raf.writeInt(id);

        StringBuilder sb = new StringBuilder(nif);
        sb.setLength(9);
        raf.writeChars(sb.toString());

        raf.writeDouble(saldo);
        raf.writeBoolean(true); // Activo
    }

    public Optional<RegistroCliente> buscarPorId(int id) throws IOException {
        Long pos = indice.get(id);
        if (pos == null) return Optional.empty();

        raf.seek(pos);
        int regId = raf.readInt();

        char[] chars = new char[9];
        for (int i = 0; i < 9; i++) chars[i] = raf.readChar();
        String nif = new String(chars).trim();

        double saldo = raf.readDouble();
        boolean activo = raf.readBoolean();

        return Optional.of(new RegistroCliente(regId, nif, saldo, activo));
    }

    public boolean eliminarPorId(int id) throws IOException {
        Long pos = indice.remove(id);
        if (pos == null) return false;

        // Desactivamos el registro en disco marcando el byte activo a false
        raf.seek(pos + 4 + 18 + 8);
        raf.writeBoolean(false);
        return true;
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

    public static void main(String[] args) throws IOException {
        File f = new File("clientes_indexados.dat");
        try (Proyecto03MotorAleatorioIndexado motor = new Proyecto03MotorAleatorioIndexado(f)) {
            motor.guardarOActualizar(501, "12345678A", 1250.0);
            motor.guardarOActualizar(502, "87654321B", 3400.5);
            motor.guardarOActualizar(503, "11223344C", 85.0);

            System.out.println("Búsqueda O(1) de ID 502: " + motor.buscarPorId(502).orElse(null));
            motor.eliminarPorId(502);
            System.out.println("Búsqueda tras borrado de ID 502: " + motor.buscarPorId(502).orElse(null));
        } finally {
            f.delete();
        }
    }
}
```

</details>

---

## Proyecto 4: Analizador y Conversor Bidireccional XML a JSON

Crea un conversor de documentos estructurados que lea un fichero de compras en formato XML mediante **DOM** y lo transforme a un formato JSON equivalente:

1. Lee `pedidos.xml` extrayendo el ID del pedido, el cliente y la lista de artículos comprados con su precio y cantidad.
2. Calcula el coste total de cada pedido.
3. Genera un fichero de salida `pedidos.json` con el formato JSON estándar formateado con sangrías.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Proyecto04ConversorXmlJson {

    public record Articulo(String descripcion, int cantidad, double precioUnitario) {
        public double subtotal() { return cantidad * precioUnitario; }
    }

    public record Pedido(String id, String cliente, List<Articulo> articulos) {
        public double total() {
            return articulos.stream().mapToDouble(Articulo::subtotal).sum();
        }
    }

    public static List<Pedido> parsearXmlPedidos(File fXml) throws Exception {
        List<Pedido> pedidos = new ArrayList<>();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(fXml);
        doc.getDocumentElement().normalize();

        NodeList nodosPedidos = doc.getElementsByTagName("pedido");
        for (int i = 0; i < nodosPedidos.getLength(); i++) {
            Element elPed = (Element) nodosPedidos.item(i);
            String id = elPed.getAttribute("id");
            String cliente = elPed.getElementsByTagName("cliente").item(0).getTextContent();

            List<Articulo> arts = new ArrayList<>();
            NodeList nodosArt = elPed.getElementsByTagName("articulo");
            for (int j = 0; j < nodosArt.getLength(); j++) {
                Element elArt = (Element) nodosArt.item(j);
                String desc = elArt.getElementsByTagName("descripcion").item(0).getTextContent();
                int cant = Integer.parseInt(elArt.getElementsByTagName("cantidad").item(0).getTextContent());
                double precio = Double.parseDouble(elArt.getElementsByTagName("precio").item(0).getTextContent());
                arts.add(new Articulo(desc, cant, precio));
            }
            pedidos.add(new Pedido(id, cliente, arts));
        }
        return pedidos;
    }

    public static String serializarAJson(List<Pedido> pedidos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": \"").append(p.id()).append("\",\n");
            sb.append("    \"cliente\": \"").append(p.cliente()).append("\",\n");
            sb.append("    \"total\": ").append(String.format("%.2f", p.total()).replace(',', '.')).append(",\n");
            sb.append("    \"articulos\": [\n");

            for (int j = 0; j < p.articulos().size(); j++) {
                Articulo a = p.articulos().get(j);
                sb.append("      {\n");
                sb.append("        \"descripcion\": \"").append(a.descripcion()).append("\",\n");
                sb.append("        \"cantidad\": ").append(a.cantidad()).append(",\n");
                sb.append("        \"precio\": ").append(a.precioUnitario()).append("\n");
                sb.append("      }").append(j < p.articulos().size() - 1 ? "," : "").append("\n");
            }
            sb.append("    ]\n");
            sb.append("  }").append(i < pedidos.size() - 1 ? "," : "").append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        File xml = new File("pedidos.xml");
        java.nio.file.Files.writeString(xml.toPath(), """
                <pedidos>
                    <pedido id="PED-01">
                        <cliente>Marta Sanchez</cliente>
                        <articulo>
                            <descripcion>Auriculares Bluetooth</descripcion>
                            <cantidad>2</cantidad>
                            <precio>29.99</precio>
                        </articulo>
                        <articulo>
                            <descripcion>Funda protectora</descripcion>
                            <cantidad>1</cantidad>
                            <precio>9.50</precio>
                        </articulo>
                    </pedido>
                </pedidos>
                """);

        List<Pedido> pedidos = parsearXmlPedidos(xml);
        String json = serializarAJson(pedidos);

        File fJson = new File("pedidos.json");
        try (FileWriter fw = new FileWriter(fJson, StandardCharsets.UTF_8)) {
            fw.write(json);
        }

        System.out.println("JSON generado con éxito:\n" + json);
        xml.delete();
        fJson.delete();
    }
}
```

</details>

---

## Proyecto 5: Procesador y Filtro Binario de Imágenes BMP (Inversión de Color)

Implementa el analizador binario de imágenes de mapa de bits (BMP) de la autoevaluación oficial (EA23):

1. Abre una imagen `.bmp` con `FileInputStream`.
2. Lee y valida la cabecera de 54 bytes:
   - Comprueba que los dos primeros bytes sean los caracteres mágicos `'B'` y `'M'` (`0x42` y `0x4D`).
   - Lee el offset de inicio del array de píxeles (bytes 10 a 13) y la resolución de la imagen (ancho en bytes 18 a 21 y alto en bytes 22 a 25).
3. Escribe en un nuevo archivo `imagen_invertida.bmp` la misma cabecera original.
4. Para todos los bytes restantes que forman los colores RGB de los píxeles, aplica la transformación de negativo de color invirtiendo cada byte: `int invertido = 255 - byteOriginal;` y vuelca el resultado en el fichero destino.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Proyecto05FiltroBinarioBmp {

    public static boolean procesarNegativoBmp(File origen, File destino) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(origen));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destino))) {

            byte[] cabecera = new byte[54];
            int leidos = in.read(cabecera);
            if (leidos < 54) return false;

            // Validación de la firma BMP ('B' 'M')
            if (cabecera[0] != 'B' || cabecera[1] != 'M') {
                System.err.println("El fichero no es una imagen válida de formato BMP.");
                return false;
            }

            // Escribimos la cabecera idéntica en el destino
            out.write(cabecera);

            // Invertimos byte a byte la matriz de píxeles (255 - valor)
            int b;
            while ((b = in.read()) != -1) {
                out.write(255 - b);
            }
            return true;
        }
    }

    public static void main(String[] args) {
        File bmpOrig = new File("entrada.bmp");
        File bmpDest = new File("salida_negativo.bmp");

        // Creamos una cabecera BMP mínima ficticia para validar el flujo
        try (FileOutputStream fos = new FileOutputStream(bmpOrig)) {
            byte[] cabeceraMin = new byte[54];
            cabeceraMin[0] = 'B';
            cabeceraMin[1] = 'M';
            fos.write(cabeceraMin);
            fos.write(new byte[]{0, 100, (byte) 200}); // 3 bytes de muestra
        } catch (IOException ignored) {}

        try {
            boolean exito = procesarNegativoBmp(bmpOrig, bmpDest);
            System.out.println("¿Proceso de inversión completado?: " + exito);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bmpOrig.delete();
            bmpDest.delete();
        }
    }
}
```

</details>

---

## Proyecto 6: Capturador y Archivador Automático con `Robot`, `ImageIO` y NIO.2

Desarrolla el servicio de captura automática de pantalla con organización por fechas de la autoevaluación oficial (EA27):

1. Utiliza la clase `java.awt.Robot` y `Toolkit.getDefaultToolkit().getScreenSize()` para tomar una captura de la pantalla completa en un `BufferedImage`.
2. Emplea la API **Java NIO.2** (`Files.createDirectories`) para crear una estructura de carpetas jerárquica con el año, mes y día actual:
   `capturas/2025/10/16/`
3. Guarda la imagen en formato PNG mediante `ImageIO.write` con un nombre basado en la marca de tiempo: `captura_HHmmss.png`.
4. Añade una línea de registro en un archivo de auditoría `capturas/registro_auditoria.csv` con la fecha, hora, ruta del archivo y tamaño en KB.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud08.extras;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

public class Proyecto06CapturadorPantallaNIO2 {

    public static Path capturarYArchivar(Path carpetaBase) throws AWTException, IOException {
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        // 1. Estructura de carpetas NIO.2: capturas/AAAA/MM/DD
        Path dirDestino = carpetaBase
                .resolve(String.valueOf(hoy.getYear()))
                .resolve(String.format("%02d", hoy.getMonthValue()))
                .resolve(String.format("%02d", hoy.getDayOfMonth()));
        Files.createDirectories(dirDestino);

        // 2. Captura con Robot
        Robot robot = new Robot();
        Rectangle pantalla = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage img = robot.createScreenCapture(pantalla);

        // 3. Guardar imagen con ImageIO
        String nombreFichero = String.format("captura_%s.png", ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        Path archivoImg = dirDestino.resolve(nombreFichero);
        ImageIO.write(img, "png", archivoImg.toFile());

        // 4. Registrar en CSV de auditoría
        Path logCsv = carpetaBase.resolve("registro_auditoria.csv");
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new FileWriter(logCsv.toFile(), StandardCharsets.UTF_8, true)))) {
            long kb = (Files.size(archivoImg) + 1023) / 1024;
            pw.printf("%s,%s,%s,%d KB%n", hoy, ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")), archivoImg.getFileName(), kb);
        }

        return archivoImg;
    }

    public static void main(String[] args) {
        Path base = Path.of("capturas_prueba");
        try {
            Path guardada = capturarYArchivar(base);
            System.out.println("Captura archivada con éxito en: " + guardada.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Aviso (entorno sin pantalla o error gráfico): " + e.getMessage());
        } finally {
            // Limpieza opcional de pruebas
            try {
                if (Files.exists(base)) {
                    try (var s = Files.walk(base)) {
                        s.sorted((p1, p2) -> -p1.compareTo(p2)).forEach(p -> {
                            try { Files.deleteIfExists(p); } catch (Exception ignored) {}
                        });
                    }
                }
            } catch (Exception ignored) {}
        }
    }
}
```

</details>

---

## 🏆 Retos Externos: Acepta el Reto y CodeWars

### 🟢 CodeWars (Manejo de Cadenas y Parsing)

1. **[Parse nice int from char problem (8 kyu)](https://www.codewars.com/kata/557cd6882bfa3c8a9f0000c1/train/java)**
   - **Objetivo:** Extraer la edad numérica a partir de cadenas como `"4 years old"`.
   - **Pista con I/O:** Equivalente a leer el primer token entero con `Scanner.nextInt()`.
2. **[Valid Braces / Paréntesis equilibrados (6 kyu)](https://www.codewars.com/kata/5277c8604b6b3cf33b000078/train/java)**
   - **Objetivo:** Verificar si los delimitadores `()`, `[]`, `{}` están anidados correctamente.
   - **Pista:** Es la lógica fundamental de verificación sintáctica utilizada por los parsers de XML y JSON.
3. **[CSV representation of array (8 kyu)](https://www.codewars.com/kata/5a34af40e1ce0eb1f5000036/train/java)**
   - **Objetivo:** Convertir una matriz bidimensional de enteros a formato CSV con saltos de línea `\n`.
   - **Pista:** Patrón de volcado formateado similar a `PrintWriter.print` y `pw.println`.

### 🔵 Acepta el Reto

1. **[100 — Constante de Kaprekar](https://aceptaelreto.com/problem/statement.php?id=100)**
   - **Control de entrada con centinela:** Lectura de casos de prueba con `Scanner.hasNextInt()` hasta fin de fichero.
2. **[116 — ¡Hola mundo!](https://aceptaelreto.com/problem/statement.php?id=116)**
   - **Salida estándar masiva:** Uso eficiente de `System.out.println` o `PrintWriter` para imprimir respuestas sin exceder el tiempo límite.
3. **[149 — Las caries del emperador](https://aceptaelreto.com/problem/statement.php?id=149)**
   - **Búsqueda de máximos en flujo:** Detección del valor más alto en secuencias numéricas leyendo línea a línea hasta fin de fichero (*EOF*).
