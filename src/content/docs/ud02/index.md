---
title: 🔤 Unidad 2. Primeros pasos con Java
description: El ecosistema Java (JDK, JVM, Bytecode), instalación y configuración de IntelliJ IDEA, estructura canónica de un programa, tipos de datos primitivos, variables, constantes, operadores, clases estándar (String, Math, java.time), sentencias condicionales y entrada/salida por consola.
---

¡Enhorabuena por haber superado la Unidad 1! Ya dominas los cimientos del pensamiento algorítmico, la representación mediante diagramas de flujo y el diseño estructurado con pseudocódigo. Ahora ha llegado el momento más esperado por todo estudiante de informática: **traducir esa lógica conceptual a un lenguaje de programación real, industrial y de propósito general**.

En esta unidad nos sumergiremos en **Java** (en su versión estándar moderna **Java 21 LTS**), uno de los lenguajes más robustos, seguros y demandados del panorama tecnológico mundial. Utilizaremos como entorno de trabajo oficial **IntelliJ IDEA**, la herramienta de referencia en la industria del desarrollo de software profesional.

Aprenderás cómo viaja tu código desde el editor hasta los transistores del procesador a través de la Máquina Virtual de Java (JVM), dominarás los 8 tipos primitivos de datos, comprenderás cómo manipular texto y números con precisión matemática, aprenderás a interactuar con el usuario mediante la consola y controlarás el flujo de ejecución mediante sentencias condicionales modernas.

---

## 1. El Ecosistema de la Plataforma Java

A diferencia de lenguajes como C o C++ (cuyo código se compila directamente a código binario específico de una arquitectura de hardware y sistema operativo concreto), Java nació en 1995 de la mano de Sun Microsystems con una premisa revolucionaria:

> **"Write Once, Run Anywhere" (WORA): Escribe una vez, ejecuta en cualquier parte.**

Para hacer realidad este principio sin sacrificar el rendimiento, Java no compila a lenguaje máquina nativo ni se interpreta línea a línea como un script de bash o Python. En su lugar, utiliza una **arquitectura híbrida de dos fases** basada en tres pilares conceptuales:

```text
┌─────────────────────────┐          javac          ┌─────────────────────────┐
│     Código Fuente       │ ──────────────────────► │      Java Bytecode      │
│     (MiClase.java)      │       (Compilador)      │     (MiClase.class)     │
└─────────────────────────┘                         └─────────────────────────┘
                                                                 │
                                                                 ▼
                                                    ┌─────────────────────────┐
                                                    │  Máquina Virtual (JVM)  │
                                                    │  ├── Intérprete rápido  │
                                                    │  └── Compilador JIT     │
                                                    └─────────────────────────┘
                                                                 │
                                           ┌─────────────────────┴─────────────────────┐
                                           ▼                                           ▼
                                ┌─────────────────────┐                     ┌─────────────────────┐
                                │   Windows (x86_64)  │                     │     Linux (ARM64)   │
                                │   Código Máquina    │                     │    Código Máquina   │
                                └─────────────────────┘                     └─────────────────────┘
```

### 1.1 La Tríada Fundamental: JDK, JRE y JVM

Es muy frecuente que los principiantes confundan estas tres siglas. Cada una de ellas engloba a la anterior en capas concéntricas de funcionalidad:

1. **JVM (Java Virtual Machine — Máquina Virtual de Java):**
   * Es el software encargado de ejecutar el código binario intermedio de Java, denominado **Bytecode** (archivos con extensión `.class`).
   * La JVM es abstracta y neutral respecto al hardware. Existe una implementación específica de la JVM para cada sistema operativo y arquitectura (Windows x64, Linux ARM, macOS Apple Silicon, etc.).
   * Incorpora dos motores de ejecución: un **intérprete** que arranca de inmediato y un **compilador JIT (*Just-In-Time*)**, que detecta qué fragmentos de código se ejecutan repetidamente ("puntos calientes" o *hotspots*) y los compila al vuelo a instrucciones nativas de la CPU con optimizaciones extremas.
   * Administra la memoria de forma autónoma mediante el **Garbage Collector (Recolector de Basura)**, liberando automáticamente los objetos que el programa ya no utiliza.

2. **JRE (Java Runtime Environment — Entorno de Ejecución de Java):**
   * Es el paquete mínimo necesario para que un usuario final pueda **ejecutar** aplicaciones Java en su ordenador.
   * Contiene la **JVM** junto con las **bibliotecas de clases estándar de la API de Java** (`java.lang`, `java.util`, `java.io`, etc.) y los archivos de configuración requeridos.
   * *Nota:* A partir de Java 11, Oracle y OpenJDK ya no distribuyen un instalador independiente de JRE; para desarrollar o ejecutar se instala directamente el JDK o se empaqueta un runtime ligero a medida con la herramienta `jlink`.

3. **JDK (Java Development Kit — Kit de Desarrollo de Java):**
   * Es el paquete integral que necesita cualquier **programador**.
   * Incluye la **JVM**, todas las librerías de la API y el conjunto completo de herramientas de desarrollo por línea de comandos:
     * `javac`: el compilador que transforma archivos `.java` en Bytecode `.class`.
     * `java`: el lanzador que inicializa la JVM y ejecuta la clase principal de la aplicación.
     * `javadoc`: el generador automático de documentación técnica en formato HTML a partir de comentarios en el código.
     * `jar`: empaquetador de clases y recursos en ficheros comprimidos `.jar` (*Java Archive*).
     * `jdb`: el depurador (*debugger*) para inspeccionar la ejecución paso a paso.

```text
┌──────────────────────────────────────────────────────────────────────────┐
│  JDK (Java Development Kit)                                              │
│  Herramientas de desarrollo: javac, javadoc, jar, jlink, jdb...          │
│  ┌────────────────────────────────────────────────────────────────────┐  │
│  │  JRE (Java Runtime Environment)                                    │  │
│  │  Bibliotecas del API de Java (java.base, java.sql, etc.)           │  │
│  │  ┌──────────────────────────────────────────────────────────────┐  │  │
│  │  │  JVM (Java Virtual Machine)                                  │  │  │
│  │  │  Cargador de clases, Intérprete, Compilador JIT, GC          │  │  │
│  │  └──────────────────────────────────────────────────────────────┘  │  │
│  └────────────────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────────────────┘
```

### 1.2 Versiones de Java y Filosofía LTS

El ecosistema Java se rige por un calendario de publicaciones semestral (una nueva versión en marzo y otra en septiembre de cada año). Existen dos tipos de lanzamientos:

* **Versiones STS (*Short Term Support*):** Tienen soporte y actualizaciones de seguridad únicamente durante 6 meses (por ejemplo, Java 18, 19, 20, 22). Se emplean para probar novedades del lenguaje en entornos de experimentación.
* **Versiones LTS (*Long Term Support*):** Diseñadas para entornos de producción empresarial con soporte oficial garantizado durante años. Las grandes versiones históricas y actuales de referencia en la industria son:
  * **Java 8 LTS (2014):** La mayor revolución histórica (introdujo lambdas y la API Stream).
  * **Java 11 LTS (2018):** Sistema modular de módulos (`JPMS`) y eliminación de APIs obsoletas.
  * **Java 17 LTS (2021):** Clases selladas (*sealed classes*), registros (*records*) y patrones de diseño modernos.
  * **Java 21 LTS (2023 - Versión del curso):** Hilos virtuales (*Virtual Threads / Project Loom*), coincidencia de patrones en `switch` (*Pattern Matching*), colecciones secuenciadas y plantillas de cadenas.

:::note[OpenJDK vs Oracle JDK]
**OpenJDK** es la implementación de código abierto de referencia de la plataforma Java Standard Edition (SE). La inmensa mayoría de distribuciones comerciales (como Eclipse Temurin, Amazon Corretto, Azul Zulu o Microsoft Build of OpenJDK) se compilan directamente a partir del código de OpenJDK bajo licencia GPL v2 con Classpath Exception, lo que garantiza su gratuidad total y libertad para su uso comercial.
:::

---

## 2. El Entorno de Desarrollo Oficial: IntelliJ IDEA

En el ciclo de DAM y en la industria del software internacional, **IntelliJ IDEA** (desarrollado por JetBrains) es ampliamente reconocido como el IDE más avanzado, ergonómico e inteligente para el desarrollo en Java.

### 2.1 Descarga e Instalación de IntelliJ IDEA Community

1. Dirígete al portal oficial de JetBrains (`https://www.jetbrains.com/idea/download/`).
2. Descarga la edición **IntelliJ IDEA Community Edition**, la versión gratuita, de código abierto y completamente funcional para todo el temario de DAM.
3. Ejecuta el asistente de instalación asegurándote de marcar las casillas:
   * Crear acceso directo en el escritorio (*Desktop Shortcut*).
   * Añadir la carpeta `bin` a la variable de entorno `PATH`.
   * Asociar archivos `.java`.

### 2.2 Creación de un Nuevo Proyecto y Vinculación del JDK

Una de las ventajas más sobresalientes de IntelliJ IDEA es que **puede descargar y configurar automáticamente el JDK por ti** sin necesidad de lidiar manualmente con variables de entorno del sistema operativo:

1. Abre IntelliJ IDEA y haz clic en **New Project** (Nuevo Proyecto).
2. En la barra lateral izquierda, selecciona **New Project**.
3. Configura los siguientes parámetros:
   * **Name:** `UD02_PrimerosPasos`
   * **Location:** Carpeta de tu espacio de trabajo (por ejemplo, `C:\workspace\DAM\UD02_PrimerosPasos`).
   * **Language:** `Java`.
   * **Build system:** `IntelliJ`.
   * **JDK:** Si no tienes ninguno instalado, despliega el selector, pulsa en **Download JDK**, escoge la versión **21** y el proveedor **Eclipse Temurin (Adoptium)**. IntelliJ lo descargará e integrará automáticamente.
   * Marca la casilla opcional *Add sample code* si deseas que genere un archivo de prueba.
4. Haz clic en **Create**.

:::tip[No te compliques configurando variables de entorno a mano]
En cursos antiguos o manuales tradicionales era necesario configurar manualmente las variables del sistema (`PATH`, `JAVA_HOME`, `CLASSPATH`) en el Panel de Control de Windows o en el fichero `.bashrc` de Linux para poder compilar y ejecutar desde la terminal. **IntelliJ IDEA se encarga de todo esto por ti**: detecta o descarga cualquier versión del JDK que necesites directamente desde su asistente, y compila y ejecuta con un solo clic. Como futuro técnico superior en DAM es conveniente que comprendas para qué sirven estas variables (lo vimos en la sección anterior), pero apóyate en la potencia de tu IDE para concentrarte en lo verdaderamente determinante: aprender a programar.
:::

```text
Estructura de directorios generada por IntelliJ IDEA:
UD02_PrimerosPasos/
├── .idea/                 ← Metadatos y configuración interna de IntelliJ (no tocar)
├── src/                   ← ¡AQUÍ VA TU CÓDIGO FUENTE! Archivos .java
│   └── Main.java
├── out/                   ← Carpeta de salida donde IntelliJ compila los .class
└── UD02_PrimerosPasos.iml ← Fichero descriptor del módulo de IntelliJ
```

### 2.3 Atajos de Teclado y Plantillas Vivas (*Live Templates*)

Para programar con fluidez en IntelliJ IDEA, memoriza y practica estos atajos desde el primer día:

| Atajo / Plantilla | Acción en IntelliJ IDEA | Utilidad Práctica |
|---|---|---|
| `main` o `psvm` + <kbd>Tab</kbd> | Genera automáticamente el método `public static void main(String[] args)` | Ahorra escribir 40 caracteres de cabecera en cada programa. |
| `sout` + <kbd>Tab</kbd> | Genera `System.out.println();` | La instrucción de impresión más habitual. |
| `souf` + <kbd>Tab</kbd> | Genera `System.out.printf("");` | Para imprimir texto con formato de columnas y decimales. |
| <kbd>Shift</kbd> + <kbd>F10</kbd> | **Run (Ejecutar)** | Compila y ejecuta el programa activo de inmediato. |
| <kbd>Ctrl</kbd> + <kbd>F9</kbd> | **Build Project** | Compila todo el proyecto comprobando errores sintácticos. |
| <kbd>Shift</kbd> + <kbd>F9</kbd> | **Debug (Depurar)** | Inicia la ejecución controlada deteniéndose en los puntos de interrupción. |
| <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>L</kbd> | **Reformat Code** | Formatea e indenta perfectamente todo tu código según el estándar oficial de Java. |
| <kbd>Ctrl</kbd> + <kbd>/</kbd> | **Toggle Comment** | Comenta o descomenta la línea actual con `//`. |
| <kbd>Alt</kbd> + <kbd>Enter</kbd> | **Show Context Actions** | Menú mágico de corrección: soluciona errores, añade imports y sugiere mejoras. |

---

## 3. Anatomía de un Programa en Java

Vamos a crear y analizar minuciosamente nuestro primer programa formal en Java. Dentro de la carpeta `src/`, crea un paquete llamado `es.iesperenxisa.primerospasos` y un archivo llamado `HolaMundo.java`:

```java
package es.iesperenxisa.primerospasos;

/**
 * Mi primer programa en Java para el módulo de Programación DAM.
 * Imprime un mensaje de bienvenida y realiza una operación básica.
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class HolaMundo {

    public static void main(String[] args) {
        // Imprimimos un saludo por la consola de salida estándar
        System.out.println("¡Bienvenido al universo Java desde IntelliJ IDEA!");
        
        // Declaramos una variable entera y mostramos un cálculo
        int horasSemanales = 8;
        System.out.println("Horas semanales dedicadas: " + horasSemanales);
    }
}
```

### 3.1 Desglose Riguroso del Código

Cada una de las líneas anteriores encierra una regla estructural obligatoria del lenguaje Java:

#### 1. La declaración de paquete: `package es.iesperenxisa.primerospasos;`
* Los **paquetes** (*packages*) son el mecanismo de Java para organizar las clases en carpetas jerárquicas y evitar colisiones de nombres entre librerías.
* Por convención internacional, los nombres de paquetes se escriben íntegramente en minúsculas utilizando la notación de dominio web inverso del centro o empresa (`es.iesperenxisa.modulo.tema`).

#### 2. La definición de la clase: `public class HolaMundo`
* Java es un lenguaje orientado a objetos puro: **absolutamente todo el código debe residir en el interior de una clase**.
* `public`: modificador de visibilidad que indica que la clase es accesible desde cualquier parte del proyecto.
* `class`: palabra reservada fundamental que indica la definición de un molde o tipo.
* `HolaMundo`: identificador o nombre asignado a la clase.

:::caution[Regla de Oro de los Ficheros Java]
Si una clase está declarada con el modificador `public`, el archivo en disco **DEBE llamarse exactamente igual que la clase**, respetando estrictamente las mayúsculas y minúsculas (sensibilidad a la caja tipográfica o *case-sensitive*) y finalizando con la extensión `.java`:
* Nombre de la clase: `public class HolaMundo` → Nombre de archivo: `HolaMundo.java`.
* Si el fichero se llamara `holamundo.java` o `Hola_Mundo.java`, el compilador emitirá un error fatal de compilación.
:::

#### 3. El punto de entrada universal: `public static void main(String[] args)`
Es el método más célebre de Java. Cuando ordenas a la JVM ejecutar una clase, busca con exactitud milimétrica esta cabecera para comenzar la ejecución:

* `public`: El método debe poder ser invocado externamente por la Máquina Virtual desde fuera de la clase.
* `static`: Indica que el método pertenece a la clase en sí y **no requiere la creación previa de un objeto** en memoria mediante `new` para poder ser invocado. Al encenderse la JVM, la aplicación aún no ha creado ningún objeto; sin `static`, el programa jamás podría arrancar.
* `void`: Especifica el tipo de retorno. Significa que el método realiza una tarea pero **no devuelve ningún dato** al finalizar su ejecución.
* `main`: Es el nombre reservado que identifica la puerta de entrada. Si cometes una errata (como `Main` con mayúscula o `mian`), el compilador no se quejará, pero la JVM responderá con el clásico error: *"Main method not found in class"*.
* `String[] args`: Es un vector o array de cadenas de caracteres (`String`). Permite recibir parámetros o modificadores que el usuario introduzca desde la línea de comandos al lanzar el programa (por ejemplo: `java MiServidor --puerto 8080`).

#### 4. La salida por pantalla: `System.out.println(...)`
* `System`: Clase predefinida perteneciente al núcleo de Java (`java.lang.System`) que proporciona acceso a recursos del sistema.
* `out`: Objeto estático dentro de `System` que representa el flujo de **salida estándar** (*Standard Output Stream*), conectado por defecto a la consola.
* `println`: Método que envía una cadena de texto a la consola e introduce automáticamente un **salto de línea** al final. Si utilizáramos `print()`, el cursor se quedaría pegado al final de la frase.
* `;` (punto y coma): En Java, todas las sentencias ejecutables finalizan obligatoriamente en punto y coma. Omitirlo es el error sintáctico más común de los programadores noveles.

### 3.2 Tipología de Comentarios y Generación con JavaDoc

El código profesional debe ser autoexplicativo y estar documentado con elegancia. Java soporta tres modalidades de comentarios:

```java
// 1. Comentario de una sola línea: se extiende hasta el final de la línea actual.

/*
   2. Comentario de múltiples líneas o de bloque:
   Útil para explicaciones largas o para deshabilitar
   temporalmente bloques enteros de código durante una prueba.
*/

/**
 * 3. Comentario de documentación JavaDoc:
 * Comienza con /** y finaliza con * /
 * Permite a la herramienta 'javadoc' generar páginas web de ayuda técnica.
 * 
 * @param radio El radio de la circunferencia en metros (debe ser > 0)
 * @return El área calculada en metros cuadrados
 * @throws IllegalArgumentException si el radio es negativo
 */
public double calcularArea(double radio) {
    return Math.PI * radio * radio;
}
```

### 3.3 Compilación y Ejecución Manual desde la Terminal

Aunque en el día a día trabajarás con los botones de IntelliJ IDEA, como futuro técnico superior en DAM debes dominar con soltura qué comandos se ejecutan entre bastidores desde una consola de comandos (PowerShell, Bash o CMD):

```bash
# 1. Compilación: Transforma el archivo fuente en Bytecode
javac HolaMundo.java

# Al compilar con éxito, se genera en la misma carpeta el archivo binario:
# HolaMundo.class

# 2. Ejecución: Se lanza la JVM indicando el nombre de la clase (¡SIN extensión .class!)
java HolaMundo

# 3. Generación de documentación HTML:
javadoc -d docs/ HolaMundo.java
```

---

## 4. Elementos Básicos del Lenguaje: Variables, Constantes y Tipos

Un programa informático es, en esencia, un transformador de datos. Para manipular esos datos en la memoria RAM, necesitamos declarar **variables** y **constantes**.

### 4.1 Identificadores y Reglas de Nomenclatura

Un **identificador** es el nombre que el programador asigna a una variable, constante, método, clase o paquete. En Java, los identificadores deben cumplir reglas sintácticas inflexibles y convenciones de estilo universales:

#### Reglas Sintácticas Obligatorias (impuestas por el compilador):
1. Solo pueden estar compuestos por caracteres alfanuméricos Unicode (letras y dígitos), el signo de dólar `$` y el guion bajo `_`.
2. **Nunca pueden comenzar por un dígito** (`1variable` es ilegal; `variable1` es legal).
3. No pueden coincidir con ninguna de las **palabras reservadas** de Java (`int`, `class`, `public`, `return`, `void`, `if`, etc.).
4. No pueden contener espacios en blanco ni operadores aritméticos o de puntuación (`mi-variable`, `total%`, `nombre completo` son ilegales).
5. Java distingue estrictamente entre mayúsculas y minúsculas: `numero`, `Numero` y `NUMERO` son tres variables totalmente distintas para el compilador.

#### Convenciones de Nomenclatura Profesionales (Java Code Conventions):

| Elemento | Convención Oficial | Ejemplo Válido y Explicativo |
|---|---|---|
| **Clases e Interfaces** | **UpperCamelCase** (PascalCase): Cada palabra comienza en mayúscula; sustantivos. | `GestorClientes`, `FacturaElectronica`, `CalculadoraFinanciera` |
| **Variables y Atributos** | **lowerCamelCase**: Primera palabra en minúscula, siguientes en mayúscula; sustantivos descriptivos. | `edadUsuario`, `saldoCuentaBancaria`, `esMayorDeEdad` |
| **Métodos y Funciones** | **lowerCamelCase**: Verbo que denota acción seguido de objeto. | `calcularTotal()`, `obtenerDni()`, `imprimirInforme()` |
| **Constantes (`final`)** | **UPPER_SNAKE_CASE**: Todas las letras en mayúsculas separadas por guion bajo. | `PI`, `MAX_INTENTOS_LOGIN`, `VELOCIDAD_DE_LA_LUZ` |
| **Paquetes** | Minúsculas continuas separadas por puntos; dominio inverso. | `es.iesperenxisa.primerospasos`, `org.empresa.utilidades` |

:::caution[Evita la 'ñ', tildes y caracteres especiales en el código fuente]
Técnicamente, el compilador de Java utiliza la codificación Unicode y aceptará variables como `int año = 2024;` o `double puntuación = 9.5;` sin dar error sintáctico. Sin embargo, **la convención profesional internacional prohíbe taxativamente su uso**.
Si compartes código con compañeros en otros sistemas operativos (Windows, Linux, macOS), repositorios Git o servidores de integración y despliegue continuo (CI/CD) con diferentes configuraciones regionales de terminal (*charsets* como UTF-8 vs Windows-1252), esos caracteres pueden corromperse y provocar fallos de compilación desconcertantes.
* En **comentarios** y en **cadenas de texto para el usuario** (`println`, interfaces): escribe con ortografía impecable en español (con tildes y 'ñ').
* En **identificadores de código** (variables, métodos, clases): utiliza **exclusivamente el alfabeto inglés sin tildes ni eñes** (`int anio = 2024;` o en inglés `int year = 2024;`).
:::

### 4.2 Variables vs Constantes

* **Variable:** Es una posición nombrada de memoria RAM cuyo contenido puede cambiar a lo largo del tiempo durante la ejecución del programa.
* **Constante:** Es una posición de memoria cuyo valor se define en el momento de su inicialización y queda protegido contra cualquier modificación posterior mediante la palabra clave `final`. Si intentas reasignar una constante, el compilador bloqueará el proceso.

```java
// Declaración e inicialización de variables
int edad = 20;
edad = 21; // Completamente válido: el valor se actualiza en memoria

// Declaración de una constante
final double TIPO_IVA = 0.21;
// TIPO_IVA = 0.25; // ¡ERROR DE COMPILACIÓN! Cannot assign a value to final variable
```

### 4.3 Inferencia de Tipos con `var` (Java 10+)

A partir de Java 10, es posible omitir el tipo explícito al declarar variables locales utilizando la palabra reservada contextual `var`. El compilador infiere el tipo exacto basándose en el valor de la asignación inicial:

```java
var contador = 10;                // El compilador infiere que es de tipo int
var precio = 19.99;               // El compilador infiere que es double
var mensaje = "Hola DAM";         // El compilador infiere String
var cliente = new GestorClientes();// El compilador infiere GestorClientes
```

:::caution[Reglas y Buenas Prácticas con `var`]
* `var` solo puede utilizarse en **variables locales** dentro de un método y **requiere inicialización inmediata obligatoria** en la misma línea (`var x;` da error porque el compilador no puede adivinar qué es).
* Java **sigue siendo un lenguaje de tipado estático**: una vez que `var contador = 10` se infiere como `int`, jamás podrás asignarle un texto (`contador = "hola";` fallará en compilación).
* Emplea `var` únicamente cuando el tipo resulte redundante o evidente (por ejemplo con llamadas a constructores largos: `var lista = new ArrayList<String>()`). Si perjudica la legibilidad del código, declara siempre el tipo explícito.
:::

---

## 5. El Sistema de Tipos: Los 8 Tipos de Datos Primitivos

Java divide su sistema de tipos en dos grandes categorías:
1. **Tipos Primitivos:** Valores elementales puros alojados directamente en la pila de memoria (*Stack*).
2. **Tipos Referenciados u Objetos:** Punteros gestionados que apuntan a estructuras de datos complejas en la memoria dinámica (*Heap*).

A continuación se detalla la tabla canónica de los 8 tipos primitivos de Java:

| Categoría | Tipo | Tamaño en Memoria | Rango de Valores Representable | Valor por Defecto |
|---|---|---|---|---|
| **Enteros** | `byte` | 8 bits (1 byte) | -128 a 127 (-2⁷ a 2⁷ - 1) | `0` |
| | `short` | 16 bits (2 bytes) | -32.768 a 32.767 (-2¹⁵ a 2¹⁵ - 1) | `0` |
| | `int` | 32 bits (4 bytes) | -2.147.483.648 a 2.147.483.647 (-2³¹ a 2³¹ - 1) | `0` |
| | `long` | 64 bits (8 bytes) | -9.223.372.036.854.775.808 a 9.223.372.036.854.775.807 (-2⁶³ a 2⁶³ - 1) | `0L` |
| **Coma Flotante** | `float` | 32 bits (4 bytes) | ~1.4 × 10⁻⁴⁵ a ~3.4 × 10³⁸ (precisión simple, 7 dígitos) | `0.0f` |
| | `double` | 64 bits (8 bytes) | ~4.9 × 10⁻³²⁴ a ~1.8 × 10³⁰⁸ (precisión doble, 15-17 dígitos) | `0.0d` |
| **Carácter** | `char` | 16 bits (2 bytes) | Caracteres Unicode UTF-16 (` ` a `￿` ó 0 a 65.535) | ` ` |
| **Booleano** | `boolean` | 1 bit (lógico) | Únicamente `true` o `false` | `false` |

:::caution[Java no comprueba desbordamientos numéricos en tipos primitivos]
A diferencia de lo que cabría esperar intuitivamente, si a una variable `short` que contiene su valor máximo permitido (`32.767`) le sumas `1`, Java **no lanza ningún error ni detiene el programa**: el valor da la vuelta de manera cíclica en aritmética binaria de complemento a dos, pasando a valer instantáneamente `-32.768`.
Elegir un tipo de dato numérico demasiado ajustado para tus variables es una fuente clásica de *bugs* silenciosos y catastróficos en producción. En caso de duda:
* Para números enteros: utiliza siempre `int` (o `long` para cifras astronómicas o identificadores globales).
* Para números con decimales: utiliza siempre `double`.
:::

### 5.1 Literales y Notación Numérica

Un **literal** es un valor constante escrito directamente en el código fuente:

* **Literales Enteros:** Por defecto, cualquier número entero entero suelto en el código (como `42`) es tratado por el compilador como un `int`. Si necesitamos asignarlo a un `long`, debemos agregar obligatoriamente el sufijo `L` o `l` (se recomienda siempre la `L` mayúscula para no confundirla con el número `1`):
  ```java
  long poblacionMundial = 8_000_000_000L; // Sufijo L obligatorio
  ```
* **Separadores Visuales con Guion Bajo:** Para mejorar la legibilidad de cifras astronómicas, Java permite intercalar guiones bajos entre dígitos numéricos:
  ```java
  int unMillon = 1_000_000;
  long tarjetaCredito = 4542_1234_5678_9012L;
  ```
* **Bases Numéricas:**
  * Decimal: `int dec = 26;`
  * Binario (prefijo `0b` o `0B`): `int bin = 0b0001_1010; // Vale 26 en decimal`
  * Octal (prefijo `0`): `int oct = 032; // Vale 26 en decimal`
  * Hexadecimal (prefijo `0x` o `0X`): `int hex = 0x1A; // Vale 26 en decimal`
* **Literales Decimales (Coma Flotante):** Cualquier número con punto decimal (como `3.14`) es considerado automáticamente de tipo `double`. Para forzar que sea `float`, debe llevar el sufijo `F` o `f`:
  ```java
  double piDoble = 3.1415926535; // Válido: double por defecto
  float piSimple = 3.141592f;    // Válido: forzado a float con 'f'
  // float error = 3.14;        // ¡ERROR! Type mismatch: cannot convert from double to float
  ```
* **Literales de Carácter y Secuencias de Escape:** Los valores de tipo `char` se encierran siempre entre comillas simples `' '` (a diferencia de los textos `String` que usan comillas dobles `" "`):
  ```java
  char letra = 'A';
  char caracterUnicode = 'A'; // 'A' en código Unicode hexadecimal
  char saltoLinea = '
';          // Salto de línea (Line Feed)
  char tabulador = '	';           // Tabulación horizontal
  char barraInvertida = '\';      // Barra invertida literal
  char comillaSimple = ''';       // Comilla simple literal
  ```

---

## 6. Conversión de Tipos (*Type Casting*)

En muchas situaciones necesitamos transferir el valor almacenado en una variable de un tipo a otra variable de diferente tipo. En Java existen dos categorías de conversión:

```text
               ENSANCHAMIENTO (Automático / Seguro / Sin pérdida)
  byte ────► short ────► int ────► long ────► float ────► double
               char ────► int
  ◄──────────────────────────────────────────────────────────────
               ESTRECHAMIENTO (Explícito / 'Casting' forzado / Posible pérdida)
```

### 6.1 Conversión Implícita o Ensanchamiento (*Widening*)

Ocurre de manera completamente automática cuando se asigna un valor de un tipo con menor rango de memoria a una variable de un tipo de mayor rango. Como el contenedor de destino es más grande que el origen, **no existe posibilidad de desbordamiento ni pérdida de precisión**:

```java
int numeroPequeno = 100;
long numeroGrande = numeroPequeno;  // int se promociona automáticamente a long
double decimal = numeroGrande;      // long se promociona automáticamente a double (100.0)
```

### 6.2 Conversión Explícita o Estrechamiento (*Narrowing*)

Ocurre cuando intentamos forzar la introducción de un valor de mayor tamaño o precisión en un contenedor más pequeño. El compilador de Java lo prohibirá tajantemente a menos que el programador asuma la responsabilidad expresa mediante un **operador de moldeado o *cast***: `(tipoDestino) valor`.

:::caution[Peligros del Casting Explícito: Truncamiento y Desbordamiento (*Overflow*)]
El estrechamiento puede acarrear consecuencias drásticas:
1. **Pérdida de decimales (Truncamiento):** Al convertir de `double` a `int`, no se redondea: se cortan los decimales a machete.
   ```java
   double precio = 9.99;
   int precioEntero = (int) precio; // Vale 9 (se pierden los .99 decimales)
   ```
2. **Desbordamiento (*Overflow* numérico):** Si el valor excede el rango máximo del tipo destino, los bits se desbordan produciendo números absurdos o negativos:
   ```java
   int valorGrande = 130;
   byte valorByte = (byte) valorGrande; 
   // Un byte solo almacena de -128 a 127.
   // Al desbordarse 130, el bit de signo se activa y valorByte pasa a valer: -126
   System.out.println("Resultado de forzar 130 en byte: " + valorByte); // Imprime -126
   ```
:::

---

## 7. Operadores y Precedencia en Java

Los operadores son símbolos especiales que indican a la CPU qué cálculo o evaluación lógica ejecutar sobre uno, dos o tres operandos.

### 7.1 Operadores Aritméticos

| Operador | Significado | Ejemplo con `int a = 10, b = 3;` | Resultado | Observaciones |
|---|---|---|---|---|
| `+` | Suma o Concatenación | `a + b` | `13` | Si uno de los operandos es `String`, concatena texto. |
| `-` | Resta | `a - b` | `7` | También actúa como operador unario negativo (`-a`). |
| `*` | Multiplicación | `a * b` | `30` | |
| `/` | División | `a / b` | `3` | **¡Atención!** Si ambos operandos son enteros, la división es entera (trunca el resto). Para decimales, al menos uno debe ser `double` (`10.0 / 3` → `3.3333333333333335`). |
| `%` | Módulo (Resto de división) | `a % b` | `1` | Devuelve el residuo entero de `10 / 3`. Imprescindible para saber si un número es par (`n % 2 == 0`). |

### 7.2 Asignación Compuesta

Permiten abreviar operaciones en las que una variable se modifica a partir de su propio valor:
* `x += 5;` equivale exactamente a `x = x + 5;`
* `x -= 2;` equivale a `x = x - 2;`
* `x *= 3;` equivale a `x = x * 3;`
* `x /= 2;` equivale a `x = x / 2;`
* `x %= 10;` equivale a `x = x % 10;`

### 7.3 Incremento y Decremento: Prefijo vs Sufijo

Estos operadores unarios incrementan o reducen en 1 el valor de una variable. Sin embargo, su posición produce efectos colaterales muy diferentes si forman parte de una expresión:

* **Post-incremento (`x++`):** Primero **se utiliza el valor actual** de `x` en la evaluación de la expresión y, a continuación, se incrementa `x` en memoria.
* **Pre-incremento (`++x`):** Primero **se incrementa de inmediato** `x` en memoria y luego se utiliza el nuevo valor en la expresión.

```java
int a = 5;
int b = a++; // b recibe 5, y a continuación 'a' pasa a valer 6

int c = 5;
int d = ++c; // 'c' pasa de inmediato a valer 6, y d recibe 6
```

### 7.4 Operadores Relacionales o de Comparación

Comparan dos operandos y devuelven un resultado estrictamente booleano (`true` o `false`):

| Operador | Significado | Expresión (`x = 5`, `y = 8`) | Resultado |
|---|---|---|---|
| `==` | Igual a | `x == y` | `false` |
| `!=` | Distinto de | `x != y` | `true` |
| `<` | Menor que | `x < y` | `true` |
| `<=` | Menor o igual que | `x <= 5` | `true` |
| `>` | Mayor que | `x > y` | `false` |
| `>=` | Mayor o igual que | `y >= 8` | `true` |

### 7.5 Operadores Lógicos y Cortocircuito (*Short-Circuit Evaluation*)

Permiten combinar múltiples condiciones booleanas:

* `&&` (**AND lógico con cortocircuito**): Devuelve `true` si y solo si **ambos** operandos son verdaderos. Si el primer operando es `false`, la JVM **no se molesta en evaluar el segundo**, porque el resultado final será inexorablemente falso (cortocircuito).
* `||` (**OR lógico con cortocircuito**): Devuelve `true` si **al menos uno** de los operandos es verdadero. Si el primer operando es `true`, el segundo **no se evalúa**, porque el resultado ya está garantizado como verdadero.
* `!` (**NOT lógico o negación**): Invierte el valor de verdad (`!true` es `false`; `!false` es `true`).

```java
int divisor = 0;
int dividendo = 100;

// Gracias al cortocircuito, la siguiente línea NO lanza división por cero:
if (divisor != 0 && (dividendo / divisor > 10)) {
    System.out.println("Operación válida");
} else {
    System.out.println("División abortada de forma segura por cortocircuito");
}
```

### 7.6 El Operador Condicional Ternario (`? :`)

Es el único operador ternario de Java (requiere tres operandos). Constituye una contracción compacta de una sentencia `if-else` que devuelve un valor:

```java
// Sintaxis: variable = condicion ? valorSiVerdadero : valorSiFalso;
int edad = 19;
String estado = (edad >= 18) ? "Mayor de edad" : "Menor de edad";
System.out.println("El cliente es: " + estado);
```

### 7.7 Tabla de Precedencia de Operadores

Cuando una expresión combina operadores heterogéneos, Java los resuelve según su jerarquía de prioridad:

```text
Jerarquía de Precedencia (de Mayor a Menor prioridad):
1. Paréntesis:  ( )   [ ]   .
2. Postfijos:   expr++   expr--
3. Unarios:     ++expr   --expr   +expr   -expr   !   ~   (cast)
4. Multiplicativos:  *   /   %
5. Aditivos:         +   -
6. Desplazamiento:   <<   >>   >>>
7. Relacionales:     <   >   <=   >=   instanceof
8. Igualdad:         ==   !=
9. Lógicos a nivel de bits: &  ^  |
10. Lógicos condicionales:  &&
11. Lógicos condicionales:  ||
12. Ternario:        ? :
13. Asignación:      =   +=   -=   *=   /=   %=
```

:::tip[Regla de Oro: Ante la Duda, Usa Paréntesis]
Nunca confíes en recordar la tabla completa de memoria ni obligues a tus compañeros de equipo a descifrar expresiones crípticas como `a + b * c >> d && e`. **Utiliza paréntesis explícitos**: clarifican la intención de tu código, evitan errores sutiles y no penalizan en absoluto la velocidad de ejecución.
:::

---

## 8. Clases y Objetos Estándar de Uso Frecuente

Java incluye de fábrica miles de clases en su biblioteca estándar. En este punto inicial del curso, destacamos cuatro herramientas indispensables:

### 8.1 La Clase `String`: Manipulación de Cadenas de Texto

En Java, un texto no es un array de caracteres primitivo ni un tipo básico: **es un objeto de la clase `java.lang.String`**.

#### Inmutabilidad de los Strings
Una vez creado un objeto `String` en memoria, **su contenido no puede ser alterado jamás**. Cualquier método que aparente modificar una cadena (`toUpperCase()`, `replace()`, etc.) en realidad construye y devuelve un **nuevo objeto `String`** con el cambio aplicado, dejando la cadena original intacta.

```java
String saludo = "hola";
saludo.toUpperCase(); // ¡No cambia la variable saludo!
System.out.println(saludo); // Sigue imprimiendo "hola"

saludo = saludo.toUpperCase(); // Reasignamos la referencia al nuevo objeto
System.out.println(saludo); // Ahora imprime "HOLA"
```

#### Métodos Clave de la Clase `String`

```java
String texto = "  Desarrollo de Aplicaciones Multiplataforma (DAM)  ";

// 1. Longitud de la cadena (número de caracteres)
int longitud = texto.length(); // 52 caracteres

// 2. Limpieza de espacios en blanco en los extremos
String limpio = texto.strip(); // o texto.trim()

// 3. Obtener el carácter en un índice concreto (0-indexado)
char primeraLetra = limpio.charAt(0); // 'D'

// 4. Extracción de una subcadena [inicio, finExcluido)
String siglas = limpio.substring(44, 47); // "DAM"

// 5. Búsquedas y comprobaciones
boolean contiene = limpio.contains("Aplicaciones"); // true
boolean empieza = limpio.startsWith("Desarrollo");   // true
int posicion = limpio.indexOf("Multiplataforma");    // 26

// 6. Sustitución de caracteres o secuencias
String sustituido = limpio.replace("DAM", "1º DAM");

// 7. Conversión de caja
String mayusculas = limpio.toUpperCase();
String minusculas = limpio.toLowerCase();
```

:::caution[¡REGLA SAGRADA! Comparación de Strings con `.equals()`, NUNCA con `==`]
Este es, con diferencia, el error más letal que cometen los programadores noveles en Java:

* El operador `==` compara si dos variables apuntan **a la misma dirección física de memoria RAM**.
* El método `.equals()` compara si el **contenido textual** de dos cadenas es exactamente el mismo carácter a carácter.

```java
String s1 = "hola";
Scanner sc = new Scanner(System.in);
System.out.print("Escribe 'hola': ");
String s2 = sc.nextLine();

if (s1 == s2) {
    // ¡CASI NUNCA SE CUMPLE! Aunque hayas escrito 'hola', son objetos distintos en memoria.
}

if (s1.equals(s2)) {
    // ¡FORMA CORRECTA! Devuelve true si ambos contienen las letras h-o-l-a.
    System.out.println("Las cadenas son idénticas en contenido.");
}

// Para ignorar mayúsculas y minúsculas:
if (s1.equalsIgnoreCase(s2)) { ... }
```
:::

### 8.2 La Clase `Math`: Cálculos Matemáticos

La clase `java.lang.Math` ofrece constantes y métodos matemáticos estáticos que se invocan directamente sobre el nombre de la clase sin necesidad de crear objetos con `new`:

```java
// Constantes matemáticas fundamentales
double pi = Math.PI; // 3.141592653589793
double e = Math.E;   // 2.718281828459045

// Funciones habituales
double raiz = Math.sqrt(25.0);           // Raíz cuadrada: 5.0
double potencia = Math.pow(2.0, 8.0);     // 2 elevado a 8: 256.0
double absoluto = Math.abs(-42.5);        // Valor absoluto: 42.5
int maximo = Math.max(15, 29);            // Máximo entre dos números: 29
int minimo = Math.min(15, 29);            // Mínimo: 15
double hipotenusa = Math.hypot(3.0, 4.0); // Hipotenusa de catetos 3 y 4: 5.0

// Redondeos
long redondeo = Math.round(7.6);          // Redondea al entero más próximo: 8
double techo = Math.ceil(7.1);            // Redondeo hacia arriba: 8.0
double suelo = Math.floor(7.9);           // Redondeo hacia abajo (trunca): 7.0

// Generación de números pseudoaleatorios
// Math.random() genera un double aleatorio en el rango semiabierto [0.0, 1.0)
// Fórmula general para obtener un entero aleatorio en el rango [min, max]:
int min = 1, max = 6;
int dado = (int)(Math.random() * (max - min + 1)) + min;
System.out.println("Tirada de dado (1-6): " + dado);
```

### 8.3 Fechas y Horas Modernas: El Paquete `java.time`

Desde Java 8, se desaconseja por completo el uso de las vetustas clases `Date` y `Calendar` en favor de la moderna y robusta API `java.time`:

```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 1. Obtener la fecha y hora actuales del sistema
LocalDate hoy = LocalDate.now();
LocalTime horaActual = LocalTime.now();
LocalDateTime momentoExacto = LocalDateTime.now();

System.out.println("Hoy es: " + hoy); // Imprime: YYYY-MM-DD

// 2. Crear una fecha específica
LocalDate nacimiento = LocalDate.of(2004, 10, 25);

// 3. Formateo personalizado al estilo español
DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
System.out.println("Momento formateado: " + momentoExacto.format(formatoEspanol));
```

### 8.4 Aritmética de Alta Precisión: `BigInteger` y `BigDecimal`

En aplicaciones bancarias o astronómicas, los tipos primitivos `long` y `double` son insuficientes:
* `BigInteger` (`java.math.BigInteger`): Permite manejar números enteros de longitud teóricamente infinita (limitada únicamente por la RAM).
* `BigDecimal` (`java.math.BigDecimal`): Resuelve el clásico problema de imprecisión en coma flotante de `double` (donde `0.1 + 0.2 = 0.30000000000000004`), proporcionando control exacto de redondeo monetario.

```java
import java.math.BigDecimal;
import java.math.BigInteger;

// Factorial de 100 con BigInteger
BigInteger factorial = BigInteger.ONE;
for (int i = 1; i <= 100; i++) {
    factorial = factorial.multiply(BigInteger.valueOf(i));
}

// Cálculo monetario exacto con BigDecimal
BigDecimal precioUnitario = new BigDecimal("19.99");
BigDecimal cantidad = new BigDecimal("3");
BigDecimal total = precioUnitario.multiply(cantidad);
System.out.println("Total exacto en euros: " + total); // 59.97
```

### 8.5 Introducción a los Tipos Enumerados (`enum`)

A menudo un programa necesita representar un conjunto cerrado de opciones fijas (los días de la semana, los estados de una factura, los puntos cardinales). Usar números mágicos o cadenas de texto libres es propenso a erratas. Java ofrece los tipos enumerados para garantizar **seguridad de tipos en tiempo de compilación**:

```java
// Definición de una enumeración
public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
}

// Uso en el programa
DiaSemana diaHoy = DiaSemana.VIERNES;

if (diaHoy == DiaSemana.SABADO || diaHoy == DiaSemana.DOMINGO) {
    System.out.println("¡Es fin de semana!");
} else {
    System.out.println("Jornada lectiva en el instituto.");
}
```

:::tip[⭐ Be the Code: Seguridad de tipos frente a "cadenas mágicas"]
Imagina que en lugar de un `enum`, utilizas una variable de tipo `String` para controlar el día: `String dia = "LUNS";`. Si cometes una errata tipográfica al teclear, **el compilador de Java no se quejará en absoluto**: tu programa compilará con éxito y el fallo pasará completamente desapercibido hasta que un cliente lo descubra en producción.
Por el contrario, al utilizar `DiaSemana dia = DiaSemana.LUNS;`, **el compilador bloqueará la construcción en el acto**, subrayándolo en rojo e indicando que `LUNS` no existe en la enumeración. Un buen desarrollador siempre prefiere que un error salte en tiempo de compilación antes que en tiempo de ejecución.
:::

---

## 9. Entrada y Salida Estándar por Consola

La interacción entre el usuario humano y el programa a través de la consola de texto requiere dos canales: la salida estándar (`System.out`) y la entrada estándar (`System.in`).

### 9.1 Salida con Formato: `System.out.printf()`

Aunque `println()` es muy práctico para imprimir mensajes simples, cuando queremos mostrar tablas, alinear columnas o limitar el número de decimales, la función `System.out.printf()` (heredada de C) es insustituible:

```java
String producto = "Teclado Mecánico";
double precio = 89.954;
int cantidad = 2;
double total = precio * cantidad;

// Formateamos la salida con especificadores de formato
System.out.printf("Producto: %-20s | Uds: %03d | Total: %8.2f €%n", 
                  producto, cantidad, total);
```

#### Especificadores de Formato Principales:
* `%d`: Número entero decimal (`byte`, `short`, `int`, `long`).
* `%f`: Número de coma flotante (`float`, `double`). Podemos indicar precisión: `%.2f` redondea a 2 decimales.
* `%s`: Cadena de caracteres (`String`). Con `%-20s` alinea a la izquierda ocupando 20 espacios.
* `%c`: Carácter individual (`char`).
* `%b`: Valor booleano (`boolean`).
* `%n`: Salto de línea independiente de la plataforma (más seguro que `
`).

### 9.2 Lectura de Datos con la Clase `Scanner`

Para leer lo que el usuario escribe en el teclado, utilizamos la clase `java.util.Scanner`, envolviendo el flujo `System.in`:

```java
import java.util.Scanner;
import java.util.Locale;

public class EntradaDatos {
    public static void main(String[] args) {
        // Configuramos Scanner con Locale.US para que los decimales se introduzcan con punto (.)
        Scanner teclado = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Introduce tu nombre completo: ");
        String nombre = teclado.nextLine(); // Lee toda la línea hasta pulsar Enter

        System.out.print("Introduce tu edad: ");
        int edad = teclado.nextInt();       // Lee un número entero

        System.out.print("Introduce tu altura en metros (ej: 1.75): ");
        double altura = teclado.nextDouble();// Lee un número decimal

        System.out.printf("Hola %s. Tienes %d años y mides %.2f m.%n", nombre, edad, altura);
        
        teclado.close(); // Buena práctica: cerrar el recurso al finalizar
    }
}
```

:::caution[⚠️ El Clásico Problema del Salto de Línea en el Buffer de Scanner]
Casi todos los alumnos caen en esta trampa alguna vez:
Cuando ejecutas `nextInt()` o `nextDouble()`, el método lee los dígitos numéricos pero **deja el carácter de salto de línea (`
`) flotando dentro del buffer del teclado**. 

Si a continuación invocas inmediatamente un `nextLine()`, este consumirá de golpe ese `
` residual, interpretando que el usuario ha introducido una línea vacía y saltándose la pregunta sin dejarte escribir:

```java
System.out.print("Introduce tu edad: ");
int edad = teclado.nextInt(); // El usuario escribe "20" y pulsa Enter. El buffer contiene: '
'

// ¡SOLUCIÓN OBLIGATORIA! Limpiar el buffer consumiendo el salto de línea pendiente:
teclado.nextLine(); 

System.out.print("Introduce tu ciudad: ");
String ciudad = teclado.nextLine(); // Ahora sí espera a que el usuario escriba su ciudad
```
:::

:::tip[¿Cómo leer un único carácter con `Scanner`?]
Habrás notado que `Scanner` cuenta con métodos para casi todos los tipos primitivos (`nextInt()`, `nextDouble()`, `nextBoolean()`), pero **no dispone de ningún método `nextChar()`**.
Para leer un único carácter de teclado, la técnica idiomática en Java consiste en leer la siguiente palabra como texto con `next()` y extraer su primer carácter con `.charAt(0)`:
```java
System.out.print("¿Deseas continuar? (S/N): ");
char respuesta = teclado.next().toUpperCase().charAt(0);
```
:::

---

## 10. Sentencias de Decisión y Control de Flujo (RA4)

Hasta ahora nuestros programas han sido puramente secuenciales: cada instrucción se ejecuta indefectiblemente una tras otra. Las **sentencias de selección o decisión** permiten bifurcar el camino que sigue el procesador en función de que una o más condiciones booleanas se evalúen como verdaderas o falsas.

### 10.1 Decisión Simple y Doble: `if` e `if-else`

```java
double saldo = 150.0;
double cargo = 200.0;

if (saldo >= cargo) {
    saldo -= cargo;
    System.out.println("Cargo realizado con éxito. Saldo restante: " + saldo + " €");
} else {
    System.out.println("Operación denegada: Saldo insuficiente.");
}
```

### 10.2 Escaleras de Decisión Múltiple: `if - else if - else`

Cuando existen más de dos escenarios excluyentes, encadenamos condiciones:

```java
double nota = 7.8;

if (nota < 0.0 || nota > 10.0) {
    System.out.println("Error: Calificación inválida.");
} else if (nota < 5.0) {
    System.out.println("Suspenso");
} else if (nota < 7.0) {
    System.out.println("Aprobado");
} else if (nota < 9.0) {
    System.out.println("Notable");
} else if (nota < 10.0) {
    System.out.println("Sobresaliente");
} else {
    System.out.println("Matrícula de Honor");
}
```

### 10.3 Selección Múltiple Clásica: `switch` con `break`

La sentencia `switch` evalúa una única expresión y salta directamente a la etiqueta `case` cuyo valor coincida. Puede evaluar tipos `byte`, `short`, `char`, `int`, `String` y tipos `enum` (pero **no** `float` ni `double`):

```java
int opcionMenu = 2;

switch (opcionMenu) {
    case 1:
        System.out.println("Cargando partida guardada...");
        break; // Imprescindible para salir del switch
    case 2:
        System.out.println("Iniciando nueva partida...");
        break;
    case 3:
        System.out.println("Saliendo del juego.");
        break;
    default:
        System.out.println("Opción no reconocida.");
        break;
}
```

:::caution[El Peligro del *Fall-Through*]
En el `switch` clásico, si olvidas colocar la instrucción `break` al final de un `case`, el flujo de ejecución continuará cayendo en cascada (*fall-through*) y ejecutando el código de los siguientes bloques `case`, independientemente de que sus valores coincidan o no.
:::

### 10.4 Expresiones `switch` Modernas (Java 14/17/21 LTS)

Java modernizó profundamente la sintaxis de `switch` para erradicar los errores del `break` y permitir su uso tanto como sentencia como en calidad de **expresión que devuelve un valor**.

#### Ventajas de las Expresiones Switch con Flecha (`->`):
1. **Sin *fall-through*:** No requiere la instrucción `break`; solo se ejecuta la rama correspondiente.
2. **Casos Múltiples:** Permite agrupar varios valores en una sola línea separados por comas.
3. **Retorno de Valor:** El `switch` puede colocarse a la derecha de una asignación.
4. **Palabra clave `yield`:** Si una de las ramas requiere un bloque multilínea entre llaves `{ }`, se utiliza `yield` para producir el valor final retornado.

```java
int dia = 6;

// El switch moderno devuelve directamente un String asignado a la variable
String tipoDia = switch (dia) {
    case 1, 2, 3, 4, 5 -> "Día laborable";
    case 6, 7 -> {
        System.out.println("¡A disfrutar del descanso!");
        yield "Fin de semana"; // yield devuelve el valor dentro de un bloque
    }
    default -> throw new IllegalArgumentException("Día inválido: " + dia);
};

System.out.println("El día " + dia + " es: " + tipoDia);
```

---

## 11. Tipología Práctica de Errores en Java

Retomando la clasificación de la Unidad 1, al programar en Java nos enfrentamos a tres categorías de errores bien diferenciadas:

### 1. Errores de Compilación (Sintácticos y Semánticos Estáticos)
* **Cuándo ocurren:** Antes de que el programa pueda ejecutarse. El compilador `javac` analiza el código y detecta que se violan las reglas gramaticales de Java (olvidar un punto y coma, cometer erratas en palabras reservadas, tipos incompatibles como `int n = "hola";` o variables no declaradas).
* **Cómo te ayuda IntelliJ IDEA:** Subraya inmediatamente el error con una línea roja ondulada. Al colocar el cursor sobre ella y pulsar <kbd>Alt</kbd> + <kbd>Enter</kbd> (*Show Context Actions*), el IDE te ofrece sugerencias automáticas de corrección (*Quick-fixes*).

### 2. Errores en Tiempo de Ejecución (*Runtime Exceptions*)
* **Cuándo ocurren:** El código es sintácticamente impecable y compila sin problemas a Bytecode, pero la JVM se topa con una operación imposible al ejecutarse con determinados datos (dividir un número entero entre cero `10 / 0`, introducir letras en la consola cuando `Scanner.nextInt()` esperaba dígitos, o intentar acceder a una posición inexistente).
* **Cómo se manifiestan:** El programa aborta bruscamente lanzando una **Excepción** en la consola acompañada de un volcado de pila (*Stack Trace*) en texto rojo, indicando la clase, método y línea exacta del colapso:
  ```text
  Exception in thread "main" java.util.InputMismatchException
      at java.base/java.util.Scanner.throwFor(Scanner.java:947)
      at java.base/java.util.Scanner.nextInt(Scanner.java:2267)
      at es.iesperenxisa.primerospasos.Main.main(Main.java:14)
  ```
  *(En la Unidad 3 aprenderás a capturar y gestionar estas excepciones de forma elegante con bloques `try-catch`).*

### 3. Errores Lógicos (*Bugs*)
* **Cuándo ocurren:** El programa compila perfectamente y se ejecuta sin lanzar ninguna excepción, pero **el resultado obtenido es incorrecto** (por ejemplo, calcular una media dividiendo entre 2 en vez de entre 3, o aplicar una condición `>` en lugar de `>=`).
* **Cómo resolverlos:** Son los errores más desafiantes en la vida profesional. Para localizarlos, se recurre al **depurador (*Debugger*) de IntelliJ IDEA** (<kbd>Shift</kbd> + <kbd>F9</kbd>), colocando puntos de interrupción (*breakpoints*) para pausar la ejecución y ver paso a paso el contenido de cada variable en memoria RAM.

---

## 12. ⭐ Be the Code: Análisis de Traza y Memoria

Para ser un programador competente, debes ser capaz de "ejecutar" mentalmente el código como si fueras la propia CPU. Vamos a analizar dos de los enigmas más desconcertantes de Java.

### Desafío 1: La Trampa de los Operadores de Pre/Post-Incremento

Observa con máxima atención el siguiente fragmento de código:

```java
int x = 5;
int y = 10;
int z = ++x * 2 + y-- - x++ + y;
```

¿Qué valores exactos tienen `x`, `y` y `z` tras completarse la línea?

#### Análisis Paso a Paso de la CPU:

```text
ESTADO INICIAL DE MEMORIA:
  x = 5
  y = 10

EVALUACIÓN DE IZQUIERDA A DERECHA DE LA EXPRESIÓN:
  Término 1: ++x
    - Es un pre-incremento. 'x' pasa inmediatamente de 5 a 6 en memoria.
    - El término aporta el valor: 6.
    - Estado actual de variables: [x = 6, y = 10]

  Término 2: * 2
    - Se multiplica el 6 por 2: 6 * 2 = 12.

  Término 3: + y--
    - Es un post-decremento. Primero se usa el valor actual de 'y' (10) para la suma.
    - Acumulado provisional: 12 + 10 = 22.
    - Justo después de usarlo, 'y' se reduce en memoria a 9.
    - Estado actual de variables: [x = 6, y = 9]

  Término 4: - x++
    - Es un post-incremento. Primero se usa el valor actual de 'x' (6) para la resta.
    - Acumulado provisional: 22 - 6 = 16.
    - Justo después de usarlo, 'x' se incrementa en memoria a 7.
    - Estado actual de variables: [x = 7, y = 9]

  Término 5: + y
    - Se toma el valor actual de 'y' (9) y se suma.
    - Acumulado final de 'z': 16 + 9 = 25.

ESTADO FINAL DE MEMORIA:
  x = 7
  y = 9
  z = 25
```

### Desafío 2: El Enigma del *String Pool* y la Memoria Stack vs Heap

Considera este código:

```java
String a = "hola";
String b = "hola";
String c = new String("hola");

System.out.println(a == b);      // ¿Qué imprime?
System.out.println(a == c);      // ¿Qué imprime?
System.out.println(a.equals(c)); // ¿Qué imprime?
```

#### Lo que Ocurre en la Memoria RAM:

```text
PILA (Stack)                        MEMORIA DINÁMICA (Heap)
┌──────────────┐                     ┌──────────────────────────────────────────────┐
│  Ref. a      │ ──────────────────► │  String Pool (Piscina de Literales):         │
│  (Dir: 0x10) │                     │  ┌────────────────────────┐                  │
├──────────────┤                     │  │ Objeto: "hola" (0x10)  │                  │
│  Ref. b      │ ──────────────────► │  └────────────────────────┘                  │
│  (Dir: 0x10) │                     │                                              │
├──────────────┤                     │  Zona de Objetos Estándar:                   │
│  Ref. c      │ ─────────┐          │  ┌────────────────────────┐                  │
│  (Dir: 0x99) │          └────────► │  │ Objeto: "hola" (0x99)  │                  │
└──────────────┘                     │  └────────────────────────┘                  │
                                     └──────────────────────────────────────────────┘
```

1. **`a == b` imprime `true`:**
   * Al escribir literales directos (`"hola"`), Java optimiza la memoria guardándolos en una zona especial del Heap llamada **String Pool**.
   * Cuando defines `b = "hola"`, Java comprueba que ya existe un objeto idéntico en el pool y hace que `b` apunte exactamente a la misma dirección física que `a` (`0x10 == 0x10`).
2. **`a == c` imprime `false`:**
   * Al forzar la creación explícita con `new String("hola")`, obligas a la JVM a reservar un bloque de memoria nuevo e independiente en el Heap fuera del pool (`0x99`).
   * Como `0x10 != 0x99`, la comparación por identidad de direcciones físicas con `==` devuelve `false`.
3. **`a.equals(c)` imprime `true`:**
   * El método `equals` va a las dos direcciones de memoria, abre el contenido de ambos objetos y comprueba que ambos encierran la secuencia de letras `'h'`, `'o'`, `'l'`, `'a'`.

---

## 13. ¡No Hay Preguntas Tontas!

### ¿Por qué `1 / 2` da `0` en Java cuando cualquier calculadora dice `0.5`?
> Porque en Java rige la regla de **preservación de tipos en operaciones binarias**: si los dos operandos son de tipo entero (`int`), el operador división `/` ejecuta obligatoriamente una **división entera**, truncando cualquier resto o parte decimal. Para que el resultado conserve decimales, al menos uno de los operandos debe ser de tipo decimal: escribe `1.0 / 2`, `1 / 2.0` o aplica un cast explícito `(double) 1 / 2`.

### ¿Por qué `"test" + 2 + 3` produce `"test23"` pero `2 + 3 + "test"` produce `"5test"`?
> Por la **asociatividad de izquierda a derecha** del operador `+`:
> * En `2 + 3 + "test"`: Primero se evalúa `2 + 3`. Como ambos son enteros, se realiza una suma aritmética tradicional que da `5`. Luego se evalúa `5 + "test"`; al haber un `String`, se activa la concatenación textual produciendo `"5test"`.
> * En `"test" + 2 + 3`: Primero se evalúa `"test" + 2`, lo cual concatena produciendo la cadena `"test2"`. Después se evalúa `"test2" + 3`, que vuelve a concatenar dando como resultado `"test23"`.
> * ¿Y cuánto vale `"test" + 2 * 3`? La multiplicación tiene mayor precedencia que la suma: primero se calcula `2 * 3 = 6`, y luego se concatena dando `"test6"`.

### ¿Por qué es obligatorio poner una `F` al final de `float f = 3.14F;` pero no hace falta nada especial en `double d = 3.14;`?
> Porque en la especificación formal de Java, todo número con coma decimal escrito como literal en el código fuente es clasificado automáticamente como `double` (64 bits). Intentar meter un `double` de 64 bits en un `float` de 32 bits supone un estrechamiento con riesgo de pérdida de precisión que el compilador bloquea por seguridad. La `F` le indica explícitamente al compilador: *"Este literal debe crearse directamente como un float de 32 bits"*.

### Si `char` almacena letras, ¿por qué puedo escribir `char c = 65;` o hacer `c++`?
> Porque bajo el capó de la CPU, un `char` en Java no es más que un **número entero sin signo de 16 bits** que almacena el código numérico Unicode del símbolo correspondiente. El número `65` corresponde a la letra `'A'` en la tabla Unicode/ASCII. Si ejecutas `c++`, la celda de memoria pasa a valer `66`, que corresponde al símbolo `'B'`.

### ¿Puedo tener un proyecto con varias clases que contengan su propio método `main`?
> **Sí, rotundamente.** Puedes tener decenas de clases distintas con su correspondiente método `public static void main(String[] args)` en un mismo proyecto. Cada una de ellas representará un punto de entrada independiente. En IntelliJ IDEA puedes elegir cuál de ellas ejecutar haciendo clic derecho sobre el archivo y pulsando *Run 'NombreClase.main()'*.

### ¿Qué diferencia práctica hay entre `System.out.print` y `System.out.println`?
> `System.out.print()` imprime el texto indicado y mantiene el cursor de la consola en esa misma línea, justo a continuación del último carácter. Por su parte, `System.out.println()` imprime el texto e introduce automáticamente un salto de línea invisible (`
`), desplazando el cursor al principio de la siguiente línea de la consola.

---

## 14. Resumen de la Unidad y Enlace con Java Avanzado

En esta intensa unidad didáctica has dado el salto definitivo al desarrollo profesional:
* Has asimilado la arquitectura del ecosistema Java: cómo el compilador `javac` genera Bytecode neutral y cómo la **JVM** lo ejecuta a la velocidad de la luz mediante el compilador **JIT**.
* Has instalado y configurado el IDE de referencia en la industria: **IntelliJ IDEA**, dominando sus atajos esenciales (`psvm`, `sout`, `Shift+F10`, `Ctrl+Alt+L`).
* Has diseccionado la estructura de una clase Java y el método de arranque `public static void main`.
* Conoces al detalle los **8 tipos primitivos de datos**, sus tamaños en bits y rangos, y sabes evitar los peligros de desbordamiento en el **casting explícito**.
* Dominas la jerarquía de operadores, la evaluación en cortocircuito de `&&` y `||`, y las particularidades de pre/post-incremento.
* Conoces las herramientas más potentes del JDK básico: cadenas inmutables con `String`, funciones matemáticas con `Math`, fechas con `java.time`, precisión monetaria con `BigDecimal` y tipos enumerados con `enum`.
* Sabes formatear salidas elegantes con `printf` y leer datos del teclado con `Scanner`, previniendo el error del salto de línea residual.
* Controlas el flujo de decisión mediante estructuras `if-else` y las modernas expresiones `switch` con sintaxis de flecha `->` y `yield`.

En la **Unidad 3**, ampliaremos este control del flujo incorporando **bucles e iteraciones** (`while`, `for`, `do-while`), aprenderemos a gestionar situaciones anómalas con **excepciones** (`try-catch`), estructuraremos información masiva mediante **arrays** y dividiremos la lógica en subprogramas reutilizables mediante **métodos**.

---

## 15. Relación Curricular: Resultados de Aprendizaje y Criterios de Evaluación

Esta unidad cubre íntegramente los siguientes Resultados de Aprendizaje y Criterios de Evaluación del currículo oficial del módulo **0485 - Programación (DAM)**:

| Resultado de Aprendizaje (RA) | Criterios de Evaluación Oficiales Cubiertos |
|---|---|
| **RA1.** Reconoce la estructura de un programa informático, identificando y relacionando los elementos propios del lenguaje de programación utilizado. | **CE 1.a)** Se ha reconocido la estructura de un programa informático.<br/>**CE 1.b)** Se han identificado los componentes de la plataforma de desarrollo (JDK, JRE, JVM).<br/>**CE 1.c)** Se ha utilizado un entorno integrado de desarrollo (IntelliJ IDEA).<br/>**CE 1.d)** Se han utilizado las herramientas de compilación y ejecución de programas.<br/>**CE 1.e)** Se han identificado los diferentes tipos de variables y su ámbito de utilización.<br/>**CE 1.f)** Se ha reconocido la necesidad de utilizar constantes.<br/>**CE 1.g)** Se han utilizado los tipos de datos básicos y operadores provistos por el lenguaje.<br/>**CE 1.h)** Se han aplicado conversiones de tipo explícitas e implícitas.<br/>**CE 1.i)** Se han introducido comentarios explicativos y documentación adecuada (JavaDoc).<br/>**CE 1.j)** Se han utilizado clases estándar del lenguaje (`String`, `Math`, `Scanner`). |
| **RA4.** Desarrolla programas organizados en clases aplicando criterios de encapsulamiento y modularidad. | **CE 4.a)** Se han utilizado estructuras de control condicionales (`if`, `switch`).<br/>**CE 4.b)** Se han evaluado expresiones lógicas y relacionales complejas.<br/>**CE 4.c)** Se han aplicado buenas prácticas en el control del flujo del programa. |
| **RA5.** Realiza operaciones de entrada y salida de información. | **CE 5.a)** Se ha utilizado la consola para realizar operaciones de entrada y salida de información.<br/>**CE 5.b)** Se han aplicado formatos en la visualización de la información (`printf`).<br/>**CE 5.c)** Se han identificado las posibilidades de entrada/salida de la consola (`System.out`, `Scanner`). |

:::note[Aclaración curricular sobre los Resultados de Aprendizaje de la Unidad 2]
En la programación didáctica oficial del centro se asignan formalmente las 24 horas de esta unidad didáctica a **RA1** y **RA4**. No obstante, las competencias relativas a entrada y salida de datos por consola (`System.out`, `Scanner`, formatos con `printf`) se encuentran tipificadas en el Real Decreto estatal del título bajo el **RA5**. Se han integrado con total naturalidad en esta unidad para dotar al alumno de herramientas prácticas de interacción interactiva desde el primer día de trabajo en IntelliJ IDEA.
:::

---

## 16. Boletines de Ejercicios y Retos Prácticos

Pon en práctica de forma autónoma todos los conocimientos adquiridos a través de los boletines de ejercicios organizados por niveles:

<div class="ejercicio-links">
  <a class="elink" href="./boletin-02-inicial/">🟢 Boletín Inicial (Propuestos)</a>
  <a class="elink" href="./boletin-02-inicial-resuelto/">✅ Boletín Inicial (Resueltos)</a>
  <a class="elink" href="./boletin-02-intermedio/">⭐ Boletín Intermedio (Propuestos)</a>
  <a class="elink" href="./boletin-02-intermedio-resuelto/">✅ Boletín Intermedio (Resueltos)</a>
  <a class="elink" href="./boletin-02-extras/">🔥 Retos y Plataformas Externas</a>
</div>
