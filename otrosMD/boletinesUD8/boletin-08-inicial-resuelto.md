---
title: Boletín U08 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre ficheros y streams
---

# ✅ Boletín U08 — Inicial (resuelto)

1. **Comprobar una ruta**
   <details>
   <summary>💡 Solución</summary>

   ```java
   File f = new File("C:/Temp");
   System.out.println("¿Existe? " + f.exists());
   System.out.println("¿Es fichero? " + f.isFile());
   System.out.println("¿Es carpeta? " + f.isDirectory());
   if (f.isDirectory()) {
       System.out.println("Elementos: " + f.listFiles().length);
   }
   ```
   </details>

2. **Crear notas.txt**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (FileWriter fw = new FileWriter("notas.txt")) {
       fw.write("Línea 1\n");
       fw.write("Línea 2\n");
       fw.write("Línea 3\n");
       fw.write("Línea 4\n");
       fw.write("Línea 5\n");
   } catch (IOException e) {
       e.printStackTrace();
   }
   ```
   </details>

3. **Leer numerado**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (BufferedReader br = new BufferedReader(new FileReader("notas.txt"))) {
       String linea;
       int numero = 1;
       while ((linea = br.readLine()) != null) {
           System.out.println(numero + ": " + linea);
           numero++;
       }
   } catch (IOException e) {
       e.printStackTrace();
   }
   ```
   </details>

4. **Modo append**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (FileWriter fw = new FileWriter("notas.txt", true)) {
       fw.write("Nueva línea añadida\n");
   } catch (IOException e) {
       e.printStackTrace();
   }
   ```
   </details>

5. **Listar ficheros con tamaño**
   <details>
   <summary>💡 Solución</summary>

   ```java
   File carpeta = new File("C:/Temp");
   for (File f : carpeta.listFiles()) {
       System.out.println(f.getName() + " - " + f.length() + " bytes");
   }
   ```
   </details>

6. **FileReader vs. BufferedReader**
   <details>
   <summary>💡 Solución</summary>

   `FileReader` solo sabe leer carácter a carácter (o un bloque de caracteres) — no tiene ningún método para leer una línea completa. `BufferedReader` añade un búfer interno (mejora el rendimiento agrupando lecturas) y, sobre todo, el método `readLine()`, mucho más práctico cuando el fichero es texto organizado en líneas. Por eso casi siempre se usan juntos: `FileReader` abre el fichero, `BufferedReader` lo envuelve para darte una forma cómoda de leerlo.
   </details>

7. **Files.readAllLines**
   <details>
   <summary>💡 Solución</summary>

   ```java
   List<String> lineas = Files.readAllLines(Path.of("notas.txt"));
   System.out.println("Número de líneas: " + lineas.size());
   ```
   </details>

8. **write(int) vs. write(String)**
   <details>
   <summary>💡 Solución</summary>

   ```java
   writer.write(2024);          // MAL: escribe el carácter Unicode nº 2024, no el texto "2024"
   writer.write("" + 2024);      // BIEN: concatenar con un String vacío lo convierte a texto
   writer.write(String.valueOf(2024));  // BIEN: alternativa equivalente, más explícita
   ```

   `write(int)` interpreta el número como el **código** de un carácter, no como el valor a escribir en texto — es una de las trampas más comunes al empezar a escribir en ficheros en Java.
   </details>
