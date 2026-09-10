package u7ArrayList;
import java.util.ArrayList;
import java.util.Scanner;

public class UD7ArticulosRelaciones {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int opcion;
        ArrayList<Articulo> almacen = new ArrayList<>();
        ArrayList<Venta> ventas = new ArrayList<>();

        do{
            System.out.println("\nGESTIÓN DE ARTÍCULOS - MENÚ DE OPCIONES");
            System.out.println("---------------------------------------");
            System.out.println("1. Crear nuevo artículo");
            System.out.println("2. Cambiar nombre de artículo");
            System.out.println("3. Cambiar precio de artículo");
            System.out.println("4. Consultar stock");
            System.out.println("5. Insertar venta");
            System.out.println("6. Calcular facturación total");
            System.out.println("7. Mostrar datos de ventas");
            System.out.println("8. Salir");
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    crearArticulo(almacen, sc);
                    break;
                case 2:
                    cambiarNombreArticulo(almacen, sc);
                    break;
                case 3:
                    cambiarPrecioArticulo(almacen, sc);
                    break;
                case 4:
                    consultarStock(almacen, sc);
                    break;
                case 5:
                    insertarVenta(almacen, ventas, sc);
                    break;
                case 6:
                    calcularFacturacionTotal(ventas);
                    break;
                case 7:
                    mostrarDatosVentas(ventas, sc);
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Por favor, introduce una opción válida...\n\n");
            }

        }while(opcion!=8);
        
    }

    private static void consultarStock(ArrayList<Articulo> almacen, Scanner sc) {
        if(almacen.size() != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<almacen.size();i++){
                if(almacen.get(i).getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Cantidad en stock: ");
                    System.out.println(almacen.get(i).getCuantosQuedan());
                    encontrado = true;
                    break;
                }
            }

            if(!encontrado){
                System.out.println("No se ha encontrado el artículo...\n\n");
            }
        }else{
            System.out.println("No hay artículos almacenados!\n\n");
        }
    }

    private static void cambiarPrecioArticulo(ArrayList<Articulo> almacen, Scanner sc) {
        if(almacen.size() != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<almacen.size();i++){
                if(almacen.get(i).getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Introduce nuevo precio: ");
                    double nuevoPrecio = Double.parseDouble(sc.nextLine());
                    almacen.get(i).setPrecio(nuevoPrecio);
                    System.out.println("El precio se ha cambiado correctamente!");
                    encontrado = true;
                    break;
                }
            }

            if(!encontrado){
                System.out.println("No se ha encontrado el artículo...\n\n");
            }
        }else{
            System.out.println("No hay artículos almacenados!\n\n");
        }
    }

    public static void cambiarNombreArticulo(ArrayList<Articulo> almacen, Scanner sc) {
        if(almacen.size() != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<almacen.size();i++){
                if(almacen.get(i).getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Introduce nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();
                    almacen.get(i).setNombre(nuevoNombre);
                    System.out.println("El nombre se ha cambiado correctamente!");
                    encontrado = true;
                    break;
                }
            }

            if(!encontrado){
                System.out.println("No se ha encontrado el artículo...\n\n");
            }
        }else{
            System.out.println("No hay artículos almacenados!\n\n");
        }
    }

    public static void crearArticulo(ArrayList<Articulo> almacen, Scanner sc) {
            System.out.print("Introduce nombre del artículo: "); //No comprobamos duplicidades
            String nombre = sc.nextLine();
            System.out.print("Introduce precio del artículo: ");
            double precio = Double.parseDouble(sc.nextLine());
            System.out.print("Introduce iva del artículo: ");
            int iva = Integer.parseInt(sc.nextLine());
            System.out.print("Introduce stock del artículo: ");
            int stock = Integer.parseInt(sc.nextLine());

            Articulo aux = new Articulo(nombre, precio, iva, stock);
            almacen.add(aux);
    }

    private static void insertarVenta(ArrayList<Articulo> almacen, ArrayList<Venta> ventas, Scanner sc) {
        if (almacen.size() != 0) {
            System.out.print("Introduce la fecha de la venta: ");
			String fechaVenta = sc.nextLine();
			Venta nuevaVenta = new Venta(fechaVenta);
            agregarArticulosAVenta(almacen, nuevaVenta, sc);
            ventas.add(nuevaVenta);
            System.out.println("Venta insertada correctamente.");
        } else {
            System.out.println("No hay artículos almacenados para realizar una venta.\n\n");
        }
    }

    private static void agregarArticulosAVenta(ArrayList<Articulo> almacen, Venta venta, Scanner sc) {
        boolean agregarOtroArticulo;
        do {
            System.out.print("Introduce el nombre del artículo: ");
            String nombreArticulo = sc.nextLine();
            Articulo articulo = buscarArticuloPorNombre(almacen, nombreArticulo);
            if (articulo != null) {
                System.out.print("Introduce la cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());
                venta.agregarArticulo(articulo, cantidad); //Sería conveniente ver si hay stock antes
				// Lo haríamos con el método "vender" del artículo. Haz la mejora correspondiente.
            } else {
                System.out.println("No se encontró el artículo!!!.");
            }
            System.out.print("¿Desea añadir otro artículo a la venta? (S/N): ");
            agregarOtroArticulo = sc.nextLine().equalsIgnoreCase("S");
        } while (agregarOtroArticulo);
    }

    private static Articulo buscarArticuloPorNombre(ArrayList<Articulo> almacen, String nombre) {
        for (Articulo articulo : almacen) {
            if (articulo.getNombre().equalsIgnoreCase(nombre)) {
                return articulo;
            }
        }
        return null;
    }

    private static void calcularFacturacionTotal(ArrayList<Venta> ventas) {
        float facturacionTotal = 0.0f;
        for (Venta venta : ventas) {
            facturacionTotal += venta.getPrecioFinal();
        }
        System.out.println("La facturación total es: " + facturacionTotal);
    }

    private static void mostrarDatosVentas(ArrayList<Venta> ventas, Scanner sc) {
        if (ventas.size() != 0) {
            System.out.println("1. Mostrar datos de ventas por ID");
            System.out.println("2. Mostrar datos de ventas por fecha");
            System.out.print("Introduce una opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    mostrarDatosVentasPorID(ventas, sc);
                    break;
                case 2:
                    mostrarDatosVentasPorFecha(ventas, sc);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("No hay ventas registradas.\n\n");
        }
    }

    private static void mostrarDatosVentasPorID(ArrayList<Venta> ventas, Scanner sc) {
        System.out.print("Introduce ID de la venta: ");
        int idVenta = Integer.parseInt(sc.nextLine());
        for (Venta venta : ventas) {
            if (venta.getId() == idVenta) {
                System.out.println("Datos de la venta:");
                mostrarDetallesVenta(venta);
                return;
            }
        }
        System.out.println("No se encontró una venta con el ID especificado.\n\n");
    }

    private static void mostrarDatosVentasPorFecha(ArrayList<Venta> ventas, Scanner sc) {
        System.out.print("Introduce la fecha de la venta (formato dd/mm/yyyy): ");
        String fechaVenta = sc.nextLine();
        for (Venta venta : ventas) {
            if (venta.getFecha().equals(fechaVenta)) {
                System.out.println("Ventas realizadas en la fecha indicada:");
                mostrarDetallesVenta(venta);
                return;
            }
        }
        System.out.println("No se encontró una venta en la fecha indicada.\n\n");
    }

    private static void mostrarDetallesVenta(Venta venta) {
        System.out.println("ID: " + venta.getId());
        System.out.println("Fecha: " + venta.getFecha());
        System.out.println("Precio Final: " + venta.getPrecioFinal());
        System.out.println("Artículos y cantidades:");
        for (int i = 0; i < venta.getArticulos().size(); i++) {
            Articulo articulo = venta.getArticulos().get(i);
            int cantidad = venta.getCantidades().get(i);
            System.out.println("- " + articulo.getNombre() + " (Cantidad: " + cantidad + ")");
        }
        System.out.println();
    }
}

