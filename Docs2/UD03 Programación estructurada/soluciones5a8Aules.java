//Soluciones ejercicios
//Ejercicio 5
import java.util.Scanner;

public class Ejercicio5 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int base, exp;

    System.out.println("Introduce la base: ");
    base = sc.nextInt();

    System.out.println("Introduce el exponente: ");
    exp = sc.nextInt();

    if(exp>0)
        System.out.println("Solución:" + Math.pow(base,exp));
    else if (exp == 0)
        System.out.println("Resultado: 0");
    else
        System.out.println("Solución:" + 1/Math.pow(base,-1*exp));
  }
}

//Ejercicio 6
import java.util.Scanner;

public class Ejercicio6 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int nota, edad; String sexo;

    System.out.println("Introduce nota: ");
    nota = sc.nextInt();

    System.out.println("Introduce edad: ");
    edad = sc.nextInt();

    System.out.println("Introduce sexo (M-F): ");
    sexo = sc.nextLine();

    if(nota >=5 && edad>=18 && sexo.equals("M"))
        System.out.println("POSIBLE");
    else if(nota >=5 && edad>=18 && sexo.equals("F"))
        System.out.println("ACEPTADA");
    else
        System.out.println("NO ACEPTADA");

  }
}


//Ejercicio 7
import java.util.Scanner;
public class Ejercicio7 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int x1, x2, y1, y2, r1, r2;
    double distancia;

    System.out.print("Introduce el valor \"x\" de la 1a circunferencia: ");
    x1 = scanner.nextInt();
    System.out.print("Introduce el valor \"y\" de la 1a circunferencia: ");
    y1 = scanner.nextInt();
    System.out.print("Introduce el radio de la 1a circunferencia: ");
    r1 = scanner.nextInt();
    System.out.print("Introduce el valor \"x\" de la 2a circunferencia: ");
    x2 = scanner.nextInt();
    System.out.print("Introduce el valor \"y\" de la 2a circunferencia: ");
    y2 = scanner.nextInt();
    System.out.print("Introduce el radio de la 2a circunferencia: ");
    r2 = scanner.nextInt();

    distancia = Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));

    if (distancia == 0) {
      System.out.println("Concéntricas.");
    } else if (distancia > (r1+r2)) {
      System.out.println("Exteriores.");
    } else if ((distancia > 0) && distancia < Math.abs(r1-r2)) {
      System.out.println("Interiores.");
    } else if (distancia == (r1+r2)) {
      System.out.println("Tangentes exteriores.");
    } else if (distancia == Math.abs(r1-r2)) {
      System.out.println("Tangentes interiores.");
    } else if (distancia < (r1+r2) && distancia > Math.abs(r1-r2)) {
      System.out.println("Secantes.");
    }
   }
  }
  
//Ejercicio 8
import java.util.Scanner;

public class Prueba1 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int a, b, c;

    System.out.println("Introduce coeficiente a: ");
    a = sc.nextInt();

    System.out.println("Introduce coeficiente b: ");
    b = sc.nextInt();

    System.out.println("Introduce coeficiente c: ");
    c = sc.nextInt();

    double discriminante = (Math.pow(b, 2) - (4 * a * c));

    if (discriminante >= 0) {
         // Una solucion
        if(discriminante == 0){
            double sol = ((-b) - (4 * a * c)) / (2 * a);
            System.out.println("La única solución es: " + sol);
        // 2 soluciones
        }else{
            double sol1 = ((-b) + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            double sol2 = ((-b) - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            System.out.println("Solución 1: " + sol1);
            System.out.println("Solución 2: " + sol2);
        }
    }else{
        System.out.println("No tiene solución!!!");
    }
  }
}