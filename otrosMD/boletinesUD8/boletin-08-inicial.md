---
title: Boletín U08 — Inicial
description: Ejercicios propuestos de nivel inicial sobre ficheros y streams
---

# 📝 Boletín U08 — Inicial

1. Escribe un programa que cree un objeto `File` apuntando a una carpeta de tu elección, y muestre si existe, si es un fichero o una carpeta, y (si es carpeta) cuántos elementos contiene.

2. Escribe un programa que use `FileWriter` para crear un fichero `notas.txt` con 5 líneas de texto de tu elección.

3. Escribe un programa que lea el fichero `notas.txt` del ejercicio anterior línea a línea con `BufferedReader` y muestre cada línea numerada (`1: ...`, `2: ...`).

4. Modifica el ejercicio 2 para que, en vez de sobrescribir el fichero, añada una nueva línea al final cada vez que se ejecuta el programa (modo *append*).

5. Escribe un programa que use `File.listFiles()` para listar todos los ficheros de una carpeta y mostrar, para cada uno, su nombre y su tamaño en bytes.

6. Explica con tus propias palabras qué diferencia hay entre `FileReader` y `BufferedReader`, y por qué normalmente se usan juntos.

7. Escribe un programa que use `Files.readAllLines()` (de la API NIO2) para leer todo el contenido de un fichero de golpe y contar cuántas líneas tiene.

8. Explica qué error concreto comete este código y corrígelo: `writer.write(2024);` cuando la intención era escribir el texto "2024" en el fichero.
