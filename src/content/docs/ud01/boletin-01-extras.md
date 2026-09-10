---
title: 🧠 Boletín 01 Extras — Retos de Pensamiento Computacional
description: Retos algorítmicos avanzados, acertijos de lógica computacional y preparación para plataformas de programación competitiva.
next: false
---

En las primeras unidades aún no disponemos de sintaxis Java suficiente para enviar soluciones automáticas a jueces en línea como CodeWars o Acepta el Reto (lo cual iniciaremos a partir de la **Unidad 3**). Sin embargo, este boletín presenta **retos clásicos de lógica algorítmica y optimización** que entrenarán tu agilidad mental para la resolución de problemas en competición.

---

### Reto 1: La Conjetura de Collatz (Secuencia del Granizo)

#### Enunciado
El matemático Lothar Collatz propuso en 1937 la siguiente regla para cualquier número entero positivo `N`:
* Si el número es **par**, se divide entre 2 (`N ← N / 2`).
* Si el número es **impar**, se multiplica por 3 y se le suma 1 (`N ← 3 × N + 1`).
* El proceso se repite con el nuevo valor obtenido hasta que el número llegue irremediablemente al valor **1**.

Diseña un algoritmo en pseudocódigo que:
1. Pida un número entero positivo inicial `N`.
2. Imprima toda la secuencia de números generados hasta alcanzar el 1.
3. Cuente y muestre la cantidad total de pasos necesarios (longitud de la órbita) y el valor pico más alto alcanzado durante la trayectoria.

:::tip[💡 Pistas para la resolución]
* Utiliza un bucle `Mientras N ≠ 1 Hacer`.
* Mantén una variable `pasos ← 0` y una variable `picoMaximo ← N`.
* Actualiza `picoMaximo` en cada iteración comparando si el nuevo `N` supera el récord anterior.
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
* Como el rango es de 1 a 100, la búsqueda binaria evalúa `100 / 2 = 50`, luego `25`, `12`, etc.
* ¿Qué potencia de 2 supera a 100? `2⁶ = 64 < 100`, mientras que `2⁷ = 128 ≥ 100`. ¡El número máximo de intentos necesarios es siempre ≤ 7!
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

<details>
<summary>💡 Solución Razonada al Cruce del Río</summary>

1. **Viaje 1 (Ida):** El granjero cruza con la **cabra** (deja al lobo y la lechuga juntos en la orilla original sin peligro).
2. **Viaje 1 (Vuelta):** El granjero regresa solo.
3. **Viaje 2 (Ida):** El granjero cruza con el **lobo**.
4. **Viaje 2 (Vuelta):** ¡Paso clave! El granjero **se trae de vuelta a la cabra** (no puede dejar juntos al lobo y la cabra en la orilla de destino).
5. **Viaje 3 (Ida):** El granjero deja a la cabra y cruza con la **lechuga**.
6. **Viaje 3 (Vuelta):** El granjero regresa solo (el lobo y la lechuga quedan juntos sin problema).
7. **Viaje 4 (Ida):** El granjero cruza finalmente con la **cabra**.
* **Resultado:** Los tres elementos han cruzado íntegros en 7 trayectos.
</details>

---

### Reto 5: El Enigma de los Tres Interruptores y la Bombilla

#### Enunciado
Te encuentras en la planta baja de una casa frente a un panel con **tres interruptores** (`1`, `2` y `3`) en posición de apagado. En la buhardilla del piso superior hay una única bombilla incandescente tradicional, la cual es invisible desde la planta baja.
* Puedes accionar los interruptores tantas veces como desees.
* Sin embargo, solo puedes subir al piso de arriba a comprobar el estado de la bombilla **una única vez**.
¿Cómo puedes determinar con absoluta certeza matemática cuál de los tres interruptores es el que enciende la bombilla?

:::tip[💡 Pistas para la resolución]
* Un dispositivo físico no solo emite información lumínica visible por los ojos; también genera otros efectos observables en el entorno físico (como la disipación de energía térmica o calor).
:::

<details>
<summary>💡 Solución Razonada de los Interruptores</summary>

1. Enciende el **Interruptor 1** y déjalo accionado durante 10 minutos para que la corriente caliente el filamento de la bombilla.
2. Pasados los 10 minutos, apaga el Interruptor 1 e inmediatamente enciende el **Interruptor 2**.
3. Sube a la buhardilla e inspecciona la bombilla:
   * **Caso A:** Si la bombilla está encendida → El interruptor correcto es el **Interruptor 2**.
   * **Caso B:** Si la bombilla está apagada pero al tocarla el cristal está **caliente** → El interruptor correcto es el **Interruptor 1**.
   * **Caso C:** Si la bombilla está apagada y el cristal está **frío** → El interruptor correcto es el **Interruptor 3**.
* **Lección computacional:** En arquitectura de sistemas, a menudo podemos leer información de estado latente (*side channels*) más allá del valor binario aparente (0 o 1).
</details>

---

### Reto 6: Las 9 Monedas y la Balanza de Dos Platos (Búsqueda Ternaria)

#### Enunciado
Dispones de **9 monedas de oro** visualmente idénticas, pero una de ellas es falsa y tiene un peso ligeramente menor que las 8 restantes (las cuales pesan exactamente lo mismo).
Cuentas con una **balanza clásica de dos platos** (que únicamente indica si el plato izquierdo pesa más, si pesa más el derecho, o si ambos están equilibrados).
¿Cómo puedes identificar inequívocamente la moneda falsa utilizando la balanza un **máximo de 2 veces**?

:::tip[💡 Pistas para la resolución]
* En lugar de dividir el conjunto por la mitad (búsqueda binaria, que requeriría 4 pesadas en el peor caso), aprovecha que una balanza de dos platos tiene **tres estados posibles**: inclinación izquierda, inclinación derecha o equilibrio perfecto.
* Divide las monedas en grupos de 3 (búsqueda ternaria: `9 = 3 × 3`).
:::

<details>
<summary>💡 Solución Razonada de las 9 Monedas</summary>

1. **Primera Pesada:** Divide las 9 monedas en tres grupos de 3: `Grupo A (3)`, `Grupo B (3)` y `Grupo C (3)`. Coloca el Grupo A en el plato izquierdo y el Grupo B en el plato derecho:
   * **Si la balanza se equilibra:** La moneda defectuosa está obligatoriamente en el **Grupo C**.
   * **Si la balanza se inclina:** La moneda defectuosa está en el grupo que subió (el más ligero).
2. **Segunda Pesada:** Has aislado el problema a solo 3 monedas sospechosas (`M1`, `M2`, `M3`). Coloca `M1` en el plato izquierdo y `M2` en el derecho:
   * **Si la balanza se equilibra:** La moneda falsa es **`M3`**.
   * **Si la balanza se desequilibra:** La moneda falsa es la del plato más elevado.
* **Lección computacional:** La balanza proporciona `log₃(N)` de capacidad de discriminación. Con `3² = 9` monedas, bastan exactamente `2` pesadas.
</details>

---

### Reto 7: La Torre de Hanói y la Estrategia Recursiva

#### Enunciado
El clásico juego de la **Torre de Hanói** consta de 3 varillas verticales (`Origen`, `Auxiliar` y `Destino`) y `N` discos de radios decrecientes ensartados en la varilla `Origen`. El objetivo es trasladar todos los discos a la varilla `Destino` cumpliendo dos normas inquebrantables:
1. Solo se puede mover un disco en cada paso.
2. Nunca puede colocarse un disco sobre otro de menor tamaño.

**Desafío algorítmico:** Describe en pseudocódigo un subalgoritmo recursivo `Hanoi(n, origen, destino, auxiliar)` que imprima la secuencia exacta de movimientos necesarios para resolver la torre de `N` discos.

:::tip[💡 Pistas para la resolución]
Aplica descomposición recursiva (*Divide y Vencerás*):
* Mover `N` discos de `Origen` a `Destino` equivale a:
  1. Mover los `N - 1` discos superiores de `Origen` a `Auxiliar`.
  2. Mover el disco grande restante `N` directamente de `Origen` a `Destino`.
  3. Mover los `N - 1` discos aparcados en `Auxiliar` hacia `Destino`.
* El caso base se produce cuando `N = 1` (un único movimiento elemental).
:::

<details>
<summary>💡 Solución en Pseudocódigo para la Torre de Hanói</summary>

```text
SubProceso Hanoi(n, origen, destino, auxiliar)
    // CASO BASE: Si solo hay 1 disco, se mueve directamente
    Si n = 1 Entonces
        Escribir "Mover disco 1 desde ", origen, " hacia ", destino
    SiNo
        // Paso 1: Mover los n - 1 discos superiores de origen a auxiliar
        Hanoi(n - 1, origen, auxiliar, destino)
        
        // Paso 2: Mover el disco base n de origen a destino
        Escribir "Mover disco ", n, " desde ", origen, " hacia ", destino
        
        // Paso 3: Mover los n - 1 discos de auxiliar a destino
        Hanoi(n - 1, auxiliar, destino, origen)
    FinSi
FinSubProceso

Algoritmo ResolucionHanoi
    Definir totalDiscos Como Entero
    totalDiscos ← 3
    
    Escribir "=== SOLUCIÓN DE LA TORRE DE HANÓI PARA ", totalDiscos, " DISCOS ==="
    Hanoi(totalDiscos, "Varilla A", "Varilla C", "Varilla B")
FinAlgoritmo
```

* El número total de movimientos necesarios para resolver una torre de `N` discos es exactamente `2ᴺ - 1` (para 3 discos: `2³ - 1 = 7` movimientos; para 64 discos como reza la leyenda hindú, se requerirían `18.446.744.073.709.551.615` movimientos, más de 580 mil millones de años).
</details>