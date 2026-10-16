---
draft: true
prev: false
next: false
title: "🌐 Unidad 11. Trabajar con bases de datos orientadas a objetos y NoSQL"
description: "Persistencia de objetos nativa, superación del desfase objeto-relacional, motores BDOO (Db4o), arquitectura NoSQL documental con MongoDB y driver oficial de Java."
---

:::note[Información Curricular y Metadatos]
* **Módulo Profesional:** Programación (0485) — Ciclo Formativo de Grado Superior DAM / DAW.
* **Resultado de Aprendizaje Principal:** **RA8** — Utiliza bases de datos orientadas a objetos, analizando sus características y aplicando técnicas para mantener la persistencia de la información (17 horas lectivas).
  * **Criterio 8.a:** Se han identificado las características de las bases de datos orientadas a objetos.
  * **Criterio 8.b:** Se ha analizado su aplicación en el desarrollo de aplicaciones mediante lenguajes orientados a objetos.
  * **Criterio 8.c:** Se han instalado sistemas gestores de bases de datos orientados a objetos.
  * **Criterio 8.d:** Se han clasificado y analizado los distintos métodos soportados por los sistemas gestores para la gestión de la información almacenada.
  * **Criterio 8.e:** Se han creado bases de datos y las estructuras necesarias para el almacenamiento de objetos.
  * **Criterio 8.f:** Se han programado aplicaciones que almacenen objetos en las bases de datos creadas.
  * **Criterio 8.g:** Se han realizado programas para recuperar, actualizar y eliminar objetos de las bases de datos.
  * **Criterio 8.h:** Se han realizado programas para almacenar y gestionar tipos de datos estructurados, compuestos y relacionados.
* **Entorno Oficial:** IntelliJ IDEA (Java LTS + Db4o / MongoDB).
:::

---

## 1. El Desfase Objeto-Relacional (Object-Relational Impedance Mismatch)

En las unidades anteriores descubrimos la potencia del paradigma orientado a objetos en Java: encapsulamos datos y comportamientos en clases, construimos jerarquías polimórficas mediante herencia, enlazamos objetos en memoria a través de referencias y almacenamos colecciones heterogéneas.

Sin embargo, al intentar guardar esos objetos en una base de datos relacional tradicional (RDBMS), surge un conflicto conceptual severo conocido como el **desfase objeto-relacional**:

```
[Mundo Orientado a Objetos (Java)]          [Mundo Relacional (SQL RDBMS)]
• Grafo en red de objetos y referencias     • Tablas planas bidimensionales (filas y columnas)
• Herencia, clases abstractas, interfaces   • Claves foráneas (FK), tablas intermedias N:M
• Identidad por referencia en memoria (==)  • Identidad por clave primaria (PK)
• Encapsulación de estado y métodos        • Separación total entre datos y procedimientos
• Colecciones dinámicas (List, Set, Map)    • Filas atómicas normalizadas (1FN, 2FN, 3FN)
```

Para guardar un grafo de objetos en SQL, nos vemos obligados a "despedazar" el objeto: escribir sentencias `INSERT`, mapear manualmente decenas de columnas, crear tablas asociativas y realizar múltiples operaciones `JOIN` costosas para reconstruir el objeto en la lectura.

Frente a esta dificultad histórica nacieron dos grandes corrientes:
1. **BDOO (Bases de Datos Orientadas a Objetos Puras):** Sistemas diseñados para almacenar directamente el grafo de objetos de la memoria en disco tal cual, sin tablas intermedias (ej. **Db4o**, ObjectDB).
2. **Bases de Datos NoSQL y Documentales:** Motores flexibles de alto rendimiento que almacenan documentos semiestructurados (como JSON/BSON en **MongoDB**), eliminando los esquemas rígidos de las tablas tradicionales.

---

## 2. Bases de Datos Orientadas a Objetos: El Estándar ODMG

A finales de los años 90, el consorcio **ODMG (Object Database Management Group)** definió un estándar para unificar las BDOO:
* **Modelo de Objetos ODMG:** Extiende el modelo clásico de POJOs incorporando persistencia transparente.
* **ODL (Object Definition Language):** Lenguaje independiente del fabricante para especificar esquemas y relaciones entre clases de objetos.
* **OQL (Object Query Language):** Lenguaje de consultas declarativo similar a SQL pero con capacidad para navegar a través de relaciones punto a punto (`SELECT c.cliente.direccion.ciudad FROM Pedido c`).

---

## 3. El Motor BDOO Db4o (DataBase for Objects)

**Db4o** es uno de los motores BDOO embebidos más representativos en la historia de Java. Permite persistir cualquier clase estándar de Java (POJO) en un único archivo binario sin necesidad de configurar esquemas, tablas ni mapeos XML.

### 3.1. Arquitectura y Apertura del Contenedor de Objetos

La interfaz nuclear de Db4o es `ObjectContainer`, que representa tanto la conexión como la propia base de datos:

```java
package com.dam.bdoo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class GestorBDOO {

    private static final String FICHERO_BD = "datos_empresa.db4o";

    public static ObjectContainer abrirBaseDatos() {
        // Abre o crea el fichero binario de objetos en disco
        return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD);
    }
}
```

### 3.2. Operaciones Básicas CRUD en Db4o

#### Inserción Directa de Objetos
Para guardar un objeto en disco, simplemente se invoca a `store()`:

```java
public static void guardarEmpleado() {
    try (ObjectContainer db = GestorBDOO.abrirBaseDatos()) {
        Empleado emp = new Empleado("12345678Z", "Laura Sánchez", "Analista", 2800.0);
        
        // Persiste el objeto de inmediato sin definir tablas previas
        db.store(emp);
        db.commit();
        System.out.println("Objeto Empleado persistido con éxito en Db4o.");
    }
}
```

#### Consultas por Ejemplo (QBE - Query by Example)
QBE es el mecanismo más intuitivo de Db4o: se crea un "objeto prototipo" rellenando únicamente los atributos por los que deseamos filtrar. Los campos dejados a `null` o `0` actúan como comodines (*wildcards*):

```java
public static void buscarPorPuesto(String puestoBuscado) {
    try (ObjectContainer db = GestorBDOO.abrirBaseDatos()) {
        // Creamos una plantilla con el puesto deseado y el resto a null
        Empleado plantilla = new Empleado(null, null, puestoBuscado, 0.0);

        ObjectSet<Empleado> resultados = db.queryByExample(plantilla);

        System.out.println("Empleados encontrados en el puesto: " + puestoBuscado);
        while (resultados.hasNext()) {
            Empleado e = resultados.next();
            System.out.println("• " + e.getNombre() + " - " + e.getSalario() + " €");
        }
    }
}
```

#### Consultas Nativas (NQ - Native Queries)
Las consultas nativas permiten filtrar registros utilizando código Java puro con seguridad de tipos (*type-safe*), evitando cadenas de texto propensas a errores:

```java
List<Empleado> bienRemunerados = db.query(new Predicate<Empleado>() {
    @Override
    public boolean match(Empleado emp) {
        return emp.getSalario() >= 2500.0 && emp.getNombre().startsWith("L");
    }
});
```

#### Modificación y Borrado
```java
// Modificación: se recupera el objeto, se altera su estado y se vuelve a almacenar
ObjectSet<Empleado> set = db.queryByExample(new Empleado("12345678Z", null, null, 0.0));
if (set.hasNext()) {
    Empleado emp = set.next();
    emp.setSalario(3100.0);
    db.store(emp); // Actualiza la instancia existente
}

// Borrado: se retira de la persistencia con delete()
if (set.hasNext()) {
    db.delete(set.next());
}
```

---

## 4. Persistencia Avanzada: Composición, Herencia y Grafos

La verdadera ventaja de una BDOO frente a un RDBMS se aprecia al persistir relaciones complejas:

### 4.1. Objetos Compuestos y Colecciones
Si un objeto `Departamento` contiene una colección `List<Empleado>`, Db4o almacena la lista y todos los empleados referenciados en una única llamada a `db.store(departamento)`:

```java
public class Departamento {
    private String nombre;
    private List<Empleado> plantilla;

    public Departamento(String nombre) {
        this.nombre = nombre;
        this.plantilla = new ArrayList<>();
    }
    // Métodos para agregar empleados y getters
}
```

### 4.2. Persistencia Polimórfica y Herencia
Imaginemos una jerarquía donde `Desarrollador` y `Gerente` extienden de `Empleado`. En Db4o:
* `db.query(Empleado.class)` recupera automáticamente todas las instancias, incluyendo tanto a desarrolladores como a gerentes sin necesidad de sentencias `UNION` o `JOIN`.
* Se conserva la identidad exacta del tipo polimórfico en tiempo de ejecución.

### 4.3. Profundidad de Activación y Actualización (`activationDepth` y `updateDepth`)
En un grafo con cientos de objetos conectados entre sí (un cliente apunta a facturas, cada factura a líneas, cada línea a productos, cada producto a proveedores...), si Db4o cargara en memoria el grafo entero en cada consulta, la memoria RAM se agotaría.

* **Profundidad de Activación (*Activation Depth*):** Número de niveles de referencias que Db4o carga automáticamente desde el disco al consultar un objeto raíz (por defecto, 5 niveles).
* **Profundidad de Actualización (*Update Depth*):** Determina si al llamar a `store(objeto)` se deben actualizar también recursivamente los objetos anidados modificados.

### 4.4. Evolución y Estado Actual de las BDOO: ObjectDB

Aunque Db4o es un referente didáctico inmejorable por su sencillez conceptual, en entornos de producción actuales existen motores BDOO comerciales de alto rendimiento como **ObjectDB**. 

La gran ventaja de ObjectDB es que se gestiona mediante la API estándar **JPA (Jakarta Persistence API)** que vimos en la Unidad 10:
* Utiliza las mismas anotaciones oficiales (`@Entity`, `@Id`, `@OneToMany`, etc.).
* Se programa con los mismos métodos (`em.persist(objeto)`, `em.find(Clase.class, id)`).
* **La diferencia fundamental:** Por debajo no hay tablas relacionales ni sentencias SQL; ObjectDB serializa el grafo de objetos directamente en páginas de almacenamiento binario optimizado, eliminando el coste de traducción del ORM.

---

## 5. El Ecosistema NoSQL y la Era del Big Data

A medida que internet creció exponencialmente, gigantes tecnológicos (Google, Amazon, Facebook) descubrieron que las bases de datos relacionales tradicionales presentaban serias barreras para escalar horizontalmente:

```
[Escalabilidad Vertical (SQL Tradicional)]
Comprar un servidor más grande, con más CPUs y más RAM (Coste exponencial, límite físico).

[Escalabilidad Horizontal (NoSQL Sharding)]
Repartir los datos entre decenas o cientos de servidores económicos en clúster (Escalabilidad infinita).
```

### 5.1. El Teorema CAP
Formulado por Eric Brewer, establece que en un sistema distribuido en red solo es posible garantizar simultáneamente **dos** de las siguientes tres propiedades:

```
                  Consistencia (C)
                        ▲
                       / \
                      /   \
                     /     \
 Disponibilidad (A) ◄-------► Tolerancia a Particiones (P)
```

1. **C (Consistencia):** Todos los clientes leen exactamente los mismos datos al mismo tiempo.
2. **A (Disponibilidad):** Cada petición recibe una respuesta sin errores, aunque un nodo falle.
3. **P (Tolerancia a Particiones):** El sistema sigue funcionando aunque se caiga la comunicación entre servidores.

Los sistemas NoSQL suelen elegir **AP** (sacrificando consistencia inmediata a cambio de *consistencia eventual - BASE*) o **CP** (priorizando consistencia estricta en particiones).

### 5.2. Familias de Bases de Datos NoSQL
* **Clave-Valor (*Key-Value*):** Altísima velocidad en memoria RAM (ej. **Redis**).
* **Documentales (*Document Stores*):** Almacenan documentos JSON/BSON semiestructurados (ej. **MongoDB**, CouchDB).
* **Columnares (*Wide-Column*):** Consultas masivas sobre atributos específicos (ej. **Apache Cassandra**, HBase).
* **Orientadas a Grafos (*Graph Databases*):** Diseñadas para redes sociales y conexiones complejas (ej. **Neo4j**).

---

## 6. Bases de Datos Orientadas a Documentos: MongoDB

**MongoDB** es el motor NoSQL más utilizado del mundo. Almacena la información en formato **BSON (Binary JSON)** agrupados en **colecciones**.

### 6.1. Correspondencia Terminológica

| Concepto Relacional (RDBMS) | Equivalente en MongoDB |
| :--- | :--- |
| Base de Datos (*Database*) | Base de Datos (*Database*) |
| Tabla (*Table*) | **Colección (*Collection*)** |
| Fila / Registro (*Row / Record*) | **Documento (*Document*)** |
| Columna (*Column*) | **Campo (*Field*)** |
| Clave Primaria (`PRIMARY KEY`) | Campo `_id` (`ObjectId`) |
| Índice (*Index*) | Índice (*Index*) |
| `JOIN` relacional | Documentos embebidos / etapa lookup |

### 6.2. La Estructura del `ObjectId` (12 Bytes)
En MongoDB, cada documento posee una clave primaria única `_id` formada por un `ObjectId` de 12 bytes empaquetados:
* **4 bytes:** Marca de tiempo UNIX (*timestamp* en segundos desde 1970). Permite ordenar cronológicamente sin índice extra.
* **5 bytes:** Identificador único aleatorio por proceso y máquina.
* **3 bytes:** Contador incremental que garantiza unicidad local.

### 6.3. Decisiones de Modelado: Embeber vs Referenciar

Al no existir operaciones `JOIN` tradicionales en el motor documental, la arquitectura de los datos se rige por dos estrategias de diseño:

1. **Embeber Documentos (*Embedded Documents*):**
   * El subdocumento o array anidado se almacena dentro del documento padre (ej. los ítems o líneas de una factura, las direcciones de un cliente).
   * **Ventajas:** Lectura atómica ultrarrápida en una sola consulta por clave primaria, sin cruces ni bloqueos de red.
   * **Cuándo usarlo:** Relaciones "1 a pocos" o de contención lógica donde los elementos no tienen sentido de existencia independiente fuera del documento padre.
2. **Referenciar Documentos (*Normalized References*):**
   * Se almacena únicamente el identificador `_id` de la entidad foránea (similar a una clave ajena en SQL).
   * **Ventajas:** Evita la duplicación de datos y previene documentos gigantescos que superen el límite físico de 16 MB por documento en MongoDB.
   * **Cuándo usarlo:** Relaciones "Muchos a Muchos" (N:M) o "1 a miles/millones" (ej. un sensor con millones de lecturas históricas).

---

## 7. MongoDB desde Java Moderno: Driver Oficial `mongodb-driver-sync`

### 7.1. Configuración de la Dependencia Maven
En el archivo `pom.xml`:

```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>5.0.0</version>
</dependency>
```

### 7.2. Conexión y Gestión del Cliente

```java
package com.dam.nosql;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    private static final String CADENA_CONEXION = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "empresa_dam";

    public static MongoClient crearCliente() {
        return MongoClients.create(CADENA_CONEXION);
    }

    public static MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(NOMBRE_BD);
    }
}
```

### 7.3. Operaciones CRUD con Documentos BSON

#### Inserción de Documentos
```java
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;

try (MongoClient cliente = ConexionMongo.crearCliente()) {
    MongoDatabase db = ConexionMongo.obtenerBaseDatos(cliente);
    MongoCollection<Document> colAlumnos = db.getCollection("alumnos");

    Document doc = new Document("nombre", "Carlos Gómez")
            .append("ciclo", "DAM")
            .append("edad", 21)
            .append("asignaturas", Arrays.asList("Programación", "Bases de Datos", "Sistemas"))
            .append("direccion", new Document("calle", "Gran Vía").append("numero", 45));

    colAlumnos.insertOne(doc);
    System.out.println("Documento insertado con _id autogenerado: " + doc.getObjectId("_id"));
}
```

#### Consultas con Filtros (`Filters`)
El driver de Java proporciona la factoría estática `Filters` para escribir consultas de forma limpia y legible:

```java
import static com.mongodb.client.model.Filters.*;

// Buscar alumnos de DAM mayores o iguales a 20 años
for (Document doc : colAlumnos.find(and(eq("ciclo", "DAM"), gte("edad", 20)))) {
    System.out.println("• " + doc.getString("nombre") + " - Edad: " + doc.getInteger("edad"));
}
```

#### Actualizaciones con Operadores Atómicos (`Updates`)
```java
import static com.mongodb.client.model.Updates.*;

// Incrementar la edad en 1 y actualizar el ciclo
colAlumnos.updateOne(
    eq("nombre", "Carlos Gómez"),
    combine(set("ciclo", "DAM 2º"), inc("edad", 1))
);
```

#### Borrado de Documentos
```java
colAlumnos.deleteOne(eq("nombre", "Carlos Gómez"));
```

---

## 8. Mapeo Automático de POJOs en MongoDB con `PojoCodecProvider`

En lugar de construir objetos `Document` a mano campo a campo, el driver moderno de MongoDB permite serializar y deserializar clases POJO estándar automáticamente:

```java
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

// 1. Configuramos el registro con el proveedor de POJOs automático
CodecRegistry pojoCodecRegistry = fromRegistries(
    getDefaultCodecRegistry(),
    fromProviders(PojoCodecProvider.builder().automatic(true).build())
);

// 2. Obtenemos la base de datos con el registro tipado
MongoDatabase db = cliente.getDatabase("empresa_dam").withCodecRegistry(pojoCodecRegistry);

// 3. Obtenemos la colección tipada directamente a nuestra clase Java Alumno
MongoCollection<Alumno> colAlumnos = db.getCollection("alumnos", Alumno.class);

// 4. Inserción transparente
Alumno alumno = new Alumno("Elena Torres", "DAW", 23);
colAlumnos.insertOne(alumno);

// 5. Consulta fuertemente tipada
for (Alumno al : colAlumnos.find(eq("ciclo", "DAW"))) {
    System.out.println("Alumno POJO: " + al.getNombre() + " (" + al.getEdad() + " años)");
}
```

---

## 9. Comparativa Global: RDBMS vs BDOO vs NoSQL vs ORM

| Característica | RDBMS Relacional (MySQL / PostgreSQL) | BDOO Pura (Db4o / ObjectDB) | NoSQL Documental (MongoDB) | Mapeo ORM (Hibernate / JPA) |
| :--- | :--- | :--- | :--- | :--- |
| **Modelo de Datos** | Tablas relacionales con esquema estricto | Grafo de objetos nativo en memoria | Documentos semiestructurados BSON/JSON | Tablas mapeadas a objetos Java |
| **Lenguaje de Consulta** | SQL estándar | QBE / NQ / SODA | JSON Queries / Filters API | JPQL / HQL / Criteria |
| **Desfase Objeto-Relacional** | Alto (requiere mapeo manual) | Nulo (persistencia directa) | Muy bajo (documento similar a objeto) | Resuelto por el framework |
| **Transacciones ACID** | Nativas, estrictas y maduras | Nativas en contenedor | Soportadas a nivel de documento y multi-doc | Depende de la base subyacente |
| **Escalabilidad Horizontal** | Compleja (réplicas maestro-esclavo) | Limitada (entornos embebidos) | Nativa e inmediata (*sharding*) | Depende de la base subyacente |
| **Caso de Uso Ideal** | Banca, contabilidad, ERP, datos muy relacionados | CAD/CAM, simulaciones, software embebido | Catálogos web, Big Data, analítica rápida | Aplicaciones empresariales Java estándar |

---

## 10. ⭐ Be the Code: La Persistencia de un Grafo de Objetos Circular

Analicemos qué ocurre al guardar un grafo con referencias circulares:

```java
// Persona posee un Coche y Coche conoce a su Propietario
Persona p = new Persona("Marcos");
Coche c = new Coche("Toyota", p);
p.setCoche(c);
```

### ¿Cómo lo resuelve una Base de Datos Relacional (SQL)?
1. No se pueden insertar simultáneamente: la tabla `personas` tiene una clave foránea `coche_id`, y la tabla `coches` tiene una clave foránea `propietario_id`.
2. Una de las dos claves foráneas debe declararse obligatoriamente como anulable (`NULL`).
3. El desarrollador debe ejecutar tres sentencias SQL:
   * `INSERT INTO personas (nombre, coche_id) VALUES ('Marcos', NULL);`
   * `INSERT INTO coches (marca, propietario_id) VALUES ('Toyota', id_persona);`
   * `UPDATE personas SET coche_id = id_coche WHERE id = id_persona;`

### ¿Cómo lo resuelve una BDOO (Db4o) o NoSQL Documental?
1. En Db4o: se invoca `db.store(p)`. El motor analiza la dirección de memoria de cada objeto, detecta la referencia circular mediante su tabla interna de identidades y serializa ambos objetos en disco resolviendo los punteros automáticamente sin bloquearse en un bucle infinito.
2. En MongoDB: se puede almacenar como un único documento embebido (donde el coche está anidado dentro de la persona), o referenciando el `_id` de forma independiente.

---

## 11. ¡No Hay Preguntas Tontas!

### Si las BDOO eran tan ideales para los objetos, ¿por qué no sustituyeron por completo a SQL?
Las bases de datos relacionales ya contaban con décadas de optimizaciones matemáticas (álgebra relacional), herramientas de reporting universales, y la inmensa mayoría de las empresas ya tenían sus datos almacenados en SQL. Además, la aparición de frameworks ORM como Hibernate permitió a los programadores trabajar con objetos en Java sin renunciar a la seguridad y madurez de los servidores relacionales.

### ¿Qué significa exactamente el término "NoSQL"?
Actualmente significa **"Not Only SQL" (No Solo SQL)**. No implica rechazar el lenguaje SQL, sino reconocer que para ciertas aplicaciones masivas o con esquemas cambiantes, existen alternativas no relacionales más eficientes y escalables. De hecho, muchas bases NoSQL actuales incorporan variantes de SQL para facilitar las consultas.

### ¿Es MongoDB adecuado para un sistema bancario o contable?
Tradicionalmente los bancos han confiado en motores relacionales (Oracle, PostgreSQL) por sus garantías transaccionales ACID estrictas y modelos de datos normalizados. Aunque las versiones modernas de MongoDB incorporan transacciones distribuidas multi-documento, los entornos donde la consistencia matemática inmediata y la prevención de anomalías contables son críticas siguen prefiriendo mayoritariamente bases de datos relacionales.

### ¿Puedo tener bases de datos políglotas en un mismo proyecto empresarial?
**Absolutamente sí, y es el estándar en la arquitectura de microservicios moderna:** la persistencia políglota (*Polyglot Persistence*) consiste en utilizar la base de datos óptima para cada subsistema. Por ejemplo, un RDBMS relacional para la facturación y pagos, Redis para la gestión de sesiones en memoria y caché de alta velocidad, y MongoDB o Elasticsearch para el catálogo de productos y búsquedas de texto libre.
