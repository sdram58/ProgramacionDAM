---
title: Boletín U11 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre MongoDB desde Java, modelado de documentos y db4o
---

# ✅ Boletín U11 — Intermedio (resuelto)

Se asumen estos imports en las soluciones Java:

```java
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import java.util.*;
```

1. **PeliculaDao completo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Pelicula {
       private String titulo;
       private int anio;
       private String genero;
       private double nota;
       // constructor con los cuatro atributos, getters y toString
   }

   public class PeliculaDao {
       private final MongoCollection<Document> coleccion;

       public PeliculaDao(MongoCollection<Document> coleccion) { this.coleccion = coleccion; }

       public void insertar(Pelicula p) {
           coleccion.insertOne(aDocumento(p));
       }

       public Pelicula buscarPorTitulo(String titulo) {
           Document d = coleccion.find(Filters.eq("titulo", titulo)).first();
           return d == null ? null : aPelicula(d);
       }

       public List<Pelicula> buscarTodas() {
           List<Pelicula> lista = new ArrayList<>();
           for (Document d : coleccion.find()) lista.add(aPelicula(d));
           return lista;
       }

       public boolean actualizarNota(String titulo, double nota) {
           UpdateResult r = coleccion.updateOne(Filters.eq("titulo", titulo), Updates.set("nota", nota));
           return r.getMatchedCount() > 0;
       }

       public boolean borrar(String titulo) {
           return coleccion.deleteOne(Filters.eq("titulo", titulo)).getDeletedCount() > 0;
       }

       private Document aDocumento(Pelicula p) {
           return new Document("titulo", p.getTitulo())
                   .append("anio", p.getAnio())
                   .append("genero", p.getGenero())
                   .append("nota", p.getNota());
       }

       private Pelicula aPelicula(Document d) {
           return new Pelicula(d.getString("titulo"), d.getInteger("anio"),
                               d.getString("genero"), d.getDouble("nota"));
       }
   }
   ```

   Es el mismo patrón DAO de la unidad 10: el resto del programa trabaja con `Pelicula` y no sabe nada de documentos. Los dos métodos privados de conversión hacen aquí el papel de `convertir(ResultSet)`.
   </details>

2. **Búsqueda con varios criterios**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public List<Pelicula> buscar(String genero, int anioMinimo, double notaMinima) {
       List<Pelicula> lista = new ArrayList<>();
       Bson filtro = Filters.and(
               Filters.eq("genero", genero),
               Filters.gt("anio", anioMinimo),
               Filters.gte("nota", notaMinima));
       for (Document d : coleccion.find(filtro).sort(Sorts.descending("nota")).limit(10)) {
           lista.add(aPelicula(d));
       }
       return lista;
   }
   ```

   (`Bson` se importa de `org.bson.conversions.Bson`.) Si cambia el orden de `sort` y `limit` en el código no cambia el resultado: es MongoDB quien aplica primero el orden y luego el límite.
   </details>

3. **Condición O**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public List<Pelicula> buscarVariosCriterios() {
       List<Pelicula> lista = new ArrayList<>();
       Bson filtro = Filters.or(
               Filters.eq("genero", "terror"),
               Filters.eq("genero", "ciencia ficcion"),
               Filters.gt("nota", 9));
       for (Document d : coleccion.find(filtro)) lista.add(aPelicula(d));
       return lista;
   }
   ```

   Para varios valores del mismo campo hay una forma más corta: `Filters.in("genero", "terror", "ciencia ficcion")`.
   </details>

4. **Subir la nota de un género**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public long subirNotaGenero(String genero, double incremento) {
       UpdateResult r = coleccion.updateMany(
               Filters.eq("genero", genero),
               Updates.inc("nota", incremento));
       return r.getModifiedCount();
   }
   ```

   `getMatchedCount()` indica cuántos documentos cumplían el filtro y `getModifiedCount()` cuántos cambiaron realmente; con `inc` de un valor distinto de cero coinciden.
   </details>

5. **Documentos anidados**
   <details>
   <summary>💡 Solución</summary>

   ```javascript
   db.libros.insertMany([
     { titulo: "Don Quijote", autor: { nombre: "Cervantes", pais: "España" }, etiquetas: ["clasico", "novela"] },
     { titulo: "Rayuela", autor: { nombre: "Cortázar", pais: "Argentina" }, etiquetas: ["novela", "experimental"] }
   ])
   db.libros.find({ "autor.pais": "España" })       // notación de punto (entre comillas)
   db.libros.find({ etiquetas: "novela" })          // busca dentro de la lista
   ```

   ```java
   MongoCollection<Document> libros = bd.getCollection("libros");

   for (Document d : libros.find(Filters.eq("autor.pais", "España"))) {
       System.out.println(d.getString("titulo"));
   }
   for (Document d : libros.find(Filters.eq("etiquetas", "novela"))) {
       System.out.println(d.getString("titulo"));
   }

   // Leer un campo anidado de un documento ya recuperado
   Document autor = d.get("autor", Document.class);
   System.out.println(autor.getString("nombre"));
   ```

   La notación de punto necesita comillas en el shell porque `autor.pais` no es un identificador válido de JavaScript.
   </details>

6. **Agrupar y calcular**
   <details>
   <summary>💡 Solución</summary>

   ```java
   AggregateIterable<Document> resultado = peliculas.aggregate(Arrays.asList(
           Aggregates.group("$genero",
                   Accumulators.sum("total", 1),
                   Accumulators.avg("notaMedia", "$nota"))));

   for (Document d : resultado) {
       System.out.printf("%s: %d películas, nota media %.2f%n",
               d.getString("_id"), d.getInteger("total"), d.getDouble("notaMedia"));
   }
   ```

   Es el equivalente a `SELECT genero, COUNT(*), AVG(nota) FROM pelicula GROUP BY genero`. El campo por el que se agrupa se convierte en el `_id` del resultado, y los nombres de campo van precedidos de `$` cuando se usan como **valor**.
   </details>

7. **Índices**
   <details>
   <summary>💡 Solución</summary>

   ```java
   // Índice normal (acelera las búsquedas por título):
   // peliculas.createIndex(Indexes.ascending("titulo"));

   // Índice único (además impide títulos repetidos). Es una u otra, no las dos sobre el mismo campo:
   peliculas.createIndex(Indexes.ascending("titulo"), new IndexOptions().unique(true));

   try {
       peliculas.insertOne(new Document("titulo", "Alien").append("anio", 1979));
       peliculas.insertOne(new Document("titulo", "Alien").append("anio", 1979));  // repetido
   } catch (com.mongodb.MongoWriteException e) {
       System.err.println("Título duplicado: " + e.getMessage());
   }
   ```

   Crear un índice normal y luego uno único sobre el mismo campo da un conflicto de definiciones (por eso se muestran como alternativas), y si ya hay títulos repetidos en la colección la creación del índice único falla. Un índice acelera las búsquedas por ese campo a costa de espacio y de algo más de tiempo al escribir.
   </details>

8. **Diseño de la tienda**
   <details>
   <summary>💡 Solución</summary>

   Una propuesta razonable:

   - **`clientes`**: documentos con nombre, email, dirección (la dirección **embebida**, porque solo tiene sentido junto al cliente).
   - **`productos`**: documentos con nombre, precio, stock y atributos propios de cada tipo de producto (aquí brilla la flexibilidad sin esquema fijo).
   - **`pedidos`**: cada pedido guarda el `_id` del cliente (**referencia**) y una lista **embebida** de líneas `{ idProducto, nombre, precioUnitario, cantidad }`.

   Las líneas se embeben porque no existen fuera de su pedido y siempre se leen con él (un pedido se lee en una sola consulta). Del producto se copian el nombre y el precio **en el momento de la compra**: así el pedido histórico no cambia si luego se modifica el precio del producto (aquí la duplicación es una *ventaja*, no un defecto). El cliente se referencia porque tiene vida propia y muchos pedidos. Con este diseño es cómodo "mostrar un pedido completo" y "listar los pedidos de un cliente" (`find({ idCliente: ... })`); es más incómodo "mostrar el nombre actual del cliente junto a cada pedido" (hace falta una segunda consulta o una operación de unión `$lookup`) y "calcular las ventas totales de un producto" (hay que recorrer las líneas de todos los pedidos con una agregación).
   </details>

9. 🧪 **Consulta nativa sobre objetos compuestos**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Coche {
       private String marca;
       private Piloto piloto;
       public Coche(String marca, Piloto piloto) { this.marca = marca; this.piloto = piloto; }
       public String getMarca() { return marca; }
       public Piloto getPiloto() { return piloto; }
       @Override public String toString() { return marca + " (" + piloto + ")"; }
   }

   // ...
   db.store(new Coche("Ferrari", new Piloto("Carlos Sainz", 80)));
   db.store(new Coche("Mercedes", new Piloto("Lewis Hamilton", 60)));
   db.store(new Coche("Alpine", new Piloto("Pierre Gasly", 30)));

   List<Coche> buenos = db.query(new Predicate<Coche>() {
       @Override
       public boolean match(Coche c) {
           return c.getPiloto().getPuntos() > 50;
       }
   });
   buenos.forEach(System.out::println);
   ```

   Gracias a que la consulta es Java, puedes "navegar" por los objetos (`c.getPiloto().getPuntos()`) como lo harías en cualquier otro punto del programa.
   </details>

10. 🧪 **Actualización en cascada**
    <details>
    <summary>💡 Solución</summary>

    Con la configuración por defecto, el cambio **no se guarda**: `store(coche)` solo actualiza los campos simples del coche (la marca), no los del objeto `Piloto` que contiene, así que al reabrir la base el piloto sigue con sus puntos antiguos. Se corrige configurando la base antes de abrirla:

    ```java
    EmbeddedConfiguration cfg = Db4oEmbedded.newConfiguration();
    cfg.common().objectClass(Coche.class).cascadeOnUpdate(true);
    cfg.common().objectClass(Coche.class).cascadeOnDelete(true);   // opcional, ver abajo
    ObjectContainer db = Db4oEmbedded.openFile(cfg, "F1.db4");
    ```

    Con `cascadeOnUpdate(true)`, `store(coche)` actualiza también al piloto contenido. En cuanto al borrado, sin `cascadeOnDelete(true)` el `delete(coche)` elimina el coche pero **deja el piloto huérfano**: sigue en la base de datos aunque ya nadie lo referencia. Con la cascada activada, se borra también el piloto. Hay que usarlo con cuidado: si el mismo piloto estuviera referenciado desde otro coche, también desaparecería de allí.
    </details>

11. **Comparación**
    <details>
    <summary>💡 Solución</summary>

    | | Relacional + JDBC | BDOO | MongoDB |
    |---|---|---|---|
    | **Esquema** | Fijo, definido de antemano con `CREATE TABLE` | Derivado de las clases | Flexible: cada documento puede tener campos distintos |
    | **Consultas** | SQL (cadenas de texto en el código) | Por ejemplo, consultas nativas en Java o SODA | Filtros (JSON o clases `Filters`) y agregaciones |
    | **Relaciones** | Claves ajenas y `JOIN` | Referencias entre objetos, de forma natural | Embeber o referenciar; sin `JOIN` clásico |
    | **Conversión objeto ↔ datos** | Manual (DAO) o con un ORM | Ninguna | Manual (`Document`) o con *codecs*/POJOs |
    | **Transacciones** | ACID completo | Normalmente sí | Atómicas por documento; transacciones multi-documento posibles en configuraciones concretas |
    | **Madurez** | Estándar universal | Poca; productos como db4o abandonados | Muy extendida y mantenida |
    </details>
