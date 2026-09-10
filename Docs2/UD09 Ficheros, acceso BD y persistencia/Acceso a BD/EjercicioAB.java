import java.sql.*;

public class Ejercicio4B {

    static final String databaseURL = "jdbc:mysql://localhost/login";
    static final String USER = "usuario";
    static final String PASS = "password";

    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection(databaseURL, USER, PASS);
            Statement stmt = conn.createStatement();
            Scanner sc = new Scanner(System.in);
        ) {
			
            String sql = "SELECT * FROM users";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("ID\tUsuario\tContraseña");
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String user = rs.getString("usuario");
                    String pass = rs.getString("contraseña");
                    System.out.println(id + "\t" + user + "\t" + pass);
                }
            }

            System.out.println("Introduzca el ID del usuario que desea modificar:");
            int userId = Integer.parseInt(sc.nextLine()); //¿qué pasa si no existe...?

            System.out.println("Introduzca el nuevo nombre de usuario:");
            String nuevoNombre = sc.nextLine();

            String updateSql = "UPDATE users SET usuario=? WHERE ID=?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, nuevoNombre);
                updateStmt.setInt(2, userId);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Nombre del usuario modificado con éxito.");
                } else {
                    System.out.println("No se encontró ningún usuario con el ID introducido.");
                }
            }

            System.out.println("Usuarios tras el cambio...");
			try (ResultSet updatedRs = stmt.executeQuery(sql)) {
                System.out.println("ID\tUsuario\tContraseña");
                while (updatedRs.next()) {
                    int id = updatedRs.getInt("ID");
                    String user = updatedRs.getString("usuario");
                    String pass = updatedRs.getString("contraseña");
                    System.out.println(id + "\t" + user + "\t" + pass);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
