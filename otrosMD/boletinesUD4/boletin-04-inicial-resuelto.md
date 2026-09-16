---
title: Boletín U04 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre clases y objetos en Java
---

# ✅ Boletín U04 — Inicial (resuelto)

1. **Clase Libro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Libro {
       private String titulo;
       private String autor;
       private int paginas;

       public Libro(String titulo, String autor, int paginas) {
           this.titulo = titulo;
           this.autor = autor;
           this.paginas = paginas;
       }

       public String getTitulo() { return titulo; }
       public void setTitulo(String titulo) { this.titulo = titulo; }
       public String getAutor() { return autor; }
       public void setAutor(String autor) { this.autor = autor; }
       public int getPaginas() { return paginas; }
       public void setPaginas(int paginas) { this.paginas = paginas; }
   }
   ```
   </details>

2. **Dos objetos Libro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Main {
       public static void main(String[] args) {
           Libro l1 = new Libro("Ficciones", "Borges", 200);
           Libro l2 = new Libro("1984", "Orwell", 328);
           System.out.println(l1.getTitulo() + " - " + l1.getAutor());
           System.out.println(l2.getTitulo() + " - " + l2.getAutor());
       }
   }
   ```
   </details>

3. **esLargo()**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public boolean esLargo() {
       return paginas > 300;
   }
   ```
   </details>

4. **Rectangulo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Rectangulo {
       private double base;
       private double altura;

       public Rectangulo(double base, double altura) {
           this.base = base;
           this.altura = altura;
       }

       public double calcularArea() { return base * altura; }
       public double calcularPerimetro() { return 2 * (base + altura); }
   }
   ```
   </details>

5. **private vs. public**
   <details>
   <summary>💡 Solución</summary>

   Un atributo `private` solo puede leerse o modificarse desde dentro de su propia clase; uno `public` puede tocarse desde cualquier parte del programa. Se recomienda declarar los atributos como `private` (y exponer getters/setters en su lugar) para mantener el **encapsulamiento**: el objeto controla exactamente cómo y cuándo se puede modificar su propio estado — por ejemplo, un setter puede validar que un valor sea razonable antes de aceptarlo, cosa que un atributo público directamente accesible no permite.
   </details>

6. **Constructor sin parámetros con this(...)**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public Libro() {
       this("Sin título", "Desconocido", 0);
   }
   ```

   `this(...)` desde dentro de un constructor llama a **otro constructor de la misma clase** — evita repetir la misma lógica de inicialización dos veces.
   </details>

7. **Referencia compartida**
   <details>
   <summary>💡 Solución</summary>

   Imprime **"El Aleph"**. `l2 = l1` no crea una copia del libro: copia la referencia, así que `l1` y `l2` apuntan al mismo objeto en memoria. Cuando `l2.setTitulo(...)` modifica el objeto, el cambio se ve también a través de `l1`, porque en realidad solo existe un único objeto `Libro`.
   </details>

8. **Contador static**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Rectangulo {
       private double base, altura;
       public static int numeroDeRectangulos = 0;

       public Rectangulo(double base, double altura) {
           this.base = base;
           this.altura = altura;
           numeroDeRectangulos++;
       }
   }

   // en el main:
   new Rectangulo(2, 3);
   new Rectangulo(4, 5);
   new Rectangulo(1, 1);
   System.out.println(Rectangulo.numeroDeRectangulos);  // 3
   ```

   `numeroDeRectangulos` es compartido por **todos** los objetos de la clase (una única copia, no una por objeto) — se accede con el nombre de la clase, `Rectangulo.numeroDeRectangulos`, no con una instancia concreta.
   </details>
