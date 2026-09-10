import java.sql.*;

public class Ejercicio4A {

    static final String databaseURL = "jdbc:mysql://localhost/login";
    static final String USER = "usuario";
    static final String PASS = "password";

    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection(databaseURL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            System.out.println("Usuarios sin cambios...");
			
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

            String updateSql = "UPDATE users SET usuario=? WHERE ID=1";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, "NUEVONOMBRE");
                updateStmt.executeUpdate();
                System.out.println("Cambiando nombre del 1er usuario.");
            }

            System.out.println("Usuarios tras el cambio...");
			try (ResultSet updatedRs = stmt.executeQuery(sql)) {
                System.out.println("ID\tUsuario\tContraseña");
                while (updatedRs.next()) {
                    int id = updatedRs.getInt("ID");
                    String user = updatedRs.getString("usuario");
                    String pass = updatedRs.getString("contraseña");
                    System.out.println(id + "\t" + usuario + "\t" + pass);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
