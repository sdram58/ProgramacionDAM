---
title: Boletín U08 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre ficheros y streams
---

# 📝 Boletín U08 — Intermedio

1. Diseña una clase `Contacto` (nombre, teléfono) que implemente `Serializable`. Escribe un programa que serialice una lista de al menos 3 contactos en un fichero `contactos.dat`, y otro (o el mismo, en dos fases) que los lea de vuelta y los muestre.

2. Escribe un programa que copie el contenido de un fichero de texto a otro nuevo, pero **invirtiendo el orden de las líneas** (la última línea del original pasa a ser la primera del nuevo).

3. Escribe un programa que lea un fichero de texto con una lista de números (uno por línea) y escriba en otro fichero solo los que sean pares, además de mostrar por consola cuántos ha escrito.

4. Usando la API NIO2 (`Files`, `Path`), escribe un programa que recorra todos los ficheros de una carpeta y muestre solo los que tengan una extensión concreta (por ejemplo, `.txt`).

5. Escribe un programa que lea un fichero CSV sencillo (líneas con formato `nombre,edad`, sin librerías externas) y construya una `List<Persona>` a partir de su contenido, usando `String.split(",")`.

6. Escribe un programa que, dado un fichero XML sencillo con varias etiquetas `<libro titulo="..." autor="..."/>`, use `DocumentBuilder` para leerlo y mostrar por consola el título y autor de cada libro.

7. Añade manejo de errores robusto al ejercicio 1: si el fichero `contactos.dat` no existe todavía al intentar leerlo, el programa debe mostrar un mensaje amigable en vez de un `printStackTrace()` crudo.

8. Compara, para un fichero de 100.000 líneas, el tiempo que tarda en leerse completo con `Files.readAllLines()` frente a leerlo línea a línea con `BufferedReader` sin acumular nada en memoria. ¿Cuándo usarías cada enfoque?
