---
title: "Boletín 5.2: Ejercicios Intermedios — Arquitecturas Jerárquicas, Clases Anidadas y Programación Funcional"
description: "12 problemas de nivel intermedio sobre composición frente a agregación, jerarquías multinivel, interfaces con métodos default, clases anidadas y composición de expresiones lambda en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 3
  badge:
    text: "Intermedio"
    variant: "caution"
---

En este segundo boletín de la **Unidad 5: Programación orientada a objetos avanzada** resolverás problemas completos de arquitectura de clases: contrastarás la agregación frente a la composición en estructuras geométricas, construirás jerarquías de varios niveles con clases abstractas e interfaces, diseñarás clases estáticas anidadas de doble precisión y dominarás la evolución hacia expresiones lambda y su composición funcional.

---

### Ejercicio 1: Agregación vs. Composición en Geometría (`Punto` y `Poligono`)

1. Escribe la clase `Punto` con dos atributos `double x` e `y`, constructores (incluyendo constructor de copia), getters, setters, método `public double distancia(Punto otro)`, método `public void desplazar(double dx, double dy)`, método `public void escalar(double fx, double fy)` y sobrescritura de `toString()` con formato `"(x, y)"`.
2. Utilizando la clase `Punto`, escribe la clase `Poligono`, cuyo atributo interno es un array `Punto[] vertices` donde los puntos en celdas consecutivas (y el último con el primero) forman los lados del polígono:
   - En el constructor, valida que el array tenga **al menos 3 vértices** (en caso contrario, lanza `IllegalArgumentException`).
   - Implementa dos constructores o modalidades para contrastar **Agregación** (almacena directamente el array recibido) frente a **Composición fuerte** (realiza una copia profunda tanto del array como de cada objeto `Punto` en su interior).
   - Métodos `trasladar(double dx, double dy)`, `escalar(double fx, double fy)`, `numVertices()` y `perimetro()` (suma de las distancias de cada vértice al siguiente, cerrando el último con la celda `0`).
3. En el `main`, demuestra cómo alterar un `Punto` externo afecta al polígono agregado pero deja intacto al polígono compuesto.

---

### Ejercicio 2: Jerarquía Abstracta de `Vehiculo`, `Velero`, `Bicicleta` y `CocheAsegurado` (`Asegurable`)

1. Crea la clase abstracta `Vehiculo` con atributos protegidos `String fuentePotencia`, `int ruedas` y `double precio`. Su constructor recibe la fuente de potencia, el número de ruedas y el precio, e invoca al método abstracto `public abstract void setPrecio(double precio);`.
2. Crea dos subclases de `Vehiculo`:
   - `Velero`: añade el atributo `int eslora` (en metros) y fija en `setPrecio(double)` un límite máximo constante de `100_000.00 €` (si se supera, se topa en ese máximo).
   - `Bicicleta`: tiene siempre `2` ruedas, puede ser impulsada por `"una persona"` o por `"motor eléctrico"`, y fija un precio máximo de `3_999.99 €`.
3. Crea la interfaz `Asegurable` con los métodos `void setCobertura()` y `double getCobertura()`.
4. Crea la clase `CocheAsegurado` que extienda a `Vehiculo` e implemente `Asegurable`:
   - Añade el atributo `private double cobertura`.
   - Su límite de precio máximo en `setPrecio` es de `60_000.00 €`.
   - `setCobertura()` calcula y fija el importe asegurado en el **90 %** del precio del coche.
5. En el `main`, crea un array polimórfico `Vehiculo[] flota` con 5 vehículos variados. Recórrelo construyendo un resumen con `StringBuilder` que incluya el contador `"#n"`, el nombre de su clase (`getClass().getSimpleName()`) y sus datos, y contabiliza usando `instanceof` cuántos veleros, bicicletas y coches asegurados hay.

---

### Ejercicio 3: Polimorfismo con Interfaces en la Selección de Fútbol (`Seleccionable`)

1. Define la interfaz `Seleccionable` con los métodos `void concentrarse()`, `void viajar()`, `void entrenar()` y `void jugarPartido()`.
2. Crea la clase abstracta `MiembroSeleccionFutbol` que implemente `Seleccionable`, con los atributos `protected String nombre` y `protected int edad`, constructores, getters/setters e implementación común de `concentrarse()` y `viajar()`.
3. Crea la subclase `Futbolista extends MiembroSeleccionFutbol` añadiendo `int dorsal` y `String demarcacion`. Sobrescribe `entrenar()` y `jugarPartido()`, y añade el método específico `public void entrevista()`.
4. Crea la subclase `Entrenador extends MiembroSeleccionFutbol` añadiendo `String idFederacion`. Sobrescribe `entrenar()` y `jugarPartido()`, y añade el método específico `public void planificarEntrenamiento()`.
5. En la clase `TestSeleccion`, define un array o lista de objetos `Seleccionable` con un entrenador y varios futbolistas. Mediante bucles polimórficos haz que todos se concentren, viajen, entrenen y jueguen el partido; finalmente, recorre la colección y utiliza `instanceof` con *Pattern Matching* para que solo los futbolistas concedan una entrevista y solo el entrenador planifique el entrenamiento.

---

### Ejercicio 4: Implementación Jerárquica de la Interfaz `Imprimible`

1. Define la interfaz `Imprimible` con dos métodos:
   - `ArrayList<String> contenidoArrayList()`: devuelve una lista de cadenas `"atributo=valor"`.
   - `String contenidoString()`: devuelve una única cadena con formato `"{atributo1=valor1, atributo2=valor2, ...}"`. Implementa este método como método **`default`** en la propia interfaz apoyándote en `contenidoArrayList()`.
2. Haz que la clase abstracta `Persona` (`nombre`, `fechaNacimiento`) implemente `Imprimible`.
3. Haz que las subclases `Alumno` (`grupo`, `notaMedia`) y `Profesor` (`especialidad`, `salario`) hereden de `Persona` y sobrescriban únicamente `contenidoArrayList()` reutilizando `super.contenidoArrayList()` para no duplicar código.

---

### Ejercicio 5: Jerarquía `Intervalo`, `Localizable` e `IntervaloAbierto`

1. Crea la interfaz `Localizable` que obligue a implementar `boolean estaDentro(int x)`.
2. Crea la clase `Intervalo implements Localizable` que representa el intervalo cerrado de enteros `[menor, mayor]`:
   - En el constructor `public Intervalo(int menor, int mayor)`, si `menor == mayor` lanza una `IllegalArgumentException` (precondición: los extremos deben ser distintos). Si `mayor < menor`, intercámbialos automáticamente para garantizar que `this.menor < this.mayor`.
   - Añade getters y setters manteniendo siempre la invariante `menor < mayor`.
   - Implementa `estaDentro(int x)` devolviendo `true` si `menor <= x && x <= mayor`.
   - Añade un constructor por defecto `public Intervalo()` que calcule dos valores a partir de `LocalDateTime.now()` (`d1 = hora + segundos * 10` y `d2 = mes * minutos + 1`) y reutilice el constructor parametrizado mediante `this(...)` y métodos auxiliares `private static`.
3. Crea la subclase `IntervaloAbierto extends Intervalo` que añade los atributos `private boolean abiertoAbajo` y `private boolean abiertoArriba`:
   - Añade un constructor por defecto (abierto por ambos extremos) y un constructor completo apoyándose en `super(...)`.
   - Sobrescribe `estaDentro(int x)` invocando primero a `super.estaDentro(x)` y comprobando adicionalmente que, si es abierto por abajo, `x > getMenor()`, y si es abierto por arriba, `x < getMayor()`.

---

### Ejercicio 6: Clases Anidadas Estáticas de Doble Precisión (`Punto2D.Float` y `Punto2D.Double`)

En aplicaciones gráficas (como en la librería `java.awt.geom` del JDK) a veces necesitamos millones de puntos con coordenadas `float` para ahorrar memoria RAM, y otras veces coordenadas `double` para máxima precisión matemática:
1. Diseña la clase abstracta `Punto2D` con los métodos abstractos `public abstract double getX()`, `public abstract double getY()` y `public abstract Punto2D suma(Punto2D otro)`.
2. Dentro de `Punto2D`, declara dos **clases estáticas anidadas públicas** que extiendan a `Punto2D`:
   - `public static class Float extends Punto2D`: almacena `private float x, y`. Su método `suma(Punto2D otro)` devuelve un nuevo `Punto2D.Float`.
   - `public static class Double extends Punto2D`: almacena `private double x, y`. Su método `suma(Punto2D otro)` devuelve un nuevo `Punto2D.Double`.
3. Comprueba en el método `main` el funcionamiento de `new Punto2D.Float(1.0f, 2.0f)`, `new Punto2D.Double(1.0 / 3.0, 2.0)` y la suma cruzada entre ambas precisiones.

---

### Ejercicio 7: Las 5 Evoluciones de `java.io.FileFilter` (De Clase Externa a Lambda)

La interfaz funcional estándar `java.io.FileFilter` declara el método `boolean accept(File pathname)` y se utiliza en `carpeta.listFiles(filtro)` para obtener únicamente los archivos que cumplan una condición.
Implementa en un programa demostrativo las **5 formas históricas** de filtrar los archivos terminados en `".md"` o `".java"` del directorio actual:
1. **Enfoque A:** Mediante una clase con nombre de nivel superior (`FiltroClaseExterna`).
2. **Enfoque B:** Mediante una clase estática anidada (`FiltroAnidadoEstatico`).
3. **Enfoque C:** Mediante una clase interna local declarada dentro del método.
4. **Enfoque D:** Mediante una **clase anónima** (`new FileFilter() { ... }`).
5. **Enfoque E:** Mediante una **expresión lambda** directa (`f -> f.getName().endsWith(...)`).

---

### Ejercicio 8: Sistema Bancario Abstracto (`CuentaBancaria`, `CuentaCorriente` y `CuentaAhorro`)

1. Diseña la clase abstracta `CuentaBancaria`:
   - Atributos: `protected final String iban`, `protected double saldo` y la constante estática `public static final double INTERES_ANUAL_BASICO = 0.025` (2,5 %).
   - Método privado `private void anadir(double cantidad)` que sume `cantidad` (positiva o negativa) al saldo, reutilizado internamente por los métodos públicos `ingresar(double)`, `retirar(double)` y `traspasar(CuentaBancaria destino, double cantidad)`.
   - Método abstracto `public abstract void calcularIntereses()`.
2. Crea las dos subclases concretas:
   - `CuentaCorriente`: al ejecutar `calcularIntereses()`, incrementa su saldo aplicando directamente el `INTERES_ANUAL_BASICO`.
   - `CuentaAhorro`: define la constante `public static final double SALDO_MINIMO = 3000.0`. Si su saldo actual es inferior a `SALDO_MINIMO`, aplica la **mitad** del interés básico (`INTERES_ANUAL_BASICO / 2.0`); si iguala o supera `SALDO_MINIMO`, aplica el **doble** del interés básico (`INTERES_ANUAL_BASICO * 2.0`).
3. Prueba ambas cuentas en un `ArrayList<CuentaBancaria>`, realiza ingresos, retiradas y traspasos, y aplica el cierre anual de intereses polimórficamente.

---

### Ejercicio 9: Sistema Solar Jerárquico (`Astro`, `Planeta` y `Satelite`)

1. Crea la clase abstracta `Astro` con los atributos `protected String nombre`, `protected double masa`, `protected double diametroMedio`, `protected double periodoRotacion`, `protected double periodoTraslacion` y `protected double distanciaMedia`, junto con el método abstracto `public abstract void muestra()`.
2. Crea la subclase `Planeta extends Astro`, que añade `private double distanciaAlSol`, `private boolean orbitaAlSol` y una lista `private ArrayList<Satelite> satelites`. Incluye un método `anadirSatelite(Satelite s)` y sobrescribe `muestra()` para imprimir los datos del planeta y la lista de sus lunas asociadas.
3. Crea la subclase `Satelite extends Astro`, que añade `private Planeta planetaQueOrbita` y sobrescribe `muestra()`.
4. En el programa principal, registra la Tierra (con la Luna) y Júpiter (con Ío y Europa) en un `ArrayList<Astro>` y muestra toda su información astronómica.

---

### Ejercicio 10: Flota Multimodal de Transporte con Validación Regex de Matrículas

Diseña la jerarquía de clases para una compañía logística de transporte multimodal:
1. Clase abstracta `Vehiculo` con atributos inmutables `protected final String matricula` y `protected final String modelo`, y el método obligatorio `public abstract void imprimir()`.
2. Tres subclases intermedias **instanciables** (pueden existir vehículos terrestres, acuáticos o aéreos genéricos) que validan el formato de su matrícula en el constructor mediante expresiones regulares (lanzando `IllegalArgumentException` si no cumple el patrón):
   - `Terrestre extends Vehiculo`: atributo `protected final int numRuedas`. Matrícula de **4 números y 3 letras** (`\\d{4}[A-Za-z]{3}`).
   - `Acuatico extends Vehiculo`: atributo `protected final double eslora`. Matrícula de **entre 3 y 10 letras** (`[A-Za-z]{3,10}`).
   - `Aereo extends Vehiculo`: atributo `protected final int numAsientos`. Matrícula de **4 letras y 6 números** (`[A-Za-z]{4}\\d{6}`).
3. Seis subclases especializadas finales:
   - De `Terrestre`: `Coche` (`boolean aireAcondicionado`) y `Moto` (`String color`).
   - De `Acuatico`: `Barco` (`boolean tieneMotor`) y `Submarino` (`double profundidadMaxima`).
   - De `Aereo`: `Avion` (`double tiempoMaximoVuelo`) y `Helicoptero` (`int numHelices`).
4. En el `main`, instancia vehículos de las 9 clases instanciables en un `ArrayList<Vehiculo>` y recórrelos invocando `imprimir()`.

---

### Ejercicio 11: Arquitectura con Clases Abstractas e Interfaces (`La Granja`)

Modela los elementos de una granja eligiendo justificadamente entre **clase concreta**, **clase abstracta** e **interfaz**:
1. Clase abstracta `AnimalGranja`: tiene el atributo `protected LocalDateTime nace` y el método abstracto `public abstract void naceDe()` (cada especie nace de forma distinta: parto o huevo).
2. Clase abstracta `AnimalDomestico extends AnimalGranja`: animales que conviven con el ser humano porque le son útiles.
3. Interfaces de capacidad:
   - `Volador` (`void vuela()`).
   - `Trabajador` (`void trabaja()`).
   - `MascotaHabladora` (`void acompanaEnCasa()`, `void habla()`).
4. Implementa las clases concretas ubicándolas en su lugar exacto de la jerarquía y dotándolas únicamente de las interfaces que les correspondan: `Vaca`, `Gallina`, `Perro`, `Gato`, `Tigre` (animal salvaje, no doméstico) y `MulaMecanica` (tractor agrícola: **no es un animal**, pero sí implementa `Trabajador`).

---

### Ejercicio 12: Funciones de Orden Superior y Composición de Expresiones Lambda

1. Implementa tres métodos genéricos propios que trabajen con las interfaces de `java.util.function`:
   - `public static <T> List<T> filtrar(List<T> lista, Predicate<T> predicado)`
   - `public static <T, R> List<R> transformar(List<T> lista, Function<T, R> funcion)`
   - `public static <T> void consumir(List<T> lista, Consumer<T> consumidor)`
2. Dado un `record Articulo(String nombre, String categoria, double precio, int stock)`:
   - Combina predicados con `.and()`, `.or()` y `.negate()` para filtrar artículos de `"Informática"` que cuesten más de `100 €` y tengan stock disponible.
   - Compón dos funciones `Function<Double, Double>` usando `.andThen()` y `.compose()`: una que aplique un descuento de `10 €` y otra que sume el `21 %` de IVA, comprobando la diferencia entre aplicar primero el descuento o primero el IVA.
   - Ordena la lista de artículos usando `Comparator.comparing(...)` por categoría ascendente y, en caso de empate, por precio descendente (`.thenComparing(comparing(Articulo::precio).reversed())`).
