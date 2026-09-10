package clinicaVeterinaria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void menu(){
        System.out.println("Bienvenido/a a SOFTVETClinic\n");
        System.out.println("Menú de opciones");
        System.out.println("----------------");
        System.out.println("1. Alta de cliente.");
        System.out.println("2. Baja de cliente.");
        System.out.println("3. Alta de animal.");
        System.out.println("4. Baja de animal.");
        System.out.println("5. Crear actuación.");
        System.out.println("6. Finalizar actuación.");
        System.out.println("7. Generar factura.");
        System.out.println("8. Obtener facturación mensual.");
        System.out.println("9. Mostrar información.");
        System.out.println("10. Salir");
    }

    public static Cliente buscarCliente(String nombre, List<Cliente> clientes){
        for(int i = 0; i < clientes.size(); i++){
            if(clientes.get(i).getNombre().equals(nombre))
            return clientes.get(i);
        }
        return null;
    }

    public static void altaCliente(Scanner sc, List<Cliente> clientes){
        System.out.println("ALTA de CLIENTE");
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine();
        
        Cliente c = buscarCliente(nombre, clientes);
        
        if(c == null){
            System.out.print("Introduce fecha de nacimiento (dd/mm/aaaa): ");
            String fechaNac = sc.nextLine();
            System.out.print("Introduce dirección: ");
            String direccion = sc.nextLine();
            System.out.print("Introduce teléfono: ");
            String telefono = sc.nextLine();

            int nuevo_id = (clientes.size() == 0) ? 1 : clientes.get(clientes.size()-1).getId() + 1;

            Cliente cNuevo = new Cliente(nuevo_id, nombre, fechaNac, direccion, telefono);
            clientes.add(cNuevo);
            System.out.println("Cliente creado satisfactoriamente.");

        } else {
            System.out.println("Ese cliente ya existe, introduzca otro nombre!");
        }   
    }

    public static void bajaCliente(Scanner sc, List<Cliente> clientes){
        System.out.println("BAJA de CLIENTE");
        System.out.print("Introduce el nombre del cliente a eliminar: ");
        String nombre = sc.nextLine();
        
        Cliente c = buscarCliente(nombre, clientes);

        if(c == null){
            System.out.println("El cliente no existe!");
        } else if(c.getAnimales().size() > 0) {
            System.out.println("No se puede eliminar un cliente con animales registrados.");
            System.out.println("Primero hay que dar de baja los animales...");
        } else {
            clientes.remove(c);
        }
    }

    public static void altaAnimal(Scanner sc, List<Cliente> clientes){
        System.out.println("ALTA de ANIMAL");
        System.out.print("Introduce el nombre del cliente: ");
        String nombreCli = sc.nextLine();
        
        Cliente c = buscarCliente(nombreCli, clientes);
        
        if(c != null){
            System.out.print("Introduce el nombre del animal: ");
            String nombreAnimal = sc.nextLine();

            boolean animalEncontrado = false;

            for(int i = 0; i < c.getAnimales().size(); i++){
                if(c.getAnimales().get(0).getNombre().equals(nombreAnimal)){
                    animalEncontrado = true;
                    break;
                }
            }

            if(!animalEncontrado){
                System.out.print("Introduce especie: ");
                String especie = sc.nextLine();
                System.out.print("Introduce raza: ");
                String raza = sc.nextLine();
                System.out.print("Introduce fecha de nacimiento: ");
                String fNac = sc.nextLine();
                System.out.print("Introduce sexo: ");
                String sexo = sc.nextLine();

                Animal a = new Animal(especie, raza, nombreAnimal, fNac, sexo, c);

            } else {
                System.out.println("El cliente ya tiene un animal con ese nombre.");
            }

        } else {
            System.out.println("El cliente no existe!");
        }   

    }

    public static void bajaAnimal(){

    }

    public static void crearActuacion(){

    }

    public static void finalizarActuacion(){

    }

    public static void generarFactura(Scanner sc, List<Actuacion> actuaciones){
        // También podríamos localizar por cliente y animal
        System.out.print("Introduce el ID de la actuación:");
        int idAct = Integer.parseInt(sc.nextLine());

        boolean encontrado = false;
        Actuacion act;

        for (int i = 0; i < actuaciones.size(); i++){
            act = actuaciones.get(i);
            if(act.getIdActuacion() == idAct){
                System.out.print("Introduce importe: ");
                float importe = Float.parseFloat(sc.nextLine());
                System.out.print("Introduce fecha (dd/mm/aaaa): ");
                String fecha = sc.nextLine();

                Factura f = new Factura(0, importe, false, fecha, act, );
            }
        }

    }

    public static void obtenerFacturacionMensual(){

    }

    public static void mostrarInfo(){

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        List<Cliente> clientes = new ArrayList<>();
        List<Actuacion> actuaciones = new ArrayList<>();

        do{
            menu();
            System.out.print("\nPor favor, seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    altaCliente(sc, clientes);
                    break;
                case 2:
                    bajaCliente(sc, clientes);
                     break;
                case 3:
                    altaAnimal(sc, clientes);
                    break;
                case 4:
                    bajaAnimal();
                    break;
                case 5:
                    crearActuacion();
                    break;
                case 6:
                    finalizarActuacion();
                    break;
                case 7:
                    generarFactura(sc, actuaciones);
                    break;
                case 8:
                    obtenerFacturacionMensual();
                     break;
                case 9:
                    mostrarInfo();
                    break;
                default:
                    System.out.println("Por favor, introduzca una opción válida...");
            }


        }while(opcion != 10);

        System.out.println("Saliendo, gracias por usar SOFTVETClinic.");
        
    }
    
}
