//SOLUCIONES EJERCICIOS ADICIONALES UD4

import java.util.Arrays;

public class Ejercicio1 {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 4, 5, 6, 7};
		int[] resultado = new int[nums1.length];
		int producto;


        for (int i = 0; i < nums1.length; i++) {
            producto = 1;
            for (int j = 0; j < nums1.length; j++) {		
                if (i != j) {
                    producto = producto * nums1[j];
                }
            }
            resultado[i] = producto;
        }

        System.out.println("nums1: " + Arrays.toString(resultado));
    }
}

import java.util.Arrays;

public class Ejercicio2 {

    public static void main(String[] args) {
        int[] vector = {1, 2, 5, 0, 4, 3, 6};

        Arrays.sort(vector);

        // Verificar
        boolean esReorganizable = true;
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] == vector[i - 1] || vector[i] != vector[i - 1] + 1) {
                esReorganizable = false;
                break;
            }
        }

        System.out.println("Comprobación solicitada: " + esReorganizable);
    }
}

public class Ejercicio3 {

    public static void main(String[] args) {
        int[] vector = {1, -2, 5, -4, 3, -6};

        boolean alternante = true;
        for (int i = 0; i < vector.length - 1; i++) {
            if ((vector[i] >= 0 && vector[i + 1] >= 0) || (vector[i] < 0 && vector[i + 1] < 0)) {
                alternante = false;
                break;
            }
        }

        System.out.println("Comprobación solicitada: " + alternante);
    }
}

import java.util.Random;

public class Ejercicio4 {

    public static void main(String[] args) {
        int personas = 10;
        int[][] encuesta = new int[10][3]; // Cada fila representa una persona y sus 3 atributos

        Random random = new Random();
        for (int i = 0; i < personas; i++) {
            encuesta[i][0] = random.nextInt(2) + 1; // Sexo (1=masculino, 2=femenino)
            encuesta[i][1] = random.nextInt(2) + 1; // Trabaja (1=si trabaja, 2=no trabaja)
            encuesta[i][2] = encuesta[i][1] == 1 ? random.nextInt(3001) : 0; // Sueldo (0 si no trabaja)
        }

        // Calcular los datos solicitados
        int totalHombres = 0;
        int totalMujeres = 0;
        int hombresTrabajan = 0;
        int mujeresTrabajan = 0;
        int sueldoHTrabajan = 0;
        int sueldoMTrabajan = 0;

        for (int i = 0; i < personas; i++) {
            // Cantidad de hombres (tengan o no trabajo)
            if (encuesta[i][0] == 1) {
                totalHombres++;
            }

            // Cantidad de mujeres (tengan o no trabajo)
            if (encuesta[i][0] == 2) {
                totalMujeres++;
            }

            // Cantidad de hombres que trabajan
            if (encuesta[i][0] == 1 && encuesta[i][1] == 1) {
                hombresTrabajan++;
                sueldoHTrabajan += encuesta[i][2];
            }

            // Cantidad de mujeres que trabajan
            if (encuesta[i][0] == 2 && encuesta[i][1] == 1) {
                mujeresTrabajan++;
                sueldoMTrabajan += encuesta[i][2];
            }
        }

        // Porcentajes y sueldo promedio
        float porcentajeH = (float) totalHombres / personas * 100;
        float porcentajeM = (float) totalMujeres / personas * 100;
        float porcentajeHT = (float) hombresTrabajan / personas * 100;
        float porcentajeMT = (float) mujeresTrabajan / personas * 100;
        float sueldoPromedioHT = hombresTrabajan == 0 ? 0 : (float) sueldoHTrabajan / hombresTrabajan;
        float sueldoPromedioMT = mujeresTrabajan == 0 ? 0 : (float) sueldoMTrabajan / mujeresTrabajan;

        System.out.println("Porcentaje de hombres (tengan o no trabajo): " + porcentajeH + "%");
        System.out.println("Porcentaje de mujeres (tengan o no trabajo): " + porcentajeM + "%");
        System.out.println("Porcentaje de hombres que trabajan: " + porcentajeHT + "%");
        System.out.println("Porcentaje de mujeres que trabajan: " + porcentajeMT + "%");
        System.out.println("Sueldo promedio de los hombres que trabajan: " + sueldoPromedioHT);
        System.out.println("Sueldo promedio de las mujeres que trabajan: " + sueldoPromedioMT);
    }
}


public class Ejercicio5 {

    public static void main(String[] args) {
        int[][] matriz = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int filas = matriz.length;
        int columnas = matriz[0].length;

        int[][] matrizT = new int[columnas][filas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizT[j][i] = matriz[i][j];
            }
        }

        System.out.println("\nMatriz Transpuesta:");
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                System.out.print(matrizT[i][j] + " ");
            }
            System.out.println();
        }
    }
}

import java.util.Scanner;

public class Ejercicio6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce una cadena:");
        String entrada = scanner.nextLine();

        String resultado = entrada.charAt(0);

		for (int i = 1; i < entrada.length(); i++) {
			if (entrada.charAt(i) != entrada.charAt(i - 1)) {
				resultado = resultado + entrada.charAt(i);
				//Recuerda que ahí el "+" se comporta como concatenación, no como suma
			}
		}

        System.out.println("Cadena de entrada: " + entrada);
        System.out.println("Salida del programa: " + resultado);

        scanner.close();
    }
}

import java.util.Scanner;

public class Ejercicio7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce una frase:");
        String frase = scanner.nextLine();

        String[] palabras = frase.split("\\s");

        for (int i = 0; i < palabras.length; i++) {
            if ((i + 1) % 2 != 0) {
                char[] caracteres = palabras[i].toCharArray();
                int longitud = caracteres.length;

                // Vector auxiliar para hacer el intercambio
                char[] charInvertidos = new char[longitud];
                for (int j = 0, k = longitud - 1; j < longitud; j++, k--) {
                    charInvertidos[j] = caracteres[k];
                }
                // Machacamos la posición con la palabra invertida
                palabras[i] = new String(charInvertidos);
            }
        }

        // Unir las palabras de nuevo en una frase
        String resultado = String.join(" ", palabras);

        System.out.println("Resultado: " + resultado);
        scanner.close();
    }
}

import java.util.Scanner;

public class Ejercicio8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce las cadenas separadas por espacios:");
        String input = scanner.nextLine();
        String[] palabras = input.split("\\s+");

        // Buscamos el tamaño de cadena más pequeño
        int minLength = Integer.MAX_VALUE;
        for (int k = 0; k < palabras.length; k++) {
            minLength = Math.min(minLength, palabras[k].length());
        }

        // Buscar prefijo común más largo
        int i = 0;
        while (i < minLength) {
            char c = palabras[0].charAt(i);
            boolean esPrefijoComun = true;

            for (int j = 1; j < palabras.length; j++) {
                if (palabras[j].charAt(i) != c) {
                    esPrefijoComun = false;
                    break;
                }
            }

            if (esPrefijoComun) {
                System.out.print(c);
            } else {
                break;
            }

            i++;
        }

        System.out.println();
    }
}











