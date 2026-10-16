---
draft: true
prev: false
next: false
title: "📝 Unidad 11 — Boletín Inicial (Resuelto)"
description: "Soluciones completas, comentadas y explicadas paso a paso para el boletín inicial de bases de datos orientadas a objetos (Db4o) y NoSQL (MongoDB)."
---

:::note[Entornos y Dependencias]
Las soluciones cubren tanto el motor BDOO embebido Db4o como el driver oficial sincrónico de MongoDB (`org.mongodb:mongodb-driver-sync`).
:::

---

### Solución Ejercicio 1: Apertura de Contenedor e Inserción Básica en Db4o

#### Clase Modelo `Persona`
```java
package com.dam.boletin11.inicial.model;

import java.io.Serializable;

public class Persona implements Serializable {
    private String dni;
    private String nombre;
    private int edad;

    public Persona() {}

    public Persona(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    @Override
    public String toString() {
        return "Persona{dni='" + dni + "', nombre='" + nombre + "', edad=" + edad + "}";
    }
}
```

#### Programa de Inserción
```java
package com.dam.boletin11.inicial;

import com.dam.boletin11.inicial.model.Persona;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Ejercicio01InsercionDb4o {
    private static final String FICHERO_BD = "personas_inicial.db4o";

    public static void main(String[] args) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            Persona p1 = new Persona("11111111A", "Ana Morales", 24);
            Persona p2 = new Persona("22222222B", "Bernardo Gómez", 35);
            Persona p3 = new Persona("33333333C", "Carla Vidal", 24);

            db.store(p1);
            db.store(p2);
            db.store(p3);

            db.commit();
            System.out.println("Tres objetos Persona almacenados directamente en el contenedor Db4o.");
        }
    }
}
```

---

### Solución Ejercicio 2: Consultas por Ejemplo (QBE) en Db4o

```java
package com.dam.boletin11.inicial;

import com.dam.boletin11.inicial.model.Persona;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ejercicio02ConsultasQBE {
    private static final String FICHERO_BD = "personas_inicial.db4o";

    public static void buscarPorEdad(int edadBuscada) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            // Plantilla con edad fijada y cadenas a null
            Persona plantilla = new Persona(null, null, edadBuscada);
            ObjectSet<Persona> resultados = db.queryByExample(plantilla);

            System.out.println("Personas con " + edadBuscada + " años:");
            while (resultados.hasNext()) {
                System.out.println("• " + resultados.next());
            }
        }
    }

    public static void main(String[] args) {
        buscarPorEdad(24);
    }
}
```

---

### Solución Ejercicio 3: Modificación y Borrado en Db4o

```java
package com.dam.boletin11.inicial;

import com.dam.boletin11.inicial.model.Persona;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ejercicio03ModificarBorrarDb4o {
    private static final String FICHERO_BD = "personas_inicial.db4o";

    public static void actualizarEdad(String dni, int nuevaEdad) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            ObjectSet<Persona> res = db.queryByExample(new Persona(dni, null, 0));
            if (res.hasNext()) {
                Persona p = res.next();
                p.setEdad(nuevaEdad);
                db.store(p); // Actualiza la instancia persistente
                db.commit();
                System.out.println("Edad actualizada con éxito para " + p.getNombre());
            }
        }
    }

    public static void eliminar(String dni) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            ObjectSet<Persona> res = db.queryByExample(new Persona(dni, null, 0));
            if (res.hasNext()) {
                Persona p = res.next();
                db.delete(p);
                db.commit();
                System.out.println("Persona eliminada del contenedor: " + dni);
            }
        }
    }

    public static void main(String[] args) {
        actualizarEdad("11111111A", 25);
        eliminar("33333333C");
    }
}
```

---

### Solución Ejercicio 4: Consultas Nativas (Native Queries) en Db4o

```java
package com.dam.boletin11.inicial;

import com.dam.boletin11.inicial.model.Persona;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.List;

public class Ejercicio04ConsultasNativas {
    private static final String FICHERO_BD = "personas_inicial.db4o";

    public static void main(String[] args) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            // Consulta nativa fuertemente tipada con Predicate
            List<Persona> lista = db.query(new Predicate<Persona>() {
                @Override
                public boolean match(Persona p) {
                    return p.getEdad() >= 18 && p.getEdad() <= 30 && p.getNombre().startsWith("A");
                }
            });

            System.out.println("Resultados de consulta nativa NQ:");
            for (Persona p : lista) {
                System.out.println("-> " + p);
            }
        }
    }
}
```

---

### Solución Ejercicio 5: Conexión e Inserción de Documentos en MongoDB

```java
package com.dam.boletin11.inicial;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Ejercicio05InsercionMongo {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient cliente = MongoClients.create(uri)) {
            MongoDatabase db = cliente.getDatabase("instituto_dam");
            MongoCollection<Document> colAlumnos = db.getCollection("alumnos");

            Document doc = new Document("nombre", "Raúl Ortiz")
                    .append("modulo", "Programación")
                    .append("curso", "1º DAM")
                    .append("matriculado", true);

            colAlumnos.insertOne(doc);

            ObjectId idGenerado = doc.getObjectId("_id");
            System.out.println("Documento insertado con éxito en MongoDB. ID: " + idGenerado);
        } catch (Exception e) {
            System.err.println("Aviso de conexión (servidor MongoDB local no disponible): " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 6: Inserción Masiva de Documentos (`insertMany`)

```java
package com.dam.boletin11.inicial;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ejercicio06InsercionMasivaMongo {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient cliente = MongoClients.create(uri)) {
            MongoDatabase db = cliente.getDatabase("tienda_dam");
            MongoCollection<Document> colProductos = db.getCollection("productos");

            List<Document> catalogo = new ArrayList<>();
            catalogo.add(new Document("referencia", "TECL-01").append("nombre", "Teclado Mecánico RGB").append("precio", 79.99).append("categoria", "perifericos").append("etiquetas", Arrays.asList("gaming", "rgb", "usb")));
            catalogo.add(new Document("referencia", "RAT-02").append("nombre", "Ratón Óptico Ergonómico").append("precio", 34.50).append("categoria", "perifericos").append("etiquetas", Arrays.asList("oficina", "inalambrico")));
            catalogo.add(new Document("referencia", "MON-03").append("nombre", "Monitor 27 Pulgadas IPS").append("precio", 189.00).append("categoria", "pantallas").append("etiquetas", Arrays.asList("144hz", "hdmi")));
            catalogo.add(new Document("referencia", "AUR-04").append("nombre", "Auriculares con Cancelación").append("precio", 65.00).append("categoria", "perifericos").append("etiquetas", Arrays.asList("audio", "bluetooth")));

            colProductos.insertMany(catalogo);
            System.out.println("Se han insertado " + catalogo.size() + " productos en la colección.");
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 7: Consultas con Filtros en MongoDB (`Filters`)

```java
package com.dam.boletin11.inicial;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

public class Ejercicio07ConsultasFiltrosMongo {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient cliente = MongoClients.create(uri)) {
            MongoDatabase db = cliente.getDatabase("tienda_dam");
            MongoCollection<Document> colProductos = db.getCollection("productos");

            System.out.println("--- Periféricos con precio <= 100 € ---");
            for (Document doc : colProductos.find(and(eq("categoria", "perifericos"), lte("precio", 100.0)))) {
                System.out.println("• " + doc.getString("nombre") + " [" + doc.getString("referencia") + "] - " + doc.getDouble("precio") + " €");
            }
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 8: Modificaciones Atómicas en MongoDB (`Updates`)

```java
package com.dam.boletin11.inicial;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class Ejercicio08ActualizacionesMongo {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient cliente = MongoClients.create(uri)) {
            MongoDatabase db = cliente.getDatabase("tienda_dam");
            MongoCollection<Document> colProductos = db.getCollection("productos");

            // Modificación atómica combinada
            UpdateResult res = colProductos.updateOne(
                    eq("referencia", "TECL-01"),
                    combine(
                            set("precio", 89.99),
                            push("etiquetas", "oferta_flash")
                    )
            );

            System.out.println("Documentos modificados: " + res.getModifiedCount());
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```
