---
title: Boletín U05 — Extras
description: Retos de CodeWars y AceptaElReto sobre herencia, interfaces y lambdas
---

# 🔥 Boletín U05 — Extras

> Busca en [CodeWars](https://www.codewars.com/) katas de nivel 6-5 kyu con la etiqueta `Inheritance` o `Interfaces`, y practica también transformando bucles con lambdas y `removeIf`/`forEach`. Aquí tienes dos retos propios.

## Reto 1: jerarquía de empleados con nómina

Diseña una jerarquía `Empleado` (abstracta) → `EmpleadoFijo`, `EmpleadoComercial`, `EmpleadoBecario`, cada una con su propio cálculo de `calcularNomina()`:
- `EmpleadoFijo`: salario fijo mensual.
- `EmpleadoComercial`: salario base + 5% de sus ventas del mes.
- `EmpleadoBecario`: una beca fija, pero con un tope máximo legal de 600€ (si el cálculo superara ese tope, debe quedarse en 600€).

Escribe un método `static double nominaTotal(Empleado[] empleados)` que sume la nómina de toda la plantilla sin importar de qué tipo sea cada empleado.

<details>
<summary>💡 Solución</summary>

```java
abstract class Empleado {
    protected String nombre;
    public abstract double calcularNomina();
}

class EmpleadoFijo extends Empleado {
    private double salario;
    public EmpleadoFijo(double salario) { this.salario = salario; }
    public double calcularNomina() { return salario; }
}

class EmpleadoComercial extends Empleado {
    private double salarioBase, ventas;
    public EmpleadoComercial(double salarioBase, double ventas) {
        this.salarioBase = salarioBase;
        this.ventas = ventas;
    }
    public double calcularNomina() { return salarioBase + ventas * 0.05; }
}

class EmpleadoBecario extends Empleado {
    private double beca;
    private static final double TOPE = 600.0;
    public EmpleadoBecario(double beca) { this.beca = beca; }
    public double calcularNomina() { return Math.min(beca, TOPE); }
}

static double nominaTotal(Empleado[] empleados) {
    double total = 0;
    for (Empleado e : empleados) {
        total += e.calcularNomina();
    }
    return total;
}
```

El truco está en que `nominaTotal` no necesita ningún `if`/`instanceof` — el polimorfismo hace que cada `calcularNomina()` ejecute automáticamente la versión correcta.
</details>

## Reto 2: pipeline de transformación con lambdas

Dada una lista de palabras, escribe un método genérico `procesar(List<String> lista, Predicate<String> filtro, Function<String, String> transformacion)` que devuelva una **nueva** lista con solo las palabras que cumplen `filtro`, ya transformadas con `transformacion`. Pruébalo para quedarte solo con las palabras de más de 4 letras, convertidas a mayúsculas.

<details>
<summary>💡 Solución</summary>

```java
static List<String> procesar(List<String> lista, Predicate<String> filtro, Function<String, String> transformacion) {
    List<String> resultado = new ArrayList<>();
    for (String s : lista) {
        if (filtro.test(s)) {
            resultado.add(transformacion.apply(s));
        }
    }
    return resultado;
}

// Uso:
List<String> palabras = List.of("sol", "programación", "java", "algoritmo", "if");
List<String> resultado = procesar(
    palabras,
    s -> s.length() > 4,
    s -> s.toUpperCase()
);
System.out.println(resultado);  // [PROGRAMACIÓN, ALGORITMO]
```

Este patrón —pasar el "qué comprobar" y el "qué hacer" como parámetros, en vez de código fijo dentro del método— es la base de la API Stream que verás en la unidad 7: en vez de escribir un bucle distinto para cada combinación de filtro y transformación, escribes un único método genérico y le pasas el comportamiento que necesites en cada llamada.
</details>
