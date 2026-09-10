package ejercicio4;
import java.util.Scanner;

/* Ampliación ejercicio 4 - Artículo

Crea un programa principal con el siguiente menú de opciones:
1. Crear nuevo artículo
2. Cambiar nombre de artículo
3. Cambiar precio de artículo
4. Consultar stock
5. Salir

Actúa en consecuencia de cada opción. Para almacenar los artículos en memoria utiliza un array con 30 posiciones disponibles. Al finalizar cada opción, si no es salir, volveremos al menú. Los puntos 2, 3 y 4 deben consultarle al usuario algún dato con el que localizar el artículo. Si no existe nos quejaremos.

Reflexiona cómo implementar estas mejoras (no están resueltas): evitar artículos con código duplicado, eliminar artículo.
*/

public class UD6Ej4b {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        Articulo[] almacen = new Articulo[30];
        int articulosAlmacenados = 0;

        do{
            System.out.println("GESTIÓN DE ARTÍCULOS - MENÚ DE OPCIONES");
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
                    articulosAlmacenados = crearArticulo(almacen, articulosAlmacenados, sc);
                    break;
                case 2:
                    cambiarNombreArticulo(almacen, sc, articulosAlmacenados);
                    break;
                case 3:
                    cambiarPrecioArticulo(almacen, sc, articulosAlmacenados);
                    break;
                case 4:
                    consultarStock(almacen, sc, articulosAlmacenados);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Por favor, introduce una opción válida...\n\n");
            }

        }while(opcion!=5);
        
    }

    private static void consultarStock(Articulo[] almacen, Scanner sc, int articulosAlmacenados) {
        if(articulosAlmacenados != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<articulosAlmacenados;i++){
                if(almacen[i].getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Cantidad en stock: ");
                    System.out.println(almacen[i].getCuantosQuedan());
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

    private static void cambiarPrecioArticulo(Articulo[] almacen, Scanner sc, int articulosAlmacenados) {
        if(articulosAlmacenados != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<articulosAlmacenados;i++){
                if(almacen[i].getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Introduce nuevo precio: ");
                    double nuevoPrecio = Double.parseDouble(sc.nextLine());
                    almacen[i].setPrecio(nuevoPrecio);
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

    private static void cambiarNombreArticulo(Articulo[] almacen, Scanner sc, int articulosAlmacenados) {
        if(articulosAlmacenados != 0){
            System.out.print("Introduce nombre del artículo: ");
            String nombre = sc.nextLine();
            boolean encontrado = false;

            for(int i=0; i<articulosAlmacenados;i++){
                if(almacen[i].getNombre().equals(nombre)){
                    System.out.print("Artículo encontrado. Introduce nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();
                    almacen[i].setNombre(nuevoNombre);
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

    public static int crearArticulo(Articulo[] almacen, int articulosAlmacenados, Scanner sc) {
        if(articulosAlmacenados < 30){
            System.out.print("Introduce nombre del artículo: "); //No comprobamos duplicidades
            String nombre = sc.nextLine();
            System.out.print("Introduce precio del artículo: ");
            double precio = Double.parseDouble(sc.nextLine());
            System.out.print("Introduce iva del artículo: ");
            int iva = Integer.parseInt(sc.nextLine());
            System.out.print("Introduce stock del artículo: ");
            int stock = Integer.parseInt(sc.nextLine());

            Articulo aux = new Articulo(nombre, precio, iva, stock);
            almacen[articulosAlmacenados] = aux;
            return (articulosAlmacenados + 1);
                        
        }else{
            System.out.println("El almacén está lleno, no se pueden crear más artículos.\n\n");
            return articulosAlmacenados;
        }
    }
}

//Analiza qué sucede si un usuario introduce, al crear un artículo, todos los datos correctos excepto una cantidad negativa.
//¿se crea el artículo? ¿con qué valores?
//Si intentas buscarlo, ¿qué pasa?