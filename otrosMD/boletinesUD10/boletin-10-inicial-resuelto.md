---
title: Boletín U10 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre JDBC y bases de datos relacionales
---

# ✅ Boletín U10 — Inicial (resuelto)

En todas las soluciones se asume esta configuración al principio de la clase:

```java
private static final String URL = "jdbc:mysql://localhost:3306/tienda";
private static final String USUARIO = "root";
private static final String CLAVE = "tu_clave";
```

1. **Comprobar la conexión**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE)) {
       System.out.println("Conexión correcta");
   } catch (SQLException e) {
       System.err.println("No se pudo conectar: " + e.getMessage());
   }
   ```
   </details>

2. **Mostrar todos los productos**
   <details>
   <summary>💡 Solución</summary>

   ```java
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, nombre, precio, stock FROM producto")) {
       while (rs.next()) {
           System.out.printf("%d - %s - %.2f € - %d uds%n",
                   rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"));
       }
   } catch (SQLException e) {
       System.err.println("Error: " + e.getMessage());
   }
   ```
   </details>

3. **Productos caros, ordenados**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String sql = "SELECT nombre, precio FROM producto WHERE precio > 20 ORDER BY precio DESC";
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
       while (rs.next()) {
           System.out.println(rs.getString("nombre") + ": " + rs.getDouble("precio") + " €");
       }
   }
   ```

   Filtrar y ordenar se hace **en la consulta SQL**, no recorriendo todo el `ResultSet` en Java: la base de datos está pensada para eso y lo hace mucho mejor.
   </details>

4. **Insertar con PreparedStatement**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String nombre = "Webcam";
   double precio = 39.90;
   int stock = 8;

   String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        PreparedStatement ps = con.prepareStatement(sql)) {
       ps.setString(1, nombre);
       ps.setDouble(2, precio);
       ps.setInt(3, stock);
       int filas = ps.executeUpdate();
       System.out.println("Filas insertadas: " + filas);
   }
   ```
   </details>

5. **Buscar por id**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Scanner sc = new Scanner(System.in);
   System.out.print("Id del producto: ");
   int id = sc.nextInt();

   String sql = "SELECT nombre, precio FROM producto WHERE id = ?";
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        PreparedStatement ps = con.prepareStatement(sql)) {
       ps.setInt(1, id);
       try (ResultSet rs = ps.executeQuery()) {
           if (rs.next()) {
               System.out.println(rs.getString("nombre") + " - " + rs.getDouble("precio") + " €");
           } else {
               System.out.println("No existe");
           }
       }
   }
   ```

   `rs.next()` sirve a la vez para "avanzar a la primera fila" y para saber si hay alguna: si devuelve `false`, la consulta no devolvió nada.
   </details>

6. **Reponer stock bajo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String sql = "UPDATE producto SET stock = stock + 10 WHERE stock < 5";
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        Statement st = con.createStatement()) {
       int modificados = st.executeUpdate(sql);
       System.out.println("Productos modificados: " + modificados);
   }
   ```

   Aquí basta `Statement` porque la sentencia no lleva ningún dato externo: no hay nada que inyectar.
   </details>

7. **Borrar por id**
   <details>
   <summary>💡 Solución</summary>

   ```java
   Scanner sc = new Scanner(System.in);
   System.out.print("Id a borrar: ");
   int id = sc.nextInt();

   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        PreparedStatement ps = con.prepareStatement("DELETE FROM producto WHERE id = ?")) {
       ps.setInt(1, id);
       if (ps.executeUpdate() > 0) {
           System.out.println("Producto borrado");
       } else {
           System.out.println("No existía ningún producto con ese id");
       }
   }
   ```
   </details>

8. **Inyección SQL**
   <details>
   <summary>💡 Solución</summary>

   Una inyección SQL ocurre cuando un dato que escribe el usuario se concatena dentro de la sentencia, de forma que el servidor lo interpreta como **parte del código SQL** en vez de como un dato. Por ejemplo, si el programa construye `"... WHERE nombre = '" + texto + "'"` y el usuario escribe `x' OR '1'='1`, la condición pasa a cumplirse siempre y se devuelven todas las filas (o, peor, se pueden añadir sentencias que borren datos). Un `PreparedStatement` manda al servidor la sentencia con los marcadores `?` **por un lado** y los valores **por otro**: el servidor ya ha entendido la estructura de la sentencia antes de recibir los datos, así que estos nunca pueden cambiar su significado, por muchas comillas o palabras reservadas que contengan.
   </details>
