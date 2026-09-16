---
title: Boletín U06 — Extras
description: Retos de CodeWars y AceptaElReto sobre colecciones y genéricos
---

# 🔥 Boletín U06 — Extras

> Busca en [CodeWars](https://www.codewars.com/) katas de nivel 6-5 kyu etiquetadas `Data Structures` o `Lists`. Aquí tienes dos retos propios.

## Reto 1: eliminar duplicados preservando el orden

Dado un `ArrayList<Integer>` que puede contener elementos repetidos, escribe un método que devuelva una **nueva** lista con los mismos elementos pero sin duplicados, **conservando el orden de la primera aparición** de cada uno.

:::tip[Pista]
Un `Set` no mantiene el orden de inserción de forma fiable (salvo `LinkedHashSet`)... pero puedes usar un `Set` solo para *comprobar* si ya has visto un elemento, mientras construyes el resultado en una `List` aparte.
:::

<details>
<summary>💡 Solución</summary>

```java
static List<Integer> sinDuplicados(List<Integer> lista) {
    Set<Integer> vistos = new HashSet<>();
    List<Integer> resultado = new ArrayList<>();
    for (int n : lista) {
        if (vistos.add(n)) {   // add() devuelve false si ya estaba
            resultado.add(n);
        }
    }
    return resultado;
}
```

`Set.add()` devuelve `true` solo la primera vez que se añade un valor — un truco muy usado para combinar "comprobar si ya existe" y "marcarlo como visto" en una sola llamada.
</details>

## Reto 2: la palabra más frecuente

Dado un texto largo (un `String` con varias frases), escribe un método que devuelva la palabra que más veces aparece (ignorando mayúsculas/minúsculas y signos de puntuación básicos).

<details>
<summary>💡 Solución</summary>

```java
static String palabraMasFrecuente(String texto) {
    String[] palabras = texto.toLowerCase().replaceAll("[^a-záéíóúñ ]", "").split("\\s+");
    Map<String, Integer> conteo = new HashMap<>();

    for (String palabra : palabras) {
        if (palabra.isEmpty()) continue;
        conteo.merge(palabra, 1, Integer::sum);
    }

    String mejor = null;
    int max = 0;
    for (Map.Entry<String, Integer> e : conteo.entrySet()) {
        if (e.getValue() > max) {
            max = e.getValue();
            mejor = e.getKey();
        }
    }
    return mejor;
}
```

`map.merge(clave, 1, Integer::sum)` es un atajo para "si la clave no existe, ponla a 1; si ya existe, súmale 1 a lo que había" — evita el `if (map.containsKey(...))` manual de siempre.
</details>
