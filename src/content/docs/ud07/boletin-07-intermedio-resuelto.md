---
title: "Boletín 7 — Nivel Intermedio (Resuelto)"
description: "Soluciones completas y comentadas en Java 25 LTS del Boletín Intermedio de la Unidad 7: flatMap, takeWhile/dropWhile, Auditoría de Comercial y Transacción, agrupaciones multinivel, Aeropuerto e inversión de mapas."
slug: ud07/boletin-07-intermedio-resuelto
draft: true
prev: false
next: false
---

A continuación encontrarás las soluciones íntegras en **Java 25 LTS** para los 12 ejercicios del Boletín Intermedio de la Unidad 7.

---

## Ejercicio 1: Producto cartesiano y parejas divisibles por 3 con `flatMap`

```java
package ud07.intermedio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ejercicio01ProductoCartesianoFlatMap {

    public static void main(String[] args) {
        List<Integer> lista1 = List.of(1, 2, 3);
        List<Integer> lista2 = List.of(3, 4);

        // 1. Producto cartesiano completo: todas las parejas [a, b]
        List<int[]> todasLasParejas = lista1.stream()
                .flatMap(a -> lista2.stream()
                        .map(b -> new int[]{a, b})
                )
                .toList();

        String formatoParejas = todasLasParejas.stream()
                .map(par -> "(" + par[0] + ", " + par[1] + ")")
                .collect(Collectors.joining(", "));
        System.out.println("1. Todas las parejas: [" + formatoParejas + "]");

        // 2. Parejas cuya suma (a + b) es múltiplo exacto de 3
        List<int[]> divisiblesPor3 = lista1.stream()
                .flatMap(a -> lista2.stream()
                        .filter(b -> (a + b) % 3 == 0)
                        .map(b -> new int[]{a, b})
                )
                .toList();

        String formatoDiv3 = divisiblesPor3.stream()
                .map(par -> "(" + par[0] + ", " + par[1] + ")")
                .collect(Collectors.joining(", "));
        System.out.println("2. Parejas con suma divisible por 3: [" + formatoDiv3 + "]");

        // 3. Caracteres únicos de una lista de palabras
        List<String> palabras = List.of("Hola", "Mundo");
        List<String> letrasUnicas = palabras.stream()
                .map(p -> p.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .toList();
        System.out.println("3. Letras únicas sin repetir: " + letrasUnicas);
    }
}
```

---

## Ejercicio 2: Dataset literario `Escritor` — Aplanamiento de obras (`flatMap`) y Recorte Condicional (`takeWhile` / `dropWhile`)

```java
package ud07.intermedio;

import ud07.inicial.Ejercicio11EscritoresBasico;
import ud07.inicial.Ejercicio11EscritoresBasico.Escritor;
import java.util.Comparator;
import java.util.List;

public class Ejercicio02EscritoresFlatMapYTakeWhile {

    public static void main(String[] args) {
        List<Escritor> escritores = Ejercicio11EscritoresBasico.cargarEscritores();

        // 1. Aplanamiento de todas las listas de títulos con flatMap
        List<String> catalogoObras = escritores.stream()
                .flatMap(e -> e.titulos().stream())
                .distinct()
                .sorted()
                .toList();
        System.out.println("1. Catálogo global de obras (" + catalogoObras.size() + "):");
        catalogoObras.forEach(obra -> System.out.println("   - " + obra));

        // Ordenamos previamente los escritores por año de nacimiento para takeWhile / dropWhile
        List<Escritor> ordenadosPorNacimiento = escritores.stream()
                .sorted(Comparator.comparingInt(Escritor::nace))
                .toList();

        // 2a. takeWhile: tomar mientras hayan nacido antes de 1900
        List<String> anterioresSigloXX = ordenadosPorNacimiento.stream()
                .takeWhile(e -> e.nace() < 1900)
                .map(e -> e.nombre() + " (" + e.nace() + ")")
                .toList();
        System.out.println("\n2a. takeWhile (nacidos < 1900): " + anterioresSigloXX);

        // 2b. dropWhile: descartar mientras hayan nacido antes de 1900
        List<String> desdeSigloXX = ordenadosPorNacimiento.stream()
                .dropWhile(e -> e.nace() < 1900)
                .map(e -> e.nombre() + " (" + e.nace() + ")")
                .toList();
        System.out.println("2b. dropWhile (nacidos >= 1900): " + desdeSigloXX);
    }
}
```

---

## Ejercicio 3: Dataset literario `Escritor` — Máximos, Reducción de Cadenas, Agrupación y Partición

```java
package ud07.intermedio;

import ud07.inicial.Ejercicio11EscritoresBasico;
import ud07.inicial.Ejercicio11EscritoresBasico.Escritor;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ejercicio03EscritoresAgrupacionYParticion {

    public static void main(String[] args) {
        List<Escritor> escritores = Ejercicio11EscritoresBasico.cargarEscritores();

        // 1. Escritor con más obras publicadas
        escritores.stream()
                .max(Comparator.comparingInt(e -> e.titulos().size()))
                .ifPresent(prolifico -> System.out.println("1. Escritor con más obras: "
                        + prolifico.nombre() + " (" + prolifico.titulos().size() + " obras)"));

        // 2a. Concatenar nombres usando reduce
        String nombresConReduce = escritores.stream()
                .map(Escritor::nombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        System.out.println("\n2a. Nombres concatenados (reduce): " + nombresConReduce);

        // 2b. Concatenar nombres usando Collectors.joining
        String nombresConJoining = escritores.stream()
                .map(Escritor::nombre)
                .collect(Collectors.joining(", "));
        System.out.println("2b. Nombres concatenados (joining): " + nombresConJoining);

        // 3. Agrupación por movimiento literario
        Map<String, List<String>> porMovimiento = escritores.stream()
                .collect(Collectors.groupingBy(
                        Escritor::movimiento,
                        Collectors.mapping(Escritor::nombre, Collectors.toList())
                ));
        System.out.println("\n3. Escritores agrupados por movimiento literario:");
        porMovimiento.forEach((mov, autores) -> System.out.println("   " + mov + ": " + autores));

        // 4. Partición entre fallecidos (true) y vivos (false)
        Map<Boolean, List<String>> particionFallecidos = escritores.stream()
                .collect(Collectors.partitioningBy(
                        e -> e.muere() != null,
                        Collectors.mapping(Escritor::nombre, Collectors.toList())
                ));
        System.out.println("\n4. Partición de escritores:");
        System.out.println("   Fallecidos (true): " + particionFallecidos.get(true));
        System.out.println("   En activo / vivos (false): " + particionFallecidos.get(false));
    }
}
```

---

## Ejercicio 4: Auditoría Financiera de `Comercial` y `Transaccion` — Parte 1 (Las 8 Consultas Clásicas)

```java
package ud07.intermedio;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ejercicio04AuditoriaTransaccionesParte1 {

    public record Comercial(String nombre, String ciudad) {}

    public record Transaccion(Comercial comercial, int anio, int valor) {}

    public static List<Transaccion> cargarTransacciones() {
        Comercial raul = new Comercial("Raúl", "Cambridge");
        Comercial mario = new Comercial("Mario", "Milán");
        Comercial alicia = new Comercial("Alicia", "Cambridge");
        Comercial brian = new Comercial("Brian", "Cambridge");

        return List.of(
                new Transaccion(brian, 2011, 300),
                new Transaccion(raul, 2012, 1000),
                new Transaccion(raul, 2011, 400),
                new Transaccion(mario, 2012, 710),
                new Transaccion(mario, 2012, 700),
                new Transaccion(alicia, 2012, 950)
        );
    }

    public static void main(String[] args) {
        List<Transaccion> transacciones = cargarTransacciones();

        // 1. Transacciones de 2011 ordenadas por valor de menor a mayor
        List<Transaccion> de2011 = transacciones.stream()
                .filter(t -> t.anio() == 2011)
                .sorted(Comparator.comparingInt(Transaccion::valor))
                .toList();
        System.out.println("1. Transacciones de 2011 ordenadas por valor: " + de2011);

        // 2. Ciudades únicas donde trabajan los comerciales
        List<String> ciudadesUnicas = transacciones.stream()
                .map(t -> t.comercial().ciudad())
                .distinct()
                .toList();
        System.out.println("2. Ciudades únicas: " + ciudadesUnicas);

        // 3. Comerciales de Cambridge ordenados por nombre
        List<Comercial> deCambridge = transacciones.stream()
                .map(Transaccion::comercial)
                .filter(c -> "Cambridge".equals(c.ciudad()))
                .distinct()
                .sorted(Comparator.comparing(Comercial::nombre))
                .toList();
        System.out.println("3. Comerciales de Cambridge ordenados: " + deCambridge);

        // 4. Cadena con todos los nombres de comerciales ordenados alfabéticamente
        String nombresOrdenados = transacciones.stream()
                .map(t -> t.comercial().nombre())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println("4. Todos los comerciales: " + nombresOrdenados);

        // 5. ¿Hay algún comercial en Milán?
        boolean algunoEnMilan = transacciones.stream()
                .anyMatch(t -> "Milán".equals(t.comercial().ciudad()));
        System.out.println("5. ¿Algún comercial en Milán?: " + algunoEnMilan);

        // 6. Valores de las transacciones de los comerciales de Cambridge
        List<Integer> valoresCambridge = transacciones.stream()
                .filter(t -> "Cambridge".equals(t.comercial().ciudad()))
                .map(Transaccion::valor)
                .toList();
        System.out.println("6. Valores de transacciones en Cambridge: " + valoresCambridge);

        // 7. Valor más alto de todas las transacciones
        int valorMaximo = transacciones.stream()
                .mapToInt(Transaccion::valor)
                .max()
                .orElse(0);
        System.out.println("7. Valor máximo de transacción: " + valorMaximo);

        // 8. Transacción con el valor más pequeño
        Optional<Transaccion> transaccionMinima = transacciones.stream()
                .min(Comparator.comparingInt(Transaccion::valor));
        System.out.println("8. Transacción de menor valor: " + transaccionMinima.orElse(null));
    }
}
```

---

## Ejercicio 5: Auditoría Financiera de `Comercial` y `Transaccion` — Parte 2 (Agrupaciones Multinivel)

```java
package ud07.intermedio;

import ud07.intermedio.Ejercicio04AuditoriaTransaccionesParte1.Transaccion;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ejercicio05AuditoriaTransaccionesParte2 {

    public static void main(String[] args) {
        List<Transaccion> transacciones = Ejercicio04AuditoriaTransaccionesParte1.cargarTransacciones();

        // 1. Agrupar transacciones por ciudad
        Map<String, List<Transaccion>> porCiudad = transacciones.stream()
                .collect(Collectors.groupingBy(t -> t.comercial().ciudad()));
        System.out.println("1. Transacciones agrupadas por ciudad:");
        porCiudad.forEach((ciudad, lista) -> System.out.println("   " + ciudad + " -> " + lista.size() + " operaciones"));

        // 2. Particionar transacciones en rentables (>= 700) y estándar (< 700)
        Map<Boolean, List<Transaccion>> particionRentables = transacciones.stream()
                .collect(Collectors.partitioningBy(t -> t.valor() >= 700));
        System.out.println("\n2. Partición por rentabilidad (>= 700):");
        System.out.println("   Alto valor (true): " + particionRentables.get(true).size());
        System.out.println("   Estándar (false): " + particionRentables.get(false).size());

        // 3. Agrupación multinivel: por ciudad y dentro de cada ciudad por rentabilidad
        Map<String, Map<Boolean, List<Transaccion>>> porCiudadYRentabilidad = transacciones.stream()
                .collect(Collectors.groupingBy(
                        t -> t.comercial().ciudad(),
                        Collectors.partitioningBy(t -> t.valor() >= 700)
                ));
        System.out.println("\n3. Agrupación multinivel (Ciudad -> Rentabilidad >= 700):");
        porCiudadYRentabilidad.forEach((ciudad, mapaRent) ->
                System.out.println("   " + ciudad + " -> Rentables: " + mapaRent.get(true)
                        + " | No rentables: " + mapaRent.get(false))
        );

        // 4. Volumen total facturado por cada comercial
        Map<String, Integer> totalPorComercial = transacciones.stream()
                .collect(Collectors.groupingBy(
                        t -> t.comercial().nombre(),
                        Collectors.summingInt(Transaccion::valor)
                ));
        System.out.println("\n4. Volumen total facturado por comercial: " + totalPorComercial);
    }
}
```

---

## Ejercicio 6: Agrupaciones Multinivel y *Downstream Collectors* sobre `Plato.MENU`

```java
package ud07.intermedio;

import ud07.inicial.Ejercicio05MenuRestaurante;
import ud07.inicial.Ejercicio05MenuRestaurante.Plato;
import ud07.inicial.Ejercicio05MenuRestaurante.Tipo;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Ejercicio06DownstreamCollectorsPlatos {

    public enum NivelCalorico { DIETETICO, NORMAL, CALORICO }

    public static NivelCalorico clasificarCalorias(Plato p) {
        if (p.calorias() <= 400) return NivelCalorico.DIETETICO;
        if (p.calorias() <= 700) return NivelCalorico.NORMAL;
        return NivelCalorico.CALORICO;
    }

    public static void main(String[] args) {
        List<Plato> menu = Ejercicio05MenuRestaurante.MENU;

        // 1. Agrupación de dos niveles: Tipo -> NivelCalorico
        Map<Tipo, Map<NivelCalorico, List<Plato>>> porTipoYNivel = menu.stream()
                .collect(Collectors.groupingBy(
                        Plato::tipo,
                        Collectors.groupingBy(Ejercicio06DownstreamCollectorsPlatos::clasificarCalorias)
                ));
        System.out.println("1. Agrupación multinivel (Tipo -> NivelCalorico):");
        porTipoYNivel.forEach((tipo, submapa) -> System.out.println("   " + tipo + " -> " + submapa));

        // 2. Plato más calórico de cada tipo desenvolviendo el Optional con collectingAndThen
        Map<Tipo, Plato> masCaloricoPorTipo = menu.stream()
                .collect(Collectors.groupingBy(
                        Plato::tipo,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Plato::calorias)),
                                Optional::orElseThrow
                        )
                ));
        System.out.println("\n2. Plato más calórico por tipo: " + masCaloricoPorTipo);

        // 3. Niveles calóricos presentes en cada tipo con mapping y toCollection
        Map<Tipo, Set<NivelCalorico>> nivelesPorTipo = menu.stream()
                .collect(Collectors.groupingBy(
                        Plato::tipo,
                        Collectors.mapping(
                                Ejercicio06DownstreamCollectorsPlatos::clasificarCalorias,
                                Collectors.toCollection(HashSet::new)
                        )
                ));
        System.out.println("\n3. Niveles calóricos por tipo: " + nivelesPorTipo);

        // 4. Estadísticas completas por tipo con summarizingInt
        Map<Tipo, IntSummaryStatistics> estadisticasPorTipo = menu.stream()
                .collect(Collectors.groupingBy(
                        Plato::tipo,
                        Collectors.summarizingInt(Plato::calorias)
                ));
        System.out.println("\n4. Estadísticas por tipo de plato:");
        estadisticasPorTipo.forEach((tipo, stats) -> System.out.println("   " + tipo + " -> " + stats));
    }
}
```

---

## Ejercicio 7: Inversión de relación muchos-a-muchos en `Empleado` con `flatMap` y `groupingBy`

```java
package ud07.intermedio;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ejercicio07EmpleadosPorPuesto {

    public record Empleado(String nombre, int edad, double sueldo, List<String> puestos) {}

    public record Asignacion(String puesto, String nombreEmpleado) {}

    public static void main(String[] args) {
        List<Empleado> plantilla = List.of(
                new Empleado("Ana", 32, 48_000, List.of("Backend", "Arquitecta", "DevOps")),
                new Empleado("Luis", 27, 36_000, List.of("Frontend", "Backend")),
                new Empleado("Marta", 35, 52_000, List.of("DevOps", "Seguridad")),
                new Empleado("Carlos", 29, 38_000, List.of("Frontend", "UX"))
        );

        // Invertimos Empleado -> List<Puesto> a Puesto -> List<NombreEmpleado>
        Map<String, List<String>> empleadosPorPuesto = plantilla.stream()
                .flatMap(emp -> emp.puestos().stream()
                        .map(puesto -> new Asignacion(puesto, emp.nombre()))
                )
                .collect(Collectors.groupingBy(
                        Asignacion::puesto,
                        Collectors.mapping(Asignacion::nombreEmpleado, Collectors.toList())
                ));

        System.out.println("Mapa invertido (Puesto -> Empleados):");
        empleadosPorPuesto.forEach((puesto, nombres) ->
                System.out.println("   " + puesto + ": " + nombres)
        );
    }
}
```

---

## Ejercicio 8: Generador de Tripletas Pitagóricas con `IntStream`

```java
package ud07.intermedio;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ejercicio08TripletasPitagoricas {

    public static Stream<int[]> generarTripletas(int limite) {
        return IntStream.rangeClosed(1, limite)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, limite)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0)
                        .map(t -> new int[]{(int) t[0], (int) t[1], (int) t[2]})
                );
    }

    public static void main(String[] args) {
        // 1, 2 y 3. Primeras 10 tripletas pitagóricas con su perímetro
        System.out.println("--- 10 primeras tripletas pitagóricas en [1..100] ---");
        generarTripletas(100)
                .limit(10)
                .forEach(t -> {
                    int perimetro = t[0] + t[1] + t[2];
                    System.out.printf("   (%d, %d, %d) -> Perímetro = %d%n", t[0], t[1], t[2], perimetro);
                });

        // 4. Tripletas cuyo perímetro sea exactamente 60 o 120
        System.out.println("\n--- Tripletas con perímetro 60 o 120 ---");
        List<int[]> perimetroEspecial = generarTripletas(100)
                .filter(t -> {
                    int p = t[0] + t[1] + t[2];
                    return p == 60 || p == 120;
                })
                .toList();

        perimetroEspecial.forEach(t ->
                System.out.printf("   (%d, %d, %d) -> Perímetro = %d%n",
                        t[0], t[1], t[2], t[0] + t[1] + t[2])
        );
    }
}
```

---

## Ejercicio 9: Gestión Aeroportuaria (`Vuelo`, `Duracion` y `Aeropuerto` — Parte 1)

```java
package ud07.intermedio;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Ejercicio09AeropuertoParte1 {

    public record Duracion(int horas, int minutos) {
        public int enMinutos() {
            return horas * 60 + minutos;
        }

        public static Duracion suma(Duracion d1, Duracion d2) {
            int total = d1.enMinutos() + d2.enMinutos();
            return new Duracion(total / 60, total % 60);
        }

        @Override
        public String toString() {
            return horas + "h " + minutos + "m";
        }
    }

    public record Vuelo(
            String codigo,
            String destino,
            LocalDate fecha,
            double precio,
            int plazasTotales,
            int numPasajeros,
            Duracion duracion
    ) {
        public boolean estaCompleto() {
            return numPasajeros >= plazasTotales;
        }

        public double porcentajeOcupacion() {
            return (numPasajeros * 100.0) / plazasTotales;
        }

        public double recaudacion() {
            return numPasajeros * precio;
        }
    }

    public static class Aeropuerto {
        private final List<Vuelo> vuelos;

        public Aeropuerto(List<Vuelo> vuelos) {
            this.vuelos = List.copyOf(vuelos);
        }

        public List<Vuelo> getVuelos() {
            return vuelos;
        }

        // 1. Contar vuelos en una fecha
        public long contarVuelosEnFecha(LocalDate fecha) {
            return vuelos.stream()
                    .filter(v -> v.fecha().equals(fecha))
                    .count();
        }

        // 2. Obtener lista de vuelos completos
        public List<Vuelo> obtenerVuelosCompletos() {
            return vuelos.stream()
                    .filter(Vuelo::estaCompleto)
                    .toList();
        }

        // 3. ¿Están completos todos los vuelos hacia un destino?
        public boolean todosCompletosADestino(String destino) {
            return vuelos.stream()
                    .filter(v -> v.destino().equalsIgnoreCase(destino))
                    .allMatch(Vuelo::estaCompleto);
        }

        // 4. Vuelo más barato hacia un destino
        public Optional<Vuelo> vueloMasBaratoADestino(String destino) {
            return vuelos.stream()
                    .filter(v -> v.destino().equalsIgnoreCase(destino))
                    .min(Comparator.comparingDouble(Vuelo::precio));
        }

        // 5. Mayor porcentaje de ocupación
        public double mayorPorcentajeOcupacion() {
            return vuelos.stream()
                    .mapToDouble(Vuelo::porcentajeOcupacion)
                    .max()
                    .orElse(0.0);
        }

        // 6a. Recaudación total acumulada
        public double recaudacionTotal() {
            return vuelos.stream()
                    .mapToDouble(Vuelo::recaudacion)
                    .sum();
        }

        // 6b. Recaudación media hacia un destino
        public double recaudacionMediaADestino(String destino) {
            return vuelos.stream()
                    .filter(v -> v.destino().equalsIgnoreCase(destino))
                    .mapToDouble(Vuelo::recaudacion)
                    .average()
                    .orElse(0.0);
        }

        // 7. Suma total de duraciones con reduce
        public Duracion duracionTotalVuelos() {
            return vuelos.stream()
                    .map(Vuelo::duracion)
                    .reduce(new Duracion(0, 0), Duracion::suma);
        }
    }

    public static List<Vuelo> cargarVuelosPrueba() {
        LocalDate hoy = LocalDate.of(2025, 5, 10);
        LocalDate manana = LocalDate.of(2025, 5, 11);
        return List.of(
                new Vuelo("IB101", "Londres", hoy, 120.0, 180, 180, new Duracion(2, 30)),
                new Vuelo("RY202", "Londres", manana, 65.0, 180, 175, new Duracion(2, 35)),
                new Vuelo("VY303", "París", hoy, 95.0, 150, 150, new Duracion(1, 55)),
                new Vuelo("AF404", "París", manana, 140.0, 150, 150, new Duracion(2, 0)),
                new Vuelo("LH505", "Berlín", hoy, 160.0, 200, 140, new Duracion(3, 10)),
                new Vuelo("IB606", "Londres", hoy, 110.0, 180, 160, new Duracion(2, 25))
        );
    }

    public static void main(String[] args) {
        Aeropuerto aero = new Aeropuerto(cargarVuelosPrueba());
        LocalDate hoy = LocalDate.of(2025, 5, 10);

        System.out.println("1. Vuelos el 2025-05-10: " + aero.contarVuelosEnFecha(hoy));
        System.out.println("2. Vuelos completos: " + aero.obtenerVuelosCompletos().size());
        System.out.println("3. ¿Todos completos a París?: " + aero.todosCompletosADestino("París"));
        System.out.println("4. Más barato a Londres: " + aero.vueloMasBaratoADestino("Londres").orElse(null));
        System.out.printf("5. Mayor ocupación: %.2f %%%n", aero.mayorPorcentajeOcupacion());
        System.out.printf("6. Recaudación total: %.2f EUR | Media Londres: %.2f EUR%n",
                aero.recaudacionTotal(), aero.recaudacionMediaADestino("Londres"));
        System.out.println("7. Duración total acumulada: " + aero.duracionTotalVuelos());
    }
}
```

---

## Ejercicio 10: Gestión Aeroportuaria (`Vuelo` y `Aeropuerto` — Parte 2: Estadísticas y Mapas)

```java
package ud07.intermedio;

import ud07.intermedio.Ejercicio09AeropuertoParte1.Vuelo;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Ejercicio10AeropuertoParte2 {

    public static void main(String[] args) {
        List<Vuelo> vuelos = Ejercicio09AeropuertoParte1.cargarVuelosPrueba();

        // 1. Varianza estadística de los precios: E[X^2] - (E[X])^2
        DoubleSummaryStatistics stats = vuelos.stream()
                .collect(Collectors.summarizingDouble(Vuelo::precio));
        double media = stats.getAverage();
        double varianza = vuelos.stream()
                .mapToDouble(v -> Math.pow(v.precio() - media, 2))
                .average()
                .orElse(0.0);
        System.out.printf("1. Precio medio: %.2f EUR | Varianza: %.2f%n", media, varianza);

        // 2. Mapa de código de vuelo a destino
        Map<String, String> codigoADestino = vuelos.stream()
                .collect(Collectors.toMap(Vuelo::codigo, Vuelo::destino));
        System.out.println("2. Mapa Código -> Destino: " + codigoADestino);

        // 3. Mapa de destino a códigos concatenados usando mergeFunction
        Map<String, String> destinoACodigos = vuelos.stream()
                .collect(Collectors.toMap(
                        Vuelo::destino,
                        Vuelo::codigo,
                        (c1, c2) -> c1 + ", " + c2
                ));
        System.out.println("3. Mapa Destino -> Códigos concatenados: " + destinoACodigos);

        // 4. Total de pasajeros agrupados por fecha
        Map<LocalDate, Integer> pasajerosPorDia = vuelos.stream()
                .collect(Collectors.groupingBy(
                        Vuelo::fecha,
                        Collectors.summingInt(Vuelo::numPasajeros)
                ));
        System.out.println("4. Pasajeros por fecha: " + pasajerosPorDia);

        // 5. Vuelo más barato por cada destino con reducing + BinaryOperator.minBy
        Map<String, Optional<Vuelo>> masBaratoPorDestino = vuelos.stream()
                .collect(Collectors.groupingBy(
                        Vuelo::destino,
                        Collectors.reducing(
                                BinaryOperator.minBy(Comparator.comparingDouble(Vuelo::precio))
                        )
                ));
        System.out.println("5. Vuelo más barato por destino:");
        masBaratoPorDestino.forEach((dest, optVuelo) ->
                System.out.println("   " + dest + " -> " + optVuelo.map(Vuelo::codigo).orElse("N/A"))
        );
    }
}
```

---

## Ejercicio 11: Inversión Funcional de Mapas y Destinos Estrella

```java
package ud07.intermedio;

import ud07.intermedio.Ejercicio09AeropuertoParte1.Vuelo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Ejercicio11InversionMapasYDestinosEstrella {

    /**
     * Invierte un Map<K, V> en un Map<V, List<K>> agrupando las claves que compartían valor.
     */
    public static <K, V> Map<V, List<K>> invierteMapAList(Map<K, V> mapa) {
        return mapa.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
    }

    /**
     * Invierte un Map<K, V> en un Map<V, Set<K>>.
     */
    public static <K, V> Map<V, Set<K>> invierteMapASet(Map<K, V> mapa) {
        return mapa.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toSet())
                ));
    }

    /**
     * Obtiene el conjunto de destinos que empatan en el mayor número de vuelos.
     */
    public static Set<String> obtenerDestinosMasVuelos(List<Vuelo> vuelos) {
        Map<String, Long> conteoPorDestino = vuelos.stream()
                .collect(Collectors.groupingBy(Vuelo::destino, Collectors.counting()));

        long maxVuelos = conteoPorDestino.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);

        return conteoPorDestino.entrySet().stream()
                .filter(entry -> entry.getValue() == maxVuelos)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<Vuelo> vuelos = Ejercicio09AeropuertoParte1.cargarVuelosPrueba();

        Map<String, String> codigoADestino = vuelos.stream()
                .collect(Collectors.toMap(Vuelo::codigo, Vuelo::destino));

        System.out.println("1a. Inversión a List (Destino -> Lista de Códigos): "
                + invierteMapAList(codigoADestino));
        System.out.println("1b. Inversión a Set (Destino -> Set de Códigos): "
                + invierteMapASet(codigoADestino));

        System.out.println("2. Destino(s) con mayor número de vuelos: "
                + obtenerDestinosMasVuelos(vuelos));
    }
}
```

---

## Ejercicio 12: Particionamiento Funcional de Números Primos y Compuestos

```java
package ud07.intermedio;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ejercicio12ParticionPrimos {

    public static boolean esPrimo(int candidato) {
        if (candidato < 2) return false;
        int limiteRaiz = (int) Math.sqrt(candidato);
        return IntStream.rangeClosed(2, limiteRaiz)
                .noneMatch(divisor -> candidato % divisor == 0);
    }

    public static Map<Boolean, List<Integer>> particionarPrimosHasta(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(Collectors.partitioningBy(Ejercicio12ParticionPrimos::esPrimo));
    }

    public static void main(String[] args) {
        Map<Boolean, List<Integer>> clasificacion = particionarPrimosHasta(100);
        List<Integer> primos = clasificacion.get(true);
        List<Integer> compuestos = clasificacion.get(false);

        int sumaPrimos = primos.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Primos hasta 100 (" + primos.size() + "): " + primos);
        System.out.println("Cantidad de compuestos hasta 100: " + compuestos.size());
        System.out.println("Suma total de los primos <= 100: " + sumaPrimos);
    }
}
```
