---
title: "Boletín 4.1: Nivel Inicial — Soluciones Resueltas"
description: "Soluciones completas y comentadas de los 12 ejercicios de nivel inicial de la Unidad 4 en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 2
  badge:
    text: "Soluciones"
    variant: "success"
---

A continuación se presentan las soluciones comentadas de los 12 ejercicios iniciales de la Unidad 4, implementadas siguiendo los estándares de encapsulación, validación defensiva y buenas prácticas de Java 25 LTS.

---

### Solución Ejercicio 1: La Clase `Libro`

```java
public class Libro {
    private String titulo;
    private String autor;
    private int numeroPaginas;

    public Libro(String titulo, String autor, int numeroPaginas) {
        setTitulo(titulo);
        setAutor(autor);
        setNumeroPaginas(numeroPaginas);
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede ser nulo ni vacío.");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor del libro no puede ser nulo ni vacío.");
        }
        this.autor = autor.trim();
    }

    public int getNumeroPaginas() {
        return this.numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        if (numeroPaginas <= 0) {
            throw new IllegalArgumentException("El número de páginas debe ser estrictamente positivo: " + numeroPaginas);
        }
        this.numeroPaginas = numeroPaginas;
    }

    public boolean esLargo() {
        return this.numeroPaginas > 300;
    }
}
```

```java
public class MainLibro {
    public static void main(String[] args) {
        Libro l1 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 863);
        Libro l2 = new Libro("El Principito", "Antoine de Saint-Exupéry", 96);

        System.out.printf("Libro 1: '%s' por %s (%d págs.) -> ¿Es largo? %b%n",
                l1.getTitulo(), l1.getAutor(), l1.getNumeroPaginas(), l1.esLargo());
        System.out.printf("Libro 2: '%s' por %s (%d págs.) -> ¿Es largo? %b%n",
                l2.getTitulo(), l2.getAutor(), l2.getNumeroPaginas(), l2.esLargo());
    }
}
```

---

### Solución Ejercicio 2: La Clase `Rectangulo`

```java
public class Rectangulo {
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException("La base y la altura deben ser mayores que cero.");
        }
        this.base = base;
        this.altura = altura;
    }

    public double getBase() {
        return this.base;
    }

    public void setBase(double base) {
        if (base <= 0) throw new IllegalArgumentException("La base debe ser positiva.");
        this.base = base;
    }

    public double getAltura() {
        return this.altura;
    }

    public void setAltura(double altura) {
        if (altura <= 0) throw new IllegalArgumentException("La altura debe ser positiva.");
        this.altura = altura;
    }

    public double calcularArea() {
        return this.base * this.altura;
    }

    public double calcularPerimetro() {
        return 2 * (this.base + this.altura);
    }

    public boolean esCuadrado() {
        return Double.compare(this.base, this.altura) == 0;
    }

    public void escalar(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("El factor de escala debe ser positivo: " + factor);
        }
        this.base *= factor;
        this.altura *= factor;
    }
}
```

---

### Solución Ejercicio 3: La Clase `Circulo`

```java
public class Circulo {
    private double radio;

    public Circulo(double radio) {
        if (radio <= 0) {
            throw new IllegalArgumentException("El radio del círculo debe ser estrictamente positivo.");
        }
        this.radio = radio;
    }

    // Constructor sin parámetros delegando en el canónico
    public Circulo() {
        this(1.0);
    }

    public double getRadio() {
        return this.radio;
    }

    public void setRadio(double radio) {
        if (radio <= 0) throw new IllegalArgumentException("Radio no válido.");
        this.radio = radio;
    }

    public double calcularArea() {
        return Math.PI * Math.pow(this.radio, 2);
    }

    public double calcularPerimetro() {
        return 2 * Math.PI * this.radio;
    }
}
```

---

### Solución Ejercicio 4: Conversor Térmico con la Clase `Termometro`

```java
public class Termometro {
    public static final double CERO_ABSOLUTO_CELSIUS = -273.15;
    private double temperaturaCelsius;

    public Termometro(double temperaturaCelsius) {
        setTemperaturaCelsius(temperaturaCelsius);
    }

    public double getTemperaturaCelsius() {
        return this.temperaturaCelsius;
    }

    public void setTemperaturaCelsius(double celsius) {
        if (celsius < CERO_ABSOLUTO_CELSIUS) {
            throw new IllegalArgumentException("Temperatura por debajo del cero absoluto: " + celsius);
        }
        this.temperaturaCelsius = celsius;
    }

    public double getFahrenheit() {
        return (this.temperaturaCelsius * 9.0 / 5.0) + 32.0;
    }

    public double getKelvin() {
        return this.temperaturaCelsius + 273.15;
    }

    public void incrementar(double delta) {
        setTemperaturaCelsius(this.temperaturaCelsius + delta);
    }

    public void decrementar(double delta) {
        setTemperaturaCelsius(this.temperaturaCelsius - delta);
    }
}
```

---

### Solución Ejercicio 5: Coordenadas Cartesianas con `Punto2D`

```java
public class Punto2D {
    private double x;
    private double y;

    public Punto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Punto2D() {
        this(0.0, 0.0);
    }

    public double getX() { return this.x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return this.y; }
    public void setY(double y) { this.y = y; }

    public void desplazar(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public double distanciaAlOrigen() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }
}
```

---

### Solución Ejercicio 6: La Máquina Cafetera

```java
public class Cafetera {
    private int capacidadMaxima;
    private int cantidadActual;

    public Cafetera() {
        this(1000, 0);
    }

    public Cafetera(int capacidadMaxima) {
        this(capacidadMaxima, 0);
    }

    public Cafetera(int capacidadMaxima, int cantidadActual) {
        this.capacidadMaxima = Math.max(capacidadMaxima, 100);
        if (cantidadActual > this.capacidadMaxima) {
            this.cantidadActual = this.capacidadMaxima;
        } else {
            this.cantidadActual = Math.max(cantidadActual, 0);
        }
    }

    public void llenarCafetera() {
        this.cantidadActual = this.capacidadMaxima;
    }

    public int servirTaza(int capacidadTaza) {
        if (capacidadTaza <= 0) return 0;

        if (this.cantidadActual >= capacidadTaza) {
            this.cantidadActual -= capacidadTaza;
            return capacidadTaza;
        } else {
            int servido = this.cantidadActual;
            this.cantidadActual = 0;
            return servido; // Sirve lo que queda
        }
    }

    public void vaciarCafetera() {
        this.cantidadActual = 0;
    }

    public void agregarCafe(int cantidad) {
        if (cantidad > 0) {
            this.cantidadActual = Math.min(this.cantidadActual + cantidad, this.capacidadMaxima);
        }
    }

    public int getCapacidadMaxima() { return this.capacidadMaxima; }
    public int getCantidadActual() { return this.cantidadActual; }
}
```

---

### Solución Ejercicio 7: Cronómetro Simple con `Reloj`

```java
public class Reloj {
    private int hora;
    private int minuto;
    private int segundo;

    public Reloj(int hora, int minuto, int segundo) {
        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59 || segundo < 0 || segundo > 59) {
            throw new IllegalArgumentException("Valores de tiempo fuera de límites.");
        }
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    public Reloj() {
        this(0, 0, 0);
    }

    public void ticTac() {
        this.segundo++;
        if (this.segundo == 60) {
            this.segundo = 0;
            this.minuto++;
            if (this.minuto == 60) {
                this.minuto = 0;
                this.hora++;
                if (this.hora == 24) {
                    this.hora = 0;
                }
            }
        }
    }

    public String getHoraFormateada() {
        return String.format("%02d:%02d:%02d", this.hora, this.minuto, this.segundo);
    }
}
```

---

### Solución Ejercicio 8: Identificadores con Miembros `static`

```java
import java.util.Objects;

public class Empleado {
    private static int contadorSecuencial = 1000;
    private final int idEmpleado;
    private String nombre;
    private String departamento;
    private double sueldo;

    public Empleado(String nombre, String departamento, double sueldo) {
        this.idEmpleado = ++contadorSecuencial;
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.departamento = departamento;
        this.sueldo = Math.max(sueldo, 0);
    }

    public static int getTotalEmpleados() {
        return contadorSecuencial - 1000;
    }

    public int getIdEmpleado() { return this.idEmpleado; }
    public String getNombre() { return this.nombre; }
    public double getSueldo() { return this.sueldo; }

    public void aplicarIncremento(double porcentaje) {
        if (porcentaje > 0) {
            this.sueldo += this.sueldo * (porcentaje / 100.0);
        }
    }
}
```

---

### Solución Ejercicio 9: La Clase `Contador` y Encadenamiento

```java
public class Contador {
    private int valor;

    public Contador() {
        this(0);
    }

    public Contador(int valorInicial) {
        this.valor = valorInicial;
    }

    public Contador(Contador otro) {
        this(otro != null ? otro.valor : 0);
    }

    public Contador incrementar() {
        this.valor++;
        return this; // Permite method chaining
    }

    public Contador decrementar() {
        this.valor--;
        return this;
    }

    public Contador incrementar(int cantidad) {
        this.valor += cantidad;
        return this;
    }

    public int getValor() {
        return this.valor;
    }
}
```

---

### Solución Ejercicio 10: Array de Objetos Alumnos

```java
public class GestionAlumnos {
    public static class Alumno {
        private final String nombre;
        private final double nota;

        public Alumno(String nombre, double nota) {
            this.nombre = nombre;
            this.nota = nota;
        }

        public String getNombre() { return this.nombre; }
        public double getNota() { return this.nota; }
    }

    public static double calcularNotaMedia(Alumno[] lista) {
        if (lista == null || lista.length == 0) return 0.0;
        double suma = 0;
        for (Alumno a : lista) {
            suma += a.getNota();
        }
        return suma / lista.length;
    }

    public static Alumno obtenerMejorAlumno(Alumno[] lista) {
        if (lista == null || lista.length == 0) return null;
        Alumno mejor = lista[0];
        for (int i = 1; i < lista.length; i++) {
            if (lista[i].getNota() > mejor.getNota()) {
                mejor = lista[i];
            }
        }
        return mejor;
    }

    public static void main(String[] args) {
        Alumno[] clase = {
            new Alumno("Elena", 8.5),
            new Alumno("Marcos", 6.2),
            new Alumno("Sara", 9.8),
            new Alumno("David", 7.0),
            new Alumno("Lucía", 5.4)
        };

        System.out.printf("Nota media de la clase: %.2f%n", calcularNotaMedia(clase));
        Alumno top = obtenerMejorAlumno(clase);
        System.out.printf("Mejor alumno: %s con un %.1f%n", top.getNombre(), top.getNota());
    }
}
```

---

### Solución Ejercicio 11: Control de Vehículo con la Clase `Coche`

```java
public class Coche {
    private final String marca;
    private final String modelo;
    private boolean motorEncendido;
    private int velocidadActual;

    public Coche(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
        this.motorEncendido = false;
        this.velocidadActual = 0;
    }

    public boolean arrancar() {
        if (this.motorEncendido) return false;
        this.motorEncendido = true;
        return true;
    }

    public boolean apagar() {
        if (!this.motorEncendido || this.velocidadActual > 0) {
            return false; // Peligro: no apagar en marcha
        }
        this.motorEncendido = false;
        return true;
    }

    public void acelerar(int incremento) {
        if (this.motorEncendido && incremento > 0) {
            this.velocidadActual = Math.min(this.velocidadActual + incremento, 200);
        }
    }

    public void frenar(int decremento) {
        if (decremento > 0) {
            this.velocidadActual = Math.max(this.velocidadActual - decremento, 0);
        }
    }

    public int getVelocidadActual() { return this.velocidadActual; }
    public boolean isMotorEncendido() { return this.motorEncendido; }
}
```

---

### Solución Ejercicio 12: Generador y Validador de Contraseñas

```java
import java.security.SecureRandom;

public class Password {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private int longitud;
    private String valor;

    public Password() {
        this(8);
    }

    public Password(int longitud) {
        this.longitud = Math.max(longitud, 4);
        regenerar();
    }

    public void regenerar() {
        StringBuilder sb = new StringBuilder(this.longitud);
        for (int i = 0; i < this.longitud; i++) {
            int idx = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(idx));
        }
        this.valor = sb.toString();
    }

    public boolean esFuerte() {
        int mayus = 0;
        int minus = 0;
        int digitos = 0;

        for (int i = 0; i < this.valor.length(); i++) {
            char c = this.valor.charAt(i);
            if (Character.isUpperCase(c)) mayus++;
            else if (Character.isLowerCase(c)) minus++;
            else if (Character.isDigit(c)) digitos++;
        }

        return mayus >= 2 && minus >= 1 && digitos >= 2;
    }

    public String getValor() { return this.valor; }
    public int getLongitud() { return this.longitud; }
}
```
