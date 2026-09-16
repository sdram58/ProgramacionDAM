---
title: Boletín U04 — Extras
description: Retos de CodeWars y AceptaElReto sobre diseño de clases
---

# 🔥 Boletín U04 — Extras

> Busca en [CodeWars](https://www.codewars.com/) katas de nivel 7-6 kyu etiquetadas `Object-oriented Programming`, y en [AceptaElReto](https://www.acepta.com/reto/) la categoría de diseño de clases. Aquí tienes dos retos propios en el mismo espíritu.

## Reto 1: la clase Fraccion

Diseña una clase `Fraccion` (numerador y denominador, ambos `int`) con:
- Un constructor que **simplifique automáticamente** la fracción al crearla (por ejemplo, `Fraccion(4, 8)` debe quedar guardada internamente como `1/2`).
- Un `toString()` que la muestre como `"numerador/denominador"`.
- Un método `sumar(Fraccion otra)` que devuelva una **nueva** `Fraccion` con el resultado de la suma (también simplificada).

:::tip[Pista]
Para simplificar una fracción necesitas el máximo común divisor (MCD) de numerador y denominador. Puedes calcularlo con el algoritmo de Euclides, de forma recursiva: `mcd(a, 0) = a`, y si no, `mcd(a, b) = mcd(b, a % b)`.
:::

<details>
<summary>💡 Solución</summary>

```java
public class Fraccion {
    private int numerador;
    private int denominador;

    public Fraccion(int numerador, int denominador) {
        int mcd = mcd(Math.abs(numerador), Math.abs(denominador));
        this.numerador = numerador / mcd;
        this.denominador = denominador / mcd;
    }

    private static int mcd(int a, int b) {
        return b == 0 ? a : mcd(b, a % b);
    }

    public Fraccion sumar(Fraccion otra) {
        int nuevoNumerador = this.numerador * otra.denominador + otra.numerador * this.denominador;
        int nuevoDenominador = this.denominador * otra.denominador;
        return new Fraccion(nuevoNumerador, nuevoDenominador);
    }

    @Override
    public String toString() {
        return numerador + "/" + denominador;
    }
}
```
</details>

## Reto 2: el mazo de cartas

Diseña una clase `Carta` (con atributos `palo` y `valor`) y una clase `Mazo` que contenga un array de 40 objetos `Carta` (la baraja española: palos "Oros", "Copas", "Espadas", "Bastos"; valores del 1 al 10, sin el 8 ni el 9). El `Mazo` debe tener un método `barajar()` que reordene el array al azar, y un método `repartir(int n)` que devuelva las primeras `n` cartas del mazo.

:::tip[Pista para barajar]
Un algoritmo sencillo: recorre el array de atrás hacia adelante, y en cada posición intercambia la carta actual con una carta en una posición aleatoria anterior (o igual). Se conoce como el algoritmo de Fisher-Yates.
:::

<details>
<summary>💡 Solución</summary>

```java
public class Carta {
    private String palo;
    private int valor;

    public Carta(String palo, int valor) {
        this.palo = palo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}

public class Mazo {
    private Carta[] cartas;

    public Mazo() {
        String[] palos = {"Oros", "Copas", "Espadas", "Bastos"};
        cartas = new Carta[40];
        int i = 0;
        for (String palo : palos) {
            for (int valor = 1; valor <= 10; valor++) {
                if (valor == 8 || valor == 9) continue;
                cartas[i++] = new Carta(palo, valor);
            }
        }
    }

    public void barajar() {
        for (int i = cartas.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Carta temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }
    }

    public Carta[] repartir(int n) {
        Carta[] mano = new Carta[n];
        for (int i = 0; i < n; i++) {
            mano[i] = cartas[i];
        }
        return mano;
    }
}
```
</details>
