---
title: Boletín U05 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre herencia, clases abstractas e interfaces
---

# ✅ Boletín U05 — Inicial (resuelto)

1. **Vehiculo y Coche**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Vehiculo {
       protected String marca;
       public void arrancar() { System.out.println("El vehículo arranca"); }
   }

   class Coche extends Vehiculo {
       private int numPuertas;
   }

   // main:
   Coche c = new Coche();
   c.arrancar();           // heredado de Vehiculo
   ```
   </details>

2. **Herencia o composición**
   <details>
   <summary>💡 Solución</summary>

   - (a) `Biblioteca` y `Libro`: **composición** — una biblioteca *tiene* libros, no es un tipo de libro.
   - (b) `Gato` y `Mamifero`: **herencia** — un gato *es un* tipo de mamífero.
   - (c) `Ordenador` y `TarjetaGrafica`: **composición** — un ordenador *tiene* una tarjeta gráfica.
   - (d) `Cuadrado` y `Figura`: **herencia** — un cuadrado *es un* tipo de figura.
   </details>

3. **Constructores con super**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Vehiculo {
       protected String marca;
       public Vehiculo(String marca) { this.marca = marca; }
   }

   class Coche extends Vehiculo {
       private int numPuertas;
       public Coche(String marca, int numPuertas) {
           super(marca);
           this.numPuertas = numPuertas;
       }
   }
   ```
   </details>

4. **Sobrescritura y polimorfismo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Coche extends Vehiculo {
       @Override
       public void arrancar() { System.out.println("El coche arranca con la llave"); }
   }

   // main:
   Vehiculo[] vehiculos = { new Vehiculo(), new Coche() };
   for (Vehiculo v : vehiculos) {
       v.arrancar();   // imprime el mensaje distinto según el tipo REAL de cada objeto
   }
   ```
   </details>

5. **Empleado abstracto**
   <details>
   <summary>💡 Solución</summary>

   ```java
   abstract class Empleado {
       protected String nombre;
       public abstract double calcularNomina();
   }

   class EmpleadoFijo extends Empleado {
       private double salario;
       @Override
       public double calcularNomina() { return salario; }
   }

   class EmpleadoComercial extends Empleado {
       private double salarioBase, comision;
       @Override
       public double calcularNomina() { return salarioBase + comision; }
   }
   ```
   </details>

6. **Interface Imprimible**
   <details>
   <summary>💡 Solución</summary>

   ```java
   interface Imprimible {
       void imprimir();
   }

   class Factura implements Imprimible {
       public void imprimir() { System.out.println("Imprimiendo factura..."); }
   }

   class Etiqueta implements Imprimible {
       public void imprimir() { System.out.println("Imprimiendo etiqueta..."); }
   }
   ```
   </details>

7. **extends vs. implements**
   <details>
   <summary>💡 Solución</summary>

   `extends` establece una relación de **herencia** ("ES-UN"): la subclase hereda atributos y comportamiento ya implementado de su superclase, y solo puede extender una única clase. `implements` establece una relación de **capacidad** ("SABE HACER"): la clase se compromete a implementar los métodos de la interface, sin heredar ningún código ya hecho (solo la obligación de los métodos), y puede implementar varias interfaces a la vez.
   </details>

8. **Método final**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Constante {
       public final void metodo() { System.out.println("No se puede cambiar"); }
   }

   class Otra extends Constante {
       // public void metodo() { ... }  // ERROR DE COMPILACIÓN
   }
   ```

   El compilador da un error del estilo *"metodo() in Otra cannot override metodo() in Constante; overridden method is final"* — `final` en un método es precisamente la forma de **prohibir** que cualquier subclase lo redefina, y el compilador lo hace cumplir de forma estricta.
   </details>
