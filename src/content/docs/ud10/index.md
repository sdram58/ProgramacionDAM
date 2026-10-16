---
draft: true
prev: false
next: false
title: "🗄️ Unidad 10. Trabajar con bases de datos relacionales"
description: "Acceso a bases de datos relacionales desde Java mediante JDBC: conexiones seguras, sentencias preparadas contra inyección SQL, transacciones ACID y patrón DAO."
---

:::note[Información Curricular y Metadatos]
* **Módulo Profesional:** Programación (0485) — Ciclo Formativo de Grado Superior DAM / DAW.
* **Resultado de Aprendizaje Principal:** **RA9** — Gestiona información almacenada en bases de datos relacionales manteniendo la integridad y consistencia de los datos (20 horas lectivas).
  * **Criterio 9.a:** Se han identificado las características y métodos de acceso a sistemas gestores de bases de datos relacionales.
  * **Criterio 9.b:** Se han programado conexiones con bases de datos.
  * **Criterio 9.c:** Se ha escrito código para almacenar información en bases de datos.
  * **Criterio 9.d:** Se han creado programas para recuperar y mostrar información almacenada en bases de datos.
  * **Criterio 9.e:** Se han efectuado borrados y modificaciones sobre la información almacenada.
  * **Criterio 9.f:** Se han creado aplicaciones que ejecuten consultas sobre bases de datos.
  * **Criterio 9.g:** Se han creado aplicaciones para posibilitar la gestión de información presente en bases de datos.
* **Entorno Oficial:** IntelliJ IDEA (Java LTS + MySQL / SQLite / H2).
:::

---

## 1. Persistencia de Datos y el Modelo Relacional

Hasta este momento del curso, la información de nuestros programas residía en la memoria RAM (estructuras de datos volátiles) o en ficheros planos de disco (secuenciales, binarios o JSON/XML). 

Sin embargo, en aplicaciones empresariales reales, los datos exigen:
1. **Concurrencia:** Cientos o miles de usuarios leyendo y modificando información simultáneamente.
2. **Integridad y consistencia:** Reglas de validación estrictas, claves primarias únicas y claves foráneas que garanticen relaciones válidas.
3. **Seguridad y recuperación:** Copias de seguridad automáticas, permisos de acceso por usuario y recuperación ante caídas del servidor.
4. **Eficiencia en consultas masivas:** Indexación B-Tree y optimizadores de planes de ejecución para consultar millones de filas en milisegundos.

Estas garantías las proporcionan los **SGBD Relacionales (Sistemas Gestores de Bases de Datos Relacionales - RDBMS)**, tales como **MySQL, MariaDB, PostgreSQL, Oracle, SQL Server** o motores embebidos ligeros como **SQLite** y **H2**.

---

## 2. La Arquitectura JDBC (Java Database Connectivity)

**JDBC** es una API estándar de Java (`java.sql.*` y `javax.sql.*`) que define un conjunto homogéneo de interfaces abstractas para interactuar con cualquier base de datos relacional desde código Java.

```
+-------------------------------------------------------+
|               Aplicación Java (Tu Código)             |
+-------------------------------------------------------+
|         API Estándar JDBC (java.sql / javax.sql)       |
|   (Connection, PreparedStatement, ResultSet, etc.)    |
+-------------------------------------------------------+
|              DriverManager / DataSource               |
+-------------------------------------------------------+
        ↓                        ↓                    ↓
+---------------+        +---------------+    +---------------+
| Driver MySQL  |        | Driver Postgre|    | Driver SQLite |
| (Connector/J) |        | (pgJDBC)      |    | (sqlite-jdbc) |
+---------------+        +---------------+    +---------------+
        ↓                        ↓                    ↓
  Servidor MySQL          Servidor Postgre     Fichero .db
```

### 2.1. Tipos de Drivers JDBC
Los controladores (*drivers*) traducen las llamadas genéricas de Java al protocolo de red propietario de cada fabricante de base de datos. Se clasifican históricamente en cuatro tipos:
* **Tipo 1 (Puente JDBC-ODBC):** Obsoleto y retirado desde Java 8.
* **Tipo 2 (API Nativa):** Requiere librerías en C/C++ instaladas en la máquina cliente.
* **Tipo 3 (Protocolo de Red):** Utiliza un servidor intermedio que traduce las peticiones.
* **Tipo 4 (Protocolo Puro Java / Thin Driver):** **El estándar actual de la industria.** Es un archivo `.jar` autónomo que se comunica directamente por sockets TCP/IP con el servidor de bases de datos sin requerir ninguna instalación externa en el cliente.

### 2.2. Dependencias en IntelliJ IDEA (Maven / Gradle)
Para utilizar el driver de MySQL en un proyecto moderno de IntelliJ IDEA gestionado con Maven, añadimos en el fichero `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

:::note[Si tu proyecto utiliza módulos Java (module-info.java)]
En proyectos modulares, debes declarar explícitamente la dependencia en el fichero `module-info.java`:
```java
module mi.proyecto.dam {
    requires java.sql;       // Habilita java.sql y javax.sql
    requires java.desktop;   // Si se integra con Swing / JTable
}
```
:::

---

## 3. Conexión con la Base de Datos

### 3.1. La Cadena de Conexión (JDBC URL)
La URL de conexión tiene un formato estandarizado según el motor:

`jdbc:subprotocolo://host:puerto/nombre_base_datos?parametros`

* **MySQL:** `jdbc:mysql://localhost:3306/tienda_dam?serverTimezone=UTC&useSSL=false`
* **PostgreSQL:** `jdbc:postgresql://localhost:5432/tienda_dam`
* **SQLite (fichero local):** `jdbc:sqlite:datos_empresa.db`

### 3.2. Gestión de Conexiones con `try-with-resources`

Abrir una conexión a una base de datos implica una negociación de red TCP costosa y reserva recursos de memoria en el servidor. **Toda conexión debe cerrarse de forma estricta** cuando finalice su uso.

La interfaz `java.sql.Connection` extiende de `AutoCloseable`, lo que nos permite emplear la estructura `try-with-resources`:

```java
package com.dam.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConexion {

    private static final String URL = "jdbc:mysql://localhost:3306/instituto_dam?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "password123";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    public static void main(String[] args) {
        System.out.println("Intentando conectar con el SGBD...");
        
        try (Connection con = obtenerConexion()) {
            if (con != null && !con.isClosed()) {
                System.out.println("¡Conexión establecida con éxito!");
                System.out.println("Catálogo actual: " + con.getCatalog());
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión SQL: " + e.getErrorCode() + " - " + e.getMessage());
        }
        // La conexión queda garantizadamente cerrada al salir del bloque try
    }
}
```

---

## 4. Correspondencia de Tipos de Datos (SQL vs Java)

| Tipo SQL | Tipo Java Primitivo / Clase | Método ResultSet |
| :--- | :--- | :--- |
| `VARCHAR`, `CHAR`, `TEXT` | `java.lang.String` | `getString(col)` |
| `INT`, `INTEGER` | `int` / `Integer` | `getInt(col)` |
| `BIGINT` | `long` / `Long` | `getLong(col)` |
| `DECIMAL`, `NUMERIC` | `java.math.BigDecimal` (¡Precisión exacta!) | `getBigDecimal(col)` |
| `FLOAT`, `REAL` | `float` / `Float` | `getFloat(col)` |
| `DOUBLE` | `double` / `Double` | `getDouble(col)` |
| `BOOLEAN`, `TINYINT(1)` | `boolean` / `Boolean` | `getBoolean(col)` |
| `DATE` | `java.time.LocalDate` / `java.sql.Date` | `getDate(col).toLocalDate()` |
| `TIME` | `java.time.LocalTime` / `java.sql.Time` | `getTime(col).toLocalTime()` |
| `DATETIME`, `TIMESTAMP` | `java.time.LocalDateTime` / `java.sql.Timestamp` | `getTimestamp(col).toLocalDateTime()` |
| `BLOB` (Binary Large Object) | `byte[]` / `InputStream` | `getBytes(col)` / `getBinaryStream(col)` |

---

## 5. Ejecución de Sentencias: `Statement` vs `PreparedStatement`

En JDBC existen dos interfaces fundamentales para ejecutar sentencias SQL:
* **`Statement`:** Diseñado para sentencias estáticas sin parámetros. **Nunca debe usarse concatenando variables del usuario**, ya que provoca una vulnerabilidad crítica de inyección SQL (*SQL Injection*).
* **`PreparedStatement`:** **El estándar obligatorio.** Precompila la sentencia en el servidor, separa la estructura sintáctica del comando de los datos del usuario, y ofrece un rendimiento superior al reutilizar el plan de ejecución compilado.

### 5.1. Operaciones de Modificación DML (`INSERT`, `UPDATE`, `DELETE`)

Las modificaciones en la base de datos se ejecutan con el método `executeUpdate()`, que devuelve un entero con la cantidad de filas afectadas por la instrucción:

```java
public static boolean registrarAlumno(Alumno alumno) {
    String sql = "INSERT INTO alumnos (dni, nombre, ciclo, nota_media) VALUES (?, ?, ?, ?)";

    try (Connection con = GestorConexion.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        // Los índices posicionales en JDBC empiezan en 1 (¡no en 0!)
        ps.setString(1, alumno.getDni());
        ps.setString(2, alumno.getNombre());
        ps.setString(3, alumno.getCiclo());
        ps.setBigDecimal(4, alumno.getNotaMedia());

        int filasInsertadas = ps.executeUpdate();
        return filasInsertadas > 0;

    } catch (SQLException e) {
        System.err.println("Error al insertar alumno: " + e.getMessage());
        return false;
    }
}
```

### 5.2. Recuperación de Claves Primarias Autoincrementales (`RETURN_GENERATED_KEYS`)

Cuando la base de datos genera automáticamente la clave primaria (por ejemplo, mediante `AUTO_INCREMENT` en MySQL o `SERIAL` en PostgreSQL), podemos recuperar el identificador generado sin necesidad de lanzar una segunda consulta:

```java
String sql = "INSERT INTO pedidos (cliente_id, fecha, total) VALUES (?, ?, ?)";

try (Connection con = GestorConexion.obtenerConexion();
     PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    ps.setInt(1, 104);
    ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
    ps.setBigDecimal(3, new BigDecimal("149.95"));

    ps.executeUpdate();

    // Obtenemos el conjunto de claves generadas por el motor
    try (ResultSet rsClaves = ps.getGeneratedKeys()) {
        if (rsClaves.next()) {
            int nuevoId = rsClaves.getInt(1);
            System.out.println("Pedido creado con el ID autogenerado: " + nuevoId);
        }
    }
}
```

---

## 6. Consultas y Procesamiento con `ResultSet`

El método `executeQuery()` ejecuta consultas `SELECT` y devuelve un objeto `ResultSet`. 

Un `ResultSet` representa una tabla virtual con un **cursor** que inicialmente se posiciona justo antes de la primera fila (`beforeFirst`).
* `rs.next()`: Avanza el cursor a la siguiente fila. Devuelve `true` si hay una fila disponible y `false` cuando se alcanza el final de los resultados.
* `rs.getTipo(nombreColumna)` o `rs.getTipo(indiceColumna)`: Extrae el valor de la columna en la fila actual.

```java
public static List<Alumno> listarAlumnosAprobados() {
    List<Alumno> lista = new ArrayList<>();
    String sql = "SELECT id, dni, nombre, ciclo, nota_media FROM alumnos WHERE nota_media >= ? ORDER BY nombre ASC";

    try (Connection con = GestorConexion.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setBigDecimal(1, new BigDecimal("5.00"));

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String ciclo = rs.getString("ciclo");
                BigDecimal nota = rs.getBigDecimal("nota_media");

                Alumno al = new Alumno(id, dni, nombre, ciclo, nota);
                lista.add(al);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al consultar alumnos: " + e.getMessage());
    }

    return lista;
}
```

### 6.1. Detección de Valores `NULL` con `wasNull()`
En tipos primitivos como `int` o `double`, Java no puede almacenar `null`. Si una columna de la base de datos contiene `NULL`, `getInt()` devolverá `0`. Para saber si la columna contenía realmente un valor nulo:

```java
int edad = rs.getInt("edad");
if (rs.wasNull()) {
    System.out.println("El usuario no ha especificado su edad (valor NULL en BD).");
}
```

### 6.2. Tipos de `ResultSet` (Desplazamiento y Concurrencia)

Por defecto, un `ResultSet` avanza exclusivamente **hacia adelante** y es de **solo lectura**. Al crear un `Statement` o `PreparedStatement`, podemos especificar capacidades avanzadas:

```java
Statement st = con.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE, // Permite mover el cursor hacia atrás y saltar a filas arbitrarias
    ResultSet.CONCUR_READ_ONLY         // Modo de solo lectura
);
```

| Constante de Tipo | Capacidad de Desplazamiento |
| :--- | :--- |
| `ResultSet.TYPE_FORWARD_ONLY` | Solo permite avanzar con `next()`. Es el valor predeterminado por máxima eficiencia. |
| `ResultSet.TYPE_SCROLL_INSENSITIVE` | Permite libre movimiento (`first()`, `last()`, `previous()`, `absolute(n)`). No refleja cambios de otros usuarios. |
| `ResultSet.TYPE_SCROLL_SENSITIVE` | Cursor desplazable bidireccional que refleja los cambios concurrentes de la base de datos en tiempo real. |

| Constante de Concurrencia | Significado |
| :--- | :--- |
| `ResultSet.CONCUR_READ_ONLY` | El cursor no permite editar los datos subyacentes (predeterminado). |
| `ResultSet.CONCUR_UPDATABLE` | Permite actualizar o eliminar la fila actual llamando a `updateString()`, `updateRow()` o `deleteRow()`. |

---

## 7. Metadatos de la Base de Datos

JDBC permite consultar la estructura y configuración de la base de datos en tiempo de ejecución:

### 7.1. Metadatos de Resultados (`ResultSetMetaData`)
Permite conocer dinámicamente las columnas resultantes de cualquier consulta (útil para construir tablas visuales genéricas):

```java
ResultSetMetaData rsmd = rs.getMetaData();
int numColumnas = rsmd.getColumnCount();

for (int i = 1; i <= numColumnas; i++) {
    String nombre = rsmd.getColumnLabel(i);
    String tipo = rsmd.getColumnTypeName(i);
    System.out.println("Columna " + i + ": " + nombre + " [Tipo SQL: " + tipo + "]");
}
```

### 7.2. Metadatos del Sistema (`DatabaseMetaData`)
Permite conocer las capacidades del motor, versión del software, esquemas, catálogos y tablas existentes:

```java
DatabaseMetaData dbmd = con.getMetaData();
System.out.println("Motor de BD: " + dbmd.getDatabaseProductName());
System.out.println("Versión: " + dbmd.getDatabaseProductVersion());

// Listar todas las tablas de usuario de la base de datos
try (ResultSet tablas = dbmd.getTables(null, null, "%", new String[]{"TABLE"})) {
    while (tablas.next()) {
        System.out.println("Tabla encontrada: " + tablas.getString("TABLE_NAME"));
    }
}
```

---

## 8. Procedimientos y Funciones Almacenadas (`CallableStatement`)

Para invocar rutinas almacenadas (*Stored Procedures*) en el SGBD, utilizamos `CallableStatement`:

```sql
-- Procedimiento en MySQL
DELIMITER //
CREATE PROCEDURE calcular_salario_neto(IN p_bruto DECIMAL(10,2), OUT p_neto DECIMAL(10,2))
BEGIN
    SET p_neto = p_bruto * 0.81;
END //
DELIMITER ;
```

Invocación desde Java:

```java
String llamadaSql = "{call calcular_salario_neto(?, ?)}";

try (Connection con = GestorConexion.obtenerConexion();
     CallableStatement cs = con.prepareCall(llamadaSql)) {

    // 1. Establecer parámetro de entrada (IN)
    cs.setBigDecimal(1, new BigDecimal("2500.00"));

    // 2. Registrar el tipo de dato del parámetro de salida (OUT)
    cs.registerOutParameter(2, java.sql.Types.DECIMAL);

    // 3. Ejecutar la llamada
    cs.execute();

    // 4. Leer el resultado devuelto por la rutina
    BigDecimal salarioNeto = cs.getBigDecimal(2);
    System.out.println("Salario neto calculado por el SGBD: " + salarioNeto + " €");
}
```

### 8.1. Almacenamiento de Datos Binarios Grandes (`BLOB`)

Para almacenar archivos binarios (imágenes, documentos PDF o firmas digitalizadas) en la base de datos relacional, SQL proporciona el tipo `BLOB` (*Binary Large Object*). 

En JDBC, los datos binarios no se cargan como cadenas de texto, sino mediante flujos de entrada (`InputStream`):

```java
// Inserción de un archivo binario mediante un flujo de streaming
String sql = "INSERT INTO usuarios_avatares (usuario_id, foto) VALUES (?, ?)";

try (Connection con = GestorConexion.obtenerConexion();
     PreparedStatement ps = con.prepareStatement(sql);
     InputStream entradaFichero = new FileInputStream("recursos/avatar.png")) {

    ps.setInt(1, 42);
    ps.setBinaryStream(2, entradaFichero); // Envía los bytes directamente por socket
    ps.executeUpdate();
    System.out.println("Avatar binario guardado en la columna BLOB.");
}
```

Para recuperarlo desde el `ResultSet`:

```java
try (InputStream flujoLectura = rs.getBinaryStream("foto");
     OutputStream salidaFichero = new FileOutputStream("descargas/recuperado.png")) {
    flujoLectura.transferTo(salidaFichero);
}
```

---

## 9. Transacciones y Concurrencia (Propiedades ACID)

Una **transacción** es un conjunto de operaciones sobre la base de datos que deben ejecutarse como una **unidad atómica indivisible**: o se completan todas con éxito, o se deshacen por completo, dejando el sistema en su estado consistente previo.

Cumplen las propiedades **ACID**:
* **A (Atomicidad):** Todo o nada (*All or nothing*).
* **C (Consistencia):** La base de datos pasa de un estado válido a otro estado válido.
* **I (Aislamiento):** Las transacciones concurrentes no interfieren entre sí.
* **D (Durabilidad):** Una vez confirmada la transacción, los cambios persisten ante cualquier fallo eléctrico.

### 9.1. Control Manual de Transacciones en JDBC

Por defecto, toda conexión JDBC tiene activado el modo `autoCommit(true)`, lo que significa que cada sentencia `executeUpdate()` se confirma inmediatamente de forma individual. 

Para agrupar varias sentencias en una transacción:
1. Desactivar el autocommit: `con.setAutoCommit(false)`.
2. Ejecutar las sentencias preparadas.
3. Si todo va bien, confirmar los cambios: `con.commit()`.
4. Si ocurre cualquier error o excepción, revertir los cambios: `con.rollback()`.
5. Restaurar el autocommit en el bloque `finally` o cerrar la conexión.

```java
public static boolean transferirFondos(int cuentaOrigen, int cuentaDestino, BigDecimal importe) {
    String sqlRetirar = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ? AND saldo >= ?";
    String sqlIngresar = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?";

    Connection con = null;
    try {
        con = GestorConexion.obtenerConexion();
        
        // 1. INICIAR TRANSACCIÓN MANUAL
        con.setAutoCommit(false);

        // Paso A: Retirar dinero de origen
        try (PreparedStatement psRetirar = con.prepareStatement(sqlRetirar)) {
            psRetirar.setBigDecimal(1, importe);
            psRetirar.setInt(2, cuentaOrigen);
            psRetirar.setBigDecimal(3, importe);

            int filasRetiro = psRetirar.executeUpdate();
            if (filasRetiro == 0) {
                // Fondos insuficientes o cuenta no existe
                throw new SQLException("Saldo insuficiente en la cuenta de origen: " + cuentaOrigen);
            }
        }

        // Paso B: Ingresar dinero en destino
        try (PreparedStatement psIngresar = con.prepareStatement(sqlIngresar)) {
            psIngresar.setBigDecimal(1, importe);
            psIngresar.setInt(2, cuentaDestino);

            int filasIngreso = psIngresar.executeUpdate();
            if (filasIngreso == 0) {
                throw new SQLException("La cuenta destino no existe: " + cuentaDestino);
            }
        }

        // 2. CONFIRMAR TODOS LOS CAMBIOS DE FORMA ATÓMICA
        con.commit();
        System.out.println("Transferencia de " + importe + " € realizada con éxito.");
        return true;

    } catch (SQLException ex) {
        System.err.println("Error en la transferencia. Ejecutando ROLLBACK: " + ex.getMessage());
        if (con != null) {
            try {
                // 3. REVERTIR CUALQUIER CAMBIO PARCIAL
                con.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error grave en rollback: " + rollbackEx.getMessage());
            }
        }
        return false;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException closeEx) {
                // Registro de cierre
            }
        }
    }
}
```

---

## 10. Arquitectura Empresarial: El Patrón DAO (Data Access Object)

En una aplicación profesional, **el código SQL jamás debe mezclarse con la interfaz gráfica (Swing) ni con las clases de lógica de negocio**.

El patrón **DAO (Data Access Object)** centraliza todos los accesos a datos tras una interfaz pura de Java, desacoplando la tecnología de persistencia del resto de la aplicación.

```
+-------------------+        +----------------------+        +-----------------------+        +-------------+
|    Capa Vista     | =====⇒ |      Capa DAO        | =====⇒ |  Driver JDBC          | =====⇒ | Servidor BD |
| (Swing / Consola) |        | (AlumnoDAO / DAOImpl)|        | (Connection / PreStm) |        |   (MySQL)   |
+-------------------+        +----------------------+        +-----------------------+        +-------------+
         ↑                              ↑
         |                              |
         +--------- POJO / DTO ---------+
              (Clase Alumno pura)
```

### 10.1. El POJO de Dominio
```java
package com.dam.model;

import java.math.BigDecimal;

public class Alumno {
    private int id;
    private String dni;
    private String nombre;
    private String ciclo;
    private BigDecimal notaMedia;

    public Alumno() {}

    public Alumno(int id, String dni, String nombre, String ciclo, BigDecimal notaMedia) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.notaMedia = notaMedia;
    }

    // Getters, Setters, toString, equals y hashCode
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }
    public BigDecimal getNotaMedia() { return notaMedia; }
    public void setNotaMedia(BigDecimal notaMedia) { this.notaMedia = notaMedia; }
}
```

### 10.2. La Interfaz DAO
```java
package com.dam.dao;

import com.dam.model.Alumno;
import java.util.List;
import java.util.Optional;

public interface AlumnoDAO {
    boolean insertar(Alumno alumno);
    boolean actualizar(Alumno alumno);
    boolean eliminar(int id);
    Optional<Alumno> buscarPorId(int id);
    List<Alumno> listarTodos();
}
```

### 10.3. Integración con Interfaces Gráficas (`JTable`)
Gracias al patrón DAO, cargar datos de la base de datos en un `JTable` de Swing resulta trivial:

```java
public void cargarTablaDesdeBD(DefaultTableModel modeloTabla, AlumnoDAO alumnoDAO) {
    // 1. Limpiar filas previas de la tabla
    modeloTabla.setRowCount(0);

    // 2. Obtener la lista desacoplada de objetos Alumno
    List<Alumno> alumnos = alumnoDAO.listarTodos();

    // 3. Volcar los objetos en el modelo visual
    for (Alumno al : alumnos) {
        modeloTabla.addRow(new Object[]{
            al.getId(),
            al.getDni(),
            al.getNombre(),
            al.getCiclo(),
            al.getNotaMedia()
        });
    }
}
```

---

## 11. Conceptos de Producción: DataSources y Connection Pooling

En aplicaciones reales con miles de peticiones simultáneas (aplicaciones web o servicios REST), abrir y cerrar conexiones físicas a la base de datos con `DriverManager.getConnection()` es inviable, ya que la negociación TCP/TLS tarda decenas de milisegundos.

La solución estándar es un **Pool de Conexiones (Connection Pool)** gestionado mediante un `DataSource` (como **HikariCP**):
* Al arrancar la aplicación, el pool abre por adelantado un grupo fijo de conexiones activas (por ejemplo, 10 conexiones en reposo).
* Cuando un hilo necesita ejecutar una consulta, el pool le "presta" una conexión existente de inmediato (en microsegundos).
* Al invocar `connection.close()`, la conexión no se destruye físicamente ni se cierra el socket de red: simplemente se devuelve al pool lista para el siguiente usuario.

### 11.1. Mapeo Objeto-Relacional (ORM) e Introducción a JPA

A medida que las aplicaciones empresariales crecen, escribir a mano el código JDBC de mapeo (`ps.setString(1, e.getNombre())`, `rs.getString("nombre")`) para decenas de tablas se vuelve redundante y propenso a errores.

Un **ORM (Object-Relational Mapping)** automatiza la traducción entre las tablas SQL y las clases de Java. El estándar oficial en el ecosistema Java empresarial es **JPA (Jakarta Persistence API)**, implementado por motores como **Hibernate** o **EclipseLink**.

En JPA, las clases se decoran mediante anotaciones estándar:

```java
import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String nombre;

    private String puesto;

    private java.math.BigDecimal salario;

    // Constructores, getters y setters
}
```

La persistencia de un objeto se delega al gestor `EntityManager`, sin necesidad de escribir sentencias SQL a mano:

```java
EntityManager em = factoriaEntityManager.createEntityManager();
em.getTransaction().begin();

// El ORM genera y ejecuta automáticamente: INSERT INTO empleados (nombre, puesto, salario) VALUES (...)
em.persist(new Empleado("Laura Pérez", "Arquitecta de Software", new BigDecimal("3400.00")));

em.getTransaction().commit();
em.close();
```

:::note[Conexión Curricular: De JDBC a JPA]
Dominar JDBC nativo es indispensable: JPA no reemplaza a JDBC, sino que se ejecuta **sobre** JDBC. En situaciones de optimización extrema, consultas analíticas o depuración de cuellos de botella, el desarrollador sénior de DAM siempre analiza el comportamiento de la conexión JDBC subyacente.
:::

---

## 12. ⭐ Be the Code: La Anatomía de una Inyección SQL

Imaginemos un sistema de inicio de sesión con este código vulnerable basado en concatenación con `Statement`:

```java
// CÓDIGO GRAVEMENTE VULNERABLE:
String usuario = txtUsuario.getText(); // El atacante introduce: admin' -- 
String clave = txtClave.getText();     // El atacante introduce cualquier cosa

String sql = "SELECT * FROM usuarios WHERE usuario = '" + usuario + "' AND password = '" + clave + "'";
Statement st = con.createStatement();
ResultSet rs = st.executeQuery(sql);

if (rs.next()) {
    System.out.println("¡Acceso concedido como administrador!");
}
```

### ¿Qué sucede en el servidor de Base de Datos?
1. Al sustituir la variable, el comando SQL resultante enviado al motor es:
   `SELECT * FROM usuarios WHERE usuario = 'admin' -- ' AND password = 'xxx'`
2. En la sintaxis SQL, `-- ` marca el inicio de un comentario.
3. El motor de base de datos ignora por completo la comprobación de la contraseña (`AND password = ...`).
4. La consulta se evalúa como: *"Dame el usuario llamado admin"*. Encuentra la fila del administrador, devuelve un registro en el `ResultSet` y el atacante accede al sistema sin conocer la clave.

### La Solución Inmune: `PreparedStatement`
```java
String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, usuario);
    ps.setString(2, clave);
    try (ResultSet rs = ps.executeQuery()) {
        // ...
    }
}
```
Con `PreparedStatement`, el motor de base de datos compila la estructura de la consulta **antes** de recibir los datos. Cuando llegan los parámetros, el motor trata `'admin' -- ` como una simple cadena literal de texto dentro de comillas, jamás como código ejecutable. La inyección SQL queda totalmente neutralizada.

---

## 13. ¡No Hay Preguntas Tontas!

### ¿Por qué los índices en JDBC empiezan en 1 y no en 0 como los arrays de Java?
El estándar JDBC fue diseñado para armonizarse con el estándar ISO/ANSI SQL y las especificaciones X/Open de bases de datos relacionales, donde las columnas y parámetros de una sentencia se enumeran tradicionalmente empezando en 1.

### ¿Por qué no debemos usar nunca `float` o `double` para importes monetarios?
Los tipos `float` y `double` representan números en coma flotante bajo el estándar IEEE 754, que almacena aproximaciones binarias y acumula errores de redondeo (por ejemplo, `0.1 + 0.2` en double produce `0.30000000000000004`). En transacciones financieras y bases de datos empresariales, se debe utilizar siempre el tipo SQL `DECIMAL`/`NUMERIC` y su equivalente en Java `java.math.BigDecimal`, que garantiza una precisión decimal exacta sin pérdida de céntimos.

### ¿Qué diferencia hay entre `execute()`, `executeQuery()` y `executeUpdate()`?
* **`executeQuery()`:** Se usa exclusivamente para sentencias `SELECT`. Devuelve un objeto `ResultSet`.
* **`executeUpdate()`:** Se usa para sentencias DML (`INSERT`, `UPDATE`, `DELETE`) y DDL (`CREATE`, `DROP`). Devuelve un número entero indicando cuántas filas resultaron modificadas.
* **`execute()`:** Es un método genérico que se utiliza cuando no sabemos de antemano el tipo de sentencia SQL que se va a ejecutar (por ejemplo, en una consola interactiva de base de datos). Devuelve `true` si el resultado es un `ResultSet` y `false` si es un número de actualización.

### ¿Qué ocurre si no cierro una `Connection` o un `ResultSet`?
Se produce una **fuga de conexiones (Connection Leak)**. Cada conexión abierta mantiene un socket de red activo y reserva memoria en el servidor de la base de datos. Si una aplicación deja conexiones abiertas, llegará un momento en que el servidor agotará su límite máximo (`max_connections`) y denegará el acceso a cualquier otro usuario, bloqueando la empresa por completo.
