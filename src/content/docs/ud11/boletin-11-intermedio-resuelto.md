---
draft: true
prev: false
next: false
title: "📝 Unidad 11 — Boletín Intermedio (Resuelto)"
description: "Soluciones completas y detalladas de los ejercicios intermedios de persistencia orientada a objetos (Db4o) y NoSQL (MongoDB)."
---

:::note[Tecnologías Abordadas]
Las soluciones implementan persistencia de grafos, herencia polimórfica, transacciones con Db4o, y configuración de códecs POJO con pipelines de agregación en MongoDB.
:::

---

### Solución Ejercicio 1: Persistencia de Objetos Compuestos en Db4o

#### Clases Modelo
```java
package com.dam.boletin11.intermedio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Direccion implements Serializable {
    private String calle;
    private String ciudad;
    private String codigoPostal;

    public Direccion() {}
    public Direccion(String calle, String ciudad, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    @Override
    public String toString() {
        return calle + ", " + ciudad + " (" + codigoPostal + ")";
    }
}
```

```java
package com.dam.boletin11.intermedio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private int id;
    private String nombre;
    private Direccion direccion;
    private List<String> pedidos = new ArrayList<>();

    public Cliente() {}
    public Cliente(int id, String nombre, Direccion direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public List<String> getPedidos() { return pedidos; }
    public void agregarPedido(String p) { this.pedidos.add(p); }

    @Override
    public String toString() {
        return "Cliente [" + id + "] " + nombre + " | Dirección: " + direccion + " | Pedidos: " + pedidos;
    }
}
```

#### Programa de Inserción y Recuperación
```java
package com.dam.boletin11.intermedio;

import com.dam.boletin11.intermedio.model.Cliente;
import com.dam.boletin11.intermedio.model.Direccion;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ejercicio01ObjetosCompuestos {
    private static final String FICHERO_BD = "clientes_compuestos.db4o";

    public static void main(String[] args) {
        // 1. Guardar cliente compuesto
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            Direccion dir = new Direccion("Avenida del Puerto 12", "Valencia", "46021");
            Cliente cli = new Cliente(101, "Marta Navarro", dir);
            cli.agregarPedido("Portátil Dell XPS");
            cli.agregarPedido("Ratón Inalámbrico Logitech");

            // Persistencia del grafo completo en una sola llamada
            db.store(cli);
            db.commit();
            System.out.println("Cliente y sus objetos anidados almacenados con éxito.");
        }

        // 2. Recuperar en sesión independiente
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            ObjectSet<Cliente> set = db.queryByExample(new Cliente(101, null, null));
            if (set.hasNext()) {
                Cliente c = set.next();
                System.out.println("Objeto recuperado íntegro:");
                System.out.println(c);
            }
        }
    }
}
```

---

### Solución Ejercicio 2: Persistencia Polimórfica y Herencia en Db4o

#### Jerarquía de Clases
```java
package com.dam.boletin11.intermedio.model;

import java.io.Serializable;

public abstract class Vehiculo implements Serializable {
    private String matricula;
    private String marca;

    public Vehiculo() {}
    public Vehiculo(String matricula, String marca) {
        this.matricula = matricula;
        this.marca = marca;
    }

    public String getMatricula() { return matricula; }
    public String getMarca() { return marca; }

    @Override
    public String toString() {
        return "Matrícula: " + matricula + ", Marca: " + marca;
    }
}
```

```java
package com.dam.boletin11.intermedio.model;

public class Coche extends Vehiculo {
    private int numeroPuertas;

    public Coche() {}
    public Coche(String matricula, String marca, int numeroPuertas) {
        super(matricula, marca);
        this.numeroPuertas = numeroPuertas;
    }

    public int getNumeroPuertas() { return numeroPuertas; }

    @Override
    public String toString() {
        return "[COCHE] " + super.toString() + ", Puertas: " + numeroPuertas;
    }
}
```

```java
package com.dam.boletin11.intermedio.model;

public class Moto extends Vehiculo {
    private int cilindrada;

    public Moto() {}
    public Moto(String matricula, String marca, int cilindrada) {
        super(matricula, marca);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() { return cilindrada; }

    @Override
    public String toString() {
        return "[MOTO] " + super.toString() + ", Cilindrada: " + cilindrada + " cc";
    }
}
```

#### Consulta Polimórfica
```java
package com.dam.boletin11.intermedio;

import com.dam.boletin11.intermedio.model.Coche;
import com.dam.boletin11.intermedio.model.Moto;
import com.dam.boletin11.intermedio.model.Vehiculo;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ejercicio02PolimorfismoDb4o {
    private static final String FICHERO_BD = "vehiculos.db4o";

    public static void main(String[] args) {
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            db.store(new Coche("1234-BBB", "Ford", 5));
            db.store(new Coche("5678-CCC", "Seat", 3));
            db.store(new Moto("9012-DDD", "Yamaha", 600));
            db.store(new Moto("3456-EEE", "Honda", 250));
            db.commit();
        }

        // Consulta polimórfica buscando la superclase Vehiculo
        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FICHERO_BD)) {
            ObjectSet<Vehiculo> flota = db.query(Vehiculo.class);

            System.out.println("Flota completa recuperada polimórficamente (sin JOINs):");
            while (flota.hasNext()) {
                Vehiculo v = flota.next();
                System.out.println("• " + v);
            }
        }
    }
}
```

---

### Solución Ejercicio 3: Configuración de Cascada en Actualizaciones (`cascadeOnUpdate`)

```java
package com.dam.boletin11.intermedio;

import com.dam.boletin11.intermedio.model.Cliente;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

public class Ejercicio03CascadaUpdateDb4o {
    private static final String FICHERO_BD = "clientes_compuestos.db4o";

    public static void main(String[] args) {
        // Configuramos la cascada para que las modificaciones en subobjetos se propaguen
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Cliente.class).cascadeOnUpdate(true);

        try (ObjectContainer db = Db4oEmbedded.openFile(config, FICHERO_BD)) {
            ObjectSet<Cliente> res = db.queryByExample(new Cliente(101, null, null));
            if (res.hasNext()) {
                Cliente cli = res.next();
                // Modificamos el objeto anidado
                cli.getDireccion().setCiudad("Alicante");
                cli.getDireccion().setCodigoPostal("03001");
                
                // Al tener cascadeOnUpdate activo, almacena también la Dirección
                db.store(cli);
                db.commit();
                System.out.println("Dirección anidada actualizada mediante cascada.");
            }
        }
    }
}
```

---

### Solución Ejercicio 4: Transacciones y Rollback en Db4o

```java
package com.dam.boletin11.intermedio;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio04TransaccionesRollback {

    public static class Departamento {
        private String nombre;
        private List<String> empleados = new ArrayList<>();

        public Departamento(String nombre) { this.nombre = nombre; }
        public String getNombre() { return nombre; }
        public List<String> getEmpleados() { return empleados; }
    }

    public static void main(String[] args) {
        String dbFile = "transacciones_bdoo.db4o";

        try (ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbFile)) {
            Departamento depto = new Departamento("Logística");
            depto.getEmpleados().add("Pepe");
            depto.getEmpleados().add("Lucía");
            db.store(depto);
            db.commit();

            // Inicio de operación con posible fallo
            try {
                depto.getEmpleados().add("Carlos");
                db.store(depto);

                // Simulamos una regla de negocio rota
                boolean reglaViolada = true;
                if (reglaViolada) {
                    throw new IllegalStateException("Error de negocio: Aforo máximo alcanzado.");
                }

                db.commit();
            } catch (Exception ex) {
                System.err.println("Excepción capturada: " + ex.getMessage());
                db.rollback(); // Reversión total
                System.out.println("Rollback completado en Db4o. El cambio ha sido deshecho.");
            }
        }
    }
}
```

---

### Solución Ejercicio 5: Mapeo Automático de POJOs en MongoDB con `PojoCodecProvider`

#### Clase POJO `Estudiante`
```java
package com.dam.boletin11.intermedio.model;

import org.bson.types.ObjectId;

public class Estudiante {
    private ObjectId id;
    private String nombre;
    private String ciclo;
    private double notaMedia;

    public Estudiante() {}
    public Estudiante(String nombre, String ciclo, double notaMedia) {
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.notaMedia = notaMedia;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }
    public double getNotaMedia() { return notaMedia; }
    public void setNotaMedia(double notaMedia) { this.notaMedia = notaMedia; }
}
```

#### Programa con `PojoCodecProvider`
```java
package com.dam.boletin11.intermedio;

import com.dam.boletin11.intermedio.model.Estudiante;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Ejercicio05PojoCodecMongo {
    public static void main(String[] args) {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient cliente = MongoClients.create(settings)) {
            MongoDatabase db = cliente.getDatabase("instituto_dam");
            MongoCollection<Estudiante> col = db.getCollection("estudiantes", Estudiante.class);

            // Inserción directa de un POJO
            col.insertOne(new Estudiante("David Serrano", "1º DAM", 8.75));

            // Consulta tipada
            for (Estudiante est : col.find(eq("ciclo", "1º DAM"))) {
                System.out.println("Estudiante recuperado: " + est.getNombre() + " - Nota: " + est.getNotaMedia());
            }
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 6: Documentos Embebidos y Consultas por Campos Anidados

```java
package com.dam.boletin11.intermedio;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

public class Ejercicio06DocumentosEmbebidosMongo {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient cliente = MongoClients.create(uri)) {
            MongoDatabase db = cliente.getDatabase("empresa_dam");
            MongoCollection<Document> col = db.getCollection("plantilla");

            Document contacto = new Document("email", "silvia@empresa.com").append("telefono", "912345678");
            Document empleado = new Document("nombre", "Silvia Gil")
                    .append("departamento", "IT")
                    .append("contacto", contacto)
                    .append("habilidades", Arrays.asList("Java", "MongoDB", "Spring Boot"));

            col.insertOne(empleado);

            // Consulta por subdocumento anidado
            System.out.println("Empleados con teléfono de Madrid (prefijo 91):");
            for (Document d : col.find(regex("contacto.telefono", "^91"))) {
                System.out.println("• " + d.getString("nombre") + " -> " + d.get("contacto"));
            }

            // Consulta por elemento en array
            System.out.println("Empleados expertos en Java:");
            for (Document d : col.find(eq("habilidades", "Java"))) {
                System.out.println("• " + d.getString("nombre"));
            }
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 7: Paginación y Ordenación Eficiente en MongoDB

```java
package com.dam.boletin11.intermedio;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class Ejercicio07PaginacionMongo {

    public static void mostrarPagina(MongoCollection<Document> col, int pagina, int tamPagina) {
        System.out.println("=== PÁGINA " + pagina + " (Elementos por página: " + tamPagina + ") ===");

        col.find()
                .sort(Sorts.descending("precio"))
                .skip((pagina - 1) * tamPagina)
                .limit(tamPagina)
                .forEach(doc -> System.out.println("• " + doc.getString("nombre") + " -> " + doc.get("precio") + " €"));
    }

    public static void main(String[] args) {
        try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
            MongoCollection<Document> col = cliente.getDatabase("tienda_dam").getCollection("productos");
            mostrarPagina(col, 1, 3);
            mostrarPagina(col, 2, 3);
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 8: Pipeline de Agregación en MongoDB

```java
package com.dam.boletin11.intermedio;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;

public class Ejercicio08AgregacionMongo {
    public static void main(String[] args) {
        try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
            MongoCollection<Document> col = cliente.getDatabase("tienda_dam").getCollection("ventas");

            // Pipeline de agregación
            col.aggregate(Arrays.asList(
                    Aggregates.match(Filters.gte("anio", 2026)),
                    Aggregates.group(
                            "\u0024categoria",
                            Accumulators.sum("totalFacturado", "\u0024total"),
                            Accumulators.avg("ticketMedio", "\u0024total"),
                            Accumulators.sum("numVentas", 1)
                    )
            )).forEach(doc -> {
                System.out.println("Categoría: " + doc.getString("_id")
                        + " | Total: " + doc.get("totalFacturado") + " €"
                        + " | Media: " + doc.get("ticketMedio") + " €"
                        + " | Nº Ventas: " + doc.get("numVentas"));
            });
        } catch (Exception e) {
            System.err.println("Nota de ejecución: " + e.getMessage());
        }
    }
}
```
