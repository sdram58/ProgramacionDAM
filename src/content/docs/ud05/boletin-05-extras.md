---
title: "Boletín 5.3: Retos Avanzados y Proyectos Integradores de POO"
description: "6 grandes retos y proyectos completos de arquitectura orientada a objetos: Análisis de Riesgos, Inventario de Mascotas, Logística de Almacenes, Clínica Veterinaria SOFTVETClinic y Jerarquías Selladas Funcionales en Java 25 LTS."
draft: true
prev: false
next: false
sidebar:
  order: 5
  badge:
    text: "Retos Extras"
    variant: "danger"
---

Bienvenido al boletín de retos avanzados y proyectos integradores de la **Unidad 5: Programación orientada a objetos avanzada**.

> 💡 **Entrenamiento adicional:** En [CodeWars](https://www.codewars.com/) busca katas de nivel **6 kyu y 5 kyu** con las etiquetas `Object-oriented Programming`, `Design Patterns` y `Functional Programming`, y en [Acepta el Reto](https://www.aceptaelreto.com/) problemas de clasificación con `Comparable` / `Comparator`. A continuación tienes **6 proyectos completos** con sus soluciones íntegras comentadas en Java 25 LTS.

---

### Reto 1: Sistema de Análisis de Riesgos (`Amenaza` Inmutable con *Wither-Setters* y Composición Segura)

#### Contexto y Planteamiento
En ciberseguridad (metodología MAGERIT), un sistema de análisis de riesgos modela **Activos** que sufren **Amenazas**, las cuales son mitigadas por **Salvaguardas**:
1. Diseña la clase **`Amenaza`** como una clase **100 % inmutable** (`final class`, atributos `private final String codigo`, `descripcion` y `double probabilidad`, `impacto` entre `0.0` y `10.0`).
   - **Requisito especial (*Inmutabilidad fluida*):** Mantén métodos `setProbabilidad(double)` y `setImpacto(double)` pero, en lugar de mutar el objeto actual, haz que **devuelvan una nueva instancia de `Amenaza`** con el cambio aplicado, dejando intacto el objeto original.
   - Sobrescribe `toString()`, `equals(Object)` y `hashCode()`.
2. Diseña la clase mutable **`Salvaguarda`** (`String nombre`, `double eficacia` entre `0.0` y `1.0`) e implementa en ella tanto un **constructor de copia** como el método **`clone()` (`Cloneable`)**.
3. Diseña la clase **`Activo`** (`String nombre`, `double valorEconomico`, `Amenaza amenazaPrincipal`, `Salvaguarda salvaguarda`):
   - Dado que `Amenaza` es inmutable, puede compartirse sin riesgo; pero dado que `Salvaguarda` es mutable, el constructor, el getter y el setter de `Activo` deben usar **copias defensivas** para garantizar una composición fuerte sin fugas de referencias.
   - Añade el método `calcularRiesgoResidual()` que calcule: `riesgoIntrinseco = valorEconomico * (probabilidad * impacto / 100.0)` reducido según la eficacia de la salvaguarda `riesgoIntrinseco * (1.0 - eficacia)`.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.Objects;

public class Reto01AnalisisRiesgos {

    // 1. Clase Amenaza: Inmutable con métodos modificadores que retornan nuevas instancias
    public static final class Amenaza {
        private final String codigo;
        private final String descripcion;
        private final double probabilidad; // 0.0 a 10.0
        private final double impacto;      // 0.0 a 10.0

        public Amenaza(String codigo, String descripcion, double probabilidad, double impacto) {
            this.codigo = Objects.requireNonNull(codigo);
            this.descripcion = Objects.requireNonNull(descripcion);
            this.probabilidad = Math.clamp(probabilidad, 0.0, 10.0);
            this.impacto = Math.clamp(impacto, 0.0, 10.0);
        }

        public String getCodigo() { return this.codigo; }
        public String getDescripcion() { return this.descripcion; }
        public double getProbabilidad() { return this.probabilidad; }
        public double getImpacto() { return this.impacto; }

        // Setters funcionales (Wither pattern): no mutan this, devuelven un nuevo objeto
        public Amenaza setProbabilidad(double nuevaProbabilidad) {
            return new Amenaza(this.codigo, this.descripcion, nuevaProbabilidad, this.impacto);
        }

        public Amenaza setImpacto(double nuevoImpacto) {
            return new Amenaza(this.codigo, this.descripcion, this.probabilidad, nuevoImpacto);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Amenaza amenaza = (Amenaza) o;
            return Double.compare(this.probabilidad, amenaza.probabilidad) == 0
                    && Double.compare(this.impacto, amenaza.impacto) == 0
                    && this.codigo.equals(amenaza.codigo)
                    && this.descripcion.equals(amenaza.descripcion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.codigo, this.descripcion, this.probabilidad, this.impacto);
        }

        @Override
        public String toString() {
            return String.format("Amenaza[%s: '%s', prob=%.1f, imp=%.1f]",
                    this.codigo, this.descripcion, this.probabilidad, this.impacto);
        }
    }

    // 2. Clase Salvaguarda: Mutable pero provista de Constructor de Copia y clone()
    public static class Salvaguarda implements Cloneable {
        private String nombre;
        private double eficacia; // 0.0 (0%) a 1.0 (100%)

        public Salvaguarda(String nombre, double eficacia) {
            this.nombre = nombre;
            this.eficacia = Math.clamp(eficacia, 0.0, 1.0);
        }

        // Constructor de copia
        public Salvaguarda(Salvaguarda original) {
            if (original == null) {
                throw new IllegalArgumentException("La salvaguarda a copiar no puede ser null.");
            }
            this.nombre = original.nombre;
            this.eficacia = original.eficacia;
        }

        public String getNombre() { return this.nombre; }
        public double getEficacia() { return this.eficacia; }
        public void setEficacia(double eficacia) { this.eficacia = Math.clamp(eficacia, 0.0, 1.0); }

        @Override
        public Salvaguarda clone() {
            try {
                return (Salvaguarda) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new IllegalStateException("Error al clonar Salvaguarda", e);
            }
        }

        @Override
        public String toString() {
            return String.format("Salvaguarda['%s', eficacia=%.0f%%]", this.nombre, this.eficacia * 100);
        }
    }

    // 3. Clase Activo: Compuesta de Amenaza (inmutable) y Salvaguarda (mutable protegida)
    public static class Activo {
        private String nombre;
        private double valorEconomico;
        private Amenaza amenaza;
        private Salvaguarda salvaguarda;

        public Activo(String nombre, double valorEconomico, Amenaza amenaza, Salvaguarda salvaguarda) {
            this.nombre = nombre;
            this.valorEconomico = Math.max(0.0, valorEconomico);
            this.amenaza = amenaza; // Inmutable: seguro asignar directamente
            this.salvaguarda = (salvaguarda != null) ? new Salvaguarda(salvaguarda) : null;
        }

        public Amenaza getAmenaza() { return this.amenaza; }
        public void setAmenaza(Amenaza amenaza) { this.amenaza = amenaza; }

        public Salvaguarda getSalvaguarda() {
            return (this.salvaguarda != null) ? this.salvaguarda.clone() : null;
        }

        public void setSalvaguarda(Salvaguarda salvaguarda) {
            this.salvaguarda = (salvaguarda != null) ? new Salvaguarda(salvaguarda) : null;
        }

        public double calcularRiesgoResidual() {
            if (this.amenaza == null) return 0.0;
            double severidad = (this.amenaza.getProbabilidad() * this.amenaza.getImpacto()) / 100.0;
            double riesgoIntrinseco = this.valorEconomico * severidad;
            double factorMitigacion = (this.salvaguarda != null) ? (1.0 - this.salvaguarda.getEficacia()) : 1.0;
            return riesgoIntrinseco * factorMitigacion;
        }

        @Override
        public String toString() {
            return String.format("Activo['%s' (%.2f€) | %s | %s -> Riesgo Residual: %.2f€]",
                    this.nombre, this.valorEconomico, this.amenaza,
                    this.salvaguarda, calcularRiesgoResidual());
        }
    }

    public static void main(String[] args) {
        Amenaza rans = new Amenaza("A-01", "Ransomware Cifrado", 8.0, 9.0);
        Salvaguarda backup = new Salvaguarda("Backups Inmutables Offline", 0.85);

        Activo servidorBD = new Activo("Cluster PostgreSQL", 50_000.0, rans, backup);
        System.out.println("Estado inicial:\n  " + servidorBD);

        // Intentamos alterar la salvaguarda externa (comprobando la copia defensiva):
        backup.setEficacia(0.0);
        System.out.println("Tras intentar sabotear backup externo a 0%:\n  " + servidorBD);

        // Evolucionamos la amenaza inmutable generando un nuevo objeto con setProbabilidad:
        Amenaza ransAgravado = rans.setProbabilidad(10.0);
        servidorBD.setAmenaza(ransAgravado);
        System.out.println("Tras actualizar amenaza inmutable a probabilidad 10.0:\n  " + servidorBD);
    }
}
```
</details>

---

### Reto 2: Inventario de Tienda de Mascotas con Jerarquía Multinivel (`Mascota`, `Ave`, `Perro`, `Gato`, `Loro`, `Canario`)

#### Contexto y Planteamiento
Implementa la arquitectura completa para gestionar el inventario de una tienda de mascotas:
1. **Jerarquía de clases:**
   - Clase abstracta **`Mascota`**: atributos `protected String nombre`, `protected int edad`, `protected String estado` y `protected String fechaNacimiento`. Métodos abstractos `public abstract void muestra()`, `public abstract void habla()` y método concreto `public void cumplirAnios()`.
   - Subclases directas **`Perro`** (`raza`, `boolean pulgas`) y **`Gato`** (`color`, `boolean peloLargo`).
   - Subclase intermedia abstracta **`Ave extends Mascota`**: añade `protected String pico` y `protected boolean vuela`, y declara el método abstracto `public abstract void volar()`.
   - Subclases de `Ave`: **`Loro`** (`origen`, `habla`) y **`Canario`** (`color`, `boolean canta`).
2. **Clase gestora `Inventario`:**
   - Almacena las mascotas en un `private final ArrayList<Mascota> animales`.
   - Métodos para: listar resumen (`getClass().getSimpleName() + ": " + getNombre()`), mostrar todos los datos de un animal concreto por índice, mostrar las fichas completas de todos los animales, insertar mascota, eliminar mascota y vaciar el inventario.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.ArrayList;

public class Reto02TiendaMascotas {

    public static abstract class Mascota {
        protected String nombre;
        protected int edad;
        protected String estado;
        protected String fechaNacimiento;

        public Mascota(String nombre, int edad, String estado, String fechaNacimiento) {
            this.nombre = nombre;
            this.edad = edad;
            this.estado = estado;
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getNombre() { return this.nombre; }
        public void cumplirAnios() { this.edad++; }
        public void morir() { this.estado = "Fallecido"; }

        public abstract void muestra();
        public abstract void habla();
    }

    public static class Perro extends Mascota {
        private String raza;
        private boolean pulgas;

        public Perro(String nombre, int edad, String estado, String fechaNac, String raza, boolean pulgas) {
            super(nombre, edad, estado, fechaNac);
            this.raza = raza;
            this.pulgas = pulgas;
        }

        @Override
        public void muestra() {
            System.out.printf("🐕 Perro[nombre=%s, edad=%d, estado=%s, nac=%s, raza=%s, pulgas=%b]%n",
                    this.nombre, this.edad, this.estado, this.fechaNacimiento, this.raza, this.pulgas);
        }

        @Override
        public void habla() {
            System.out.println(this.nombre + " dice: ¡Guau, guau!");
        }
    }

    public static class Gato extends Mascota {
        private String color;
        private boolean peloLargo;

        public Gato(String nombre, int edad, String estado, String fechaNac, String color, boolean peloLargo) {
            super(nombre, edad, estado, fechaNac);
            this.color = color;
            this.peloLargo = peloLargo;
        }

        @Override
        public void muestra() {
            System.out.printf("🐈 Gato[nombre=%s, edad=%d, estado=%s, nac=%s, color=%s, peloLargo=%b]%n",
                    this.nombre, this.edad, this.estado, this.fechaNacimiento, this.color, this.peloLargo);
        }

        @Override
        public void habla() {
            System.out.println(this.nombre + " dice: ¡Miau, prrrr!");
        }
    }

    public static abstract class Ave extends Mascota {
        protected String pico;
        protected boolean vuela;

        public Ave(String nombre, int edad, String estado, String fechaNac, String pico, boolean vuela) {
            super(nombre, edad, estado, fechaNac);
            this.pico = pico;
            this.vuela = vuela;
        }

        public abstract void volar();
    }

    public static class Loro extends Ave {
        private String origen;

        public Loro(String nombre, int edad, String estado, String fechaNac,
                    String pico, boolean vuela, String origen) {
            super(nombre, edad, estado, fechaNac, pico, vuela);
            this.origen = origen;
        }

        @Override
        public void muestra() {
            System.out.printf("🦜 Loro[nombre=%s, edad=%d, pico=%s, vuela=%b, origen=%s]%n",
                    this.nombre, this.edad, this.pico, this.vuela, this.origen);
        }

        @Override public void habla() { System.out.println(this.nombre + " dice: ¡Hola pirata, dame pipas!"); }
        @Override public void volar() { System.out.println(this.nombre + " planea por la tienda."); }
    }

    public static class Canario extends Ave {
        private String color;
        private boolean canta;

        public Canario(String nombre, int edad, String estado, String fechaNac,
                       String pico, boolean vuela, String color, boolean canta) {
            super(nombre, edad, estado, fechaNac, pico, vuela);
            this.color = color;
            this.canta = canta;
        }

        @Override
        public void muestra() {
            System.out.printf("🐤 Canario[nombre=%s, edad=%d, pico=%s, color=%s, canta=%b]%n",
                    this.nombre, this.edad, this.pico, this.color, this.canta);
        }

        @Override public void habla() { System.out.println(this.nombre + " trina: ¡Pío, pío, tralara!"); }
        @Override public void volar() { System.out.println(this.nombre + " revolotea de percha en percha."); }
    }

    public static class Inventario {
        private final ArrayList<Mascota> animales = new ArrayList<>();

        public void insertarAnimal(Mascota m) {
            if (m != null) this.animales.add(m);
        }

        public boolean eliminarAnimal(int indice) {
            if (indice >= 0 && indice < this.animales.size()) {
                this.animales.remove(indice);
                return true;
            }
            return false;
        }

        public void vaciarInventario() {
            this.animales.clear();
        }

        public void mostrarListaResumen() {
            System.out.println("=== RESUMEN DEL INVENTARIO ===");
            for (int i = 0; i < this.animales.size(); i++) {
                Mascota m = this.animales.get(i);
                System.out.printf(" [%d] %s -> %s%n", i, m.getClass().getSimpleName(), m.getNombre());
            }
        }

        public void mostrarTodosLosDatos() {
            System.out.println("=== FICHAS COMPLETAS DE MASCOTAS ===");
            for (Mascota m : this.animales) {
                m.muestra();
                m.habla();
            }
        }
    }

    public static void main(String[] args) {
        Inventario tienda = new Inventario();
        tienda.insertarAnimal(new Perro("Rex", 4, "Sano", "10/03/2022", "Pastor Alemán", false));
        tienda.insertarAnimal(new Gato("Mishi", 2, "Sano", "05/08/2024", "Blanco", true));
        tienda.insertarAnimal(new Loro("Paco", 12, "Sano", "01/01/2014", "Curvo", true, "Amazonas"));
        tienda.insertarAnimal(new Canario("Piolín", 1, "Sano", "15/04/2025", "Corto", true, "Amarillo", true));

        tienda.mostrarListaResumen();
        System.out.println();
        tienda.mostrarTodosLosDatos();
    }
}
```
</details>

---

### Reto 3: Red Logística de Almacenes y Traslado de Productos con Control de Capacidad

#### Contexto y Planteamiento
Desarrolla una aplicación logística basada en dos clases con relación de **agregación** (`Producto` y `Almacen`), dado que los productos son entidades independientes que pueden trasladarse de un almacén a otro:
1. **`Producto`:** atributos `idProducto` (`int`), `nombre` (`String`), `precio` (`double`) y `cantidad` (`int` — unidades del lote).
2. **`Almacen`:** atributos `idAlmacen` (`int`), `nombre` (`String`), `capacidadMaxima` (`int` — número máximo de unidades sumando las cantidades de todos sus productos) y un `ArrayList<Producto> productos`:
   - Método `public int getOcupacionActual()`: suma las cantidades de todos los productos alojados en el almacén.
   - Método `public boolean agregarProducto(Producto p)`: añade el producto únicamente si `getOcupacionActual() + p.getCantidad() <= capacidadMaxima`.
   - Método `public void subirPreciosPorcentaje(double porcentaje)`: incrementa el precio de todos los productos de ese almacén.
3. **Operaciones de Red Logística:**
   - Buscar un producto por su `nombre` en toda la red de almacenes e indicar exactamente en qué almacén se encuentra.
   - Método `public static boolean moverProducto(int idProd, ArrayList<Almacen> red, int idAlmacenDestino)`: localiza en qué almacén de la red se encuentra el producto `idProd`, verifica si el almacén destino tiene capacidad suficiente para acoger su `cantidad` y, en caso afirmativo, lo retira del almacén origen y lo ingresa en el almacén destino.

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.ArrayList;

public class Reto03LogisticaAlmacenes {

    public static class Producto {
        private final int idProducto;
        private String nombre;
        private double precio;
        private int cantidad;

        public Producto(int idProducto, String nombre, double precio, int cantidad) {
            this.idProducto = idProducto;
            this.nombre = nombre;
            this.precio = Math.max(0.0, precio);
            this.cantidad = Math.max(1, cantidad);
        }

        public int getIdProducto() { return this.idProducto; }
        public String getNombre() { return this.nombre; }
        public double getPrecio() { return this.precio; }
        public void setPrecio(double precio) { this.precio = Math.max(0.0, precio); }
        public int getCantidad() { return this.cantidad; }

        @Override
        public String toString() {
            return String.format("Prod[#%d '%s' | %.2f€ | %d uds]",
                    this.idProducto, this.nombre, this.precio, this.cantidad);
        }
    }

    public static class Almacen {
        private final int idAlmacen;
        private final String nombre;
        private final int capacidadMaxima;
        private final ArrayList<Producto> productos;

        public Almacen(int idAlmacen, String nombre, int capacidadMaxima) {
            this.idAlmacen = idAlmacen;
            this.nombre = nombre;
            this.capacidadMaxima = capacidadMaxima;
            this.productos = new ArrayList<>();
        }

        public int getIdAlmacen() { return this.idAlmacen; }
        public String getNombre() { return this.nombre; }
        public ArrayList<Producto> getProductos() { return this.productos; }

        public int getOcupacionActual() {
            int suma = 0;
            for (Producto p : this.productos) {
                suma += p.getCantidad();
            }
            return suma;
        }

        public boolean agregarProducto(Producto p) {
            if (p == null) return false;
            if (getOcupacionActual() + p.getCantidad() > this.capacidadMaxima) {
                System.out.printf("❌ Capacidad insuficiente en '%s' (Ocupado: %d/%d, Lote: %d)%n",
                        this.nombre, getOcupacionActual(), this.capacidadMaxima, p.getCantidad());
                return false;
            }
            this.productos.add(p);
            return true;
        }

        public void subirPreciosPorcentaje(double porcentaje) {
            for (Producto p : this.productos) {
                p.setPrecio(p.getPrecio() * (1.0 + porcentaje / 100.0));
            }
        }

        public void mostrarContenido() {
            System.out.printf("🏭 Almacén #%d '%s' [Ocupación: %d/%d uds]:%n",
                    this.idAlmacen, this.nombre, getOcupacionActual(), this.capacidadMaxima);
            if (this.productos.isEmpty()) {
                System.out.println("   (Vacío)");
            } else {
                for (Producto p : this.productos) {
                    System.out.println("   - " + p);
                }
            }
        }
    }

    public static void buscarProductoPorNombre(ArrayList<Almacen> red, String nombreBuscado) {
        for (Almacen alm : red) {
            for (Producto p : alm.getProductos()) {
                if (p.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    System.out.printf("🔍 '%s' localizado en el almacén '%s' (%s)%n",
                            nombreBuscado, alm.getNombre(), p);
                    return;
                }
            }
        }
        System.out.println("🔍 El producto '" + nombreBuscado + "' no existe en ningún almacén.");
    }

    public static boolean moverProducto(int idProd, ArrayList<Almacen> red, int idAlmacenDestino) {
        Almacen origen = null;
        Producto prodEncontrado = null;
        Almacen destino = null;

        for (Almacen alm : red) {
            if (alm.getIdAlmacen() == idAlmacenDestino) {
                destino = alm;
            }
            for (Producto p : alm.getProductos()) {
                if (p.getIdProducto() == idProd) {
                    origen = alm;
                    prodEncontrado = p;
                }
            }
        }

        if (prodEncontrado == null || destino == null || origen == destino) {
            return false;
        }

        if (destino.agregarProducto(prodEncontrado)) {
            origen.getProductos().remove(prodEncontrado);
            System.out.printf("✅ Traslado completado: '%s' movido de '%s' a '%s'.%n",
                    prodEncontrado.getNombre(), origen.getNombre(), destino.getNombre());
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Almacen almNorte = new Almacen(1, "Logística Norte (Sagunto)", 100);
        Almacen almSur   = new Almacen(2, "Logística Sur (Alicante)", 80);

        ArrayList<Almacen> red = new ArrayList<>();
        red.add(almNorte);
        red.add(almSur);

        almNorte.agregarProducto(new Producto(101, "Palet Placas Solares", 1200.0, 45));
        almNorte.agregarProducto(new Producto(102, "Inversor Híbrido", 650.0, 30));
        almSur.agregarProducto(new Producto(103, "Batería Litio", 900.0, 20));

        System.out.println("--- ESTADO ANTES DEL MOVIMIENTO ---");
        almNorte.mostrarContenido();
        almSur.mostrarContenido();

        System.out.println();
        buscarProductoPorNombre(red, "Inversor Híbrido");
        moverProducto(101, red, 2); // Mueve 45 uds a Sur (20 + 45 = 65 <= 80)
        moverProducto(102, red, 2); // Intenta mover 30 uds a Sur (65 + 30 = 95 > 80 -> Rechazado)

        System.out.println("\n--- ESTADO DESPUÉS DEL MOVIMIENTO ---");
        almNorte.mostrarContenido();
        almSur.mostrarContenido();
    }
}
```
</details>

---

### Reto 4: SOFTVETClinic — Sistema Integral de Gestión de Clínicas Veterinarias

#### Contexto y Planteamiento
Diseña el núcleo de dominio de **SOFTVETClinic**, un sistema de gestión de clínicas veterinarias con las siguientes reglas de negocio:
1. **Jerarquía de Personas:**
   - Clase abstracta `Persona(String dni, String nombre, String telefono)`.
   - Subclase `Cliente extends Persona`: posee una lista `ArrayList<Animal> mascotas`. **Regla crítica de integridad:** no se permite dar de baja a un cliente si todavía tiene animales asociados.
   - Subclase abstracta `Empleado extends Persona` (`double salario`) → subclases `Veterinario` (`String numColegiado`, `String especialidad`) y `Auxiliar` (`String turno`).
2. **Animales, Actuaciones Clínicas y Facturación:**
   - `Animal(String chip, String nombre, String especie, Cliente propietario)`.
   - `Actuacion(int id, Animal animal, Veterinario veterinario, LocalDate fecha, double coste, EstadoActuacion estado)` donde `enum EstadoActuacion { INICIADA, EN_PROCESO, FINALIZADA }`. Incluye `finalizarActuacion()`.
   - `Factura(int numFactura, Actuacion actuacion, boolean pagada)`: **solo puede generarse si la actuación figura en estado `FINALIZADA`** (en caso contrario lanza `IllegalStateException`).
   - Método en la clínica `calcularFacturacionMensual(int mes, int anio)`: suma el importe de todas las facturas emitidas en ese mes concreto (`1..12`).

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.time.LocalDate;
import java.util.ArrayList;

public class Reto04SoftVetClinic {

    public enum EstadoActuacion { INICIADA, EN_PROCESO, FINALIZADA }

    public static abstract class Persona {
        protected final String dni;
        protected String nombre;

        public Persona(String dni, String nombre) {
            this.dni = dni;
            this.nombre = nombre;
        }

        public String getDni() { return this.dni; }
        public String getNombre() { return this.nombre; }
    }

    public static class Cliente extends Persona {
        private final ArrayList<Animal> animales = new ArrayList<>();

        public Cliente(String dni, String nombre) {
            super(dni, nombre);
        }

        public ArrayList<Animal> getAnimales() { return this.animales; }

        @Override
        public String toString() {
            return String.format("Cliente[%s - %s | Mascotas registradas: %d]",
                    this.dni, this.nombre, this.animales.size());
        }
    }

    public static class Veterinario extends Persona {
        private final String numColegiado;

        public Veterinario(String dni, String nombre, String numColegiado) {
            super(dni, nombre);
            this.numColegiado = numColegiado;
        }

        @Override
        public String toString() {
            return String.format("Vet[%s (Col. %s)]", this.nombre, this.numColegiado);
        }
    }

    public static class Animal {
        private final String nombre;
        private final String especie;
        private final Cliente propietario;

        public Animal(String nombre, String especie, Cliente propietario) {
            this.nombre = nombre;
            this.especie = especie;
            this.propietario = propietario;
        }

        public String getNombre() { return this.nombre; }
        public Cliente getPropietario() { return this.propietario; }

        @Override
        public String toString() {
            return String.format("Animal['%s' (%s) -> Dueño: %s]",
                    this.nombre, this.especie, this.propietario.getNombre());
        }
    }

    public static class Actuacion {
        private final int id;
        private final Animal animal;
        private final Veterinario veterinario;
        private final LocalDate fecha;
        private final double importe;
        private EstadoActuacion estado;

        public Actuacion(int id, Animal animal, Veterinario vet, LocalDate fecha, double importe) {
            this.id = id;
            this.animal = animal;
            this.veterinario = vet;
            this.fecha = fecha;
            this.importe = importe;
            this.estado = EstadoActuacion.INICIADA;
        }

        public int getId() { return this.id; }
        public LocalDate getFecha() { return this.fecha; }
        public double getImporte() { return this.importe; }
        public EstadoActuacion getEstado() { return this.estado; }

        public void finalizarActuacion() {
            this.estado = EstadoActuacion.FINALIZADA;
        }

        @Override
        public String toString() {
            return String.format("Actuación[#%d | %s | %s | Fecha: %s | %.2f€ | Estado: %s]",
                    this.id, this.animal.getNombre(), this.veterinario, this.fecha, this.importe, this.estado);
        }
    }

    public static class Factura {
        private final int idFactura;
        private final Actuacion actuacion;
        private boolean pagada;

        public Factura(int idFactura, Actuacion actuacion) {
            if (actuacion.getEstado() != EstadoActuacion.FINALIZADA) {
                throw new IllegalStateException(
                        "No se puede facturar la actuación #" + actuacion.getId() + " porque no está FINALIZADA.");
            }
            this.idFactura = idFactura;
            this.actuacion = actuacion;
            this.pagada = false;
        }

        public Actuacion getActuacion() { return this.actuacion; }
        public void pagarFactura() { this.pagada = true; }

        @Override
        public String toString() {
            return String.format("Factura[#%d | Actuación #%d (%s) | Importe: %.2f€ | Pagada: %b]",
                    this.idFactura, this.actuacion.getId(), this.actuacion.getFecha(),
                    this.actuacion.getImporte(), this.pagada);
        }
    }

    public static class ClinicaVeterinaria {
        private final ArrayList<Cliente> clientes = new ArrayList<>();
        private final ArrayList<Animal> animales = new ArrayList<>();
        private final ArrayList<Factura> facturas = new ArrayList<>();

        public boolean altaCliente(Cliente nuevo) {
            for (Cliente c : this.clientes) {
                if (c.getNombre().equalsIgnoreCase(nuevo.getNombre())) return false; // Evita duplicados
            }
            return this.clientes.add(nuevo);
        }

        public boolean bajaClientePorNombre(String nombre) {
            for (int i = 0; i < this.clientes.size(); i++) {
                Cliente c = this.clientes.get(i);
                if (c.getNombre().equalsIgnoreCase(nombre)) {
                    if (!c.getAnimales().isEmpty()) {
                        System.out.println("❌ No se puede dar de baja a " + nombre
                                + ": aún tiene " + c.getAnimales().size() + " mascota(s) asociada(s).");
                        return false;
                    }
                    this.clientes.remove(i);
                    return true;
                }
            }
            return false;
        }

        public boolean altaAnimal(Animal a) {
            for (Animal ex : this.animales) {
                if (ex.getNombre().equalsIgnoreCase(a.getNombre())) return false;
            }
            a.getPropietario().getAnimales().add(a);
            return this.animales.add(a);
        }

        public void registrarFactura(Factura f) {
            this.facturas.add(f);
        }

        public double obtenerFacturacionMensual(int mes) {
            double total = 0.0;
            for (Factura f : this.facturas) {
                if (f.getActuacion().getFecha().getMonthValue() == mes) {
                    total += f.getActuacion().getImporte();
                }
            }
            return total;
        }
    }

    public static void main(String[] args) {
        ClinicaVeterinaria clinica = new ClinicaVeterinaria();
        Cliente cli = new Cliente("12345678A", "Marta Soler");
        Veterinario vet = new Veterinario("87654321B", "Dr. Hugo Roca", "COL-4699");

        clinica.altaCliente(cli);
        Animal kira = new Animal("Kira", "Perro Labrador", cli);
        clinica.altaAnimal(kira);

        // Intento de baja de cliente con mascota asociada (debe ser bloqueado):
        clinica.bajaClientePorNombre("Marta Soler");

        // Actuación clínica y facturación:
        Actuacion act1 = new Actuacion(1, kira, vet, LocalDate.of(2026, 3, 15), 85.50);
        act1.finalizarActuacion();
        Factura fac1 = new Factura(1001, act1);
        fac1.pagarFactura();
        clinica.registrarFactura(fac1);

        System.out.println(fac1);
        System.out.printf("Facturación del mes 3 (Marzo): %.2f €%n", clinica.obtenerFacturacionMensual(3));
    }
}
```
</details>

---

### Reto 5: Arquitectura de Aeronaves, Vuelos y Motor de Escenarios 2D

#### Contexto y Planteamiento
Resuelve dos problemas clásicos de decisión arquitectónica entre herencia e interfaces:
1. **Dominio Aeronáutico:**
   - Todas las aeronaves pueden `despegar()` y `aterrizar()` (interfaz `Volable` o clase abstracta `Aeronave`).
   - Existen aeronaves civiles (`AvionCivil`, con `maxPasajeros` y que operan en `Aeropuerto(nombre, pais, ciudad)`) y aeronaves militares (`AvionMilitar`, con lista de `armamento` y que operan en `BaseMilitar(nombre)`).
   - Un `Vuelo` asocia una aeronave que despega de una instalación de origen y aterriza en una de destino durante un tiempo medido en segundos.
2. **Motor de Videojuego (`Escenario`):**
   - Necesitamos una clase `Escenario` que herede de una clase gráfica `Component` (que le proporciona el método `paint()`) y que además ofrezca a cualquier videojuego el contrato `insertaPersonaje(Personaje p)`. Justifica por qué `Jugable` debe ser una **interfaz** (dado que `Escenario` ya gasta su única herencia con `extends Component`).

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.ArrayList;
import java.util.List;

public class Reto05AeronavesYVideojuego {

    // 1. Dominio Aeronáutico
    public static abstract class InstalacionAerea {
        protected final String nombre;
        public InstalacionAerea(String nombre) { this.nombre = nombre; }
        public String getNombre() { return this.nombre; }
    }

    public static class Aeropuerto extends InstalacionAerea {
        private final String ciudad;
        private final String pais;

        public Aeropuerto(String nombre, String ciudad, String pais) {
            super(nombre);
            this.ciudad = ciudad;
            this.pais = pais;
        }
    }

    public static class BaseMilitar extends InstalacionAerea {
        public BaseMilitar(String nombre) { super(nombre); }
    }

    public static abstract class Aeronave<I extends InstalacionAerea> {
        protected final String codigo;

        public Aeronave(String codigo) { this.codigo = codigo; }

        public void despegar(I origen) {
            System.out.println("🛫 Aeronave " + this.codigo + " despega desde " + origen.getNombre());
        }

        public void aterrizar(I destino) {
            System.out.println("🛬 Aeronave " + this.codigo + " aterriza en " + destino.getNombre());
        }
    }

    public static class AvionCivil extends Aeronave<Aeropuerto> {
        private final int maxPasajeros;

        public AvionCivil(String codigo, int maxPasajeros) {
            super(codigo);
            this.maxPasajeros = maxPasajeros;
        }
    }

    public static class AvionMilitar extends Aeronave<BaseMilitar> {
        private final List<String> armamento;

        public AvionMilitar(String codigo, List<String> armamento) {
            super(codigo);
            this.armamento = new ArrayList<>(armamento);
        }
    }

    public static class Vuelo<I extends InstalacionAerea> {
        private final Aeronave<I> aeronave;
        private final I origen;
        private final I destino;
        private final long duracionSegundos;

        public Vuelo(Aeronave<I> aeronave, I origen, I destino, long duracionSegundos) {
            this.aeronave = aeronave;
            this.origen = origen;
            this.destino = destino;
            this.duracionSegundos = duracionSegundos;
        }

        public void ejecutarVuelo() {
            this.aeronave.despegar(this.origen);
            System.out.println("   ⏱️ Volando durante " + this.duracionSegundos + " segundos...");
            this.aeronave.aterrizar(this.destino);
        }
    }

    // 2. Arquitectura del Escenario de Videojuego
    // Como Escenario ya debe heredar de Component (extends Component), agrupamos
    // insertaPersonaje() en una INTERFAZ ContenedorPersonajes para no chocar con la herencia simple.
    public record Personaje(String nombre, int x, int y) {}

    public static class Component {
        public void paint() {
            System.out.println("[Component] Renderizando lienzo base...");
        }
    }

    public interface ContenedorPersonajes {
        void insertaPersonaje(Personaje p);
    }

    public static class Escenario extends Component implements ContenedorPersonajes {
        private final List<Personaje> personajes = new ArrayList<>();

        @Override
        public void insertaPersonaje(Personaje p) {
            this.personajes.add(p);
        }

        @Override
        public void paint() {
            super.paint();
            for (Personaje p : this.personajes) {
                System.out.println("  🎮 Dibujando a " + p.nombre() + " en (" + p.x() + ", " + p.y() + ")");
            }
        }
    }

    public static void main(String[] args) {
        Aeropuerto vlc = new Aeropuerto("Manises", "Valencia", "España");
        Aeropuerto mad = new Aeropuerto("Barajas", "Madrid", "España");
        AvionCivil iberia = new AvionCivil("IB-8900", 180);

        Vuelo<Aeropuerto> vueloComercial = new Vuelo<>(iberia, vlc, mad, 2700);
        vueloComercial.ejecutarVuelo();

        System.out.println();
        Escenario nivel1 = new Escenario();
        nivel1.insertaPersonaje(new Personaje("Caballero", 10, 20));
        nivel1.insertaPersonaje(new Personaje("Dragón", 80, 50));
        nivel1.paint();
    }
}
```
</details>

---

### Reto 6: Pasarela Financiera con Jerarquías Selladas (`sealed`), *Pattern Matching* y *Currying* Funcional

#### Contexto y Planteamiento
Diseña una pasarela de pagos de última generación utilizando las capacidades más avanzadas de **Java 25 LTS**:
1. Declara una interfaz sellada **`sealed interface MedioPago permits PagoTarjeta, TransferenciaSEPA, PagoCripto`** implementada por tres `records` inmutables.
2. Implementa una función **currificada** `Function<Double, DoubleUnaryOperator> generadorComision = porcentaje -> (importe -> importe * (porcentaje / 100.0))` para preconfigurar calculadoras de comisión según el canal de pago.
3. Procesa una lista de pagos utilizando un **`switch` exhaustivo con Pattern Matching de tipos y guardas `when`** (sin necesidad de `default` gracias a que la interfaz es `sealed`).

<details>
<summary>💡 Ver Solución Comentada</summary>

```java
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class Reto06PasarelaSelladaFuncional {

    // 1. Jerarquía cerrada con sealed interface y records (Java 25 LTS)
    public sealed interface MedioPago permits PagoTarjeta, TransferenciaSEPA, PagoCripto {
        double importe();
    }

    public record PagoTarjeta(String ultimos4Digitos, double importe, boolean internacional) implements MedioPago {}
    public record TransferenciaSEPA(String ibanDestino, double importe, boolean inmediata) implements MedioPago {}
    public record PagoCripto(String redBlockchain, double importe) implements MedioPago {}

    public static void main(String[] args) {
        // 2. Currying funcional: Fijamos el porcentaje y obtenemos operadores especializados
        Function<Double, DoubleUnaryOperator> generadorComision =
                porcentaje -> (importe -> importe * (porcentaje / 100.0));

        DoubleUnaryOperator comisionTarjetaNac  = generadorComision.apply(0.5); // 0.5%
        DoubleUnaryOperator comisionTarjetaInt  = generadorComision.apply(2.5); // 2.5%
        DoubleUnaryOperator comisionCripto      = generadorComision.apply(1.2); // 1.2%

        List<MedioPago> transacciones = List.of(
            new PagoTarjeta("4591", 200.0, false),
            new PagoTarjeta("8832", 500.0, true),
            new TransferenciaSEPA("ES9121000418450200051332", 1500.0, false),
            new TransferenciaSEPA("ES9121000418450200051332", 300.0, true),
            new PagoCripto("Ethereum-L2", 1000.0)
        );

        System.out.println("=== LIQUIDACIÓN POLIMÓRFICA Y FUNCIONAL DE PAGOS ===");
        for (MedioPago pago : transacciones) {
            // 3. Pattern Matching en switch sobre jerarquía sellada (no requiere default)
            double comision = switch (pago) {
                case PagoTarjeta t when t.internacional() -> comisionTarjetaInt.applyAsDouble(t.importe());
                case PagoTarjeta t                        -> comisionTarjetaNac.applyAsDouble(t.importe());
                case TransferenciaSEPA s when s.inmediata() -> 1.50; // Tarifa plana inmediata
                case TransferenciaSEPA s                    -> 0.00; // SEPA estándar gratuita
                case PagoCripto c                           -> comisionCripto.applyAsDouble(c.importe());
            };

            System.out.printf(" -> %-58s | Comisión: %5.2f € | Total: %7.2f €%n",
                    pago, comision, pago.importe() + comision);
        }
    }
}
```
</details>
