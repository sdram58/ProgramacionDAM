---
title: "🟡 Boletín 06 — Nivel Intermedio (Ejercicios Propuestos)"
description: "12 ejercicios de nivel intermedio sobre pilas y colas avanzadas, validación de expresiones (EA3), análisis y conteo de tablas (EA11), codificación ordinal (EA13), frecuencias literarias (EA14), agrupaciones Map<K, List<V>> (EA15, EA16) y memoización en Java 25 LTS."
draft: true
prev: false
next: false
---

Este boletín reúne **12 problemas de dificultad intermedia** en los que deberás combinar pilas, colas auxiliares, mapas compuestos (`Map<K, List<V>>`), comparadores multicriterio y programación funcional sobre mapas (`merge`, `computeIfAbsent`), abarcando las actividades oficiales **EA3, EA11, EA13, EA14, EA15 y EA16** así como los problemas clásicos de estructuras de datos avanzadas.

:::tip[Comprueba tus soluciones]
Una vez hayas intentado resolver los ejercicios en IntelliJ IDEA, revisa las soluciones comentadas paso a paso en el [**Boletín 06 — Nivel Intermedio (Resuelto)**](/ProgramacionDAM/ud06/boletin-06-intermedio-resuelto/).
:::

---

## Ejercicio 1: Validador de Expresiones con Paréntesis, Corchetes y Llaves Balanceados (Actividad EA3)

Uno de los algoritmos clásicos de los compiladores e IDEs como IntelliJ IDEA consiste en verificar mediante una **pila (`Deque<Character>` o `Stack<Character>`)** si todos los símbolos de apertura `(`, `[` y `{` de una expresión matemática o bloque de código están correctamente cerrados y anidados con `)`, `]` y `}`.

Escribe un método `public static boolean estaBalanceada(String expresion)` que recorra la cadena carácter a carácter:
* Si encuentra un delimitador de apertura `(`, `[` o `{`, lo apila (`push`).
* Si encuentra un delimitador de cierre `)`, `]` o `}`, comprueba que la pila no esté vacía y que la cima desapilada (`pop`) sea exactamente su pareja de apertura correspondiente.
* Al terminar de recorrer la cadena, la expresión solo es válida si la pila ha quedado **completamente vacía**.
* Prueba tu método con:
  * `"{[a * (b + c)] - 5}"` → `true`
  * `"[(a + b) * {c - d]}"` → `false` (cierre cruzado)
  * `"((a + b)"` → `false` (falta cerrar)
  * `"(a + b))"` → `false` (cierre sin apertura previa)

---

## Ejercicio 2: Inserción Ordenada en una Pila (`insertarOrdenado` con Pila Auxiliar)

En una pila pura (`Stack<Integer>` o `Deque<Integer>`), solo tenemos permiso para acceder a la cima (`push`, `pop`, `peek` e `isEmpty`).
Escribe un método estático `public static void insertarOrdenado(Stack<Integer> pila, int valor)` que reciba una pila de enteros ordenada de menor (en el fondo) a mayor (en la cima) —por ejemplo `[1, 3, 5, 10]` donde `10` es la cima— e inserte `valor` en su posición exacta apoyándose **exclusivamente en una segunda pila auxiliar** (desapilando temporalmente los elementos mayores que `valor`, metiendo `valor` y devolviendo los elementos desde la pila auxiliar).
* Comprueba que al insertar `7` en `[1, 3, 5, 10]`, la pila queda `[1, 3, 5, 7, 10]`.

---

## Ejercicio 3: Expulsión Selectiva en una Cola de Cine (`expulsarCliente`)

En la taquilla de un cine hay una cola FIFO de espectadores (`Queue<String>`) esperando para comprar su entrada. Algunos espectadores se han colado varias veces con el mismo nombre o deben ser expulsados sin alterar el orden relativo de los demás clientes legítimos.
Escribe un método `public static void expulsarCliente(Queue<String> cola, String nombreExpulsado)` que elimine **todas las apariciones** de `nombreExpulsado` en la cola rotando sus elementos (`poll` y `offer` sobre la misma cola o con una cola auxiliar, sin usar índices de listas).
* Por ejemplo, si la cola es `["Juan", "Pepe", "María", "Pepe", "Ana", "Pepe"]` y llamamos a `expulsarCliente(cola, "Pepe")`, la cola resultante debe ser `["Juan", "María", "Ana"]`.

---

## Ejercicio 4: Historial de Navegador Web con `HashMap<String, String>`

Crea una clase **`NavegadorWeb`** que almacene el historial de páginas visitadas asociando el **nombre corto del sitio (`String`)** con su **URL completa (`String`)** en un `HashMap<String, String>`:
1. Implementa `visitarPagina(String nombre, String url)`, `consultarUrl(String nombre)` (que devuelva la URL o un mensaje indicando que nunca se ha visitado ese sitio), `borrarDelHistorial(String nombre)` y `vaciarHistorial()`.
2. Añade un método `buscarPorDominio(String extension)` (por ejemplo `".es"` o `".org"`) que recorra las entradas del mapa y devuelva una `List<String>` con todos los sitios cuya URL termine en dicho dominio.

---

## Ejercicio 5: Agenda Multiteléfono de Clientes y Búsqueda Inversa

En el mundo real, una persona suele tener **varios números de teléfono** (móvil personal, trabajo, fijo).
1. Diseña la clase **`Cliente`** con atributos `nombre` (`String`) y `telefonos` (`ArrayList<String>`), y el método `addTelefono(String numero)` que añada el teléfono solo si no estaba ya registrado en ese cliente.
2. En una clase `GestorAgenda`, almacena los clientes en un `HashMap<String, Cliente>` (donde la clave es el nombre del cliente).
3. Además de buscar los teléfonos a partir del nombre del cliente (`O(1)`), implementa el método de **búsqueda inversa** `public List<String> buscarClientesPorPrefijoTelefono(String prefijo)` que devuelva los nombres de todos los clientes que tengan algún teléfono que empiece por `prefijo` (por ejemplo, `"96"`).

---

## Ejercicio 6: Estadísticas sobre Listas: `contar` y `contarSI` (Actividad EA11)

Crea la clase **`TablaEA11`** que encapsule una lista de cadenas (`List<String> datos`) y proporcione las funciones estadísticas equivalentes a las de una hoja de cálculo:
1. `int contar(String valor)`: cuenta cuántas veces aparece exactamente `valor` en la lista.
2. `int contarSI(Predicate<String> condicion)`: cuenta cuántos elementos cumplen una condición funcional arbitraria (por ejemplo, empezar por `"A"`, tener más de 5 letras o contener dígitos).
3. `Map<String, Integer> tablaFrecuencias()`: devuelve un mapa con cada elemento único de la lista y el número de veces que se repite, utilizando `mapa.merge(elem, 1, Integer::sum)`.

---

## Ejercicio 7: Codificación Ordinal Automática de Variables Categóricas (Actividad EA13)

En Ciencia de Datos e Inteligencia Artificial es muy frecuente transformar valores de texto (como el nivel de estudios de una encuesta: `"Primaria"`, `"ESO"`, `"Bachillerato"`, `"FP Superior"`, `"Universidad"`) en códigos enteros únicos (`0, 1, 2, ...`).
Escribe una clase **`CodificadorOrdinalEA13`** que reciba una `List<String>` con las respuestas en bruto de decenas de encuestados (con muchos valores repetidos):
1. Recorre la lista y utiliza un `LinkedHashMap<String, Integer>` para asignar automáticamente un código entero incremental (`0, 1, 2...`) a cada categoría distinta en el orden en que aparece por primera vez (`putIfAbsent`).
2. Genera y devuelve una nueva `List<Integer>` con toda la encuesta codificada numéricamente, y muestra el diccionario de equivalencias `Categoría <-> Código`.

---

## Ejercicio 8: Analizador de Frecuencias Literarias del Poema de Bécquer (Actividad EA14)

Dado el célebre poema de Gustavo Adolfo Bécquer (Rima LIII — *«Volverán las oscuras golondrinas...»*), escribe un programa `AnalizadorBecquerEA14` que:
1. Limpie el texto convirtiéndolo a minúsculas y eliminando signos de puntuación (`. , ; : ¡ ! ¿ ?`), y separe todas sus palabras.
2. Cuente cuántas veces aparece cada palabra en el poema.
3. Imprima **dos informes completos**:
   * **Informe A (Orden Alfabético):** todas las palabras junto a su frecuencia ordenadas de la `a` a la `z` (apoyándote en un `TreeMap<String, Integer>`).
   * **Informe B (Ranking de Apariciones):** las palabras ordenadas de **mayor a menor número de apariciones** y, en caso de empate en frecuencia, ordenadas alfabéticamente (volcando `entrySet()` a una lista y ordenándola con `Comparator`).

---

## Ejercicio 9: Agrupación de Obras por Escritor con `Map<String, List<String>>` (Actividad EA15)

A partir de una colección de líneas de texto (simulando el fichero `Literatura.txt`) con el formato `"Autor;Título de la Obra"` (por ejemplo, `"Cervantes;El Quijote"`, `"Lope de Vega;Fuenteovejuna"`, `"Cervantes;La Galatea"`, `"Calderón;La vida es sueño"`, `"Cervantes;Rinconete y Cortadillo"`):
1. Construye un **`Map<String, List<String>>`** donde la clave sea el nombre del escritor y el valor sea la lista con todas sus obras registradas, utilizando **`computeIfAbsent(autor, k -> new ArrayList<>()).add(obra)`**.
2. Muestra el catálogo de autores ordenados alfabéticamente y, debajo de cada autor, sus obras también ordenadas alfabéticamente.
3. Indica qué autor tiene más obras registradas en el catálogo.

---

## Ejercicio 10: Agrupación de Alumnos por Calificación e Histograma (Actividad EA16)

Dada una lista de registros de notas de examen (simulando `Examen.txt`) con el formato `"NombreAlumno;NotaEntera"` (notas entre `0` y `10`):
1. Agrupa a los alumnos en un **`TreeMap<Integer, List<String>>`** donde la clave sea la nota (`0..10`) y el valor sea la lista de nombres de alumnos que han obtenido exactamente esa calificación.
2. Imprime el informe ordenado desde la nota más alta (`10`) hasta la más baja (`0`) utilizando **`treeMap.descendingMap()`**, mostrando cuántos alumnos hay en cada nota y sus nombres.

---

## Ejercicio 11: Gestión de Matrículas de 1º DAM/DAW con `HashMap<String, Alumno>`

Una academia necesita gestionar el alumnado de 1º de DAM/DAW mediante un diccionario `HashMap<String, Alumno>` cuya clave única es el **`nia`** (Número de Identificación del Alumno):
1. Crea la clase **`Alumno`** con `nia` (`String`), `nombre` (`String`), `ciclo` (`"DAM"` o `"DAW"`) y `notaMedia` (`double`).
2. Crea la clase **`SecretariaVirtual`** con los métodos:
   * `matricular(Alumno a)`: registra al alumno solo si su NIA no estaba ya dado de alta (`putIfAbsent`).
   * `darDeBaja(String nia)`: elimina al alumno por su NIA.
   * `modificarNotaMedia(String nia, double nuevaNota)`: actualiza la nota si el alumno existe.
   * `listarPorNotaDescendente()`: devuelve una lista con todos los alumnos ordenados de mayor a menor nota media.
   * `calcularMediaPorCiclo()`: devuelve un `Map<String, Double>` con la nota media global de `"DAM"` y la de `"DAW"`.

---

## Ejercicio 12: Caché Inteligente con `computeIfAbsent` y Caminos en Cuadrícula (Memoización)

1. Crea una clase `CalculadoraCombinatoria` que calcule el **Coeficiente Binomial `C(n, k)`** (cuántas formas hay de elegir `k` elementos de un grupo de `n`, definido recursivamente como `C(n, k) = C(n-1, k-1) + C(n-1, k)`, con casos base `C(n, 0) = 1` y `C(n, n) = 1`).
2. Si lo calculas con recursividad simple, `C(60, 30)` tardaría horas por tener complejidad exponencial `O(2ⁿ)`. Utiliza un **`Map<String, Long> memo`** (donde la clave sea `"n,k"`) para memorizar cada resultado intermedio y calcula instantáneamente `C(60, 30)`.
