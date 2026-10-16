---
title: "Boletín 4.1: Nivel Inicial — Ejercicios Prácticos"
description: "Ejercicios fundamentales de clases, atributos privados, constructores, getters y setters, la referencia this y miembros estáticos en Java."
draft: true
prev: false
next: false
sidebar:
  order: 1
  badge:
    text: "Inicial"
    variant: "note"
---

Bienvenido al boletín de nivel inicial de la **Unidad 4: Clases y objetos**.

Estos 12 ejercicios están diseñados para asentar los conceptos fundamentales de la Programación Orientada a Objetos (POO): definición de clases, encapsulación de atributos con visibilidad `private`, métodos accesores y mutadores con validaciones de invariantes, constructores canónicos y sobrecargados con delegación `this(...)`, y uso del modificador `static`.

:::tip[Consejo de Desarrollo con IntelliJ IDEA]
Crea un proyecto en IntelliJ IDEA y coloca cada clase en su propio archivo `.java`. Practica la generación automática de constructores, getters y setters mediante el atajo **`Alt + Insert`** (o clic derecho -> *Generate...*).
:::

---

### Ejercicio 1: La Clase `Libro` y Encapsulamiento Básico
**Enunciado:**  
Diseña una clase llamada `Libro` que contenga los atributos privados: `titulo` (String), `autor` (String) y `numeroPaginas` (int).  
- Define un constructor canónico que inicialice los tres atributos, validando que el título y el autor no sean nulos ni cadenas vacías, y que el número de páginas sea estrictamente mayor que 0 (lanzando `IllegalArgumentException` si no se cumplen).  
- Implementa todos los métodos getters y setters con las mismas validaciones.  
- Añade un método `public boolean esLargo()` que devuelva `true` si el libro supera las 300 páginas.  
- En una clase `Main`, instancia dos libros distintos y muestra sus datos y si son largos.

---

### Ejercicio 2: Geometría Básica con la Clase `Rectangulo`
**Enunciado:**  
Crea una clase `Rectangulo` con dos atributos de instancia privados: `base` y `altura` (ambos de tipo `double`).  
- El constructor debe rechazar valores menores o iguales a cero.  
- Implementa los métodos `public double calcularArea()` y `public double calcularPerimetro()`.  
- Implementa un método `public boolean esCuadrado()` que determine si la base y la altura son idénticas.  
- Añade un método `public void escalar(double factor)` que multiplique ambas dimensiones por un factor positivo.

---

### Ejercicio 3: La Clase `Circulo` y Constantes Matemáticas
**Enunciado:**  
Diseña una clase `Circulo` con un atributo privado `radio` (double).  
- El constructor debe asegurar que el radio sea positivo.  
- Implementa `calcularArea()` y `calcularPerimetro()` haciendo uso de la constante estática oficial `Math.PI`.  
- Proporciona un constructor por defecto sin parámetros que asigne un radio de `1.0` delegando en el constructor principal mediante `this(1.0)`.

---

### Ejercicio 4: Conversor Térmico con la Clase `Termometro`
**Enunciado:**  
Crea una clase `Termometro` que almacene internamente la temperatura en grados Celsius (`temperaturaCelsius` de tipo `double`).  
- El constructor debe verificar que la temperatura no sea inferior al cero absoluto (-273.15 °C).  
- Añade métodos para consultar la temperatura en escala Fahrenheit: `public double getFahrenheit()` y en escala Kelvin: `public double getKelvin()`.  
- Añade métodos `incrementar(double delta)` y `decrementar(double delta)`.

---

### Ejercicio 5: Coordenadas Cartesianas con la Clase `Punto2D`
**Enunciado:**  
Modela un punto en el plano bidimensional con la clase `Punto2D`:  
- Atributos privados: `x` e `y` (double).  
- Constructor por defecto que sitúe el punto en el origen `(0.0, 0.0)` mediante `this(0.0, 0.0)`.  
- Constructor parametrizado `public Punto2D(double x, double y)`.  
- Método `public void desplazar(double dx, double dy)` que traslade las coordenadas.  
- Método `public double distanciaAlOrigen()` que devuelva la distancia euclídea respecto al `(0,0)` (`√(x² + y²)`).

---

### Ejercicio 6: La Máquina Cafetera
**Enunciado:**  
Diseña una clase `Cafetera` para gestionar el servicio de café en una oficina:  
- Atributos privados: `capacidadMaxima` (c.c. de café, por defecto 1000) y `cantidadActual` (c.c. de café que contiene).  
- Constructor predeterminado: capacidad máxima 1000 c.c. y cafetera vacía (0 c.c.).  
- Constructor con capacidad máxima dada (inicialmente vacía).  
- Constructor con capacidad máxima y cantidad actual (si la cantidad actual supera la máxima, se ajusta al máximo).  
- Métodos:  
  - `llenarCafetera()`: llena la cafetera a su máxima capacidad.  
  - `servirTaza(int capacidadTaza)`: descuenta el café servido. Si no hay suficiente para llenar la taza, sirve lo que quede y deja la cafetera a 0. Devuelve la cantidad real servida.  
  - `vaciarCafetera()`: pone la cantidad actual a 0.  
  - `agregarCafe(int cantidad)`: añade café sin sobrepasar la capacidad máxima.

---

### Ejercicio 7: Cronómetro Simple con la Clase `Reloj`
**Enunciado:**  
Modela la clase `Reloj` con tres atributos enteros privados: `hora` (0-23), `minuto` (0-59) y `segundo` (0-59).  
- Constructor canónico con validación rigurosa de rangos.  
- Constructor sin argumentos que inicialice el reloj a las `00:00:00`.  
- Método `public void ticTac()` que adelante el reloj exactamente un segundo, gestionando adecuadamente los cambios de minuto, hora y el paso de medianoche (`23:59:59` -> `00:00:00`).  
- Método `public String getHoraFormateada()` que devuelva la hora en formato `HH:mm:ss` con dos dígitos por campo (p. ej. `"09:05:03"`).

---

### Ejercicio 8: Identificadores Autoincrementales con Miembros `static`
**Enunciado:**  
Diseña una clase `Empleado` con:  
- Un atributo estático privado `contadorSecuencial` inicializado en 1000.  
- Un atributo de instancia inmutable `final int idEmpleado`.  
- Atributos de instancia: `nombre` (String), `departamento` (String) y `sueldo` (double).  
- Cada vez que se cree un nuevo empleado, el constructor debe asignarle automáticamente el valor de `++contadorSecuencial` sin posibilidad de duplicidad.  
- Proporciona un método estático `public static int getTotalEmpleados()` que devuelva cuántos empleados se han contratado.

---

### Ejercicio 9: La Clase `Contador` y Encadenamiento con `this`
**Enunciado:**  
Implementa una clase `Contador` con un atributo entero privado `valor`.  
- Constructor sin parámetros: inicializa en 0.  
- Constructor con valor inicial.  
- Constructor de copia: `public Contador(Contador otro)` que replique el valor de otra instancia.  
- Métodos `incrementar()`, `decrementar()` e `incrementar(int cantidad)`.  
- Implementa los métodos devolviendo la referencia `this` para posibilitar llamadas fluidas encadenadas:  
  `contador.incrementar().incrementar(5).decrementar();`

---

### Ejercicio 10: Array de Objetos Alumnos
**Enunciado:**  
Utilizando la clase `Alumno` (con `nombre` y `nota` entre 0.0 y 10.0):  
- En el método `main`, declara e inicializa un array de 5 alumnos con nombres y calificaciones distintas.  
- Diseña un método estático `public static double calcularNotaMedia(Alumno[] lista)` que calcule la media del grupo.  
- Diseña un método estático `public static Alumno obtenerMejorAlumno(Alumno[] lista)` que localice y devuelva la referencia al alumno con la calificación más alta.

---

### Ejercicio 11: Control de Vehículo con la Clase `Coche`
**Enunciado:**  
Modela una clase `Coche` con los atributos: `marca`, `modelo`, `motorEncendido` (boolean) y `velocidadActual` (int, en km/h).  
- El constructor recibe marca y modelo; el coche arranca apagado y con velocidad 0.  
- Métodos:  
  - `arrancar()`: enciende el motor. No se puede arrancar si ya está encendido.  
  - `apagar()`: apaga el motor, solo si la velocidad es 0 (no se puede apagar en movimiento).  
  - `acelerar(int incremento)`: incrementa la velocidad si el motor está encendido, hasta un máximo de 200 km/h.  
  - `frenar(int decremento)`: reduce la velocidad sin bajar de 0 km/h.

---

### Ejercicio 12: Generador y Validador de Contraseñas
**Enunciado:**  
Crea la clase `Password`:  
- Atributos: `longitud` (por defecto 8) y `valor` (String con la contraseña).  
- Constructor por defecto: genera una contraseña aleatoria de 8 caracteres.  
- Constructor con longitud personalizada: genera una contraseña con la longitud indicada.  
- Método `public boolean esFuerte()`: devuelve `true` si la contraseña contiene al menos 2 mayúsculas, 1 minúscula y 2 dígitos numéricos.  
- Método `public void regenerar()`: genera un nuevo valor aleatorio manteniendo la longitud actual.

---

:::note[¿Necesitas consultar las soluciones?]
Revisa el código completo comentado en el [Boletín 4.1 Resuelto](./boletin-04-inicial-resuelto).
:::
