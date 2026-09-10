---
title: Boletín U01 — Extras
description: Retos de pensamiento algorítmico puro, sin necesitar Java todavía
---

# 🔥 Boletín U01 — Extras

> Los retos de CodeWars y AceptaElReto necesitan tipos, `Scanner` y estructuras de control en Java real, así que cogen ritmo de verdad a partir de la U03. Mientras tanto, estos retos clásicos ponen a prueba tu pensamiento algorítmico puro — sin escribir ni una línea de código.

## Reto 1: los tres interruptores

Estás en una habitación con tres interruptores. Uno de ellos enciende una bombilla que está en otra habitación, a la que no puedes ver desde donde estás. Puedes mover los interruptores todo lo que quieras, pero solo puedes entrar **una vez** a la habitación de la bombilla para comprobar cuál es. ¿Cómo averiguas qué interruptor es el correcto?

:::tip[Pista]
Una bombilla encendida un rato también se calienta. No tienes que fiarte solo de tus ojos.
:::

<details>
<summary>💡 Solución</summary>

Enciende el interruptor 1 y espera unos minutos. Apágalo y enciende el interruptor 2. Entra en la habitación: si la bombilla está encendida, es el interruptor 2; si está apagada pero **caliente**, es el interruptor 1; si está apagada y fría, es el interruptor 3.
</details>

## Reto 2: pesar con una balanza de dos platos

Tienes 9 monedas idénticas a simple vista, pero una de ellas pesa un poco menos que las demás. Tienes una balanza de dos platos (sin números, solo dice qué lado pesa más) y puedes usarla como máximo **2 veces**. ¿Cómo encuentras la moneda falsa?

<details>
<summary>💡 Solución</summary>

Divide las 9 monedas en tres grupos de 3. Pesa el grupo A contra el B:
- Si pesan igual, la moneda falsa está en el grupo C.
- Si no pesan igual, está en el grupo más ligero.

Ya tienes un grupo de 3 sospechosas. Pesa dos de esas tres monedas entre sí: si pesan igual, la falsa es la tercera; si no, es la más ligera de las dos.
</details>

## Reto 3: el algoritmo de la torre de Hanói (razonamiento, no código)

Tienes 3 varillas y varios discos de distinto tamaño apilados en la primera varilla, de mayor a menor. El objetivo es mover toda la torre a la tercera varilla, moviendo un disco cada vez y sin poner nunca un disco más grande encima de uno más pequeño.

**Reto:** describe, en pseudocódigo con subalgoritmos, la estrategia general para mover `n` discos de una varilla `origen` a una varilla `destino` usando una varilla `auxiliar` (pista: piensa en el problema como "mover n-1 discos" + "mover 1 disco" + "mover n-1 discos" otra vez).

<details>
<summary>💡 Solución</summary>

```
SubAlgoritmo Hanoi(n, origen, destino, auxiliar)
    Si (n == 1) Entonces
        Escribir "Mueve el disco 1 de ", origen, " a ", destino
    Sino
        Hanoi(n - 1, origen, auxiliar, destino)
        Escribir "Mueve el disco ", n, " de ", origen, " a ", destino
        Hanoi(n - 1, auxiliar, destino, origen)
    FinSi
FinSubAlgoritmo
```

Es un ejemplo clásico de que algunos problemas se piensan de forma mucho más natural en términos **recursivos**: "mover n discos" se define en función de "mover n-1 discos", hasta llegar al caso base (mover 1 solo disco).
</details>
