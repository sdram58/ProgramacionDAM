---
title: "Boletín 7 — Retos Extras y Proyectos Integradores"
description: "Proyectos avanzados de la Unidad 7 con soluciones desplegables en Java 25 LTS (Sistema Geométrico Punto y NubePuntos con 18 métodos de Stream, ColectorPrimos personalizado, Simulación Concurrente de Supermercado con Callable/Future, Agregador Asíncrono con CompletableFuture, Benchmark parallelStream y Analizador de Archivos con Files.lines), junto con retos de Acepta el Reto y CodeWars."
slug: ud07/boletin-07-extras
draft: true
prev: false
next: false
---

Este boletín reúne **6 proyectos integradores de nivel profesional** (con sus soluciones completas desplegables en Java 25 LTS) que cubren desde el diseño de motores geométricos analíticos con Streams hasta la creación de recolectores personalizados `Collector<T, A, R>`, concurrencia con `ExecutorService`, `Callable`, `Future` y `CompletableFuture`, procesamiento masivo en paralelo y lectura funcional de ficheros con `Files.lines`. Al final encontrarás además una selección de retos algorítmicos de **Acepta el Reto** y **CodeWars** ideales para resolver mediante tuberías funcionales.

---

## Proyecto 1: Motor Geométrico Analítico (`Punto` y `NubePuntos` — Los 18 Métodos de Stream)

Diseña un sistema de análisis geométrico bidimensional compuesto por el enumerado `TipoCuadrante { PRIMERO, SEGUNDO, TERCERO, CUARTO, EJES }`, el enumerado de proximidad al origen `TipoDiana { CENTRO, CERCA, MEDIO, LEJOS, FUERA }`, el tipo `Punto(double x, double y)` (implementando `Comparable<Punto>` por coordenada `x` y a igualdad por `y`) y la clase **`NubePuntos`** que encapsula un `Set<Punto> puntos`.

Implementa en `NubePuntos` los **18 métodos analíticos** utilizando **exclusivamente la API Stream**:

1. `long contarPuntosConAbscisaMayorQue(double xMin)`: número de puntos cuya coordenada `x > xMin`.
2. `long contarPuntosEnCuadrante(TipoCuadrante c)`: número de puntos situados en el cuadrante indicado.
3. `long contarPuntosADistanciaMenorQue(Punto ref, double distMax)`: puntos cuya distancia euclídea a `ref` es menor que `distMax`.
4. `Optional<Punto> calcularCentroGravedad()`: devuelve el punto cuyas coordenadas `(mediaX, mediaY)` son el promedio de todos los puntos de la nube.
5. `boolean hayAlgunPuntoEnEjes()`: comprueba con `anyMatch` si algún punto tiene `x == 0` o `y == 0`.
6. `boolean hayAlgunPuntoEnCirculo(Punto centro, double radio)`: comprueba si existe algún punto dentro del círculo dado.
7. `boolean todosEnPrimerCuadrante()`: comprueba con `allMatch` si todos los puntos pertenecen al `PRIMERO`.
8. `Optional<Punto> puntoMasLejanoAlOrigen()`: punto con mayor distancia al origen `(0, 0)`.
9. `Optional<Punto> puntoMasCercanoA(Punto ref)`: punto de la nube (distinto de `ref`) más próximo a `ref`.
10. `NubePuntos subNubeEnCirculo(Punto centro, double radio)`: devuelve una nueva `NubePuntos` con los puntos contenidos en el círculo.
11. `Map<TipoCuadrante, SortedSet<Punto>> agruparPorCuadranteOrdenado()`: agrupa los puntos por cuadrante en conjuntos ordenados (`Collectors.toCollection(TreeSet::new)`).
12. `SortedMap<TipoCuadrante, Long> contarPuntosPorCuadranteOrdenado()`: devuelve un `TreeMap` con el conteo de puntos de cada cuadrante.
13. `SortedMap<TipoDiana, Set<Punto>> clasificarEnDiana()`: agrupa los puntos en un `TreeMap` según su distancia al origen (`<= 1` `CENTRO`, `<= 3` `CERCA`, `<= 6` `MEDIO`, `<= 10` `LEJOS`, `> 10` `FUERA`).
14. `NubePuntos desplazarTodos(double dx, double dy)`: devuelve una nueva `NubePuntos` tras trasladar todos los puntos con `map(p -> p.desplazar(dx, dy))`.
15. `Map<TipoCuadrante, Punto> sumaVectorialPorCuadrante()`: suma las coordenadas `(x1 + x2, y1 + y2)` de los puntos de cada cuadrante usando `Collectors.reducing(new Punto(0, 0), Punto::sumar)`.
16. `Map<TipoCuadrante, Optional<Punto>> puntoMasAlejadoPorCuadrante()`: obtiene el punto más lejano al origen dentro de cada cuadrante.
17. `Map<TipoCuadrante, Punto> centroGravedadPorCuadrante()`: calcula el centro de gravedad `(mediaX, mediaY)` de cada cuadrante usando `Collectors.collectingAndThen`.
18. `static NubePuntos leerDesdeLineas(Stream<String> lineas)`: parsea líneas con formato `"x,y"` (como las devueltas por `Files.lines(Path)`) ignorando líneas vacías o comentarios `#`.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Proyecto01NubePuntos {

    public enum TipoCuadrante { PRIMERO, SEGUNDO, TERCERO, CUARTO, EJES }

    public enum TipoDiana { CENTRO, CERCA, MEDIO, LEJOS, FUERA }

    public record Punto(double x, double y) implements Comparable<Punto> {

        public static final Punto ORIGEN = new Punto(0.0, 0.0);

        public double distanciaA(Punto otro) {
            return Math.hypot(this.x - otro.x, this.y - otro.y);
        }

        public double distanciaAlOrigen() {
            return Math.hypot(this.x, this.y);
        }

        public TipoCuadrante cuadrante() {
            if (x == 0.0 || y == 0.0) return TipoCuadrante.EJES;
            if (x > 0 && y > 0) return TipoCuadrante.PRIMERO;
            if (x < 0 && y > 0) return TipoCuadrante.SEGUNDO;
            if (x < 0 && y < 0) return TipoCuadrante.TERCERO;
            return TipoCuadrante.CUARTO;
        }

        public TipoDiana zonaDiana() {
            double d = distanciaAlOrigen();
            if (d <= 1.0) return TipoDiana.CENTRO;
            if (d <= 3.0) return TipoDiana.CERCA;
            if (d <= 6.0) return TipoDiana.MEDIO;
            if (d <= 10.0) return TipoDiana.LEJOS;
            return TipoDiana.FUERA;
        }

        public Punto desplazar(double dx, double dy) {
            return new Punto(this.x + dx, this.y + dy);
        }

        public static Punto sumar(Punto p1, Punto p2) {
            return new Punto(p1.x + p2.x, p1.y + p2.y);
        }

        @Override
        public int compareTo(Punto otro) {
            int cmpX = Double.compare(this.x, otro.x);
            return (cmpX != 0) ? cmpX : Double.compare(this.y, otro.y);
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", x, y);
        }
    }

    public static class NubePuntos {
        private final Set<Punto> puntos;

        public NubePuntos(Set<Punto> puntos) {
            this.puntos = new HashSet<>(puntos);
        }

        public Set<Punto> getPuntos() {
            return Set.copyOf(puntos);
        }

        // 1. Contar puntos con abscisa mayor que xMin
        public long contarPuntosConAbscisaMayorQue(double xMin) {
            return puntos.stream()
                    .filter(p -> p.x() > xMin)
                    .count();
        }

        // 2. Contar puntos en un cuadrante específico
        public long contarPuntosEnCuadrante(TipoCuadrante c) {
            return puntos.stream()
                    .filter(p -> p.cuadrante() == c)
                    .count();
        }

        // 3. Contar puntos a una distancia menor que distMax respecto de ref
        public long contarPuntosADistanciaMenorQue(Punto ref, double distMax) {
            return puntos.stream()
                    .filter(p -> p.distanciaA(ref) < distMax)
                    .count();
        }

        // 4. Calcular el centro de gravedad global (mediaX, mediaY)
        public Optional<Punto> calcularCentroGravedad() {
            if (puntos.isEmpty()) return Optional.empty();
            DoubleSummaryStatistics statsX = puntos.stream().collect(Collectors.summarizingDouble(Punto::x));
            DoubleSummaryStatistics statsY = puntos.stream().collect(Collectors.summarizingDouble(Punto::y));
            return Optional.of(new Punto(statsX.getAverage(), statsY.getAverage()));
        }

        // 5. ¿Hay algún punto sobre los ejes de coordenadas?
        public boolean hayAlgunPuntoEnEjes() {
            return puntos.stream()
                    .anyMatch(p -> p.cuadrante() == TipoCuadrante.EJES);
        }

        // 6. ¿Hay algún punto dentro del círculo dado?
        public boolean hayAlgunPuntoEnCirculo(Punto centro, double radio) {
            return puntos.stream()
                    .anyMatch(p -> p.distanciaA(centro) <= radio);
        }

        // 7. ¿Están todos los puntos en el primer cuadrante?
        public boolean todosEnPrimerCuadrante() {
            return !puntos.isEmpty() && puntos.stream()
                    .allMatch(p -> p.cuadrante() == TipoCuadrante.PRIMERO);
        }

        // 8. Punto más lejano al origen (0, 0)
        public Optional<Punto> puntoMasLejanoAlOrigen() {
            return puntos.stream()
                    .max(Comparator.comparingDouble(Punto::distanciaAlOrigen));
        }

        // 9. Punto de la nube (distinto de ref) más cercano a ref
        public Optional<Punto> puntoMasCercanoA(Punto ref) {
            return puntos.stream()
                    .filter(p -> !p.equals(ref))
                    .min(Comparator.comparingDouble(p -> p.distanciaA(ref)));
        }

        // 10. Sub-nube con los puntos contenidos dentro de un círculo
        public NubePuntos subNubeEnCirculo(Punto centro, double radio) {
            Set<Punto> filtrados = puntos.stream()
                    .filter(p -> p.distanciaA(centro) <= radio)
                    .collect(Collectors.toSet());
            return new NubePuntos(filtrados);
        }

        // 11. Agrupar puntos por cuadrante en un SortedSet (TreeSet)
        public Map<TipoCuadrante, SortedSet<Punto>> agruparPorCuadranteOrdenado() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::cuadrante,
                            Collectors.toCollection(TreeSet::new)
                    ));
        }

        // 12. Contar puntos por cuadrante devolviendo un SortedMap (TreeMap)
        public SortedMap<TipoCuadrante, Long> contarPuntosPorCuadranteOrdenado() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::cuadrante,
                            TreeMap::new,
                            Collectors.counting()
                    ));
        }

        // 13. Clasificar puntos por zonas concéntricas de diana en un TreeMap
        public SortedMap<TipoDiana, Set<Punto>> clasificarEnDiana() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::zonaDiana,
                            TreeMap::new,
                            Collectors.toSet()
                    ));
        }

        // 14. Desplazar todos los puntos de la nube según un vector (dx, dy)
        public NubePuntos desplazarTodos(double dx, double dy) {
            Set<Punto> trasladados = puntos.stream()
                    .map(p -> p.desplazar(dx, dy))
                    .collect(Collectors.toSet());
            return new NubePuntos(trasladados);
        }

        // 15. Suma vectorial de puntos por cada cuadrante usando reducing
        public Map<TipoCuadrante, Punto> sumaVectorialPorCuadrante() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::cuadrante,
                            Collectors.reducing(Punto.ORIGEN, Punto::sumar)
                    ));
        }

        // 16. Punto más alejado del origen dentro de cada cuadrante
        public Map<TipoCuadrante, Optional<Punto>> puntoMasAlejadoPorCuadrante() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::cuadrante,
                            Collectors.maxBy(Comparator.comparingDouble(Punto::distanciaAlOrigen))
                    ));
        }

        // 17. Centro de gravedad por cada cuadrante
        public Map<TipoCuadrante, Punto> centroGravedadPorCuadrante() {
            return puntos.stream()
                    .collect(Collectors.groupingBy(
                            Punto::cuadrante,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    lista -> {
                                        double mx = lista.stream().mapToDouble(Punto::x).average().orElse(0.0);
                                        double my = lista.stream().mapToDouble(Punto::y).average().orElse(0.0);
                                        return new Punto(mx, my);
                                    }
                            )
                    ));
        }

        // 18. Construcción funcional a partir de un flujo de líneas de texto "x,y"
        public static NubePuntos leerDesdeLineas(Stream<String> lineas) {
            Set<Punto> leidos = lineas
                    .map(String::trim)
                    .filter(s -> !s.isEmpty() && !s.startsWith("#"))
                    .map(s -> s.split(","))
                    .filter(partes -> partes.length == 2)
                    .map(partes -> new Punto(
                            Double.parseDouble(partes[0].trim()),
                            Double.parseDouble(partes[1].trim())
                    ))
                    .collect(Collectors.toSet());
            return new NubePuntos(leidos);
        }
    }

    public static void main(String[] args) {
        List<String> datosFicheroSimulado = List.of(
                "# Nube de puntos de prueba",
                "0.5, 0.5",
                "2.0, 1.5",
                "4.0, 3.0",
                "-2.0, 2.0",
                "-5.0, -5.0",
                "3.0, -4.0",
                "0.0, 8.0"
        );

        NubePuntos nube = NubePuntos.leerDesdeLineas(datosFicheroSimulado.stream());

        System.out.println("Puntos cargados (" + nube.getPuntos().size() + "): " + nube.getPuntos());
        System.out.println("1. Con x > 1.0: " + nube.contarPuntosConAbscisaMayorQue(1.0));
        System.out.println("4. Centro de gravedad global: " + nube.calcularCentroGravedad().orElse(null));
        System.out.println("5. ¿Algún punto en ejes?: " + nube.hayAlgunPuntoEnEjes());
        System.out.println("8. Más lejano al origen: " + nube.puntoMasLejanoAlOrigen().orElse(null));
        System.out.println("12. Conteo por cuadrante (TreeMap): " + nube.contarPuntosPorCuadranteOrdenado());
        System.out.println("13. Clasificación en diana: " + nube.clasificarEnDiana());
        System.out.println("15. Suma vectorial por cuadrante: " + nube.sumaVectorialPorCuadrante());
        System.out.println("17. Centro de gravedad por cuadrante: " + nube.centroGravedadPorCuadrante());
    }
}
```

</details>

---

## Proyecto 2: Implementación de `ColectorPrimos` (`Collector<T, A, R>`) y Optimización Algorítmica

Cuando usamos `Collectors.partitioningBy(n -> esPrimo(n))`, el método `esPrimo(n)` no tiene acceso a la lista de números primos que ya hemos descubierto previamente, por lo que prueba todos los divisores enteros hasta `Math.sqrt(n)`.

Si en cambio diseñamos nuestro propio **`ColectorPrimos implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>>`**, el acumulador ya tiene en su poder la lista `acc.get(true)` con todos los números primos hallados hasta el momento. Así, para comprobar si el siguiente `candidato` es primo, basta con probar divisores **únicamente entre los números primos ya acumulados** cuyo cuadrado no supere a `candidato` (`takeWhile(p -> p * p <= candidato)`).

1. Implementa la clase `ColectorPrimos` sobrescribiendo los 5 métodos de la interfaz `Collector` (`supplier`, `accumulator`, `combiner`, `finisher` y `characteristics`).
2. Ejecuta un benchmark comparando el tiempo que tarda `Collectors.partitioningBy` frente a tu `new ColectorPrimos()` al clasificar el primer millón de números enteros.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Proyecto02ColectorPrimosPersonalizado {

    public static class ColectorPrimos implements
            Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        /**
         * Comprueba si candidato es primo dividiendo SOLO entre los primos ya descubiertos
         * que sean menores o iguales a la raíz cuadrada del candidato.
         */
        private static boolean esPrimoConListaPrevia(List<Integer> primosDescubiertos, int candidato) {
            int raiz = (int) Math.sqrt(candidato);
            return primosDescubiertos.stream()
                    .takeWhile(p -> p <= raiz)
                    .noneMatch(p -> candidato % p == 0);
        }

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> {
                Map<Boolean, List<Integer>> mapa = new HashMap<>();
                mapa.put(true, new ArrayList<>());
                mapa.put(false, new ArrayList<>());
                return mapa;
            };
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidato) -> {
                boolean esPrimo = esPrimoConListaPrevia(acc.get(true), candidato);
                acc.get(esPrimo).add(candidato);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> mapa1, Map<Boolean, List<Integer>> mapa2) -> {
                mapa1.get(true).addAll(mapa2.get(true));
                mapa1.get(false).addAll(mapa2.get(false));
                return mapa1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }

    public static boolean esPrimoEstandar(int candidato) {
        int raiz = (int) Math.sqrt(candidato);
        return IntStream.rangeClosed(2, raiz)
                .noneMatch(d -> candidato % d == 0);
    }

    public static void main(String[] args) {
        int limite = 500_000;

        long t0 = System.nanoTime();
        Map<Boolean, List<Integer>> resEstandar = IntStream.rangeClosed(2, limite)
                .boxed()
                .collect(Collectors.partitioningBy(Proyecto02ColectorPrimosPersonalizado::esPrimoEstandar));
        long msEstandar = (System.nanoTime() - t0) / 1_000_000;

        long t1 = System.nanoTime();
        Map<Boolean, List<Integer>> resPersonalizado = IntStream.rangeClosed(2, limite)
                .boxed()
                .collect(new ColectorPrimos());
        long msPersonalizado = (System.nanoTime() - t1) / 1_000_000;

        System.out.println("Primos hallados hasta " + limite + ": " + resPersonalizado.get(true).size());
        System.out.println("Tiempo partitioningBy estándar: " + msEstandar + " ms");
        System.out.println("Tiempo ColectorPrimos optimizado: " + msPersonalizado + " ms");
    }
}
```

</details>

---

## Proyecto 3: Simulación Concurrente de Supermercado (`ExecutorService`, `Callable`, `Future`) y Coordinación `wait/notify`

Implementa los dos escenarios clásicos de concurrencia vistos en el bloque de programación multiparadigma de la unidad:

1. **Línea de cajas de un supermercado con `ExecutorService` y `Callable<Long>`:**
   - Un `Cliente(String nombre, List<Integer> SegundosPorProducto)` lleva un carro con varios productos, cada uno de los cuales requiere ciertos milisegundos de escaneo.
   - Una `TareaCobro implements Callable<Long>` procesa el carro de un cliente en una caja y devuelve el tiempo total invertido en milisegundos.
   - Utiliza un pool de hilos fijo `Executors.newFixedThreadPool(2)` (2 cajeras trabajando en paralelo para atender a 4 clientes), recoge la lista de objetos `Future<Long>` mediante `invokeAll` e imprime el recibo de tiempos de cada cliente.
2. **Empaquetado automático con `Bolsa` e `HiloEnvio` (`wait` y `notify`):**
   - Una `Bolsa` tiene capacidad máxima para `5` productos (`estaLlena()`).
   - Un hilo consumidor `HiloEnvio` espera (`bolsa.wait()`) hasta que la bolsa se llena; en el instante en que el productor añade el quinto producto, despierta al hilo de envío con `bolsa.notify()` para despachar el paquete.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Proyecto03SupermercadoConcurrente {

    public record Cliente(String nombre, List<Integer> tiemposProductosMs) {}

    public record ResultadoCobro(String cliente, String hiloCajera, long tiempoTotalMs) {}

    public static class TareaCobro implements Callable<ResultadoCobro> {
        private final Cliente cliente;

        public TareaCobro(Cliente cliente) {
            this.cliente = cliente;
        }

        @Override
        public ResultadoCobro call() throws Exception {
            long inicio = System.currentTimeMillis();
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] Comienza a cobrar a " + cliente.nombre());

            for (int i = 0; i < cliente.tiemposProductosMs().size(); i++) {
                Thread.sleep(cliente.tiemposProductosMs().get(i));
            }

            long duracion = System.currentTimeMillis() - inicio;
            System.out.println("[" + nombreHilo + "] Finalizó cobro de " + cliente.nombre()
                    + " en " + duracion + " ms");
            return new ResultadoCobro(cliente.nombre(), nombreHilo, duracion);
        }
    }

    // Parte 2: Coordinación con wait() y notify() sobre Bolsa
    public static class Bolsa {
        private final List<String> productos = new ArrayList<>();
        private final int capacidadMaxima;

        public Bolsa(int capacidadMaxima) {
            this.capacidadMaxima = capacidadMaxima;
        }

        public synchronized void agregarProducto(String producto) {
            if (!estaLlena()) {
                productos.add(producto);
                System.out.println("   + Añadido a la bolsa: " + producto
                        + " (" + productos.size() + "/" + capacidadMaxima + ")");
                if (estaLlena()) {
                    notifyAll();
                }
            }
        }

        public synchronized boolean estaLlena() {
            return productos.size() >= capacidadMaxima;
        }

        public synchronized void esperarLlenadoYEnviar() throws InterruptedException {
            while (!estaLlena()) {
                wait();
            }
            System.out.println(">>> [HiloEnvio] ¡Bolsa llena! Enviando paquete con: " + productos);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. SIMULACIÓN DE CAJERAS CON EXECUTORSERVICE Y CALLABLE ===");
        List<Cliente> colaClientes = List.of(
                new Cliente("Cliente 1", List.of(100, 150, 100)),
                new Cliente("Cliente 2", List.of(200, 100)),
                new Cliente("Cliente 3", List.of(120, 120, 120)),
                new Cliente("Cliente 4", List.of(150, 150))
        );

        try (ExecutorService poolCajeras = Executors.newFixedThreadPool(2)) {
            List<TareaCobro> tareas = colaClientes.stream()
                    .map(TareaCobro::new)
                    .toList();

            List<Future<ResultadoCobro>> futuros = poolCajeras.invokeAll(tareas);

            System.out.println("\n--- Resumen de tickets cobrados ---");
            for (Future<ResultadoCobro> f : futuros) {
                ResultadoCobro r = f.get();
                System.out.println("   " + r.cliente() + " atendido por " + r.hiloCajera()
                        + " -> " + r.tiempoTotalMs() + " ms");
            }
        }

        System.out.println("\n=== 2. COORDINACIÓN BOLSA + HILO ENVÍO (WAIT / NOTIFY) ===");
        Bolsa bolsa = new Bolsa(5);
        Thread hiloEnvio = new Thread(() -> {
            try {
                bolsa.esperarLlenadoYEnviar();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "HiloEnvio");

        hiloEnvio.start();

        for (int i = 1; i <= 5; i++) {
            Thread.sleep(80);
            bolsa.agregarProducto("Producto-" + i);
        }
        hiloEnvio.join();
    }
}
```

</details>

---

## Proyecto 4: Agregador Asíncrono de Precios con `CompletableFuture<T>`

Construye un comparador de precios no bloqueante para una tienda online utilizando **`CompletableFuture`**:

1. Implementa un método `CompletableFuture<Double> consultarPrecioAsync(String tienda, String producto)` que consulte en segundo plano el precio de un artículo. Si el nombre de la tienda es `"TiendaCaida"`, simula un fallo de red lanzando una excepción y recupérala con `.exceptionally(ex -> Double.MAX_VALUE)` o `.handle(...)`.
2. Utiliza **`thenCombine`** para consultar en paralelo el precio de un portátil en `"Amazonia"` y el tipo de cambio EUR/USD en un servicio de divisas, combinando ambos resultados cuando los dos hayan finalizado.
3. Utiliza **`applyToEither`** para lanzar dos consultas redundantes a dos servidores espejo (`ServidorA` y `ServidorB`) y quedarte automáticamente con la respuesta del servidor que conteste **primero**.
4. Dada una lista de 5 tiendas, lanza todas las consultas simultáneamente mediante un Stream de `CompletableFuture`, espera a todas con `CompletableFuture.allOf(...)` y devuelve la oferta más barata válida.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Proyecto04AgregadorCompletableFuture {

    public record Oferta(String tienda, double precioEur) {}

    public static CompletableFuture<Oferta> consultarTiendaAsync(String tienda, double precioBase) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(80, 220));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if ("TiendaCaida".equalsIgnoreCase(tienda)) {
                throw new RuntimeException("Timeout de conexión en " + tienda);
            }
            return new Oferta(tienda, precioBase);
        }).exceptionally(ex -> {
            System.out.println("   [Aviso] Fallo recuperado en " + tienda + ": " + ex.getMessage());
            return new Oferta(tienda, Double.MAX_VALUE);
        });
    }

    public static void main(String[] args) {
        // 1 y 4. Consulta masiva en paralelo a 5 tiendas con allOf y join
        System.out.println("--- 1. Comparador paralelo en 5 tiendas (con tolerancia a fallos) ---");
        List<CompletableFuture<Oferta>> futurosOfertas = List.of(
                consultarTiendaAsync("PcComponentes", 949.0),
                consultarTiendaAsync("Amazonia", 925.0),
                consultarTiendaAsync("TiendaCaida", 899.0),
                consultarTiendaAsync("MediaShop", 960.0),
                consultarTiendaAsync("CoolTech", 915.0)
        );

        CompletableFuture.allOf(futurosOfertas.toArray(new CompletableFuture[0])).join();

        Oferta mejorOferta = futurosOfertas.stream()
                .map(CompletableFuture::join)
                .filter(o -> o.precioEur() < Double.MAX_VALUE)
                .min(Comparator.comparingDouble(Oferta::precioEur))
                .orElseThrow();
        System.out.println("Mejor oferta encontrada: " + mejorOferta);

        // 2. thenCombine: combinar precio en USD con tasa de cambio EUR/USD en paralelo
        System.out.println("\n--- 2. Combinación paralela Precio USD + Cambio Divisa (thenCombine) ---");
        CompletableFuture<Double> futuroPrecioUsd = CompletableFuture.supplyAsync(() -> 1000.0);
        CompletableFuture<Double> futuroTasaEur = CompletableFuture.supplyAsync(() -> 0.92);

        double precioFinalEur = futuroPrecioUsd
                .thenCombine(futuroTasaEur, (usd, tasa) -> usd * tasa)
                .join();
        System.out.printf("Precio convertido: %.2f EUR%n", precioFinalEur);

        // 3. applyToEither: quedarse con el servidor espejo más rápido
        System.out.println("\n--- 3. Carrera entre servidores espejo (applyToEither) ---");
        CompletableFuture<String> espejoEuropa = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(60); } catch (InterruptedException ignored) {}
            return "Respuesta desde Espejo-Europa (60 ms)";
        });
        CompletableFuture<String> espejoAmerica = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(180); } catch (InterruptedException ignored) {}
            return "Respuesta desde Espejo-América (180 ms)";
        });

        String ganador = espejoEuropa.applyToEither(espejoAmerica, r -> r).join();
        System.out.println("Servidor ganador: " + ganador);
    }
}
```

</details>

---

## Proyecto 5: Benchmark de Procesamiento Masivo `stream()` vs. `parallelStream()` sobre 1.000.000 de Empleados

Crea un banco de pruebas que genere en memoria una lista de **1.000.000 de registros** `Empleado(int id, String departamento, double salarioAnual, int antiguedad)` y compare el rendimiento de una tubería compleja de agregación ejecutada de forma **secuencial** (`stream()`) frente a su ejecución **paralela** (`parallelStream()` y `groupingByConcurrent`):

1. Calcula el salario medio y la suma salarial filtrando empleados con más de 5 años de antigüedad y aplicando una simulación de cálculo intensivo de retención fiscal por empleado.
2. Agrupa los 1.000.000 de empleados por departamento usando `Collectors.groupingBy` (secuencial) frente a `Collectors.groupingByConcurrent` (en un `ConcurrentMap` sobre `parallelStream()`).

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Proyecto05BenchmarkParallelStream {

    public record Empleado(int id, String departamento, double salario, int antiguedad) {}

    private static final String[] DEPARTAMENTOS = {"IT", "Ventas", "Finanzas", "RRHH", "I+D", "Logística"};

    /**
     * Simula un cálculo matemático de coste CPU moderado sobre el salario bruto.
     */
    public static double calcularSalarioNetoComplejo(Empleado e) {
        double base = e.salario();
        for (int i = 1; i <= 25; i++) {
            base = base * 0.9999 + Math.sqrt(base + i);
        }
        return base;
    }

    public static void main(String[] args) {
        int totalRegistros = 1_000_000;
        System.out.println("Generando " + totalRegistros + " empleados en memoria...");

        List<Empleado> empleados = IntStream.rangeClosed(1, totalRegistros)
                .mapToObj(i -> new Empleado(
                        i,
                        DEPARTAMENTOS[i % DEPARTAMENTOS.length],
                        24_000 + (i % 50_000),
                        i % 30
                ))
                .toList();

        // 1. Ejecución secuencial con stream()
        long t0 = System.nanoTime();
        Map<String, Double> mediaSecuencial = empleados.stream()
                .filter(e -> e.antiguedad() > 5)
                .collect(Collectors.groupingBy(
                        Empleado::departamento,
                        Collectors.averagingDouble(Proyecto05BenchmarkParallelStream::calcularSalarioNetoComplejo)
                ));
        long msSecuencial = (System.nanoTime() - t0) / 1_000_000;

        // 2. Ejecución multicore con parallelStream() + groupingByConcurrent
        long t1 = System.nanoTime();
        ConcurrentMap<String, Double> mediaParalela = empleados.parallelStream()
                .filter(e -> e.antiguedad() > 5)
                .collect(Collectors.groupingByConcurrent(
                        Empleado::departamento,
                        Collectors.averagingDouble(Proyecto05BenchmarkParallelStream::calcularSalarioNetoComplejo)
                ));
        long msParalelo = (System.nanoTime() - t1) / 1_000_000;

        System.out.println("Tiempo stream() secuencial:       " + msSecuencial + " ms");
        System.out.println("Tiempo parallelStream() multicore: " + msParalelo + " ms");
        System.out.println("Resultado verificado (IT): " + String.format("%.2f", mediaParalela.get("IT")));
    }
}
```

</details>

---

## Proyecto 6: Analizador Funcional de Archivos de Texto y Logs con `Files.lines`

El método `Files.lines(Path ruta, Charset cs)` del paquete `java.nio.file` devuelve un `Stream<String>` perezoso que lee un archivo línea a línea sin cargar todo el documento de golpe en la memoria RAM.

1. Utilizando un bloque **`try-with-resources`** (`try (Stream<String> lineas = Files.lines(ruta))`), implementa un analizador de vocabulario que:
   - Divida cada línea en palabras mediante `flatMap(linea -> Arrays.stream(linea.split("[^\\p{L}]+")))`.
   - Normalice todas las palabras a minúsculas y descarte cadenas vacías o de longitud menor que `3`.
   - Cuente cuántas **palabras únicas** contiene el archivo y devuelva el **Top 5 de palabras más frecuentes** utilizando `Collectors.groupingBy(Function.identity(), Collectors.counting())` seguido de un Stream sobre `entrySet()` ordenado de mayor a menor frecuencia.

<details>
<summary>👁️ Ver solución completa en Java 25 LTS</summary>

```java
package ud07.extras;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Proyecto06AnalizadorTextoFilesLines {

    public static void main(String[] args) throws IOException {
        // Creamos un archivo temporal de prueba para que el ejemplo sea 100% autónomo
        Path archivoTemp = Files.createTempFile("quijote_muestra_", ".txt");
        Files.writeString(archivoTemp, """
                En un lugar de la Mancha, de cuyo nombre no quiero acordarme,
                no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero,
                adarga antigua, rocín flaco y galgo corredor.
                La Mancha es tierra de molinos y de aventuras sin igual en la historia de la literatura.
                """, StandardCharsets.UTF_8);

        try (Stream<String> lineas = Files.lines(archivoTemp, StandardCharsets.UTF_8)) {
            Map<String, Long> frecuencias = lineas
                    .flatMap(linea -> Arrays.stream(linea.toLowerCase().split("[^\\p{L}]+")))
                    .filter(palabra -> palabra.length() >= 3)
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));

            System.out.println("Total de palabras únicas (>= 3 letras): " + frecuencias.size());

            List<Map.Entry<String, Long>> top5 = frecuencias.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .limit(5)
                    .toList();

            System.out.println("Top 5 palabras más repetidas:");
            top5.forEach(e -> System.out.println("   - '" + e.getKey() + "': " + e.getValue() + " apariciones"));
        } finally {
            Files.deleteIfExists(archivoTemp);
        }
    }
}
```

</details>

---

## 🏆 Retos Externos: Acepta el Reto y CodeWars

Pon a prueba tu dominio de la programación declarativa resolviendo estos desafíos clásicos en plataformas de programación competitiva:

### 🟢 CodeWars (Katas de Java Streams)

1. **[Square(n) Sum (8 kyu)](https://www.codewars.com/kata/515e271a311df0350d00000f/train/java)**
   - **Objetivo:** Dado un array `int[] n`, devuelve la suma de los cuadrados de cada número.
   - **Pista con Streams:** `Arrays.stream(n).map(x -> x * x).sum()`.
2. **[Find the odd int (6 kyu)](https://www.codewars.com/kata/54da5a58ea159efa38000836/train/java)**
   - **Objetivo:** En un array de enteros, todos aparecen un número par de veces excepto uno que aparece un número impar de veces. Encuéntralo.
   - **Pista con Streams:** Puedes agrupar con `Collectors.groupingBy(Function.identity(), Collectors.counting())` y filtrar la entrada cuyo valor sea impar, o aplicar la reducción XOR bit a bit: `Arrays.stream(a).reduce(0, (x, y) -> x ^ y)`.
3. **[Counting Duplicates (6 kyu)](https://www.codewars.com/kata/54bf1c2cd5b56cc47f0007a1/train/java)**
   - **Objetivo:** Cuenta cuántos caracteres alfanuméricos distintos (insensibles a mayúsculas/minúsculas) se repiten más de una vez en un `String`.
   - **Pista con Streams:** Usa `texto.toLowerCase().chars().boxed()`, agrupa con `groupingBy(..., counting())` y cuenta cuántas frecuencias del mapa cumplen `frecuencia > 1`.
4. **[Highest Scoring Word (6 kyu)](https://www.codewars.com/kata/57eb8fcdf670e99d9b000272/train/java)**
   - **Objetivo:** Dada una frase, cada letra puntúa según su posición en el alfabeto (`a = 1`, `b = 2`...). Devuelve la palabra con mayor puntuación.
   - **Pista con Streams:** `Arrays.stream(s.split(" ")).max(Comparator.comparingInt(w -> w.chars().map(c -> c - 'a' + 1).sum())).orElse("")`.

### 🔵 Acepta el Reto

1. **[109 — Liga de pádel](https://aceptaelreto.com/problem/statement.php?id=109)**
   - **Pista:** Acumula los puntos de cada pareja en un `Map<String, Integer>`, usa Streams sobre `entrySet()` para encontrar el máximo con `max(Map.Entry.comparingByValue())` y verifica con `filter` y `count()` si existe empate en la primera posición.
2. **[152 — Va de modas](https://aceptaelreto.com/problem/statement.php?id=152)**
   - **Pista:** Agrupa la secuencia de enteros con `Collectors.groupingBy(Function.identity(), Collectors.counting())` y extrae la clave con mayor frecuencia mediante `.entrySet().stream().max(Map.Entry.comparingByValue())`.
3. **[416 — Michael J. Fox y el Pato Donald (Cumpleaños repetidos)](https://aceptaelreto.com/problem/statement.php?id=416)**
   - **Pista:** Extrae únicamente el par `"día/mes"` de cada fecha, elimínale el año con `map`, y comprueba si hay duplicados comparando `distinct().count() < totalPersonas`.
