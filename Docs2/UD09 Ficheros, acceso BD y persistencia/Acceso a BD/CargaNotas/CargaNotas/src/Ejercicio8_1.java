import java.sql.*;
import java.util.Scanner;

public class Ejercicio8_1 {

    public static void menu() {
        System.out.println("GESTIÓN DE TIENDA");
        System.out.println("Menú de opciones");
        System.out.println("----------------");
        System.out.println("1. Mostrar el nombre de los productos, el precio en euros y el precio en dólares");
        System.out.println("2. Mostrar el nombre y el precio del producto más caro");
        System.out.println("3. Mostrar el nombre y precio del producto más barato");
        System.out.println("4. Mostrar todos los productos que tengan un precio mayor que 200€ y cuyo código de fabricante sea el 6");
        System.out.println("5. Mostrar todos los productos del fabricante Lenovo");
        System.out.println("6. Salir\n");
        System.out.print("Seleccione una opción: ");
    }
    
    public static void consulta(String query, Connection con) throws SQLException{
        Statement sentencia = con.createStatement();
        ResultSet rConsulta = sentencia.executeQuery(query);

        if(rConsulta.getRow() > 1){
            float precio;
            System.out.print("Resultado: ");
            while(rConsulta.next()){
                precio = rConsulta.getFloat("precio");
                System.out.println(rConsulta.getString("nombre") + " -- " + precio + " -- " + String.format("%.2f", precio * 0.94));
            }
            System.out.println();
        } else {
            System.out.print("Resultado: ");
            rConsulta.next();
            System.out.println(rConsulta.getString("nombre") + " -- " + rConsulta.getFloat("precio"));
        }
           
        sentencia.close();
    }

    public static void main(String[] args) {
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        String query;

        Connection con;
        String url = "jdbc:mysql://localhost/tienda";
        String usr = "root";
        String pwd = "123456";
        try {
            con = DriverManager.getConnection(url, usr, pwd);

            do {
                menu();

                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: por favor introduzca un numero entero.\n\n");
                }

                switch (opcion) {
                    case 1:
                        consulta("SELECT nombre, precio FROM producto", con);
                        break;
                    case 2:
                        query = "SELECT nombre, precio FROM producto ORDER BY precio DESC LIMIT 1;";
                        consulta(query, con);
                        break;
                    case 3:
                        query = "SELECT nombre, precio FROM producto ORDER BY precio ASC LIMIT 1;";
                        consulta(query, con);
                        break;
                    case 4:
                        
                        consulta4(con);
                        break;
                    case 5:
                        productosLenovo(con);
                        break;
                }

            } while (opcion != 6);
            con.close();
            System.out.println("Saliendo...");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
