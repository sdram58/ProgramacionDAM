import java.util.Scanner;

public class RegistroTemperaturas {
 
	public static void main (String[] args) {
		final int MAX_SEMANAS = 52;
		int numTemperaturas = 0;
		float[] temperaturas = new float[MAX_SEMANAS * 7];
		int[] fecha = {1,1};
		boolean fin = false;
		
		while(!fin) {
			System.out.println("\nBienvenido al registro de temperaturas");
			System.out.println("---------------------------------------");
			System.out.println("[R] Registrar temperaturas semanales.");
			System.out.println("[M] Consultar temperatura media.");
			System.out.println("[D] Consultar diferencia máxima.");
			System.out.println("[S] Salir.");
			System.out.println("Opción: ");	
				
			Scanner leer = new Scanner(System.in);
			String opcion = leer.nextLine();
					
			
			if (opcion.equals("R")) {
				numTemperaturas = registroTemperaturasSemanales(numTemperaturas, temperaturas, fecha);
			} else if (opcion.equals("M")) {
				mostrarMedia(numTemperaturas, fecha, temperaturas);
			} else if (opcion.equals("D")) {
				mostrarDiferencia(numTemperaturas, fecha, temperaturas);
			} else if (opcion.equals("S")) {
				fin = true;
				System.out.println("Gracias por su visita.");
			} else {
				System.out.println("¡Opción incorrecta!\n");
			}
		}
	}
 
	public static int registroTemperaturasSemanales(int nTemp, float[] temperaturas, int[] fecha) {
		if ((nTemp + 7) >= temperaturas.length) {
			System.out.println("No hay espacio para más datos.");
			return nTemp;
		} else {
			leerTemperaturasTeclado(nTemp, temperaturas);
			incrementarFecha(fecha);
			return (nTemp + 7);
		}
		
	}
	public static void mostrarMedia(int nTemps, int[] fecha, float[] temps) {
		if (nTemps > 0) {
			System.out.print("\nHasta hoy, ");
			mostrarFecha(fecha);
			System.out.print(", la media ha sido de ");
			calcularMedia(nTemps, temps);
			System.out.println(" grados.");
		} else {
			System.out.println("\n¡No hay temperaturas registradas!.");
		}
	}
	public static void mostrarDiferencia(int nTemps, int[] fecha, float[] temps) {
		if (nTemps > 0) {
			System.out.print("\nHasta hoy, ");
			mostrarFecha(fecha);
			System.out.print(", la diferencia máxima ha sido de ");
			calcularDiferencia(nTemps, temps);
			System.out.println(" grados.");
		} else {
			System.out.println("\n¡No hay temperaturas registradas!.");
		}
	}

	public static void leerTemperaturasTeclado(int numTemp, float[] temperaturas) {
		int numLeidas = 0; String entradaTemps;
		Scanner lector = new Scanner(System.in);
		System.out.println("Escribe las temperaturas de esta semana:");
				
		entradaTemps = lector.nextLine();
		String temps[] = entradaTemps.split(" ");
				
		while(numLeidas < 7) {
			temperaturas[numTemp+numLeidas] = Float.parseFloat(temps[numLeidas]);
			numLeidas++;					
		}
		
		lector.nextLine();
	}
	
	public static void incrementarFecha(int[] fecha) {
		int diasMes = 0;
	
		if (fecha[1] == 2) {
			diasMes = 28;
		} else if ((fecha[1] == 4)||(fecha[1] == 6)||(fecha[1] == 9 )||(fecha[1] == 11)) {
			diasMes = 30;
		} else {
			diasMes = 31;
		}
		
		fecha[0] = fecha[0] + 7;
		
		if (fecha[0] > diasMes) {
			fecha[0] = fecha[0] - diasMes;
			fecha[1]++;

		if (fecha[1] > 12) {
			fecha[1] = 1;
			}
		}
	}
	
	public static void mostrarFecha(int[] fecha) {
	
		System.out.print(fecha[0] + " de ");
		
		switch(fecha[1]) {
			case 1:
				System.out.print("Enero"); break;
			case 2:
				System.out.print("Febrero"); break;
			case 3:
				System.out.print("Marzo"); break;
			case 4:
				System.out.print("Abril"); break;
			case 5:
				System.out.print("Mayo"); break;
			case 6:
				System.out.print("Junio"); break;
			case 7:
				System.out.print("Julio"); break;
			case 8:
				System.out.print("Agosto"); break;
			case 9:
				System.out.print("Septiembre"); break;
			case 10:
				System.out.print("Octubre"); break;
			case 11:
				System.out.print("Noviembre"); break;
			case 12:
				System.out.print("Diciembre");
			}
		}
		
	public static void calcularMedia(int numTemps, float[] temps) {
		float acumulador = 0;
		for(int i = 0; i < numTemps; i++) {
			acumulador = acumulador + temps[i];
		}
		
		System.out.print((acumulador / numTemps));
	}
	
	public static void calcularDiferencia(int nTemps, float[] temps) {
		float maxima = temps[0];
		float minima = temps[0];
		for(int i = 1; i < nTemps; i++) {
			if (temps[i] < minima) {
				minima = temps[i];
			}
			
			if (temps[i] > maxima) {
				maxima = temps[i];
			}
		}
		System.out.print((maxima - minima));
	}
}