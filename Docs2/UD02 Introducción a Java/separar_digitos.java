import java.util.Scanner;
public class Ejercicio11 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el número: ");
        int numero = sc.nextInt();
        int n1 = numero / 100000 % 10;
        System.out.println(numero/100000);
        System.out.println(numero/100000%10);
		
        int n2 = numero / 10000 % 10;
        System.out.println(numero/10000);
        System.out.println(numero/10000%10);
		
        int n3 = numero / 1000 % 10;
        System.out.println(numero/1000);
        System.out.println(numero/1000%10);
		
        int n4 = numero / 100 % 10;
        System.out.println(numero/100);
        System.out.println(numero/100%10);
		
        int n5 = numero / 10 % 10;
        System.out.println(numero/10);
        System.out.println(numero/10%10);
		
        int n6 = numero % 10;
        System.out.print("Cifras: ");
        System.out.println(n1 + " " + n2 + " " + n3 + " " + n4 + " " + n5 + " " + n6);
    }
}
