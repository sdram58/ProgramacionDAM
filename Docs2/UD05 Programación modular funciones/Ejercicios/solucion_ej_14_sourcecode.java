import java.util.Scanner;

public class Ejercicio14 {

    public static void imprimeTriangulo(char a, int linea) {

        // Para cada línea
        for (int i = 1; i <= linea; i++) {

            // Imprimimos espacios
            for (int j = i - 1; j < linea - 1; j++) {
                System.out.print(" ");
            }

            // Imprimimos el char pasado como argumento
            for (int j = 0; j < i * 2 - 1; j++) {
                System.out.print(a);
            }

            // Nueva línea
            System.out.println("");
        }
    }

    public static void main(String args[]) {

        int lineas;
        char car;

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el caracter:");
        car = sc.next().charAt(0);
        System.out.print("Introduce el número de lineas:");
        lineas = sc.nextInt();
        
        imprimeTriangulo(car, lineas);
        
        System.out.println();

    }
}