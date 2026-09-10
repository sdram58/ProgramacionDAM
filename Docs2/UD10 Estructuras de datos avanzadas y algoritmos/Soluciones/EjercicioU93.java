import java.util.Stack;

public class EjercicioU93 {

	public static void insertarOrdenado(int valor, Stack<Integer> pila) {
			Stack<Integer> pilaux = new Stack<>();
				
			while(!pila.isEmpty()) {
				if(valor > pila.peek())
					pilaux.push(pila.pop());
				else {
					pilaux.push(valor);
					break;
				}
			}
			
			if(pila.isEmpty())
				pila.push(valor);
								
			while(!pilaux.isEmpty())
				pila.push(pilaux.pop());
	}
	
	public static void main(String[] args) {
		Stack<Integer> pilamain = new Stack<>();
		
		pilamain.push(5);
		pilamain.push(4);
		
		insertarOrdenado(1,pilamain);
		
		while(!pilamain.isEmpty())
			System.out.println(pilamain.pop());
	}

}