import java.util.Scanner;
import java.util.Stack;

public class EjercicioU94 {
  

  public static void main (String[] args) {
      
	  Stack<String> pila = new Stack<>();  // Pila para guardar los parentesis de apertura
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Escriba expresión matemática: ");
	  String exp = sc.nextLine();	  
	  
	  if (exp.length()<=0) {
		  System.out.println("ERROR: Introduzca una expresión correcta");
      } else {
    	  parentesis(exp, pila);
      }
	  
	  sc.close();
  }

  public static void parentesis (String linea, Stack<String> pila) {
      for (int i = 0; i < linea.length(); i++) {
	  if (linea.charAt(i) == '(') pila.push(")");
	  else if (linea.charAt(i) == '{') pila.push("}");
	  else if (linea.charAt(i) == '[') pila.push("]");
	  else if (linea.charAt(i) == ')') verifica(')', pila);
	  else if (linea.charAt(i) == '}') verifica('}', pila);
	  else if (linea.charAt(i) == ']') verifica(']', pila);
      }

      if (pila.isEmpty())
    	  System.out.println("Expresión balanceada");
      else 
    	  System.out.println("Expresión NO balanceada");	  
  }

  public static void verifica (char c, Stack<String> pila) {
      if (pila.isEmpty()) {
    	  System.out.println("Expresión NO balanceada"); 
    	  System.exit(0); 
      } else {
		  Character s = pila.pop().charAt(0);  
		  if (c != s.charValue()) {
		      System.out.println("Expresión NO balanceada"); 
		      System.exit(0); 
		  }
      }
  }
}