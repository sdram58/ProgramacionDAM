---
draft: true
prev: false
next: false
title: "⚡ Unidad 11 — Retos, Proyectos y Extras"
description: "Retos y proyectos avanzados de BDOO y NoSQL: arquitectura cliente-servidor en Db4o, patrón Generic Repository en MongoDB y migrador RDBMS a Documental."
---

:::tip[Retos de Nivel Arquitectónico]
Estos retos profundizan en arquitecturas distribuidas, abstracciones genéricas de acceso a datos y procesos de modernización de bases de datos relacionales hacia modelos documentales.
:::

---

## 🌐 Proyecto 1: Arquitectura Cliente / Servidor en Db4o

### Descripción del Escenario
Además del modo embebido donde la base de datos se abre exclusivamente dentro del proceso local de la JVM, Db4o permite trabajar en **modo Cliente/Servidor a través de la red TCP/IP**. 

Un servidor central aloja el fichero físico `.db4o` y atiende conexiones concurrentes de múltiples clientes autenticados:

```
[Cliente Java A (Swing / Consola)] ──── TCP:0485 ────┐
                                                     ▼
                                          [Servidor Db4o Central]
                                          (datos_red.db4o)
                                                     ▲
[Cliente Java B (Servicio Web)]    ──── TCP:0485 ────┘
```

### Código del Servidor Db4o

```java
package com.dam.boletin11.extras;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;

public class ServidorDb4o {
    private static final int PUERTO = 4850;
    private static final String FICHERO_BD = "datos_red.db4o";

    public static void main(String[] args) {
        // Abrimos el servidor en el puerto indicado
        ObjectServer server = Db4oClientServer.openServer(
                Db4oClientServer.newServerConfiguration(),
                FICHERO_BD,
                PUERTO
        );

        // Concedemos credenciales de acceso a los clientes
        server.grantAccess("alumnoDAM", "passwordSeguro123");

        System.out.println("Servidor Db4o activo escuchando en el puerto " + PUERTO + "...");
        System.out.println("Pulsa Enter para detener el servidor.");
        
        try {
            System.in.read();
        } catch (Exception ex) {
            // Espera
        } finally {
            server.close();
            System.out.println("Servidor Db4o detenido con éxito.");
        }
    }
}
```

### Código del Cliente Db4o Remoto

```java
package com.dam.boletin11.extras;

import com.dam.boletin11.inicial.model.Persona;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.cs.Db4oClientServer;

public class ClienteDb4o {
    public static void main(String[] args) {
        // Conexión por red al servidor central
        try (ObjectContainer cliente = Db4oClientServer.openClient(
                Db4oClientServer.newClientConfiguration(),
                "localhost",
                4850,
                "alumnoDAM",
                "passwordSeguro123")) {

            System.out.println("Conectado con éxito al servidor remoto de Db4o.");

            // Insertar datos a través de la red
            Persona p = new Persona("99999999X", "Cliente Remoto", 28);
            cliente.store(p);
            cliente.commit();

            // Consultar datos almacenados en el servidor
            ObjectSet<Persona> res = cliente.queryByExample(Persona.class);
            while (res.hasNext()) {
                System.out.println("• " + res.next());
            }
        }
    }
}
```

---

## 🍃 Proyecto 2: Repositorio Genérico para MongoDB (`MongoRepository<T, ID>`)

### Descripción
Diseña un patrón repositorio genérico para MongoDB similar a Spring Data, que abstraiga las operaciones CRUD tipadas para cualquier entidad:

```java
package com.dam.boletin11.extras;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericMongoRepository<T> {

    private final MongoCollection<T> coleccion;

    public GenericMongoRepository(MongoCollection<T> coleccion) {
        this.coleccion = coleccion;
    }

    public void guardar(T entidad) {
        coleccion.insertOne(entidad);
    }

    public Optional<T> buscarPorId(ObjectId id) {
        T entidad = coleccion.find(Filters.eq("_id", id)).first();
        return Optional.ofNullable(entidad);
    }

    public List<T> listarTodos() {
        List<T> lista = new ArrayList<>();
        coleccion.find().into(lista);
        return lista;
    }

    public boolean eliminarPorId(ObjectId id) {
        return coleccion.deleteOne(Filters.eq("_id", id)).getDeletedCount() > 0;
    }
}
```

---

## ⚡ Reto de Modernización: Migrador Automático RDBMS a NoSQL Documental

### Enunciado
Construye una utilidad `MigradorRelacionalADocumental` que lea las tablas normalizadas `clientes`, `facturas` y `lineas_factura` desde un motor relacional mediante JDBC, y las transforme en una colección NoSQL desnormalizada en MongoDB (`pedidos_consolidados`):
* En lugar de tres tablas separadas con claves foráneas, cada factura debe insertarse en MongoDB como un único documento raíz que contiene:
  * Los datos del cliente embebidos dentro de un subdocumento `"cliente"`.
  * Las líneas de factura como un array de subdocumentos `"lineas"`.
* Mide la diferencia de rendimiento al consultar una factura completa con todas sus líneas:
  * En RDBMS: requiere un `SELECT ... INNER JOIN ... INNER JOIN`.
  * En MongoDB: se recupera en una única lectura por clave `_id` sin ningún tipo de combinación en tiempo de consulta.

---

## ⚡ Parte 2: Micro-Retos de Persistencia NoSQL Avanzada

### Reto A: Agenda de Contactos con Documento Único y Arrays Anidados
Diseña una clase `AgendaMongo` que gestione una colección de contactos en MongoDB donde cada contacto es **un solo documento completo**:
* Campos del documento: `nombre` (cadena), `empresa` (cadena), `telefonos` (lista de cadenas), `etiquetas` (lista de cadenas).
* Métodos a implementar:
  1. `anadirContacto(String nombre, String empresa, List<String> telefonos, List<String> etiquetas)`
  2. `buscarPorNombre(String patron)`: búsqueda insensible a mayúsculas utilizando expresiones regulares (`Filters.regex("nombre", Pattern.quote(patron), "i")`).
  3. `anadirTelefono(String nombre, String nuevoTelefono)`: añade el teléfono a la lista sin necesidad de leer todo el documento previamente, utilizando `Updates.addToSet("telefonos", nuevoTelefono)` para evitar números duplicados.
  4. `eliminarContacto(String nombre)`: borra el documento que coincida con el nombre.

```java
package com.dam.boletin11.extras;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AgendaMongo {
    private final MongoCollection<Document> colContactos;

    public AgendaMongo(MongoCollection<Document> colContactos) {
        this.colContactos = colContactos;
    }

    public void anadirContacto(String nombre, String empresa, List<String> telefonos, List<String> etiquetas) {
        Document doc = new Document("nombre", nombre)
                .append("empresa", empresa)
                .append("telefonos", telefonos)
                .append("etiquetas", etiquetas);
        colContactos.insertOne(doc);
    }

    public List<Document> buscarPorNombre(String patron) {
        return colContactos.find(Filters.regex("nombre", Pattern.quote(patron), "i"))
                .into(new ArrayList<>());
    }

    public boolean anadirTelefono(String nombre, String nuevoTelefono) {
        return colContactos.updateOne(
                Filters.eq("nombre", nombre),
                Updates.addToSet("telefonos", nuevoTelefono)
        ).getModifiedCount() > 0;
    }

    public boolean eliminarContacto(String nombre) {
        return colContactos.deleteOne(Filters.eq("nombre", nombre)).getDeletedCount() > 0;
    }
}
```

---

### Reto B: Persistencia del Inventario de un Videojuego (POJO Embebido)
Implementa la persistencia del perfil y equipamiento de un jugador en MongoDB:
* Clase `Item(String nombre, String rareza, int poder)`.
* Clase `Jugador(String nick, int nivel, int puntos, List<Item> inventario)`.
* Empleando `PojoCodecProvider` o documentos BSON:
  1. Guarda un jugador con 3 ítems en su inventario en una única operación.
  2. Implementa un método `incrementarPuntuacion(String nick, int puntosExtra)` que actualice de forma atómica la puntuación del jugador mediante `Updates.inc("puntos", puntosExtra)` sin tener que descargar ni deserializar el documento entero en memoria.
  3. Realiza una consulta para obtener el TOP 5 de jugadores ordenados por puntuación descendente (`Sorts.descending("puntos")` y `limit(5)`).

