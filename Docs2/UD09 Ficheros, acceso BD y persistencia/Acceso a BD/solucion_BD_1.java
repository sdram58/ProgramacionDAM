import java.sql.*;
import java.util.Scanner;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }
		
		Connection conBD = null;
		String servidor = "jdbc:mysql://localhost:3306/";
        String basedatos = "login";
        String user = "root";
        String password = "aquituclave";
       	
        try {
            conBD = DriverManager.getConnection(servidor + basedatos, user, password);
                
            String consulta = "select * from users";
            Statement stm = conBD.createStatement();
            ResultSet rs = stm.executeQuery(consulta);
            
            while (rs.next()) {
                System.out.println("ID de usuario: " + rs.getString(1));
                System.out.println("Usuario: " + rs.getString(2));
                System.out.println("Contraseña: " + rs.getString(3));
                System.out.println("----------------------------------");
            }
            
            rs.close();
            stm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error en base datos: " + error.getMessage());
        }  
	}
}
