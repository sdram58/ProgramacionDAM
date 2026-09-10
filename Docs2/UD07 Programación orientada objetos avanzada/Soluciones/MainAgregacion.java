import java.util.ArrayList;
import java.util.Scanner;

public class MainAgregacion {
    public static void bajaCliente(ArrayList<Cliente> clientes){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce nombre de cliente cque desea eliminar:");
        String nombre = sc.nextLine();
                
        for(Cliente cli: clientes){
            if(cli.getNombre().equals(nombre))
                clientes.remove(cli);
        }
    }

    public static void altaCliente(ArrayList<Cliente> clientes){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre:");
        String nombre = sc.nextLine();
        System.out.println("Introduzca el apellido:");
        String apellido = sc.nextLine();
        System.out.println("Introduzca el día de nacimiento:");
        int dia = Integer.parseInt(sc.nextLine());
        System.out.println("Introduzca el mes de nacimiento:");
        int mes = Integer.parseInt(sc.nextLine());
        System.out.println("Introduzca el año de nacimiento:");
        int anyo = Integer.parseInt(sc.nextLine());

        FechaNacimiento fech = new FechaNacimiento(dia, mes, anyo);
        Cliente c1 = new Cliente(nombre,apellido,fech);
        clientes.add(c1);
        System.out.println("Cliente introducido correctamente...");
        System.out.println("DATOS DEL CLIENTE");
        System.out.println(c1);
    }

    public static void modificarCliente(ArrayList<Cliente> clientes){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce nombre de cliente cuyos datos deseas modificar:");
        String nombre = sc.nextLine();
        
        for(Cliente cli: clientes){
            if(cli.getNombre().equals(nombre)){
                System.out.println("Seleccione dato a cambiar");
                System.out.println("-------------------------");
                System.out.println("[N]: Nombre.");
                System.out.println("[A]: Apellido.");
                System.out.println("[D]: Día de nacimiento.");
                System.out.println("[M]: Mes de nacimiento.");
                System.out.println("[Y]: Año de nacimiento.");
                System.out.println("Seleccione una opción:");
                String opcion = sc.nextLine().toUpperCase();

                switch(opcion){
                    case "N":
                        System.out.println("Introduce nuevo nombre:"); 
                        cli.setNombre(sc.nextLine());
                        break;
                    case "A":
                        System.out.println("Introduce nuevo apellido:"); 
                        cli.setApellido(sc.nextLine());
                        break;
                    case "D":
                        System.out.println("Introduce nuevo día:"); 
                        cli.getFecha().setDia(Integer.parseInt(sc.nextLine()));
                        break;
                    case "M":
                        System.out.println("Introduce nuevo mes:"); 
                        cli.getFecha().setMes(Integer.parseInt(sc.nextLine()));
                        break;
                    case "Y":
                        System.out.println("Introduce nuevo año:"); 
                        cli.getFecha().setAño(Integer.parseInt(sc.nextLine()));
                        break;
                    default:
                        System.out.println("Dato incorrecto...");
                }
            }
        }
         
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String opcion;
        ArrayList<Cliente> clientes = new ArrayList<>();
        do{
            System.out.println("Gestión de clientes - Menú de opciones");
            System.out.println("--------------------------------------");
            System.out.println("[A]: Alta de cliente.");
            System.out.println("[M]: Modificar cliente.");
            System.out.println("[B]: Baja de cliente.");
            System.out.println("[S]: Salir.");
            System.out.println("Seleccione una opción:");
            opcion = sc.nextLine().toUpperCase();

            switch(opcion){
                case "A": 
                    altaCliente(clientes);
                    break;
                case "M":
                    modificarCliente(clientes);
                    break;
                case "B":
                    bajaCliente(clientes);
                    break;
                default:
                    System.out.println("OPCIÓN INCORRECTA...");
            }  
        }while(!opcion.equals("S"));
    }    
}

