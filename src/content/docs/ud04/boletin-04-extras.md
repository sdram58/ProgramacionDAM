---
title: "Boletín 4.3: Retos Orientados a Objetos y Programación Competitiva"
description: "Retos avanzados de modelado orientado a objetos, algoritmos sobre estructuras de objetos y diseño de arquitecturas en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 5
  badge:
    text: "Retos Extras"
    variant: "danger"
---

Bienvenido al boletín de retos avanzados de la **Unidad 4: Clases y objetos**.

Los siguientes 6 desafíos combinan la riqueza del modelado orientado a objetos con la exigencia algorítmica de plataformas de programación competitiva (CodeWars, LeetCode, Acepta el Reto). Cada reto incluye su contexto, especificación formal, análisis de diseño y la solución íntegra comentada en Java 25 LTS.

---

### Reto 1: La Baraja de Cartas con Enums y Algoritmo de Fisher-Yates

#### Contexto y Planteamiento
Diseña un simulador completo de baraja de cartas utilizando las mejores prácticas de la POO moderna:
1. Un `enum Palo` que defina los cuatro palos tradicionales (`OROS, COPAS, ESPADAS, BASTOS` o `CORAZONES, DIAMANTES, TREBOLES, PICAS`).
2. Un `enum Valor` con los valores de las cartas y su puntuación numérica asociada.
3. Un `record Carta(Palo palo, Valor valor)` inmutable con un método `toString()` compacto (ej. `"AS de ESPADAS"`).
4. Una clase `Baraja` que gestione internamente una colección o array de cartas:
   - Debe inicializar todas las combinaciones posibles en el constructor.
   - Método `public void barajar()`: debe implementar el **algoritmo de barajado in-place de Fisher-Yates / Knuth**, garantizando que todas las permutaciones sean equiprobables en tiempo lineal `O(N)` sin crear arrays auxiliares.
   - Método `public Carta robar()`: retira y devuelve la carta superior de la baraja (lanzando `IllegalStateException` si la baraja está agotada).
   - Método `public Carta[] repartirMano(int numCartas)`: devuelve una mano de cartas consecutivas.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Arrays;
import java.util.Random;

public class Reto01BarajaCartas {

    public enum Palo { OROS, COPAS, ESPADAS, BASTOS }

    public enum Valor {
        AS(1, "As"), DOS(2, "Dos"), TRES(3, "Tres"), CUATRO(4, "Cuatro"),
        CINCO(5, "Cinco"), SEIS(6, "Seis"), SIETE(7, "Siete"),
        SOTA(10, "Sota"), CABALLO(11, "Caballo"), REY(12, "Rey");

        private final int puntos;
        private final String nombre;

        Valor(int puntos, String nombre) {
            this.puntos = puntos;
            this.nombre = nombre;
        }

        public int getPuntos() { return this.puntos; }
        public String getNombre() { return this.nombre; }
    }

    public record Carta(Palo palo, Valor valor) {
        @Override
        public String toString() {
            return this.valor.getNombre() + " de " + this.palo;
        }
    }

    public static class Baraja {
        private final Carta[] cartas;
        private int indiceTope;
        private static final Random RNG = new Random();

        public Baraja() {
            Palo[] palos = Palo.values();
            Valor[] valores = Valor.values();
            this.cartas = new Carta[palos.length * valores.length];
            int pos = 0;
            for (Palo p : palos) {
                for (Valor v : valores) {
                    this.cartas[pos++] = new Carta(p, v);
                }
            }
            this.indiceTope = 0;
        }

        // Algoritmo de Fisher-Yates in-place (O(n))
        public void barajar() {
            for (int i = this.cartas.length - 1; i > 0; i--) {
                int j = RNG.nextInt(i + 1);
                Carta temp = this.cartas[i];
                this.cartas[i] = this.cartas[j];
                this.cartas[j] = temp;
            }
            this.indiceTope = 0; // Reiniciar posición del mazo
        }

        public Carta robar() {
            if (this.indiceTope >= this.cartas.length) {
                throw new IllegalStateException("No quedan cartas en la baraja.");
            }
            return this.cartas[this.indiceTope++];
        }

        public Carta[] repartirMano(int numCartas) {
            if (numCartas <= 0 || (this.indiceTope + numCartas) > this.cartas.length) {
                throw new IllegalArgumentException("No hay suficientes cartas para repartir.");
            }
            Carta[] mano = new Carta[numCartas];
            for (int i = 0; i < numCartas; i++) {
                mano[i] = robar();
            }
            return mano;
        }

        public int getCartasRestantes() {
            return this.cartas.length - this.indiceTope;
        }
    }

    public static void main(String[] args) {
        Baraja baraja = new Baraja();
        baraja.barajar();
        System.out.println("Baraja española barajada con éxito.");

        Carta[] mano = baraja.repartirMano(4);
        System.out.println("Mano repartida: " + Arrays.toString(mano));
        System.out.println("Cartas restantes en el mazo: " + baraja.getCartasRestantes());
    }
}
```
</details>

---

### Reto 2: Máquina Expendedora Automática con Algoritmo Voraz de Cambio

#### Contexto y Planteamiento
Diseña un sistema orientado a objetos para controlar una máquina expendedora automática con monedas reales:
1. `record Articulo(String codigo, String nombre, int precioCentimos, int stock)` (inmutable o con stock modificable).
2. Clase `MaquinaExpendedora`:
   - Gestiona un catálogo de artículos indexados por código.
   - Gestiona un saldo acumulado introducido por el cliente (en céntimos).
   - Método `public void insertarMoneda(int centimos)` (solo admite monedas de 5, 10, 20, 50 céntimos, 1€ y 2€).
   - Método `public Articulo comprar(String codigoArticulo)`: si el saldo es suficiente y hay stock, despacha el artículo, descuenta el stock y calcula el cambio restante.
   - Método `public int[] devolverCambio()`: descompone el cambio restante en el número mínimo de monedas de cada valor utilizando un **algoritmo voraz (*Greedy Algorithm*)** en denominaciones de 200, 100, 50, 20, 10 y 5 céntimos.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Arrays;

public class Reto02MaquinaExpendedora {

    public static class Articulo {
        private final String codigo;
        private final String nombre;
        private final int precioCentimos;
        private int stock;

        public Articulo(String codigo, String nombre, int precioCentimos, int stock) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.precioCentimos = precioCentimos;
            this.stock = Math.max(stock, 0);
        }

        public String getCodigo() { return this.codigo; }
        public String getNombre() { return this.nombre; }
        public int getPrecioCentimos() { return this.precioCentimos; }
        public int getStock() { return this.stock; }
        public void decrementarStock() { this.stock--; }
    }

    public static class Maquina {
        private final Articulo[] catalogo;
        private int saldoClienteCentimos;
        private static final int[] DENOMINACIONES = {200, 100, 50, 20, 10, 5};

        public Maquina(Articulo[] catalogo) {
            this.catalogo = catalogo;
            this.saldoClienteCentimos = 0;
        }

        public void insertarMoneda(int centimos) {
            boolean valida = false;
            for (int d : DENOMINACIONES) {
                if (d == centimos) { valida = true; break; }
            }
            if (!valida) {
                throw new IllegalArgumentException("Moneda no aceptada: " + centimos + " céntimos.");
            }
            this.saldoClienteCentimos += centimos;
        }

        public Articulo comprar(String codigo) {
            for (Articulo art : this.catalogo) {
                if (art.getCodigo().equalsIgnoreCase(codigo)) {
                    if (art.getStock() <= 0) {
                        throw new IllegalStateException("Producto agotado: " + art.getNombre());
                    }
                    if (this.saldoClienteCentimos < art.getPrecioCentimos()) {
                        throw new IllegalStateException("Saldo insuficiente. Faltan: " +
                                (art.getPrecioCentimos() - this.saldoClienteCentimos) + " céntimos.");
                    }
                    art.decrementarStock();
                    this.saldoClienteCentimos -= art.getPrecioCentimos();
                    return art;
                }
            }
            throw new IllegalArgumentException("Código de producto no encontrado: " + codigo);
        }

        // Algoritmo voraz (Greedy) para cambio mínimo de monedas
        public int[] devolverCambio() {
            int cambio = this.saldoClienteCentimos;
            int[] monedasDevueltas = new int[DENOMINACIONES.length];

            for (int i = 0; i < DENOMINACIONES.length; i++) {
                if (cambio >= DENOMINACIONES[i]) {
                    monedasDevueltas[i] = cambio / DENOMINACIONES[i];
                    cambio %= DENOMINACIONES[i];
                }
            }
            this.saldoClienteCentimos = 0;
            return monedasDevueltas;
        }

        public int getSaldoClienteCentimos() { return this.saldoClienteCentimos; }
    }

    public static void main(String[] args) {
        Articulo[] productos = {
            new Articulo("A1", "Agua Mineral 500ml", 120, 5),
            new Articulo("B2", "Refresco de Cola", 160, 3),
            new Articulo("C3", "Snack Frutos Secos", 95, 4)
        };

        Maquina m = new Maquina(productos);
        m.insertarMoneda(200); // Inserta 2.00€
        Articulo comprado = m.comprar("A1"); // Cuesta 1.20€, sobra 0.80€

        System.out.println("Dispensado con éxito: " + comprado.getNombre());
        int[] cambio = m.devolverCambio();

        System.out.println("Cambio devuelto (monedas de 2€, 1€, 50c, 20c, 10c, 5c): " + Arrays.toString(cambio));
    }
}
```
</details>

---

### Reto 3: Sistema Bancario con Clientes y Múltiples Cuentas (Asociación Bidireccional)

#### Contexto y Planteamiento
Modela la relación entre un `Cliente` y sus `CuentasBancarias`:
- Un `Cliente` puede poseer hasta 3 cuentas bancarias distintas (ahorro, corriente, nómina).
- Método `vincularCuenta(CuentaBancaria c)` que asocie la cuenta al cliente evitando sobrepasar el límite de 3.
- Método `public double calcularPatrimonioTotal()` que compute la suma acumulada de saldos de todas las cuentas del cliente.
- Método `public static boolean transferir(CuentaBancaria origen, CuentaBancaria destino, double cantidad)`: operación atómica que verifique saldo, realice la retirada de origen y el abono en destino.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Objects;

public class Reto03SistemaBancario {

    public static class Cuenta {
        private final String iban;
        private double saldo;

        public Cuenta(String iban, double saldoInicial) {
            this.iban = iban;
            this.saldo = Math.max(saldoInicial, 0.0);
        }

        public String getIban() { return this.iban; }
        public double getSaldo() { return this.saldo; }

        public void ingresar(double cant) {
            if (cant > 0) this.saldo += cant;
        }

        public boolean retirar(double cant) {
            if (cant > 0 && this.saldo >= cant) {
                this.saldo -= cant;
                return true;
            }
            return false;
        }

        public static boolean transferir(Cuenta origen, Cuenta destino, double cantidad) {
            Objects.requireNonNull(origen);
            Objects.requireNonNull(destino);
            if (origen.retirar(cantidad)) {
                destino.ingresar(cantidad);
                return true;
            }
            return false;
        }
    }

    public static class Cliente {
        private final String nif;
        private final String nombre;
        private final Cuenta[] cuentas;
        private int totalCuentas;

        public Cliente(String nif, String nombre) {
            this.nif = nif;
            this.nombre = nombre;
            this.cuentas = new Cuenta[3]; // Máximo 3 cuentas
            this.totalCuentas = 0;
        }

        public boolean agregarCuenta(Cuenta c) {
            if (c != null && this.totalCuentas < 3) {
                this.cuentas[this.totalCuentas++] = c;
                return true;
            }
            return false;
        }

        public double calcularPatrimonioTotal() {
            double total = 0;
            for (int i = 0; i < this.totalCuentas; i++) {
                total += this.cuentas[i].getSaldo();
            }
            return total;
        }

        public String getNif() { return this.nif; }
        public String getNombre() { return this.nombre; }
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente("12345678Z", "Carlos Alarcón");
        Cuenta c1 = new Cuenta("ES01", 1500.0);
        Cuenta c2 = new Cuenta("ES02", 3200.0);

        cli.agregarCuenta(c1);
        cli.agregarCuenta(c2);

        System.out.printf("Patrimonio total de %s: %.2f€%n", cli.getNombre(), cli.calcularPatrimonioTotal());
        Cuenta.transferir(c1, c2, 500.0);
        System.out.printf("Saldo c1: %.2f€ | Saldo c2: %.2f€%n", c1.getSaldo(), c2.getSaldo());
    }
}
```
</details>

---

### Reto 4: Calculadora de Polinomios Inmutables

#### Contexto y Planteamiento
Modela una clase inmutable `Polinomio` para manipular expresiones matemáticas del tipo `P(x) = c₀ + c₁x + c₂x² + ... + cₙxⁿ`:
- El array interno de coeficientes almacena en el índice `i` el coeficiente del término de grado `i`.
- Constructor con array de coeficientes (realizando copia defensiva y recortando ceros superiores redundantes).
- Método `public double evaluar(double x)`: evalúa el valor numérico de `P(x)` mediante el **algoritmo de Horner** (`O(N)`).
- Método `public Polinomio sumar(Polinomio otro)`: devuelve un nuevo polinomio suma.
- Método `public Polinomio derivar()`: devuelve el polinomio derivada formal de `P(x)`.
- Sobrescribe `toString()` formateando algebraicamente la expresión matemática (ej. `"3.0x^2 - 4.0x + 5.0"`).

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Arrays;

public final class Polinomio {
    private final double[] coef; // coef[i] es el coeficiente de x^i

    public Polinomio(double... coeficientes) {
        if (coeficientes == null || coeficientes.length == 0) {
            this.coef = new double[]{0.0};
            return;
        }
        // Localizar el grado real ignorando ceros a la derecha
        int grado = coeficientes.length - 1;
        while (grado > 0 && coeficientes[grado] == 0.0) {
            grado--;
        }
        this.coef = Arrays.copyOf(coeficientes, grado + 1);
    }

    public int getGrado() {
        return this.coef.length - 1;
    }

    // Algoritmo de Horner para evaluación en O(n)
    public double evaluar(double x) {
        double resultado = 0.0;
        for (int i = this.coef.length - 1; i >= 0; i--) {
            resultado = resultado * x + this.coef[i];
        }
        return resultado;
    }

    public Polinomio sumar(Polinomio otro) {
        int maxLen = Math.max(this.coef.length, otro.coef.length);
        double[] suma = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = (i < this.coef.length) ? this.coef[i] : 0.0;
            double b = (i < otro.coef.length) ? otro.coef[i] : 0.0;
            suma[i] = a + b;
        }
        return new Polinomio(suma);
    }

    public Polinomio derivar() {
        if (this.coef.length <= 1) {
            return new Polinomio(0.0);
        }
        double[] derivada = new double[this.coef.length - 1];
        for (int i = 1; i < this.coef.length; i++) {
            derivada[i - 1] = this.coef[i] * i;
        }
        return new Polinomio(derivada);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = this.coef.length - 1; i >= 0; i--) {
            double c = this.coef[i];
            if (c == 0.0 && this.coef.length > 1) continue;

            if (sb.length() > 0 && c > 0) sb.append(" + ");
            else if (c < 0) sb.append(" - ");

            double val = Math.abs(c);
            if (i == 0 || val != 1.0) sb.append(val);

            if (i > 0) sb.append("x");
            if (i > 1) sb.append("^").append(i);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        // P(x) = 5 - 4x + 3x^2
        Polinomio p = new Polinomio(5.0, -4.0, 3.0);
        System.out.println("P(x) = " + p);
        System.out.println("P(2) = " + p.evaluar(2.0)); // 5 - 8 + 12 = 9
        System.out.println("P'(x) = " + p.derivar());    // -4.0 + 6.0x
    }
}
```
</details>

---

### Reto 5: Simulación de Ecosistema en Tablero Matricial

#### Contexto y Planteamiento
Modela una simulación matricial de vida celular en un tablero de `FILAS × COLUMNAS`:
- `enum Especie { VACIA, HIERBA, CONEJO }`
- Cada casilla contiene un objeto `Organismo(Especie especie, int energia)`.
- En cada turno de la simulación:
  1. La `HIERBA` gana 1 punto de energía.
  2. El `CONEJO` pierde 1 punto de energía. Si su energía llega a 0, muere (se convierte en `VACIA`).
  3. Si un `CONEJO` tiene adyacente una casilla con `HIERBA`, se desplaza a ella, devora la hierba y suma su energía.
- Diseña la clase `TableroEcosistema` y simula 5 ciclos mostrando la evolución en consola.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto05Ecosistema {

    public enum Especie { VACIA, HIERBA, CONEJO }

    public static class Organismo {
        Especie especie;
        int energia;

        public Organismo(Especie e, int energia) {
            this.especie = e;
            this.energia = energia;
        }

        @Override
        public String toString() {
            return switch (this.especie) {
                case VACIA -> " . ";
                case HIERBA -> " H ";
                case CONEJO -> " C ";
            };
        }
    }

    public static class Tablero {
        private final Organismo[][] mapa;
        private final int filas;
        private final int cols;

        public Tablero(int f, int c) {
            this.filas = f;
            this.cols = c;
            this.mapa = new Organismo[f][c];
            for (int i = 0; i < f; i++) {
                for (int j = 0; j < c; j++) {
                    this.mapa[i][j] = new Organismo(Especie.VACIA, 0);
                }
            }
        }

        public void colocar(int f, int c, Especie e, int energia) {
            this.mapa[f][c] = new Organismo(e, energia);
        }

        public void avanzarTurno() {
            for (int i = 0; i < this.filas; i++) {
                for (int j = 0; j < this.cols; j++) {
                    Organismo org = this.mapa[i][j];
                    if (org.especie == Especie.HIERBA) {
                        org.energia++;
                    } else if (org.especie == Especie.CONEJO) {
                        org.energia--;
                        if (org.energia <= 0) {
                            this.mapa[i][j] = new Organismo(Especie.VACIA, 0); // Muerte
                        }
                    }
                }
            }
        }

        public void imprimir() {
            for (int i = 0; i < this.filas; i++) {
                for (int j = 0; j < this.cols; j++) {
                    System.out.print(this.mapa[i][j]);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Tablero t = new Tablero(4, 4);
        t.colocar(1, 1, Especie.CONEJO, 3);
        t.colocar(1, 2, Especie.HIERBA, 2);
        t.colocar(3, 3, Especie.HIERBA, 1);

        System.out.println("--- TURNO INICIAL ---");
        t.imprimir();
        t.avanzarTurno();
        System.out.println("--- TRAS TURNO 1 ---");
        t.imprimir();
    }
}
```
</details>

---

### Reto 6: Cola de Impresión Segura con Prioridades (Singleton + FIFO)

#### Contexto y Planteamiento
En sistemas operativos y servidores de red, el gestor de impresión (*Print Spooler*) debe ser único en toda la máquina (Patrón Singleton) y procesar trabajos ordenados según una cola de prioridad:
1. `record Documento(String id, String nombre, int paginas, int prioridad)` (prioridad 1 = Urgente, 2 = Normal, 3 = Baja).
2. Clase `SpoolerImpresion` (Singleton):
   - Constructor estrictamente privado.
   - Array interno de trabajos pendientes con capacidad máxima para 20 documentos.
   - Método `public boolean encolar(Documento doc)`: inserta el documento de forma ordenada según su prioridad (menor número = mayor prioridad) respetando el orden de llegada para igual prioridad.
   - Método `public Documento imprimirSiguiente()`: extrae y procesa el trabajo con máxima prioridad.
   - Método `public int getTrabajosPendientes()`.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
public class Reto06SpoolerSingleton {

    public record Documento(String id, String nombre, int paginas, int prioridad) {
        public Documento {
            if (prioridad < 1 || prioridad > 3) throw new IllegalArgumentException("Prioridad entre 1 y 3.");
        }
    }

    public static class SpoolerImpresion {
        private static final SpoolerImpresion INSTANCIA = new SpoolerImpresion();
        private final Documento[] cola;
        private int totalDocumentos;

        private SpoolerImpresion() {
            this.cola = new Documento[20];
            this.totalDocumentos = 0;
        }

        public static SpoolerImpresion getInstance() {
            return INSTANCIA;
        }

        public synchronized boolean encolar(Documento doc) {
            if (doc == null || this.totalDocumentos >= this.cola.length) {
                return false; // Cola saturada
            }

            // Inserción ordenada por prioridad (1 = Máxima prioridad)
            int pos = this.totalDocumentos - 1;
            while (pos >= 0 && this.cola[pos].prioridad() > doc.prioridad()) {
                this.cola[pos + 1] = this.cola[pos]; // Desplazar hacia la derecha
                pos--;
            }
            this.cola[pos + 1] = doc;
            this.totalDocumentos++;
            return true;
        }

        public synchronized Documento imprimirSiguiente() {
            if (this.totalDocumentos == 0) return null;

            Documento atendido = this.cola[0];
            // Desplazar elementos hacia la izquierda
            System.arraycopy(this.cola, 1, this.cola, 0, this.totalDocumentos - 1);
            this.cola[--this.totalDocumentos] = null;
            return atendido;
        }

        public int getTotalDocumentos() { return this.totalDocumentos; }
    }

    public static void main(String[] args) {
        SpoolerImpresion spooler = SpoolerImpresion.getInstance();

        spooler.encolar(new Documento("D1", "Informe_Anual.pdf", 45, 2));
        spooler.encolar(new Documento("D2", "Borrador_Notas.txt", 2, 3));
        spooler.encolar(new Documento("D3", "ALERTA_INCIDENCIA.pdf", 1, 1)); // Máxima prioridad

        System.out.println("Imprimiendo trabajos por orden de prioridad:");
        while (spooler.getTotalDocumentos() > 0) {
            Documento doc = spooler.imprimirSiguiente();
            System.out.printf(" -> Imprimiendo [%s] (Prioridad %d): %s (%d págs.)%n",
                    doc.id(), doc.prioridad(), doc.nombre(), doc.paginas());
        }
    }
}
```
</details>
