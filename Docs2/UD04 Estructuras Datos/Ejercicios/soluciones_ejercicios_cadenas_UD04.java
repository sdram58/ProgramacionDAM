//Ejercicio 1: Realiza un programa que pida una frase y la muestre invertida.
public static void main(String[] args) {
	  
	  String frase;
	  char[] vector;
	  Scanner leer = new Scanner(System.in);
	  
	  System.out.println("Introduce una frase:");
	  frase = leer.nextLine();
	  vector = frase.toCharArray();
	  
	  System.out.println("La frase invertida es: ");
	  for(int i=vector.length-1;i>=0;i--)
		  System.out.print(vector[i]);   
    }
	
	
//Ejercicio 2: Realiza un programa que pida una frase y cuente la cantidad de vocales que están acentuadas.
//Cuidado con la lectura de los acentos en entrada estándar... probablemente toque cambiar la página de códigos con chcp
public static void main(String[] args) {
	  
	  String frase;
	  char[] vector;
	  int[] cantVocAcent = {0,0,0,0,0}; //se puede también con int a,e,i,o,u = 0; en vez de usar un vector
	  Scanner leer = new Scanner(System.in);
	  
	  System.out.println("Introduce una frase:");
	  frase = leer.nextLine();
	  vector = frase.toCharArray();
	  
	  for(int i=0;i<vector.length;i++)
		  switch(vector[i]){
			  case 'á':
				  cantVocAcent[0]++;
				  break;
			  case 'é':
				  cantVocAcent[1]++;
				  break;
			  case 'í':
				  cantVocAcent[2]++;
				  break;
			  case 'ó':
				  cantVocAcent[3]++;
				  break;
			  case 'ú':
				  cantVocAcent[4]++;
				  break;
		  }
	  System.out.println("En la frase hay un total de: ");
	  System.out.println("á: " + cantVocAcent[0]);
	  System.out.println("é: " + cantVocAcent[1]);
	  System.out.println("í: " + cantVocAcent[2]);
	  System.out.println("ó: " + cantVocAcent[3]);
	  System.out.println("ú: " + cantVocAcent[4]);
	  
    }
	
//Ejercicio 3: Realiza un programa que pida una dirección web, del estilo de: http://www.google.com, la analice y muestre esto en pantalla....
	public static void main(String[] args) {
		String direccion;
		int posP, posN, posH;
		Scanner leer = new Scanner(System.in);
		System.out.println("Introduzca una URL:");
		direccion = leer.nextLine();
		
		posP = direccion.indexOf('/');
		System.out.println("Protocolo: " + direccion.substring(0,posP-1));
		
		posH = direccion.indexOf('.');
		System.out.println("Nombre de dominio: " + direccion.substring(posH+1,direccion.length()));
		
		System.out.println("Host: " + direccion.substring(posP+2,posH));	
			  
    }
	
//Ejercicio 4: Realiza un programa que permita, a partir de una frase introducida por teclado, sustituir en ella una letra cualquiera por otra (ambas tendrán que ser pedidas por teclado).
	public static void main(String[] args) {
		String frase; 
		char lOld, lNew;
		char[] vector; 
		boolean sust = false; //Analiza para qué usamos esta variable...
		Scanner leer = new Scanner(System.in);
		
		System.out.println("Introduce una frase:");
		frase = leer.nextLine();
		vector = frase.toCharArray();
		
		System.out.println("Indica la letra a sustituir: ");
		lOld = leer.next().charAt(0);
		
		System.out.println("Indica la letra para la sustitución: ");
		lNew = leer.next().charAt(0);
		
		for(int i=0;i<frase.length();i++){
			if(vector[i] == lOld) {
				vector[i] = lNew;
				sust = true;
			}
		}
		
		if(!sust)
			System.out.println("La letra " + lOld + " no ha sido encontrada en la frase.");
		else {
			System.out.println("La nueva frase es: ");
			System.out.println(vector);
		}
		// Prueba a usar la función de String.replace() para resolver este problema, ¿qué forma prefieres?
    }

//Ejercicio 5: saber si una frase es un palíndromo o no.
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String frase;
        boolean palindromo = true;

        // Suponemos que la frase no tiene acentos ni signos de puntuación.
        System.out.print("Introduce una frase: ");

        frase = sc.nextLine();
        frase = frase.replace(" ", "");
        frase = frase.toUpperCase();
        int longitud = frase.length();

        for (int i = 0; i < longitud && palindromo; i++) {
            // comparamos el carácter de la posición i con el de la última posición - i
            if (frase.charAt(i) != frase.charAt((longitud - 1) - i)) {
                palindromo = false;
            }
        }

        if (palindromo) {
            System.out.println("La frase es palindromo");
        } else {
            System.out.println("La frase no es palindromo");
        }
    }
	
//Ejercicio 6: Realiza un programa que, a partir de una frase que hemos introducido por teclado, cuente cuantas palabras tiene.

	public static void main(String[] args) {
		String frase;
		int contador = 1, pos;
		Scanner leer = new Scanner(System.in);
		
		System.out.println("Introduce una frase:");
		frase = leer.nextLine();
				
	    frase = frase.trim(); //eliminar los posibles espacios en blanco al principio y al final                              
	    if (frase.isEmpty()) { //si la cadena está vacía
	        contador = 0;
	    } else {
	            pos = frase.indexOf(" "); //se busca el primer espacio en blanco
	            while (pos != -1) {   //mientras que se encuentre un espacio en blanco, ¿porqué usamos -1?
	                   contador++;    //se cuenta una palabra
	                   pos = frase.indexOf(" ", pos + 1); //se busca el siguiente espacio en blanco                       
	            }                                     //a continuación del actual
	    }
	    
	    System.out.println("La frase tiene " + contador + " palabras.");		
    }
	
	//¿Qué limitaciones tiene este programa?. Podemos mejorarlo: usa "split". Para más avanzados usa StringTokenizer

//Ejercicio 7: gestión de notas

import java.util.Scanner;

public class Ejercicio7 {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
 
        //Tamaño del array
        final int TAM = 10;
 
        //Creamos los arrays
        String[] nombres = new String[TAM];
        double[] notas = new double[TAM];
 
        //Rellenamos ambos arrays
        for(int i=0;i<notas.length;i++){
            System.out.println("Introduce el nombre del alumno "+(i+1));
            nombres[i] = sc.nextLine();

            double nota;
            //Validamos la nota
            do{
                System.out.println("Introduce la nota del alumno "+(i+1));
                nota = Double.parseDouble(sc.nextLine());
            } while(nota<=0 || nota>=10);
            
            notas[i] = nota;
        }
 
        //Rellena el vector de calificaciones con palabras a partir de cada nota
        String[] resultado = new String[notas.length];
        for(int i=0;i<notas.length;i++){
            switch((int)notas[i]){
                case 1:
                case 2:
                case 3:
                case 4:
                    resultado[i] = "Suspenso";
                    break;
                case 5:
                case 6:
                    resultado[i] = "Aprobado";
                    break;
                case 7:
                case 8:
                    resultado[i] = "Notable";
                    break;
                case 9:
                case 10:
                    resultado[i] = "Sobresaliente";
                    break;
            }
        }
 
        //Mostramos el contenido de tpdos los vectores
        for(int i=0;i<nombres.length;i++){
            System.out.println("El alumno " + nombres[i] + " tiene un " + notas[i] + " -> Calificación: " + resultado[i]);
        }
    }
}



//Ejercicio 8: compresión RLE

	public static void main(String[] args) {

		String compresion = "";
		String x;
        int contador = 1;
        char caracter = 0;
        Scanner leer = new Scanner(System.in);
        
        System.out.println("Introduce la cadena a comprimir: ");
        x = leer.nextLine();
 
        for (int i = 0; i < x.length()-1; i++) {
            caracter = x.charAt(i); 
 
            if (caracter == x.charAt(i+1)){
            	contador ++;
            }else{
            	compresion += caracter + "" + contador;
            	caracter =  x.charAt(i+1);
            	contador = 1;
            }
        }
        compresion += caracter + "" + contador;
          
    	System.out.println("Cadena comprimida en RLE: ");
		System.out.println(compresion);
		System.out.println("Tamaño cadena original: " + x.length() + " bytes.");
		System.out.println("Tamaño cadena comprimida: " + compresion.length() + " bytes.");
		leer.close();
	}

//Ejercicio 9: cifrado César

import java.io.IOException;
import java.util.Scanner;

public class Ejercicio9 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String texto;
        int codigo;
        char opcion;
        
        do {
            System.out.print("Introduce un texto: ");
            texto = sc.nextLine();
        } while (texto.isEmpty());
        
        //Introducir el valor del desplazamiento (código de cifrado)
        do {
            System.out.print("Introduce el código: ");
            codigo = sc.nextInt();
        } while (codigo < 1);
        
        //Introducir la operación a realizar: cifrar o descifrar
        do {
            sc.nextLine();
            System.out.print("(C) cifrar o (D) descifrar?: ");
            opcion = sc.nextLine().charAt(0);
        } while (Character.toUpperCase(opcion) != 'C' && Character.toUpperCase(opcion) != 'D');                   
        
        if (Character.toUpperCase(opcion) == 'C') { //Función de cifrar
            String cifrado = new String();
            codigo = codigo % 26; //Por si el usuario introduce un valor superior a 26
            for (int i = 0; i < texto.length(); i++) {
                //Aquí se hace el cifrado. Este bloque condicional se puede optimizar, piensa cómo.
                if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                    if ((texto.charAt(i) + codigo) > 'z') {
                        cifrado += ((char) (texto.charAt(i) + codigo - 26));
                    } else {
                        cifrado += ((char) (texto.charAt(i) + codigo));
                    }
                } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                    if ((texto.charAt(i) + codigo) > 'Z') {
                        cifrado += ((char) (texto.charAt(i) + codigo - 26));
                    } else {
                        cifrado += ((char) (texto.charAt(i) + codigo));
                    }
                }
            }

            System.out.println("Texto cifrado: " + cifrado);

        } else { //Función para descifrar

            String descifrado = new String();
            codigo = codigo % 26;
            for (int i = 0; i < texto.length(); i++) {
                //Aquí se hace el descifrado. Este bloque condicional se puede optimizar, piensa cómo.
                if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                    if ((texto.charAt(i) - codigo) < 'a') {
                        descifrado += ((char) (texto.charAt(i) - codigo + 26));
                    } else {
                        descifrado += ((char) (texto.charAt(i) - codigo));
                    }
                } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                    if ((texto.charAt(i) - codigo) < 'A') {
                        descifrado += ((char) (texto.charAt(i) - codigo + 26));
                    } else {
                        descifrado += ((char) (texto.charAt(i) - codigo));
                    }
                }
            }
            System.out.println("Texto descifrado: " + descifrado);
        }
    }
}

//Ejercicio 10: sin expresiones regulares
import java.util.Scanner;

public class ComprobadorPasswords{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String password;
		char aux;
		boolean mayusc, minusc, puntuacion, digito;
		mayusc = minusc = puntuacion = digito = false;

		System.out.println("Introduzca la contraseña: ");
		password = sc.nextLine();

		if(password.length()<8){ //si tamaño menor no hace falta seguir
			System.out.println("La contraseña es DÉBIL.");
		} else {
			//sin expresión regular hay que iterar por toda la cadena
			for(int i=0;i<password.length();i++){
				aux = password.charAt(i);

				if(aux >= 'A' && aux <= 'Z')
					mayusc = true;
				else if(aux >= 'a' && aux <= 'z')
					minusc = true;
				else if(aux == '@' || aux == '-' || aux == '_' || aux == '#' || aux == '$' || aux == '%') //podríamos poner más...
					puntuacion = true;
				else if(aux >= '0' && aux <= '9')
					digito = true;
			}

			if(mayusc && minusc && puntuacion && digito)
				System.out.println("La contraseña es FUERTE.");
			else
				System.out.println("La contraseña es DÉBIL.");
		}
	}
}


//Ejercicio 10: con expresiones regulares. Este ejercicio no es sencillo y sólo está a modo de reto

import java.util.Scanner;
import java.util.regex.*;

public class ComprobadorRegex{
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String password;
		boolean passOk = false;

		System.out.println("Introduzca la contraseña: ");
		password = sc.nextLine();

		if(password.length()<8){ //si tamaño menor no hace falta seguir
			System.out.println("La contraseña es DÉBIL.");
		} else {
			
			Pattern patron = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&])(?=\\S+$).{8,}$");
	        Matcher comprobador = patron.matcher(password);
			
			passOk = comprobador.matches();

			if(passOk)
				System.out.println("La contraseña es FUERTE.");
			else
				System.out.println("La contraseña es DÉBIL.");
		}
	}
}

// Ejercicio 11: programa para validar DNI, sin usar expresiones regulares

import java.util.Scanner;

public class PruebaDNI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        String dni;

        System.out.print("Introduce el DNI completo (00000000X): ");
        dni = sc.nextLine();

        if (dni.length() == 9){ //Sólo comprobamos longitud. Podríamos comprobar más cosas...
            int dniNumero = Integer.parseInt(dni.substring(0, 8));
            char letra = dni.charAt(8);

            int resto = dniNumero % 23;
            char letraCalculada = letras[resto];

            if (letraCalculada == letra) {
                System.out.println("DNI válido");
            } else {
                System.out.println("DNI inválido");
            }
         } else {
            System.out.println("Formato de DNI incorrecto");
        }
    }
}

