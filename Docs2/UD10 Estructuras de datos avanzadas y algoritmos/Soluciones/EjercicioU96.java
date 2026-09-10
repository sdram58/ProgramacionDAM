import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EjercicioU96 {

	public static void main(String[] args) {
		HashMap<String, Integer> historial = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		int opcion;
		String url;
		
		try {
			do {
				System.out.println("Elija una opción (1, 2 ó 3)");
				System.out.println("1. Introducir una URL");
				System.out.println("2. Consultar historial");
				System.out.println("3. Salir");
			
				opcion = Integer.parseInt(sc.nextLine());
			
				switch(opcion) {
					case 1:
						System.out.println("Introduzca una URL:");
						url = sc.nextLine(); //No vamos a validar, pero sería interesante hacerlo con una expresión regular (opcional)					
						
						if(!historial.containsKey(url))
							historial.put(url, 1);
						else {
							historial.replace(url, historial.get(url)+1);						
						}
						
						break;
						
					case 2:
						System.out.println("CONTENIDO DEL HISTORIAL");
						System.out.println("-----------------------");
						for(Map.Entry<String, Integer> entrada: historial.entrySet()) {
							System.out.println("URL: " + entrada.getKey() + " - visitas: " + entrada.getValue());												
						}
						System.out.println("\n");
						
						break;
					
					case 3:
						System.exit(0);
						
					default:
						System.out.println("Introduzca una opción válida...");
				}	
			}while(opcion!=3);
		} catch(NumberFormatException e) {
			System.out.println("ERROR: introduzca un número");			
		}
	}
}
