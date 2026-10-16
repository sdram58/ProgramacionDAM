---
title: "Boletín 8 — Nivel Intermedio"
description: "Ejercicios propuestos de nivel intermedio sobre persistencia de objetos (Serializable, transient, MiObjectOutputStream), ficheros de acceso aleatorio (RandomAccessFile, seek, borrado lógico y compactación), compresión ZIP, parsers XML (DOM y SAX), algoritmos de búsqueda y utilidades con Java NIO.2."
slug: ud08/boletin-08-intermedio
draft: true
prev: false
next: false
---

En este boletín de nivel intermedio profundizarás en las técnicas profesionales de persistencia de datos y manipulación de archivos en Java: serialización de grafos de objetos con `ObjectInputStream` y `ObjectOutputStream`, resolución técnica de la cabecera duplicada en modo *append* (`MiObjectOutputStream`), diseño de bases de datos basadas en ficheros de acceso aleatorio con `RandomAccessFile` (cálculo de desplazamientos de puntero, borrado lógico y compactación *pack*), compresión y extracción de archivos ZIP, procesamiento de documentos estructurados XML mediante **DOM** y **SAX**, algoritmos de búsqueda manual sobre grandes volúmenes de texto y utilidades avanzadas con la API **Java NIO.2**.

---

## Ejercicio 1: Persistencia de objetos y serialización de colecciones

En una clínica veterinaria se gestiona un censo de pacientes caninos y felinos representados por la clase `Mascota`:

```java
public class Mascota implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String chip;
    private String nombre;
    private String especie;
    private int edad;
    private double pesoKg;
    // Constructor, getters, setters y toString
}
```

1. Diseña una clase `ClinicaVeterinaria` que mantenga en memoria una `List<Mascota>`.
2. Implementa el método `public void guardarCenso(File archivo) throws IOException` que serialice la lista completa de mascotas en un archivo binario mediante `ObjectOutputStream` con búfer.
3. Implementa el método `public void cargarCenso(File archivo) throws IOException, ClassNotFoundException` que lea y deserialice la lista con `ObjectInputStream`, reemplazando el censo en memoria.
4. Escribe un programa principal que añada al menos 4 mascotas, las guarde en `censo.obj`, limpie la lista en memoria y las recupere desde el archivo, verificando su integridad.

---

## Ejercicio 2: Control de campos sensibles con `transient` y versionado

Una plataforma de comercio electrónico modela las cuentas de sus clientes mediante `CuentaUsuario`:

```java
public class CuentaUsuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 100L;

    private String username;
    private String email;
    private transient String passwordHash; // Campo confidencial
    private transient String tokenSesion;   // Token volátil de sesión
    private double saldoMonedero;
}
```

1. Explica qué ocurrirá con los campos `passwordHash` y `tokenSesion` cuando un objeto de esta clase sea serializado a disco y posteriormente deserializado en otra ejecución del programa.
2. Implementa un método de prueba que serialice tres usuarios con sus correspondientes contraseñas y tokens.
3. Deserializa el archivo e imprime el contenido de los objetos recuperados, comprobando experimentalmente que los campos `transient` han tomado el valor `null` por defecto.
4. ¿Qué ocurriría si modificas el valor de `serialVersionUID = 200L` en la clase e intentas deserializar el archivo generado anteriormente? Provoca deliberadamente el error y analiza la excepción resultante (`InvalidClassException`).

---

## Ejercicio 3: El problema de la cabecera repetida en `append` y `MiObjectOutputStream`

Si deseas añadir objetos a un archivo serializado existente abriendo el flujo con `new FileOutputStream("datos.obj", true)`, el constructor de `ObjectOutputStream` escribirá una segunda cabecera mágica en mitad del archivo, lo que provocará una excepción `StreamCorruptedException` al intentar leerlo.

1. Diseña la clase `MiObjectOutputStream` que herede de `ObjectOutputStream`:
   - Sobrescribe el método `protected void writeStreamHeader() throws IOException` para que llame a `reset()` en lugar de escribir los 4 bytes de cabecera (`super.writeStreamHeader()`).
2. Diseña un método factoría `public static ObjectOutputStream abrirParaEscritura(File f) throws IOException`:
   - Si el archivo no existe o está vacío (`f.length() == 0`), debe devolver una instancia de `ObjectOutputStream` estándar.
   - Si el archivo ya existe y contiene datos, debe devolver una instancia de tu `MiObjectOutputStream` envolviendo a `new FileOutputStream(f, true)`.
3. Escribe un programa que en una primera fase guarde dos objetos, cierre el stream, y en una segunda fase abra el archivo en modo *append* añadiendo un tercer objeto.
4. Lee secuencialmente todos los objetos del fichero con un único `ObjectInputStream` en un bucle `while (true)` controlado por `EOFException`, comprobando que los 3 objetos se leen sin errores.

---

## Ejercicio 4: Fichero de acceso aleatorio (`RandomAccessFile`) — CRUD de registros fijos

Diseña un sistema de almacenamiento de artículos de un almacén utilizando un archivo de acceso aleatorio `almacen.dat` donde cada registro tenga un tamaño fijo en bytes:

* `int id`: 4 bytes.
* `char[30] descripcion`: 30 caracteres Unicode (2 bytes por carácter = 60 bytes).
* `double precio`: 8 bytes.
* `int stock`: 4 bytes.
* `boolean activo`: 1 byte (`true` = registro activo, `false` = borrado lógicamente).
* **Tamaño total del registro (`TAMA_REGISTRO`):** `4 + 60 + 8 + 4 + 1 = 77 bytes`.

Implementa una clase `GestorAlmacenAleatorio` con los siguientes métodos:

1. `void insertarArticulo(int numReg, int id, String descripcion, double precio, int stock)`: posiciona el puntero en `numReg * TAMA_REGISTRO` y escribe los campos (ajustando la descripción a exactamente 30 caracteres).
2. `void consultarArticulo(int numReg)`: lee el registro en esa posición e imprime sus datos (o avisa si el registro está marcado como borrado o fuera del fichero).
3. `void modificarPrecioYStock(int numReg, double nuevoPrecio, int nuevoStock)`: modifica exclusivamente los campos de precio y stock sin reescribir el resto del registro.
4. `void borrarLogico(int numReg)`: marca el booleano `activo` a `false`.

---

## Ejercicio 5: Compactación de ficheros aleatorios (*Pack*)

Siguiendo con el sistema del ejercicio anterior, cuando un archivo con borrado lógico acumula muchas bajas, el tamaño físico del archivo sigue creciendo y los registros inactivos ocupan espacio innecesario.

1. Implementa en `GestorAlmacenAleatorio` el método `public void compactarArchivo()` que:
   - Abra el archivo actual `almacen.dat` en modo solo lectura (`"r"`).
   - Cree un archivo temporal `almacen_temp.dat` en modo `"rw"`.
   - Recorra secuencialmente todos los registros del archivo original de 77 en 77 bytes.
   - Escriba en el archivo temporal **únicamente aquellos registros cuyo campo `activo` sea `true`**.
   - Cierre ambos flujos.
   - Elimine el archivo original `almacen.dat` con `delete()` y renombre `almacen_temp.dat` a `almacen.dat`.
2. Muestra por consola el tamaño en bytes del archivo antes y después de la compactación.

---

## Ejercicio 6: Algoritmo de búsqueda de patrones en buffer sin librerías (Búsqueda en Pi)

Dispones de un archivo `pi-million.txt` que contiene el número `3.` seguido de los **primeros 1.000.000 de decimales del número Pi** en una o varias líneas continuas.

1. Implementa un programa que solicite al usuario una secuencia numérica (por ejemplo `"1415"`, `"9265"` o su fecha de nacimiento `"25121998"`).
2. El programa debe buscar dicha secuencia dentro del primer millón de decimales **leyendo el fichero secuencialmente a través de un búfer de caracteres (`char[] buffer = new char[8192]`)**.
3. **Restricción estricta:** Está **terminantemente prohibido** cargar todo el archivo en memoria (`readString` o similar) o utilizar métodos de búsqueda prefabricados como `String.indexOf` o expresiones regulares. Debes programar manualmente el algoritmo de coincidencia de caracteres gestionando la ventana deslizante entre bloques consecutivos de lectura.
4. Devuelve la **posición decimal exacta** (1-indexada) donde comienza la coincidencia o un mensaje indicando que no se encuentra en el primer millón de dígitos.

---

## Ejercicio 7: Contador estadístico de texto con `Map` (Líneas, Palabras y Top 10)

Escribe un analizador textual que procese un archivo de texto clásico (por ejemplo `quijote.txt`):

1. Utilizando `BufferedReader` (o `Scanner`), cuenta y muestra:
   - El número total de **líneas**.
   - El número total de **palabras** (separadas por espacios, signos de puntuación o tabuladores).
   - El número total de **caracteres** (incluyendo y excluyendo espacios en blanco).
2. Utiliza un `HashMap<String, Integer>` para contabilizar la frecuencia de aparición de cada palabra (normalizando a minúsculas y descartando signos ortográficos y palabras de menos de 3 letras).
3. Muestra por consola las **10 palabras más frecuentes** junto con su recuento, ordenadas de mayor a menor frecuencia.

---

## Ejercicio 8: Parseo y modificación de XML con DOM

Dado el archivo `empleados.xml`:

```xml
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
```

1. Utiliza `DocumentBuilderFactory` y `DocumentBuilder` para cargar y parsear el documento XML en memoria.
2. Recorre todos los nodos `<empleado>` y muestra los datos de los empleados pertenecientes al departamento `"Informatica"`.
3. Calcula y muestra el **sueldo medio** de los empleados de la empresa.
4. Aplica una subida salarial del 10 % a todos los empleados del departamento de `"Informatica"` modificando el valor textual de sus nodos `<sueldo>`.
5. Guarda el árbol XML modificado en un nuevo archivo `empleados_actualizado.xml` utilizando `TransformerFactory` y `Transformer` (configurando la propiedad `OutputKeys.INDENT` a `"yes"`).

---

## Ejercicio 9: Parseo de XML basado en eventos con SAX

Para ficheros XML de gran tamaño (cientos de megabytes), el parser DOM resulta inviable por agotamiento de memoria RAM.

1. Implementa un parser SAX (`SAXParserFactory` y `SAXParser`) que procese el mismo archivo `empleados.xml`.
2. Extiende la clase `DefaultHandler` implementando los métodos `startElement`, `characters` y `endElement`.
3. Durante el procesamiento, extrae el atributo `id` de cada empleado y el contenido de las etiquetas `<nombre>` y `<sueldo>`.
4. Al finalizar la lectura (`endDocument`), muestra por consola la suma total de las masas salariales de todos los empleados procesados.

---

## Ejercicio 10: Compresión y extracción de archivos ZIP (`ZipStream`)

1. **Compresión:** Implementa el método `public static void comprimirEnZip(List<File> archivos, File archivoZipDestino) throws IOException`:
   - Crea un `ZipOutputStream` envolviendo a un `FileOutputStream`.
   - Para cada archivo de la lista, crea una nueva entrada `new ZipEntry(archivo.getName())`, agrégala con `zos.putNextEntry(e)` y transfiere sus bytes mediante un búfer antes de cerrar la entrada con `zos.closeEntry()`.
2. **Descompresión:** Implementa el método `public static void descomprimirZip(File archivoZip, File carpetaDestino) throws IOException`:
   - Utiliza `ZipInputStream` para iterar sobre todas las entradas del archivo ZIP (`zis.getNextEntry()`).
   - Para cada entrada, crea el archivo correspondiente en la carpeta de destino y vuelca sus bytes con un `FileOutputStream`.
3. Comprueba el funcionamiento comprimiendo tres archivos de texto y descomprimiéndolos en una subcarpeta `extraidos/`.

---

## Ejercicio 11: Inspector de proyectos con Java NIO.2 (`Files.walk` y `Files.lines`)

Diseña una herramienta de auditoría de código para proyectos Java utilizando la moderna API **NIO.2**:

1. Solicita al usuario la ruta del directorio raíz de un proyecto (por ejemplo `src/`).
2. Utiliza **`Files.walk(Path raiz)`** para recorrer todo el árbol de directorios de manera recursiva:
   - Filtra únicamente los ficheros regulares con extensión `.java`.
   - Cuenta el **número total de clases/archivos Java**.
   - Encuentra el archivo Java más grande en tamaño de bytes.
3. Para cada archivo Java encontrado, utiliza **`Files.lines(Path)`** para calcular:
   - El número total de líneas físicas.
   - El número de **líneas de código efectivas** (descartando líneas en blanco y líneas que comiencen por `//` o `*`).
4. Muestra un informe estadístico resumido en consola.

---

## Ejercicio 12: Selector gráfico con `JFileChooser` y visor de propiedades

Diseña una utilidad de escritorio sencilla mediante Swing:

1. Muestra un diálogo `JFileChooser` con un filtro `FileNameExtensionFilter` que admita documentos de texto e imágenes (`txt`, `csv`, `png`, `jpg`, `pdf`).
2. Si el usuario selecciona un archivo y pulsa *Aceptar*:
   - Muestra en consola (o en un `JOptionPane`) las propiedades completas del archivo: nombre, ruta absoluta, tamaño formateado en KB o MB, si tiene permisos de lectura y escritura (`canRead`, `canWrite`) y su tipo MIME detectado mediante `Files.probeContentType(archivo.toPath())`.
3. Si el usuario pulsa *Cancelar*, muestra un mensaje informativo y finaliza la aplicación de forma limpia.
