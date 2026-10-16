---
title: "✅ Boletín 06 — Nivel Inicial (Solucionario)"
description: "Soluciones completas y comentadas en Java 25 LTS de los 12 ejercicios del Boletín Inicial de la Unidad 6: Genéricos y Colecciones."
draft: true
prev: false
next: false
---

A continuación se presentan las **soluciones completas en Java 25 LTS** para los 12 ejercicios propuestos en el [**Boletín 06 — Nivel Inicial**](/ProgramacionDAM/ud06/boletin-06-inicial/).

---

## Solución Ejercicio 1: Métodos Genéricos sobre Arrays (`intercambia` y `contarApariciones`)

```java
import java.util.Arrays;
import java.util.Objects;

public class UtilArraysGenericos {

    // Método genérico que intercambia dos posiciones de cualquier array de objetos T[]
    public static <T> void intercambia(T[] array, int pos1, int pos2) {
        if (array == null || pos1 < 0 || pos2 < 0 || pos1 >= array.length || pos2 >= array.length) {
            throw new IndexOutOfBoundsException("Índices fuera de rango para el intercambio.");
        }
        T temporal = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temporal;
    }

    // Método genérico que cuenta cuántas veces aparece 'buscado' en 'array'
    public static <T> int contarApariciones(T[] array, T buscado) {
        if (array == null) return 0;
        int contador = 0;
        for (T elemento : array) {
            if (Objects.equals(elemento, buscado)) {
                contador++;
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        String[] lenguajes = {"Java", "Python", "C++", "Java"};
        System.out.println("Original Strings: " + Arrays.toString(lenguajes));
        intercambia(lenguajes, 0, 2);
        System.out.println("Tras intercambiar 0 y 2: " + Arrays.toString(lenguajes));
        System.out.println("Apariciones de 'Java': " + contarApariciones(lenguajes, "Java"));

        Integer[] numeros = {10, 20, 30, 40};
        intercambia(numeros, 1, 3);
        System.out.println("Tras intercambiar 1 y 3 en Integer[]: " + Arrays.toString(numeros));
    }
}
```

---

## Solución Ejercicio 2: Clases Genéricas `Caja<T>`, `Pareja<T, V>` y `OperaMate<T extends Number>`

```java
import java.util.Objects;

class Caja<T> {
    private T contenido;

    public void guardar(T elem) {
        this.contenido = elem;
    }

    public T extraer() {
        T temp = this.contenido;
        this.contenido = null;
        return temp;
    }

    public boolean estaVacia() {
        return this.contenido == null;
    }

    public void mostrarTipo() {
        if (estaVacia()) {
            System.out.println("La caja está vacía.");
        } else {
            System.out.println("Caja contiene un objeto de tipo: " + contenido.getClass().getName());
        }
    }
}

class Pareja<T, V> {
    private final T primero;
    private final V segundo;

    public Pareja(T primero, V segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T getPrimero() { return primero; }
    public V getSegundo() { return segundo; }

    public boolean esIgualA(Pareja<T, V> otra) {
        if (otra == null) return false;
        return Objects.equals(this.primero, otra.primero)
                && Objects.equals(this.segundo, otra.segundo);
    }

    @Override
    public String toString() {
        return "(" + primero + ", " + segundo + ")";
    }
}

class OperaMate<T extends Number> {
    private final T num;

    public OperaMate(T num) {
        this.num = num;
    }

    public double reciproco() {
        return 1.0 / num.doubleValue();
    }

    public double parteFraccionaria() {
        return num.doubleValue() - num.intValue();
    }

    // Usamos comodín ilimitado <?> para poder comparar p.ej. OperaMate<Integer> con OperaMate<Double>
    public boolean tieneMismoValorAbsoluto(OperaMate<?> otro) {
        return Math.abs(this.num.doubleValue()) == Math.abs(otro.num.doubleValue());
    }
}

public class ClasesGenericasDemo {
    public static void main(String[] args) {
        Caja<String> cajaTexto = new Caja<>();
        cajaTexto.guardar("Certificado Digital");
        cajaTexto.mostrarTipo();
        System.out.println("Extraído: " + cajaTexto.extraer() + " | ¿Vacía?: " + cajaTexto.estaVacia());

        Pareja<Integer, String> p1 = new Pareja<>(404, "Not Found");
        Pareja<Integer, String> p2 = new Pareja<>(404, "Not Found");
        System.out.println("Pareja: " + p1 + " | ¿Igual a p2?: " + p1.esIgualA(p2));

        OperaMate<Integer> opInt = new OperaMate<>(-25);
        OperaMate<Double> opDbl = new OperaMate<>(25.0);
        System.out.println("Recíproco de -25: " + opInt.reciproco());
        System.out.println("¿Mismo valor absoluto (-25 y 25.0)?: " + opInt.tieneMismoValorAbsoluto(opDbl));
    }
}
```

---

## Solución Ejercicio 3: Búsqueda Secuencial en `ArrayList` (`indiceDe`)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BusquedaArrayList {

    public static int indiceDe(ArrayList<String> lista, String valorBuscado) {
        for (int i = 0; i < lista.size(); i++) {
            if (Objects.equals(lista.get(i), valorBuscado)) {
                return i; // Primera aparición encontrada
            }
        }
        return -1; // No encontrado
    }

    public static void main(String[] args) {
        ArrayList<String> letras = new ArrayList<>(List.of("a", "b", "c", "g", "d", "f", "e", "g"));
        System.out.println("Lista: " + letras);
        System.out.println("indiceDe('g'): " + indiceDe(letras, "g") + " (indexOf oficial: " + letras.indexOf("g") + ")");
        System.out.println("indiceDe('z'): " + indiceDe(letras, "z") + " (indexOf oficial: " + letras.indexOf("z") + ")");
    }
}
```

---

## Solución Ejercicio 4: Operaciones Fundamentales con Pilas `Stack<String>` (Actividad EA2)

```java
import java.util.Stack;

public class DemoStackEA2 {
    public static void main(String[] args) {
        Stack<String> pila = new Stack<>();

        // 1. Apilamos elementos con push()
        pila.push("Primero");
        pila.push("Segundo");
        pila.push("Tercero");
        pila.push("Cuarto");

        // 2. Estado inicial y consulta de la cima con peek()
        System.out.println("Contenido completo de la pila: " + pila);
        System.out.println("Elemento en la cima (peek): " + pila.peek());

        // 3. Búsqueda de distancia en base 1 desde la cima con search()
        // Como "Cuarto" es el 1 (cima) y "Tercero" el 2, "Segundo" está a distancia 3
        System.out.println("Distancia de 'Segundo' desde la cima: " + pila.search("Segundo"));
        System.out.println("Distancia de 'Inexistente': " + pila.search("Inexistente"));

        // 4. Desapilado completo (LIFO: sale primero "Cuarto" y último "Primero")
        System.out.print("Extrayendo elementos con pop(): ");
        while (!pila.empty()) {
            System.out.print(pila.pop() + " ");
        }
        System.out.println("\n¿Pila vacía al terminar?: " + pila.empty());
    }
}
```

---

## Solución Ejercicio 5: Inversión de Palabras y Comprobación de Palíndromos con Pila

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class InvertirPalabraPila {

    public static String invertirPalabra(String texto) {
        Deque<Character> pila = new ArrayDeque<>();
        for (int i = 0; i < texto.length(); i++) {
            pila.push(texto.charAt(i));
        }

        StringBuilder invertida = new StringBuilder(texto.length());
        while (!pila.isEmpty()) {
            invertida.append(pila.pop());
        }
        return invertida.toString();
    }

    public static boolean esPalindromoConPila(String palabra) {
        String limpia = palabra.replaceAll("\\s+", "").toLowerCase();
        return limpia.equals(invertirPalabra(limpia));
    }

    public static void main(String[] args) {
        String original = " Murciélago ";
        System.out.println("Original: '" + original + "' -> Invertida: '" + invertirPalabra(original) + "'");
        System.out.println("¿'Reconocer' es palíndromo?: " + esPalindromoConPila("Reconocer"));
        System.out.println("¿'Dabale arroz a la zorra el abad' es palíndromo?: "
                + esPalindromoConPila("Dabale arroz a la zorra el abad"));
    }
}
```

---

## Solución Ejercicio 6: Gestión de una Cola de Impresión FIFO con `Queue<String>` (Actividad EA4)

```java
import java.util.LinkedList;
import java.util.Queue;

public class ColaImpresionEA4 {
    public static void main(String[] args) {
        Queue<String> colaImpresion = new LinkedList<>();

        // 1. Encolamos cinco documentos por orden de llegada
        colaImpresion.offer("Factura_001.pdf");
        colaImpresion.offer("Contrato_Laboral.pdf");
        colaImpresion.offer("Apuntes_UD06.pdf");
        colaImpresion.offer("Nomina_Marzo.pdf");
        colaImpresion.offer("Horario_DAM.pdf");

        // 2. Estado inicial
        System.out.println("Documentos en cola (" + colaImpresion.size() + "): " + colaImpresion);
        System.out.println("Próximo documento en imprimirse (peek): " + colaImpresion.peek());
        System.out.println("--------------------------------------------------");

        // 3. Procesamiento FIFO con poll()
        while (!colaImpresion.isEmpty()) {
            String impreso = colaImpresion.poll();
            System.out.println("🖨️ Imprimiendo: " + impreso + " | Pendientes: " + colaImpresion.size());
        }
    }
}
```

---

## Solución Ejercicio 7: Triaje de Urgencias con `PriorityQueue<E>` (Actividad EA5)

```java
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueEA5 {

    record Paciente(String nombre, int nivelUrgencia) implements Comparable<Paciente> {
        @Override
        public int compareTo(Paciente otro) {
            return Integer.compare(this.nivelUrgencia, otro.nivelUrgencia);
        }

        @Override
        public String toString() {
            return nombre + " (Urgencia " + nivelUrgencia + ")";
        }
    }

    public static void main(String[] args) {
        // Parte 1: PriorityQueue de enteros
        Queue<Integer> pqNumeros = new PriorityQueue<>(List.of(40, 10, 50, 20, 30));
        System.out.println("Recorrido for-each del heap interno: " + pqNumeros);
        System.out.print("Extracción ordenada con poll(): ");
        while (!pqNumeros.isEmpty()) {
            System.out.print(pqNumeros.poll() + " ");
        }
        System.out.println("\n");

        // Parte 2: Triaje hospitalario
        Queue<Paciente> urgencias = new PriorityQueue<>();
        urgencias.offer(new Paciente("Carlos (Esguince)", 4));
        urgencias.offer(new Paciente("Elena (Dolor torácico)", 1));
        urgencias.offer(new Paciente("Marcos (Fiebre alta)", 2));
        urgencias.offer(new Paciente("Lucía (Consulta receta)", 5));
        urgencias.offer(new Paciente("Pedro (Fractura abierta)", 1));

        System.out.println("Orden de atención en Urgencias:");
        while (!urgencias.isEmpty()) {
            System.out.println("  -> Atendiendo a: " + urgencias.poll());
        }
    }
}
```

---

## Solución Ejercicio 8: `Persona` y `ListaPersonas` con `ArrayList`, `Vector` e `Iterator` (EA6, EA7 y EA8)

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

class Persona implements Comparable<Persona> {
    private String dni;
    private String nombre;
    private String apellidos;
    private int edad;

    public Persona(String dni, String nombre, String apellidos, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public int getEdad() { return edad; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    // Orden natural requerido también en EA9: por apellidos y luego por nombre
    @Override
    public int compareTo(Persona otra) {
        int cmpApellido = this.apellidos.compareToIgnoreCase(otra.apellidos);
        if (cmpApellido != 0) {
            return cmpApellido;
        }
        return this.nombre.compareToIgnoreCase(otra.nombre);
    }

    @Override
    public String toString() {
        return String.format("%s - %s, %s (%d años)", dni, apellidos, nombre, edad);
    }
}

public class ListaPersonas {
    private final List<Persona> personas = new ArrayList<>();

    public void anadir(Persona p) {
        if (!personas.contains(p)) {
            personas.add(p);
        }
    }

    public boolean eliminarPorDni(String dni) {
        return personas.removeIf(p -> p.getDni().equalsIgnoreCase(dni));
    }

    public Persona buscarPorDni(String dni) {
        for (Persona p : personas) {
            if (p.getDni().equalsIgnoreCase(dni)) return p;
        }
        return null;
    }

    public List<Persona> buscarPorApellido(String apellido) {
        List<Persona> resultado = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getApellidos().toLowerCase().contains(apellido.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // EA8: Recorrido explícito con Iterator
    public void mostrarConIterador() {
        Iterator<Persona> it = personas.iterator();
        while (it.hasNext()) {
            System.out.println("  * " + it.next());
        }
    }

    // EA8: Borrado seguro durante iteración con it.remove()
    public int eliminarMenoresDeEdad() {
        int borrados = 0;
        Iterator<Persona> it = personas.iterator();
        while (it.hasNext()) {
            Persona actual = it.next();
            if (actual.getEdad() < 18) {
                it.remove();
                borrados++;
            }
        }
        return borrados;
    }

    public static void main(String[] args) {
        ListaPersonas censo = new ListaPersonas();
        censo.anadir(new Persona("11111111A", "Ana", "García López", 24));
        censo.anadir(new Persona("22222222B", "Lucas", "Martínez Ruiz", 16));
        censo.anadir(new Persona("33333333C", "Elena", "García Pérez", 15));
        censo.anadir(new Persona("44444444D", "David", "Sánchez Gómez", 31));

        System.out.println("Censo completo (con Iterator):");
        censo.mostrarConIterador();

        int eliminados = censo.eliminarMenoresDeEdad();
        System.out.println("\nEliminados " + eliminados + " menores de edad. Censo restante:");
        censo.mostrarConIterador();

        // EA7: Equivalencia con Vector
        Vector<Persona> vectorLegacy = new Vector<>();
        vectorLegacy.addElement(censo.buscarPorDni("11111111A"));
        System.out.println("\nPrimer elemento en Vector legacy: " + vectorLegacy.firstElement());
    }
}
```

---

## Solución Ejercicio 9: Ordenación Natural y Multicriterio de `Persona` (Actividad EA9)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenacionPersonasEA9 {
    public static void main(String[] args) {
        List<Persona> lista = new ArrayList<>(List.of(
            new Persona("44444444D", "David", "Sánchez", 31),
            new Persona("11111111A", "Zoe", "García", 24),
            new Persona("33333333C", "Ana", "García", 19),
            new Persona("22222222B", "Lucas", "Martínez", 28)
        ));

        // 1. Orden natural (Comparable: por apellidos y luego nombre)
        Collections.sort(lista);
        System.out.println("1. Orden natural (Apellidos, Nombre):");
        lista.forEach(p -> System.out.println("   " + p));

        // 2. Comparator por edad ascendente
        lista.sort(Comparator.comparingInt(Persona::getEdad));
        System.out.println("\n2. Ordenadas por edad ascendente:");
        lista.forEach(p -> System.out.println("   " + p));

        // 3. Comparator por DNI descendente
        lista.sort(Comparator.comparing(Persona::getDni).reversed());
        System.out.println("\n3. Ordenadas por DNI descendente:");
        lista.forEach(p -> System.out.println("   " + p));
    }
}
```

---

## Solución Ejercicio 10: Colecciones Inmutables con Métodos Factoría de Java 9+ (Actividad EA10)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactoriasInmutablesEA10 {
    public static void main(String[] args) {
        List<String> modulos = List.of("Programación", "Bases de Datos", "Sistemas Informáticos");
        Set<String> provincias = Set.of("Valencia", "Alicante", "Castellón");
        Map<String, Integer> prefijos = Map.of("España", 34, "Francia", 33, "Portugal", 351);

        System.out.println("Módulos: " + modulos);
        System.out.println("Provincias: " + provincias);
        System.out.println("Prefijos: " + prefijos);

        // 1. Intento de añadir a List.of
        try {
            modulos.add("Entornos de Desarrollo");
        } catch (UnsupportedOperationException e) {
            System.out.println("✅ Capturada UnsupportedOperationException al intentar add() en List.of");
        }

        // 2. Intento de modificar con set()
        try {
            modulos.set(0, "Otro");
        } catch (UnsupportedOperationException e) {
            System.out.println("✅ Capturada UnsupportedOperationException al intentar set() en List.of");
        }

        // 3. Intento de insertar null
        try {
            List.of("A", null);
        } catch (NullPointerException e) {
            System.out.println("✅ Capturada NullPointerException: las factorías .of() prohíben elementos null");
        }

        // 4. Intento de duplicados en Set.of
        try {
            Set.of("Java", "Kotlin", "Java");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Capturada IllegalArgumentException al pasar duplicados a Set.of");
        }

        // 5. Copia mutable en ArrayList
        List<String> modulosMutables = new ArrayList<>(modulos);
        modulosMutables.add("Lenguajes de Marcas");
        System.out.println("Copia mutable ampliada: " + modulosMutables);
    }
}
```

---

## Solución Ejercicio 11: Operaciones de Teoría de Conjuntos con `HashSet` y `TreeSet`

```java
import java.util.Set;
import java.util.TreeSet;

public class OperacionesConjuntosExtraescolares {
    public static void main(String[] args) {
        Set<String> robotica = Set.of("Ana", "Luis", "Carlos", "Elena", "Marta");
        Set<String> ajedrez = Set.of("Carlos", "Sofía", "Ana", "Pedro", "Jorge");

        // 1. Unión (A ∪ B)
        Set<String> union = new TreeSet<>(robotica);
        union.addAll(ajedrez);
        System.out.println("Unión (todos los participantes): " + union);

        // 2. Intersección (A ∩ B)
        Set<String> ambos = new TreeSet<>(robotica);
        ambos.retainAll(ajedrez);
        System.out.println("Intersección (hacen ambas actividades): " + ambos);

        // 3. Diferencia (Robótica - Ajedrez)
        Set<String> soloRobotica = new TreeSet<>(robotica);
        soloRobotica.removeAll(ajedrez);
        System.out.println("Diferencia (solo Robótica): " + soloRobotica);

        // 4. Diferencia simétrica ((A ∪ B) - (A ∩ B))
        Set<String> simetrica = new TreeSet<>(union);
        simetrica.removeAll(ambos);
        System.out.println("Diferencia simétrica (solo una actividad): " + simetrica);
    }
}
```

---

## Solución Ejercicio 12: Listín Telefónico con `HashMap<String, String>`

```java
import java.util.HashMap;
import java.util.Map;

public class ListinTelefonico {
    private final Map<String, String> contactos = new HashMap<>();

    public void anadirContacto(String nombre, String telefono) {
        String anterior = contactos.put(nombre, telefono);
        if (anterior != null) {
            System.out.printf("ℹ️ Actualizado teléfono de %s (antes %s -> ahora %s)%n", nombre, anterior, telefono);
        } else {
            System.out.printf("✅ Añadido nuevo contacto: %s (%s)%n", nombre, telefono);
        }
    }

    public String buscarTelefono(String nombre) {
        return contactos.getOrDefault(nombre, "Contacto no registrado");
    }

    public boolean eliminarContacto(String nombre) {
        return contactos.remove(nombre) != null;
    }

    public void mostrarListin() {
        System.out.println("--- LISTÍN TELEFÓNICO (" + contactos.size() + " contactos) ---");
        for (Map.Entry<String, String> entrada : contactos.entrySet()) {
            System.out.println("  📞 " + entrada.getKey() + " -> " + entrada.getValue());
        }
    }

    public static void main(String[] args) {
        ListinTelefonico listin = new ListinTelefonico();
        listin.anadirContacto("Ana", "600111222");
        listin.anadirContacto("Carlos", "600333444");
        listin.anadirContacto("Ana", "699888777"); // Actualiza el de Ana

        System.out.println("Teléfono de Carlos: " + listin.buscarTelefono("Carlos"));
        System.out.println("Teléfono de Pedro: " + listin.buscarTelefono("Pedro"));

        listin.eliminarContacto("Carlos");
        listin.mostrarListin();
    }
}
```
