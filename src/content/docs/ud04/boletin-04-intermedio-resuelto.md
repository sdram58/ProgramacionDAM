---
title: "Boletín 4.2: Nivel Intermedio — Soluciones Resueltas"
description: "Soluciones completas, comentadas y optimizadas de los 12 ejercicios de nivel intermedio de la Unidad 4 en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 4
  badge:
    text: "Soluciones"
    variant: "success"
---

A continuación se presentan las soluciones comentadas de los 12 ejercicios de nivel intermedio de la Unidad 4, haciendo énfasis en la inmutabilidad, el respeto a los contratos de `Object`, la modularidad y los patrones de diseño orientados a objetos.

---

### Solución Ejercicio 1: Sobrescritura Canónica de `equals()`, `hashCode()` y `toString()`

```java
import java.util.Objects;

public class Libro {
    private final String isbn;
    private String titulo;
    private String autor;
    private int paginas;

    public Libro(String isbn, String titulo, String autor, int paginas) {
        if (isbn == null || !isbn.matches("^\\d{10}(\\d{3})?$")) {
            throw new IllegalArgumentException("El ISBN debe tener 10 o 13 dígitos numéricos.");
        }
        this.isbn = isbn;
        this.titulo = Objects.requireNonNull(titulo, "El título no puede ser nulo");
        this.autor = Objects.requireNonNull(autor, "El autor no puede ser nulo");
        this.paginas = Math.max(paginas, 1);
    }

    public String getIsbn() { return this.isbn; }
    public String getTitulo() { return this.titulo; }
    public String getAutor() { return this.autor; }
    public int getPaginas() { return this.paginas; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Libro other)) return false;
        return Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.isbn);
    }

    @Override
    public String toString() {
        return String.format("Libro[ISBN='%s', Título='%s', Autor='%s', Páginas=%d]",
                this.isbn, this.titulo, this.autor, this.paginas);
    }
}
```

---

### Solución Ejercicio 2: Composición (`Motor` y `Vehiculo`)

```java
public class Motor {
    private final String combustible;
    private final int potenciaCv;
    private boolean encendido;

    public Motor(String combustible, int potenciaCv) {
        this.combustible = combustible;
        this.potenciaCv = Math.max(potenciaCv, 1);
        this.encendido = false;
    }

    public void encender() { this.encendido = true; }
    public void apagar() { this.encendido = false; }
    public boolean isEncendido() { return this.encendido; }
    public int getPotenciaCv() { return this.potenciaCv; }
    public String getCombustible() { return this.combustible; }
}
```

```java
public class Vehiculo {
    private final String matricula;
    private final String marca;
    private final Motor motor; // Composición

    public Vehiculo(String matricula, String marca, Motor motor) {
        this.matricula = matricula;
        this.marca = marca;
        this.motor = java.util.Objects.requireNonNull(motor, "El motor es obligatorio");
    }

    public void arrancar() {
        if (!this.motor.isEncendido()) {
            this.motor.encender();
            System.out.println("Vehículo " + this.matricula + " ha arrancado su motor.");
        }
    }

    public void detener() {
        this.motor.apagar();
        System.out.println("Vehículo " + this.matricula + " ha apagado su motor.");
    }

    @Override
    public String toString() {
        return String.format("Vehiculo[%s %s | Motor: %d CV %s | Estado: %s]",
                this.marca, this.matricula, this.motor.getPotenciaCv(),
                this.motor.getCombustible(), this.motor.isEncendido() ? "ENCENDIDO" : "APAGADO");
    }
}
```

---

### Solución Ejercicio 3: La Clase Inmutable `Fraccion`

```java
import java.util.Objects;

public final class Fraccion {
    private final int numerador;
    private final int denominador;

    public Fraccion(int numerador, int denominador) {
        if (denominador == 0) {
            throw new IllegalArgumentException("El denominador no puede ser cero.");
        }

        // Normalización de signos
        if (denominador < 0) {
            numerador = -numerador;
            denominador = -denominador;
        }

        // Simplificación por Máximo Común Divisor (Euclides)
        int mcd = mcd(Math.abs(numerador), denominador);
        this.numerador = numerador / mcd;
        this.denominador = denominador / mcd;
    }

    private static int mcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public int getNumerador() { return this.numerador; }
    public int getDenominador() { return this.denominador; }

    public Fraccion sumar(Fraccion otra) {
        int num = (this.numerador * otra.denominador) + (otra.numerador * this.denominador);
        int den = this.denominador * otra.denominador;
        return new Fraccion(num, den);
    }

    public Fraccion restar(Fraccion otra) {
        int num = (this.numerador * otra.denominador) - (otra.numerador * this.denominador);
        int den = this.denominador * otra.denominador;
        return new Fraccion(num, den);
    }

    public Fraccion multiplicar(Fraccion otra) {
        return new Fraccion(this.numerador * otra.numerador, this.denominador * otra.denominador);
    }

    public Fraccion dividir(Fraccion otra) {
        if (otra.numerador == 0) {
            throw new ArithmeticException("No se puede dividir entre una fracción con valor cero.");
        }
        return new Fraccion(this.numerador * otra.denominador, this.denominador * otra.numerador);
    }

    @Override
    public String toString() {
        if (this.denominador == 1) return String.valueOf(this.numerador);
        return this.numerador + "/" + this.denominador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Fraccion f)) return false;
        return this.numerador == f.numerador && this.denominador == f.denominador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numerador, this.denominador);
    }
}
```

---

### Solución Ejercicio 4: `record CoordenadaGPS`

```java
public record CoordenadaGPS(double latitud, double longitud) {

    public CoordenadaGPS {
        if (latitud < -90.0 || latitud > 90.0) {
            throw new IllegalArgumentException("Latitud fuera de rango [-90, 90]: " + latitud);
        }
        if (longitud < -180.0 || longitud > 180.0) {
            throw new IllegalArgumentException("Longitud fuera de rango [-180, 180]: " + longitud);
        }
    }

    // Fórmula del Semiverseno (Haversine) para cálculo de distancias sobre la superficie terrestre
    public double distanciaKm(CoordenadaGPS destino) {
        final double RADIO_TIERRA_KM = 6371.0;
        double dLat = Math.toRadians(destino.latitud() - this.latitud);
        double dLon = Math.toRadians(destino.longitud() - this.longitud);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(destino.latitud())) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }
}
```

---

### Solución Ejercicio 5: El Patrón Singleton (`GestorConfiguracion`)

```java
import java.util.HashMap;
import java.util.Map;

public class GestorConfiguracion {
    // Instancia estática única (Eager initialization)
    private static final GestorConfiguracion INSTANCIA = new GestorConfiguracion();
    private final Map<String, String> propiedades;

    // Constructor estrictamente privado
    private GestorConfiguracion() {
        this.propiedades = new HashMap<>();
        this.propiedades.put("app.nombre", "SistemaDAM");
        this.propiedades.put("app.version", "2.5.0");
    }

    public static GestorConfiguracion getInstance() {
        return INSTANCIA;
    }

    public void setPropiedad(String clave, String valor) {
        this.propiedades.put(clave, valor);
    }

    public String getPropiedad(String clave, String valorPorDefecto) {
        return this.propiedades.getOrDefault(clave, valorPorDefecto);
    }
}
```

---

### Solución Ejercicio 6: La Clase `Mochila` con Copias Defensivas

```java
import java.util.Arrays;

public class Mochila {
    private final String[] contenido;
    private int totalElementos;

    public Mochila(int capacidadMaxima, String[] itemsIniciales) {
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad máxima debe ser mayor que cero.");
        }
        this.contenido = new String[capacidadMaxima];
        this.totalElementos = 0;

        // Copia defensiva en la entrada
        if (itemsIniciales != null) {
            for (String item : itemsIniciales) {
                if (item != null && this.totalElementos < capacidadMaxima) {
                    this.contenido[this.totalElementos++] = item;
                }
            }
        }
    }

    public boolean guardar(String item) {
        if (item != null && this.totalElementos < this.contenido.length) {
            this.contenido[this.totalElementos++] = item;
            return true;
        }
        return false;
    }

    // Copia defensiva en la salida
    public String[] getContenido() {
        return Arrays.copyOf(this.contenido, this.totalElementos);
    }
}
```

---

### Solución Ejercicio 7: Enumeración Rica `NivelSeveridad`

```java
public enum NivelSeveridad {
    DEBUG(1, "DEB", "[36m"),
    INFO(2, "INF", "[32m"),
    WARN(3, "WRN", "[33m"),
    ERROR(4, "ERR", "[31m"),
    FATAL(5, "FTL", "[35m");

    private final int nivelNumerico;
    private final String prefijo;
    private final String codigoColorAnsi;

    NivelSeveridad(int nivelNumerico, String prefijo, String codigoColorAnsi) {
        this.nivelNumerico = nivelNumerico;
        this.prefijo = prefijo;
        this.codigoColorAnsi = codigoColorAnsi;
    }

    public int getNivelNumerico() { return this.nivelNumerico; }
    public String getPrefijo() { return this.prefijo; }
    public String getCodigoColorAnsi() { return this.codigoColorAnsi; }

    public boolean esCritico() {
        return this.nivelNumerico >= 4;
    }

    public boolean esMayorOIgualQue(NivelSeveridad otro) {
        return this.nivelNumerico >= otro.nivelNumerico;
    }
}
```

---

### Solución Ejercicio 8: Cuenta Bancaria con Historial de Transacciones

```java
import java.time.LocalDateTime;
import java.util.Arrays;

public class CuentaConHistorial {
    public record Transaccion(LocalDateTime fechaHora, String tipo, double importe, double saldoPosterior) {}

    private final String iban;
    private double saldo;
    private final Transaccion[] historial;
    private int contadorTransacciones;

    public CuentaConHistorial(String iban, double saldoInicial) {
        this.iban = iban;
        this.saldo = Math.max(saldoInicial, 0.0);
        this.historial = new Transaccion[10];
        this.contadorTransacciones = 0;
    }

    public void ingresar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Importe inválido");
        this.saldo += cantidad;
        registrar("INGRESO", cantidad);
    }

    public boolean retirar(double cantidad) {
        if (cantidad <= 0 || this.saldo < cantidad) return false;
        this.saldo -= cantidad;
        registrar("RETIRADA", cantidad);
        return true;
    }

    private void registrar(String tipo, double importe) {
        Transaccion t = new Transaccion(LocalDateTime.now(), tipo, importe, this.saldo);
        if (this.contadorTransacciones < 10) {
            this.historial[this.contadorTransacciones++] = t;
        } else {
            // Desplazamiento hacia la izquierda (cola circular de los 10 últimos)
            System.arraycopy(this.historial, 1, this.historial, 0, 9);
            this.historial[9] = t;
        }
    }

    public Transaccion[] getUltimosMovimientos() {
        return Arrays.copyOf(this.historial, this.contadorTransacciones);
    }

    public double getSaldo() { return this.saldo; }
}
```

---

### Solución Ejercicio 9: Validación Compacta con `record Usuario`

```java
public record Usuario(String username, String email, int edad) {

    public Usuario {
        if (username == null || !username.matches("^[a-zA-Z0-9_]{4,15}$")) {
            throw new IllegalArgumentException("El username debe tener entre 4 y 15 caracteres alfanuméricos.");
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
        }
        if (edad < 18) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad (≥ 18 años): " + edad);
        }
    }
}
```

---

### Solución Ejercicio 10: Enumeración de Divisas con Conversión

```java
public enum Divisa {
    EUR(1.0, "€"),
    USD(1.08, "$"),
    GBP(0.85, "£"),
    JPY(162.50, "¥");

    private final double tasaRespectoEuro;
    private final String simbolo;

    Divisa(double tasaRespectoEuro, String simbolo) {
        this.tasaRespectoEuro = tasaRespectoEuro;
        this.simbolo = simbolo;
    }

    public double getTasaRespectoEuro() { return this.tasaRespectoEuro; }
    public String getSimbolo() { return this.simbolo; }

    public double convertirA(double cantidad, Divisa divisaDestino) {
        if (cantidad < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        // Paso 1: Convertir a Euros base
        double euros = cantidad / this.tasaRespectoEuro;
        // Paso 2: Convertir de Euros a divisa destino
        return euros * divisaDestino.tasaRespectoEuro;
    }
}
```

---

### Solución Ejercicio 11: Encapsulación de Matriz con `Matriz2D`

```java
import java.util.Arrays;

public final class Matriz2D {
    private final double[][] datos;
    private final int filas;
    private final int columnas;

    public Matriz2D(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0) throw new IllegalArgumentException("Dimensiones inválidas.");
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new double[filas][columnas];
    }

    public Matriz2D(double[][] origen) {
        if (origen == null || origen.length == 0 || origen[0] == null) {
            throw new IllegalArgumentException("Matriz origen inválida.");
        }
        this.filas = origen.length;
        this.columnas = origen[0].length;
        this.datos = new double[filas][columnas];
        // Copia defensiva profunda
        for (int i = 0; i < filas; i++) {
            this.datos[i] = Arrays.copyOf(origen[i], columnas);
        }
    }

    public Matriz2D sumar(Matriz2D otra) {
        if (this.filas != otra.filas || this.columnas != otra.columnas) {
            throw new IllegalArgumentException("Las dimensiones deben ser idénticas para sumar matrices.");
        }
        Matriz2D res = new Matriz2D(this.filas, this.columnas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                res.datos[i][j] = this.datos[i][j] + otra.datos[i][j];
            }
        }
        return res;
    }

    public Matriz2D transponer() {
        Matriz2D t = new Matriz2D(this.columnas, this.filas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                t.datos[j][i] = this.datos[i][j];
            }
        }
        return t;
    }

    public double traza() {
        if (this.filas != this.columnas) throw new IllegalStateException("La traza solo se define para matrices cuadradas.");
        double suma = 0;
        for (int i = 0; i < filas; i++) suma += this.datos[i][i];
        return suma;
    }
}
```

---

### Solución Ejercicio 12: Factorías Estáticas con `ColorRGB`

```java
import java.util.Objects;

public final class ColorRGB {
    public static final ColorRGB ROJO = new ColorRGB(255, 0, 0);
    public static final ColorRGB VERDE = new ColorRGB(0, 255, 0);
    public static final ColorRGB AZUL = new ColorRGB(0, 0, 255);
    public static final ColorRGB BLANCO = new ColorRGB(255, 255, 255);
    public static final ColorRGB NEGRO = new ColorRGB(0, 0, 0);

    private final int r;
    private final int g;
    private final int b;

    // Constructor estrictamente privado
    private ColorRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static ColorRGB fromRGB(int r, int g, int b) {
        validarRango(r);
        validarRango(g);
        validarRango(b);
        return new ColorRGB(r, g, b);
    }

    public static ColorRGB fromHex(String hex) {
        Objects.requireNonNull(hex);
        String limpio = hex.replace("#", "").trim();
        if (limpio.length() != 6) {
            throw new IllegalArgumentException("El código hexadecimal debe tener 6 dígitos: " + hex);
        }
        int r = Integer.parseInt(limpio.substring(0, 2), 16);
        int g = Integer.parseInt(limpio.substring(2, 4), 16);
        int b = Integer.parseInt(limpio.substring(4, 6), 16);
        return new ColorRGB(r, g, b);
    }

    private static void validarRango(int v) {
        if (v < 0 || v > 255) {
            throw new IllegalArgumentException("Canal de color fuera de rango [0, 255]: " + v);
        }
    }

    public String toHex() {
        return String.format("#%02X%02X%02X", this.r, this.g, this.b);
    }

    public int getR() { return this.r; }
    public int getG() { return this.g; }
    public int getB() { return this.b; }

    @Override
    public String toString() {
        return String.format("ColorRGB(%d, %d, %d -> %s)", this.r, this.g, this.b, toHex());
    }
}
```
