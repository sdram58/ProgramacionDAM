---
title: Boletín U05 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre polimorfismo, interfaces y lambdas
---

# ✅ Boletín U05 — Intermedio (resuelto)

1. **Jerarquía Figura**
   <details>
   <summary>💡 Solución</summary>

   ```java
   abstract class Figura {
       public abstract double calcularArea();
       public abstract double calcularPerimetro();
   }

   class Circulo extends Figura {
       private double radio;
       public Circulo(double radio) { this.radio = radio; }
       public double calcularArea() { return Math.PI * radio * radio; }
       public double calcularPerimetro() { return 2 * Math.PI * radio; }
   }

   class Rectangulo extends Figura {
       private double base, altura;
       public Rectangulo(double base, double altura) { this.base = base; this.altura = altura; }
       public double calcularArea() { return base * altura; }
       public double calcularPerimetro() { return 2 * (base + altura); }
   }

   // main:
   Figura[] figuras = { new Circulo(3), new Rectangulo(4, 5) };
   for (Figura f : figuras) {
       System.out.println(f.calcularArea());
   }
   ```
   </details>

2. **Mayor área**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static Figura mayorArea(Figura[] figuras) {
       Figura mayor = figuras[0];
       for (Figura f : figuras) {
           if (f.calcularArea() > mayor.calcularArea()) {
               mayor = f;
           }
       }
       return mayor;
   }
   ```

   El método no necesita saber de qué subclase concreta es cada figura — solo usa `calcularArea()`, y el polimorfismo se encarga de ejecutar la versión correcta en cada caso.
   </details>

3. **Comparador3D con lambda**
   <details>
   <summary>💡 Solución</summary>

   ```java
   interface Comparador3D<T> {
       int comparar(T a, T b);
   }

   Comparador3D<Circulo> porRadio = (c1, c2) -> Double.compare(c1.getRadio(), c2.getRadio());
   int resultado = porRadio.comparar(new Circulo(3), new Circulo(5));  // negativo: el primero es menor
   ```
   </details>

4. **CuentaBancaria abstracta**
   <details>
   <summary>💡 Solución</summary>

   ```java
   abstract class CuentaBancaria {
       protected double saldo;
       public abstract double calcularInteres();
   }

   class CuentaAhorro extends CuentaBancaria {
       public double calcularInteres() { return saldo * 0.02; }
   }

   class CuentaCorriente extends CuentaBancaria {
       public double calcularInteres() { return saldo * 0.005; }
   }
   ```
   </details>

5. **Contar con instanceof**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static int contarCirculos(Figura[] figuras) {
       int contador = 0;
       for (Figura f : figuras) {
           if (f instanceof Circulo) {
               contador++;
           }
       }
       return contador;
   }
   ```
   </details>

6. **removeIf con lambdas**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> nombres = new ArrayList<>(List.of("Ana", "Roberto", "Eva", "Marcos", "Ada", "Luis", "Elena", "Bea"));
   nombres.removeIf(n -> n.length() < 4);

   List<String> copia = new ArrayList<>(nombres);
   copia.removeIf(n -> !n.startsWith("A"));
   ```
   </details>

7. **Predicate, Function y Consumer**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Predicate<Integer> esPrimo = n -> {
       if (n < 2) return false;
       for (int i = 2; i * i <= n; i++) {
           if (n % i == 0) return false;
       }
       return true;
   };
   Function<Integer, Integer> alCuadrado = n -> n * n;
   Consumer<Integer> imprimir = n -> System.out.println("Número: " + n);

   int numero = 7;
   imprimir.accept(numero);
   System.out.println("¿Primo? " + esPrimo.test(numero));
   System.out.println("Al cuadrado: " + alCuadrado.apply(numero));
   ```
   </details>

8. **Upcasting vs. downcasting**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Animal a = new Perro();          // upcasting: siempre seguro, implícito
   Perro p = (Perro) a;              // downcasting: seguro aquí, porque a de verdad es un Perro

   Animal a2 = new Gato();
   Perro p2 = (Perro) a2;            // ¡ClassCastException en tiempo de ejecución! a2 no es un Perro
   ```

   El *upcasting* nunca puede fallar porque cualquier `Perro` es garantizadamente un `Animal`. El *downcasting* solo es seguro si el objeto real es (o desciende de) el tipo al que lo conviertes — si te equivocas, el programa compila sin problema pero **falla al ejecutarse**, lo cual es exactamente el motivo por el que conviene comprobar antes con `instanceof`.
   </details>
