---
title: "Boletín 7 — Nivel Intermedio"
description: "Ejercicios propuestos de nivel intermedio sobre la API Stream de Java: flatMap, producto cartesiano, takeWhile/dropWhile, auditoría financiera de Comercial y Transacción, agrupaciones multinivel con Collectors.groupingBy, gestión aeroportuaria e inversión funcional de mapas."
slug: ud07/boletin-07-intermedio
draft: true
prev: false
next: false
---

En este boletín de nivel intermedio profundizarás en las herramientas más potentes de la **API Stream de Java**: el aplanamiento de estructuras anidadas y productos cartesianos con `flatMap`, el recorte condicional en flujos ordenados con `takeWhile` y `dropWhile` (Java 9+), la generación de tripletas geométricas con `IntStream`, las agrupaciones multinivel y particiones con `Collectors.groupingBy` y `partitioningBy`, el uso de recolectores en cascada (*downstream collectors* como `mapping`, `collectingAndThen`, `summingInt`, `summarizingDouble`), la construcción avanzada de diccionarios con `toMap` (incluyendo resolución de colisiones de claves) y la inversión funcional de mapas sobre `entrySet().stream()`.

---

## Ejercicio 1: Producto cartesiano y parejas divisibles por 3 con `flatMap`

Dadas dos listas de números enteros:

```java
List<Integer> lista1 = List.of(1, 2, 3);
List<Integer> lista2 = List.of(3, 4);
```

1. **Producto cartesiano completo:** Utilizando dos llamadas encadenadas a `stream()` y `flatMap`, genera una `List<int[]>` con **todas las parejas posibles** `[a, b]` donde `a` pertenece a `lista1` y `b` pertenece a `lista2`:
   `[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]`.
2. **Parejas cuya suma es divisible por 3:** Modifica la tubería anterior intercalando un `filter` dentro del `flatMap` (o después de él) para devolver exclusivamente aquellas parejas cuya suma `(a + b)` sea múltiplo exacto de `3` (`(a + b) % 3 == 0`):
   `[(2, 4), (3, 3)]`.
3. **Caracteres únicos de una lista de palabras:** Dada la lista `List.of("Hola", "Mundo")`, utiliza `map(p -> p.split(""))`, `flatMap(Arrays::stream)` y `distinct()` para obtener la lista de letras individuales sin repetir: `["H", "o", "l", "a", "M", "u", "n", "d"]`.

---

## Ejercicio 2: Dataset literario `Escritor` — Aplanamiento de obras (`flatMap`) y Recorte Condicional (`takeWhile` / `dropWhile`)

Retomando la clase `Escritor` y el conjunto de datos `cargarEscritores()` del Boletín Inicial (donde cada `Escritor` contiene un atributo `List<String> titulos`):

1. **Catálogo global de obras con `flatMap`:** Aplana las listas de títulos de todos los escritores (`flatMap(e -> e.titulos().stream())`), elimina posibles duplicados y muestra por consola el catálogo completo de obras literarias ordenado alfabéticamente.
2. **Recorte condicional con `takeWhile` y `dropWhile`:**
   - Ordena previamente la lista de escritores por su año de nacimiento (`nace`) de menor a mayor.
   - Sobre el flujo ya ordenado cronológicamente, utiliza **`takeWhile(e -> e.nace() < 1900)`** para extraer los escritores anteriores al siglo XX deteniendo la evaluación en cuanto aparezca el primero del siglo XX.
   - Sobre otro flujo ordenado de la misma forma, utiliza **`dropWhile(e -> e.nace() < 1900)`** para descartar a los clásicos anteriores a 1900 y quedarte con los escritores nacidos a partir de 1900 inclusive.

---

## Ejercicio 3: Dataset literario `Escritor` — Máximos, Reducción de Cadenas, Agrupación y Partición

Continuando con el dataset `Escritor`:

1. **Escritor más prolífico (`max`):** Encuentra y muestra el escritor que tiene **mayor cantidad de obras publicadas** en su lista `titulos` utilizando `.max(Comparator.comparingInt(e -> e.titulos().size()))`.
2. **Concatenación de nombres (`reduce` vs. `joining`):** Obtén una única cadena `String` con los nombres de todos los escritores separados por `", "` de dos maneras:
   - Utilizando `.map(Escritor::nombre).reduce((a, b) -> a + ", " + b)`.
   - Utilizando `.map(Escritor::nombre).collect(Collectors.joining(", "))`.
3. **Agrupación por movimiento literario (`groupingBy`):** Agrupa a los escritores según su `movimiento` literario en un `Map<String, List<Escritor>>` y muestra qué autores pertenecen a cada corriente.
4. **Partición entre fallecidos y vivos (`partitioningBy`):** Divide la colección de escritores en dos grupos mediante un `Map<Boolean, List<Escritor>>` donde la clave `true` agrupe a los escritores ya fallecidos (`e.muere() != null`) y `false` agrupe a los escritores que siguen vivos (`e.muere() == null`).

---

## Ejercicio 4: Auditoría Financiera de `Comercial` y `Transaccion` — Parte 1 (Las 8 Consultas Clásicas)

En el departamento de auditoría de un banco de inversión se analizan las operaciones realizadas por un equipo de comerciales (`Comercial`) a través de la clase `Transaccion`:

```java
public record Comercial(String nombre, String ciudad) {}

public record Transaccion(Comercial comercial, int anio, int valor) {}
```

Con el siguiente juego de datos oficial:

```java
Comercial raul = new Comercial("Raúl", "Cambridge");
Comercial mario = new Comercial("Mario", "Milán");
Comercial alicia = new Comercial("Alicia", "Cambridge");
Comercial brian = new Comercial("Brian", "Cambridge");

List<Transaccion> transacciones = List.of(
    new Transaccion(brian, 2011, 300),
    new Transaccion(raul, 2012, 1000),
    new Transaccion(raul, 2011, 400),
    new Transaccion(mario, 2012, 710),
    new Transaccion(mario, 2012, 700),
    new Transaccion(alicia, 2012, 950)
);
```

Resuelve mediante Streams las **8 consultas clásicas de auditoría**:

1. Encuentra todas las transacciones del año **2011** y ordénalas de menor a mayor valor.
2. ¿Cuáles son todas las **ciudades únicas** (sin repetición) donde trabajan los comerciales?
3. Encuentra todos los comerciales que trabajan en `"Cambridge"` y ordénalos alfabéticamente por su nombre.
4. Devuelve una única cadena `String` con los nombres de todos los comerciales ordenados alfabéticamente.
5. ¿Existe algún comercial que trabaje en la ciudad de `"Milán"`? (Usa `anyMatch`).
6. Imprime los valores (`valor`) de todas las transacciones realizadas por los comerciales que residen en `"Cambridge"`.
7. ¿Cuál es el **valor más alto** de todas las transacciones registradas?
8. Encuentra la **transacción con el valor más pequeño** de toda la lista.

---

## Ejercicio 5: Auditoría Financiera de `Comercial` y `Transaccion` — Parte 2 (Agrupaciones Multinivel)

Sobre el mismo dataset de `Transaccion` del ejercicio anterior, genera los siguientes informes agregados empleando `Collectors`:

1. **Agrupación por ciudad:** Agrupa las transacciones en un `Map<String, List<Transaccion>>` cuya clave sea la ciudad del comercial (`t.comercial().ciudad()`).
2. **Partición por rentabilidad:** Particiona las transacciones en un `Map<Boolean, List<Transaccion>>` distinguiendo entre operaciones de **alto valor** (`valor >= 700` → `true`) y operaciones estándar (`valor < 700` → `false`).
3. **Agrupación multinivel (Ciudad + Rentabilidad):** Construye un `Map<String, Map<Boolean, List<Transaccion>>>` que agrupe primero las transacciones por ciudad y, dentro de cada ciudad, las particione en rentables (`>= 700`) y no rentables (`< 700`).
4. **Volumen total facturado por comercial (`summingInt`):** Obtén un `Map<String, Integer>` que asocie el nombre de cada comercial con la **suma total** de los valores de sus transacciones.

---

## Ejercicio 6: Agrupaciones Multinivel y *Downstream Collectors* sobre `Plato.MENU`

A partir de la lista `Plato.MENU` del Boletín Inicial, define el enumerado `enum NivelCalorico { DIETETICO, NORMAL, CALORICO }` siguiendo la regla:
- `<= 400` calorías → `DIETETICO`
- Entre `401` y `700` calorías → `NORMAL`
- `> 700` calorías → `CALORICO`

Implementa las siguientes recolecciones avanzadas:

1. **Agrupación de dos niveles (`Tipo` → `NivelCalorico`):** Obtén un `Map<Tipo, Map<NivelCalorico, List<Plato>>>` que clasifique los platos primero por su tipo (`CARNE`, `PESCADO`, `OTROS`) y en segundo nivel por su rango calórico.
2. **Plato más calórico de cada tipo sin `Optional` (`collectingAndThen`):** Obtén un `Map<Tipo, Plato>` con el plato de mayor número de calorías de cada categoría envolviendo `maxBy(Comparator.comparingInt(Plato::calorias))` con `Collectors.collectingAndThen(..., Optional::orElseThrow)`.
3. **Niveles calóricos disponibles por tipo (`mapping`):** Obtén un `Map<Tipo, Set<NivelCalorico>>` que indique qué niveles calóricos existen dentro de cada tipo de plato.
4. **Estadísticas completas por tipo (`summarizingInt`):** Obtén un `Map<Tipo, IntSummaryStatistics>` y muestra para cada tipo el conteo, la suma, el mínimo, la media y el máximo de calorías.

---

## Ejercicio 7: Inversión de relación muchos-a-muchos en `Empleado` con `flatMap` y `groupingBy`

En una empresa tecnológica un mismo empleado puede desempeñar simultáneamente varios puestos o roles:

```java
public record Empleado(String nombre, int edad, double sueldo, List<String> puestos) {}
```

Por ejemplo:
- `"Ana"` ocupa los puestos `["Backend", "Arquitecta", "DevOps"]`.
- `"Luis"` ocupa `["Frontend", "Backend"]`.
- `"Marta"` ocupa `["DevOps", "Seguridad"]`.
- `"Carlos"` ocupa `["Frontend", "UX"]`.

Escribe una tubería de Stream que **invierta la relación** para obtener un **`Map<String, List<String>>`** donde cada clave sea el nombre de un **puesto** (`"Backend"`, `"Frontend"`, `"DevOps"`, etc.) y su valor asociado sea la **lista de nombres de los empleados** que desempeñan dicho puesto.

:::tip[Pista: Tupla intermedia con `flatMap`]
Transforma cada `Empleado` mediante `flatMap` en un flujo de parejas `(puesto, nombreEmpleado)` —puedes usar un `record ParPuestoEmpleado(String puesto, String nombre)` o `Map.Entry<String, String>`— y después aplica `Collectors.groupingBy` con `Collectors.mapping`.
:::

---

## Ejercicio 8: Generador de Tripletas Pitagóricas con `IntStream`

Una **tripleta pitagórica** es un conjunto de tres números enteros positivos `(a, b, c)` que cumplen el teorema de Pitágoras: `a * a + b * b = c * c` (por ejemplo, `(3, 4, 5)` o `(5, 12, 13)`).

1. Utilizando `IntStream.rangeClosed(1, 100)` y `flatMap` (con `.boxed()` cuando sea necesario), genera todas las tripletas pitagóricas `int[]{a, b, c}` con `a` y `b` entre `1` y `100` (evitando duplicados exigiendo que `b >= a`).
2. Comprueba que la hipotenusa `Math.sqrt(a * a + b * b)` sea un número entero exacto verificando que `hipotenusa % 1 == 0`.
3. Muestra las **10 primeras tripletas pitagóricas** encontradas junto con su perímetro (`a + b + c`).
4. Filtra aquellas tripletas cuyo perímetro sea exactamente igual a `60` o `120`.

---

## Ejercicio 9: Gestión Aeroportuaria (`Vuelo`, `Duracion` y `Aeropuerto` — Parte 1)

Un aeropuerto gestiona sus operaciones diarias mediante los tipos `Duracion(int horas, int minutos)` y `Vuelo`:

```java
public record Duracion(int horas, int minutos) {
    public int enMinutos() {
        return horas * 60 + minutos;
    }

    public static Duracion suma(Duracion d1, Duracion d2) {
        int totalMin = d1.enMinutos() + d2.enMinutos();
        return new Duracion(totalMin / 60, totalMin % 60);
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
```

Implementa en una clase `Aeropuerto` (que encapsula una `List<Vuelo> vuelos`) los siguientes métodos usando **exclusivamente Streams**:

1. `long contarVuelosEnFecha(LocalDate fecha)`: devuelve cuántos vuelos salen en la fecha indicada.
2. `List<Vuelo> obtenerVuelosCompletos()`: devuelve la lista de vuelos cuyas plazas están completas (`Vuelo::estaCompleto`).
3. `boolean todosCompletosADestino(String destino)`: devuelve `true` si todos los vuelos hacia el destino indicado están completos (`allMatch`).
4. `Optional<Vuelo> vueloMasBaratoADestino(String destino)`: devuelve el vuelo de menor precio con destino a la ciudad indicada.
5. `double mayorPorcentajeOcupacion()`: devuelve el porcentaje de ocupación más alto registrado entre todos los vuelos (`mapToDouble` + `max`).
6. `double recaudacionTotal()` y `double recaudacionMediaADestino(String destino)`: calcula la facturación acumulada y la facturación media hacia un destino.
7. `Duracion duracionTotalVuelos()`: suma las duraciones de todos los vuelos empleando `.map(Vuelo::duracion).reduce(new Duracion(0, 0), Duracion::suma)`.

---

## Ejercicio 10: Gestión Aeroportuaria (`Vuelo` y `Aeropuerto` — Parte 2: Estadísticas y Mapas)

Amplía la clase `Aeropuerto` del ejercicio anterior con los siguientes métodos avanzados de recolección:

1. `double varianzaPrecios()`: calcula la **varianza estadística** de los precios de los vuelos (`media de los cuadrados - cuadrado de la media`). Obtén primero un `DoubleSummaryStatistics` con `Collectors.summarizingDouble(Vuelo::precio)` para conocer la media y el número de elementos `N`, o calcula la suma de `(precio - media)^2 / N` mediante un Stream.
2. `Map<String, String> mapaCodigoADestino()`: construye con `Collectors.toMap` un diccionario donde cada clave sea el `codigo` del vuelo y el valor sea su `destino`.
3. `Map<String, String> mapaDestinoACodigosConcatenados()`: construye con `Collectors.toMap(Vuelo::destino, Vuelo::codigo, (c1, c2) -> c1 + ", " + c2)` un diccionario que asocie cada destino con una cadena que concatene todos los códigos de vuelos que viajan a esa ciudad (resolviendo las colisiones de clave mediante la función de fusión `mergeFunction`).
4. `Map<LocalDate, Integer> pasajerosPorFecha()`: agrupa los vuelos por `fecha` y calcula el total de pasajeros que vuelan cada día (`Collectors.summingInt(Vuelo::numPasajeros)`).
5. `Map<String, Optional<Vuelo>> vueloMasBaratoPorDestino()`: obtiene para cada destino el vuelo más económico utilizando `Collectors.groupingBy` junto con `Collectors.reducing(BinaryOperator.minBy(Comparator.comparingDouble(Vuelo::precio)))` (o `Collectors.minBy`).

---

## Ejercicio 11: Inversión Funcional de Mapas y Destinos Estrella

1. **Inversión genérica de mapas:** Escribe dos métodos genéricos estáticos que inviertan cualquier `Map<K, V>` transformando sus entradas `map.entrySet().stream()` de modo que los antiguos valores `V` pasen a ser las claves del nuevo mapa y las antiguas claves `K` se agrupen en una colección:
   - `public static <K, V> Map<V, List<K>> invierteMapAList(Map<K, V> mapa)`
   - `public static <K, V> Map<V, Set<K>> invierteMapASet(Map<K, V> mapa)`
2. **Destinos con mayor número de vuelos (`getDestinosMasVuelos`):** En la clase `Aeropuerto`, implementa un método `Set<String> obtenerDestinosMasVuelos()` que:
   - Agrupe y cuente cuántos vuelos van a cada destino (`Map<String, Long>`).
   - Determine cuál es el número máximo de vuelos que recibe un destino.
   - Devuelva el conjunto (`Set<String>`) de todos los destinos que empatan en ese máximo número de vuelos.

---

## Ejercicio 12: Particionamiento Funcional de Números Primos y Compuestos

1. Implementa un método predicado auxiliar `public static boolean esPrimo(int candidato)` que compruebe mediante `IntStream.rangeClosed(2, (int) Math.sqrt(candidato)).noneMatch(divisor -> candidato % divisor == 0)` si un número entero mayor o igual que `2` es primo.
2. Implementa un método `public static Map<Boolean, List<Integer>> particionarPrimosHasta(int n)` que genere todos los enteros desde `2` hasta `n` con `IntStream.rangeClosed(2, n).boxed()` y los clasifique usando `Collectors.partitioningBy( Clase::esPrimo )`.
3. Muestra por consola cuántos números primos y cuántos compuestos existen hasta `n = 100`, así como la suma de todos los números primos menores o iguales que `100`.
