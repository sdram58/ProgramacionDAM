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
        String password = "root";
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduzca usuario: ");
        String usuario = sc.nextLine();
        
        System.out.println("Introduzca contraseña: ");
        String pass = sc.nextLine();
	
        try {
            conBD = DriverManager.getConnection(servidor + basedatos, user, password);
                
            String consulta = "select * from users where user =? and password=?";
            PreparedStatement stm = conBD.prepareStatement(consulta);
            stm.setString(1, usuario);
            stm.setString(2, pass);
            ResultSet rs = stm.executeQuery();
    
            if(rs.next())
            	System.out.println("Autenticación correcta!");
            else
            	System.out.println("Autenticación fallida!");
            
            rs.close();
            stm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error en base datos: " + error.getMessage());
        }  
	}
}
