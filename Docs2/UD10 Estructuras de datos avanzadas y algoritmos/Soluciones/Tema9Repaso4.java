import java.util.Stack;
import java.util.Scanner;

public class Tema9Repaso {
   
   public static void main(String[] args) {

        Stack<Character> pila = new Stack<>();
        Scanner sc = new SCanner(System.in);

        System.out.println("Escriba la frase a invertir:");
        String frase = sc.nextLine();

        for(letra in frase){
            pila.push(letra);
        }

        System.out.print("Frase invertida: ");
        while(!pila.isEmpty()){
            System.out.print(pila.pop());
        }
    }
}

