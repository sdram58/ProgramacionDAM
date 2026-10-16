---
title: 🏗️ Unidad 4. Clases y objetos
description: Fundamentos de la programación orientada a objetos (POO), abstracción, encapsulamiento, instanciación en el Heap, constructores, atributos y métodos de instancia, la referencia this, modificadores de acceso, miembros static, la clase Object (toString, equals, hashCode), records inmutables, enums y depuración con IntelliJ IDEA.
draft: true
prev: false
next: false
sidebar:
  order: 4
  badge:
    text: "En revisión"
    variant: "caution"
---

Hasta este punto de tu formación como programador, has aprendido a estructurar el flujo de ejecución con sentencias condicionales, bucles iterativos, captura de excepciones y funciones modulares (`public static`). Sin embargo, en el paradigma estructurado tradicional los datos (variables y arrays) y las funciones que los manipulan viven completamente separados. En proyectos de software medianos o grandes, esta separación provoca que el código se vuelva frágil, propenso a inconsistencias y difícil de mantener.

La **Programación Orientada a Objetos (POO)** supone una auténtica revolución conceptual en la ingeniería del software: propone modelar los programas informáticos no como secuencias lineales de instrucciones, sino como **sistemas colaborativos de entidades inteligentes denominadas objetos**, que encapsulan tanto sus **datos internos (estado)** como las **operaciones que pueden realizar (comportamiento)**.

En esta unidad nos adentraremos en el corazón de la POO utilizando **Java 25 LTS** (con compatibilidad plena con Java 21 LTS) y **IntelliJ IDEA** como nuestro entorno de desarrollo profesional.

---

## 1. El Paradigma de la Programación Orientada a Objetos (POO)

### 1.1 De la Programación Estructurada a los Objetos

Imagina que desarrollamos una aplicación bancaria con el enfoque estructurado de la Unidad 3. Declararíamos variables independientes:

```java
// Enfoque estructurado (separación artificial entre datos y lógica)
String[] titulares = {"Laura Gómez", "Carlos Ruíz"};
String[] iban = {"ES211234...", "ES984321..."};
double[] saldos = {1450.50, 320.00};
boolean[] bloqueadas = {false, true};

// Método externo que debe recibir y coordinar todos los arrays sueltos
public static void transferir(int origenIdx, int destinoIdx, double cantidad) {
    if (!bloqueadas[origenIdx] && saldos[origenIdx] >= cantidad) {
        saldos[origenIdx] -= cantidad;
        saldos[destinoIdx] += cantidad;
    }
}
```

Este enfoque presenta debilidades críticas:
1. **Desincronización de datos:** Si se elimina o reordena un índice en `titulares`, todos los arrays paralelos pueden quedar desalineados.
2. **Falta de integridad:** Cualquier parte del código puede acceder directamente a `saldos[origenIdx] = -50000;` corrompiendo las reglas del negocio financiero sin ningún tipo de control o validación.
3. **Escalabilidad nula:** Añadir una nueva propiedad (como el límite de crédito o el tipo de cuenta) exige modificar decenas de métodos y parámetros en toda la aplicación.

La **POO** resuelve este problema agrupando los datos y las funciones que operan sobre ellos en una única unidad lógica coherente: **la clase `CuentaBancaria`**.

---

### 1.2 Los Cuatro Pilares Fundamentales de la POO

El paradigma orientado a objetos se sustenta sobre cuatro principios universales de diseño:

```
+-------------------------------------------------------------------------+
|                  LOS CUATRO PILARES DE LA ORIENTACIÓN A OBJETOS        |
+-------------------+--------------------+------------------+-------------+
|    ABSTRACCIÓN    |   ENCAPSULAMIENTO  |     HERENCIA     | POLIMORFISMO|
| Aislar lo esencial| Ocultar el estado  | Reutilizar y     | Tratar a    |
| ignorando detalles| interno y exponer  | extender clases  | diferentes  |
| irrelevantes para | una interfaz       | base jerárquicas | objetos bajo|
| el problema.      | pública segura.    | (se ve en UD5).  | una interfaz|
+-------------------+--------------------+------------------+-------------+
```

1. **Abstracción:** Capacidad de identificar las características y comportamientos esenciales de una entidad del mundo real que resultan pertinentes para nuestro sistema, descartando los detalles superfluos. Para una clínica veterinaria, un perro tiene `nombre`, `peso`, `raza` y `fechaVacunacion`; el color de sus ojos o su juguete favorito suelen ser irrelevantes.
2. **Encapsulamiento (*Information Hiding*):** Mecanismo que empaqueta los atributos y métodos dentro de una clase y restringe el acceso directo al estado interno desde el exterior. El objeto actúa como una cápsula protectora: solo se puede interactuar con él a través de métodos públicos controlados.
3. **Herencia (introducción):** Mecanismo de extensión que permite a una clase nueva (subclase) heredar atributos y métodos de una clase preexistente (superclase), promoviendo la reutilización del código (se profundizará en la Unidad 5).
4. **Polimorfismo (introducción):** Capacidad que permite enviar un mismo mensaje (invocar un método) a objetos de diferentes clases y que cada uno responda de forma específica a su propia naturaleza (se profundizará en la Unidad 5).

---

### 1.3 Diferencia Fundamental: Clase vs. Objeto

La distinción entre clase y objeto es el concepto más trascendental que debes dominar en esta etapa:

| Concepto | Definición Analógica | Naturaleza en Java |
| :--- | :--- | :--- |
| **Clase (*Class*)** | Es el **plano arquitectónico**, la **receta** o el **molde**. Define qué atributos y qué métodos tendrán los elementos creados con ella, pero no ocupa memoria para datos reales. | Código fuente (`.java`), compilado en `.class` y cargado en el *Metaspace*. |
| **Objeto (*Object / Instance*)** | Es la **casa construida**, el **pastel horneado** a partir de la receta. Es una entidad concreta y viva con valores específicos para sus atributos. | Instancia física creada con el operador `new` alojada en la memoria **Heap**. |

#### La Tríada del Objeto: Estado, Comportamiento e Identidad

Todo objeto en tiempo de ejecución se caracteriza por tres elementos:
1. **Estado:** Conjunto de valores almacenados en sus variables o atributos de instancia en un instante determinado.
2. **Comportamiento:** Conjunto de métodos y operaciones que el objeto puede ejecutar, capaces de consultar o alterar su estado interno.
3. **Identidad:** Propiedad intrínseca que distingue a un objeto de todos los demás en la memoria, incluso si dos objetos contienen exactamente los mismos valores en todos sus atributos (su dirección física de memoria o referencia en el Heap es única).

---

### 1.4 Nociones Básicas de Modelado UML: El Diagrama de Clases

El **Lenguaje Unificado de Modelado (UML - *Unified Modeling Language*)** es el estándar internacional de la industria para representar gráficamente la estructura y relaciones de un sistema software antes de codificarlo.

En UML, una clase se representa mediante un rectángulo dividido en tres compartimentos horizontales:

```
+-------------------------------------------------------------+
|                      CuentaBancaria                         | <-- 1. Nombre de la clase
+-------------------------------------------------------------+
| - titular: String                                           | <-- 2. Atributos
| - iban: String                                              |    (visibilidad, nombre, tipo)
| - saldo: double                                             |
| - bloqueada: boolean                                        |
+-------------------------------------------------------------+
| + ingresar(cantidad: double): void                          | <-- 3. Métodos
| + retirar(cantidad: double): boolean                        |    (visibilidad, nombre, parámetros, retorno)
| + getSaldo(): double                                        |
| + estaBloqueada(): boolean                                  |
+-------------------------------------------------------------+
```

#### Símbolos Universales de Visibilidad en UML:
- **`-` (Privado / `private`):** Accesible exclusivamente desde dentro del código de la propia clase.
- **`+` (Público / `public`):** Accesible libremente desde cualquier clase del proyecto.
- **`#` (Protegido / `protected`):** Accesible dentro del mismo paquete y por subclases hereditarias.
- **`~` (Paquete / *package-private*):** Accesible únicamente por clases situadas dentro del mismo paquete (sin modificador en Java).

---

## 2. Definición de Clases en Java

### 2.1 Anatomía Canónica de una Clase

En Java, cada clase pública debe declararse en su propio fichero con el mismo nombre y extensión `.java` (por ejemplo, `CuentaBancaria.java`):

```java
package es.ies.dam.banco; // Declaración del paquete de pertenencia

public class CuentaBancaria {

    // 1. ATRIBUTOS DE INSTANCIA (Estado interno)
    private String titular;
    private String iban;
    private double saldo;
    private boolean bloqueada;

    // 2. MÉTODOS CONSTRUCTORES (Inicialización)
    public CuentaBancaria(String titular, String iban) {
        this.titular = titular;
        this.iban = iban;
        this.saldo = 0.0;
        this.bloqueada = false;
    }

    // 3. MÉTODOS DE INSTANCIA (Comportamiento)
    public void ingresar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser positiva: " + cantidad);
        }
        if (this.bloqueada) {
            throw new IllegalStateException("Operación denegada: la cuenta está bloqueada.");
        }
        this.saldo += cantidad;
    }

    public boolean retirar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a retirar debe ser positiva: " + cantidad);
        }
        if (this.bloqueada || this.saldo < cantidad) {
            return false; // Retirada rechazada
        }
        this.saldo -= cantidad;
        return true; // Retirada completada
    }

    // 4. MÉTODOS ACCESORES (Getters)
    public double getSaldo() {
        return this.saldo;
    }

    public String getTitular() {
        return this.titular;
    }

    public boolean isBloqueada() {
        return this.bloqueada;
    }
}
```

---

### 2.2 Atributos de Instancia y Valores por Defecto en el Heap

Cuando se declara un atributo a nivel de clase (sin el modificador `static`), cada objeto instanciado tendrá su propia copia independiente de esa variable en el Heap.

A diferencia de las variables locales dentro de un método (que deben inicializarse explícitamente antes de leerse o el compilador arrojará un error), **los atributos de instancia se inicializan automáticamente por la JVM** al reservar memoria con `new`:

| Tipo de Atributo | Valor Inicial por Defecto |
| :--- | :--- |
| `byte`, `short`, `int`, `long` | `0` |
| `float`, `double` | `0.0` |
| `boolean` | `false` |
| `char` | `'\u0000'` (carácter nulo) |
| Referencias a Objetos (`String`, arrays, envoltorios) | `null` |

---

### 2.3 Métodos de Instancia y la Palabra Reservada `this`

Un **método de instancia** es una función que opera en el contexto de un objeto concreto. Dentro de cualquier método no estático de una clase, Java proporciona automáticamente una variable implícita especial: **`this`**.

`this` es una referencia que **apunta directamente a la instancia actual del objeto que está ejecutando el método**.

#### Usos Principales de `this`:

1. **Desambiguación de nombres (Sombreamiento de variables o *Shadowing*):**  
   Cuando un parámetro formal o una variable local tiene exactamente el mismo nombre que un atributo de instancia, el identificador local oculta al atributo. Se utiliza `this.` para indicar inequívocamente al compilador que nos referimos al campo del objeto:
   ```java
   public void setTitular(String titular) {
       this.titular = titular; // this.titular = Atributo; titular = Parámetro recibido
   }
   ```
2. **Invocación encadenada de constructores (`this(...)`):**  
   Permite a un constructor llamar a otro constructor de la misma clase para evitar duplicación de código de inicialización (analizado en profundidad en la Sección 4).
3. **Paso del propio objeto como argumento:**  
   Si un objeto necesita pasarse a sí mismo como argumento a otro método o registrarse en un servicio:
   ```java
   gestorNotificaciones.registrarCuenta(this);
   ```
4. **Retorno de la propia instancia para interfaces fluidas (*Method Chaining*):**  
   Al hacer que un método devuelva `this`, es posible encadenar múltiples llamadas seguidas en una única sentencia:
   ```java
   public CuentaBancaria asignarLimite(double limite) {
       this.limiteCredito = limite;
       return this; // Permite: cuenta.asignarLimite(500).ingresar(100);
   }
   ```

---

## 3. Control de Visibilidad y Encapsulamiento (*Information Hiding*)

El **encapsulamiento** es el escudo protector que garantiza la integridad del software. Consiste en declarar los atributos como **`private`** para que ningún código exterior pueda manipularlos directamente de forma arbitraria, y proporcionar métodos públicos (**`getters`** y **`setters`**) con lógica de validación.

### 3.1 La Tabla Maestra de Modificadores de Acceso en Java

Java ofrece cuatro niveles de visibilidad para clases, atributos y métodos:

| Modificador | Misma Clase | Mismo Paquete | Subclases (otro paquete) | Todo el Proyecto |
| :--- | :---: | :---: | :---: | :---: |
| **`private`** | **SÍ** | No | No | No |
| *(default / package-private)* | **SÍ** | **SÍ** | No | No |
| **`protected`** | **SÍ** | **SÍ** | **SÍ** (vía herencia) | No |
| **`public`** | **SÍ** | **SÍ** | **SÍ** | **SÍ** |

:::caution[El Modificador por Defecto (Package-Private)]
Si no antepones ninguna palabra clave de visibilidad antes de un atributo o método (por ejemplo `double saldo;`), Java no lo hace privado ni público: aplica el nivel **por defecto o package-private**, permitiendo que cualquier otra clase situada dentro de la misma carpeta de paquete pueda leerlo y modificarlo directamente. En POO profesional, **declara siempre explícitamente `private` tus atributos**.
:::

---

### 3.2 Métodos Accesores (*Getters*) y Mutadores (*Setters*)

Para permitir la lectura o actualización segura de atributos privados, la convención estándar de Java (*JavaBeans standard*) define:

- **Getters (Lectura):** Prefijo `get` seguido del nombre del atributo con la primera letra en mayúscula (ej. `getTitular()`). En variables booleanas es común usar el prefijo `is` (ej. `isActivo()`, `isBloqueada()`).
- **Setters (Escritura controlada):** Prefijo `set` seguido del nombre del atributo. Deben incorporar validaciones de invariantes para proteger al objeto frente a estados corruptos.

```java
public class Alumno {
    private String nombre;
    private double nota; // Invariante: debe estar entre 0.0 y 10.0

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del alumno no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        if (nota < 0.0 || nota > 10.0) {
            throw new IllegalArgumentException("Nota no válida (" + nota + "). Debe estar en el rango [0.0, 10.0].");
        }
        this.nota = nota;
    }
}
```

:::tip[Generación Automática en IntelliJ IDEA]
No redactes manualmente cada getter y setter. En IntelliJ IDEA, coloca el cursor dentro de la clase y presiona **`Alt + Insert`** (o haz clic derecho y selecciona *Generate...*). Elige **Getter and Setter**, selecciona los atributos que deseas encapsular y el IDE generará el código de forma instantánea.
:::

---

### 3.3 Atributos Inmutables con la Palabra Reservada `final`

Cuando un atributo de instancia se declara con la palabra clave **`final`**, solo se le puede asignar valor una única vez (ya sea en su propia declaración o en el constructor de la clase). Tras esa asignación inicial, su valor permanece inalterable durante toda la vida útil del objeto:

```java
import java.util.Objects;

public class Empleado {
    private final String dni; // Inmutable: el DNI nunca cambia tras instanciarse
    private String departamento;
    private double sueldoBase;

    public Empleado(String dni, String departamento, double sueldoBase) {
        this.dni = Objects.requireNonNull(dni, "El DNI no puede ser nulo");
        this.departamento = departamento;
        this.sueldoBase = sueldoBase;
    }

    public String getDni() {
        return this.dni; // Proporciona getter pero NO setter
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
```

## 4. Ciclo de Vida de los Objetos: Instanciación, Constructores y Destrucción

Un objeto en Java no nace por generación espontánea ni permanece para siempre en la memoria. Atraviesa un ciclo de vida bien definido: **nacimiento (instanciación con constructores)**, **vida activa (invocación de métodos y modificación de estado)** y **muerte (pérdida de referencias y reciclaje por el Garbage Collector)**.

---

### 4.1 El Operador `new` y la Reserva de Memoria en el Heap

Cuando el compilador y la JVM se encuentran con una sentencia como:

```java
CuentaBancaria miCuenta = new CuentaBancaria("Sofía Morales", "ES9121000418450200051332");
```

La JVM ejecuta internamente una secuencia de cuatro pasos atómicos:
1. **Reserva física de espacio:** Se solicita un bloque contiguo de bytes en la memoria **Heap** con capacidad suficiente para albergar todos los atributos de instancia declarados en la clase (y los heredados de sus superclases).
2. **Inicialización por defecto:** Todas las variables del objeto recién reservado se inicializan a sus valores neutros o por defecto (`0`, `0.0`, `false`, `null`).
3. **Ejecución del constructor:** Se invoca el método constructor correspondiente, pasando los argumentos facilitados. En este paso se ejecutan las asignaciones iniciales y las validaciones de negocio.
4. **Devolución de la referencia:** El operador `new` devuelve la dirección física de memoria donde reside el objeto en el Heap (por ejemplo, `0x7b4a2f`), la cual queda almacenada en la variable local de la pila **Stack** (`miCuenta`).

```
      MEMORIA STACK                               MEMORIA HEAP
+------------------------+             +------------------------------------+
| Variable: miCuenta     | ----------> | Objeto CuentaBancaria (0x7b4a2f)   |
| (Dirección 0x7b4a2f)   |             | - titular: "Sofía Morales"         |
+------------------------+             | - iban: "ES91210004..."            |
                                       | - saldo: 0.0                       |
                                       | - bloqueada: false                 |
                                       +------------------------------------+
```

---

### 4.2 Anatomía y Propósito de los Constructores

Un **constructor** es un método especial cuya única misión es inicializar un objeto en un estado coherente, válido y listo para operar desde el mismo instante en que se reserva su memoria.

#### Reglas Sintácticas Inquebrantables de los Constructores:
1. Su nombre debe coincidir **exactamente con el nombre de la clase** (respetando estrictamente mayúsculas y minúsculas).
2. **No tiene ningún tipo de retorno**, ni siquiera `void`. *(Si colocas `public void CuentaBancaria()`, Java lo interpretará como un método ordinario con un nombre desafortunado, no como un constructor)*.
3. Se invoca única y exclusivamente mediante la palabra reservada **`new`** en el momento de crear el objeto.

---

### 4.3 El Constructor por Defecto Implícito (*Default Constructor*)

Si escribes una clase y **no declaras ningún constructor explícito**, el compilador de Java inserta automáticamente en el bytecode un constructor público sin parámetros y con cuerpo vacío:

```java
public class Sensor {
    private double temperatura;
    // El compilador añade automáticamente: public Sensor() {}
}
```

:::danger[La Desaparición del Constructor por Defecto]
En el momento exacto en que declaras **cualquier constructor propio con parámetros**, el compilador de Java **retira de inmediato el constructor por defecto sin parámetros**.

Si tras definir `public Sensor(double tempInicial)` intentas hacer `Sensor s = new Sensor();`, el código fallará en compilación con el error: `constructor Sensor in class Sensor cannot be applied to given types`. Si necesitas ambos, debes declarar explícitamente el constructor sin argumentos.
:::

---

### 4.4 Sobrecarga de Constructores y Delegación con `this(...)`

Al igual que los métodos ordinarios, los constructores admiten **sobrecarga**: podemos proporcionar múltiples constructores con diferente número o tipo de parámetros para permitir distintas formas de inicializar un objeto.

Para evitar la duplicación de código de inicialización y validación entre constructores, Java permite que un constructor invoque a otro de su misma clase utilizando la sintaxis **`this(argumentos)`**:

```java
public class Producto {
    private final String codigo;
    private String descripcion;
    private double precio;
    private int stock;

    // 1. Constructor Principal (Canónico): recibe todos los datos y valida
    public Producto(String codigo, String descripcion, double precio, int stock) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de producto es obligatorio.");
        }
        if (precio < 0 || stock < 0) {
            throw new IllegalArgumentException("El precio y el stock no pueden ser negativos.");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.descripcion = (descripcion != null) ? descripcion.trim() : "Sin descripción";
        this.precio = precio;
        this.stock = stock;
    }

    // 2. Constructor Secundario: precio dado, stock inicial por defecto a 0
    public Producto(String codigo, String descripcion, double precio) {
        this(codigo, descripcion, precio, 0); // Delega en el constructor principal
    }

    // 3. Constructor Mínimo: producto recién catalogado con precio 0.0 y stock 0
    public Producto(String codigo, String descripcion) {
        this(codigo, descripcion, 0.0, 0); // Delega en el constructor principal
    }
}
```

:::caution[Regla de Oro de `this(...)`]
La llamada delegada a otro constructor mediante `this(...)` debe ser **estrictamente la primera sentencia ejecutable** del cuerpo del constructor. Colocar cualquier otra instrucción antes de `this(...)` provocará un error de compilación inmediato (`call to this must be first statement in constructor`).
:::

---

### 4.5 Bloques de Inicialización de Instancia

Además de los constructores, Java permite definir **bloques de inicialización de instancia**: fragmentos de código delimitados por llaves `{ ... }` directamente en el cuerpo de la clase (fuera de cualquier método).

El código contenido en estos bloques se copia e inserta automáticamente al principio de cada constructor generado. Su uso es poco frecuente, pero resulta útil para inicializaciones complejas compartidas por constructores anónimos:

```java
public class DispositivoRed {
    private final String idUnico;

    // Bloque de inicialización de instancia: se ejecuta antes de cualquier constructor
    {
        this.idUnico = java.util.UUID.randomUUID().toString();
    }

    public DispositivoRed() {
        // idUnico ya ha sido generado
    }
}
```

---

### 4.6 Destrucción de Objetos y el Recolector de Basura (*Garbage Collector*)

En lenguajes de bajo nivel como C o C++, el programador debe liberar manualmente la memoria con `free()` o `delete`. Un descuido produce fugas de memoria (*memory leaks*) que saturan el sistema.

Java utiliza un modelo de gestión automática de memoria mediante el **Garbage Collector (GC)**, un hilo de fondo de la JVM que monitoriza constantemente el Heap.

#### ¿Cuándo es un Objeto Elegible para Destrucción?
Un objeto se convierte en candidato a ser eliminado cuando **deja de ser alcanzable por cualquier hilo de ejecución activo** (es decir, ya no existe ninguna variable de referencia en el Stack que apunte a él, directa o indirectamente):

```java
public static void demostracionMemoria() {
    CuentaBancaria c1 = new CuentaBancaria("Juan", "ES111");
    CuentaBancaria c2 = new CuentaBancaria("Ana", "ES222");

    c1 = c2; 
    // Ahora c1 apunta al objeto de Ana.
    // El objeto original de Juan ("ES111") ha quedado HUÉRFANO en el Heap:
    // Ya ninguna variable apunta a él. Es candidato inmediato para el Garbage Collector.

    c2 = null; 
    // El objeto de Ana sigue vivo porque c1 aún lo referencia.
}
// Al salir del método demostracionMemoria(), c1 desaparece del Stack.
// Ahora el objeto de Ana también queda huérfano y será reclamado por el GC.
```

:::warning[El Método finalize() está Prohibido en Java Moderno]
Históricamente, la clase `Object` incluía un método `finalize()` que se ejecutaba supuestamente antes de que el GC destruyera el objeto. Sin embargo, su comportamiento impredecible, bloqueos de hilos y graves problemas de seguridad hicieron que Oracle lo declarase **obsoleto desde Java 9 y marcado para eliminación definitiva**.

Para liberar recursos del sistema (archivos abiertos, conexiones a bases de datos, sockets de red), la práctica oficial y obligatoria es implementar la interfaz `java.lang.AutoCloseable` y utilizar la estructura **`try-with-resources`** vista en la Unidad 3.
:::

---

## 5. Miembros Estáticos: La Palabra Reservada `static`

Hasta ahora, todos los atributos y métodos estudiados pertenecían a **instancias individuales**: cada objeto tenía su propio `saldo` y su propio `titular`.

Sin embargo, en multitud de escenarios necesitamos elementos que no pertenezcan a ningún objeto concreto, sino a **la clase en su conjunto**. Para este propósito, Java proporciona el modificador **`static`**.

---

### 5.1 Variables Estáticas (Atributos de Clase)

Una **variable estática** es un campo declarado con `static`. Existe **una única copia física** de esa variable en toda la JVM (alojada en la zona de memoria *Metaspace*), compartida solidariamente por todos los objetos de esa clase:

```java
public class Empleado {
    // 1. Atributo estático: compartido por todos los empleados
    private static int contadorTotalEmpleados = 0;

    // 2. Atributos de instancia: propios de cada empleado individual
    private final int idEmpleado;
    private String nombre;

    public Empleado(String nombre) {
        this.nombre = nombre;
        // Incrementamos el contador global y asignamos un ID secuencial automático
        contadorTotalEmpleados++;
        this.idEmpleado = contadorTotalEmpleados;
    }

    public static int getContadorTotalEmpleados() {
        return contadorTotalEmpleados;
    }

    public int getIdEmpleado() {
        return this.idEmpleado;
    }
}
```

Si instanciamos tres objetos:
```java
Empleado e1 = new Empleado("Marina"); // id: 1
Empleado e2 = new Empleado("Roberto"); // id: 2
Empleado e3 = new Empleado("Elena");   // id: 3

System.out.println(Empleado.getContadorTotalEmpleados()); // Imprime: 3
```

#### Constantes Globales (`public static final`)
La combinación de modificadores más famosa de Java se utiliza para definir constantes públicas universales:
- `public`: Accesible desde cualquier lugar del proyecto.
- `static`: Existe una única copia compartida a nivel de clase (no requiere instanciar objetos).
- `final`: Su valor es inmutable y no puede reasignarse.

Por convención estándar (*Java Naming Conventions*), las constantes se escriben siempre en **`MAYÚSCULAS_CON_GUIONES_BAJOS`**:

```java
public class ConfiguracionFinanciera {
    public static final double TIPO_IVA_GENERAL = 21.0;
    public static final double TIPO_IVA_REDUCIDO = 10.0;
    public static final int DIAS_PLAZO_DEVOLUCION = 30;
    public static final String MONEDA_DEFECTO = "EUR";
}
```

---

### 5.2 Métodos Estáticos (Métodos de Clase)

Un **método estático** es una función asociada a la clase, no a ningún objeto. Se invoca directamente utilizando el nombre de la clase, sin necesidad de crear ninguna instancia con `new`:

```java
double hipotenusa = Math.sqrt(Math.pow(3, 2) + Math.pow(4, 2)); // Métodos estáticos de java.lang.Math
```

#### La Gran Restricción de los Métodos Estáticos:
Puesto que un método estático no se ejecuta en el contexto de ningún objeto en el Heap:
1. **NO existe la referencia `this`** dentro de un método estático.
2. **NO puede acceder directamente a atributos o métodos de instancia**. Intentar leer un campo no estático desde un método estático generará el clásico error de compilación: `non-static variable saldo cannot be referenced from a static context`.

```java
public class UtilidadesConversor {

    // Método estático de utilidad pura: depende únicamente de sus parámetros
    public static double celsiusAFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    public static double fahrenheitACelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }
}
```

---

### 5.3 Bloques de Inicialización Estática

Si una clase necesita inicializar atributos estáticos mediante lógica compleja (lectura de ficheros de configuración, bucles de carga o captura de excepciones), se emplean los **bloques de inicialización estática**:

```java
public class PaletaColores {
    public static final java.util.Map<String, String> COLORES_HEX = new java.util.HashMap<>();

    // Se ejecuta una sola vez cuando la JVM carga la clase en memoria por primera vez
    static {
        COLORES_HEX.put("ROJO", "#FF0000");
        COLORES_HEX.put("VERDE", "#00FF00");
        COLORES_HEX.put("AZUL", "#0000FF");
    }
}
```

---

## 6. La Clase Raíz del Universo Java: `java.lang.Object`

En la jerarquía de tipos de Java, **toda clase desciende directa o indirectamente de la clase universal `java.lang.Object`**. Si no especificas la cláusula `extends` al declarar una clase, el compilador añade automáticamente `extends Object`.

Esto garantiza que cualquier objeto en Java herede un conjunto de métodos fundamentales:

```
                  +--------------------------------+
                  |       java.lang.Object         |
                  +--------------------------------+
                  | + toString(): String           |
                  | + equals(obj: Object): boolean |
                  | + hashCode(): int              |
                  | + getClass(): Class<?>         |
                  +--------------------------------+
                                  ▲
                                  │ (herencia implícita)
                  +---------------+----------------+
                  |         CuentaBancaria         |
                  +--------------------------------+
```

---

### 6.1 Sobrescritura (*Override*) y la Anotación `@Override`

La **sobrescritura** consiste en redefinir en una subclase un método heredado de su superclase para dotarlo de un comportamiento específico.

Para indicar formalmente al compilador que nuestra intención es sobrescribir un método, anteponemos la anotación **`@Override`**:

```java
@Override
public String toString() { ... }
```

:::tip[¿Por qué es indispensable usar @Override?]
Si cometes una errata al escribir el nombre del método (por ejemplo `public String tostring()`), sin `@Override` el compilador creerá que estás creando un método nuevo ordinario. Con `@Override`, el compilador comprobará si el método existe en la superclase; si no existe, detendrá la compilación informándote del error de inmediato.
:::

---

### 6.2 Sobrescribir `toString()`: Representación Textual Legible

La implementación por defecto de `toString()` en la clase `Object` devuelve una cadena poco informativa con el nombre de la clase y el código hash en hexadecimal:

```java
System.out.println(miCuenta); 
// Salida por defecto sin sobrescribir: es.ies.dam.banco.CuentaBancaria@7b4a2f
```

Sobrescribir `toString()` es una práctica obligatoria para que el objeto muestre una representación clara de su estado, fundamental para depuración y logs:

```java
@Override
public String toString() {
    return String.format("CuentaBancaria[IBAN='%s', Titular='%s', Saldo=%.2f€, Bloqueada=%b]",
            this.iban, this.titular, this.saldo, this.bloqueada);
}
```

Ahora:
```java
System.out.println(miCuenta); 
// Salida legible: CuentaBancaria[IBAN='ES912100...', Titular='Sofía Morales', Saldo=1450.50€, Bloqueada=false]
```

---

### 6.3 Comparación de Objetos: Operador `==` vs. Método `equals()`

Este es el punto donde más programadores noveles tropiezan:

```java
CuentaBancaria c1 = new CuentaBancaria("Carlos", "ES001");
CuentaBancaria c2 = new CuentaBancaria("Carlos", "ES001");

System.out.println(c1 == c2);      // ¡Imprime FALSE!
System.out.println(c1.equals(c2)); // Imprime TRUE (si sobrescribimos equals correctamente)
```

¿Por qué ocurre esto?
- **El operador `==`:** Compara **identidad física de referencia** (si ambas variables apuntan a la misma dirección física en el Heap). Como `c1` y `c2` fueron creados con dos sentencias `new` distintas, residen en posiciones de memoria diferentes; por tanto `c1 == c2` es `false`.
- **El método `equals()`:** Compara **igualdad de contenido o semántica de negocio** (si dos objetos representan la misma entidad real, por ejemplo dos cuentas con el mismo IBAN).

#### El Contrato Riguroso de `equals()` en Java
Según la especificación oficial de la JVM, cualquier implementación de `equals` debe ser:
1. **Reflexiva:** `x.equals(x)` debe devolver siempre `true`.
2. **Simétrica:** `x.equals(y)` debe devolver `true` si y solo si `y.equals(x)` devuelve `true`.
3. **Transitiva:** Si `x.equals(y)` y `y.equals(z)` son `true`, entonces `x.equals(z)` debe ser `true`.
4. **Consistente:** Múltiples invocaciones sobre objetos no modificados deben arrojar siempre el mismo resultado.
5. **Tratamiento de `null`:** `x.equals(null)` debe devolver siempre `false` (sin lanzar `NullPointerException`).

#### Implementación Canónica Paso a Paso de `equals()` (Java Moderno):

```java
import java.util.Objects;

public class Cliente {
    private final String dni;
    private String nombre;
    private String email;

    public Cliente(String dni, String nombre, String email) {
        this.dni = Objects.requireNonNull(dni);
        this.nombre = nombre;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Comprobación rápida de identidad física
        if (this == obj) {
            return true;
        }

        // 2. Comprobación de tipo y cast seguro mediante Pattern Matching de Java moderno
        if (!(obj instanceof Cliente other)) {
            return false; // Si es null o de una clase incompatible, devuelve false
        }

        // 3. Comparación de los atributos clave que determinan la identidad de negocio
        return Objects.equals(this.dni, other.dni);
    }

    @Override
    public int hashCode() {
        // La regla de oro: si dos objetos son iguales por equals, DEBEN devolver el mismo hashCode
        return Objects.hash(this.dni);
    }
}
```

---

### 6.4 El Contrato Indisociable con `hashCode()`

El método `hashCode()` devuelve un número entero de 32 bits utilizado por estructuras de almacenamiento basadas en tablas hash (como `HashSet`, `HashMap` o `Hashtable`, estudiadas en la Unidad 6).

:::danger[La Regla de Oro de equals y hashCode]
**Si dos objetos son iguales según el método `equals()`, ambos DEBEN devolver obligatoriamente el mismo valor entero en `hashCode()`.**

Si sobrescribes `equals()` pero olvidas sobrescribir `hashCode()`, romperás por completo el funcionamiento interno de las colecciones de Java: al añadir un objeto a un `HashSet`, la colección no será capaz de encontrarlo ni detectará duplicados, causando fallos lógicos gravísimos en producción.
:::

---

### 6.5 Generación de Métodos Canónicos con IntelliJ IDEA

En el desarrollo profesional nadie pica a mano el código de `equals()` y `hashCode()`, pues es fácil cometer errores sutiles de verificación de nulos:

1. Dentro de tu clase en IntelliJ IDEA, presiona **`Alt + Insert`** (o clic derecho → *Generate...*).
2. Selecciona **`equals() and hashCode()`**.
3. Elige la plantilla *IntelliJ Default* o *java.util.Objects.equals and hashCode*.
4. Marca las casillas de los campos que identifican de forma única a tu entidad (por ejemplo, el `dni` o el `iban`).
5. Pulsa *Finish*. El IDE generará el código canónico optimizado y libre de errores.

## 7. Diseño Avanzado de Clases y Patrones Esenciales

El dominio de la orientación a objetos no concluye con la creación de clases ordinarias. La arquitectura de software moderna exige patrones de diseño contrastados y mecanismos para garantizar la inmutabilidad y la claridad semántica.

---

### 7.1 Clases Inmutables: Robustez y Ausencia de Efectos Colaterales

Un **objeto inmutable** es aquel cuyo estado no puede alterarse en absoluto tras su instanciación. Ejemplos eminentes de clases inmutables en la biblioteca estándar de Java son `java.lang.String`, `java.lang.Integer`, `java.time.LocalDate` y `java.math.BigDecimal`.

#### Ventajas Fundamentales de la Inmutabilidad:
1. **Seguridad frente a hilos (*Thread-Safety*):** Al no poder mutar, múltiples hilos pueden compartir el mismo objeto simultáneamente sin necesidad de sincronización ni bloqueos.
2. **Ausencia de efectos colaterales:** Un método que reciba un objeto inmutable puede operar con él con la absoluta certeza de que nadie modificará su contenido de forma imprevista.
3. **Fiabilidad en colecciones:** Son candidatos idóneos para actuar como claves en mapas (`HashMap`) o elementos en conjuntos (`HashSet`), ya que su `hashCode` nunca cambiará.

#### Reglas para Diseñar una Clase Inmutable en Java Tradicional:
1. Declarar la clase como **`final`** (para impedir que una subclase herede de ella y sobrecargue o añada mutabilidad).
2. Declarar todos los atributos como **`private final`**.
3. **No proporcionar ningún método mutador (*setter*)**.
4. Si la clase contiene referencias a objetos mutables (por ejemplo, arrays o colecciones), aplicar **copias defensivas** tanto en el constructor como en los getters:

```java
import java.util.Arrays;

public final class SecuenciaPuntosInmutable {
    private final String etiqueta;
    private final int[] coordenadas;

    public SecuenciaPuntosInmutable(String etiqueta, int[] coordenadas) {
        this.etiqueta = (etiqueta != null) ? etiqueta : "Sin etiqueta";
        // COPIA DEFENSIVA EN EL CONSTRUCTOR:
        // Clona el array exterior para que modificaciones posteriores del invocador no afecten al objeto
        this.coordenadas = (coordenadas != null) ? Arrays.copyOf(coordenadas, coordenadas.length) : new int[0];
    }

    public String getEtiqueta() {
        return this.etiqueta; // String es inmutable por naturaleza
    }

    public int[] getCoordenadas() {
        // COPIA DEFENSIVA EN EL GETTER:
        // Devuelve un nuevo array clonado; si el exterior muta la copia devuelta, el objeto permanece intacto
        return Arrays.copyOf(this.coordenadas, this.coordenadas.length);
    }
}
```

---

### 7.2 Los `record` en Java Moderno (Java 16+, 21 LTS y 25 LTS)

En aplicaciones corporativas, una gran parte de las clases son meros **transportadores de datos inmutables** (*Data Carriers* o DTOs - *Data Transfer Objects*). Escribir manualmente constructores, getters, `equals()`, `hashCode()` y `toString()` para 10 atributos genera cientos de líneas de código redundante (*boilerplate*).

Para solucionar esto, Java moderno introdujo los **`record`**: una forma concisa y declarativa de definir clases de datos inmutables:

```java
// ¡Una única línea sustituye a 50 líneas de código tradicional!
public record CoordenadaGPS(double latitud, double longitud, String descripcion) {

    // Constructor compacto opcional para comprobación de invariantes y precondiciones
    public CoordenadaGPS {
        if (latitud < -90.0 || latitud > 90.0) {
            throw new IllegalArgumentException("Latitud fuera de rango [-90, 90]: " + latitud);
        }
        if (longitud < -180.0 || longitud > 180.0) {
            throw new IllegalArgumentException("Longitud fuera de rango [-180, 180]: " + longitud);
        }
        if (descripcion == null) {
            descripcion = "Sin descripción";
        }
    }
}
```

#### ¿Qué Genera Automáticamente la JVM para un `record`?
1. Una clase final inmutable que hereda de `java.lang.Record`.
2. Todos los campos declarados como componentes `private final`.
3. Un constructor canónico con todos los componentes.
4. Métodos accesores con el **mismo nombre que el componente, sin prefijo `get`** (por ejemplo: `punto.latitud()`, `punto.longitud()`).
5. Implementaciones canónicas perfectas de `equals()`, `hashCode()` y `toString()`.

---

### 7.3 El Patrón de Diseño Singleton

El **Singleton** es un patrón de diseño creacional cuyo objetivo es garantizar que una clase tenga **una única instancia viva en toda la memoria de la JVM**, proporcionando un único punto de acceso global a ella.

Se utiliza con frecuencia para representar componentes centralizados: gestores de configuración del sistema, pools de conexiones a bases de datos o servicios de auditoría.

#### Implementación Clásica Segura:

```java
public class ConfiguracionSistema {

    // 1. Instancia estática única y privada (Eager Initialization)
    private static final ConfiguracionSistema INSTANCIA = new ConfiguracionSistema();

    // Atributos de configuración
    private String urlServidor;
    private int puerto;

    // 2. CONSTRUCTOR PRIVADO: Nadie desde el exterior puede ejecutar 'new ConfiguracionSistema()'
    private ConfiguracionSistema() {
        this.urlServidor = "https://api.empresa.es";
        this.puerto = 8443;
    }

    // 3. Método estático público de acceso universal
    public static ConfiguracionSistema getInstance() {
        return INSTANCIA;
    }

    public String getUrlServidor() {
        return this.urlServidor;
    }

    public void setUrlServidor(String urlServidor) {
        this.urlServidor = urlServidor;
    }

    public int getPuerto() {
        return this.puerto;
    }
}
```

**Uso desde cualquier punto de la aplicación:**
```java
ConfiguracionSistema config = ConfiguracionSistema.getInstance();
System.out.println("Conectando a: " + config.getUrlServidor());
```

---

### 7.4 Enumeraciones (`enum`) en Java

En otros lenguajes (como C o C++), un `enum` es simplemente un alias numérico entero. En Java, **un `enum` es una clase de pleno derecho** con superclase base `java.lang.Enum`.

Una enumeración define un conjunto cerrado, exhaustivo y seguro frente a tipos de valores constantes.

#### Enumeración Básica:
```java
public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
}
```

#### Enumeración Rica con Atributos y Métodos:
Las enumeraciones en Java pueden tener sus propios atributos, constructores privados y lógica de negocio:

```java
public enum NivelAcceso {
    INVITADO(1, "Acceso solo de lectura básico"),
    OPERADOR(2, "Acceso a operaciones estándar y reportes"),
    ADMINISTRADOR(3, "Acceso total de administración y configuración");

    private final int nivelNumerico;
    private final String descripcion;

    // Constructor del enum (siempre es implícita o explícitamente privado)
    NivelAcceso(int nivelNumerico, String descripcion) {
        this.nivelNumerico = nivelNumerico;
        this.descripcion = descripcion;
    }

    public int getNivelNumerico() {
        return this.nivelNumerico;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public boolean puedeModificarUsuarios() {
        return this == ADMINISTRADOR;
    }
}
```

#### Métodos de Utilidad Nativos de los `enum`:
- `values()`: Devuelve un array con todas las constantes en el orden en que fueron declaradas (`NivelAcceso.values()`).
- `valueOf(String nombre)`: Convierte un String en la constante correspondiente (o lanza `IllegalArgumentException` si no existe).
- `name()`: Devuelve el identificador textual exacto de la constante (`"ADMINISTRADOR"`).
- `ordinal()`: Devuelve la posición entera indexada en base cero de la constante.

---

## 8. Organización Modular: Paquetes e Importaciones

A medida que una aplicación crece y cuenta con decenas o cientos de clases, es indispensable organizarlas en carpetas lógicas para evitar el caos y prevenir colisiones de nombres.

### 8.1 Concepto de Paquete (`package`)

Un **paquete** en Java cumple una doble función:
1. **Espacio de nombres (*Namespace*):** Permite que existan dos clases con el mismo nombre siempre que pertenezcan a paquetes diferentes (por ejemplo, `java.util.Date` frente a `java.sql.Date`).
2. **Estructura física en disco:** La sentencia de paquete debe coincidir **exactamente con la jerarquía de carpetas en el sistema de archivos**.

#### Convención Oficial de Nombres de Paquetes:
Se utiliza el **nombre de dominio inverso de la organización o centro educativo** en minúsculas puras, seguido del nombre del proyecto y el subdominio funcional:
```java
package es.iesperenxisa.dam.gestionbancaria.modelo;
```
Físicamente en el disco duro, este fichero debe residir en la ruta:
`src/main/java/es/iesperenxisa/dam/gestionbancaria/modelo/CuentaBancaria.java`

---

### 8.2 Sentencias `import` e Importaciones Estáticas

Para utilizar una clase situada en otro paquete, debemos indicárselo al compilador mediante la instrucción **`import`**:

```java
import java.util.Scanner;        // Importación explícita (Recomendada)
import java.time.LocalDate;

// Importación con comodín (importa todas las clases públicas del paquete, pero no sus subpaquetes)
import java.util.*;

// Importación estática: permite invocar métodos y constantes estáticas directamente sin anteponer la clase
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

public class CalculoGeometria {
    public double calcularAreaCirculo(double r) {
        return PI * r * r; // Se usa PI directamente en vez de Math.PI
    }
}
```

:::note[El Paquete Implícito java.lang]
Todas las clases del paquete fundamental **`java.lang`** (como `String`, `Math`, `System`, `Integer`, `Double`, `Object` o `Exception`) son importadas de forma completamente automática e invisible por el compilador en todos los ficheros de Java. Nunca necesitas escribir `import java.lang.String;`.
:::

---

## 9. ⭐ Be the Code: Desafíos de Traza y Memoria

Un programador que domina la POO debe visualizar instantáneamente qué ocurre en la memoria Stack y Heap ante cada sentencia. Resuelve mentalmente los siguientes tres retos.

---

### Desafío 1: Comparaciones de Identidad vs. Contenido y Paso por Referencia

Analiza minuciosamente el siguiente código y deduce qué imprime exactamente en la consola:

```java
public class RetoPOO1 {
    static class Mensaje {
        String texto;
        Mensaje(String t) { this.texto = t; }
    }

    public static void alterar(Mensaje m1, Mensaje m2) {
        m1.texto = "Modificado";
        m2 = new Mensaje("Nuevo");
        m2.texto = "Cambiado";
    }

    public static void main(String[] args) {
        Mensaje a = new Mensaje("Original A");
        Mensaje b = new Mensaje("Original B");
        Mensaje c = a;

        alterar(a, b);

        System.out.println("1: " + a.texto);
        System.out.println("2: " + b.texto);
        System.out.println("3: " + (a == c));
        System.out.println("4: " + a.equals(c));
    }
}
```

<details>
<summary>🔍 Ver Resolución y Traza de Memoria</summary>

**Análisis de la ejecución:**
1. `Mensaje c = a;`: `c` y `a` apuntan exactamente al mismo objeto en el Heap.
2. Al invocar `alterar(a, b)`:
   - `m1` recibe una copia de la referencia que apunta al objeto `a`. `m1.texto = "Modificado"` muta el atributo del objeto compartido. Por tanto, `a.texto` ahora vale `"Modificado"`.
   - `m2` recibe una copia de la referencia que apunta al objeto `b`. La sentencia `m2 = new Mensaje("Nuevo")` reasigna la variable local `m2` a un nuevo objeto. Esta reasignación no tiene ningún efecto sobre la referencia original `b` en el `main`. Por tanto, `b.texto` sigue siendo `"Original B"`.
3. `a == c`: Ambas variables almacenan la misma dirección física en el Heap -> `true`.
4. `a.equals(c)`: Como `Mensaje` no sobrescribe `equals()`, hereda la implementación por defecto de `Object` (que evalúa `this == obj`), por lo que también es `true`.

**Salida Final:**
```text
1: Modificado
2: Original B
3: true
4: true
```
</details>

---

### Desafío 2: Encadenamiento de Constructores y Atributos Estáticos

¿Cuál es la salida producida en la consola tras ejecutar este programa?

```java
public class RetoPOO2 {
    static class ContadorInstancias {
        static int total = 0;
        int id;
        String nombre;

        ContadorInstancias(String n) {
            this(n, ++total);
            System.out.print("C1(" + this.nombre + ") ");
        }

        ContadorInstancias(String n, int i) {
            this.nombre = n;
            this.id = i;
            System.out.print("C2(" + this.id + ") ");
        }
    }

    public static void main(String[] args) {
        ContadorInstancias obj1 = new ContadorInstancias("Alfa");
        ContadorInstancias obj2 = new ContadorInstancias("Beta", 99);
        System.out.println("-> Total: " + ContadorInstancias.total);
    }
}
```

<details>
<summary>🔍 Ver Resolución y Traza</summary>

**Secuencia de llamadas:**
1. `new ContadorInstancias("Alfa")`:
   - Se evalúa el argumento `++total`: `total` pasa de `0` a `1`.
   - Llama a `this("Alfa", 1)`.
   - Se ejecuta el cuerpo de `C2`: imprime `C2(1) `.
   - Retorna al cuerpo de `C1`: imprime `C1(Alfa) `.
2. `new ContadorInstancias("Beta", 99)`:
   - Invoca directamente al constructor de dos parámetros sin pasar por `C1`. `total` no se incrementa.
   - Se ejecuta `C2`: imprime `C2(99) `.
3. Impresión final de `total`: `1`.

**Salida en consola:**
```text
C2(1) C1(Alfa) C2(99) -> Total: 1
```
</details>

---

### Desafío 3: La Trampa de la Mutabilidad Encubierta

Observa la siguiente clase y determina qué imprimirá la última línea:

```java
public class RetoPOO3 {
    static class BovedaSegura {
        private final int[] claves;

        public BovedaSegura(int[] clavesEntrada) {
            this.claves = clavesEntrada; // ¡Alerta de diseño!
        }

        public int[] getClaves() {
            return this.claves; // ¡Alerta de diseño!
        }
    }

    public static void main(String[] args) {
        int[] misClaves = {1234, 5678};
        BovedaSegura boveda = new BovedaSegura(misClaves);

        misClaves[0] = 9999;
        boveda.getClaves()[1] = 0000;

        System.out.println(java.util.Arrays.toString(boveda.getClaves()));
    }
}
```

<details>
<summary>🔍 Ver Resolución y Explicación de Fuga de Referencia</summary>

**Explicación de la vulnerabilidad de encapsulamiento:**
- Aunque el atributo `claves` se declaró como `final`, `final` solo impide reasignar la referencia del array; no impide mutar el contenido de sus celdas.
- En el constructor, `this.claves = clavesEntrada` no hizo una copia defensiva: guardó la misma dirección que `misClaves`. Al alterar `misClaves[0] = 9999`, la bóveda quedó alterada.
- En el getter `getClaves()`, se devolvió la referencia directa al array interno. La sentencia `boveda.getClaves()[1] = 0000` modificó la segunda celda.
- Por tanto, la bóveda no era inmutable en absoluto: sufrió una **fuga de encapsulamiento (*Escaping Reference*)**.

**Salida:**
```text
[9999, 0]
```
*(Para subsanar este grave fallo, la clase debería usar `Arrays.copyOf()` tanto en el constructor como en el getter)*.
</details>

---

## 10. ¡No Hay Preguntas Tontas!

---

### 1. ¿Por qué el constructor tiene que llamarse exactamente igual que la clase y no tiene tipo de retorno?
Porque el constructor no es una función ordinaria que calcula y devuelve un valor, sino una rutina especializada de inicialización de memoria gestionada directamente por el operador `new`. La JVM necesita una convención inequívoca para distinguir la creación de un objeto de una llamada a método. Si tuviera tipo de retorno (incluso `void`), el compilador creería que es un método cualquiera que casualmente comparte el nombre con la clase.

---

### 2. ¿Por qué no dejamos todos los atributos públicos y nos ahorramos escribir getters y setters?
Si dejas los atributos `public`, cualquier parte del programa puede asignar valores destructivos o ilegales en cualquier momento (como `cuenta.saldo = -999999;` o `alumno.nota = 450.0;`) sin que la clase pueda impedirlo ni enterarse. Al hacerlos `private` y usar `getters` y `setters`, el objeto mantiene el **control absoluto sobre sus invariantes**: puede validar datos, lanzar excepciones defensivas, calcular campos al vuelo o mantener auditorías de quién modificó la información.

---

### 3. Si pongo `final` en una variable objeto (`final Cuenta c = new Cuenta()`), ¿significa que su saldo no puede cambiar?
**No.** Este es uno de los equívocos más comunes. La palabra `final` en una variable de referencia significa que **la referencia (la dirección de memoria)** no puede cambiar jamás: nunca podrás reasignar `c = new Cuenta()`. Sin embargo, el objeto vivo en el Heap al que apunta `c` sigue siendo completamente mutable: puedes llamar a `c.ingresar(500)` y alterar su saldo libremente. Para que el estado interno sea inmutable, deben ser los atributos internos de la clase los que se declaren como `final`.

---

### 4. ¿Por qué `c1.equals(c2)` devuelve `false` por defecto si no lo sobrescribo?
Porque la implementación original de `equals()` en la clase raíz `java.lang.Object` se define literalmente como `return (this == obj);`. Es decir, si no sobrescribes el método en tu clase, `equals()` se comporta con la misma rigidez que el operador `==`, comparando únicamente si ambos objetos ocupan la misma dirección física en el Heap.

---

### 5. ¿Cuál es la diferencia conceptual entre una variable de instancia y una variable `static`?
- **Variable de instancia:** Pertenece a **un objeto concreto**. Si creas 100 objetos `CuentaBancaria`, habrá 100 variables `saldo` distintas en el Heap, cada una con el dinero de su titular.
- **Variable `static`:** Pertenece a **la clase global**. Existe una única celda de memoria en toda la JVM compartida por los 100 objetos (por ejemplo, el contador de cuántas cuentas existen en el banco).

---

### 6. ¿Por qué dentro de un método `static` no puedo utilizar la palabra reservada `this`?
Porque un método estático se ejecuta a nivel de clase (`NombreClase.metodo()`), con independencia de que existan o no objetos instanciados en el Heap. La palabra `this` representa la dirección del objeto que está ejecutando la llamada; en un contexto estático, no hay ningún objeto en ejecución, por lo que `this` carece por completo de significado físico.

---

### 7. ¿Qué es exactamente un `record` de Java y cuándo debo usarlo en lugar de una clase normal?
Un `record` es una clase especializada inmutable pensada para actuar como transportador transparente de datos. Debes usar un `record` cuando necesites agrupar un conjunto fijo de datos de solo lectura (como una coordenada, una fila leída de base de datos, un par clave-valor o un resultado de cálculo). Debes seguir usando una `class` ordinaria cuando tu entidad requiera estado mutable, herencia jerárquica con `extends` o encapsulamiento con lógica de negocio compleja.

---

### 8. ¿Por qué en una enumeración (`enum`) el constructor es obligatoriamente privado?
Porque el propósito de un `enum` es definir un conjunto exhaustivo y cerrado de instancias fijas conocidas en tiempo de compilación (`LUNES`, `MARTES`, etc.). Si el constructor fuera público, cualquier parte del programa podría ejecutar `new DiaSemana()` en tiempo de ejecución, creando días de la semana inexistentes y destruyendo la garantía de conjunto finito y seguro del tipo.

---

## 11. Resumen de la Unidad y Conexión con la Unidad 5

En esta Unidad 4 hemos dado el paso decisivo hacia la ingeniería del software orientada a objetos:

```
+-------------------------------------------------------------------------+
|                  NÚCLEO DE LA PROGRAMACIÓN ORIENTADA A OBJETOS          |
+-------------------+--------------------+------------------+-------------+
|  CLASES Y OBJETOS |  ENCAPSULAMIENTO   | MIEMBROS STATIC  | CLASE OBJECT|
| Molde en código   | Atributos private, | Variables de     | toString,   |
| vs instancias en  | getters/setters con| clase, constantes| equals y    |
| Heap con estado,  | validación, final  | globales y       | hashCode    |
| conducta y this.  | y records.         | utilidades.      | canónicos.  |
+-------------------+--------------------+------------------+-------------+
```

### El Salto Hacia la Unidad 5: POO Avanzada
Ahora que sabemos crear clases robustas y encapsuladas, en la **Unidad 5: Programación orientada a objetos avanzada** exploraremos los otros dos grandes pilares de la POO:
- **Herencia (`extends`):** Cómo estructurar jerarquías de clases compartiendo atributos y métodos comunes mediante superclases y subclases.
- **Polimorfismo:** Cómo tratar a distintos objetos especializados bajo una misma categoría general.
- **Clases Abstractas e Interfaces (`interface` / `implements`):** El diseño formal de contratos y desacoplamiento en Java moderno.

---

## 12. Relación Curricular Oficial

Esta unidad cubre exhaustivamente los contenidos y criterios de evaluación prescritos en el currículo oficial del ciclo formativo **CFGS Desarrollo de Aplicaciones Multiplataforma (DAM)** para el módulo **0485: Programación**:

- **RA1. Reconoce la estructura de un programa informático, identificando y relacionando los elementos propios del lenguaje de programación utilizado.**
  - *CE a, d, f:* Identificación de bloques de clases, uso de operadores y comentarios de documentación Javadoc.
- **RA2. Escribe y depura código, analizando y utilizando las estructuras de control del lenguaje.**
  - *CE a, b, c, d, e, f, g, h, i, j:* Fundamentos de POO, instanciación en el Heap, invocación de métodos y propiedades, uso de miembros `static`, constructores y depuración con IntelliJ IDEA.
- **RA3. Escribe y optimiza código estructurado aplicando los principios de la programación modular.**
  - *CE a, b, c, d:* Modularización en clases, paso de parámetros de objetos por valor en el Heap y sobrecarga de constructores y métodos.
- **RA4. Desarrolla programas organizados en clases analizando y aplicando los principios de la programación orientada a objetos.**
  - *CE a, b, c, d, f:* Sintaxis y estructura de clases, definición de atributos y métodos, creación de constructores canónicos y sobrecargados, control de visibilidad (`private`, `public`, `protected`, package-private) e instanciación de objetos en aplicaciones integradas.

---

## 13. Boletines de Ejercicios y Retos Prácticos

Para afianzar y llevar a la práctica las habilidades de diseño y codificación orientadas a objetos, dispones de una batería completa de 5 boletines:

| Recurso | Nivel / Enfoque | Descripción |
| :--- | :--- | :--- |
| [📝 **Boletín 04: Nivel Inicial**](./boletin-04-inicial) | Básico / Consolidación | 12 ejercicios guiados para asentar la creación de clases, atributos privados, constructores y getters/setters. |
| [✅ **Boletín 04: Inicial Resuelto**](./boletin-04-inicial-resuelto) | Soluciones Comentadas | Soluciones completas con código Java 25 LTS, validaciones y comentarios pedagógicos. |
| [🚀 **Boletín 04: Nivel Intermedio**](./boletin-04-intermedio) | Intermedio / Aplicado | 12 ejercicios de composición, sobrecarga, `equals`/`hashCode`, `static`, records inmutables, enums y singleton. |
| [✅ **Boletín 04: Intermedio Resuelto**](./boletin-04-intermedio-resuelto) | Soluciones Comentadas | Soluciones completas con buenas prácticas de arquitectura y diseño orientado a objetos. |
| [🏆 **Boletín 04: Retos Orientados a Objetos (Extras)**](./boletin-04-extras) | Avanzado / Arquitectura | 6 retos de alta exigencia: simulación de cuenta corriente, inventario transaccional, baraja con enums, matriz de objetos y máquina expendedora. |
