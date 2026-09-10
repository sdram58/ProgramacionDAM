import java.sql.*;

public class AccesoBD1 {

	public static void main(String[] args) {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }
 
        //Realizamos la conexión con el servidor MySQL/MariaDB
        //Con los datos de conexión: dirección, puerto, usuario y contraseña
        //En este ejemplo suponemos que el servidor MySQL está en localhost, puerto 3306, usuario root, sin contraseña
        Connection conBD = null;
        String servidor = "jdbc:mysql://localhost:3306/";
        String basedatos = "pizzeria";
        String user = "root";
        String password = "26986990";
        
        try {
            conBD = DriverManager.getConnection(servidor + basedatos, user, password);
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }
 
        //Creamos una declaración (statement) de la conexión
        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }
 
        //Ejecutamos una consulta SQL contra el statement anterior
        //El resultado se guardará en el ResultSet
        ResultSet mRS = null;
        try {
            mRS = mStm.executeQuery("Select NPizza, price from pizzas");
            //Recorremos todos los registros del SQL devuelto en el ResultSet
            while (mRS.next()) {
                System.out.println("NPizza: " + mRS.getString(1) + " Price: " + mRS.getString(2));
            }
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }
 
        //Cerramos el ResultSet, el Statement y la conexión
        try {
            mRS.close();
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }

	}

}
