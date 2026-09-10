---
title: 🧠 Boletín 01 Extras — Retos de Pensamiento Computacional
description: Retos algorítmicos avanzados, acertijos de lógica computacional y preparación para plataformas de programación competitiva.
---

En las primeras unidades aún no disponemos de sintaxis Java suficiente para enviar soluciones automáticas a jueces en línea como CodeWars o Acepta el Reto (lo cual iniciaremos a partir de la **Unidad 3**). Sin embargo, este boletín presenta **retos clásicos de lógica algorítmica y optimización** que entrenarán tu agilidad mental para la resolución de problemas en competición.

---

### Reto 1: La Conjetura de Collatz (Secuencia del Granizo)

#### Enunciado
El matemático Lothar Collatz propuso en 1937 la siguiente regla para cualquier número entero positivo NNN:
* Si el número es **par**, se divide entre 2 (NN \leftarrow N / 2N).
* Si el número es **impar**, se multiplica por 3 y se le suma 1 (NN \leftarrow 3 \times N + 1N).
* El proceso se repite con el nuevo valor obtenido hasta que el número llegue irremediablemente al valor **1**.

Diseña un algoritmo en pseudocódigo que:
1. Pida un número entero positivo inicial NNN.
2. Imprima toda la secuencia de números generados hasta alcanzar el 1.
3. Cuente y muestre la cantidad total de pasos necesarios (longitud de la órbita) y el valor pico más alto alcanzado durante la trayectoria.

:::tip[💡 Pistas para la resolución]
* Utiliza un bucle `Mientras N ≠ 1 Hacer`.
* Mantén una variable `pasos ← 0` y una variable `picoMaximo ← N`.
* Actualiza `picoMaximo` en cada iteración comparando si el nuevo NNN supera el récord anterior.
:::
---

### Reto 2: El Juego de Adivinar el Número (Búsqueda Binaria)

#### Enunciado
El ordenador genera un número secreto entero aleatorio entre 1 y 100 (en PSeInt puedes usar `numeroSecreto ← Azar(100) + 1`). El usuario debe intentar adivinarlo en el menor número posible de intentos.
* En cada intento, el usuario introduce un número.
* Si el número del usuario es menor que el secreto, el programa avisa: *"El número secreto es MAYOR"*.
* Si es mayor, avisa: *"El número secreto es MENOR"*.
* Si acierta, el programa le felicita y le indica en cuántos intentos lo ha logrado.

**Pregunta algorítmica de análisis:**
¿Cuál es el número máximo de intentos que un jugador necesitaría para adivinar **siempre** el número si juega de manera óptima utilizando la técnica de **búsqueda binaria** (dividir el intervalo a la mitad en cada paso)?

:::tip[💡 Pistas para la resolución]
* Como el rango es de 1 a 100, la búsqueda binaria evalúa N100 / 2 = 50N, luego N25N, N12N, etc.
* ¿Qué potencia de 2 supera a 100? N2^6 = 64 < 100N, mientras que N2^7 = 128 \geq 100N. ¡El número máximo de intentos necesarios es siempre N\leq 7N!
:::
---

### Reto 3: Algoritmo Voraz (Greedy) para el Cambio de Monedas

#### Enunciado
Una máquina expendedora debe devolver el cambio a un cliente tras una compra. Se dispone de una cantidad infinita de monedas de curso legal de los siguientes valores (en céntimos de euro):
* `200` (2 €), `100` (1 €), `50`, `20`, `10`, `5`, `2` y `1`.

Dado un importe a devolver en céntimos (por ejemplo, `387` céntimos):
Diseña un algoritmo que devuelva el cambio utilizando el **mínimo número total de monedas posible**.

:::tip[💡 Pistas para la resolución]
* Esta estrategia se conoce en algorítmica como **enfoque voraz (greedy)**: en cada paso, selecciona la moneda de mayor valor que no sobrepase la cantidad pendiente de devolución.
* Puedes resolverlo iterando secuencialmente desde la moneda más grande (200) hacia la más pequeña (1), calculando cuántas monedas caben mediante división entera (`cambio / valorMoneda`) y actualizando el resto mediante módulo (`cambio MOD valorMoneda`).
:::
---

### Reto 4: El Problema Clásico del Cruce del Río

#### Enunciado
Un granjero debe transportar a través de un río a un **lobo**, una **cabra** y una **lechuga (col)**. 
Dispone de una barca en la que solo caben él y **uno** de los tres elementos en cada viaje.
* Si el granjero deja solos al lobo y a la cabra en cualquier orilla, el lobo se comerá a la cabra.
* Si deja solas a la cabra y a la lechuga, la cabra se comerá la lechuga.
* El lobo no come vegetales.

Escribe la secuencia exacta y ordenada de viajes (ida y vuelta) que el granjero debe realizar para cruzar los tres elementos a la otra orilla sanos y salvos.

:::tip[💡 Pistas para la resolución]
* Piensa en qué elemento es el único que puede quedarse temporalmente solo con cualquiera de los otros dos sin peligro.
* A veces, para avanzar en un algoritmo, es necesario realizar una operación aparentemente contraintuitiva: ¡traerse de vuelta en la barca a un pasajero que ya habías cruzado!
:::