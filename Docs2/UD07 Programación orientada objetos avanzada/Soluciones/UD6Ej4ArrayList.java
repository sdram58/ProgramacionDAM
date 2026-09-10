/* Ampliación ejercicio 4 - Artículo

Crea un programa principal con el siguiente menú de opciones:
1. Crear nuevo artículo
2. Cambiar nombre de artículo
3. Cambiar precio de artículo
4. Consultar stock
5. Salir

USAREMOS UN ARRAYLIST EN ESTA VERSIÓN, EN VEZ DE UN ARRAY ESTÁTICO
*/

package u6ejercicio4;
import java.util.ArrayList;
import java.util.Scanner;

public class UD6Ej4ArrayList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        ArrayList<Articulo> almacen = new ArrayList<>();

        do{
            System.out.println("\nGESTIÓN DE ARTÍCULOS - MENÚ DE OPCIONES");
            System.out.println("---------------------------------------");
            System.out.println("1. Crear nuevo artículo");
            System.out.println("2. Cambiar nombre de artículo");
            System.out.println("3. Cambiar precio de artículo");
            System.out.println("4. Consultar stock");
            System.out.println("5. Salir");
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
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Por favor, introduce una opción válida...\n\n");
            }

        }while(opcion!=5);
        
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
}

//Analiza qué sucede si un usuario introduce, al crear un artículo, todos los datos correctos excepto una cantidad negativa.
//¿se crea el artículo? ¿con qué valores?
//Si intentas buscarlo, ¿qué pasa?