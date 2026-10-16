---
title: "Solucionario 5.1: Ejercicios Iniciales Resueltos"
description: "Soluciones completas y comentadas de los 12 ejercicios iniciales de la Unidad 5 (POO Avanzada) en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 2
  badge:
    text: "Solucionario Inicial"
    variant: "success"
---

A continuación se presentan las soluciones íntegras, comentadas y ejecutables de los **12 ejercicios** del [Boletín 5.1 Inicial](/ProgramacionDAM/ud05/boletin-05-inicial/).

---

### Ejercicio 1: Composición Segura con `Punto2D` y `Rectangulo`

```java
public class Ej01RectanguloSeguro {

    public static class Punto2D implements Cloneable {
        private double x;
        private double y;

        public Punto2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Constructor de copia
        public Punto2D(Punto2D original) {
            if (original == null) {
                throw new IllegalArgumentException("El punto a copiar no puede ser null.");
            }
            this.x = original.x;
            this.y = original.y;
        }

        public double getX() { return this.x; }
        public double getY() { return this.y; }
        public void setX(double x) { this.x = x; }
        public void setY(double y) { this.y = y; }

        @Override
        public Punto2D clone() {
            try {
                return (Punto2D) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new IllegalStateException("Error al clonar Punto2D", e);
            }
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", this.x, this.y);
        }
    }

    public static class Rectangulo {
        private Punto2D vertice; // Esquina superior izquierda (composición fuerte)
        private double ancho;
        private double alto;

        // 1. Constructor por defecto: vértice (1.0, 1.0), ancho 1.0 y alto 1.0
        public Rectangulo() {
            this.vertice = new Punto2D(1.0, 1.0);
            this.ancho = 1.0;
            this.alto = 1.0;
        }

        // 2. Constructor con 4 coordenadas (x1, y1) y (x2, y2)
        public Rectangulo(double x1, double y1, double x2, double y2) {
            this.vertice = new Punto2D(x1, y1);
            this.ancho = Math.abs(x2 - x1);
            this.alto = Math.abs(y2 - y1);
        }

        // 3. Constructor con dos Punto2D (copia defensiva de punto1)
        public Rectangulo(Punto2D punto1, Punto2D punto2) {
            this.vertice = new Punto2D(punto1);
            this.ancho = Math.abs(punto2.getX() - punto1.getX());
            this.alto = Math.abs(punto2.getY() - punto1.getY());
        }

        // 4. Constructor con (ancho, alto) y esquina en (0.0, 0.0)
        public Rectangulo(double ancho, double alto) {
            this.vertice = new Punto2D(0.0, 0.0);
            this.ancho = Math.abs(ancho);
            this.alto = Math.abs(alto);
        }

        // 5. Constructor de copia profunda
        public Rectangulo(Rectangulo original) {
            if (original == null) {
                throw new IllegalArgumentException("El rectángulo a copiar no puede ser null.");
            }
            this.vertice = original.vertice.clone();
            this.ancho = original.ancho;
            this.alto = original.alto;
        }

        // Getter seguro: devuelve un clon del vértice para preservar 'private'
        public Punto2D getVertice() {
            return this.vertice.clone();
        }

        // Setter seguro: almacena una copia defensiva del punto recibido
        public void setVertice(Punto2D nuevoVertice) {
            if (nuevoVertice != null) {
                this.vertice = new Punto2D(nuevoVertice);
            }
        }

        public double getSuperficie() {
            return this.ancho * this.alto;
        }

        public double getPerimetro() {
            return 2.0 * (this.ancho + this.alto);
        }

        public boolean isCuadrado() {
            return Math.abs(this.ancho - this.alto) < 1e-9;
        }

        @Override
        public String toString() {
            return String.format("Rectangulo[vértice=%s, ancho=%.2f, alto=%.2f, área=%.2f, cuadrado=%b]",
                    this.vertice, this.ancho, this.alto, getSuperficie(), isCuadrado());
        }
    }

    public static void main(String[] args) {
        Punto2D pExterno = new Punto2D(5.0, 5.0);
        Rectangulo r1 = new Rectangulo(pExterno, new Punto2D(10.0, 10.0));

        // Intentamos alterar pExterno y el punto devuelto por getVertice():
        pExterno.setX(999.0);
        r1.getVertice().setY(-888.0);

        // Comprobamos que r1 permanece inmune gracias a las copias defensivas:
        System.out.println("Rectángulo blindado: " + r1);
    }
}
```

---

### Ejercicio 2: Refactorización de Herencia Errónea a Composición (`Motor` y `Coche`)

#### Justificación Técnica
Aplicando la prueba conceptual de relaciones entre clases:
* **¿Un `Coche` ES UN tipo concreto de `Motor`?** No. Un coche completo no es una especialización de un motor.
* **¿Un `Coche` TIENE UN `Motor`?** Sí. Existe una relación **todo-parte** (composición o agregación). Por tanto, extender `Motor` acopla indebidamente ambas clases y expone en la interfaz pública de `Coche` métodos internos de `Motor` que no deberían invocarse directamente.

```java
public class Ej02CocheComposicion {

    public static class Motor {
        private final String cilindrada;

        public Motor(String cilindrada) {
            this.cilindrada = cilindrada;
        }

        public void encender() {
            System.out.println("Motor (" + this.cilindrada + ") encendido y revolucionando.");
        }
    }

    // ✅ Diseño correcto: Coche TIENE UN Motor (Agregación / Composición)
    public static class Coche {
        private final String modelo;
        private final Motor motor;

        public Coche(String modelo, Motor motor) {
            this.modelo = modelo;
            this.motor = motor;
        }

        public void arrancar() {
            this.motor.encender(); // Delegación en el objeto componente
            System.out.println("Coche " + this.modelo + " en marcha.");
        }
    }

    public static void main(String[] args) {
        Motor motorHibrido = new Motor("1.8L Híbrido");
        Coche miCoche = new Coche("Toyota Corolla", motorHibrido);
        miCoche.arrancar();
    }
}
```

---

### Ejercicio 3: Relación Todo-Parte en la Gestión de `Empresa` y `Empleado`

```java
import java.util.ArrayList;

public class Ej03EmpresaEmpleados {

    public static class Empleado {
        private final String nombre;
        private final String dni;
        private double sueldoBrutoMensual;
        private int edad;
        private String telefono;
        private String direccion;

        public Empleado(String nombre, String dni, double sueldoBrutoMensual) {
            this(nombre, dni, sueldoBrutoMensual, 0, "Sin teléfono", "Sin dirección");
        }

        public Empleado(String nombre, String dni, double sueldoBrutoMensual,
                        int edad, String telefono, String direccion) {
            this.nombre = nombre;
            this.dni = dni;
            this.sueldoBrutoMensual = Math.max(0.0, sueldoBrutoMensual);
            this.edad = edad;
            this.telefono = telefono;
            this.direccion = direccion;
        }

        public String getNombre() { return this.nombre; }
        public String getDni() { return this.dni; }
        public double getSueldoBrutoMensual() { return this.sueldoBrutoMensual; }

        public double calcularPorcentajeIRPF() {
            double brutoAnual = this.sueldoBrutoMensual * 12.0;
            if (brutoAnual < 12_000.0) return 20.0;
            if (brutoAnual <= 25_000.0) return 30.0;
            return 40.0;
        }

        public double calcularSueldoNetoMensual() {
            double irpf = calcularPorcentajeIRPF();
            return this.sueldoBrutoMensual * (1.0 - irpf / 100.0);
        }

        public void imprimir() {
            System.out.printf("Empleado[%s | DNI: %s | Bruto: %.2f € | IRPF: %.0f%% | Neto: %.2f €]%n",
                    this.nombre, this.dni, this.sueldoBrutoMensual,
                    calcularPorcentajeIRPF(), calcularSueldoNetoMensual());
        }
    }

    public static class Empresa {
        private final String nombre;
        private final String cif;
        private String telefono;
        private String direccion;
        private final ArrayList<Empleado> empleados;

        public Empresa(String nombre, String cif, String telefono, String direccion) {
            this.nombre = nombre;
            this.cif = cif;
            this.telefono = telefono;
            this.direccion = direccion;
            this.empleados = new ArrayList<>();
        }

        public void anadirEmpleado(Empleado e) {
            if (e != null) this.empleados.add(e);
        }

        public boolean eliminarEmpleado(Empleado e) {
            return this.empleados.remove(e);
        }

        public void mostrarEmpleados() {
            System.out.println("=== Plantilla de " + this.nombre + " (" + this.cif + ") ===");
            for (Empleado e : this.empleados) {
                e.imprimir();
            }
        }

        public void mostrarResumenSalarial() {
            for (Empleado e : this.empleados) {
                System.out.printf(" - DNI: %s -> Bruto: %.2f € | Neto: %.2f €%n",
                        e.getDni(), e.getSueldoBrutoMensual(), e.calcularSueldoNetoMensual());
            }
        }

        public double calcularTotalSueldosBrutos() {
            double total = 0.0;
            for (Empleado e : this.empleados) {
                total += e.getSueldoBrutoMensual();
            }
            return total;
        }

        public double calcularTotalSueldosNetos() {
            double total = 0.0;
            for (Empleado e : this.empleados) {
                total += e.calcularSueldoNetoMensual();
            }
            return total;
        }
    }

    public static void main(String[] args) {
        Empresa emp = new Empresa("TechLevante S.L.", "B46123456", "963001122", "Av. del Puerto 40");
        emp.anadirEmpleado(new Empleado("Elena Gómez", "12345678Z", 950.0));   // 11.400€/año -> 20%
        emp.anadirEmpleado(new Empleado("Marcos Sanz", "87654321X", 1600.0));  // 19.200€/año -> 30%
        emp.anadirEmpleado(new Empleado("Lucía Orts", "11223344B", 2800.0));   // 33.600€/año -> 40%

        emp.mostrarEmpleados();
        System.out.printf("Masa salarial bruta mensual: %.2f €%n", emp.calcularTotalSueldosBrutos());
        System.out.printf("Masa salarial neta mensual:  %.2f €%n", emp.calcularTotalSueldosNetos());
    }
}
```

---

### Ejercicio 4: Auditoría de Seguridad en Composición (`Alumno` y `Evaluacion`)

```java
public class Ej04AlumnoEvaluacionSegura {

    public static class Evaluacion implements Cloneable {
        private int eval1, eval2, eval3;

        public Evaluacion(int eval1, int eval2, int eval3) {
            this.eval1 = eval1;
            this.eval2 = eval2;
            this.eval3 = eval3;
        }

        // Constructor de copia
        public Evaluacion(Evaluacion original) {
            if (original == null) {
                throw new IllegalArgumentException("La evaluación a copiar no puede ser null.");
            }
            this.eval1 = original.eval1;
            this.eval2 = original.eval2;
            this.eval3 = original.eval3;
        }

        @Override
        public Evaluacion clone() {
            try {
                return (Evaluacion) super.clone();
            } catch (CloneNotSupportedException ex) {
                throw new IllegalStateException("Error al clonar Evaluacion", ex);
            }
        }

        public double calcularMedia() {
            return (this.eval1 + this.eval2 + this.eval3) / 3.0;
        }

        public void setEval1(int eval1) { this.eval1 = eval1; }

        @Override
        public String toString() {
            return String.format("[%d, %d, %d]", this.eval1, this.eval2, this.eval3);
        }
    }

    public static class Alumno {
        private String nombre;
        private Evaluacion e; // Parte compuesta protegida

        public Alumno(String nombre, Evaluacion e) {
            this.nombre = nombre;
            this.e = (e != null) ? new Evaluacion(e) : new Evaluacion(0, 0, 0);
        }

        // Devuelve copia defensiva con clone()
        public Evaluacion getEvaluacion() {
            return this.e.clone();
        }

        // Almacena copia defensiva con el constructor de copia
        public void setEvaluacion(Evaluacion e) {
            if (e != null) {
                this.e = new Evaluacion(e);
            }
        }

        @Override
        public String toString() {
            return String.format("Alumno: %s | Notas: %s (Media: %.2f)",
                    this.nombre, this.e, this.e.calcularMedia());
        }
    }

    public static void main(String[] args) {
        Evaluacion notasExternas = new Evaluacion(7, 8, 9);
        Alumno al = new Alumno("Pau Ribes", notasExternas);

        // Intentamos sabotear las notas desde fuera:
        notasExternas.setEval1(0);
        al.getEvaluacion().setEval1(1);

        // Las notas del alumno siguen intactas [7, 8, 9]:
        System.out.println(al);
    }
}
```

---

### Ejercicio 5: Jerarquía `Persona`, `Alumno` y `Profesor` con `super(...)`

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ej05JerarquiaPersona {

    public static class Persona {
        protected String nombre;
        protected LocalDate nace;

        public Persona(String nombre, LocalDate nace) {
            this.nombre = nombre;
            this.nace = nace;
        }

        public String getNombre() { return this.nombre; }

        public void mostrar() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.printf("Nombre: %s | Nacimiento: %s%n",
                    getNombre(), this.nace.format(fmt));
        }
    }

    public static class Alumno extends Persona {
        protected String grupo;
        protected double notaMedia;

        public Alumno(String nombre, LocalDate nace, String grupo, double notaMedia) {
            super(nombre, nace);
            this.grupo = grupo;
            this.notaMedia = notaMedia;
        }

        @Override
        public String getNombre() {
            return "Alumno: " + this.nombre;
        }

        @Override
        public void mostrar() {
            super.mostrar();
            System.out.printf("  -> Grupo: %s | Nota media: %.2f%n", this.grupo, this.notaMedia);
        }
    }

    public static class Profesor extends Persona {
        protected String especialidad;
        protected double salario;

        {
            System.out.println("[Bloque inicializador de instancia en Profesor]");
        }

        public Profesor(String nombre, LocalDate nace, String especialidad, double salario) {
            super(nombre, nace);
            this.especialidad = especialidad;
            this.salario = salario;
        }

        // Constructor que calcula la fecha actual con un método estático antes de super(...)
        public Profesor(String nombre, String especialidad, double salario) {
            super(nombre, obtenerFechaActual());
            System.out.println("[Cuerpo del constructor de Profesor]");
            this.especialidad = especialidad;
            this.salario = salario;
        }

        private static LocalDate obtenerFechaActual() {
            System.out.println("[Método estático obtenerFechaActual() ejecutado antes de super]");
            return LocalDate.now();
        }

        @Override
        public String getNombre() {
            return "Profesor: " + this.nombre;
        }

        @Override
        public void mostrar() {
            super.mostrar();
            System.out.printf("  -> Especialidad: %s | Salario: %.2f €%n", this.especialidad, this.salario);
        }
    }

    public static void main(String[] args) {
        Alumno a = new Alumno("Sara Vidal", LocalDate.of(2004, 5, 14), "1DAM", 8.75);
        Profesor p = new Profesor("José Rosa", "Informática", 2450.00);

        a.mostrar();
        p.mostrar();
    }
}
```

---

### Ejercicio 6: Ampliación de Comportamiento con `super` (`Mascota` y `Perro`)

```java
public class Ej06MascotaSuper {

    public static class Mascota {
        protected int numPatas;
        protected String nombre;
        protected String voz;
        protected double peso;

        public Mascota(int numPatas, String nombre, String voz, double peso) {
            this.numPatas = numPatas;
            this.nombre = nombre;
            this.voz = voz;
            this.peso = Math.max(0.1, peso);
        }

        public double getPeso() { return this.peso; }

        public void come(double cantidad) {
            if (cantidad > 0) {
                this.peso += cantidad;
            }
        }

        public boolean juega(double minutos) {
            double perdida = minutos / 2.0;
            if (this.peso - perdida > 0) {
                this.peso -= perdida;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("%s %d %s %.2fkg", this.nombre, this.numPatas, this.voz, this.peso);
        }
    }

    public static class Perro extends Mascota {
        private String color;

        public Perro(String nombre, double peso, String color) {
            super(4, nombre, "Guau", peso);
            this.color = color;
        }

        @Override
        public boolean juega(double minutos) {
            // Intentamos jugar normalmente con el método heredado de Mascota:
            if (!super.juega(minutos)) {
                System.out.println(this.nombre + " no tiene energía suficiente: come antes de jugar.");
                super.come(minutos - 1.0);
                return super.juega(minutos / 2.0);
            }
            return true;
        }

        @Override
        public String toString() {
            return super.toString() + " (" + this.color + ")";
        }
    }

    public static void main(String[] args) {
        Perro toby = new Perro("Toby", 3.0, "Canela");
        System.out.println("Estado inicial: " + toby);

        // Si juega 8 minutos perdería 4 kg (3 - 4 <= 0 -> primero come 7 kg y luego juega 4 min)
        toby.juega(8.0);
        System.out.println("Tras jugar 8 min: " + toby);
    }
}
```

---

### Ejercicio 7: Clase Abstracta `Figura2D` y Polimorfismo Geométrico

```java
public class Ej07Figura2DAbstracta {

    public static abstract class Figura2D {
        protected int x, y;

        public Figura2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public abstract double perimetro();
        public abstract double area();
    }

    public static class Rectangulo2D extends Figura2D {
        private double ancho, alto;

        public Rectangulo2D(int x, int y, double ancho, double alto) {
            super(x, y);
            this.ancho = ancho;
            this.alto = alto;
        }

        @Override public double perimetro() { return 2.0 * (this.ancho + this.alto); }
        @Override public double area() { return this.ancho * this.alto; }
    }

    public static class TrianguloEquilatero2D extends Figura2D {
        private double lado;

        public TrianguloEquilatero2D(int x, int y, double lado) {
            super(x, y);
            this.lado = lado;
        }

        @Override public double perimetro() { return 3.0 * this.lado; }
        @Override public double area() { return (Math.sqrt(3.0) / 4.0) * this.lado * this.lado; }
    }

    public static class Circulo2D extends Figura2D {
        private double radio;

        public Circulo2D(int x, int y, double radio) {
            super(x, y);
            this.radio = radio;
        }

        @Override public double perimetro() { return 2.0 * Math.PI * this.radio; }
        @Override public double area() { return Math.PI * this.radio * this.radio; }
    }

    public static void main(String[] args) {
        Figura2D[] figuras = {
            new Rectangulo2D(0, 0, 4.0, 5.0),
            new TrianguloEquilatero2D(2, 3, 6.0),
            new Circulo2D(5, 5, 3.0)
        };

        for (Figura2D f : figuras) {
            System.out.printf("%-24s -> Perímetro: %6.2f | Área: %6.2f%n",
                    f.getClass().getSimpleName(), f.perimetro(), f.area());
        }
    }
}
```

---

### Ejercicio 8: Interfaz `iFigura2D` y Escalado en `ArrayList`

```java
import java.util.ArrayList;

public class Ej08ProgramaFiguras {

    public interface iFigura2D {
        double perimetro();
        double area();
        void escalar(double escala);
        void imprimir();
    }

    public static class Cuadrado implements iFigura2D {
        private double lado;

        public Cuadrado(double lado) { this.lado = lado; }
        @Override public double perimetro() { return 4.0 * this.lado; }
        @Override public double area() { return this.lado * this.lado; }
        @Override public void escalar(double escala) { if (escala > 0) this.lado *= escala; }
        @Override public void imprimir() {
            System.out.printf("Cuadrado[lado=%.2f | P=%.2f | A=%.2f]%n", this.lado, perimetro(), area());
        }
    }

    public static class Rectangulo implements iFigura2D {
        private double ancho, alto;

        public Rectangulo(double ancho, double alto) { this.ancho = ancho; this.alto = alto; }
        @Override public double perimetro() { return 2.0 * (this.ancho + this.alto); }
        @Override public double area() { return this.ancho * this.alto; }
        @Override public void escalar(double escala) {
            if (escala > 0) { this.ancho *= escala; this.alto *= escala; }
        }
        @Override public void imprimir() {
            System.out.printf("Rectángulo[%.2fx%.2f | P=%.2f | A=%.2f]%n",
                    this.ancho, this.alto, perimetro(), area());
        }
    }

    public static class Triangulo implements iFigura2D {
        private double ancho, alto; // Triángulo rectángulo de base 'ancho' y altura 'alto'

        public Triangulo(double ancho, double alto) { this.ancho = ancho; this.alto = alto; }
        @Override public double perimetro() {
            double hipotenusa = Math.hypot(this.ancho, this.alto);
            return this.ancho + this.alto + hipotenusa;
        }
        @Override public double area() { return (this.ancho * this.alto) / 2.0; }
        @Override public void escalar(double escala) {
            if (escala > 0) { this.ancho *= escala; this.alto *= escala; }
        }
        @Override public void imprimir() {
            System.out.printf("Triángulo[base=%.2f, alto=%.2f | P=%.2f | A=%.2f]%n",
                    this.ancho, this.alto, perimetro(), area());
        }
    }

    public static class Circulo implements iFigura2D {
        private double radio;

        public Circulo(double radio) { this.radio = radio; }
        @Override public double perimetro() { return 2.0 * Math.PI * this.radio; }
        @Override public double area() { return Math.PI * this.radio * this.radio; }
        @Override public void escalar(double escala) { if (escala > 0) this.radio *= escala; }
        @Override public void imprimir() {
            System.out.printf("Círculo[radio=%.2f | P=%.2f | A=%.2f]%n", this.radio, perimetro(), area());
        }
    }

    public static void main(String[] args) {
        ArrayList<iFigura2D> figuras = new ArrayList<>();
        figuras.add(new Cuadrado(4.0));
        figuras.add(new Rectangulo(5.0, 3.0));
        figuras.add(new Triangulo(3.0, 4.0));
        figuras.add(new Circulo(2.0));

        System.out.println("--- 1. FIGURAS ORIGINALES ---");
        for (iFigura2D f : figuras) f.imprimir();

        System.out.println("\n--- 2. TRAS ESCALAR (x2.0) ---");
        for (iFigura2D f : figuras) { f.escalar(2.0); f.imprimir(); }

        System.out.println("\n--- 3. TRAS ESCALAR (x0.1) ---");
        for (iFigura2D f : figuras) { f.escalar(0.1); f.imprimir(); }
    }
}
```

---

### Ejercicio 9: Conversión de Objetos (`ClassCastException` e `instanceof` en `Cuento`)

```java
public class Cuento {

    interface Besable {
        void besar();
    }

    static class Principe implements Besable {
        @Override
        public void besar() {
            System.out.println("El príncipe sonríe con elegancia.");
        }
    }

    static class PrincipeAzul extends Principe { }

    static class Sapo implements Besable {
        @Override
        public void besar() {
            System.out.println("¡Croac! Sigo siendo un sapo de charca.");
        }
    }

    public static void main(String[] args) {
        Besable personaje = new Sapo();

        // 1. Demostración de ClassCastException al hacer downcasting inválido
        try {
            Principe pa = (PrincipeAzul) personaje;
            pa.besar();
        } catch (ClassCastException ex) {
            System.out.println("⚠️ Excepción capturada: No se puede convertir un Sapo en Príncipe Azul -> "
                    + ex.getMessage());
        }

        // 2. Comprobación segura previa con instanceof y Pattern Matching (Java 25 LTS)
        if (personaje instanceof PrincipeAzul principeConfirmado) {
            principeConfirmado.besar();
        } else {
            System.out.println("✅ Verificación instanceof: 'personaje' no es un PrincipeAzul, evitamos el casting.");
            personaje.besar();
        }
    }
}
```

---

### Ejercicio 10: Gestión Dinámica de `ArrayList<Producto>` con `Iterator`

```java
import java.util.ArrayList;
import java.util.Iterator;

public class Ej10ArrayListProductos {

    public static class Producto {
        private String nombre;
        private int cantidad;

        public Producto(String nombre, int cantidad) {
            this.nombre = nombre;
            this.cantidad = cantidad;
        }

        public String getNombre() { return this.nombre; }
        public int getCantidad() { return this.cantidad; }

        @Override
        public String toString() {
            return String.format("%s (%d uds)", this.nombre, this.cantidad);
        }
    }

    private static void mostrarConIterator(ArrayList<Producto> lista, String titulo) {
        System.out.println("=== " + titulo + " ===");
        Iterator<Producto> it = lista.iterator();
        while (it.hasNext()) {
            System.out.println(" -> " + it.next());
        }
    }

    public static void main(String[] args) {
        // 1, 2 y 3. Crear 5 instancias y añadirlas al ArrayList
        ArrayList<Producto> inventario = new ArrayList<>();
        inventario.add(new Producto("Teclado Mecánico", 15));
        inventario.add(new Producto("Ratón Óptico", 30));
        inventario.add(new Producto("Monitor 27 pulgadas", 8));
        inventario.add(new Producto("Auriculares USB", 20));
        inventario.add(new Producto("Alfombrilla XL", 50));

        // 4. Visualizar con Iterator
        mostrarConIterator(inventario, "Lista inicial (5 productos)");

        // 5. Eliminar 2 elementos (el primero por índice y "Monitor 27 pulgadas" con Iterator)
        inventario.remove(0);
        Iterator<Producto> it = inventario.iterator();
        while (it.hasNext()) {
            if (it.next().getNombre().startsWith("Monitor")) {
                it.remove();
            }
        }

        // 6. Insertar un nuevo producto en mitad de la lista
        inventario.add(inventario.size() / 2, new Producto("Webcam FullHD", 12));

        // 7. Visualizar de nuevo con Iterator
        mostrarConIterator(inventario, "Tras borrar 2 e insertar en medio");

        // 8. Eliminar todos los valores
        inventario.clear();
        System.out.println("¿Lista vacía tras clear()? " + inventario.isEmpty());
    }
}
```

---

### Ejercicio 11: Auditoría de Interfaces Funcionales y Expresiones Lambda

#### Análisis Razonado
* **Bloque A:**
  - `Adder` **SÍ es funcional:** declara un único método abstracto `int add(int, int)`.
  - `SmartAdder` **NO es funcional:** hereda `add(int, int)` de `Adder` y añade `add(double, double)` (tiene 2 métodos abstractos sobrecargados).
  - `Nada` **NO es funcional:** tiene 0 métodos abstractos (necesita exactamente 1).
* **Bloque B:**
  - `I1` **SÍ es funcional:** su método `cabeMas()` es `default` (ya tiene implementación), por lo que solo queda **1 método abstracto** (`void carga(int kg)`).
  - `I2` **SÍ es funcional:** el método `String toString()` coincide exactamente con la firma de un método público de `java.lang.Object` (todas las clases ya lo tienen implementado por herencia de `Object`), por lo que solo queda **1 método abstracto real** (`int maximo(int n1, int n2)`).

```java
public class Ej11InterfacesFuncionales {

    @FunctionalInterface
    interface I1 {
        void carga(int kg);
        default boolean cabeMas() { return true; }
    }

    @FunctionalInterface
    interface I2 {
        int maximo(int n1, int n2);
        @Override String toString();
    }

    public static void main(String[] args) {
        // Expresión lambda para I1 (Descriptor: int -> void)
        I1 camion = kg -> System.out.println("Cargando " + kg + " kg en la bodega.");
        camion.carga(1500);
        System.out.println("¿Cabe más carga? " + camion.cabeMas());

        // Expresión lambda para I2 (Descriptor: (int, int) -> int)
        I2 comparadorMax = (a, b) -> (a >= b) ? a : b;
        System.out.println("Máximo entre 42 y 89: " + comparadorMax.maximo(42, 89));
    }
}
```

---

### Ejercicio 12: Transformación de Lambdas a Referencias a Métodos y Constructores (`::`)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

public class Ej12ReferenciasMetodos {

    public static class Fruta {
        private String color;
        private int peso;

        public Fruta() { this("Verde", 100); }
        public Fruta(int peso) { this("Verde", peso); }
        public Fruta(String color, int peso) {
            this.color = color;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return String.format("Fruta[%s, %dg]", this.color, this.peso);
        }
    }

    public static void main(String[] args) {
        // 1. Referencia a método estático (Clase::metodoEstatico)
        Function<String, Integer> strToInt = Integer::parseInt;
        System.out.println("1. String a Integer: " + (strToInt.apply("2025") + 1));

        // 2. Referencia a método de instancia de un tipo arbitrario (Clase::metodoInstancia)
        BiPredicate<List<String>, String> contiene = List::contains;
        List<String> lenguajes = List.of("Java", "Kotlin", "Rust");
        System.out.println("2. ¿Contiene 'Java'? " + contiene.test(lenguajes, "Java"));

        // 3. Ordenación con referencia a método String::compareToIgnoreCase
        List<String> letras = new ArrayList<>(Arrays.asList("b", "A", "d", "C"));
        letras.sort(String::compareToIgnoreCase);
        System.out.println("3. Ordenadas sin importar mayúsculas: " + letras);

        // 4. Referencias a constructores (Fruta::new) con 0, 1 y 2 parámetros
        Supplier<Fruta> ctorVacio = Fruta::new;
        Function<Integer, Fruta> ctorPeso = Fruta::new;
        BiFunction<String, Integer, Fruta> ctorCompleto = Fruta::new;

        System.out.println("4a. Supplier:   " + ctorVacio.get());
        System.out.println("4b. Function:   " + ctorPeso.apply(220));
        System.out.println("4c. BiFunction: " + ctorCompleto.apply("Roja", 175));
    }
}
```
