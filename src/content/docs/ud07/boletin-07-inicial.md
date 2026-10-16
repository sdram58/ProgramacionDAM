---
title: "Boletín 7 — Nivel Inicial"
description: "Ejercicios propuestos de nivel inicial sobre programación funcional en Java: Optional<T>, creación de Streams, operaciones intermedias y terminales básicas, flujos numéricos y consultas sobre colecciones de objetos (Platos, Vehículos, Cursos, Espectadores y Escritores)."
slug: ud07/boletin-07-inicial
draft: true
prev: false
next: false
---

En este boletín inicial pondrás en práctica los fundamentos de la **Programación Funcional y la API Stream en Java**: el uso de `Optional<T>` para erradicar el temido `NullPointerException`, la creación de flujos a partir de colecciones, arrays y generadores infinitos (`iterate` y `generate`), las operaciones intermedias de filtrado, recorte y proyección (`filter`, `distinct`, `limit`, `skip`, `map`, `sorted`), las operaciones terminales de búsqueda y reducción (`forEach`, `count`, `min`, `max`, `anyMatch`, `allMatch`, `reduce`, `collect`) y las especializaciones numéricas (`IntStream`, `DoubleStream`).

:::tip[Consejo de resolución en IntelliJ IDEA]
Crea un paquete `ud07.inicial` dentro de tu proyecto de IntelliJ IDEA. Intenta resolver todos los apartados utilizando **exclusivamente tuberías de Streams** y referencias a métodos (`Clase::metodo`), evitando escribir bucles `for` o `while` imperativos. Si necesitas inspeccionar qué ocurre en cada fase de tu tubería, coloca un punto de ruptura (*breakpoint*) en la línea `.stream()` y pulsa el icono **Trace Current Stream Chain** en el panel del depurador.
:::

---

## Ejercicio 1: Navegación segura sin `null` con `Optional<T>`

En una tienda de informática se modela un ordenador (`Ordenador`) que puede tener o no una tarjeta de sonido (`TarjetaSonido`), la cual a su vez puede incorporar o no un puerto USB (`PuertoUSB`) con una versión determinada (`String version`, por ejemplo `"3.2"` o `"2.0"`).

1. Diseña las clases o `record` `PuertoUSB(String version)`, `TarjetaSonido(Optional<PuertoUSB> usb)` y `Ordenador(String modelo, Optional<TarjetaSonido> tarjetaSonido)`.
2. Implementa un método `public static String obtenerVersionUSB(Optional<Ordenador> ordenador)` que encadene `flatMap`, `map` y `orElse("DESCONOCIDA")` para devolver la versión del puerto USB sin utilizar ningún bloque `if (x != null)`.
3. Implementa un método `public static boolean esUSB32(Optional<Ordenador> ordenador)` que utilice `filter` e `isPresent()` sobre la cadena de `Optional` para comprobar si el puerto USB existe y su versión es exactamente `"3.2"`.
4. Pon a prueba ambos métodos con tres casos: un ordenador completo con USB `"3.2"`, un ordenador cuya tarjeta de sonido no tiene USB (`Optional.empty()`), y un `Optional<Ordenador>` vacío.

---

## Ejercicio 2: Primeros pasos con `Stream<String>`

A partir de una lista de cadenas de texto con valores repetidos:

```java
List<String> palabras = List.of("uno", "dos", "tres", "cuatro", "dos");
```

Escribe en una clase `PrimerosStreamsApp` cinco tuberías independientes de Streams que realicen las siguientes operaciones y muestren su resultado por pantalla:

1. **Recorrido básico:** Muestra todos los elementos de la lista utilizando `stream()` y `forEach(System.out::println)`.
2. **Ordenación natural:** Muestra los elementos de la lista ordenados alfabéticamente mediante `sorted()`.
3. **Transformación (`map`):** Convierte todas las palabras a mayúsculas (`String::toUpperCase`) y muéstralas ordenadas alfabéticamente.
4. **Filtrado y transformación:** Filtra únicamente aquellas palabras que empiecen por la letra `"t"` (`s -> s.startsWith("t")`), conviértelas a mayúsculas y muéstralas por consola.
5. **Eliminación de duplicados (`distinct`):** Elimina las palabras repetidas de la lista original y recopila el resultado en una nueva `List<String>` sin duplicados.

---

## Ejercicio 3: Operaciones funcionales sobre listas de números y cadenas

Resuelve los siguientes cinco micro-retos utilizando expresiones lambda, `Stream`, `filter`, `map`, `reduce` y `Collectors`:

1. **Filtrado de números pares:** Dada la lista `List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)`, obtén e imprime una nueva lista que contenga únicamente los números pares.
2. **Incremento unitario:** Dada la lista `List.of(10, 20, 30, 40)`, genera una nueva lista donde cada número haya sido incrementado en `1` unidad.
3. **Conjunto de cuadrados sin duplicados:** Dada la lista con repetidos `List.of(1, -2, 2, 3, -3, 4)`, calcula el cuadrado de cada número y almacena el resultado en un conjunto (`Set<Integer>`) utilizando `Collectors.toSet()`.
4. **Palabras que empiezan por `'A'`:** Dada la lista `List.of("Algoritmo", "bucle", "Array", "clase", "API", "stream")`, obtén una lista con las palabras que comienzan por `"A"` (mayúscula) convertidas íntegramente a minúsculas.
5. **Producto de mayores que 5 con `reduce`:** Dada la lista `List.of(2, 6, 3, 8, 4, 10)`, filtra los números estrictamente mayores que `5` y calcula su **producto total** (`6 × 8 × 10 = 480`) utilizando `.reduce(1, (a, b) -> a * b)`.

---

## Ejercicio 4: Sucesión de Fibonacci y flujos infinitos (`iterate` y `generate`)

La sucesión de Fibonacci comienza con `0, 1` y cada término siguiente es la suma de los dos anteriores (`0, 1, 1, 2, 3, 5, 8, 13, 21, 34...`).

1. Utilizando `Stream.iterate`, genera las **20 primeras tuplas** de Fibonacci representadas como arrays de dos enteros `new int[]{a, b}`, donde la semilla inicial es `new int[]{0, 1}` y la función de transición transforma `t` en `new int[]{t[1], t[0] + t[1]}`. Imprime las tuplas con el formato `(0, 1), (1, 1), (1, 2)...`.
2. Modifica la tubería anterior añadiendo una operación `.map(t -> t[0])` para extraer y recopilar en una `List<Integer>` únicamente los **20 primeros números** de la serie de Fibonacci.
3. Utilizando `Stream.iterate` con predicado de parada (Java 9+), genera todos los números de la serie de Fibonacci cuyo valor sea **menor que 1.000**.
4. Utilizando `Stream.generate`, genera e imprime **5 números enteros aleatorios** comprendidos entre `1` y `100` (ambos inclusive) ordenados de menor a mayor.

---

## Ejercicio 5: Consultas sobre el menú de un restaurante (`Plato`)

Partiendo de la clase `Plato` vista en la unidad (con atributos `nombre`, `vegetariano`, `calorias` y `tipo` de tipo `enum Tipo { CARNE, PESCADO, OTROS }`) y su lista de 9 platos de ejemplo `Plato.MENU`:

1. Obtén una lista con los **2 primeros platos de carne** (`Tipo.CARNE`) que aparecen en el menú utilizando `filter` y `limit(2)`.
2. Obtén una lista con los nombres de todos los **platos vegetarianos** ordenados de **menor a mayor número de calorías**.
3. Calcula la **suma total de calorías** de todos los platos del menú de dos formas distintas:
   - Usando `mapToInt(Plato::getCalorias).sum()`.
   - Usando `map(Plato::getCalorias).reduce(0, Integer::sum)`.
4. ¿Cuántos platos tiene el menú? Calcúlalo utilizando el patrón **Map-Reduce** encadenando `.map(p -> 1)` y `.reduce(0, Integer::sum)` (sin usar `.count()` ni `.size()`).

---

## Ejercicio 6: Filtrado de `Persona` por edad y cálculo de descuentos en `Producto`

1. **Rango de edad:** Crea un `record Persona(String nombre, int edad)` y una lista con al menos 6 personas de distintas edades. Escribe una tubería que filtre aquellas personas cuya edad esté comprendida entre **20 y 30 años** (ambos inclusive), las ordene por edad de menor a mayor y devuelva una lista con sus nombres.
2. **Cálculo de carrito con descuento:** Crea un `record Producto(String nombre, double precio, boolean enOferta)` y una lista con varios productos. Escribe una tubería que:
   - Filtre únicamente los productos que están en oferta (`Producto::enOferta`).
   - Aplique a su precio un **descuento del 15 %** (`precio * 0.85`).
   - Calcule y devuelva el **importe total** de esos productos rebajados utilizando `mapToDouble` y `sum()`.

---

## Ejercicio 7: Análisis de una flota de vehículos (`Vehiculo`)

Dada la siguiente estructura para representar la flota de un concesionario:

```java
public enum Marca { BMW, MERCEDES, AVENSIS, CITROEN, FORD, AUDI }

public record Vehiculo(String matricula, Marca marca, int kilometros) {}
```

Con la siguiente lista de prueba:

```java
List<Vehiculo> flota = List.of(
    new Vehiculo("1111AAA", Marca.BMW, 100_000),
    new Vehiculo("2222BBB", Marca.AUDI, 200_000),
    new Vehiculo("3333CCC", Marca.OPEL, 300_000), // o Marca.AVENSIS
    new Vehiculo("4444DDD", Marca.BMW, 100_000),
    new Vehiculo("5555EEE", Marca.AUDI, 200_000),
    new Vehiculo("6666FFF", Marca.OPEL, 300_000),
    new Vehiculo("7777GGG", Marca.CITROEN, 0)
);
```

Resuelve mediante Streams las siguientes consultas:

1. **Proyección de matrículas:** Obtén una lista con las matrículas de todos los vehículos de la flota.
2. **Simulación de kilometraje (`map`):** Obtén una lista de enteros con el resultado de **duplicar los kilómetros** (`v.kilometros() * 2`) de cada vehículo.
3. **Filtrado por marca:** Obtén una nueva `List<Vehiculo>` que contenga exclusivamente los vehículos de la marca `Marca.AUDI`.
4. **Ordenación por kilometraje:** Muestra todos los vehículos ordenados de menor a mayor número de kilómetros utilizando `Comparator.comparingInt(Vehiculo::kilometros)`.
5. **Marcas únicas (`distinct`):** Obtén una lista con todas las marcas (`Marca`) presentes en la flota **sin repeticiones**.
6. **Verificaciones con predicados (`anyMatch` / `allMatch`):** Comprueba si existe algún vehículo nuevo (`kilometros == 0`) y si todos los vehículos tienen menos de `500_000` kilómetros.

---

## Ejercicio 8: Plataforma de formación online (`Curso` — Parte 1)

Una plataforma de formación online gestiona su catálogo mediante la clase `Curso`:

```java
public record Curso(String titulo, float duracion, int videos, int alumnos) {}
```

Partiendo de la siguiente lista de cursos:

```java
List<Curso> cursos = List.of(
    new Curso("Cursos profesional de Java", 6.5f, 50, 200),
    new Curso("Cursos profesional de Python", 8.5f, 60, 800),
    new Curso("Cursos profesional de DB", 4.5f, 70, 700),
    new Curso("Cursos profesional de Android", 7.5f, 10, 400),
    new Curso("Cursos profesional de Escritura", 1.5f, 10, 300)
);
```

Resuelve las siguientes consultas empleando Streams:

1. Obtén la **cantidad de cursos** con una duración **mayor a 5 horas**.
2. Obtén la **cantidad de cursos** con una duración **menor a 2 horas**.
3. Lista el **título** de todos aquellos cursos con una cantidad de vídeos **estrictamente mayor a 50**.
4. Muestra en consola el **título de los 3 cursos con mayor duración** (ordena de mayor a menor duración y limita a 3 resultados).

---

## Ejercicio 9: Plataforma de formación online (`Curso` — Parte 2)

Continuando con el catálogo de objetos `Curso` del ejercicio anterior, resuelve las siguientes consultas:

1. Muestra en consola la **duración total** de todos los cursos sumada.
2. Calcula la duración media de todos los cursos y muestra en consola todos aquellos **libros/cursos que superen el promedio** de duración global.
3. Muestra en consola la **duración** de todos aquellos cursos que tengan una cantidad de alumnos inscritos **menor a 500**.
4. Obtén el **curso con mayor duración** de toda la plataforma utilizando `.max(...)`.
5. Crea una **`List<String>`** con todos los títulos de los cursos disponibles.

---

## Ejercicio 10: Sorteo de premios por SMS en un Concurso de TV (`Espectador`)

Un concurso de televisión registra todas las llamadas y mensajes SMS recibidos en una lista de objetos `Espectador(String nombre, String telefono)`. Durante el programa se desea sortear un premio entre **3 espectadores distintos** cuyos números de teléfono sean válidos.

1. Crea el `record Espectador(String nombre, String telefono)` y una lista con al menos 8 registros donde:
   - Algunos espectadores hayan enviado varios mensajes (su número de teléfono aparece repetido varias veces).
   - Algunos números de teléfono sean erróneos (longitud distinta de 9 o 10 dígitos, por ejemplo `"12345"` o `""`).
2. Escribe una tubería de Stream que:
   - Extraiga los números de teléfono (`map(Espectador::telefono)`).
   - Filtre aquellos que sean no nulos y tengan una longitud exacta de **9 dígitos** (o 10 según el formato elegido).
   - Elimine los números duplicados con `distinct()` para que cada participante tenga una única participación en el sorteo.
   - Seleccione a los **3 ganadores** tras barajar la lista resultante o aplicar `limit(3)`.

---

## Ejercicio 11: Dataset literario `Escritor` — Bloque Básico

Para los ejercicios 11 y 12 (y el boletín intermedio) trabajaremos con el siguiente modelo de datos sobre grandes escritores de la literatura universal:

```java
public record Escritor(
    String id,
    String nombre,
    String nacionalidad,
    int nace,
    Integer muere,          // null si sigue vivo
    String movimiento,
    List<String> titulos
) {}
```

Con el siguiente método factoría de datos de prueba:

```java
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
```

Resuelve las siguientes consultas sobre la lista de escritores:

1. **Filtrado y proyección:** Obtén una lista con los nombres de todos los escritores de nacionalidad `"Española"` e imprímelos por consola.
2. **Transformación y ordenación:** Obtén una lista con los nombres de todos los escritores convertidos a **mayúsculas** y ordenados alfabéticamente de la A a la Z.
3. **Elementos únicos y conteo:** Muestra todas las **nacionalidades distintas** que existen en la lista y calcula cuántas nacionalidades diferentes hay en total utilizando `distinct()` y `count()`.
4. **Búsqueda y extremos:** Obtén todos los escritores nacidos **antes del año 1900** y encuentra cuál es el **escritor más antiguo** (el que nació antes) utilizando `.min(Comparator.comparingInt(Escritor::nace))`.

---

## Ejercicio 12: Dataset literario `Escritor` — Edades y Paginación (`skip` y `limit`)

Utilizando la misma lista de objetos `Escritor` del ejercicio anterior:

1. Añade al `record Escritor` un método `public int edadAprox()` que calcule la edad aproximada del escritor:
   - Si ya ha fallecido (`muere != null`), devuelve `muere - nace`.
   - Si sigue vivo (`muere == null`), devuelve `Year.now().getValue() - nace`.
2. **Suma con `IntStream`:** Muestra el nombre y la edad aproximada de cada escritor, y calcula la **suma total de los años vividos** por todos los escritores del listado utilizando `mapToInt(Escritor::edadAprox).sum()`.
3. **Paginación con `limit` y `skip`:**
   - Ordena a los escritores por su año de nacimiento (de más antiguo a más reciente) y muestra los **5 primeros** (`limit(5)`).
   - Sobre esa misma ordenación por año de nacimiento, **salta los 3 primeros** escritores y muestra los **4 siguientes** (`skip(3).limit(4)`).
