---
draft: true
prev: false
next: false
title: "📝 Unidad 11 — Boletín Inicial (Propuesto)"
description: "Ejercicios guiados de iniciación a bases de datos orientadas a objetos (Db4o) y bases de datos documentales NoSQL (MongoDB) desde Java."
---

:::tip[Objetivos Pedagógicos]
* Experimentar la persistencia directa de objetos Java sin escribir sentencias SQL ni definir esquemas.
* Ejecutar consultas basadas en prototipos (QBE) y predicados nativos en motores BDOO.
* Conectar aplicaciones Java con MongoDB y manipular documentos BSON mediante su API oficial.
:::

---

### Ejercicio 1: Apertura de Contenedor e Inserción Básica en Db4o
Diseña una clase `Persona` con los atributos `dni`, `nombre` y `edad`:
* Escribe un programa que cree o abra un contenedor de objetos Db4o local (`personas.db4o`).
* Instancia tres objetos `Persona` con datos de ejemplo y almacénalos en el contenedor mediante `db.store(objeto)`.
* Confirma la transacción con `db.commit()` y cierra adecuadamente el contenedor.

---

### Ejercicio 2: Consultas por Ejemplo (QBE) en Db4o
Utilizando la base de datos de personas del ejercicio anterior:
* Crea un método `buscarPorNombre(String nombre)` que cree un objeto prototipo `new Persona(null, nombre, 0)` y ejecute `db.queryByExample(plantilla)`.
* Crea un método `buscarPorEdad(int edad)` que devuelva todas las personas que coincidan exactamente con esa edad.
* Muestra los resultados obtenidos por consola iterando sobre el `ObjectSet<Persona>`.

---

### Ejercicio 3: Modificación y Borrado en Db4o
Desarrolla métodos para actualizar y borrar objetos en Db4o:
* `actualizarEdad(String dni, int nuevaEdad)`: localiza a la persona mediante su DNI con QBE, modifica su edad mediante el setter y llama a `db.store(persona)` para consolidar el cambio.
* `eliminarPersona(String dni)`: busca a la persona por su DNI y, si existe, la retira del contenedor con `db.delete(persona)`.
* Asegura los cambios mediante `db.commit()`.

---

### Ejercicio 4: Consultas Nativas (Native Queries) en Db4o
Implementa una consulta nativa en Db4o utilizando la clase abstracta `Predicate<Persona>`:
* Filtra todas las personas cuya edad esté comprendida entre 18 y 30 años (ambos inclusive) y cuyo nombre empiece por la letra 'A'.
* Comprueba cómo el compilador de Java detecta errores de tipos de forma estática en las consultas nativas a diferencia de las consultas en cadenas de texto de SQL.

---

### Ejercicio 5: Conexión e Inserción de Documentos en MongoDB
Configura en un proyecto de IntelliJ IDEA la dependencia del driver oficial de MongoDB (`mongodb-driver-sync`):
* Conéctate a la instancia local de MongoDB en `mongodb://localhost:27017`.
* Selecciona la base de datos `instituto_dam` y la colección `alumnos`.
* Crea un documento BSON (`org.bson.Document`) con los campos: `nombre`, `modulo`, `curso` y `matriculado` (booleano).
* Insértalo con `collection.insertOne(doc)` y muestra por pantalla el `ObjectId` generado en el campo `_id`.

---

### Ejercicio 6: Inserción Masiva de Documentos (`insertMany`)
Prepara un método que reciba una lista de documentos y los inserte en un único viaje de red:
* Crea una lista `List<Document>` con al menos 4 productos de una tienda informática (campos: `referencia`, `nombre`, `precio`, `etiquetas` con un array de strings).
* Ejecuta `colProductos.insertMany(lista)`.
* Muestra por consola la cantidad de documentos insertados con éxito.

---

### Ejercicio 7: Consultas con Filtros en MongoDB (`Filters`)
Utilizando la colección de productos creada en el ejercicio anterior:
* Importa estáticamente `com.mongodb.client.model.Filters.*`.
* Realiza una consulta para obtener todos los productos con precio superior a 50 euros (`gt("precio", 50.0)`).
* Realiza una consulta combinada con operador lógico AND para buscar productos de la categoría "perifericos" cuyo precio sea inferior o igual a 100 euros.
* Itera sobre el cursor resultante y muestra los datos por consola.

---

### Ejercicio 8: Modificaciones Atómicas en MongoDB (`Updates`)
Utilizando la factoría estática `com.mongodb.client.model.Updates.*`:
* Localiza un producto por su campo `referencia`.
* Aplica una actualización combinada con `combine(...)` que:
  1. Incremente el precio del producto en un 10% mediante `mul("precio", 1.10)` o `set("precio", nuevoPrecio)`.
  2. Añada una nueva etiqueta al array `etiquetas` mediante `push("etiquetas", "oferta_flash")`.
* Comprueba el resultado inspeccionando el documento actualizado.
