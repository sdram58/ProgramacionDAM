---
title: "🟢 Boletín 06 — Nivel Inicial (Ejercicios Propuestos)"
description: "12 ejercicios fundamentales de programación genérica, ArrayList, pilas (Stack), colas (Queue y PriorityQueue), iteradores, Comparable, factorías inmutables, conjuntos (HashSet/TreeSet) y mapas (HashMap) en Java 25 LTS."
draft: true
prev: false
next: false
---

Este boletín reúne **12 ejercicios progresivos de nivel inicial** diseñados para afianzar el manejo de tipos genéricos `<T>`, el recorrido seguro con iteradores y las estructuras fundamentales del *Java Collections Framework* (`List`, `Set`, `Queue`, `Stack` y `Map`), integrando las actividades oficiales **EA2, EA4, EA5, EA6, EA7, EA8, EA9 y EA10**.

:::tip[Recomendación de trabajo en IntelliJ IDEA]
Crea un paquete `ud06.boletin.inicial` en tu proyecto de IntelliJ IDEA y resuelve cada ejercicio en su propia clase ejecutable. Cuando termines, contrasta tu código con el [**Boletín 06 — Nivel Inicial (Resuelto)**](/ProgramacionDAM/ud06/boletin-06-inicial-resuelto/).
:::

---

## Ejercicio 1: Métodos Genéricos sobre Arrays (`intercambia` y `contarApariciones`)

Crea una clase de utilidades `UtilArraysGenericos` que implemente dos métodos estáticos genéricos capaces de trabajar con arrays de cualquier tipo de objeto (`String[]`, `Integer[]`, `Double[]`, etc.):
1. `public static <T> void intercambia(T[] array, int pos1, int pos2)`: intercambia los elementos situados en los índices `pos1` y `pos2` (validando previamente que ambos índices estén dentro del rango válido del array).
2. `public static <T> int contarApariciones(T[] array, T buscado)`: devuelve cuántas veces aparece el elemento `buscado` dentro del array empleando igualdad lógica (`Objects.equals`).
3. En el método `main`, comprueba su funcionamiento con un array de `String` (`{"Java", "Python", "C++", "Java"}`) y un array de `Integer` (`{10, 20, 30, 40}`).

---

## Ejercicio 2: Clases Genéricas `Caja<T>`, `Pareja<T, V>` y Calculadora `OperaMate<T extends Number>`

Diseña tres pequeñas clases genéricas para practicar la parametrización simple, múltiple y con límite superior:
1. **`Caja<T>`**: almacena un único objeto de tipo `T`, con métodos `guardar(T elem)`, `extraer()` (que devuelve el objeto y vacía la caja dejándola a `null`), `estaVacia()` y `mostrarTipo()`.
2. **`Pareja<T, V>`**: almacena una tupla de dos objetos (`primero` de tipo `T` y `segundo` de tipo `V`), con sus *getters*, `toString()` y un método `esIgualA(Pareja<T, V> otra)` que compare ambos componentes.
3. **`OperaMate<T extends Number>`**: recibe un número de tipo `T` y ofrece los métodos `double reciproco()` (`1.0 / num`), `double parteFraccionaria()` y `boolean tieneMismoValorAbsoluto(OperaMate<?> otro)` (observa el uso del comodín `?` para poder comparar un `OperaMate<Integer>` con un `OperaMate<Double>`).

---

## Ejercicio 3: Búsqueda Secuencial en `ArrayList` (`indiceDe`)

Escribe un método estático llamado **`indiceDe`** que reciba como parámetros un `ArrayList<String> lista` y un `String valorBuscado`, y devuelva el índice (`int`) de la **primera aparición** de `valorBuscado` en la lista (o `-1` si no se encuentra), recorriendo la lista manualmente con un bucle e invocando `equals()`.
* Por ejemplo, si la lista almacena `["a", "b", "c", "g", "d", "f", "e", "g"]`, la llamada `indiceDe(lista, "g")` debe devolver `3`, y `indiceDe(lista, "z")` debe devolver `-1`.
* Comprueba en el `main` que tu método devuelve exactamente el mismo resultado que el método estándar `lista.indexOf(valorBuscado)`.

---

## Ejercicio 4: Operaciones Fundamentales con Pilas `Stack<String>` (Actividad EA2)

Escribe un programa `DemoStackEA2` que utilice la clase `java.util.Stack<String>` para gestionar una pila de cadenas de texto:
1. Apila (`push`) por este orden los elementos: `"Primero"`, `"Segundo"`, `"Tercero"` y `"Cuarto"`.
2. Muestra el contenido completo de la pila y consulta cuál es el elemento situado en la cima sin extraerlo (`peek`).
3. Utiliza el método `search("Segundo")` y `search("Inexistente")` para mostrar por pantalla a qué distancia (en base 1 desde la cima) se encuentra cada elemento.
4. Extrae y muestra todos los elementos de la pila uno por uno mediante un bucle `while (!pila.empty())` usando `pop()`, observando el orden **LIFO** (*Last In, First Out*).

---

## Ejercicio 5: Inversión de Palabras y Comprobación de Palíndromos con una Pila de Caracteres

Aprovechando el comportamiento LIFO de las pilas (`Deque<Character>` con `ArrayDeque` o `Stack<Character>`):
1. Escribe un método `public static String invertirPalabra(String texto)` que introduzca todos los caracteres de `texto` en una pila uno a uno (`push`) y luego los extraiga todos (`pop`) concatenándolos en un `StringBuilder`.
2. Escribe un método `public static boolean esPalindromoConPila(String palabra)` que determine si una palabra se lee igual de izquierda a derecha que de derecha a izquierda (por ejemplo, `"reconocer"` o `"radar"`).

---

## Ejercicio 6: Gestión de una Cola de Impresión FIFO con `Queue<String>` (Actividad EA4)

Escribe un programa `ColaImpresionEA4` que simule el funcionamiento de una cola de trabajos de impresión utilizando la interfaz `Queue<String>` implementada mediante `LinkedList<String>` (o `ArrayDeque<String>`):
1. Encola (`offer` o `add`) cinco documentos: `"Factura_001.pdf"`, `"Contrato_Laboral.pdf"`, `"Apuntes_UD06.pdf"`, `"Nomina_Marzo.pdf"` y `"Horario_DAM.pdf"`.
2. Muestra cuántos documentos hay en espera (`size()`) y cuál es el próximo documento que se va a imprimir sin sacarlo de la cola (`peek()`).
3. Procesa (imprime y desencola con `poll()`) todos los documentos en estricto orden de llegada (**FIFO**), mostrando el estado de la cola tras cada impresión hasta que quede vacía.

---

## Ejercicio 7: Triaje de Urgencias con `PriorityQueue<E>` (Actividad EA5)

A diferencia de una cola FIFO normal, una `PriorityQueue` extrae siempre primero el elemento prioritario según su orden natural o su comparador:
1. Crea primero una `PriorityQueue<Integer>` con los números `40, 10, 50, 20, 30`. Muestra la diferencia entre recorrerla con un `for-each` (orden interno del montículo) frente a extraer sus elementos con `while (!pq.isEmpty()) { pq.poll(); }` (orden ascendente estricto).
2. Crea una clase o `record Paciente(String nombre, int nivelUrgencia)` que implemente `Comparable<Paciente>` (donde urgencia `1` es crítica y va antes que urgencia `5` leve). Inserta 5 pacientes desordenados en una `PriorityQueue<Paciente>` y atiéndelos en orden de gravedad.

---

## Ejercicio 8: Clase `Persona` y `ListaPersonas` con `ArrayList`, `Vector` e `Iterator` (Actividades EA6, EA7 y EA8)

Vamos a construir paso a paso un pequeño gestor de personas probando distintas implementaciones e iteradores:
1. Crea la clase **`Persona`** con los atributos `dni` (`String`), `nombre` (`String`), `apellidos` (`String`) y `edad` (`int`), constructores, *getters/setters*, `toString()` y sobrescribe **`equals(Object)`** y **`hashCode()`** basándote en el `dni` (dos personas son la misma si tienen el mismo DNI).
2. **(EA6)** Crea la clase **`ListaPersonas`** respaldada internamente por un `ArrayList<Persona>` con los métodos: `anadir(Persona p)`, `eliminarPorDni(String dni)`, `buscarPorDni(String dni)`, `buscarPorApellido(String apellido)` y `mostrarTodas()`.
3. **(EA7)** Crea una variante o método que realice las mismas operaciones empleando la clase histórica `Vector<Persona>` (`addElement`, `removeElement`, `elementAt`) para comprobar sus similitudes y diferencias.
4. **(EA8)** Añade a `ListaPersonas` dos métodos que utilicen explícitamente un **`Iterator<Persona>`**:
   * `mostrarConIterador()`: recorre e imprime la lista usando `hasNext()` y `next()`.
   * `eliminarMenoresDeEdad()`: elimina de forma segura (sin provocar `ConcurrentModificationException`) a todas las personas con `edad < 18` utilizando `it.remove()`.

---

## Ejercicio 9: Ordenación Natural y Multicriterio de `Persona` (Actividad EA9)

Amplía la clase `Persona` del ejercicio anterior para que implemente la interfaz **`Comparable<Persona>`**:
1. Define como **orden natural (`compareTo`)** la ordenación alfabética por `apellidos` y, en caso de empate de apellidos, por `nombre` (ignorando mayúsculas/minúsculas).
2. En el programa principal, guarda 6 personas desordenadas en una lista y ordénalas con `Collections.sort(lista)`.
3. A continuación, define dos comparadores externos con **`Comparator<Persona>`**: uno para ordenar por **`edad` de menor a mayor** y otro por **`dni` descendente**, mostrando el resultado de cada ordenación.

---

## Ejercicio 10: Colecciones Inmutables con Métodos Factoría de Java 9+ (Actividad EA10)

Experimenta con los métodos factoría inmutables introducidos en Java 9 (`List.of`, `Set.of`, `Map.of` y `Map.ofEntries`):
1. Crea una lista inmutable de módulos de 1º DAM con `List.of(...)`, un conjunto inmutable de provincias con `Set.of(...)` y un mapa inmutable de prefijos telefónicos con `Map.of(...)`.
2. Comprueba (capturando las excepciones con bloques `try-catch` para que el programa continúe y explique lo ocurrido) qué sucede cuando intentas:
   * Añadir o eliminar un elemento en una colección creada con `List.of` (`UnsupportedOperationException`).
   * Modificar una posición con `set(0, "Otro")` (`UnsupportedOperationException`).
   * Pasar un elemento `null` a `List.of` o `Set.of` (`NullPointerException`).
   * Pasar dos elementos repetidos a `Set.of("A", "B", "A")` o dos claves repetidas a `Map.of` (`IllegalArgumentException`).
3. Demuestra cómo crear una copia mutable a partir de la lista inmutable mediante `new ArrayList<>(listaInmutable)`.

---

## Ejercicio 11: Operaciones de Teoría de Conjuntos con `HashSet` y `TreeSet`

Un centro educativo quiere analizar los alumnos matriculados en dos actividades extraescolares: **Robótica** y **Ajedrez**.
1. Crea un `Set<String> robotica` con `["Ana", "Luis", "Carlos", "Elena", "Marta"]` y un `Set<String> ajedrez` con `["Carlos", "Sofía", "Ana", "Pedro", "Jorge"]`.
2. Utilizando copias en **`TreeSet<String>`** (para que todos los listados salgan ordenados alfabéticamente de forma automática), calcula y muestra:
   * **Unión (`addAll`):** todos los alumnos que participan en al menos una actividad (sin duplicados).
   * **Intersección (`retainAll`):** alumnos que están apuntados a **ambas** actividades a la vez.
   * **Diferencia (`removeAll`):** alumnos que hacen **solo Robótica** (y no Ajedrez).
   * **Diferencia simétrica:** alumnos que están en una sola actividad pero no en las dos.

---

## Ejercicio 12: Listín Telefónico con `HashMap<String, String>`

Diseña una clase **`ListinTelefonico`** que almacene contactos asociando el **nombre de la persona (clave `String`)** con su **número de teléfono (valor `String`)** dentro de un `HashMap<String, String>`:
1. Implementa los métodos:
   * `void anadirContacto(String nombre, String telefono)`: inserta el contacto o actualiza su teléfono si ya existía (avisando por consola si se ha sobrescrito el número anterior).
   * `String buscarTelefono(String nombre)`: devuelve el teléfono asociado o el mensaje `"Contacto no registrado"` usando `getOrDefault`.
   * `boolean eliminarContacto(String nombre)`: borra el contacto si existe.
   * `void mostrarListin()`: recorre todas las entradas con `entrySet()` mostrando `Nombre -> Teléfono`.
2. Prueba todas las operaciones desde un método `main`.
