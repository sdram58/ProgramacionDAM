---
title: Boletín U10 — Extras
description: Retos de programación con bases de datos relacionales desde Java
---

# 🔥 Boletín U10 — Extras

> Las plataformas como CodeWars o AceptaElReto no sirven para bases de datos reales, así que estos retos son pequeños proyectos. Para practicar SQL puro, sí hay plataformas con ejercicios de consultas (busca "SQL" en CodeWars, que tiene una categoría propia). Aquí tienes dos retos.

## Reto 1: un cajero con transacciones

Crea una tabla `cuenta (id, titular, saldo)` y un programa de consola con un menú: **ingresar**, **retirar**, **transferir** entre dos cuentas y **ver saldo**. Las reglas: nunca se puede dejar un saldo negativo, y una transferencia debe ser todo-o-nada (si falla la segunda parte, la primera no debe quedar aplicada). Controla todos los errores con mensajes claros, sin mostrar nunca una traza de pila al usuario.

:::tip[Pista]
La comprobación "no dejar saldo negativo" se puede hacer en la propia sentencia SQL: `UPDATE cuenta SET saldo = saldo - ? WHERE id = ? AND saldo >= ?`. Si `executeUpdate` devuelve `0`, es que no se modificó ninguna fila porque no había saldo suficiente — y no hay ningún hueco entre "comprobar" y "restar" por el que otro usuario se pueda colar.
:::

<details>
<summary>💡 Solución</summary>

```java
public void transferir(int origen, int destino, double cantidad) throws SQLException {
    try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE)) {
        con.setAutoCommit(false);
        try (PreparedStatement quitar = con.prepareStatement(
                     "UPDATE cuenta SET saldo = saldo - ? WHERE id = ? AND saldo >= ?");
             PreparedStatement poner = con.prepareStatement(
                     "UPDATE cuenta SET saldo = saldo + ? WHERE id = ?")) {

            quitar.setDouble(1, cantidad);
            quitar.setInt(2, origen);
            quitar.setDouble(3, cantidad);
            if (quitar.executeUpdate() == 0) {
                throw new SQLException("Saldo insuficiente o la cuenta de origen no existe");
            }

            poner.setDouble(1, cantidad);
            poner.setInt(2, destino);
            if (poner.executeUpdate() == 0) {
                throw new SQLException("La cuenta de destino no existe");
            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();       // si falla cualquiera de las dos, no queda ninguna aplicada
            throw e;
        }
    }
}
```

Fíjate en que el `rollback` también cubre el caso de que el destino no exista: el dinero ya restado del origen vuelve a su sitio.
</details>

## Reto 2: de CSV a base de datos

Escribe un programa que lea un fichero CSV de productos (una línea por producto: `nombre,precio,stock`) y los inserte todos en la tabla `producto`. Si **cualquier línea** tiene un formato incorrecto, no debe quedar insertado **ninguno** de los productos, y el programa debe decir qué línea falló.

:::tip[Pista]
Une lo que sabes de ficheros (unidad 8), excepciones (unidad 3) y transacciones: lee el fichero línea a línea dentro de la transacción, y ante la primera línea inválida lanza una excepción que provoque el `rollback`.
:::

<details>
<summary>💡 Solución</summary>

```java
public void importar(String ruta) throws IOException, SQLException {
    try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
         BufferedReader br = new BufferedReader(new FileReader(ruta))) {

        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)")) {

            String linea;
            int numero = 0;
            while ((linea = br.readLine()) != null) {
                numero++;
                String[] campos = linea.split(",");
                try {
                    ps.setString(1, campos[0].trim());
                    ps.setDouble(2, Double.parseDouble(campos[1].trim()));
                    ps.setInt(3, Integer.parseInt(campos[2].trim()));
                    ps.addBatch();
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    throw new IllegalArgumentException("Formato incorrecto en la línea " + numero + ": " + linea);
                }
            }
            ps.executeBatch();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }
}
```

Con `addBatch()` + `executeBatch()` las inserciones viajan al servidor agrupadas en vez de una a una, lo que además es mucho más rápido.
</details>
