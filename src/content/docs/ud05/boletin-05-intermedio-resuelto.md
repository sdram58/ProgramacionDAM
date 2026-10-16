---
title: "Solucionario 5.2: Ejercicios Intermedios Resueltos"
description: "Soluciones completas y comentadas de los 12 ejercicios intermedios de la Unidad 5 (POO Avanzada) en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 4
  badge:
    text: "Solucionario Intermedio"
    variant: "success"
---

A continuación se presentan las soluciones íntegras, comentadas y ejecutables de los **12 ejercicios** del [Boletín 5.2 Intermedio](/ProgramacionDAM/ud05/boletin-05-intermedio/).

---

### Ejercicio 1: Agregación vs. Composición en Geometría (`Punto` y `Poligono`)

```java
import java.util.Arrays;

public class Ej01PoligonoAgregacionComposicion {

    public static class Punto {
        private double x;
        private double y;

        public Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Constructor de copia
        public Punto(Punto original) {
            this.x = original.x;
            this.y = original.y;
        }

        public double getX() { return this.x; }
        public double getY() { return this.y; }
        public void setX(double x) { this.x = x; }
        public void setY(double y) { this.y = y; }

        public void desplazar(double dx, double dy) {
            this.x += dx;
            this.y += dy;
        }

        public void escalar(double fx, double fy) {
            this.x *= fx;
            this.y *= fy;
        }

        public double distancia(Punto otro) {
            return Math.hypot(this.x - otro.x, this.y - otro.y);
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", this.x, this.y);
        }
    }

    public static class Poligono {
        private final Punto[] vertices;

        public Poligono(Punto[] puntos, boolean composicionFuerte) {
            if (puntos == null || puntos.length < 3) {
                throw new IllegalArgumentException("Un polígono necesita al menos 3 vértices.");
            }
            if (composicionFuerte) {
                // COMPOSICIÓN FUERTE: Copia profunda del array y de cada Punto
                this.vertices = new Punto[puntos.length];
                for (int i = 0; i < puntos.length; i++) {
                    this.vertices[i] = new Punto(puntos[i]);
                }
            } else {
                // AGREGACIÓN DÉBIL: Comparte las referencias externas
                this.vertices = puntos;
            }
        }

        public int numVertices() {
            return this.vertices.length;
        }

        public void trasladar(double dx, double dy) {
            for (Punto p : this.vertices) {
                p.desplazar(dx, dy);
            }
        }

        public void escalar(double fx, double fy) {
            for (Punto p : this.vertices) {
                p.escalar(fx, fy);
            }
        }

        public double perimetro() {
            double suma = 0.0;
            for (int i = 0; i < this.vertices.length; i++) {
                Punto actual = this.vertices[i];
                Punto siguiente = this.vertices[(i + 1) % this.vertices.length];
                suma += actual.distancia(siguiente);
            }
            return suma;
        }

        @Override
        public String toString() {
            return String.format("Polígono[%d vértices=%s | Perímetro=%.2f]",
                    numVertices(), Arrays.toString(this.vertices), perimetro());
        }
    }

    public static void main(String[] args) {
        Punto[] trianguloBase = {
            new Punto(0, 0),
            new Punto(3, 0),
            new Punto(0, 4)
        };

        Poligono polAgregado  = new Poligono(trianguloBase, false);
        Poligono polCompuesto = new Poligono(trianguloBase, true);

        System.out.println("Antes de alterar punto externo:");
        System.out.println("  Agregado:  " + polAgregado);
        System.out.println("  Compuesto: " + polCompuesto);

        // Alteramos el primer punto desde el exterior:
        trianguloBase[0].setX(100.0);

        System.out.println("\nTras modificar trianguloBase[0].setX(100):");
        System.out.println("  Agregado (afectado): " + polAgregado);
        System.out.println("  Compuesto (inmune):  " + polCompuesto);
    }
}
```

---

### Ejercicio 2: Jerarquía Abstracta de `Vehiculo`, `Velero`, `Bicicleta` y `CocheAsegurado`

```java
public class Ej02VehiculosAsegurables {

    public interface Asegurable {
        void setCobertura();
        double getCobertura();
    }

    public static abstract class Vehiculo {
        protected String potencia;
        protected int ruedas;
        protected double precio;

        public Vehiculo(String potencia, int ruedas, double precio) {
            this.potencia = potencia;
            this.ruedas = Math.max(0, ruedas);
            setPrecio(precio);
        }

        public String getPotencia() { return this.potencia; }
        public int getRuedas() { return this.ruedas; }
        public double getPrecio() { return this.precio; }

        public abstract void setPrecio(double precio);

        @Override
        public String toString() {
            return String.format("potencia='%s', ruedas=%d, precio=%.2f€",
                    this.potencia, this.ruedas, this.precio);
        }
    }

    public static class Velero extends Vehiculo {
        private static final double PRECIO_MAX = 100_000.00;
        private int eslora;

        public Velero(int eslora, double precio) {
            super("Viento (Vela)", 0, precio);
            this.eslora = eslora;
        }

        @Override
        public void setPrecio(double precio) {
            this.precio = Math.min(Math.max(0.0, precio), PRECIO_MAX);
        }

        @Override
        public String toString() {
            return "Velero[" + super.toString() + ", eslora=" + this.eslora + "m]";
        }
    }

    public static class Bicicleta extends Vehiculo {
        private static final double PRECIO_MAX = 3_999.99;

        public Bicicleta() {
            this("Una persona", 450.00);
        }

        public Bicicleta(String potencia, double precio) {
            super(potencia, 2, precio);
        }

        @Override
        public void setPrecio(double precio) {
            this.precio = Math.min(Math.max(0.0, precio), PRECIO_MAX);
        }

        @Override
        public String toString() {
            return "Bicicleta[" + super.toString() + "]";
        }
    }

    public static class CocheAsegurado extends Vehiculo implements Asegurable {
        private static final double PRECIO_MAX = 60_000.00;
        private double cobertura;

        public CocheAsegurado(String potencia, double precio) {
            super(potencia, 4, precio);
            setCobertura();
        }

        @Override
        public void setPrecio(double precio) {
            this.precio = Math.min(Math.max(0.0, precio), PRECIO_MAX);
        }

        @Override
        public void setCobertura() {
            this.cobertura = this.precio * 0.90;
        }

        @Override
        public double getCobertura() {
            return this.cobertura;
        }

        @Override
        public String toString() {
            return String.format("CocheAsegurado[%s, cobertura=%.2f€]",
                    super.toString(), this.cobertura);
        }
    }

    public static void main(String[] args) {
        Vehiculo[] flota = {
            new Velero(12, 85_000.0),
            new Bicicleta(),
            new Bicicleta("Motor eléctrico", 5_000.0), // Se topará en 3999.99€
            new CocheAsegurado("Motor Híbrido 140CV", 28_000.0),
            new Velero(18, 120_000.0)                  // Se topará en 100000.00€
        };

        StringBuilder sb = new StringBuilder("=== INVENTARIO DE FLOTA ===\n");
        int numVeleros = 0, numBicis = 0, numCoches = 0;

        for (int i = 0; i < flota.length; i++) {
            Vehiculo v = flota[i];
            sb.append(String.format("#%d [%s] -> %s%n",
                    i + 1, v.getClass().getSimpleName(), v));

            if (v instanceof Velero) numVeleros++;
            else if (v instanceof Bicicleta) numBicis++;
            else if (v instanceof CocheAsegurado) numCoches++;
        }

        System.out.print(sb);
        System.out.printf("Resumen instanceof -> Veleros: %d | Bicicletas: %d | Coches Asegurados: %d%n",
                numVeleros, numBicis, numCoches);
    }
}
```

---

### Ejercicio 3: Polimorfismo con Interfaces en la Selección de Fútbol (`Seleccionable`)

```java
public class Ej03TestSeleccion {

    public interface Seleccionable {
        void concentrarse();
        void viajar();
        void entrenar();
        void jugarPartido();
    }

    public static abstract class MiembroSeleccionFutbol implements Seleccionable {
        protected String nombre;
        protected int edad;

        public MiembroSeleccionFutbol(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        @Override
        public void concentrarse() {
            System.out.println(this.nombre + " -> concentrarse() [MiembroSeleccionFutbol]");
        }

        @Override
        public void viajar() {
            System.out.println(this.nombre + " -> viajar() [MiembroSeleccionFutbol]");
        }
    }

    public static class Futbolista extends MiembroSeleccionFutbol {
        private int dorsal;
        private String demarcacion;

        public Futbolista(String nombre, int edad, int dorsal, String demarcacion) {
            super(nombre, edad);
            this.dorsal = dorsal;
            this.demarcacion = demarcacion;
        }

        @Override
        public void entrenar() {
            System.out.println(this.nombre + " (#" + this.dorsal + ") -> entrenar() [Futbolista]");
        }

        @Override
        public void jugarPartido() {
            System.out.println(this.nombre + " (" + this.demarcacion + ") -> jugarPartido() [Futbolista]");
        }

        public void entrevista() {
            System.out.println(this.nombre + " -> entrevista() [Futbolista en zona mixta]");
        }
    }

    public static class Entrenador extends MiembroSeleccionFutbol {
        private String federacion;

        public Entrenador(String nombre, int edad, String federacion) {
            super(nombre, edad);
            this.federacion = federacion;
        }

        @Override
        public void entrenar() {
            System.out.println(this.nombre + " -> Realiza entrenar() [Entrenador]");
        }

        @Override
        public void jugarPartido() {
            System.out.println(this.nombre + " -> Realiza jugarPartido() [Entrenador]");
        }

        public void planificaEntrenamiento() {
            System.out.println(this.nombre + " (" + this.federacion + ") -> Realiza planificaEntrenamiento() [Entrenador]");
        }
    }

    public static void main(String[] args) {
        Seleccionable[] convocados = {
            new Entrenador("Luis de la Fuente", 63, "RFEF"),
            new Futbolista("Unai Simón", 27, 23, "Portero"),
            new Futbolista("Dani Carvajal", 32, 2, "Lateral Derecho")
        };

        System.out.println("--- 1. CONCENTRACIÓN ---");
        for (Seleccionable s : convocados) s.concentrarse();

        System.out.println("\n--- 2. VIAJE ---");
        for (Seleccionable s : convocados) s.viajar();

        System.out.println("\n--- 3. ENTRENAMIENTO ---");
        for (Seleccionable s : convocados) s.entrenar();

        System.out.println("\n--- 4. PARTIDO ---");
        for (Seleccionable s : convocados) s.jugarPartido();

        System.out.println("\n--- 5. ACCIONES ESPECÍFICAS (DOWNCASTING SEGURO) ---");
        for (Seleccionable s : convocados) {
            if (s instanceof Futbolista f) {
                f.entrevista();
            } else if (s instanceof Entrenador e) {
                e.planificaEntrenamiento();
            }
        }
    }
}
```

---

### Ejercicio 4: Implementación Jerárquica de la Interfaz `Imprimible`

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ej04PersonaImprimible {

    public interface Imprimible {
        ArrayList<String> contenidoArrayList();

        // Método default que formatea automáticamente cualquier Imprimible
        default String contenidoString() {
            return "{" + String.join(", ", contenidoArrayList()) + "}";
        }
    }

    public static abstract class Persona implements Imprimible {
        protected String nombre;
        protected LocalDate nace;

        public Persona(String nombre, LocalDate nace) {
            this.nombre = nombre;
            this.nace = nace;
        }

        @Override
        public ArrayList<String> contenidoArrayList() {
            ArrayList<String> lista = new ArrayList<>();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lista.add("nombre=" + this.nombre);
            lista.add("nace=" + this.nace.format(fmt));
            return lista;
        }
    }

    public static class Alumno extends Persona {
        private String grupo;
        private double notaMedia;

        public Alumno(String nombre, LocalDate nace, String grupo, double notaMedia) {
            super(nombre, nace);
            this.grupo = grupo;
            this.notaMedia = notaMedia;
        }

        @Override
        public ArrayList<String> contenidoArrayList() {
            ArrayList<String> lista = super.contenidoArrayList();
            lista.add("grupo=" + this.grupo);
            lista.add("notaMedia=" + this.notaMedia);
            return lista;
        }
    }

    public static class Profesor extends Persona {
        private String especialidad;
        private double salario;

        public Profesor(String nombre, LocalDate nace, String especialidad, double salario) {
            super(nombre, nace);
            this.especialidad = especialidad;
            this.salario = salario;
        }

        @Override
        public ArrayList<String> contenidoArrayList() {
            ArrayList<String> lista = super.contenidoArrayList();
            lista.add("especialidad=" + this.especialidad);
            lista.add("salario=" + this.salario);
            return lista;
        }
    }

    public static void main(String[] args) {
        Imprimible[] personas = {
            new Alumno("Clara Montes", LocalDate.of(2005, 3, 21), "1DAM-Semi", 9.1),
            new Profesor("Vicente Blasco", LocalDate.of(1978, 11, 2), "Sistemas", 2380.50)
        };

        for (Imprimible obj : personas) {
            System.out.println(obj.contenidoString());
        }
    }
}
```

---

### Ejercicio 5: Jerarquía `Intervalo`, `Localizable` e `IntervaloAbierto`

```java
import java.time.LocalDateTime;

public class Ej05Intervalos {

    public interface Localizable {
        boolean estaDentro(int x);
    }

    public static class Intervalo implements Localizable {
        private int menor;
        private int mayor;

        // Constructor por defecto apoyado en this(...) y métodos estáticos
        public Intervalo() {
            this(generarD1(), generarD2());
        }

        private static int generarD1() {
            LocalDateTime ahora = LocalDateTime.now();
            return ahora.getHour() + ahora.getSecond() * 10;
        }

        private static int generarD2() {
            LocalDateTime ahora = LocalDateTime.now();
            int d1 = generarD1();
            int d2 = ahora.getMonthValue() * ahora.getMinute();
            return (d1 == d2) ? d2 + 5 : d2;
        }

        public Intervalo(int menor, int mayor) {
            if (menor == mayor) {
                throw new IllegalArgumentException("Los extremos del intervalo no pueden ser iguales.");
            }
            if (mayor < menor) {
                int tmp = mayor;
                mayor = menor;
                menor = tmp;
            }
            this.menor = menor;
            this.mayor = mayor;
        }

        public int getMenor() { return this.menor; }
        public int getMayor() { return this.mayor; }

        public void setMenor(int nuevoMenor) {
            if (nuevoMenor == this.mayor) {
                throw new IllegalArgumentException("El extremo menor no puede coincidir con el mayor.");
            }
            if (nuevoMenor > this.mayor) {
                this.menor = this.mayor;
                this.mayor = nuevoMenor;
            } else {
                this.menor = nuevoMenor;
            }
        }

        public void setMayor(int nuevoMayor) {
            if (nuevoMayor == this.menor) {
                throw new IllegalArgumentException("El extremo mayor no puede coincidir con el menor.");
            }
            if (nuevoMayor < this.menor) {
                this.mayor = this.menor;
                this.menor = nuevoMayor;
            } else {
                this.mayor = nuevoMayor;
            }
        }

        @Override
        public boolean estaDentro(int x) {
            return x >= this.menor && x <= this.mayor;
        }

        @Override
        public String toString() {
            return "[" + this.menor + ", " + this.mayor + "]";
        }
    }

    public static class IntervaloAbierto extends Intervalo {
        private boolean abiertoAbajo;
        private boolean abiertoArriba;

        public IntervaloAbierto() {
            super();
            this.abiertoAbajo = true;
            this.abiertoArriba = true;
        }

        public IntervaloAbierto(int menor, int mayor, boolean abiertoAbajo, boolean abiertoArriba) {
            super(menor, mayor);
            this.abiertoAbajo = abiertoAbajo;
            this.abiertoArriba = abiertoArriba;
        }

        @Override
        public boolean estaDentro(int x) {
            if (!super.estaDentro(x)) {
                return false;
            }
            if (this.abiertoAbajo && x <= getMenor()) {
                return false;
            }
            if (this.abiertoArriba && x >= getMayor()) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            char izq = this.abiertoAbajo ? '(' : '[';
            char der = this.abiertoArriba ? ')' : ']';
            return izq + "" + getMenor() + ", " + getMayor() + der;
        }
    }

    public static void main(String[] args) {
        Intervalo cerrado = new Intervalo(10, 2); // Se ordena a [2, 10]
        IntervaloAbierto abierto = new IntervaloAbierto(2, 10, true, false); // (2, 10]

        System.out.println("Intervalo cerrado " + cerrado + " -> ¿Contiene el 2? " + cerrado.estaDentro(2));
        System.out.println("Intervalo semiabierto " + abierto + " -> ¿Contiene el 2? " + abierto.estaDentro(2));
        System.out.println("Intervalo semiabierto " + abierto + " -> ¿Contiene el 10? " + abierto.estaDentro(10));
    }
}
```

---

### Ejercicio 6: Clases Anidadas Estáticas de Doble Precisión (`Punto2D.Float` y `Punto2D.Double`)

```java
public abstract class Punto2D {

    public abstract double getX();
    public abstract double getY();
    public abstract Punto2D suma(Punto2D otro);

    // 1. Clase estática anidada para ahorrar RAM (coordenadas float)
    public static class Float extends Punto2D {
        private float x;
        private float y;

        public Float() { this(0.0f, 0.0f); }
        public Float(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override public double getX() { return this.x; }
        @Override public double getY() { return this.y; }

        @Override
        public Punto2D suma(Punto2D otro) {
            return new Punto2D.Float(
                    (float) (this.x + otro.getX()),
                    (float) (this.y + otro.getY())
            );
        }

        @Override
        public String toString() {
            return "(" + this.x + "f, " + this.y + "f)";
        }
    }

    // 2. Clase estática anidada para máxima precisión (coordenadas double)
    public static class Double extends Punto2D {
        private double x;
        private double y;

        public Double() { this(0.0, 0.0); }
        public Double(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override public double getX() { return this.x; }
        @Override public double getY() { return this.y; }

        @Override
        public Punto2D suma(Punto2D otro) {
            return new Punto2D.Double(
                    this.x + otro.getX(),
                    this.y + otro.getY()
            );
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("Queremos poco gasto (RAM y CPU):");
        Punto2D p1 = new Punto2D.Float(1.0f, 2.0f);
        Punto2D p2 = new Punto2D.Float(1 / 3.0f, 2.0f);
        System.out.println("  p1 = " + p1 + " y p2 = " + p2);
        System.out.println("  p1.suma(p2) = " + p1.suma(p2));

        System.out.println("\nQueremos máxima precisión:");
        Punto2D p3 = new Punto2D.Double(1.0, 2.0);
        Punto2D p4 = new Punto2D.Double(1 / 3.0, 2.0);
        System.out.println("  p3 = " + p3 + " y p4 = " + p4);
        System.out.println("  p3.suma(p4) = " + p3.suma(p4));

        System.out.println("\nPodemos mezclar ambos tipos polimórficamente:");
        System.out.println("  p1.suma(p3) = " + p1.suma(p3));
        System.out.println("  p3.suma(p1) = " + p3.suma(p1));
    }
}
```

---

### Ejercicio 7: Las 5 Evoluciones de `java.io.FileFilter` (De Clase Externa a Lambda)

```java
import java.io.File;
import java.io.FileFilter;

// Enfoque A/B: Clase independiente en el mismo fichero
class FiltroPorExtension implements FileFilter {
    private final String extension;

    public FiltroPorExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        return f.getName().endsWith(this.extension);
    }
}

public class Ej07EvolucionFileFilter {

    // Enfoque C1: Clase interna / anidada dentro de la clase principal
    private static class FiltroInterno implements FileFilter {
        private final String ext;
        public FiltroInterno(String ext) { this.ext = ext; }

        @Override
        public boolean accept(File f) {
            return f.getName().endsWith(this.ext);
        }
    }

    public static void main(String[] args) {
        File carpetaActual = new File(".");
        String extBuscada = ".json";

        // 1. Usando la clase independiente FiltroPorExtension
        File[] res1 = carpetaActual.listFiles(new FiltroPorExtension(extBuscada));

        // 2. Usando la clase anidada FiltroInterno
        File[] res2 = carpetaActual.listFiles(new FiltroInterno(extBuscada));

        // 3. Usando una clase interna local dentro de main
        class FiltroLocal implements FileFilter {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(extBuscada);
            }
        }
        File[] res3 = carpetaActual.listFiles(new FiltroLocal());

        // 4. Usando una Clase Anónima
        File[] res4 = carpetaActual.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(extBuscada);
            }
        });

        // 5. Usando una Expresión Lambda (Java 8 -> Java 25 LTS)
        File[] res5 = carpetaActual.listFiles(f -> f.getName().endsWith(extBuscada));

        System.out.println("Archivos encontrados con expresión lambda (" + extBuscada + "):");
        if (res5 != null) {
            for (File archivo : res5) {
                System.out.println(" - " + archivo.getName());
            }
        }
    }
}
```

---

### Ejercicio 8: Sistema Bancario Abstracto (`CuentaBancaria`, `CuentaCorriente` y `CuentaAhorro`)

```java
import java.util.ArrayList;

public class Ej08BancoAbstracto {

    public static abstract class CuentaBancaria {
        public static final double INTERES_ANUAL_BASICO = 0.025; // 2.5%
        protected final String iban;
        protected double saldo;

        public CuentaBancaria(String iban, double saldoInicial) {
            this.iban = iban;
            this.saldo = Math.max(0.0, saldoInicial);
        }

        public String getIban() { return this.iban; }
        public double getSaldo() { return this.saldo; }

        // Método privado general reutilizado por ingresar, retirar y traspasar
        private void anadir(double cantidad) {
            this.saldo += cantidad;
        }

        public void ingresar(double cantidad) {
            if (cantidad > 0) anadir(cantidad);
        }

        public boolean retirar(double cantidad) {
            if (cantidad > 0 && this.saldo >= cantidad) {
                anadir(-cantidad);
                return true;
            }
            return false;
        }

        public boolean traspasar(CuentaBancaria destino, double cantidad) {
            if (destino != null && this.retirar(cantidad)) {
                destino.ingresar(cantidad);
                return true;
            }
            return false;
        }

        public abstract void calcularIntereses();

        @Override
        public String toString() {
            return String.format("%s[IBAN=%s | Saldo=%.2f €]",
                    getClass().getSimpleName(), this.iban, this.saldo);
        }
    }

    public static class CuentaCorriente extends CuentaBancaria {
        public CuentaCorriente(String iban, double saldoInicial) {
            super(iban, saldoInicial);
        }

        @Override
        public void calcularIntereses() {
            double intereses = this.saldo * INTERES_ANUAL_BASICO;
            ingresar(intereses);
        }
    }

    public static class CuentaAhorro extends CuentaBancaria {
        public static final double SALDO_MINIMO = 3_000.00;

        public CuentaAhorro(String iban, double saldoInicial) {
            super(iban, saldoInicial);
        }

        @Override
        public void calcularIntereses() {
            double tasa = (this.saldo < SALDO_MINIMO)
                    ? (INTERES_ANUAL_BASICO / 2.0)
                    : (INTERES_ANUAL_BASICO * 2.0);
            ingresar(this.saldo * tasa);
        }
    }

    public static void main(String[] args) {
        ArrayList<CuentaBancaria> cuentas = new ArrayList<>();
        CuentaCorriente cc = new CuentaCorriente("ES11-0001", 2_000.0);
        CuentaAhorro caBaja = new CuentaAhorro("ES22-0002", 1_500.0); // < 3000 -> mitad interés
        CuentaAhorro caAlta = new CuentaAhorro("ES33-0003", 5_000.0); // >= 3000 -> doble interés

        cuentas.add(cc);
        cuentas.add(caBaja);
        cuentas.add(caAlta);

        cc.traspasar(caBaja, 500.0);

        System.out.println("--- ANTES DE CALCULAR INTERESES ---");
        for (CuentaBancaria c : cuentas) System.out.println("  " + c);

        for (CuentaBancaria c : cuentas) c.calcularIntereses();

        System.out.println("\n--- DESPUÉS DE CALCULAR INTERESES ANUALES ---");
        for (CuentaBancaria c : cuentas) System.out.println("  " + c);
    }
}
```

---

### Ejercicio 9: Sistema Solar Jerárquico (`Astro`, `Planeta` y `Satelite`)

```java
import java.util.ArrayList;

public class Ej09SistemaSolar {

    public static abstract class Astro {
        protected String nombre;
        protected double masa;
        protected double diametroMedio;
        protected double periodoRotacion;
        protected double periodoTraslacion;
        protected double distanciaMedia;

        public Astro(String nombre, double masa, double diametroMedio,
                     double periodoRotacion, double periodoTraslacion, double distanciaMedia) {
            this.nombre = nombre;
            this.masa = masa;
            this.diametroMedio = diametroMedio;
            this.periodoRotacion = periodoRotacion;
            this.periodoTraslacion = periodoTraslacion;
            this.distanciaMedia = distanciaMedia;
        }

        public String getNombre() { return this.nombre; }
        public abstract void muestra();
    }

    public static class Planeta extends Astro {
        private final ArrayList<Satelite> satelites;

        public Planeta(String nombre, double masa, double diametroMedio,
                       double periodoRotacion, double periodoTraslacion, double distanciaAlSol) {
            super(nombre, masa, diametroMedio, periodoRotacion, periodoTraslacion, distanciaAlSol);
            this.satelites = new ArrayList<>();
        }

        public void anadirSatelite(Satelite s) {
            if (s != null) this.satelites.add(s);
        }

        @Override
        public void muestra() {
            System.out.printf("🌍 PLANETA: %s | Masa: %.2e kg | Diámetro: %.0f km | Dist. al Sol: %.1f Mkm%n",
                    this.nombre, this.masa, this.diametroMedio, this.distanciaMedia);
            if (!this.satelites.isEmpty()) {
                System.out.println("   Satélites que lo orbitan:");
                for (Satelite s : this.satelites) {
                    System.out.println("     - " + s.getNombre());
                }
            }
        }
    }

    public static class Satelite extends Astro {
        private final Planeta planeta;

        public Satelite(String nombre, double masa, double diametroMedio,
                        double periodoRotacion, double periodoTraslacion,
                        double distanciaAlPlaneta, Planeta planeta) {
            super(nombre, masa, diametroMedio, periodoRotacion, periodoTraslacion, distanciaAlPlaneta);
            this.planeta = planeta;
            if (planeta != null) {
                planeta.anadirSatelite(this);
            }
        }

        @Override
        public void muestra() {
            System.out.printf("🌙 SATÉLITE: %s (orbita a %s) | Diámetro: %.0f km | Dist. planetaria: %.0f km%n",
                    this.nombre, this.planeta.getNombre(), this.diametroMedio, this.distanciaMedia);
        }
    }

    public static void main(String[] args) {
        Planeta tierra = new Planeta("Tierra", 5.97e24, 12742, 24.0, 365.25, 149.6);
        Satelite luna = new Satelite("Luna", 7.34e22, 3474, 655.7, 655.7, 384400, tierra);

        Planeta jupiter = new Planeta("Júpiter", 1.89e27, 139820, 9.9, 4332.6, 778.5);
        Satelite europa = new Satelite("Europa", 4.80e22, 3121, 85.2, 85.2, 670900, jupiter);

        ArrayList<Astro> catalogo = new ArrayList<>();
        catalogo.add(tierra);
        catalogo.add(luna);
        catalogo.add(jupiter);
        catalogo.add(europa);

        for (Astro a : catalogo) {
            a.muestra();
        }
    }
}
```

---

### Ejercicio 10: Flota Multimodal de Transporte con Validación Regex de Matrículas

```java
import java.util.ArrayList;

public class Ej10FlotaMultimodal {

    public static abstract class Vehiculo {
        protected final String matricula;
        protected final String modelo;

        public Vehiculo(String matricula, String modelo, String patronRegex) {
            if (matricula == null || !matricula.matches(patronRegex)) {
                throw new IllegalArgumentException(
                        "Matrícula inválida '" + matricula + "' para el patrón " + patronRegex);
            }
            this.matricula = matricula.toUpperCase();
            this.modelo = modelo;
        }

        public abstract void imprimir();
    }

    // 1. Rama Terrestre (4 números y 3 letras)
    public static class Terrestre extends Vehiculo {
        protected final int numRuedas;

        public Terrestre(String matricula, String modelo, int numRuedas) {
            super(matricula, modelo, "\\d{4}[A-Za-z]{3}");
            this.numRuedas = numRuedas;
        }

        @Override
        public void imprimir() {
            System.out.printf("Terrestre[mat=%s, mod=%s, ruedas=%d]%n",
                    this.matricula, this.modelo, this.numRuedas);
        }
    }

    public static class Coche extends Terrestre {
        private boolean aireAcondicionado;

        public Coche(String matricula, String modelo, boolean aireAcondicionado) {
            super(matricula, modelo, 4);
            this.aireAcondicionado = aireAcondicionado;
        }

        @Override
        public void imprimir() {
            System.out.printf("Coche[mat=%s, mod=%s, ruedas=%d, A/A=%b]%n",
                    this.matricula, this.modelo, this.numRuedas, this.aireAcondicionado);
        }
    }

    public static class Moto extends Terrestre {
        private String color;

        public Moto(String matricula, String modelo, String color) {
            super(matricula, modelo, 2);
            this.color = color;
        }

        @Override
        public void imprimir() {
            System.out.printf("Moto[mat=%s, mod=%s, ruedas=%d, color=%s]%n",
                    this.matricula, this.modelo, this.numRuedas, this.color);
        }
    }

    // 2. Rama Acuática (de 3 a 10 letras)
    public static class Acuatico extends Vehiculo {
        protected final double eslora;

        public Acuatico(String matricula, String modelo, double eslora) {
            super(matricula, modelo, "[A-Za-z]{3,10}");
            this.eslora = eslora;
        }

        @Override
        public void imprimir() {
            System.out.printf("Acuático[mat=%s, mod=%s, eslora=%.1fm]%n",
                    this.matricula, this.modelo, this.eslora);
        }
    }

    public static class Barco extends Acuatico {
        private boolean tieneMotor;

        public Barco(String matricula, String modelo, double eslora, boolean tieneMotor) {
            super(matricula, modelo, eslora);
            this.tieneMotor = tieneMotor;
        }

        @Override
        public void imprimir() {
            System.out.printf("Barco[mat=%s, mod=%s, eslora=%.1fm, motor=%b]%n",
                    this.matricula, this.modelo, this.eslora, this.tieneMotor);
        }
    }

    public static class Submarino extends Acuatico {
        private double profundidadMaxima;

        public Submarino(String matricula, String modelo, double eslora, double profundidadMaxima) {
            super(matricula, modelo, eslora);
            this.profundidadMaxima = profundidadMaxima;
        }

        @Override
        public void imprimir() {
            System.out.printf("Submarino[mat=%s, mod=%s, eslora=%.1fm, profMax=%.0fm]%n",
                    this.matricula, this.modelo, this.eslora, this.profundidadMaxima);
        }
    }

    // 3. Rama Aérea (4 letras y 6 números)
    public static class Aereo extends Vehiculo {
        protected final int numAsientos;

        public Aereo(String matricula, String modelo, int numAsientos) {
            super(matricula, modelo, "[A-Za-z]{4}\\d{6}");
            this.numAsientos = numAsientos;
        }

        @Override
        public void imprimir() {
            System.out.printf("Aéreo[mat=%s, mod=%s, asientos=%d]%n",
                    this.matricula, this.modelo, this.numAsientos);
        }
    }

    public static class Avion extends Aereo {
        private double tiempoMaximoVuelo;

        public Avion(String matricula, String modelo, int numAsientos, double tiempoMaximoVuelo) {
            super(matricula, modelo, numAsientos);
            this.tiempoMaximoVuelo = tiempoMaximoVuelo;
        }

        @Override
        public void imprimir() {
            System.out.printf("Avión[mat=%s, mod=%s, asientos=%d, autonomía=%.1fh]%n",
                    this.matricula, this.modelo, this.numAsientos, this.tiempoMaximoVuelo);
        }
    }

    public static class Helicoptero extends Aereo {
        private int numHelices;

        public Helicoptero(String matricula, String modelo, int numAsientos, int numHelices) {
            super(matricula, modelo, numAsientos);
            this.numHelices = numHelices;
        }

        @Override
        public void imprimir() {
            System.out.printf("Helicóptero[mat=%s, mod=%s, asientos=%d, hélices=%d]%n",
                    this.matricula, this.modelo, this.numAsientos, this.numHelices);
        }
    }

    public static void main(String[] args) {
        ArrayList<Vehiculo> flota = new ArrayList<>();
        flota.add(new Terrestre("1234BCD", "Remolque Industrial", 6));
        flota.add(new Coche("5678FGH", "Seat León", true));
        flota.add(new Moto("9012JKL", "Yamaha MT-07", "Azul"));
        flota.add(new Acuatico("NAUTILUS", "Balsa Salvavidas", 4.5));
        flota.add(new Barco("POSEIDON", "Ferry Balearia", 120.0, true));
        flota.add(new Submarino("PERAL", "S-81 Isaac Peral", 80.8, 460.0));
        flota.add(new Aereo("ECAA000111", "Globo Aerostático", 8));
        flota.add(new Avion("IBEX123456", "Airbus A350", 348, 16.5));
        flota.add(new Helicoptero("HELI654321", "Airbus H135", 7, 2));

        for (Vehiculo v : flota) {
            v.imprimir();
        }
    }
}
```

---

### Ejercicio 11: Arquitectura con Clases Abstractas e Interfaces (`La Granja`)

```java
import java.time.LocalDateTime;

public class Ej11LaGranja {

    public interface Volador { void vuela(); }
    public interface Trabajador { void trabaja(); }
    public interface MascotaHabladora {
        void acompanaEnCasa();
        void habla();
    }

    public static abstract class AnimalGranja {
        protected LocalDateTime nace;

        public AnimalGranja() {
            this.nace = LocalDateTime.now();
        }

        public abstract void naceDe();
    }

    public static abstract class AnimalDomestico extends AnimalGranja { }

    public static class Vaca extends AnimalDomestico implements Trabajador {
        @Override public void naceDe() { System.out.println("La vaca nace del vientre materno (vivíparo)."); }
        @Override public void trabaja() { System.out.println("La vaca produce leche y tira del carro."); }
    }

    public static class Gallina extends AnimalDomestico implements Volador, Trabajador {
        @Override public void naceDe() { System.out.println("La gallina nace de un huevo (ovíparo)."); }
        @Override public void vuela() { System.out.println("La gallina da pequeños vuelos rasantes en el corral."); }
        @Override public void trabaja() { System.out.println("La gallina pone huevos frescos cada mañana."); }
    }

    public static class Perro extends AnimalDomestico implements Trabajador, MascotaHabladora {
        @Override public void naceDe() { System.out.println("El perro nace de parto (vivíparo)."); }
        @Override public void trabaja() { System.out.println("El perro pastorea el rebaño y vigila la granja."); }
        @Override public void acompanaEnCasa() { System.out.println("El perro duerme junto a la chimenea."); }
        @Override public void habla() { System.out.println("El perro dice: ¡Guau, guau!"); }
    }

    public static class Gato extends AnimalDomestico implements MascotaHabladora {
        @Override public void naceDe() { System.out.println("El gato nace de parto (vivíparo)."); }
        @Override public void acompanaEnCasa() { System.out.println("El gato ronronea en el sofá."); }
        @Override public void habla() { System.out.println("El gato dice: ¡Miau!"); }
    }

    public static class Tigre extends AnimalGranja { // Salvaje: NO extiende AnimalDomestico
        @Override public void naceDe() { System.out.println("El tigre nace en la selva (vivíparo)."); }
    }

    // MulaMecanica NO es un animal, pero SÍ implementa Trabajador
    public static class MulaMecanica implements Trabajador {
        @Override public void trabaja() { System.out.println("La mula mecánica ara la tierra con motor diésel."); }
    }

    public static void main(String[] args) {
        Trabajador[] fuerzaTrabajo = { new Vaca(), new Gallina(), new Perro(), new MulaMecanica() };
        System.out.println("=== JORNADA DE TRABAJO EN LA GRANJA ===");
        for (Trabajador t : fuerzaTrabajo) {
            t.trabaja();
        }
    }
}
```

---

### Ejercicio 12: Funciones de Orden Superior y Composición de Expresiones Lambda

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.Comparator.comparing;

public class Ej12ComposicionLambdas {

    public record Articulo(String nombre, String categoria, double precio, int stock) {}

    public static <T> List<T> filtrar(List<T> lista, Predicate<T> predicado) {
        List<T> resultado = new ArrayList<>();
        for (T elem : lista) {
            if (predicado.test(elem)) resultado.add(elem);
        }
        return resultado;
    }

    public static <T, R> List<R> transformar(List<T> lista, Function<T, R> funcion) {
        List<R> resultado = new ArrayList<>();
        for (T elem : lista) {
            resultado.add(funcion.apply(elem));
        }
        return resultado;
    }

    public static <T> void consumir(List<T> lista, Consumer<T> consumidor) {
        for (T elem : lista) {
            consumidor.accept(elem);
        }
    }

    public static void main(String[] args) {
        List<Articulo> catalogo = new ArrayList<>(List.of(
            new Articulo("Portátil Pro", "Informática", 1200.0, 5),
            new Articulo("Ratón USB", "Informática", 19.90, 40),
            new Articulo("Monitor 4K", "Informática", 350.0, 0),
            new Articulo("Tarjeta Gráfica", "Informática", 480.0, 3),
            new Articulo("Silla Ergonómica", "Mobiliario", 210.0, 7)
        ));

        // 1. Composición de Predicados con .and()
        Predicate<Articulo> esInformatica = a -> "Informática".equalsIgnoreCase(a.categoria());
        Predicate<Articulo> masDe100Euros = a -> a.precio() > 100.0;
        Predicate<Articulo> conStock      = a -> a.stock() > 0;

        List<Articulo> seleccionados = filtrar(catalogo, esInformatica.and(masDe100Euros).and(conStock));
        System.out.println("1. Artículos de Informática > 100€ con stock:");
        consumir(seleccionados, a -> System.out.println("   -> " + a));

        // 2. Composición de Funciones: andThen vs compose
        Function<Double, Double> descontar10Euros = p -> p - 10.0;
        Function<Double, Double> aplicarIVA21     = p -> p * 1.21;

        double base = 100.0;
        System.out.printf("%n2. Precio base %.2f€:%n", base);
        System.out.printf("   descontar10.andThen(IVA): %.2f €%n", descontar10Euros.andThen(aplicarIVA21).apply(base));
        System.out.printf("   descontar10.compose(IVA): %.2f €%n", descontar10Euros.compose(aplicarIVA21).apply(base));

        // 3. Ordenación multicriterio con Comparator.comparing + thenComparing + reversed
        catalogo.sort(
            comparing(Articulo::categoria)
                .thenComparing(comparing(Articulo::precio).reversed())
        );

        System.out.println("\n3. Catálogo ordenado por Categoría (ASC) y Precio (DESC):");
        consumir(catalogo, a -> System.out.printf("   [%s] %-18s -> %7.2f €%n",
                a.categoria(), a.nombre(), a.precio()));
    }
}
```
