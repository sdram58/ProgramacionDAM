---
title: Boletín U07 — Extras
description: Retos de CodeWars y AceptaElReto resueltos con la API Stream
---

# 🔥 Boletín U07 — Extras

> Busca en [CodeWars](https://www.codewars.com/) katas ya resueltos en unidades anteriores con bucles, y esta vez resuélvelos usando **solo streams**, sin ningún `for`/`while` explícito — es un ejercicio excelente para interiorizar el enfoque declarativo. Aquí tienes dos retos propios.

## Reto 1: anagrama con streams

Escribe un método `boolean sonAnagramas(String a, String b)` que compruebe si dos palabras son anagramas (contienen exactamente las mismas letras, en cualquier orden), usando streams para ordenar los caracteres de cada palabra y comparar el resultado — sin bucles explícitos.

:::tip[Pista]
Puedes convertir un `String` en un `Stream<Character>` así: `texto.chars().mapToObj(c -> (char) c)`. Ordénalo con `.sorted()` y conviértelo de vuelta a `String` con `.collect(...)` y `StringBuilder`, o compara directamente los arrays de caracteres ordenados.
:::

<details>
<summary>💡 Solución</summary>

```java
static boolean sonAnagramas(String a, String b) {
    String ordenadaA = a.toLowerCase().chars()
        .sorted()
        .mapToObj(c -> String.valueOf((char) c))
        .collect(Collectors.joining());

    String ordenadaB = b.toLowerCase().chars()
        .sorted()
        .mapToObj(c -> String.valueOf((char) c))
        .collect(Collectors.joining());

    return ordenadaA.equals(ordenadaB);
}
```
</details>

## Reto 2: informe de ventas con Streams

Dada una lista de objetos `Venta` (producto, importe, mes), genera un `Map<String, Double>` con el total facturado por producto, pero **solo** contando las ventas de los últimos 6 meses del año (julio a diciembre) — todo en un único pipeline de stream, sin variables intermedias de tipo lista.

<details>
<summary>💡 Solución</summary>

```java
class Venta {
    String producto;
    double importe;
    int mes;   // 1-12
    Venta(String producto, double importe, int mes) {
        this.producto = producto; this.importe = importe; this.mes = mes;
    }
}

static Map<String, Double> totalSegundoSemestre(List<Venta> ventas) {
    return ventas.stream()
        .filter(v -> v.mes >= 7)
        .collect(Collectors.groupingBy(
            v -> v.producto,
            Collectors.summingDouble(v -> v.importe)
        ));
}
```

`Collectors.groupingBy` con un segundo `Collector` (aquí, `summingDouble`) es un patrón muy potente: en vez de agrupar en listas y sumar después a mano, el propio colector hace la suma dentro de cada grupo en un solo paso.
</details>
