package u7Alumnos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainAlumnado{

    public static void main(String[] args) {
        ArrayList<Alumno> alumnado = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int opcion;
		
        do{
            menu();
            opcion = Integer.parseInt(sc.nextLine());
             
            switch (opcion) {
                case 1:
                    insertarAlumno(sc, alumnado);
                    break;
                case 2:
                    eliminarAlumno(sc, alumnado);
                    break;
                case 3:
                    modificarDatosAlumno(sc, alumnado);
                    break;
                case 4:
                    insertarNota(sc, alumnado);
                    break;
                case 5:
                    mostrarNotasMedias(alumnado);
                    break;
                case 6:
                    eliminarNota(sc, alumnado);
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("¡Opción no válida!");
            }
        } while (opcion != 7);

        sc.close();
    }

    private static void menu() {
        System.out.println("\nMenú:");
        System.out.println("1. Insertar nuevo alumno");
        System.out.println("2. Eliminar alumno");
        System.out.println("3. Modificar datos de alumno");
        System.out.println("4. Insertar nota");
        System.out.println("5. Mostrar notas medias");
        System.out.println("6. Eliminar nota");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void insertarAlumno(Scanner sc, ArrayList<Alumno> alumnado) {
        System.out.print("Introduzca nombre del alumno: ");
        String nombre = sc.nextLine();

        System.out.print("Introduzca edad del alumno: ");
        int edad = Integer.parseInt(sc.nextLine());

        Alumno nuevoAlumno = new Alumno(nombre, edad);
        alumnado.add(nuevoAlumno);

        System.out.println("Alumno creado correctamente.");
    }

    private static void eliminarAlumno(Scanner sc, ArrayList<Alumno> alumnado) {
        System.out.print("Introduzca nombre del alumno a eliminar: ");
        String nombreEliminar = sc.nextLine();

        Iterator<Alumno> iterator = alumnado.iterator();
        boolean encontrado = false;

        while (iterator.hasNext()) {
            Alumno alumno = iterator.next();
            if (alumno.getNombre().equals(nombreEliminar)) {
                iterator.remove();
                encontrado = true;
                System.out.println("Alumno eliminado correctamente.");
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Alumno no encontrado!!!");
        }
    }

    private static void modificarDatosAlumno(Scanner sc, ArrayList<Alumno> alumnado) {
        System.out.print("Introduzca nombre del alumno a modificar: ");
        String nombreModificar = sc.nextLine();

        for (Alumno alumno : alumnado) {
            if (alumno.getNombre().equals(nombreModificar)) {
                System.out.print("Introduzca nuevo nombre del alumno: ");
                String nuevoNombre = sc.nextLine();
                alumno.setNombre(nuevoNombre);

                System.out.print("Introduzca nueva edad del alumno: ");
                int nuevaEdad = Integer.parseInt(sc.nextLine());
                alumno.setEdad(nuevaEdad);

                System.out.println("Alumno modificado correctamente.");
            }
        }

        System.out.println("Alumno no encontrado!!!");
    }

    private static void insertarNota(Scanner sc, ArrayList<Alumno> alumnado) {
        System.out.print("Introduzca nombre del alumno: ");
        String nombreBuscar = sc.nextLine();

        for (Alumno alumno : alumnado) {
            if (alumno.getNombre().equals(nombreBuscar)) {
                System.out.print("Introduzca nota a insertar: ");
                float nuevaNota = Float.parseFloat(sc.nextLine());
				if(nuevaNota >= 0 && nuevaNota <= 10){
					alumno.insertarNota(nuevaNota);
					System.out.println("Nota añadida correctamente.");
				}else{
					System.out.println("Nota errónea!!!");
				}
            }
        }

        System.out.println("Alumno no encontrado!!!");
    }

    private static void mostrarNotasMedias(ArrayList<Alumno> alumnado) {
        System.out.println("\nListado de alumnado con nota media");

        for (Alumno alumno : alumnado) {
            float notaMedia = alumno.calcularNotaMedia();
            System.out.println(alumno.getNombre() + ": " + (notaMedia == -1f ? "No hay notas." : notaMedia));
        }
    }

    private static void eliminarNota(Scanner sc, ArrayList<Alumno> alumnado) {
        System.out.print("Introduzca el nombre del alumno: ");
        String nombreBuscar = sc.nextLine();

        for (Alumno alumno : alumnado) {
            if (alumno.getNombre().equals(nombreBuscar)) {
                System.out.print("Introduzca la nota a eliminar: ");
                float notaEliminar = Float.parseFloat(sc.nextLine());

                if (alumno.eliminarNota(notaEliminar)) {
                    System.out.println("Nota eliminada correctamente.");
                } else {
                    System.out.println("Nota no encontrada. No se ha realizado ninguna eliminación.");
                }

                return;
            }
        }

        System.out.println("Alumno no encontrado. No se ha realizado ninguna eliminación de nota.");
    }
}
