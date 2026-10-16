---
title: "Boletín 7 — Nivel Inicial (Resuelto)"
description: "Soluciones completas y comentadas en Java 25 LTS del Boletín Inicial de la Unidad 7: Optional<T>, flujos básicos, Fibonacci con iterate, consultas sobre Platos, Vehículos, Cursos, Espectadores y Escritores."
slug: ud07/boletin-07-inicial-resuelto
draft: true
prev: false
next: false
---

A continuación se presentan las soluciones completas en **Java 25 LTS** para los 12 ejercicios del Boletín Inicial de la Unidad 7. Cada solución incluye el código fuente ejecutable, explicación de las decisiones de diseño funcional y la salida esperada por consola.

---

## Ejercicio 1: Navegación segura sin `null` con `Optional<T>`

```java
package ud07.inicial;

import java.util.Optional;

public class Ejercicio01OptionalUSB {

    public record PuertoUSB(String version) {}

    public record TarjetaSonido(Optional<PuertoUSB> usb) {}

    public record Ordenador(String modelo, Optional<TarjetaSonido> tarjetaSonido) {}

    /**
     * Extrae la versión del puerto USB encadenando flatMap y map sin usar bloques if.
     */
    public static String obtenerVersionUSB(Optional<Ordenador> ordenador) {
        return ordenador
                .flatMap(Ordenador::tarjetaSonido)
                .flatMap(TarjetaSonido::usb)
                .map(PuertoUSB::version)
                .orElse("DESCONOCIDA");
    }

    /**
     * Comprueba si el ordenador dispone de un puerto USB cuya versión sea exactamente "3.2".
     */
    public static boolean esUSB32(Optional<Ordenador> ordenador) {
        return ordenador
                .flatMap(Ordenador::tarjetaSonido)
                .flatMap(TarjetaSonido::usb)
                .filter(u -> "3.2".equals(u.version()))
                .isPresent();
    }

    public static void main(String[] args) {
        // Caso 1: Ordenador completo con USB 3.2
        PuertoUSB usb32 = new PuertoUSB("3.2");
        TarjetaSonido soundcardCompleta = new TarjetaSonido(Optional.of(usb32));
        Optional<Ordenador> pcGaming = Optional.of(
                new Ordenador("Workstation Pro", Optional.of(soundcardCompleta))
        );

        // Caso 2: Ordenador con tarjeta de sonido pero sin puerto USB
        TarjetaSonido soundcardSinUsb = new TarjetaSonido(Optional.empty());
        Optional<Ordenador> pcOficina = Optional.of(
                new Ordenador("Oficina Basic", Optional.of(soundcardSinUsb))
        );

        // Caso 3: Optional de Ordenador vacío
        Optional<Ordenador> pcInexistente = Optional.empty();

        System.out.println("PC Gaming -> Versión USB: " + obtenerVersionUSB(pcGaming)
                + " | ¿Es 3.2?: " + esUSB32(pcGaming));
        System.out.println("PC Oficina -> Versión USB: " + obtenerVersionUSB(pcOficina)
                + " | ¿Es 3.2?: " + esUSB32(pcOficina));
        System.out.println("PC Inexistente -> Versión USB: " + obtenerVersionUSB(pcInexistente)
                + " | ¿Es 3.2?: " + esUSB32(pcInexistente));
    }
}
```

:::note[¿Por qué usamos `flatMap` en lugar de `map`?]
Tanto `Ordenador::tarjetaSonido` como `TarjetaSonido::usb` devuelven ya un objeto `Optional<...>`. Si usáramos `.map(...)`, obtendríamos un contenedor doblemente envuelto `Optional<Optional<TarjetaSonido>>`. La operación `flatMap` desenvuelve y aplana automáticamente el resultado para mantener un único nivel `Optional<T>`.
:::

---

## Ejercicio 2: Primeros pasos con `Stream<String>`

```java
package ud07.inicial;

import java.util.List;

public class Ejercicio02PrimerosStreams {

    public static void main(String[] args) {
        List<String> palabras = List.of("uno", "dos", "tres", "cuatro", "dos");

        // 1. Recorrido básico con forEach
        System.out.println("--- 1. Todos los elementos ---");
        palabras.stream()
                .forEach(System.out::println);

        // 2. Ordenación natural alfabética
        System.out.println("\n--- 2. Ordenados alfabéticamente ---");
        palabras.stream()
                .sorted()
                .forEach(System.out::println);

        // 3. Transformación a mayúsculas y ordenación
        System.out.println("\n--- 3. En mayúsculas y ordenados ---");
        palabras.stream()
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        // 4. Filtrar las que empiezan por "t" y pasar a mayúsculas
        System.out.println("\n--- 4. Empiezan por 't' en mayúsculas ---");
        palabras.stream()
                .filter(s -> s.startsWith("t"))
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // 5. Eliminar duplicados con distinct y recolectar en una lista
        List<String> sinDuplicados = palabras.stream()
                .distinct()
                .toList();
        System.out.println("\n--- 5. Lista sin duplicados ---");
        System.out.println(sinDuplicados);
    }
}
```

---

## Ejercicio 3: Operaciones funcionales sobre listas de números y cadenas

```java
package ud07.inicial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Ejercicio03MicroRetosFuncionales {

    public static void main(String[] args) {
        // 1. Filtrado de números pares
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("1. Números pares: " + pares);

        // 2. Incrementar cada número en 1 unidad
        List<Integer> base = List.of(10, 20, 30, 40);
        List<Integer> incrementados = base.stream()
                .map(n -> n + 1)
                .toList();
        System.out.println("2. Incrementados (+1): " + incrementados);

        // 3. Cuadrados en un Set (elimina duplicados como (-2)^2 y 2^2)
        List<Integer> conSigno = List.of(1, -2, 2, 3, -3, 4);
        Set<Integer> cuadradosUnicos = conSigno.stream()
                .map(n -> n * n)
                .collect(Collectors.toSet());
        System.out.println("3. Conjunto de cuadrados: " + cuadradosUnicos);

        // 4. Palabras que empiezan por 'A' convertidas a minúsculas
        List<String> vocabulario = List.of("Algoritmo", "bucle", "Array", "clase", "API", "stream");
        List<String> empiezanPorA = vocabulario.stream()
                .filter(p -> p.startsWith("A"))
                .map(String::toLowerCase)
                .toList();
        System.out.println("4. Empiezan por 'A' en minúsculas: " + empiezanPorA);

        // 5. Producto de los números estrictamente mayores que 5 usando reduce
        List<Integer> factores = List.of(2, 6, 3, 8, 4, 10);
        int productoMayoresQueCinco = factores.stream()
                .filter(n -> n > 5)
                .reduce(1, (a, b) -> a * b);
        System.out.println("5. Producto de mayores que 5 (6 x 8 x 10): " + productoMayoresQueCinco);
    }
}
```

---

## Ejercicio 4: Sucesión de Fibonacci y flujos infinitos (`iterate` y `generate`)

```java
package ud07.inicial;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejercicio04FibonacciYGeneradores {

    public static void main(String[] args) {
        // 1. Primeras 20 tuplas de Fibonacci (a, b)
        String tuplasFibonacci = Stream.iterate(
                        new long[]{0, 1},
                        t -> new long[]{t[1], t[0] + t[1]}
                )
                .limit(20)
                .map(t -> "(" + t[0] + ", " + t[1] + ")")
                .collect(Collectors.joining(", "));
        System.out.println("1. Primeras 20 tuplas:\n" + tuplasFibonacci);

        // 2. Lista con los 20 primeros números de Fibonacci
        List<Long> primeros20 = Stream.iterate(
                        new long[]{0, 1},
                        t -> new long[]{t[1], t[0] + t[1]}
                )
                .limit(20)
                .map(t -> t[0])
                .toList();
        System.out.println("\n2. Primeros 20 números de Fibonacci:\n" + primeros20);

        // 3. Términos de Fibonacci menores que 1.000 usando iterate con predicado (Java 9+)
        List<Long> menoresQueMil = Stream.iterate(
                        new long[]{0, 1},
                        t -> t[0] < 1_000,
                        t -> new long[]{t[1], t[0] + t[1]}
                )
                .map(t -> t[0])
                .toList();
        System.out.println("\n3. Fibonacci menores que 1.000:\n" + menoresQueMil);

        // 4. Cinco enteros aleatorios entre 1 y 100 ordenados de menor a mayor
        List<Integer> aleatoriosOrdenados = Stream.generate(
                        () -> ThreadLocalRandom.current().nextInt(1, 101)
                )
                .limit(5)
                .sorted()
                .toList();
        System.out.println("\n4. Cinco aleatorios [1..100] ordenados: " + aleatoriosOrdenados);
    }
}
```

---

## Ejercicio 5: Consultas sobre el menú de un restaurante (`Plato`)

```java
package ud07.inicial;

import java.util.Comparator;
import java.util.List;

public class Ejercicio05MenuRestaurante {

    public enum Tipo { CARNE, PESCADO, OTROS }

    public record Plato(String nombre, boolean vegetariano, int calorias, Tipo tipo) {}

    public static final List<Plato> MENU = List.of(
            new Plato("Cerdo asado", false, 800, Tipo.CARNE),
            new Plato("Ternera a la plancha", false, 700, Tipo.CARNE),
            new Plato("Pollo al limón", false, 400, Tipo.CARNE),
            new Plato("Patatas fritas", true, 530, Tipo.OTROS),
            new Plato("Arroz blanco", true, 350, Tipo.OTROS),
            new Plato("Fruta de temporada", true, 120, Tipo.OTROS),
            new Plato("Pizza margarita", true, 550, Tipo.OTROS),
            new Plato("Langostinos al ajillo", false, 300, Tipo.PESCADO),
            new Plato("Salmón al horno", false, 450, Tipo.PESCADO)
    );

    public static void main(String[] args) {
        // 1. Primeros 2 platos de carne del menú
        List<Plato> dosPrimerasCarnes = MENU.stream()
                .filter(p -> p.tipo() == Tipo.CARNE)
                .limit(2)
                .toList();
        System.out.println("1. Dos primeros platos de carne: " + dosPrimerasCarnes);

        // 2. Nombres de los platos vegetarianos ordenados de menor a mayor calorías
        List<String> vegetarianosPorCalorias = MENU.stream()
                .filter(Plato::vegetariano)
                .sorted(Comparator.comparingInt(Plato::calorias))
                .map(Plato::nombre)
                .toList();
        System.out.println("2. Vegetarianos ordenados por calorías: " + vegetarianosPorCalorias);

        // 3a. Suma total de calorías con IntStream
        int caloriasConIntStream = MENU.stream()
                .mapToInt(Plato::calorias)
                .sum();

        // 3b. Suma total de calorías con map + reduce
        int caloriasConReduce = MENU.stream()
                .map(Plato::calorias)
                .reduce(0, Integer::sum);

        System.out.println("3. Calorías totales (IntStream): " + caloriasConIntStream
                + " | (reduce): " + caloriasConReduce);

        // 4. Conteo de platos mediante patrón Map-Reduce
        int totalPlatosMapReduce = MENU.stream()
                .map(p -> 1)
                .reduce(0, Integer::sum);
        System.out.println("4. Número total de platos (Map-Reduce): " + totalPlatosMapReduce);
    }
}
```

---

## Ejercicio 6: Filtrado de `Persona` por edad y cálculo de descuentos en `Producto`

```java
package ud07.inicial;

import java.util.Comparator;
import java.util.List;

public class Ejercicio06PersonasYProductos {

    public record Persona(String nombre, int edad) {}

    public record Producto(String nombre, double precio, boolean enOferta) {}

    public static void main(String[] args) {
        // 1. Personas entre 20 y 30 años ordenadas por edad
        List<Persona> personas = List.of(
                new Persona("Lucía", 24),
                new Persona("Carlos", 18),
                new Persona("Marta", 30),
                new Persona("Jorge", 35),
                new Persona("Elena", 20),
                new Persona("Pablo", 27)
        );

        List<String> jovenesVeinteañeros = personas.stream()
                .filter(p -> p.edad() >= 20 && p.edad() <= 30)
                .sorted(Comparator.comparingInt(Persona::edad))
                .map(Persona::nombre)
                .toList();
        System.out.println("1. Personas entre 20 y 30 años: " + jovenesVeinteañeros);

        // 2. Importe total de productos en oferta aplicando un 15 % de descuento
        List<Producto> catalogo = List.of(
                new Producto("Teclado Mecánico", 100.0, true),
                new Producto("Ratón Gaming", 50.0, false),
                new Producto("Monitor 27 pulgadas", 200.0, true),
                new Producto("Alfombrilla XL", 20.0, true)
        );

        double totalOfertasConDescuento = catalogo.stream()
                .filter(Producto::enOferta)
                .mapToDouble(prod -> prod.precio() * 0.85)
                .sum();

        System.out.printf("2. Total productos en oferta (-15 %%): %.2f EUR%n", totalOfertasConDescuento);
    }
}
```

---

## Ejercicio 7: Análisis de una flota de vehículos (`Vehiculo`)

```java
package ud07.inicial;

import java.util.Comparator;
import java.util.List;

public class Ejercicio07FlotaVehiculos {

    public enum Marca { BMW, MERCEDES, AVENSIS, CITROEN, FORD, AUDI, OPEL }

    public record Vehiculo(String matricula, Marca marca, int kilometros) {}

    public static void main(String[] args) {
        List<Vehiculo> flota = List.of(
                new Vehiculo("1111AAA", Marca.BMW, 100_000),
                new Vehiculo("2222BBB", Marca.AUDI, 200_000),
                new Vehiculo("3333CCC", Marca.OPEL, 300_000),
                new Vehiculo("4444DDD", Marca.BMW, 100_000),
                new Vehiculo("5555EEE", Marca.AUDI, 200_000),
                new Vehiculo("6666FFF", Marca.OPEL, 300_000),
                new Vehiculo("7777GGG", Marca.CITROEN, 0)
        );

        // 1. Proyección de matrículas
        List<String> matriculas = flota.stream()
                .map(Vehiculo::matricula)
                .toList();
        System.out.println("1. Matrículas: " + matriculas);

        // 2. Kilómetros duplicados
        List<Integer> kmDuplicados = flota.stream()
                .map(v -> v.kilometros() * 2)
                .toList();
        System.out.println("2. Kilómetros x2: " + kmDuplicados);

        // 3. Filtrado exclusiva de vehículos AUDI
        List<Vehiculo> soloAudi = flota.stream()
                .filter(v -> v.marca() == Marca.AUDI)
                .toList();
        System.out.println("3. Vehículos AUDI: " + soloAudi);

        // 4. Ordenados por kilómetros de menor a mayor
        List<Vehiculo> ordenadosPorKm = flota.stream()
                .sorted(Comparator.comparingInt(Vehiculo::kilometros))
                .toList();
        System.out.println("4. Ordenados por kilometraje:");
        ordenadosPorKm.forEach(v -> System.out.println("   " + v));

        // 5. Marcas únicas sin repeticiones
        List<Marca> marcasUnicas = flota.stream()
                .map(Vehiculo::marca)
                .distinct()
                .toList();
        System.out.println("5. Marcas distintas: " + marcasUnicas);

        // 6. Comprobaciones con anyMatch y allMatch
        boolean hayKmCero = flota.stream().anyMatch(v -> v.kilometros() == 0);
        boolean todosMenosDeMedioMillon = flota.stream().allMatch(v -> v.kilometros() < 500_000);
        System.out.println("6. ¿Algún vehículo con 0 km?: " + hayKmCero
                + " | ¿Todos < 500.000 km?: " + todosMenosDeMedioMillon);
    }
}
```

---

## Ejercicio 8: Plataforma de formación online (`Curso` — Parte 1)

```java
package ud07.inicial;

import java.util.Comparator;
import java.util.List;

public class Ejercicio08CursosParte1 {

    public record Curso(String titulo, float duracion, int videos, int alumnos) {}

    public static List<Curso> obtenerCatalogo() {
        return List.of(
                new Curso("Cursos profesional de Java", 6.5f, 50, 200),
                new Curso("Cursos profesional de Python", 8.5f, 60, 800),
                new Curso("Cursos profesional de DB", 4.5f, 70, 700),
                new Curso("Cursos profesional de Android", 7.5f, 10, 400),
                new Curso("Cursos profesional de Escritura", 1.5f, 10, 300)
        );
    }

    public static void main(String[] args) {
        List<Curso> cursos = obtenerCatalogo();

        // 1. Cantidad de cursos con duración mayor a 5 horas
        long mayoresCincoHoras = cursos.stream()
                .filter(c -> c.duracion() > 5.0f)
                .count();
        System.out.println("1. Cursos de más de 5 horas: " + mayoresCincoHoras);

        // 2. Cantidad de cursos con duración menor a 2 horas
        long menoresDosHoras = cursos.stream()
                .filter(c -> c.duracion() < 2.0f)
                .count();
        System.out.println("2. Cursos de menos de 2 horas: " + menoresDosHoras);

        // 3. Títulos de cursos con más de 50 vídeos
        List<String> masDe50Videos = cursos.stream()
                .filter(c -> c.videos() > 50)
                .map(Curso::titulo)
                .toList();
        System.out.println("3. Cursos con > 50 vídeos: " + masDe50Videos);

        // 4. Título de los 3 cursos de mayor duración
        List<String> top3Duracion = cursos.stream()
                .sorted(Comparator.comparingDouble(Curso::duracion).reversed())
                .limit(3)
                .map(Curso::titulo)
                .toList();
        System.out.println("4. Top 3 cursos con mayor duración: " + top3Duracion);
    }
}
```

---

## Ejercicio 9: Plataforma de formación online (`Curso` — Parte 2)

```java
package ud07.inicial;

import ud07.inicial.Ejercicio08CursosParte1.Curso;
import java.util.Comparator;
import java.util.List;

public class Ejercicio09CursosParte2 {

    public static void main(String[] args) {
        List<Curso> cursos = Ejercicio08CursosParte1.obtenerCatalogo();

        // 1. Duración total de todos los cursos
        double duracionTotal = cursos.stream()
                .mapToDouble(Curso::duracion)
                .sum();
        System.out.println("1. Duración total del catálogo: " + duracionTotal + " horas");

        // 2. Cursos que superan la duración media global
        double duracionMedia = cursos.stream()
                .mapToDouble(Curso::duracion)
                .average()
                .orElse(0.0);
        System.out.printf("2. Duración media: %.2f h. Cursos que la superan:%n", duracionMedia);
        cursos.stream()
                .filter(c -> c.duracion() > duracionMedia)
                .forEach(c -> System.out.println("   - " + c.titulo() + " (" + c.duracion() + " h)"));

        // 3. Duración de todos los cursos con menos de 500 alumnos
        System.out.println("3. Duraciones de cursos con < 500 alumnos:");
        cursos.stream()
                .filter(c -> c.alumnos() < 500)
                .map(Curso::duracion)
                .forEach(d -> System.out.println("   - " + d + " h"));

        // 4. Curso con mayor duración
        cursos.stream()
                .max(Comparator.comparingDouble(Curso::duracion))
                .ifPresent(c -> System.out.println("4. Curso más largo: " + c));

        // 5. Lista de Strings con todos los títulos
        List<String> todosLosTitulos = cursos.stream()
                .map(Curso::titulo)
                .toList();
        System.out.println("5. Lista de todos los títulos: " + todosLosTitulos);
    }
}
```

---

## Ejercicio 10: Sorteo de premios por SMS en un Concurso de TV (`Espectador`)

```java
package ud07.inicial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Ejercicio10SorteoConcursoTV {

    public record Espectador(String nombre, String telefono) {}

    public static void main(String[] args) {
        List<Espectador> llamadas = List.of(
                new Espectador("Ana", "600111222"),
                new Espectador("Ana", "600111222"),       // Duplicado
                new Espectador("Luis", "12345"),          // Teléfono inválido (corto)
                new Espectador("Berta", "600333444"),
                new Espectador("Carlos", "600555666"),
                new Espectador("Diana", ""),              // Teléfono vacío
                new Espectador("Berta", "600333444"),     // Duplicado
                new Espectador("Emilio", "600777888"),
                new Espectador("Fátima", "600999000")
        );

        // Filtrar teléfonos válidos (9 dígitos numéricos) y eliminar duplicados
        List<String> participantesUnicos = llamadas.stream()
                .map(Espectador::telefono)
                .filter(tel -> tel != null && tel.matches("\\d{9}"))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Teléfonos válidos únicos en el bombo: " + participantesUnicos);

        // Barajar aleatoriamente y extraer los 3 ganadores con limit(3)
        Collections.shuffle(participantesUnicos);
        List<String> ganadores = participantesUnicos.stream()
                .limit(3)
                .toList();

        System.out.println("Los 3 teléfonos ganadores del sorteo son: " + ganadores);
    }
}
```

---

## Ejercicio 11: Dataset literario `Escritor` — Bloque Básico

```java
package ud07.inicial;

import java.time.Year;
import java.util.Comparator;
import java.util.List;

public class Ejercicio11EscritoresBasico {

    public record Escritor(
            String id,
            String nombre,
            String nacionalidad,
            int nace,
            Integer muere,
            String movimiento,
            List<String> titulos
    ) {
        public int edadAprox() {
            int fin = (muere != null) ? muere : Year.now().getValue();
            return fin - nace;
        }
    }

    public static List<Escritor> cargarEscritores() {
        return List.of(
                new Escritor("cervantes", "Miguel de Cervantes", "Española", 1547, 1616, "Siglo de Oro",
                        List.of("El Quijote", "La Galatea", "Novelas ejemplares")),
                new Escritor("quevedo", "Francisco de Quevedo", "Española", 1580, 1645, "Barroco",
                        List.of("El Buscón", "Los Sueños")),
                new Escritor("gongora", "Luis de Góngora", "Española", 1561, 1627, "Barroco",
                        List.of("Soledades", "Fábula de Polifemo y Galatea")),
                new Escritor("shakespeare", "William Shakespeare", "Inglesa", 1564, 1616, "Renacimiento",
                        List.of("Hamlet", "Macbeth", "Romeo y Julieta", "Otelo")),
                new Escritor("asimov", "Isaac Asimov", "Estadounidense", 1920, 1992, "Ciencia Ficción",
                        List.of("Fundación", "Yo, Robot", "Los propios dioses")),
                new Escritor("tolkien", "J.R.R. Tolkien", "Británica", 1892, 1973, "Fantasía",
                        List.of("El Hobbit", "El Señor de los Anillos", "El Silmarillion")),
                new Escritor("garciamarquez", "Gabriel García Márquez", "Colombiana", 1927, 2014, "Realismo Mágico",
                        List.of("Cien años de soledad", "El amor en los tiempos del cólera")),
                new Escritor("allende", "Isabel Allende", "Chilena", 1942, null, "Realismo Mágico",
                        List.of("La casa de los espíritus", "Eva Luna")),
                new Escritor("perezreverte", "Arturo Pérez-Reverte", "Española", 1951, null, "Novela Histórica",
                        List.of("El club Dumas", "El capitán Alatriste", "La Reina del Sur", "Falcó"))
        );
    }

    public static void main(String[] args) {
        List<Escritor> escritores = cargarEscritores();

        // 1. Nombres de los escritores españoles
        List<String> espanoles = escritores.stream()
                .filter(e -> "Española".equalsIgnoreCase(e.nacionalidad()))
                .map(Escritor::nombre)
                .toList();
        System.out.println("1. Escritores españoles: " + espanoles);

        // 2. Nombres en mayúsculas ordenados alfabéticamente
        List<String> mayusculasOrdenados = escritores.stream()
                .map(e -> e.nombre().toUpperCase())
                .sorted()
                .toList();
        System.out.println("2. En mayúsculas y ordenados: " + mayusculasOrdenados);

        // 3. Nacionalidades distintas y su conteo
        List<String> nacionalidades = escritores.stream()
                .map(Escritor::nacionalidad)
                .distinct()
                .toList();
        long numNacionalidades = escritores.stream()
                .map(Escritor::nacionalidad)
                .distinct()
                .count();
        System.out.println("3. Nacionalidades únicas (" + numNacionalidades + "): " + nacionalidades);

        // 4. Nacidos antes de 1900 y el escritor más antiguo
        List<String> antesDe1900 = escritores.stream()
                .filter(e -> e.nace() < 1900)
                .map(e -> e.nombre() + " (" + e.nace() + ")")
                .toList();
        System.out.println("4a. Nacidos antes de 1900: " + antesDe1900);

        escritores.stream()
                .min(Comparator.comparingInt(Escritor::nace))
                .ifPresent(masAntiguo -> System.out.println("4b. Escritor más antiguo: "
                        + masAntiguo.nombre() + " (" + masAntiguo.nace() + ")"));
    }
}
```

---

## Ejercicio 12: Dataset literario `Escritor` — Edades y Paginación (`skip` y `limit`)

```java
package ud07.inicial;

import ud07.inicial.Ejercicio11EscritoresBasico.Escritor;
import java.util.Comparator;
import java.util.List;

public class Ejercicio12EscritoresEdadesYPaginacion {

    public static void main(String[] args) {
        List<Escritor> escritores = Ejercicio11EscritoresBasico.cargarEscritores();

        // 1 y 2. Mostrar edad aproximada de cada escritor y suma total de años vividos
        System.out.println("--- 1. Edad aproximada de cada escritor ---");
        escritores.stream()
                .forEach(e -> System.out.println("   " + e.nombre() + ": " + e.edadAprox() + " años"));

        int sumaTotalEdades = escritores.stream()
                .mapToInt(Escritor::edadAprox)
                .sum();
        System.out.println("\n2. Suma total de años vividos por todos los escritores: " + sumaTotalEdades);

        // 3a. Primeros 5 escritores por año de nacimiento
        System.out.println("\n--- 3a. Primeros 5 escritores por año de nacimiento ---");
        escritores.stream()
                .sorted(Comparator.comparingInt(Escritor::nace))
                .limit(5)
                .forEach(e -> System.out.println("   " + e.nace() + " - " + e.nombre()));

        // 3b. Saltar los 3 primeros y mostrar los 4 siguientes
        System.out.println("\n--- 3b. Saltar 3 primeros y mostrar los 4 siguientes (skip(3).limit(4)) ---");
        escritores.stream()
                .sorted(Comparator.comparingInt(Escritor::nace))
                .skip(3)
                .limit(4)
                .forEach(e -> System.out.println("   " + e.nace() + " - " + e.nombre()));
    }
}
```
