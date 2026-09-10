//Ejercicio 1

public static void main(String[] args) {

        int x = 1;
        int m[][] = new int[5][5];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = x++;
                System.out.printf("%4d", m[i][j]);
            }
            System.out.println("");

        }

    }
	
//Ejercicio 2
public static void main(String[] args) {

        int x = 1;
        int m[][] = new int[10][10];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = x * (j + 1);
                System.out.printf("%4d", m[i][j]);
            }
            x++;
            System.out.println("");

        }

    }
	
//Ejercicio 3
public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n, m, menor = 0, mayores = 0, menores = 0, cero = 0;

        System.out.println("Introduce numero de filas");
        n = sc.nextInt();

        System.out.println("Introduce numero de columnas");
        m = sc.nextInt();

        int matriz[][] = new int[n][m];

        for (int i = 0; i < matriz.length; i++) {
            for (int x = 0; x < matriz[0].length; x++) {

                System.out.println("Introduce valor de fila : " + (i + 1) + " Columna: " + (x + 1));
                matriz[i][x] = sc.nextInt();

                if (matriz[i][x] < 0) {
                    menores++;
                } else if (matriz[i][x] > 0) {
                    mayores++;
                } else {
                    cero++;
                }
            }
        }
    }
//Ejercicio 4
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        
        float notas[][]= new float[4][5];
        float minima,maxima,media=0,suma;
        
        for (int i = 0; i < notas.length; i++) {
			minima = 10; //Esto funciona porque sabemos los límites
			maxima = 0;  //Si no, deberíamos pensar en inicializarlos al primer valor de notas
			suma = 0;
            
			for (int j = 0; j < notas[0].length; j++) {
                System.out.print("Para el alumno "+(i+1)+" dime su nota "+(j+1)+": ");
                notas[i][j] = sc.nextFloat();
                suma += notas[i][j];

                if(notas[i][j] < minima){
                    minima = notas[i][j];
                }
                if(notas[i][j] > maxima){
                    maxima = notas[i][j];
                }
            }
            System.out.println("La nota mínima del alumno "+(i+1)+" es: " + minima);
            System.out.println("La nota máxima del alumno "+(i+1)+" es: " + maxima);
            System.out.println("La nota media del alumno "+(i+1)+" es: "+ (suma/5));
            System.out.println("");
        }    
   }
	
//Ejercicio 5
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Dime el número de personas ");
        int n = sc.nextInt();
        int nvarones = 0, nmujeres = 0, sumavarones = 0, sumamujeres = 0;

        float sueldos[][] = new float[n][2];

        for (int i = 0; i < sueldos.length; i++) {
            for (int j = 0; j < sueldos[0].length; j++) {
                if (j == 0) {
                    System.out.print("Dime el género (0 para varón y 1 para mujer) de la persona " + (i + 1) + ": ");
                    sueldos[i][j] = sc.nextFloat();
                }
                if (j == 1) {
                    System.out.print("Dime el sueldo: ");
                    sueldos[i][j] = sc.nextFloat();
                }
            }
        }

        for (int i = 0; i < sueldos.length; i++) {
            for (int j = 0; j < sueldos[0].length; j++) {
                System.out.print(sueldos[i][j] + " ");
            }
            System.out.println("");
        }

        for (int i = 0; i < sueldos.length; i++) {
            if (sueldos[i][0] == 0.0) {
                nvarones++;
                sumavarones += sueldos[i][1];
            }
            if (sueldos[i][0] == 1.0) {
                nmujeres++;
                sumamujeres += sueldos[i][1];
            }
        }
        System.out.println("El sueldo medio de los varones es: " + (sumavarones / nvarones));
    }
        System.out.println("El sueldo medio de las mujeres es: " + (sumamujeres / nmujeres));
		
		
//Ejercicio 6
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		
		int[][] alumnos = {
            {10, 15, 20, 25},
            {30, 35, 40, 45},
            {50, 55, 60, 65}
        };
     	String[] idiomas = {"Inglés", "Francés", "Alemán", "Ruso"};
        String[] niveles = {"Básico", "Medio", "Perfeccionamiento"};
        int opcion = 0, cantidad;
        
        do {
            System.out.println("\nGESTIÓN ACADEMIA IDIOMAS - Menú de opciones");
            System.out.println("-------------------------------------------");
            System.out.println("1. Actualizar cantidad de alumnos");
            System.out.println("2. Cantidad total de alumnos por idioma");
            System.out.println("3. Cantidad total de alumnos por nivel");
            System.out.println("4. Cantidad total de alumnos en la academia");
            System.out.println("5. Salir");
            System.out.print("Introduzca una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Introduzca el nivel (0-2): ");
                    int nivel = scanner.nextInt();
                    System.out.print("Introduzca el idioma (0-3): ");
                    int idioma = scanner.nextInt();
                    System.out.print("Introduzca nueva cantidad de alumnos: ");
                    cantidad = scanner.nextInt();
                    alumnos[nivel][idioma] = cantidad;
                    break;
                case 2:
                    for (int i = 0; i < alumnos[0].length; i++) {
                        cantidad = 0;
                        for (int j = 0; j < alumnos.length; j++) {
                            cantidad += alumnos[j][i];
                        }
                        System.out.println("Idioma: " + idiomas[i] + " - Alumnado: " + cantidad);
                    }
                    break;
                case 3:
                    for (int i = 0; i < alumnos.length; i++) {
                        cantidad = 0;
                        for (int j = 0; j < alumnos[0].length; j++) {
                            cantidad += alumnos[i][j];
                        }
                        System.out.println("Nivel: " + niveles[i] + " - Alumnado: " + cantidad);
                    }
                    break;
                case 4:
                    int total = 0;
                    for (int i = 0; i < alumnos.length; i++) {
                        for (int j = 0; j < alumnos[i].length; j++) {
                            total += alumnos[i][j];
                        }
                    }
                    System.out.println("Cantidad total de alumnos en la academia: " + total);
                    break;
                case 5:
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Introduzca de nuevo.");
                    break;
            }
        } while (opcion != 5);
    }
}


//Ejercicio 7
public class Ejer7 {
    public static void main(String[] args) {
        String origen = "ATGCGTAT";
        String destino = "ATATGCGT";
        char[] destinochar;
        char aux;
        int i, contador = 8;
        boolean comprobar = true;

        System.out.println("Cadena original:" + origen);
        System.out.println("Cadena a comparar:" + destino);

        while(comprobar){

            if(contador==0){
                System.out.println("Son diferentes!");
                break;
            }

            if(origen.equals(destino)){
                System.out.println("Son iguales!");
                break;
            }

            destinochar = destino.toCharArray();

            aux = destinochar[7];
            for(i=destinochar.length-1;i>=1;i--){
                destinochar[i]=destinochar[i-1];
            }
            destinochar[0] = aux;
            destino = String.valueOf(destinochar);
            System.out.println("Nueva cadena: " + destino);
            contador--;
        }
    }
}


//Ejercicio 8(ejercicio de vectores)

import java.util.Arrays;

public class Ejer8 {
    public static void main(String[] args) {
        float[] vector = {1, 3, 9, 2, 6, 4, 3, 9};
        float[] vpares;
        float[] vimpares;
        int contpares = 0, contimpares = 0, pospares = 0, posimpares = 0;

        for(int i=0;i<vector.length;i++){ //Contar cuantos hay de cada tipo
            if(vector[i] % 2 == 0)
                contpares++;
            else
                contimpares++;
        }

        vpares = new float[contpares]; //Crear vector auxiliar para pares
        vimpares = new float[contimpares]; //Lo mismo para impares

        for(int i=0;i<vector.length;i++)
            if(vector[i] % 2 == 0){
                vpares[pospares] = vector[i]; //Guardar pares
                pospares++;
            }else{
                vimpares[posimpares] = vector[i]; //Guardar impares
                posimpares++;
            }
        
            Arrays.sort(vpares); //Ordenar pares
            Arrays.sort(vimpares); //No se puede ordenar descendentemente con .sort

            float[] resultado = new float[vpares.length + vimpares.length]; //Genero vector resultado
          
            for(int i=0;i<vpares.length;i++) //Copio primero los pares
                resultado[i] = vpares[i];

            for(int i=0;i<vimpares.length;i++) //Copio los impares pero al revés tras los pares
                resultado[i+vpares.length] = vimpares[vimpares.length-1-i];

            for(int i=0;i<resultado.length;i++) //Mostrar resultado
                System.out.print(resultado[i] + " ");
    }
}

//Ejercicio 9 (realmente es un ejercicio de vectores)

    public static void main(String[] args) {
        int[] vector = {5,6,7,8,9,10,11,12,13,14}; //Vector de ejemplo, cambialo o pídelo por teclado
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduzca un valor a insertar: ");
        int valor = sc.nextInt();

        System.out.println("Introduzca la posición donde quiere insertar (0 a n-1): ");
        int pos = sc.nextInt();
        
        if(pos<0 || pos>= vector.length) //Si me piden una posición inexistente me quejo y fin
            System.out.println("La posición indicada no existe. Saliendo...");
        else {
            for(int i = vector.length-1; i > pos; i--){
                vector[i] = vector[i-1]; //Desplazo los elementos
            }
            vector[pos] = valor; //Almaceno el nuevo valor en la posición indicada
        }
        
	// Comprobar el vector
        for(int i=0;i<vector.length;i++)
            System.out.print(vector[i] + " ");

    }

//Ejercicio 10

public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero;
        int[] vector = new int[100];
		//Se puede resolver con una matriz fácilmente
        
        do{
            System.out.println("Introduce un número del 1 al 100: ");
            numero = sc.nextInt();
            if(numero < 1 || numero > 100){
                System.out.println("El número introducido es incorrecto...fin");
                break;
            } else{
                vector[numero-1]++;
            }
        }while(numero >= 1 || numero <= 100); //Pedir números mientras estén dentro del rango...

        System.out.println("Recuento de valores");

        for(int i=0;i<vector.length;i++){
            if(vector[i]>0){
                System.out.print(i+1 + ": ");
                
                for(int j=0;j<vector[i];j++){
                    System.out.print("*");
                }
                
                System.out.println(" "); //Salto de línea tras cada valor que contenga algo a imprimir
            }
        }
    }    


