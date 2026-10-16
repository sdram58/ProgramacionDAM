---
title: "Boletín 5.1: Ejercicios Iniciales — Composición, Herencia, Interfaces y Lambdas Básicas"
description: "12 ejercicios propuestos paso a paso sobre composición segura, constructores de copia, herencia con super, clases abstractas, interfaces, polimorfismo y expresiones lambda en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 1
  badge:
    text: "Inicial"
    variant: "tip"
---

En este primer boletín práctico de la **Unidad 5: Programación orientada a objetos avanzada** pondrás en práctica los pilares del diseño multiclase: la protección del encapsulamiento en objetos compuestos, la herencia con `extends` y `super`, las clases abstractas, las interfaces, el polimorfismo con `instanceof` y tus primeras expresiones lambda.

---

### Ejercicio 1: Composición Segura con `Punto2D` y `Rectangulo`

1. Define la clase mutable `Punto2D` con atributos privados `double x` e `y`, constructor parametrizado, **constructor de copia** (`public Punto2D(Punto2D original)`), getters, setters, sobrescritura de `toString()` con formato `"(x, y)"` e implementación de la interfaz `Cloneable` redefiniendo `public Punto2D clone()`.
2. Define la clase `Rectangulo` usando **composición fuerte** de un objeto `Punto2D vertice` (que representa su esquina superior izquierda) y dos atributos `double ancho` y `alto`.
3. Protege los modificadores de acceso: tanto `getVertice()` como `setVertice(Punto2D v)` deben trabajar con **copias defensivas** (usando el constructor de copia o `clone()`) para que modificar un `Punto2D` desde el exterior jamás altere el interior del `Rectangulo`.
4. Implementa **cinco constructores** en `Rectangulo`:
   - Constructor por defecto: vértice en `(1.0, 1.0)` con anchura `1.0` y altura `1.0`.
   - Constructor con 4 parámetros `(double x1, double y1, double x2, double y2)`: recibe la esquina superior izquierda `(x1, y1)` y la esquina inferior derecha `(x2, y2)` y calcula su anchura y altura.
   - Constructor con 2 puntos `(Punto2D p1, Punto2D p2)`: `p1` es la esquina superior izquierda y `p2` la inferior derecha (recuerda hacer copia defensiva de `p1`).
   - Constructor con 2 dimensiones `(double ancho, double alto)` cuyo vértice se ubique en `(0.0, 0.0)`.
   - Constructor de copia `public Rectangulo(Rectangulo original)`.
5. Añade los métodos `getSuperficie()`, `getPerimetro()` e `isCuadrado()`.

---

### Ejercicio 2: Refactorización de Herencia Errónea a Composición (`Motor` y `Coche`)

Un programador principiante ha escrito el siguiente código para reutilizar el método `encender()` de la clase `Motor` dentro de la clase `Coche`:

```java
public class Motor {
    public void encender() {
        System.out.println("Motor encendido");
    }
}

public class Coche extends Motor {
    public void arrancar() {
        encender();
        System.out.println("Coche en marcha");
    }
}
```

1. Explica razonadamente por qué este diseño viola los principios de la POO (aplica el test *ES-UN* frente a *TIENE-UN*).
2. Refactoriza el código sustituyendo la herencia por una relación de **agregación/composición** donde `Coche` contenga un atributo privado `Motor`, delegando en él la operación de encendido dentro de `arrancar()`.

---

### Ejercicio 3: Relación Todo-Parte en la Gestión de `Empresa` y `Empleado`

Desarrolla una aplicación que gestione los datos de una empresa y su plantilla de trabajadores:
1. **Clase `Empleado`:**
   - Atributos inmutables (`final`): `nombre` (`String`) y `dni` (`String`).
   - Atributos mutables: `sueldoBrutoMensual` (`double`), `edad` (`int`), `telefono` (`String`) y `direccion` (`String`).
   - Constructores: uno con los 3 datos obligatorios (`nombre`, `dni`, `sueldoBrutoMensual`) y otro completo.
   - Método `public double calcularSueldoNetoMensual()`: descuenta del sueldo bruto mensual el porcentaje de IRPF correspondiente a su sueldo bruto anual (`sueldoBrutoMensual × 12`):
     - Bruto anual `< 12.000 €` → **20 %** de IRPF.
     - Bruto anual entre `12.000 €` y `25.000 €` (incluidos) → **30 %** de IRPF.
     - Bruto anual `> 25.000 €` → **40 %** de IRPF.
2. **Clase `Empresa`:**
   - Atributos: `nombre` (`final String`), `cif` (`final String`), `telefono`, `direccion` y una colección dinámica `ArrayList<Empleado> empleados` que nace vacía al crear la empresa.
   - Métodos para añadir y eliminar empleados, listar toda la plantilla, mostrar DNI con sueldo bruto y neto de cada trabajador, y calcular la suma total de sueldos brutos y la suma total de sueldos netos de la empresa.

---

### Ejercicio 4: Auditoría de Seguridad en Composición (`Alumno` y `Evaluacion`)

Observa las siguientes clases de un programa académico:

```java
class Evaluacion {
    private int eval1, eval2, eval3;
    // Constructores, getters y setters...
}

class Alumno {
    private String nombre;
    private Evaluacion e;

    public Alumno(String nombre, Evaluacion e) {
        this.nombre = nombre;
        this.e = e;
    }

    public Evaluacion getEvaluacion() { return e; }
    public void setEvaluacion(Evaluacion e) { this.e = e; }
}
```

1. ¿Qué ocurre si desde el método `main` alguien ejecuta `alumno.getEvaluacion().setEval1(10)` o modifica el objeto `Evaluacion` que pasó al constructor? ¿Se está respetando la composición fuerte?
2.Reescribe ambas clases implementando un **constructor de copia** y el método **`clone()`** en `Evaluacion`, y modifica el constructor, getter y setter de `Alumno` para blindar el encapsulamiento.

---

### Ejercicio 5: Jerarquía `Persona`, `Alumno` y `Profesor` con `super(...)`

1. Crea la superclase `Persona` con atributos `protected String nombre` y `protected LocalDate nace`, constructor parametrizado, getters, setters y un método `public void mostrar()` que imprima sus datos personales formateados.
2. Crea las subclases `Alumno` (añade `String grupo` y `double notaMedia`) y `Profesor` (añade `String especialidad` y `double salario`).
3. Sobrescribe con `@Override` el método `getNombre()` en ambas subclases para que devuelva `"Alumno: " + nombre` y `"Profesor: " + nombre` respectivamente.
4. Sobrescribe el método `mostrar()` en `Alumno` y en `Profesor` invocando primero a **`super.mostrar()`** para reutilizar el código del padre antes de imprimir los atributos específicos.
5. Añade en `Profesor` un constructor adicional `public Profesor(String nombre, String especialidad, double salario)` que asigne automáticamente la fecha actual (`LocalDate.now()`) al atributo `nace` del padre invocando a un método privado y estático `private static LocalDate fechaHoy()` dentro de los paréntesis de `super(nombre, fechaHoy())` (imprimiendo mensajes por consola para observar el orden exacto de ejecución).

---

### Ejercicio 6: Ampliación de Comportamiento con `super` (`Mascota` y `Perro`)

1. Diseña la clase `Mascota` con los atributos `protected int numPatas`, `protected String nombre`, `protected String voz` y `protected double peso`:
   - Constructor con todos los atributos, getters y setters (excepto `peso`, que solo tendrá getter).
   - Sobrescribe `toString()` para devolver `"nombre numPatas voz peso"`.
   - Método `public void come(double cantidad)`: incrementa el peso en `cantidad` (si es positiva).
   - Método `public boolean juega(double minutos)`: reduce el peso de la mascota en `minutos / 2.0` **únicamente si tras el ejercicio el peso sigue siendo estrictamente mayor que `0`**, devolviendo `true`. Si jugar ese tiempo dejase su peso en `0` o negativo, no modifica el peso y devuelve `false`.
2. Crea la subclase `Perro` que extienda a `Mascota`:
   - Añade el atributo `private String color` e invoca a `super(...)` en su constructor.
   - Sobrescribe `juega(double minutos)` apoyándote en `super`: si al intentar jugar con `super.juega(minutos)` devuelve `false` (porque no tiene peso suficiente), el perro primero comerá una cantidad igual a `minutos - 1` y acto seguido volverá a jugar durante `minutos / 2.0` invocando a `super`.

---

### Ejercicio 7: Clase Abstracta `Figura2D` y Polimorfismo Geométrico

1. Crea la clase abstracta `Figura2D` con atributos `protected int x, y` (posición en el plano), constructor, getters/setters y dos métodos abstractos: `public abstract double perimetro()` y `public abstract double area()`.
2. Crea tres subclases concretas que extiendan a `Figura2D`:
   - `Rectangulo2D` (con `ancho` y `alto`).
   - `TrianguloEquilatero2D` (con `lado`).
   - `Circulo2D` (con `radio`).
3. En una clase principal, almacena instancias de las tres subclases en un array polimórfico `Figura2D[]` y recórrelo mostrando el área y perímetro de cada figura mediante ligadura dinámica.

---

### Ejercicio 8: Interfaz `iFigura2D` y Escalado en `ArrayList`

1. Declara la interfaz `iFigura2D` con los métodos:
   - `double perimetro()`
   - `double area()`
   - `void escalar(double escala)`: multiplica las dimensiones de la figura por `escala` (validando que `escala > 0`).
   - `void imprimir()`: muestra en una sola línea el tipo de figura, sus dimensiones, su perímetro y su área.
2. Implementa la interfaz en cuatro clases: `Cuadrado`, `Rectangulo`, `Triangulo` (de base `ancho` y altura `alto`, isósceles o rectángulo) y `Circulo`.
3. En `ProgramaFiguras`:
   - Crea un `ArrayList<iFigura2D>` y añade una figura de cada tipo.
   - Imprime todas las figuras.
   - Escala todas las figuras con `escala = 2.0` y vuelve a imprimirlas.
   - Escala todas las figuras con `escala = 0.1` y vuelve a imprimirlas.

---

### Ejercicio 9: Conversión de Objetos (`ClassCastException` e `instanceof` en `Cuento`)

1. En un mismo archivo `Cuento.java`, declara la interfaz `Besable` con el método `void besar()`.
2. Declara las clases `Principe` y `PrincipeAzul extends Principe` (ambas implementan `Besable`), y otra clase independiente `Sapo` que también implemente `Besable`.
3. En el método `main`:
   - Instancia un objeto `Sapo s = new Sapo();`.
   - Dentro de un bloque `try-catch`, intenta realizar un casting forzado de `s` (referenciado como `Besable`) a `(PrincipeAzul)` asignándolo a una variable `Principe`. Captura y muestra la excepción `ClassCastException` que se produce en tiempo de ejecución.
   - A continuación, escribe la comprobación segura utilizando el operador **`instanceof`** (con *Pattern Matching*) para verificar antes del casting si el objeto `Besable` es realmente un `PrincipeAzul` o un `Sapo`.

---

### Ejercicio 10: Gestión Dinámica de `ArrayList<Producto>` con `Iterator`

1. Define la clase `Producto` con los atributos `private String nombre` y `private int cantidad`, constructor parametrizado, getters, setters y `toString()`.
2. En el método `main`, ejecuta secuencialmente las siguientes 8 operaciones:
   1. Crea 5 instancias distintas de `Producto`.
   2. Crea un `ArrayList<Producto>`.
   3. Añade las 5 instancias al `ArrayList`.
   4. Visualiza el contenido completo del `ArrayList` recorriéndolo mediante un objeto **`Iterator<Producto>`**.
   5. Elimina dos elementos del `ArrayList` (uno por índice y otro usando `Iterator.remove()`).
   6. Inserta un nuevo `Producto` exactamente en la mitad de la lista (` add(indice, prod)`).
   7. Visualiza de nuevo el `ArrayList` con un `Iterator`.
   8. Vacía por completo la lista con `clear()` y comprueba `isEmpty()`.

---

### Ejercicio 11: Auditoría de Interfaces Funcionales y Expresiones Lambda

1. Razona cuáles de las siguientes interfaces del bloque A son **interfaces funcionales** válidas (es decir, compilarían si les añadimos `@FunctionalInterface`):
   ```java
   public interface Adder { int add(int a, int b); }
   public interface SmartAdder extends Adder { int add(double a, double b); }
   public interface Nada { }
   ```
2. Examina ahora las dos interfaces del bloque B:
   ```java
   interface I1 {
       void carga(int kg);
       default boolean cabeMas() { return true; }
   }

   interface I2 {
       int maximo(int n1, int n2);
       String toString();
   }
   ```
   Justifica por qué **ambas (`I1` e `I2`) sí son interfaces funcionales** a pesar de tener dos métodos declarados cada una, e impleméntalas en una clase ejecutable utilizando **expresiones lambda**.

---

### Ejercicio 12: Transformación de Lambdas a Referencias a Métodos y Constructores (`::`)

Escribe un programa en Java que demuestre la equivalencia entre expresiones lambda y **referencias a métodos/constructores (`::`)** para los siguientes casos:
1. Convertir una cadena a entero con `Function<String, Integer>`: de `(String s) -> Integer.parseInt(s)` a referencia a método estático.
2. Comprobar si una lista contiene un elemento con `BiPredicate<List<String>, String>`: de `(lista, elem) -> lista.contains(elem)` a referencia a método de instancia de un tipo arbitrario.
3. Ordenar una lista de cadenas ignorando mayúsculas/minúsculas con `List.sort`: de `(s1, s2) -> s1.compareToIgnoreCase(s2)` a `String::compareToIgnoreCase`.
4. Instanciar objetos `Fruta` mediante referencias a constructores (`Fruta::new`) usando:
   - `Supplier<Fruta>` (constructor vacío).
   - `Function<Integer, Fruta>` (constructor con peso).
   - `BiFunction<String, Integer, Fruta>` (constructor con color y peso).
