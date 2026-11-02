---
title: Boletín U08 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre ficheros y streams
---

# ✅ Boletín U08 — Intermedio (resuelto)

1. **Contacto serializable**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Contacto implements Serializable {
       private String nombre;
       private String telefono;
       Contacto(String nombre, String telefono) { this.nombre = nombre; this.telefono = telefono; }
       @Override
       public String toString() { return nombre + " - " + telefono; }
   }

   // Escribir
   try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contactos.dat"))) {
       oos.writeObject(new Contacto("Ada", "600111222"));
       oos.writeObject(new Contacto("Alan", "600333444"));
       oos.writeObject(new Contacto("Grace", "600555666"));
   }

   // Leer
   try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contactos.dat"))) {
       while (true) {
           Contacto c = (Contacto) ois.readObject();
           System.out.println(c);
       }
   } catch (EOFException fin) {
       // fin del fichero, normal
   }
   ```
   </details>

2. **Invertir líneas**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> lineas = Files.readAllLines(Path.of("original.txt"));
   Collections.reverse(lineas);

   try (PrintWriter pw = new PrintWriter(new FileWriter("invertido.txt"))) {
       for (String linea : lineas) {
           pw.println(linea);
       }
   }
   ```
   </details>

3. **Filtrar pares a otro fichero**
   <details>
   <summary>💡 Solución</summary>

   ```java
   int escritos = 0;
   try (BufferedReader br = new BufferedReader(new FileReader("numeros.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("pares.txt"))) {
       String linea;
       while ((linea = br.readLine()) != null) {
           int n = Integer.parseInt(linea.trim());
           if (n % 2 == 0) {
               pw.println(n);
               escritos++;
           }
       }
   }
   System.out.println("Números pares escritos: " + escritos);
   ```

   Fíjate en que `try-with-resources` acepta **varios** recursos separados por `;` — ambos se cierran automáticamente al salir del bloque.
   </details>

4. **Filtrar por extensión con NIO2**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Path carpeta = Path.of("C:/Temp");
   try (Stream<Path> ficheros = Files.list(carpeta)) {
       ficheros.filter(p -> p.toString().endsWith(".txt"))
               .forEach(System.out::println);
   }
   ```
   </details>

5. **Leer CSV sencillo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<Persona> personas = new ArrayList<>();
   try (BufferedReader br = new BufferedReader(new FileReader("personas.csv"))) {
       String linea;
       while ((linea = br.readLine()) != null) {
           String[] campos = linea.split(",");
           personas.add(new Persona(campos[0], Integer.parseInt(campos[1])));
       }
   }
   ```
   </details>

6. **Leer XML con DOM**
   <details>
   <summary>💡 Solución</summary>

   ```java
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
   DocumentBuilder builder = factory.newDocumentBuilder();
   Document doc = builder.parse(new File("libros.xml"));

   NodeList libros = doc.getElementsByTagName("libro");
   for (int i = 0; i < libros.getLength(); i++) {
       Element libro = (Element) libros.item(i);
       System.out.println(libro.getAttribute("titulo") + " - " + libro.getAttribute("autor"));
   }
   ```
   </details>

7. **Manejo de errores amigable**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contactos.dat"))) {
       // ... leer contactos
   } catch (FileNotFoundException e) {
       System.out.println("Aún no hay ningún contacto guardado.");
   } catch (IOException | ClassNotFoundException e) {
       System.out.println("Ha ocurrido un error al leer los contactos: " + e.getMessage());
   }
   ```

   Atrapar `FileNotFoundException` de forma específica (antes que el `IOException` más genérico del que hereda) permite dar un mensaje concreto y útil para ese caso, en vez de un error técnico genérico.
   </details>

8. **readAllLines vs. BufferedReader línea a línea**
   <details>
   <summary>💡 Solución</summary>

   `Files.readAllLines()` es más rápido de escribir y cómodo de usar, pero carga **todo** el fichero en memoria de golpe como una `List<String>` — para 100.000 líneas cortas no supone un problema, pero para un fichero de varios gigabytes agotaría la memoria disponible. Leer con `BufferedReader` línea a línea (o con `Files.lines(...)`, que devuelve un `Stream`) procesa una línea cada vez sin acumular nada, así que el consumo de memoria es prácticamente constante sin importar el tamaño del fichero. La regla práctica: `readAllLines` para ficheros que sabes que son pequeños, procesamiento línea a línea para ficheros de tamaño desconocido o potencialmente grande.
   </details>
