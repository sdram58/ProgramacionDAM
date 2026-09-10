import java.io.File;
import java.util.Stack;
import java.util.Scanner;

public class EjercicioU92 {

	public static void main(String[] args) {
		Stack<String> pila = new Stack<>();
		
		try {
			File f = new File("./documentos/archivo.txt");
			
			Scanner leerArchivo = new Scanner(f);
			
			while(leerArchivo.hasNext()) {
				pila.push(leerArchivo.nextLine());
			}
			
			while(!pila.isEmpty()) {
				System.out.println(pila.pop());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}