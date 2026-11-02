---
title: Boletín U11 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre bases de datos orientadas a objetos, NoSQL y MongoDB
---

# ✅ Boletín U11 — Inicial (resuelto)

En las soluciones Java se asume que existe esta configuración y estos imports:

```java
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;

private static final String URI = "mongodb://localhost:27017";
```

1. **Desfase objeto-relacional**
   <details>
   <summary>💡 Solución</summary>

   Es la diferencia entre cómo se organiza la información en un programa orientado a objetos (clases, referencias, colecciones, herencia) y cómo se organiza en una base de datos relacional (tablas, filas, claves ajenas). Para guardar un `Cliente` con su `List<Pedido>` en una relacional hay que repartir el cliente en una tabla, los pedidos en otra enlazados con una clave ajena, y reconstruir el objeto al leer. Una BDOO guarda el `Cliente` entero, con su lista de pedidos dentro, tal cual: no hay traducción porque el modelo de la base de datos y el del programa son el mismo.
   </details>

2. **Verdadero o falso**
   <details>
   <summary>💡 Solución</summary>

   - a) **Falso**. El esquema se obtiene de las propias clases; no hay `CREATE TABLE`.
   - b) **Verdadero**. Es una de sus ventajas: objetos compuestos y colecciones de objetos.
   - c) **Falso**. db4o es un motor **embebido**: es una librería (`.jar`) y la base de datos es un fichero que abre tu propio programa.
   - d) **Falso**. Los documentos de una misma colección pueden tener campos distintos; esa flexibilidad es una característica de MongoDB.
   </details>

3. **Familias NoSQL**
   <details>
   <summary>💡 Solución</summary>

   - (a) **Clave-valor** (por ejemplo Redis): se busca por un identificador de sesión y hace falta mucha velocidad.
   - (b) **Documentos** (MongoDB): cada producto es un documento con los atributos que necesite, sin obligar a todos a tener las mismas columnas.
   - (c) **Grafos** (Neo4j): lo importante son las relaciones entre elementos y recorrerlas ("amigos de amigos").
   - (d) **Columnas anchas** (Cassandra): enormes volúmenes de datos con muchas escrituras repartidos en muchos servidores.
   </details>

4. **Equivalencias**
   <details>
   <summary>💡 Solución</summary>

   | Relacional | MongoDB |
   |---|---|
   | Tabla | Colección |
   | Fila | Documento |
   | Columna | Campo |
   | Clave primaria | Campo `_id` |
   | Clave ajena | No existe como tal: se **referencia** guardando el `_id` del otro documento (o se **embebe** el documento) |
   </details>

5. **Insertar películas**
   <details>
   <summary>💡 Solución</summary>

   ```javascript
   use cine
   db.peliculas.insertMany([
     { titulo: "Alien", anio: 1979, genero: "terror", nota: 8.5 },
     { titulo: "Matrix", anio: 1999, genero: "ciencia ficcion", nota: 8.7 },
     { titulo: "El resplandor", anio: 1980, genero: "terror", nota: 8.4 },
     { titulo: "Interstellar", anio: 2014, genero: "ciencia ficcion", nota: 8.6 },
     { titulo: "Up", anio: 2009, genero: "animacion", nota: 8.3 }
   ])
   ```

   No hizo falta crear la base de datos ni la colección: se crean al insertar el primer documento.
   </details>

6. **Consultas**
   <details>
   <summary>💡 Solución</summary>

   ```javascript
   db.peliculas.find()                                          // (a)
   db.peliculas.find({ genero: "terror" })                      // (b)
   db.peliculas.find({ anio: { $gt: 2000 } })                   // (c)
   db.peliculas.find({ nota: { $gte: 8 } }).sort({ nota: -1 }) // (d)
   db.peliculas.find().limit(3)                                 // (e)
   ```

   `-1` ordena de mayor a menor y `1` de menor a mayor.
   </details>

7. **Actualizar y borrar**
   <details>
   <summary>💡 Solución</summary>

   ```javascript
   db.peliculas.updateOne({ titulo: "Up" }, { $set: { nota: 8.4 } })           // (a)
   db.peliculas.updateMany({ genero: "terror" }, { $inc: { nota: 0.5 } })      // (b)
   db.peliculas.deleteOne({ titulo: "Alien" })                                  // (c)
   ```

   Los operadores de actualización (`$set`, `$inc`...) modifican solo los campos indicados. Sin ellos, `updateOne` rechaza el documento de actualización: conviene acostumbrarse a usarlos siempre.
   </details>

8. **Insertar desde Java**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (MongoClient cliente = MongoClients.create(URI)) {
       MongoCollection<Document> peliculas = cliente.getDatabase("cine").getCollection("peliculas");
       Document d = new Document("titulo", "Blade Runner")
               .append("anio", 1982)
               .append("genero", "ciencia ficcion")
               .append("nota", 8.1);
       peliculas.insertOne(d);
       System.out.println("Insertada con _id " + d.getObjectId("_id"));
   }
   ```

   Tras `insertOne`, el propio `Document` recibe su `_id` generado.
   </details>

9. **Listar desde Java**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (MongoClient cliente = MongoClients.create(URI)) {
       MongoCollection<Document> peliculas = cliente.getDatabase("cine").getCollection("peliculas");
       for (Document d : peliculas.find()) {
           System.out.println(d.getString("titulo") + " (" + d.getInteger("anio") + ")");
       }
   }
   ```

   `find()` sin filtro devuelve todos los documentos; se puede recorrer con un `for` mejorado.
   </details>

10. **Embeber o referenciar**
    <details>
    <summary>💡 Solución</summary>

    **Embeber** es guardar el documento relacionado dentro del otro (los libros dentro del documento del autor); **referenciar** es guardar solo el `_id` del otro documento y buscarlo con una segunda consulta. Si los libros solo se consultan junto con su autor, lo natural es **embeberlos**: una única lectura devuelve todo. Si un libro puede tener varios autores, embeber obligaría a duplicar el libro en cada autor (y a actualizar todas las copias si cambia); lo adecuado es **referenciar**: una colección `libros` donde cada libro guarda la lista de `_id` de sus autores.
    </details>

11. 🧪 **Pilotos en db4o**
    <details>
    <summary>💡 Solución</summary>

    ```java
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "F1.db4");
    try {
        db.store(new Piloto("Fernando Alonso", 50));
        db.store(new Piloto("Lewis Hamilton", 60));
        db.store(new Piloto("Max Verstappen", 70));

        ObjectSet<Piloto> todos = db.queryByExample(Piloto.class);
        while (todos.hasNext()) {
            System.out.println(todos.next());
        }
    } finally {
        db.close();
    }
    ```
    </details>

12. 🧪 **Actualizar sin duplicar**
    <details>
    <summary>💡 Solución</summary>

    ```java
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "F1.db4");
    try {
        Piloto p = db.queryByExample(new Piloto("Fernando Alonso", 0)).next();
        p.sumaPuntos(10);
        db.store(p);                         // actualiza: el objeto viene de la base de datos
        System.out.println(db.queryByExample(Piloto.class).size());   // 3
    } finally {
        db.close();
    }
    ```

    Si hubieras hecho `db.store(new Piloto("Fernando Alonso", 60))`, la base de datos no tendría forma de saber que es "el mismo" piloto (los objetos tienen **identidad propia**, no se identifican por sus valores) y lo guardaría como un objeto nuevo: habría cuatro pilotos, con dos Alonsos.
    </details>
