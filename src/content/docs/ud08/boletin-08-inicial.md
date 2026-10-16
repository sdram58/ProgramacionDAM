---
title: "Boletín 8 — Nivel Inicial"
description: "Ejercicios propuestos de nivel inicial sobre ficheros y streams en Java: inspección de rutas con File, creación y renombrado de carpetas, borrado recursivo, lectura y escritura de texto con Scanner, BufferedReader y PrintWriter, copia binaria y tipos primitivos con DataStreams."
slug: ud08/boletin-08-inicial
draft: true
prev: false
next: false
---

En este boletín inicial practicarás los conceptos fundamentales de la **gestión de ficheros y streams en Java**: manipulación de rutas relativas y absolutas con `java.io.File`, comprobaciones de estado (`exists`, `isFile`, `isDirectory`), consulta de metadatos (`length`, `lastModified`), creación y renombrado de carpetas, borrado recursivo, lectura y escritura de texto estructurado con `Scanner`, `BufferedReader`, `FileWriter` y `PrintWriter`, transferencia binaria con búferes y persistencia de tipos primitivos con `DataOutputStream` y `DataInputStream`.

:::tip[Entorno de trabajo en IntelliJ IDEA]
Crea un paquete `ud08.inicial` en tu proyecto de IntelliJ IDEA. Para los ejercicios de lectura crea una carpeta `datos` en la raíz de tu proyecto e introduce ficheros de prueba sencillos. Recuerda utilizar siempre la sentencia **`try-with-resources`** para garantizar el cierre seguro de todos los flujos abiertos.
:::

---

## Ejercicio 1: Inspector de rutas del sistema (`muestraInfoRuta` v1)

Implementa un programa interactivo por consola que solicite al usuario introducir rutas del sistema de archivos (por ejemplo `"C:/Windows"`, `"src"` o `"datos.txt"`) y muestre información detallada sobre dicha ruta. El proceso se repetirá en bucle hasta que el usuario pulse la tecla Intro introduciendo una cadena vacía.

1. Implementa una función auxiliar `public static void muestraInfoRuta(File ruta) throws FileNotFoundException` que realice las siguientes comprobaciones:
   - Si la ruta no existe físicamente en el disco, lanzará una excepción `FileNotFoundException` indicando la ruta errónea.
   - Si la ruta corresponde a un **fichero regular**, mostrará por consola su nombre precedido de la etiqueta `[A] ` (por ejemplo: `[A] documento.txt`).
   - Si la ruta corresponde a un **directorio**, mostrará por consola la lista de todos sus elementos contenidos. Deberán mostrarse **primero los directorios** con la etiqueta `[*] ` y a continuación los archivos con la etiqueta `[A] `.
2. Captura y gestiona adecuadamente las excepciones en el método `main`, evitando que el programa aborte ante rutas inválidas.

---

## Ejercicio 2: Inspector detallado y ordenado (`muestraInfoRuta` v2)

Evoluciona el inspector del ejercicio anterior añadiendo ordenación y metadatos detallados:

1. Modifica la función para que admita un segundo argumento booleano: `public static void muestraInfoRuta(File ruta, boolean info) throws FileNotFoundException`.
2. En caso de directorios, muestra los elementos ordenados alfabéticamente (puedes utilizar `Arrays.sort(elementos, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER))`), mostrando primero los directorios ordenados de la A a la Z y después los archivos ordenados de la A a la Z.
3. Si el parámetro `info` es `true`, muestra junto a cada archivo o carpeta su **tamaño en bytes** (`length()`) y la **fecha y hora de su última modificación** formateada en formato legible (`Instant.ofEpochMilli(f.lastModified())` o `LocalDateTime`). Si `info` es `false`, muestra únicamente los nombres como en el ejercicio 1.

---

## Ejercicio 3: Reorganización masiva y eliminación de extensiones

Dado un directorio de trabajo ficticio con la siguiente estructura:

```text
proyecto/
├── Documentos/
├── Fotografias/
│   ├── vacaciones.jpg
│   ├── cumpleanos.png
│   └── retrato.jpeg
└── Libros/
    ├── quijote.txt
    └── odisea.txt
```

Implementa un programa en Java que realice automáticamente las siguientes transformaciones utilizando `File.renameTo(File destino)`:

1. Cambia el nombre de la carpeta `Documentos` a `DOCS`, el de `Fotografias` a `FOTOS` y el de `Libros` a `LECTURAS`.
2. Recorre todos los archivos contenidos dentro de las nuevas carpetas `FOTOS` y `LECTURAS` y **elimina su extensión**, de modo que `vacaciones.jpg` pase a llamarse `vacaciones` y `quijote.txt` pase a llamarse `quijote`.
3. Muestra por consola un mensaje confirmando cada cambio de nombre efectuado o avisando si alguna operación no se pudo completar.

---

## Ejercicio 4: Creación de estructura y carpetas del alfabeto

Diseña un programa que cree de manera desatendida la siguiente jerarquía de carpetas en el directorio del proyecto:

1. Crea dentro de una carpeta base `Almacen/` dos subdirectorios: `Mis Cosas` y `Alfabeto` (utiliza `mkdirs()` para asegurar la creación de rutas intermedias si no existen).
2. Mueve las carpetas `FOTOS` y `LECTURAS` del ejercicio anterior dentro de `Almacen/Mis Cosas/`.
3. Utilizando un bucle `for` que recorra los códigos numéricos ASCII de las letras mayúsculas (desde `65` `'A'` hasta `90` `'Z'`), crea dentro de `Almacen/Alfabeto/` **26 carpetas individuales** nombradas con cada una de las letras del abecedario (`A/`, `B/`, `C/`, ..., `Z/`).
4. Comprueba al finalizar que se han creado exactamente 26 carpetas dentro de `Alfabeto/`.

---

## Ejercicio 5: Borrado recursivo completo (`borraTodo`)

Como se vio en la teoría, `File.delete()` devuelve `false` si se intenta borrar un directorio que contenga archivos o subcarpetas en su interior.

1. Implementa una función recursiva `public static boolean borraTodo(File f) throws FileNotFoundException` que:
   - Lance `FileNotFoundException` si el fichero o directorio recibido no existe.
   - Si es un archivo regular, lo elimine directamente con `f.delete()`.
   - Si es un directorio, recorra recursivamente todos sus hijos borrando primero cada uno de ellos y, una vez que el directorio quede completamente vacío, lo elimine.
   - Devuelva `true` si la eliminación fue exitosa o `false` en caso contrario.
2. Pon a prueba tu función borrando la jerarquía completa creada en los ejercicios anteriores (`Almacen/`).

---

## Ejercicio 6: Estadísticas de un fichero de números enteros

Crea un archivo de texto llamado `numeros.txt` que contenga una cantidad indeterminada de números enteros separados por espacios en blanco o saltos de línea (por ejemplo: `12 45 -8 90 23 45 102 -3 0 14`).

Escribe un programa que lea el fichero utilizando la clase `Scanner`:
1. Calcule e imprima por pantalla:
   - El número total de enteros leídos.
   - El valor **mínimo** y el valor **máximo**.
   - La **suma total** acumulada.
   - La **media aritmética** con dos decimales de precisión.
2. Controla mediante `try-catch` las posibles excepciones `FileNotFoundException` e `InputMismatchException` (si el archivo contiene texto no numérico).

---

## Ejercicio 7: Procesamiento de notas de alumnos con longitud variable

El archivo `alumnos_notas.txt` contiene en cada línea el nombre y apellido de un alumno seguido de una cantidad variable de calificaciones enteras correspondientes a las asignaturas cursadas:

```text
Juan Perez 8 7 9 10 6
Marta Gomez 9 10 10 8 9 9
Carlos Lopez 4 5 6
Lucia Fernandez 7 8 8 9 7 8 10
```

Implementa un programa en Java que:
1. Abra el fichero mediante `FileReader` y `BufferedReader`.
2. Lea el fichero línea a línea con `readLine()`.
3. Separe los elementos de cada línea mediante `linea.split(" ")`. Las posiciones `0` y `1` corresponden al nombre y apellido; las posiciones de la `2` en adelante corresponden a las notas.
4. Calcule la **nota media** de cada estudiante y guarde los resultados en una colección de objetos `AlumnoNota(String nombreCompleto, double notaMedia)`.
5. Muestre por pantalla el listado de alumnos **ordenado de mayor a menor nota media** con el formato:
   `[9.17] Marta Gomez`.

---

## Ejercicio 8: Ordenación alfabética de ficheros de texto

Implementa una utilidad de consola que solicite al usuario:
- El nombre o ruta de un archivo de texto de entrada (por ejemplo `nombres.txt`).
- El nombre o ruta del archivo de texto de salida (por ejemplo `nombres_ordenados.txt`).

El programa deberá:
1. Leer todas las líneas del archivo de entrada mediante `BufferedReader` (o `Scanner`) y almacenarlas en una `List<String>`.
2. Ordenar las líneas alfabéticamente sin distinguir mayúsculas de minúsculas (`String.CASE_INSENSITIVE_ORDER`).
3. Escribir las líneas ordenadas en el fichero de destino utilizando `PrintWriter` y `BufferedWriter`.
4. Mostrar por consola el número total de líneas procesadas y transferidas.

---

## Ejercicio 9: Generador aleatorio de identidades

Dispones de dos archivos de texto:
- `usa_nombres.txt` (con un nombre de pila por línea: *John, Mary, James, Patricia, Robert...*).
- `usa_apellidos.txt` (con un apellido por línea: *Smith, Johnson, Williams, Brown, Jones...*).

Escribe un programa que:
1. Lea ambos archivos en memoria cargándolos en sendos `ArrayList<String>`.
2. Solicite al usuario cuántas identidades completas desea generar (por ejemplo `50`) y el nombre del archivo de salida (por ejemplo `identidades_generadas.txt`).
3. Combine aleatoriamente un nombre y un apellido en cada iteración y los escriba en el archivo de salida con un número de orden:
   `001: Mary Williams`
   `002: John Brown`
4. Muestra un mensaje al finalizar indicando la ruta absoluta del fichero generado.

---

## Ejercicio 10: Distribución de diccionario alfabético

Dado un archivo de texto `diccionario.txt` con cientos de palabras en español (una por línea):

1. Crea automáticamente una carpeta llamada `Diccionario/`.
2. Crea dentro de dicha carpeta un archivo independiente para cada letra del abecedario (`A.txt`, `B.txt`, `C.txt`... `Z.txt`).
3. Lee secuencialmente el archivo `diccionario.txt` y clasifica cada palabra escribiéndola en el archivo de texto correspondiente a su primera letra (ignorando mayúsculas y acentos).
4. Asegúrate de cerrar adecuadamente todos los flujos de escritura creados.

---

## Ejercicio 11: Benchmark de copia binaria (Byte a byte vs. Búfer)

Implementa una aplicación que compare experimentalmente el rendimiento de dos estrategias de copia sobre un archivo binario mediano (por ejemplo, un archivo de audio, vídeo o imagen de varios megabytes):

1. **Estrategia 1 (Sin búfer):** Copiar el archivo leyendo y escribiendo byte a byte con `fis.read()` y `fos.write(b)`.
2. **Estrategia 2 (Con búfer de bloques):** Copiar el mismo archivo utilizando un array de bytes intermedio de 4 KB (`byte[] buffer = new byte[4096]`) mediante `fis.read(buffer)` y `fos.write(buffer, 0, bytesLeidos)`.
3. Mide y muestra por consola el tiempo empleado en milisegundos por cada estrategia y calcula cuántas veces más rápida es la copia mediante búfer.

---

## Ejercicio 12: Registro binario de mediciones climáticas (`DataStream`)

Un equipo meteorológico registra periódicamente muestras climáticas en un fichero binario estructurado `estacion.dat`:

1. Diseña un `record Medicion(int idEstacion, String provincia, double temperatura, double humedad, boolean alertaViento)`.
2. Implementa un método `public static void guardarMediciones(File f, List<Medicion> lista)` que utilice `DataOutputStream` envuelto en `BufferedOutputStream` y `FileOutputStream` para escribir de forma compacta y tipada cada registro con `writeInt`, `writeUTF`, `writeDouble` y `writeBoolean`.
3. Implementa un método `public static List<Medicion> cargarMediciones(File f)` que lea secuencialmente todos los registros con `DataInputStream` hasta alcanzar el final del fichero (`EOFException` o control con `dis.available()`) y los devuelva en una lista.
4. Muestra en el `main` los registros recuperados formateados por consola.
