---
title: "🔴 Boletín 06 — Retos Extras y Proyectos Avanzados"
description: "Proyectos integradores resueltos (Hoja de Cálculo Genérica EA12, Museo del Oro, Supermercado FIFO/LIFO, Evaluador Postfijo RPN, TablaHashPL con Prueba Lineal y Concurrencia) más retos de Acepta el Reto y CodeWars."
draft: true
prev: false
next: false
---

Este boletín reúne **6 proyectos integradores de nivel avanzado** (con su código completo desplegable) extraídos de las actividades de ampliación de la unidad (**EA12** y los problemas avanzados de pilas, colas, tablas hash y concurrencia), seguidos de una selección de problemas de programación competitiva en **Acepta el Reto** y **CodeWars** para poner a prueba tu dominio de las estructuras de datos.

---

## 🏗️ Parte 1: Proyectos Integradores Avanzados (Con Solución Desplegable)

### Proyecto 1: Motor de Hoja de Cálculo Polimórfico y Genérico (`Hoja` y `Columna<T extends Comparable<T>>` — Actividad EA12)

Diseña un motor en memoria para representar una **Hoja de Cálculo** organizada por columnas con nombre (como `"Alumno"`, `"Edad"`, `"Nota"`):
1. **Diseño Genérico (`Columna<T extends Comparable<T>>`):** cada columna tiene una `etiqueta` (`String`), almacena una lista tipada `List<T>` de celdas y es capaz de calcular el valor máximo de la columna mediante el método **`T mayor()`** gracias al límite `<T extends Comparable<T>>`, además de contar apariciones (`contar`) y filtrar con predicados (`contarSI`).
2. **Contenedor `Hoja`:** agrupa un mapa ordenado de columnas (`Map<String, Columna<?>>`) permitiendo añadir columnas de cualquier tipo comparable (`String`, `Integer`, `Double`) y consultar estadísticas globales.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

class Columna<T extends Comparable<T>> {
    private final String nombre;
    private final List<T> celdas = new ArrayList<>();

    public Columna(String nombre, List<T> datosIniciales) {
        this.nombre = nombre;
        this.celdas.addAll(datosIniciales);
    }

    public String getNombre() { return nombre; }
    public List<T> getCeldas() { return celdas; }

    public void anadirValor(T valor) {
        celdas.add(valor);
    }

    // Gracias a <T extends Comparable<T>>, podemos hallar el máximo de cualquier columna
    public T mayor() {
        if (celdas.isEmpty()) return null;
        T max = celdas.get(0);
        for (int i = 1; i < celdas.size(); i++) {
            T actual = celdas.get(i);
            if (actual != null && actual.compareTo(max) > 0) {
                max = actual;
            }
        }
        return max;
    }

    public int contar(T buscado) {
        int cont = 0;
        for (T elem : celdas) {
            if (Objects.equals(elem, buscado)) cont++;
        }
        return cont;
    }

    public int contarSI(Predicate<T> condicion) {
        int cont = 0;
        for (T elem : celdas) {
            if (elem != null && condicion.test(elem)) cont++;
        }
        return cont;
    }

    @Override
    public String toString() {
        return String.format("Columna '%s' %s (Mayor: %s)", nombre, celdas, mayor());
    }
}

public class HojaCalculoEA12 {
    private final Map<String, Columna<?>> columnas = new LinkedHashMap<>();

    public <T extends Comparable<T>> void registrarColumna(Columna<T> col) {
        columnas.put(col.getNombre(), col);
    }

    public void imprimirResumen() {
        System.out.println("=== RESUMEN DE LA HOJA DE CÁLCULO ===");
        for (Columna<?> col : columnas.values()) {
            System.out.println("  📊 " + col);
        }
    }

    public static void main(String[] args) {
        Columna<String> colAlumnos = new Columna<>("Alumno", List.of("Ana", "Luis", "Zoe", "Carlos"));
        Columna<Integer> colEdades = new Columna<>("Edad", List.of(19, 24, 18, 22));
        Columna<Double> colNotas = new Columna<>("Nota", List.of(8.5, 4.2, 9.8, 7.0));

        HojaCalculoEA12 hoja = new HojaCalculoEA12();
        hoja.registrarColumna(colAlumnos);
        hoja.registrarColumna(colEdades);
        hoja.registrarColumna(colNotas);

        hoja.imprimirResumen();
        System.out.println("Sobresalientes (>= 9.0): " + colNotas.contarSI(n -> n >= 9.0));
        System.out.println("Mayores de 20 años:      " + colEdades.contarSI(e -> e > 20));
    }
}
```
</details>

---

### Proyecto 2: El Detective del Museo de Joyas de Oro de Bogotá (Simulación con Colas de Pases y Conjuntos)

En el famoso Museo del Oro de Bogotá existe un estricto control de acceso: cada visitante que entra recibe en la puerta un **pase numerado** extraído de una **cola de pases libres (`Queue<Integer>`)**. Cuando ese visitante sale del museo, **devuelve su pase al final de la cola de pases libres**, por lo que un mismo número de pase puede ser utilizado por varios visitantes distintos a lo largo del día.

Se ha cometido el robo de una valiosa pieza precolombina y la policía dispone del registro cronológico de eventos del día (`ENTRA Nombre` o `SALE NombrePase`) y de la hora del robo:
1. Modela la asignación y devolución de pases mediante una `Queue<Integer>` (inicialmente con los pases `1` al `5`) y un `Map<Integer, String>` con los visitantes que están actualmente dentro del museo (`Pase -> Nombre`), además de un `Map<String, List<Integer>>` con el historial de todos los pases que ha utilizado cada persona durante la jornada.
2. Identifica qué sospechosos estaban dentro del museo exactamente en el instante en que sonó la alarma.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MuseoDelOro {
    private final Queue<Integer> pasesLibres = new ArrayDeque<>();
    private final Map<Integer, String> visitantesDentro = new LinkedHashMap<>();
    private final Map<String, List<Integer>> historialPasesPorPersona = new LinkedHashMap<>();

    public MuseoDelOro(int aforoMaximo) {
        for (int i = 1; i <= aforoMaximo; i++) {
            pasesLibres.offer(i);
        }
    }

    public void registrarEntrada(String visitante) {
        if (pasesLibres.isEmpty()) {
            System.out.println("⛔ Aforo completo. " + visitante + " debe esperar.");
            return;
        }
        int paseAsignado = pasesLibres.poll();
        visitantesDentro.put(paseAsignado, visitante);
        historialPasesPorPersona.computeIfAbsent(visitante, k -> new ArrayList<>()).add(paseAsignado);
        System.out.printf("➡️ ENTRA %-10s con el Pase #%d (Libres: %s)%n", visitante, paseAsignado, pasesLibres);
    }

    public void registrarSalida(int numeroPase) {
        String saliente = visitantesDentro.remove(numeroPase);
        if (saliente != null) {
            pasesLibres.offer(numeroPase); // El pase vuelve al final de la cola FIFO
            System.out.printf("⬅️ SALE  %-10s devolviendo el Pase #%d (Libres: %s)%n", saliente, numeroPase, pasesLibres);
        }
    }

    public void sonarAlarmaRobo() {
        System.out.println("\n🚨 ¡ALARMA DE ROBO! Sospechosos actualmente dentro del Museo:");
        visitantesDentro.forEach((pase, nombre) ->
                System.out.println("   🔍 Pase #" + pase + " -> " + nombre));
        System.out.println("📋 Historial completo de pases usados hoy: " + historialPasesPorPersona);
    }

    public static void main(String[] args) {
        MuseoDelOro museo = new MuseoDelOro(3); // 3 pases numerados: 1, 2, 3
        museo.registrarEntrada("Lupin");     // Recibe pase 1
        museo.registrarEntrada("Carmen");    // Recibe pase 2
        museo.registrarSalida(1);            // Lupin sale y devuelve el pase 1 al final (cola: 3, 1)
        museo.registrarEntrada("Sherlock");  // Recibe pase 3
        museo.registrarEntrada("Moriarty");  // Recibe pase 1 (reutilizado)
        museo.registrarSalida(2);            // Carmen sale y devuelve el pase 2
        museo.registrarEntrada("Lupin");     // Lupin vuelve a entrar y recibe el pase 2

        museo.sonarAlarmaRobo();
    }
}
```
</details>

---

### Proyecto 3: Reposición de Estanterías de Supermercado por Fecha de Caducidad (Pilas y Auxiliares)

En un supermercado, los envases de lácteos de una estantería estrecha se colocan en **una pila (`Deque<LocalDate>`)** donde el cliente siempre coge el envase que está más cerca del borde (**la cima de la pila**). Para que se vendan antes los productos que caducan antes, la pila debe mantenerse siempre ordenada de modo que **en la cima esté la fecha de caducidad más próxima** y en el fondo la más lejana.

Cuando llega un reponedor con un nuevo lote de envases con distintas fechas de caducidad, debe insertarlos en la estantería retirando temporalmente a un **carro auxiliar (otra pila)** los envases cuya fecha sea más reciente y volviéndolos a colocar después. Implementa la simulación completa.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class EstanteriaSupermercado {

    record ProductoLacteo(String lote, LocalDate caducidad) {
        @Override
        public String toString() {
            return lote + " (Cad: " + caducidad + ")";
        }
    }

    private final Deque<ProductoLacteo> estanteria = new ArrayDeque<>();

    // Inserta un nuevo envase garantizando que en la CIMA quede siempre la fecha MÁS PRÓXIMA a caducar
    public void reponerEnvase(ProductoLacteo nuevo) {
        Deque<ProductoLacteo> carroAuxiliar = new ArrayDeque<>();

        // Mientras la cima caduque ANTES que el nuevo envase, la apartamos al carro auxiliar
        while (!estanteria.isEmpty() && estanteria.peek().caducidad().isBefore(nuevo.caducidad())) {
            carroAuxiliar.push(estanteria.pop());
        }

        // Colocamos el nuevo envase en su hueco exacto
        estanteria.push(nuevo);

        // Devolvemos los envases desde el carro auxiliar a la estantería
        while (!carroAuxiliar.isEmpty()) {
            estanteria.push(carroAuxiliar.pop());
        }
    }

    public void mostrarEstanteria() {
        System.out.println("🥛 Estantería (Cima = primero que coge el cliente -> Fondo):");
        for (ProductoLacteo p : estanteria) {
            System.out.println("   " + p);
        }
    }

    public static void main(String[] args) {
        EstanteriaSupermercado superDam = new EstanteriaSupermercado();
        List<ProductoLacteo> llegadaCamion = List.of(
            new ProductoLacteo("Lote-A", LocalDate.of(2026, 5, 20)),
            new ProductoLacteo("Lote-B", LocalDate.of(2026, 5, 10)),
            new ProductoLacteo("Lote-C", LocalDate.of(2026, 5, 25)),
            new ProductoLacteo("Lote-D", LocalDate.of(2026, 5, 14))
        );

        for (ProductoLacteo envase : llegadaCamion) {
            superDam.reponerEnvase(envase);
        }
        superDam.mostrarEstanteria();
    }
}
```
</details>

---

### Proyecto 4: Evaluador de Expresiones Postfijas (Notación Polaca Inversa — RPN) y Reconocedor Espejo `X&Y`

Las calculadoras científicas Hewlett-Packard y la máquina virtual de Java (JVM) evalúan las fórmulas matemáticas mediante **Notación Postfija (RPN)**, donde los operadores van **después** de sus operandos y no hacen falta paréntesis (por ejemplo, la expresión infija `(3 + 4) * 5 - 2` se escribe en postfija como `"3 4 + 5 * 2 -"`):
1. Implementa un método `evaluarPostfija(String expr)` usando una **`Deque<Double> pila`**: cada vez que leas un número, apílalo; cada vez que leas un operador (`+`, `-`, `*`, `/`), desapila los dos últimos números (`b = pop()`, `a = pop()`), aplica la operación `a op b` y apila el resultado.
2. Implementa además el reconocedor del **lenguaje espejo `X&Y`**: comprueba con una pila si una cadena tiene la forma `X&Y` donde `Y` es exactamente la cadena `X` escrita al revés (por ejemplo, `"abb&bba"` → `true`, `"ab&ab"` → `false`).

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class EvaluadorPostfijoYEspejo {

    public static double evaluarPostfija(String expresionRpn) {
        Deque<Double> pila = new ArrayDeque<>();
        String[] tokens = expresionRpn.trim().split("\\s+");

        for (String token : tokens) {
            switch (token) {
                case "+" -> { double b = pila.pop(), a = pila.pop(); pila.push(a + b); }
                case "-" -> { double b = pila.pop(), a = pila.pop(); pila.push(a - b); }
                case "*" -> { double b = pila.pop(), a = pila.pop(); pila.push(a * b); }
                case "/" -> { double b = pila.pop(), a = pila.pop(); pila.push(a / b); }
                default  -> pila.push(Double.parseDouble(token));
            }
        }
        return pila.pop();
    }

    public static boolean esCadenaEspejo(String cadena) {
        int posSeparador = cadena.indexOf('&');
        if (posSeparador == -1) return false;

        Deque<Character> pila = new ArrayDeque<>();
        // Apilamos la mitad izquierda X
        for (int i = 0; i < posSeparador; i++) {
            pila.push(cadena.charAt(i));
        }
        // Comparamos carácter a carácter con la mitad derecha Y
        for (int i = posSeparador + 1; i < cadena.length(); i++) {
            if (pila.isEmpty() || pila.pop() != cadena.charAt(i)) {
                return false;
            }
        }
        return pila.isEmpty();
    }

    public static void main(String[] args) {
        // (3 + 4) * 5 - 2 = 33.0
        String rpn = "3 4 + 5 * 2 -";
        System.out.println("Expresión RPN '" + rpn + "' = " + evaluarPostfija(rpn));

        System.out.println("¿'reconocer&reconocer' es espejo?: " + esCadenaEspejo("reconocer&reconocer"));
        System.out.println("¿'java&avaj' es espejo?:           " + esCadenaEspejo("java&avaj"));
        System.out.println("¿'java&java' es espejo?:           " + esCadenaEspejo("java&java"));
    }
}
```
</details>

---

### Proyecto 5: Implementación Propia de una Tabla Hash con Prueba Lineal y Marca de Borrado (`TablaHashPL<K, V>`)

Construye tu propio diccionario genérico **`TablaHashPL<K, V>`** sin usar `HashMap`:
1. Utiliza un array interno de casillas `Slot<K, V>[]` (donde cada `Slot` guarda `clave`, `valor` y la marca lápida **`boolean borrada`**).
2. Resuelve las colisiones mediante **Prueba Lineal** (`(hash + i) % capacidad`) y redimensiona automáticamente la tabla al siguiente número primo (`extiende()`) cuando la ocupación supere los `2/3` de la capacidad.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
public class TablaHashPL<K, V> {

    private static class Slot<K, V> {
        K clave;
        V valor;
        boolean borrada;

        Slot(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.borrada = false;
        }
    }

    private Slot<K, V>[] tabla;
    private int numElementos;
    private int ocupadasConBorradas;

    @SuppressWarnings("unchecked")
    public TablaHashPL(int capacidadInicial) {
        int cap = siguientePrimo(Math.max(7, capacidadInicial));
        this.tabla = (Slot<K, V>[]) new Slot[cap];
    }

    private int hash(K clave) {
        return (clave.hashCode() & 0x7FFFFFFF) % tabla.length;
    }

    public void put(K clave, V valor) {
        if (ocupadasConBorradas >= (tabla.length * 2) / 3) {
            extiende();
        }
        int idx = hash(clave);
        int primerBorrado = -1;

        while (tabla[idx] != null) {
            if (!tabla[idx].borrada && tabla[idx].clave.equals(clave)) {
                tabla[idx].valor = valor; // Actualiza clave existente
                return;
            }
            if (tabla[idx].borrada && primerBorrado == -1) {
                primerBorrado = idx; // Recordamos el primer hueco reciclable
            }
            idx = (idx + 1) % tabla.length;
        }

        if (primerBorrado != -1) {
            tabla[primerBorrado] = new Slot<>(clave, valor);
        } else {
            tabla[idx] = new Slot<>(clave, valor);
            ocupadasConBorradas++;
        }
        numElementos++;
    }

    public V get(K clave) {
        int idx = hash(clave);
        int pasos = 0;
        while (tabla[idx] != null && pasos < tabla.length) {
            if (!tabla[idx].borrada && tabla[idx].clave.equals(clave)) {
                return tabla[idx].valor;
            }
            idx = (idx + 1) % tabla.length;
            pasos++;
        }
        return null;
    }

    public V remove(K clave) {
        int idx = hash(clave);
        int pasos = 0;
        while (tabla[idx] != null && pasos < tabla.length) {
            if (!tabla[idx].borrada && tabla[idx].clave.equals(clave)) {
                V antiguo = tabla[idx].valor;
                tabla[idx].borrada = true; // ¡Marca lápida (Tombstone) para no romper búsquedas!
                tabla[idx].valor = null;
                numElementos--;
                return antiguo;
            }
            idx = (idx + 1) % tabla.length;
            pasos++;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void extiende() {
        Slot<K, V>[] antigua = this.tabla;
        int nuevaCap = siguientePrimo(antigua.length * 2);
        this.tabla = (Slot<K, V>[]) new Slot[nuevaCap];
        this.numElementos = 0;
        this.ocupadasConBorradas = 0;

        for (Slot<K, V> s : antigua) {
            if (s != null && !s.borrada) {
                put(s.clave, s.valor);
            }
        }
    }

    private static int siguientePrimo(int n) {
        int candidato = (n % 2 == 0) ? n + 1 : n;
        while (!esPrimo(candidato)) candidato += 2;
        return candidato;
    }

    private static boolean esPrimo(int n) {
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TablaHashPL<String, Integer> stock = new TablaHashPL<>(5);
        stock.put("Teclado", 15);
        stock.put("Ratón", 40);
        stock.put("Monitor", 8);
        stock.remove("Ratón"); // Prueba de borrado con marca lápida

        System.out.println("Stock Monitor (tras borrar Ratón): " + stock.get("Monitor"));
        System.out.println("Stock Ratón (borrado):             " + stock.get("Ratón"));
    }
}
```
</details>

---

### Proyecto 6: Cocina de Restaurante Multihilo (Patrón Productor-Consumidor con `wait()` y `notifyAll()`)

Simula la barra de pases de un restaurante donde un hilo **`Cocinero` (Productor)** prepara 5 comandas y las deposita en una `BarraDePases` de capacidad limitada, mientras un hilo **`Camarero` (Consumidor)** las recoge y las sirve en las mesas, coordinándose de forma segura mediante monitores (`synchronized`, `wait()` y `notifyAll()`).

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
import java.util.ArrayDeque;
import java.util.Queue;

public class RestauranteConcurrente {

    static class BarraDePases {
        private final Queue<String> platos = new ArrayDeque<>();
        private final int capacidadMaxima;

        public BarraDePases(int capacidadMaxima) {
            this.capacidadMaxima = capacidadMaxima;
        }

        public synchronized void colocarPlato(String plato) throws InterruptedException {
            while (platos.size() == capacidadMaxima) {
                wait(); // La barra está llena, el cocinero espera
            }
            platos.offer(plato);
            System.out.println("👨‍🍳 Cocinero emplata: " + plato + " (En barra: " + platos.size() + ")");
            notifyAll();
        }

        public synchronized String recogerPlato() throws InterruptedException {
            while (platos.isEmpty()) {
                wait(); // La barra está vacía, el camarero espera
            }
            String plato = platos.poll();
            System.out.println("🤵 Camarero sirve:   " + plato + " (En barra: " + platos.size() + ")");
            notifyAll();
            return plato;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BarraDePases barra = new BarraDePases(2);
        String[] menu = {"Paella Valenciana", "Arroz al Horno", "Fideuà", "All i Pebre", "Cremaet"};

        Thread cocinero = new Thread(() -> {
            try {
                for (String plato : menu) {
                    barra.colocarPlato(plato);
                    Thread.sleep(120);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread camarero = new Thread(() -> {
            try {
                for (int i = 0; i < menu.length; i++) {
                    barra.recogerPlato();
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        cocinero.start();
        camarero.start();
        cocinero.join();
        camarero.join();
        System.out.println("✅ Servicio de comidas finalizado sin colisiones.");
    }
}
```
</details>

---

## ⚡ Parte 2: Micro-Retos de Optimización con Colecciones

### Reto 1: Eliminar duplicados preservando el orden de primera aparición

Dado un `ArrayList<Integer>` que puede contener elementos repetidos, escribe un método que devuelva una **nueva lista** con los mismos elementos pero sin duplicados, **conservando el orden exacto de la primera aparición** de cada uno.

:::tip[Pista]
Un `HashSet` no garantiza el orden de inserción... pero puedes usar un `Set` solo para *comprobar* si ya has visto un elemento, mientras construyes el resultado en una `List` aparte aprovechando el valor devuelto por `add()`.
:::

<details>
<summary>💡 Solución comentada en Java 25 LTS</summary>

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RetoSinDuplicados {
    public static <T> List<T> sinDuplicados(List<T> lista) {
        Set<T> vistos = new HashSet<>();
        List<T> resultado = new ArrayList<>();
        for (T elemento : lista) {
            // Set.add() devuelve true si el elemento NO estaba ya en el conjunto
            if (vistos.add(elemento)) {
                resultado.add(elemento);
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(4, 2, 8, 4, 1, 2, 9, 8);
        System.out.println("Original: " + numeros);
        System.out.println("Sin duplicados (orden conservado): " + sinDuplicados(numeros));
    }
}
```

`Set.add()` devuelve `true` solo la primera vez que se añade un valor. Es un modismo fundamental en Java para combinar en una sola operación `O(1)` la comprobación de existencia y el registro.
</details>

### Reto 2: La palabra más frecuente con `Map.merge`

Dado un texto largo (un `String` con varias frases), escribe un método que devuelva la palabra que más veces aparece (normalizando a minúsculas y descartando signos ortográficos) utilizando el método `merge()` de `Map`.

<details>
<summary>💡 Solución comentada en Java 25 LTS</summary>

```java
import java.util.HashMap;
import java.util.Map;

public class RetoPalabraFrecuente {
    public static String palabraMasFrecuente(String texto) {
        String[] palabras = texto.toLowerCase().replaceAll("[^a-záéíóúñ ]", "").split("\\s+");
        Map<String, Integer> conteo = new HashMap<>();

        for (String palabra : palabras) {
            if (palabra.isEmpty()) continue;
            // merge: si la clave no existe pon 1, si existe suma 1 al valor previo
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

    public static void main(String[] args) {
        String texto = "Java es un lenguaje genial. En Java las colecciones son potentes y Java evoluciona.";
        System.out.println("Palabra más frecuente: " + palabraMasFrecuente(texto));
    }
}
```
</details>

---

## 🏆 Parte 3: Retos de Programación Competitiva (Acepta el Reto y CodeWars)

Pon a prueba la eficiencia asintótica (`O(1)` y `O(log n)`) de tus colecciones en jueces online reales. *(¡Recuerda!: si usas un `ArrayList` donde hace falta un `HashSet`, un `HashMap` o una `PriorityQueue`, el juez devolverá **TLE — Time Limit Exceeded**).*

### 🇪🇸 Retos en ¡Acepta el Reto!

1. [**Paréntesis Balanceados (Acepta el Reto #141)**](https://aceptaelreto.com/problem/statement.php?id=141)
   * **Estructuras clave:** `Deque<Character>` (`ArrayDeque`).
   * **Pista estratégica:** idéntico a nuestra actividad **EA3**, pero asegúrate de ignorar todos los caracteres que no sean `()`, `[]` o `{}` y comprobar al final que la pila haya quedado vacía (`pila.isEmpty()`).

2. [**Lo que cuesta sumar (Acepta el Reto #143 / #254 — Colas de Prioridad)**](https://aceptaelreto.com/problem/statement.php?id=143)
   * **Estructuras clave:** `PriorityQueue<Long>` y `ArrayDeque`.
   * **Pista estratégica:** cuando debes sumar siempre los dos números más pequeños de un conjunto y volver a meter el resultado de su suma en el conjunto, **nunca reordenes un `ArrayList` en cada paso (`O(n² log n)`)**. Mete todos los números en una `PriorityQueue<Long>`: extraer los dos menores (`poll() + poll()`) y volver a insertar la suma (`offer()`) cuesta apenas **`O(log n)`**.

3. [**Referencias Cruzadas (Acepta el Reto #291)**](https://aceptaelreto.com/problem/statement.php?id=291)
   * **Estructuras clave:** `TreeMap<String, TreeSet<Integer>>`.
   * **Pista estratégica:** se pide construir el índice alfabético final de un libro indicando en qué números de línea (ordenados y sin repetir) aparece cada palabra de más de 2 letras. Un `TreeMap` ordena automáticamente las palabras de la `a` a la `z`, y un `TreeSet<Integer>` como valor ordena los números de línea y descarta apariciones repetidas en la misma línea.

4. [**Eligiendo el Lugar de Vacaciones / Bingos y Loterías (Acepta el Reto #109 — Liga de Pádel)**](https://aceptaelreto.com/problem/statement.php?id=109)
   * **Estructuras clave:** `HashMap<String, Integer>`.
   * **Pista estratégica:** usa `mapaPuntos.merge(equipoGanador, 2, Integer::sum)` y `mapaPuntos.merge(equipoPerdedor, 1, Integer::sum)` para actualizar la clasificación de cualquier liga deportiva en tiempo constante `O(1)`.

---

### 🥋 Katas en CodeWars (Java)

1. [**Valid Braces (6 kyu)**](https://www.codewars.com/kata/5277c8a221e209d3f6000b56/java)
   * **Objetivo:** validar secuencias anidadas de `()`, `[]` y `{}` mediante `ArrayDeque<Character>`.
2. [**Find the odd int (6 kyu)**](https://www.codewars.com/kata/54da5a58ea159efa38000836/java)
   * **Objetivo:** dado un array de enteros, encontrar el único número que aparece un número impar de veces utilizando un `HashMap<Integer, Integer>` con `merge` o un `HashSet<Integer>` (si ya está lo sacas, si no está lo metes; el que quede al final es el impar).
3. [**Supermarket Queue (6 kyu)**](https://www.codewars.com/kata/57b06f90e298a7b53d000a86/java)
   * **Objetivo:** simular cuánto tiempo tardan `n` cajas de supermercado en atender una cola de clientes. ¡Se resuelve en 6 líneas metiendo las `n` cajas en una `PriorityQueue<Integer>` inicializada con ceros y sumando cada cliente a la caja que quede libre antes (`pq.offer(pq.poll() + tiempoCliente)`)!
