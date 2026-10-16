---
title: "✅ Boletín 06 — Nivel Intermedio (Solucionario)"
description: "Soluciones completas y comentadas en Java 25 LTS de los 12 ejercicios del Boletín Intermedio de la Unidad 6: Genéricos y Colecciones."
draft: true
prev: false
next: false
---

A continuación se presentan las **soluciones completas en Java 25 LTS** para los 12 ejercicios propuestos en el [**Boletín 06 — Nivel Intermedio**](/ProgramacionDAM/ud06/boletin-06-intermedio/).

---

## Solución Ejercicio 1: Validador de Expresiones Balanceadas `({[]})` (Actividad EA3)

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ValidadorParentesisEA3 {

    public static boolean estaBalanceada(String expresion) {
        Deque<Character> pila = new ArrayDeque<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // 1. Si es de apertura, apilamos
            if (c == '(' || c == '[' || c == '{') {
                pila.push(c);
            }
            // 2. Si es de cierre, verificamos correspondencia con la cima de la pila
            else if (c == ')' || c == ']' || c == '}') {
                if (pila.isEmpty()) {
                    return false; // Cierre sin apertura previa
                }
                char cima = pila.pop();
                if ((c == ')' && cima != '(') ||
                    (c == ']' && cima != '[') ||
                    (c == '}' && cima != '{')) {
                    return false; // Delimitadores cruzados
                }
            }
        }
        // 3. Al terminar, la pila debe haber quedado completamente vacía
        return pila.isEmpty();
    }

    public static void main(String[] args) {
        String[] pruebas = {
            "{[a * (b + c)] - 5}",
            "[(a + b) * {c - d]}",
            "((a + b)",
            "(a + b))"
        };

        for (String expr : pruebas) {
            System.out.printf("Expresión: %-24s -> ¿Balanceada?: %b%n", expr, estaBalanceada(expr));
        }
    }
}
```

---

## Solución Ejercicio 2: Inserción Ordenada en una Pila (`insertarOrdenado`)

```java
import java.util.Stack;

public class InsertarOrdenadoPila {

    public static void insertarOrdenado(Stack<Integer> pila, int valor) {
        Stack<Integer> aux = new Stack<>();

        // 1. Pasamos a la pila auxiliar todos los elementos de la cima que sean mayores que 'valor'
        while (!pila.isEmpty() && pila.peek() > valor) {
            aux.push(pila.pop());
        }

        // 2. Insertamos 'valor' en su posición ordenada
        pila.push(valor);

        // 3. Devolvemos los elementos mayores desde la pila auxiliar a la pila original
        while (!aux.isEmpty()) {
            pila.push(aux.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> numeros = new Stack<>();
        numeros.push(1);
        numeros.push(3);
        numeros.push(5);
        numeros.push(10);

        System.out.println("Pila antes de insertar: " + numeros);
        insertarOrdenado(numeros, 7);
        System.out.println("Pila tras insertar 7:   " + numeros);
        insertarOrdenado(numeros, 0);
        System.out.println("Pila tras insertar 0:   " + numeros);
    }
}
```

---

## Solución Ejercicio 3: Expulsión Selectiva en una Cola de Cine (`expulsarCliente`)

```java
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExpulsarClienteCola {

    public static void expulsarCliente(Queue<String> cola, String nombreExpulsado) {
        int totalInicial = cola.size();
        // Damos exactamente una vuelta completa a la cola original usando solo poll() y offer()
        for (int i = 0; i < totalInicial; i++) {
            String cliente = cola.poll();
            if (!cliente.equalsIgnoreCase(nombreExpulsado)) {
                cola.offer(cliente); // Solo vuelve a la cola si no es el expulsado
            }
        }
    }

    public static void main(String[] args) {
        Queue<String> taquilla = new LinkedList<>(List.of("Juan", "Pepe", "María", "Pepe", "Ana", "Pepe"));
        System.out.println("Cola inicial: " + taquilla);
        expulsarCliente(taquilla, "Pepe");
        System.out.println("Cola tras expulsar a 'Pepe': " + taquilla);
    }
}
```

---

## Solución Ejercicio 4: Historial de Navegador Web con `HashMap<String, String>`

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavegadorWeb {
    private final Map<String, String> historial = new HashMap<>();

    public void visitarPagina(String nombre, String url) {
        historial.put(nombre, url);
    }

    public String consultarUrl(String nombre) {
        return historial.getOrDefault(nombre, "El sitio '" + nombre + "' no figura en el historial.");
    }

    public boolean borrarDelHistorial(String nombre) {
        return historial.remove(nombre) != null;
    }

    public void vaciarHistorial() {
        historial.clear();
    }

    public List<String> buscarPorDominio(String extension) {
        List<String> sitios = new ArrayList<>();
        for (Map.Entry<String, String> entrada : historial.entrySet()) {
            if (entrada.getValue().endsWith(extension)) {
                sitios.add(entrada.getKey() + " (" + entrada.getValue() + ")");
            }
        }
        return sitios;
    }

    public static void main(String[] args) {
        NavegadorWeb nav = new NavegadorWeb();
        nav.visitarPagina("Buscador", "https://www.google.es");
        nav.visitarPagina("Enciclopedia", "https://es.wikipedia.org");
        nav.visitarPagina("Instituto", "https://portal.edu.gva.es");

        System.out.println("URL de Instituto: " + nav.consultarUrl("Instituto"));
        System.out.println("Sitios con dominio .es: " + nav.buscarPorDominio(".es"));
    }
}
```

---

## Solución Ejercicio 5: Agenda Multiteléfono de Clientes y Búsqueda Inversa

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Cliente {
    private final String nombre;
    private final ArrayList<String> telefonos = new ArrayList<>();

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public List<String> getTelefonos() { return telefonos; }

    public void addTelefono(String numero) {
        if (!telefonos.contains(numero)) {
            telefonos.add(numero);
        }
    }

    @Override
    public String toString() {
        return nombre + " -> " + telefonos;
    }
}

public class GestorAgenda {
    private final Map<String, Cliente> agenda = new HashMap<>();

    public void registrarTelefono(String nombreCliente, String telefono) {
        Cliente c = agenda.computeIfAbsent(nombreCliente, Cliente::new);
        c.addTelefono(telefono);
    }

    public List<String> buscarClientesPorPrefijoTelefono(String prefijo) {
        List<String> encontrados = new ArrayList<>();
        for (Cliente c : agenda.values()) {
            for (String tel : c.getTelefonos()) {
                if (tel.startsWith(prefijo)) {
                    encontrados.add(c.getNombre());
                    break; // Ya cumple, pasamos al siguiente cliente
                }
            }
        }
        return encontrados;
    }

    public static void main(String[] args) {
        GestorAgenda gestor = new GestorAgenda();
        gestor.registrarTelefono("Alberto", "963112233");
        gestor.registrarTelefono("Alberto", "600998877");
        gestor.registrarTelefono("Beatriz", "915443322");
        gestor.registrarTelefono("Carmen", "961002200");

        System.out.println("Clientes con teléfono fijo de Valencia (96): "
                + gestor.buscarClientesPorPrefijoTelefono("96"));
    }
}
```

---

## Solución Ejercicio 6: Estadísticas sobre Listas: `contar` y `contarSI` (Actividad EA11)

```java
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class TablaEA11 {
    private final List<String> datos;

    public TablaEA11(List<String> datos) {
        this.datos = datos;
    }

    public int contar(String valor) {
        int total = 0;
        for (String item : datos) {
            if (Objects.equals(item, valor)) {
                total++;
            }
        }
        return total;
    }

    public int contarSI(Predicate<String> condicion) {
        int total = 0;
        for (String item : datos) {
            if (item != null && condicion.test(item)) {
                total++;
            }
        }
        return total;
    }

    public Map<String, Integer> tablaFrecuencias() {
        Map<String, Integer> frec = new LinkedHashMap<>();
        for (String item : datos) {
            frec.merge(item, 1, Integer::sum);
        }
        return frec;
    }

    public static void main(String[] args) {
        TablaEA11 columna = new TablaEA11(List.of(
            "Aprobado", "Sobresaliente", "Aprobado", "Notable", "Suspenso", "Sobresaliente", "Aprobado"
        ));

        System.out.println("Apariciones de 'Aprobado': " + columna.contar("Aprobado"));
        System.out.println("Palabras de más de 8 letras (contarSI): " + columna.contarSI(s -> s.length() > 8));
        System.out.println("Tabla de frecuencias completa: " + columna.tablaFrecuencias());
    }
}
```

---

## Solución Ejercicio 7: Codificación Ordinal Automática de Variables Categóricas (Actividad EA13)

```java
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodificadorOrdinalEA13 {
    public static void main(String[] args) {
        List<String> encuestaEstudios = List.of(
            "Bachillerato", "FP Superior", "ESO", "FP Superior",
            "Universidad", "Bachillerato", "Primaria", "FP Superior"
        );

        Map<String, Integer> diccionarioCodigos = new LinkedHashMap<>();
        List<Integer> columnaCodificada = new ArrayList<>(encuestaEstudios.size());

        for (String nivel : encuestaEstudios) {
            // Si el nivel no estaba en el mapa, le asigna el tamaño actual del mapa (0, 1, 2...)
            diccionarioCodigos.putIfAbsent(nivel, diccionarioCodigos.size());
            columnaCodificada.add(diccionarioCodigos.get(nivel));
        }

        System.out.println("Diccionario de codificación ordinal: " + diccionarioCodigos);
        System.out.println("Datos originales:  " + encuestaEstudios);
        System.out.println("Datos codificados: " + columnaCodificada);
    }
}
```

---

## Solución Ejercicio 8: Analizador de Frecuencias Literarias de Bécquer (Actividad EA14)

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnalizadorBecquerEA14 {
    public static void main(String[] args) {
        String poema = """
            Volverán las oscuras golondrinas
            en tu balcón sus nidos a colgar,
            y otra vez con el ala a sus cristales
            jugando llamarán.
            Pero aquellas que el vuelo refrenaban
            tu hermosura y mi dicha a contemplar,
            aquellas que aprendieron nuestros nombres,
            esas... ¡no volverán!
            Volverán las tupidas madreselvas
            de tu jardín las tapias a escalar,
            y otra vez a la tarde aún más hermosas
            sus flores se abrirán.
            """;

        // 1. Limpieza de signos de puntuación y paso a minúsculas
        String limpio = poema.toLowerCase().replaceAll("[.,;:¡!¿?]+", " ");
        String[] palabras = limpio.trim().split("\\s+");

        // 2. Conteo en TreeMap (ya queda ordenado alfabéticamente para el Informe A)
        Map<String, Integer> frecuencias = new TreeMap<>();
        for (String p : palabras) {
            if (!p.isEmpty()) {
                frecuencias.merge(p, 1, Integer::sum);
            }
        }

        System.out.println("=== INFORME A: ORDEN ALFABÉTICO ===");
        frecuencias.forEach((palabra, cont) -> System.out.printf("  %-14s -> %d%n", palabra, cont));

        // 3. Informe B: Ordenado por frecuencia descendente y, en empate, alfabéticamente
        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(frecuencias.entrySet());
        ranking.sort(
            Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                     .thenComparing(Map.Entry.comparingByKey())
        );

        System.out.println("\n=== INFORME B: TOP 10 PALABRAS MÁS REPETIDAS ===");
        for (int i = 0; i < Math.min(10, ranking.size()); i++) {
            var e = ranking.get(i);
            System.out.printf("  %2d. %-14s (%d veces)%n", i + 1, e.getKey(), e.getValue());
        }
    }
}
```

---

## Solución Ejercicio 9: Agrupación de Obras por Escritor con `Map<String, List<String>>` (Actividad EA15)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ObrasPorEscritorEA15 {
    public static void main(String[] args) {
        List<String> lineasLiteratura = List.of(
            "Miguel de Cervantes;El Quijote",
            "Lope de Vega;Fuenteovejuna",
            "Miguel de Cervantes;La Galatea",
            "Calderón de la Barca;La vida es sueño",
            "Miguel de Cervantes;Rinconete y Cortadillo",
            "Lope de Vega;El perro del hortelano",
            "Federico García Lorca;Bodas de sangre"
        );

        // Usamos TreeMap para que los autores queden ordenados alfabéticamente
        Map<String, List<String>> catalogo = new TreeMap<>();

        for (String linea : lineasLiteratura) {
            String[] partes = linea.split(";");
            String autor = partes[0].trim();
            String obra = partes[1].trim();
            catalogo.computeIfAbsent(autor, k -> new ArrayList<>()).add(obra);
        }

        String autorMasProlifico = "";
        int maxObras = -1;

        System.out.println("=== CATÁLOGO LITERARIO POR AUTOR ===");
        for (Map.Entry<String, List<String>> entrada : catalogo.entrySet()) {
            String autor = entrada.getKey();
            List<String> obras = entrada.getValue();
            Collections.sort(obras); // Ordenamos las obras de cada autor alfabéticamente
            System.out.println("✒️ " + autor + " (" + obras.size() + " obras): " + obras);

            if (obras.size() > maxObras) {
                maxObras = obras.size();
                autorMasProlifico = autor;
            }
        }
        System.out.println("\n🏆 Autor con más obras registradas: " + autorMasProlifico + " (" + maxObras + ")");
    }
}
```

---

## Solución Ejercicio 10: Agrupación de Alumnos por Calificación e Histograma (Actividad EA16)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AlumnosPorNotaEA16 {
    public static void main(String[] args) {
        List<String> actasExamen = List.of(
            "Lucía;9", "Marcos;7", "Elena;10", "Pedro;5", "Sofía;9",
            "Jorge;7", "Ana;10", "Carlos;4", "Marta;7", "David;9"
        );

        NavigableMap<Integer, List<String>> porNota = new TreeMap<>();

        for (String registro : actasExamen) {
            String[] partes = registro.split(";");
            String alumno = partes[0].trim();
            int nota = Integer.parseInt(partes[1].trim());
            porNota.computeIfAbsent(nota, k -> new ArrayList<>()).add(alumno);
        }

        System.out.println("=== ACTA DE CALIFICACIONES (DE 10 A 0) ===");
        for (Map.Entry<Integer, List<String>> entrada : porNota.descendingMap().entrySet()) {
            System.out.printf("Nota %2d (%d alumnos): %s%n",
                    entrada.getKey(), entrada.getValue().size(), entrada.getValue());
        }
    }
}
```

---

## Solución Ejercicio 11: Gestión de Matrículas de 1º DAM/DAW con `HashMap<String, Alumno>`

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Alumno {
    private final String nia;
    private final String nombre;
    private final String ciclo;
    private double notaMedia;

    public Alumno(String nia, String nombre, String ciclo, double notaMedia) {
        this.nia = nia;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.notaMedia = notaMedia;
    }

    public String getNia() { return nia; }
    public String getNombre() { return nombre; }
    public String getCiclo() { return ciclo; }
    public double getNotaMedia() { return notaMedia; }
    public void setNotaMedia(double notaMedia) { this.notaMedia = notaMedia; }

    @Override
    public String toString() {
        return String.format("[%s] %-12s (%s) -> Nota: %.2f", nia, nombre, ciclo, notaMedia);
    }
}

public class SecretariaVirtual {
    private final Map<String, Alumno> matriculados = new HashMap<>();

    public boolean matricular(Alumno a) {
        return matriculados.putIfAbsent(a.getNia(), a) == null;
    }

    public boolean darDeBaja(String nia) {
        return matriculados.remove(nia) != null;
    }

    public boolean modificarNotaMedia(String nia, double nuevaNota) {
        Alumno a = matriculados.get(nia);
        if (a == null) return false;
        a.setNotaMedia(nuevaNota);
        return true;
    }

    public List<Alumno> listarPorNotaDescendente() {
        List<Alumno> lista = new ArrayList<>(matriculados.values());
        lista.sort(Comparator.comparingDouble(Alumno::getNotaMedia).reversed());
        return lista;
    }

    public Map<String, Double> calcularMediaPorCiclo() {
        Map<String, Double> sumaNotas = new HashMap<>();
        Map<String, Integer> conteo = new HashMap<>();

        for (Alumno a : matriculados.values()) {
            sumaNotas.merge(a.getCiclo(), a.getNotaMedia(), Double::sum);
            conteo.merge(a.getCiclo(), 1, Integer::sum);
        }

        Map<String, Double> medias = new HashMap<>();
        for (String ciclo : sumaNotas.keySet()) {
            medias.put(ciclo, sumaNotas.get(ciclo) / conteo.get(ciclo));
        }
        return medias;
    }

    public static void main(String[] args) {
        SecretariaVirtual sec = new SecretariaVirtual();
        sec.matricular(new Alumno("1001", "Laura", "DAM", 8.75));
        sec.matricular(new Alumno("1002", "Miguel", "DAW", 7.40));
        sec.matricular(new Alumno("1003", "Clara", "DAM", 9.30));
        sec.matricular(new Alumno("1004", "Hugo", "DAW", 8.10));

        sec.modificarNotaMedia("1002", 7.90);

        System.out.println("Ranking por nota media:");
        sec.listarPorNotaDescendente().forEach(a -> System.out.println("  " + a));
        System.out.println("Nota media por ciclo formativo: " + sec.calcularMediaPorCiclo());
    }
}
```

---

## Solución Ejercicio 12: Coeficiente Binomial `C(n, k)` con Memoización en `Map`

```java
import java.util.HashMap;
import java.util.Map;

public class CalculadoraCombinatoria {
    private static final Map<String, Long> memo = new HashMap<>();

    public static long combinatorio(int n, int k) {
        if (k == 0 || k == n) {
            return 1L;
        }
        String clave = n + "," + k;
        // Si el subproblema ya está en el mapa, lo devolvemos en O(1)
        if (memo.containsKey(clave)) {
            return memo.get(clave);
        }
        long resultado = combinatorio(n - 1, k - 1) + combinatorio(n - 1, k);
        memo.put(clave, resultado);
        return resultado;
    }

    public static void main(String[] args) {
        long t0 = System.nanoTime();
        long Formas = combinatorio(60, 30);
        long t1 = System.nanoTime();

        System.out.println("C(60, 30) = " + Formas);
        System.out.printf("Calculado en %.3f ms gracias a %d estados memorizados en el Map.%n",
                (t1 - t0) / 1_000_000.0, memo.size());
    }
}
```
