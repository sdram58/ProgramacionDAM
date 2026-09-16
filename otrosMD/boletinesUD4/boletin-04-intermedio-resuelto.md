---
title: Boletín U04 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre clases y objetos en Java
---

# ✅ Boletín U04 — Intermedio (resuelto)

1. **toString() de Libro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   @Override
   public String toString() {
       return titulo + ", de " + autor + " (" + paginas + " páginas)";
   }
   ```
   </details>

2. **equals() de Libro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   @Override
   public boolean equals(Object obj) {
       if (this == obj) return true;
       if (!(obj instanceof Libro)) return false;
       Libro otro = (Libro) obj;
       return titulo.equals(otro.titulo) && autor.equals(otro.autor);
   }

   // Pruebas:
   Libro a = new Libro("Ficciones", "Borges", 200);
   Libro b = new Libro("Ficciones", "Borges", 180);   // mismo título/autor, distintas páginas
   Libro c = new Libro("1984", "Orwell", 328);
   System.out.println(a.equals(b));  // true — mismo título y autor
   System.out.println(a.equals(c));  // false
   System.out.println(a.equals(a));  // true — reflexiva
   ```
   </details>

3. **Coordenada inmutable**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public final class Coordenada {
       private final double x;
       private final double y;

       public Coordenada(double x, double y) {
           this.x = x;
           this.y = y;
       }

       public double getX() { return x; }
       public double getY() { return y; }

       public double distanciaA(Coordenada otra) {
           double dx = x - otra.x;
           double dy = y - otra.y;
           return Math.sqrt(dx * dx + dy * dy);
       }
   }
   ```
   </details>

4. **ContadorGlobal Singleton**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class ContadorGlobal {
       private static ContadorGlobal instancia;
       private int valor = 0;

       private ContadorGlobal() {}

       public static ContadorGlobal getInstancia() {
           if (instancia == null) {
               instancia = new ContadorGlobal();
           }
           return instancia;
       }

       public void incrementar() { valor++; }
       public int getValor() { return valor; }
   }

   // en el main:
   ContadorGlobal c1 = ContadorGlobal.getInstancia();
   c1.incrementar();
   ContadorGlobal c2 = ContadorGlobal.getInstancia();
   c2.incrementar();
   System.out.println(c1.getValor());  // 2 — c1 y c2 son el mismo objeto
   ```
   </details>

5. **CuentaBancaria**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class CuentaBancaria {
       private String titular;
       private double saldo;

       public CuentaBancaria(String titular, double saldo) {
           this.titular = titular;
           this.saldo = saldo;
       }

       public void ingresar(double cantidad) {
           saldo += cantidad;
       }

       public void retirar(double cantidad) {
           if (cantidad > saldo) {
               throw new IllegalArgumentException("Saldo insuficiente");
           }
           saldo -= cantidad;
       }

       public double getSaldo() { return saldo; }
   }
   ```
   </details>

6. **Mochila con copia defensiva**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Mochila {
       private String[] objetos;

       public Mochila(String[] objetos) {
           this.objetos = objetos.clone();   // copia al entrar
       }

       public String[] getObjetos() {
           return objetos.clone();            // copia al salir
       }
   }
   ```

   Sin las dos copias, quien tuviera el array original (o el que devuelve el getter) podría añadir o quitar objetos de la mochila sin pasar por ningún método de la clase — rompiendo el control que se supone que tiene `Mochila` sobre su propio contenido.
   </details>

7. **Array de Circulos**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Circulo {
       private double radio;
       public Circulo(double radio) { this.radio = radio; }
       public double getArea() { return Math.PI * radio * radio; }
       public double getPerimetro() { return 2 * Math.PI * radio; }
   }

   static Circulo mayorArea(Circulo[] circulos) {
       Circulo mayor = circulos[0];
       for (Circulo c : circulos) {
           if (c.getArea() > mayor.getArea()) {
               mayor = c;
           }
       }
       return mayor;
   }
   ```
   </details>

8. **Por qué equals() y hashCode() van juntos**
   <details>
   <summary>💡 Solución</summary>

   Java exige que si dos objetos son "iguales" según `equals()`, deben tener el mismo `hashCode()`. Las colecciones basadas en tablas hash (como `HashMap` o `HashSet`, que verás en la unidad 6) usan `hashCode()` para decidir en qué "cajón" interno guardar cada objeto, y luego usan `equals()` dentro de ese cajón para confirmar si ya existe. Si sobrescribes `equals()` pero dejas el `hashCode()` heredado de `Object` (que se basa en la dirección de memoria), dos objetos que tú consideras iguales pueden acabar en cajones distintos — y una búsqueda que debería encontrar el objeto, no lo encuentra, aunque `equals()` diría que sí están ahí.
   </details>
