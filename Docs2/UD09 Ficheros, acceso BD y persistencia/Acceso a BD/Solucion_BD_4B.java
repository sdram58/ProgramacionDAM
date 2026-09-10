import java.sql.*;
import java.util.Scanner;

public class Solucion4B {

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
	
        try {
            conBD = DriverManager.getConnection(servidor + basedatos, user, password);
                
            Statement stm = conBD.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stm.executeQuery("select * from users");
    
            System.out.println("TABLA DE USUARIOS");
            System.out.println("-----------------");
            while(rs.next()) {
            	System.out.println("ID: " + rs.getString(1));
            	System.out.println("Usuario: " + rs.getString(2));
            	System.out.println("Contraseña: " + rs.getString(3));
            	System.out.println("-------------");
            	
            }
            
            System.out.println("Elija el usuario a modificar (ID): ");
            int nid = Integer.parseInt(sc.nextLine());
                        
            System.out.println("Introduzca el nuevo nombre de usuario: ");
            String nuevonombre = sc.nextLine();
            
            rs.beforeFirst();
            while(rs.next()) {
            	if(nid == rs.getInt(1))
            		 rs.updateString(2,nuevonombre);
            		 rs.updateRow();
            }
            
            rs.beforeFirst();
            
            System.out.println("TABLA DE USUARIOS");
            System.out.println("-----------------");
            while(rs.next()) {
            	System.out.println("ID: " + rs.getString(1));
            	System.out.println("Usuario: " + rs.getString(2));
            	System.out.println("Contraseña: " + rs.getString(3));
            	System.out.println("-------------");
            	
            }
            
            rs.close();
            stm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error en base datos: " + error.getMessage());
        }  
	}
}
