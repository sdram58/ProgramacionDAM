package u7Mascotas;

import java.util.ArrayList;
import java.util.Scanner;

public class ProgramaInventario {

    private static String getTipoMascota(Mascota m) {
        if (m instanceof Perro) {
            return "Perro";
        } else if (m instanceof Gato) {
            return "Gato";
        } else if (m instanceof Loro) {
            return "Loro";
        } else if (m instanceof Canario) {
            return "Canario";
        } else {
            return null;
        }
    }

    public static void mostrarListaAnimales(ArrayList<Mascota> animales) {
        System.out.println("=== LISTA DE ANIMALES ===");
        for (int i = 0; i < animales.size(); i++) {
            Mascota m = animales.get(i);
            String tipo = getTipoMascota(m);
            String nombre = m.getNombre();
            System.out.println(i + ": " + tipo + " " + nombre);
        }
    }

    public static void mostrarUnAnimal(ArrayList<Mascota> animales, Scanner sc) {
        System.out.println("Introduzca número del animal (1 al " + animales.size() + "): ");
        int indice = Integer.parseInt(sc.nextLine()) - 1; //Piensa: ¿porqué - 1?
                
        if (indice >= 0 && indice < animales.size()) {
            System.out.println("*** MASCOTA " + indice + " ***");
            Mascota m = animales.get(indice);
            m.muestra();
        } else{
            System.out.println("Número inexistente!");
        }

    }

    public static void mostrarTodosAnimales(ArrayList<Mascota> animales) {
        Mascota m;
        for (int i = 0; i < animales.size(); i++) {
            m = animales.get(i);
            m.muestra();
            System.out.println("");
        }
    }

    public static boolean eliminarAnimal(ArrayList<Mascota> animales, Scanner sc) {
        System.out.println("Introduzca número del animal (1 al " + animales.size() + "): ");
        int indice = Integer.parseInt(sc.nextLine()) - 1; //Piensa: ¿porqué - 1?

        if (indice >= 0 && indice < animales.size()) {
            animales.remove(indice);
            return true;
        } else {
            return false;
        }
        // ¿qué cambios tendrías que hacer si quisieras eliminarlo buscando por el nombre?
    }

    public static void insertarAnimal(ArrayList<Mascota> animales, Scanner sc){
        System.out.println("Escriba tipo de animal a insertar: ");
        String tipo = sc.nextLine();
        System.out.println("Introduzca el nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Introduzca edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        System.out.println("Introduzca fecha de nacimiento (dd/mm/aaaa): ");
        String fechaNac = sc.nextLine();
        
        switch(tipo){
            case "Perro":
                System.out.println("Introduzca raza: ");
                String raza = sc.nextLine();
                Perro p = new Perro(nombre, edad, "nuevo", fechaNac, raza, false);
                animales.add(p);
                break;
            case "Gato":
                System.out.println("Introduzca color: ");
                String color = sc.nextLine();
                Gato g = new Gato(nombre, edad, "nuevo", fechaNac, color, false);
                animales.add(g);
                break;
            case "Loro":
                System.out.println("Introduzca tipo de pico: ");
                String pico = sc.nextLine();
                System.out.println("Introduzca origen: ");
                String origen = sc.nextLine();
                Loro lo = new Loro(nombre, edad, "nuevo", fechaNac, pico, true, origen, false);
                animales.add(lo);
                break;
            case "Canario":
                System.out.println("Introduzca tipo de pico: ");
                pico = sc.nextLine();
                System.out.println("Introduzca color: ");
                color = sc.nextLine();
                Canario ca = new Canario(nombre, edad, "nuevo", fechaNac, pico, true, color, true);
                animales.add(ca);
                break;
            default:
                System.out.println("Tipo incorrecto! (Perro, Gato, Loro o Canario)");
                break;
        }
    }

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Mascota> animales = new ArrayList();
        int opcion;
                
        Perro p1 = new Perro("Rocky", 9, "jugando", "12/02/2015", "Pastor alemán", false);
        Perro p2 = new Perro("Taison", 11, "durmiendo", "04/04/2013", "Pitbull", false);
        Gato g1 = new Gato("Neko", 6, "durmiendo", "15/09/2018", "negro", false);
        Gato g2 = new Gato("Lince", 6, "comiendo", "21/07/2018", "blanco", true);
        Loro l1 = new Loro("Salomón", 14, "hablando", "06/01/2010", "largo", true, "Valencia", true);
        Canario c1 = new Canario("Chiuli", 2, "vivo", "14/12/2022", "corto", true, "amarillo", true);
        
        animales.add(p1);
        animales.add(p2);
        animales.add(g1);
        animales.add(g2);
        animales.add(l1);
        animales.add(c1);

        do{
            System.out.println("TIENDA DE ANIMALES - MENÚ DE OPCIONES");
            System.out.println("-------------------------------------");
            System.out.println("1. Mostrar lista de animales");
            System.out.println("2. Mostrar datos de un animal");
            System.out.println("3. Mostrar datos de todos los animales");
            System.out.println("4. Insertar animal");
            System.out.println("5. Eliminar animal");
            System.out.println("6. Vaciar inventario");
            System.out.println("7. Salir");
            System.out.print("Introduzca una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    mostrarListaAnimales(animales);
                    break;
                case 2:
                    mostrarUnAnimal(animales, sc);
                    break;
                case 3:
                    mostrarTodosAnimales(animales);
                    break;
                case 4:
                    insertarAnimal(animales, sc);
                    break;
                case 5:
                    eliminarAnimal(animales, sc);
                    break;
                case 6:
                    System.out.println("Esto eliminará TODOS los animales, ¿está seguro? (S/N)");
                    String eleccion = sc.nextLine();
                    if(eleccion.charAt(0) == 'S')  
                        animales.clear();
                    break;
                case 7:
                    System.out.println("Saliendo...\n\n\n");
                    break;
                default:
                    System.out.println("Por favor, introduzca una opción válida...");
                    break;
            }

        }while(opcion!=7);      
    }
}
