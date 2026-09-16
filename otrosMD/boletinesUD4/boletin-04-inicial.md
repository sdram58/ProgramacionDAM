---
title: Boletín U04 — Inicial
description: Ejercicios propuestos de nivel inicial sobre clases y objetos en Java
---

# 📝 Boletín U04 — Inicial

1. Diseña una clase `Libro` con atributos privados `titulo` (String), `autor` (String) y `paginas` (int), con sus getters y setters, y un constructor que reciba los tres valores.

2. Escribe una clase `Main` con un método `main` que cree dos objetos `Libro` distintos y muestre sus datos por pantalla usando los getters.

3. Añade a la clase `Libro` un método `esLargo()` que devuelva `true` si el libro tiene más de 300 páginas.

4. Escribe una clase `Rectangulo` con atributos `base` y `altura`, un constructor, y métodos `calcularArea()` y `calcularPerimetro()`.

5. ¿Qué diferencia hay entre un atributo `private` y uno `public`? ¿Por qué se recomienda declarar los atributos como `private`?

6. Añade a la clase `Libro` un constructor sin parámetros (además del que ya tenías) que cree un libro con valores por defecto ("Sin título", "Desconocido", 0). Pista: usa `this(...)` para reutilizar el otro constructor.

7. Explica qué imprime este código, y por qué:
   ```java
   Libro l1 = new Libro("Ficciones", "Borges", 200);
   Libro l2 = l1;
   l2.setTitulo("El Aleph");
   System.out.println(l1.getTitulo());
   ```

8. Añade a la clase `Rectangulo` un atributo `static` llamado `numeroDeRectangulos` que cuente cuántos objetos `Rectangulo` se han creado (increméntalo en el constructor), y muéstralo tras crear 3 rectángulos distintos.
