import java.util.LinkedList;
import java.util.Queue;

public class EjercicioU95 {

	public static void expulsarCliente(String nombre, Queue<String> cola) {
		cola.remove(nombre);
		//Esto se puede hacer porque la cola se implementa mediante una LinkedList, por lo que podemos usar "remove"
		//Si no estuviera disponible esto, se tendría que hacer manualmente
		//Creando una cola auxiliar, procesando elementos pasándolos a la cola nueva hasta llegar al expulsado
		//Procesar expulsado sin pasarlo a la cola nueva, procesar el resto hacia la cola nueva y fin
	}
	
	public static void main(String[] args) {
		Queue<String> colaclientes = new LinkedList<>();
		
		System.out.println("Llega el cliente Pepe");
		colaclientes.add("Pepe");
		
		System.out.println("Llega el cliente Adolfo");
		colaclientes.add("Adolfo");
		
		System.out.println("Llega el cliente Ana");
		colaclientes.add("Ana");
		
		System.out.println("\nCola: " + colaclientes);
		
		System.out.println("\nExpulsamos a Adolfo...");
		expulsarCliente("Adolfo", colaclientes);
		
		System.out.println("\nCola ahora: " + colaclientes);

	}

}
