import java.util.Scanner;
// Este código no comprueba letras acentuadas, piensa cómo resolverlo...
public class Condicionales4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
		System.out.print("Introduzca una letra: ");
        char letra = scanner.nextLine().charAt(0);

        int valorAscii = (int)letra;

            if (valorAscii >= 65 && valorAscii <= 90) {
                System.out.println("Es una mayúscula.");
            } else if(valorAscii >= 97 && valorAscii <= 122){
                System.out.println("Es una minúscula.");
            } else{
				System.out.println("No has introducido una letra...");
			}
    }
}


//-----------------------------------------------------


import java.util.Scanner;

public class Condicionales5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		double base, resultado; int exponente;
		
        System.out.print("Introduce la base: ");        
		base = sc.nextDouble();
        System.out.print("Introduce el exponente: ");
		exponente = sc.nextInt();

        if (exponente == 0) {
            resultado = 1;
        } else {
			resultado = Math.pow(base, exponente);
		}

        System.out.println("Resultado: " + resultado);
    }
}

// ------------------------------------------------------

import java.util.Scanner;

public class Condicionales6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca la nota: ");
        double nota = sc.nextDouble();
        System.out.print("Introduzca la edad: ");
        int edad = sc.nextInt();
        System.out.print("Introduzca el sexo (M/F): ");
        char sexo = sc.next().charAt(0);

        if (nota >= 5 && edad >= 18 && sexo == 'M') {
            System.out.println("POSIBLE");
        } else if (nota >= 5 && edad >= 18 && sexo == 'F') {
            System.out.println("ACEPTADA");
        } else {
            System.out.println("NO ACEPTADA");
        }
    }
}

//-------------------------------------------------------------
// Pedimos los datos y hacemos los cálculos siguientes
// Condicionales 7
double distancia = Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));

if (distancia == 0 && r1 == r2) {
	System.out.println("Concéntricas.");
} else if (distancia > (r1+r2)) {
	System.out.println("Exteriores.");
} else if (distancia == (r1+r2)) {
	System.out.println("Tangentes exteriores.");
} else if (distancia > Math.abs(r1-r2) && distancia < (r1+r2)) {
	System.out.println("Secantes.");
} else if (distancia == Math.abs(r1-r2)) {
	System.out.println("Tangentes interiores.");
} else if (distancia < Math.abs(r1-r2) && r1 != r2) {
	System.out.println("Interiores.");
} else {
	System.out.println("Concéntricas.");
}