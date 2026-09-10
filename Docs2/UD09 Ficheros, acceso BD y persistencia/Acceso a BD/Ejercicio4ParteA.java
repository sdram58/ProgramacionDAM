package ejercicio4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio4ParteA {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/pruebas";
        String user = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión realizada correctamente!");

            System.out.println("Contenido inicial de la tabla 'usuarios':");
            String query = "SELECT * FROM usuarios;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            printResultSet(resultSet);

            String updateQuery = "UPDATE usuarios SET nombre = 'Toni' WHERE id = 1;";
            statement.executeUpdate(updateQuery);
            System.out.println("El nombre del primer usuario ha sido actualizado!");

            System.out.println("Contenido actualizado de la tabla 'usuarios':");
            resultSet = statement.executeQuery(query);
            printResultSet(resultSet);

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellido");
            System.out.printf(id + " - " + nombre + " - " + apellido);
        }
    }
}
