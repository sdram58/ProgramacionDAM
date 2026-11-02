---
title: Boletín U11 — Extras
description: Retos de programación con MongoDB y bases de datos NoSQL desde Java
---

# 🔥 Boletín U11 — Extras

> Las plataformas como CodeWars o AceptaElReto no sirven para bases de datos reales, así que estos retos son pequeños proyectos. Necesitas MongoDB funcionando y el driver `mongodb-driver-sync` en el proyecto. Aquí tienes tres retos.

## Reto 1: la agenda de contactos

Crea un programa de consola con menú para gestionar una agenda en MongoDB: **añadir** contacto (nombre, empresa, lista de teléfonos y lista de etiquetas), **buscar por nombre** (aunque se escriba solo una parte y sin importar mayúsculas), **filtrar por etiqueta**, **añadir un teléfono** a un contacto existente y **borrar** un contacto. Cada contacto es **un solo documento**, con las listas dentro.

:::tip[Pista]
Para buscar por parte del nombre sin distinguir mayúsculas, usa `Filters.regex("nombre", texto, "i")` (¡y recuerda escapar el texto con `Pattern.quote` si no quieres que el usuario escriba expresiones regulares!). Para añadir un elemento a una lista dentro de un documento, sin leerlo antes, usa `Updates.push("telefonos", nuevo)`.
:::

<details>
<summary>💡 Solución</summary>

```java
public class Agenda {
    private final MongoCollection<Document> contactos;

    public Agenda(MongoCollection<Document> contactos) { this.contactos = contactos; }

    public void anadir(String nombre, String empresa, List<String> telefonos, List<String> etiquetas) {
        contactos.insertOne(new Document("nombre", nombre)
                .append("empresa", empresa)
                .append("telefonos", telefonos)
                .append("etiquetas", etiquetas));
    }

    public List<Document> buscarPorNombre(String texto) {
        return contactos.find(Filters.regex("nombre", Pattern.quote(texto), "i"))
                        .into(new ArrayList<>());
    }

    public List<Document> filtrarPorEtiqueta(String etiqueta) {
        return contactos.find(Filters.eq("etiquetas", etiqueta)).into(new ArrayList<>());
    }

    public boolean anadirTelefono(String nombre, String telefono) {
        return contactos.updateOne(Filters.eq("nombre", nombre),
                                   Updates.push("telefonos", telefono)).getMatchedCount() > 0;
    }

    public boolean borrar(String nombre) {
        return contactos.deleteOne(Filters.eq("nombre", nombre)).getDeletedCount() > 0;
    }
}
```

`into(new ArrayList<>())` vuelca el cursor de resultados en una lista. Un contacto con varios teléfonos y etiquetas en una sola fila es incómodo en el modelo relacional (necesitaría dos tablas auxiliares); en un documento es natural. Para evitar teléfonos repetidos en la lista, `Updates.addToSet` en vez de `push`.
</details>

## Reto 2: migrar de MySQL a MongoDB

Coge la tabla `producto` de la base `tienda` de la unidad 10 y escribe un programa que **copie todos sus datos a una colección** `productos` de MongoDB: lee cada fila con JDBC y la inserta como documento. Hazlo con `insertMany` (no una a una) y muestra al final cuántos documentos se han migrado. Después, añade a algunos documentos de MongoDB un campo que no existía en la tabla (por ejemplo `"colores": ["negro", "rojo"]`), y piensa qué habría hecho falta en MySQL para guardar eso.

<details>
<summary>💡 Solución</summary>

```java
List<Document> documentos = new ArrayList<>();

try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery("SELECT id, nombre, precio, stock FROM producto")) {
    while (rs.next()) {
        documentos.add(new Document("idOriginal", rs.getInt("id"))
                .append("nombre", rs.getString("nombre"))
                .append("precio", rs.getDouble("precio"))
                .append("stock", rs.getInt("stock")));
    }
}

try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
    MongoCollection<Document> productos = cliente.getDatabase("tienda").getCollection("productos");
    if (!documentos.isEmpty()) {          // insertMany falla con una lista vacía
        productos.insertMany(documentos);
    }
    System.out.println("Migrados: " + documentos.size());

    productos.updateOne(Filters.eq("nombre", "Ratón"),
                        Updates.set("colores", Arrays.asList("negro", "rojo")));
}
```

El `id` de MySQL se conserva como `idOriginal` y MongoDB genera su propio `_id`. Añadir "colores" a unos productos y no a otros es inmediato en MongoDB; en MySQL habría que añadir una columna que quedaría vacía para el resto de productos, o crear una tabla auxiliar `producto_color` con una clave ajena.
</details>

## Reto 3: persistencia de objetos de un juego

Crea una clase `Jugador` (nombre, nivel, puntos y una lista de objetos `Item`, cada uno con nombre y rareza). Escribe métodos para **guardar** un `Jugador` completo en MongoDB (como **un único documento**, con los ítems embebidos), **recuperarlo** por nombre reconstruyendo el objeto con todos sus ítems, y **sumar puntos** a un jugador sin tener que recuperarlo antes. Después, con el ranking de los 5 jugadores con más puntos, reflexiona: ¿qué ventaja tiene esto frente a hacerlo con dos tablas y JDBC?

<details>
<summary>💡 Solución</summary>

```java
public class JugadorDao {
    private final MongoCollection<Document> coleccion;

    public JugadorDao(MongoCollection<Document> coleccion) { this.coleccion = coleccion; }

    public void guardar(Jugador j) {
        List<Document> items = new ArrayList<>();
        for (Item i : j.getItems()) {
            items.add(new Document("nombre", i.getNombre()).append("rareza", i.getRareza()));
        }
        coleccion.insertOne(new Document("nombre", j.getNombre())
                .append("nivel", j.getNivel())
                .append("puntos", j.getPuntos())
                .append("items", items));
    }

    public Jugador recuperar(String nombre) {
        Document d = coleccion.find(Filters.eq("nombre", nombre)).first();
        if (d == null) return null;

        List<Item> items = new ArrayList<>();
        for (Document di : d.getList("items", Document.class)) {
            items.add(new Item(di.getString("nombre"), di.getString("rareza")));
        }
        return new Jugador(d.getString("nombre"), d.getInteger("nivel"), d.getInteger("puntos"), items);
    }

    public void sumarPuntos(String nombre, int puntos) {
        coleccion.updateOne(Filters.eq("nombre", nombre), Updates.inc("puntos", puntos));
    }

    public List<String> ranking() {
        List<String> res = new ArrayList<>();
        for (Document d : coleccion.find().sort(Sorts.descending("puntos")).limit(5)) {
            res.add(d.getString("nombre") + ": " + d.getInteger("puntos"));
        }
        return res;
    }
}
```

Con dos tablas (`jugador` e `item` con una clave ajena) habría que insertar en las dos dentro de una transacción, y al recuperar hacer un `JOIN` o dos consultas para volver a montar el objeto. Aquí el jugador es **una unidad**: se guarda y se lee de una vez y la operación sobre un solo documento es atómica. A cambio, si quisieras consultar "todos los ítems legendarios de todos los jugadores", la estructura relacional te lo pondría más fácil.
</details>
