---
title: Boletín U08 — Extras
description: Retos de CodeWars y AceptaElReto sobre procesamiento de ficheros de texto
---

# 🔥 Boletín U08 — Extras

> Busca en [CodeWars](https://www.codewars.com/) katas de procesamiento de texto (`String parsing`) y resuélvelos leyendo los datos de entrada desde un fichero en vez de una variable fija en el código. Aquí tienes dos retos propios.

## Reto 1: fusionar dos ficheros ordenados

Tienes dos ficheros de texto, cada uno con una lista de números enteros (uno por línea) **ya ordenados de menor a mayor**. Escribe un programa que genere un tercer fichero con todos los números de ambos, fusionados en un único listado también ordenado — sin cargar ambos ficheros enteros en memoria y volver a ordenar desde cero (aprovecha que ya vienen ordenados).

:::tip[Pista]
Es el mismo algoritmo que la fase de "mezcla" del *merge sort*: lee el primer número de cada fichero, escribe el menor de los dos, avanza solo en el fichero del que escribiste, y repite hasta vaciar ambos.
:::

<details>
<summary>💡 Solución</summary>

```java
try (BufferedReader br1 = new BufferedReader(new FileReader("numeros1.txt"));
     BufferedReader br2 = new BufferedReader(new FileReader("numeros2.txt"));
     PrintWriter salida = new PrintWriter(new FileWriter("fusionado.txt"))) {

    String linea1 = br1.readLine();
    String linea2 = br2.readLine();

    while (linea1 != null && linea2 != null) {
        int n1 = Integer.parseInt(linea1);
        int n2 = Integer.parseInt(linea2);
        if (n1 <= n2) {
            salida.println(n1);
            linea1 = br1.readLine();
        } else {
            salida.println(n2);
            linea2 = br2.readLine();
        }
    }
    // Vuelca lo que quede del fichero que no se ha terminado
    while (linea1 != null) { salida.println(linea1); linea1 = br1.readLine(); }
    while (linea2 != null) { salida.println(linea2); linea2 = br2.readLine(); }
}
```
</details>

## Reto 2: índice de palabras de un fichero

Dado un fichero de texto, genera un "índice" que muestre, para cada palabra distinta (en minúsculas, sin signos de puntuación), en qué número de línea aparece al menos una vez — ordenado alfabéticamente.

<details>
<summary>💡 Solución</summary>

```java
Map<String, TreeSet<Integer>> indice = new TreeMap<>();

try (BufferedReader br = new BufferedReader(new FileReader("texto.txt"))) {
    String linea;
    int numeroLinea = 1;
    while ((linea = br.readLine()) != null) {
        String[] palabras = linea.toLowerCase().replaceAll("[^a-záéíóúñ ]", "").split("\\s+");
        for (String palabra : palabras) {
            if (palabra.isEmpty()) continue;
            indice.computeIfAbsent(palabra, k -> new TreeSet<>()).add(numeroLinea);
        }
        numeroLinea++;
    }
}

for (Map.Entry<String, TreeSet<Integer>> entrada : indice.entrySet()) {
    System.out.println(entrada.getKey() + ": líneas " + entrada.getValue());
}
```

Usar un `TreeMap` para el índice y un `TreeSet` para las líneas de cada palabra da el resultado ya ordenado (alfabéticamente las palabras, numéricamente las líneas) sin necesidad de ordenar nada al final.
</details>
