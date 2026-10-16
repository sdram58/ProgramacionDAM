---
draft: true
prev: false
next: false
title: "📝 Unidad 11 — Boletín Intermedio (Propuesto)"
description: "Ejercicios de nivel intermedio sobre BDOO y NoSQL: objetos compuestos, herencia polimórfica, POJO Codecs, documentos anidados y agregaciones."
---

:::tip[Objetivos Pedagógicos]
* Gestionar la persistencia de grafos de objetos complejos y jerarquías polimórficas en BDOO.
* Configurar la profundidad de activación y actualización para optimizar la memoria.
* Utilizar el mapeo automático de POJOs y pipelines de agregación en MongoDB.
:::

---

### Ejercicio 1: Persistencia de Objetos Compuestos en Db4o
Diseña una estructura compuesta de clases:
* Clase `Direccion`: `calle`, `ciudad`, `codigoPostal`.
* Clase `Cliente`: `id`, `nombre`, `direccion` (objeto de tipo `Direccion`), y `pedidos` (`List<String>`).
* Almacena un cliente con su dirección y varios pedidos mediante una única llamada `db.store(cliente)`.
* En un programa independiente, recupera al cliente y comprueba que su objeto anidado `Direccion` y su lista de pedidos se han deserializado íntegramente en memoria sin necesidad de tablas foráneas ni JOINs.

---

### Ejercicio 2: Persistencia Polimórfica y Herencia en Db4o
Crea una jerarquía de clases de vehículos:
* Superclase abstracta `Vehiculo`: atributos `matricula` y `marca`.
* Subclase `Coche`: atributo adicional `numeroPuertas`.
* Subclase `Moto`: atributo adicional `cilindrada`.
* Almacena en Db4o dos coches y dos motos.
* Realiza una consulta polimórfica buscando simplemente `Vehiculo.class`. Comprueba que la lista recuperada contiene instancias de ambas clases derivadas conservando sus atributos específicos.

---

### Ejercicio 3: Configuración de Profundidad de Actualización (`updateDepth`)
Modifica la configuración de Db4o para entender la cascada de actualizaciones:
* Por defecto, Db4o solo actualiza hasta el nivel 1 de profundidad. Si modificas un atributo de un objeto anidado (ej. la calle de la `Direccion` de un `Cliente`) y guardas el cliente, el cambio en la dirección podría no persistir.
* Configura la profundidad de actualización en cascada mediante:
  `config.common().objectClass(Cliente.class).cascadeOnUpdate(true);`
* Modifica la ciudad de la dirección de un cliente persistido, guarda el cliente y verifica que el cambio en el subobjeto se ha grabado de forma permanente en el archivo `.db4o`.

---

### Ejercicio 4: Transacciones y Rollback en Db4o
Implementa una simulación transaccional con Db4o:
* En un método `transferirDepartamento(String dniEmpleado, Departamento origen, Departamento destino)`:
  * Retira al empleado de la lista de la plantilla de origen.
  * Añade al empleado a la plantilla de destino.
  * Si el departamento de destino supera el aforo máximo de 5 empleados, lanza una excepción de negocio y ejecuta `db.rollback()`.
* Demuestra que tras el rollback el estado de la base de datos permanece exactamente igual que antes de iniciar la operación.

---

### Ejercicio 5: Mapeo Automático de POJOs en MongoDB con `PojoCodecProvider`
Configura un proyecto Java con MongoDB empleando el registro de códecs automáticos:
* Crea una clase POJO estándar `Estudiante` (con constructor vacío, getters, setters y `equals`).
* Registra el `PojoCodecProvider` en la configuración de la base de datos.
* Obtén la colección fuertemente tipada: `MongoCollection<Estudiante> col = db.getCollection("estudiantes", Estudiante.class)`.
* Inserta instancias de `Estudiante` directamente y recupéralas mediante consultas tipadas sin manipular objetos `Document` manualmente.

---

### Ejercicio 6: Documentos Embebidos y Consultas por Campos Anidados
Utilizando MongoDB y la API `Document`:
* Inserta varios empleados que incluyan un subdocumento `"contacto"` con los campos `"email"` y `"telefono"`, y una lista `"habilidades"` (ej. `["Java", "SQL", "Docker"]`).
* Realiza una consulta para buscar a todos los empleados cuyo teléfono empiece por un prefijo determinado (`Filters.regex("contacto.telefono", "^91")`).
* Realiza una consulta para encontrar empleados que dominen la habilidad "Java" en su lista (`Filters.eq("habilidades", "Java")`).

---

### Ejercicio 7: Paginación y Ordenación Eficiente en MongoDB
Construye un método de consulta paginada:
`public static List<Document> obtenerPagina(int numeroPagina, int elementosPorPagina)`
* Aplica ordenación descendente por fecha de registro mediante `Sorts.descending("fechaRegistro")`.
* Utiliza los métodos `skip((numeroPagina - 1) * elementosPorPagina)` y `limit(elementosPorPagina)`.
* Muestra por consola los registros correspondientes a la página 2 con un tamaño de página de 5 elementos.

---

### Ejercicio 8: Pipeline de Agregación en MongoDB
Escribe una consulta de análisis de ventas utilizando el framework de agregaciones de MongoDB (`colVentas.aggregate(...)`):
* Utiliza `Aggregates.match(...)` para filtrar ventas del año actual.
* Utiliza `Aggregates.group(...)` agrupando por la categoría del producto (referenciando el campo de categoría), calculando:
  1. El total de ingresos acumulados con `Accumulators.sum("totalIngresos", "\u0024total")` (empleando el prefijo de campo de MongoDB).
  2. El precio medio con `Accumulators.avg("precioMedio", "\u0024precio")`.
  3. El número de pedidos con `Accumulators.sum("cantidadPedidos", 1)`.
* Imprime el informe resumen resultante por consola.
