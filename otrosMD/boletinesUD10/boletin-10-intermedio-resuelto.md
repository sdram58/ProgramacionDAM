---
title: Boletín U10 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre JDBC, transacciones y el patrón DAO
---

# ✅ Boletín U10 — Intermedio (resuelto)

1. **ProductoDao completo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public class Producto {
       private int id;
       private String nombre;
       private double precio;
       private int stock;
       // constructor con los cuatro atributos, getters y setters
   }

   public class ProductoDao {
       private final Connection con;
       public ProductoDao(Connection con) { this.con = con; }

       public void insertar(Producto p) throws SQLException {
           String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";
           try (PreparedStatement ps = con.prepareStatement(sql)) {
               ps.setString(1, p.getNombre());
               ps.setDouble(2, p.getPrecio());
               ps.setInt(3, p.getStock());
               ps.executeUpdate();
           }
       }

       public Producto buscarPorId(int id) throws SQLException {
           try (PreparedStatement ps = con.prepareStatement(
                   "SELECT id, nombre, precio, stock FROM producto WHERE id = ?")) {
               ps.setInt(1, id);
               try (ResultSet rs = ps.executeQuery()) {
                   return rs.next() ? convertir(rs) : null;
               }
           }
       }

       public List<Producto> buscarTodos() throws SQLException {
           List<Producto> lista = new ArrayList<>();
           try (PreparedStatement ps = con.prepareStatement("SELECT id, nombre, precio, stock FROM producto");
                ResultSet rs = ps.executeQuery()) {
               while (rs.next()) lista.add(convertir(rs));
           }
           return lista;
       }

       public boolean actualizar(Producto p) throws SQLException {
           String sql = "UPDATE producto SET nombre = ?, precio = ?, stock = ? WHERE id = ?";
           try (PreparedStatement ps = con.prepareStatement(sql)) {
               ps.setString(1, p.getNombre());
               ps.setDouble(2, p.getPrecio());
               ps.setInt(3, p.getStock());
               ps.setInt(4, p.getId());
               return ps.executeUpdate() > 0;
           }
       }

       public boolean borrar(int id) throws SQLException {
           try (PreparedStatement ps = con.prepareStatement("DELETE FROM producto WHERE id = ?")) {
               ps.setInt(1, id);
               return ps.executeUpdate() > 0;
           }
       }

       private Producto convertir(ResultSet rs) throws SQLException {
           return new Producto(rs.getInt("id"), rs.getString("nombre"),
                               rs.getDouble("precio"), rs.getInt("stock"));
       }
   }
   ```

   El método privado `convertir` evita repetir en cada consulta el paso "fila → objeto": solo hay un sitio donde cambiar si la tabla cambia.
   </details>

2. **Buscar por nombre con LIKE**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public List<Producto> buscarPorNombre(String texto) throws SQLException {
       List<Producto> lista = new ArrayList<>();
       String sql = "SELECT id, nombre, precio, stock FROM producto WHERE nombre LIKE ?";
       try (PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setString(1, "%" + texto + "%");   // los comodines % se añaden al VALOR, no a la sentencia
           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) lista.add(convertir(rs));
           }
       }
       return lista;
   }
   ```

   Los `%` se concatenan al **valor** del parámetro, no a la sentencia SQL — así se sigue evitando la inyección.
   </details>

3. **Venta con transacción**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public void vender(int idProducto, int unidades) throws SQLException {
       con.setAutoCommit(false);
       try (PreparedStatement restar = con.prepareStatement(
                    "UPDATE producto SET stock = stock - ? WHERE id = ? AND stock >= ?");
            PreparedStatement registrar = con.prepareStatement(
                    "INSERT INTO venta (id_producto, unidades) VALUES (?, ?)")) {

           restar.setInt(1, unidades);
           restar.setInt(2, idProducto);
           restar.setInt(3, unidades);
           if (restar.executeUpdate() == 0) {
               throw new SQLException("Stock insuficiente o producto inexistente");
           }

           registrar.setInt(1, idProducto);
           registrar.setInt(2, unidades);
           registrar.executeUpdate();

           con.commit();
       } catch (SQLException e) {
           con.rollback();
           throw e;
       } finally {
           con.setAutoCommit(true);   // dejar la conexión como estaba
       }
   }
   ```
   </details>

4. **Mostrar cualquier SELECT**
   <details>
   <summary>💡 Solución</summary>

   ```java
   static void mostrar(Connection con, String select) throws SQLException {
       try (Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(select)) {
           ResultSetMetaData meta = rs.getMetaData();
           int columnas = meta.getColumnCount();

           for (int i = 1; i <= columnas; i++) {            // las columnas empiezan en 1
               System.out.print(meta.getColumnLabel(i) + "\t");
           }
           System.out.println();

           while (rs.next()) {
               for (int i = 1; i <= columnas; i++) {
                   System.out.print(rs.getString(i) + "\t");   // getString sirve para cualquier tipo
               }
               System.out.println();
           }
       }
   }
   ```
   </details>

5. **Estadísticas con una sola consulta**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String sql = "SELECT SUM(precio * stock) AS valor, COUNT(*) AS total, AVG(precio) AS media FROM producto";
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
       if (rs.next()) {
           System.out.printf("Valor del inventario: %.2f €%n", rs.getDouble("valor"));
           System.out.println("Número de productos: " + rs.getInt("total"));
           System.out.printf("Precio medio: %.2f €%n", rs.getDouble("media"));
       }
   }
   ```

   Una consulta agregada devuelve **siempre una fila** (aunque la tabla esté vacía), por eso basta un `if (rs.next())` en lugar de un `while`.
   </details>

6. **Insertar 1.000 productos: una a una vs. en lote**
   <details>
   <summary>💡 Solución</summary>

   ```java
   String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";

   // (a) una por una, con autocommit
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        PreparedStatement ps = con.prepareStatement(sql)) {
       long inicio = System.nanoTime();
       for (int i = 0; i < 1000; i++) {
           ps.setString(1, "Prueba " + i);
           ps.setDouble(2, 1.0);
           ps.setInt(3, 1);
           ps.executeUpdate();
       }
       System.out.println("Una a una: " + (System.nanoTime() - inicio) / 1_000_000 + " ms");
   }

   // (b) en lote, dentro de una transacción
   try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        PreparedStatement ps = con.prepareStatement(sql)) {
       con.setAutoCommit(false);
       long inicio = System.nanoTime();
       for (int i = 0; i < 1000; i++) {
           ps.setString(1, "Prueba " + i);
           ps.setDouble(2, 1.0);
           ps.setInt(3, 1);
           ps.addBatch();
       }
       ps.executeBatch();
       con.commit();
       System.out.println("En lote: " + (System.nanoTime() - inicio) / 1_000_000 + " ms");
   }
   ```

   La versión en lote suele ser mucho más rápida. Con autocommit, cada inserción es una transacción completa (viaje a la red + confirmación en disco); en lote, las 1.000 filas viajan juntas y se confirman una sola vez.
   </details>

7. **JTable con filtro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JTextField filtro = new JTextField(15);
   JButton buscar = new JButton("Buscar");
   DefaultTableModel modelo = new DefaultTableModel(new String[]{"Id", "Nombre", "Precio", "Stock"}, 0);
   JTable tabla = new JTable(modelo);

   buscar.addActionListener(e -> {
       modelo.setRowCount(0);                      // vaciar la tabla antes de recargarla
       try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE)) {
           for (Producto p : new ProductoDao(con).buscarPorNombre(filtro.getText())) {
               modelo.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getStock()});
           }
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
       }
   });

   JPanel arriba = new JPanel();
   arriba.add(filtro);
   arriba.add(buscar);
   add(arriba, BorderLayout.NORTH);
   add(new JScrollPane(tabla), BorderLayout.CENTER);
   ```
   </details>

8. **DAO y conexión compartida**
   <details>
   <summary>💡 Solución</summary>

   El patrón DAO concentra todo el SQL de una entidad en una sola clase: el resto del programa trabaja con objetos Java y no sabe qué base de datos (ni si hay una base de datos) hay detrás, así que cambiar de SGBD, o corregir una consulta, afecta a un único sitio. Además, la lógica de negocio se puede probar sin base de datos real. Sobre la conexión: una única `Connection` compartida por varios hilos no es segura para trabajo concurrente (dos hilos mezclarían sentencias y transacciones sobre la misma sesión), y si esa conexión se cae, toda la aplicación pierde el acceso a los datos. La solución habitual es un *pool* de conexiones: cada hilo toma una conexión, la usa y la devuelve.
   </details>
