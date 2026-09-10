import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        
        Scanner leer = new Scanner(System.in);
        int num1, num2, resultado, valor, opcion;
        float n1, n2, sol;

        do{

            System.out.println("CALCULADORA");
            System.out.println("-----------");
            System.out.println("Menú de opciones:");
            System.out.println("1. Suma");
            System.out.println("2. Resta");
            System.out.println("3. Producto"); //Multiplicación
            System.out.println("4. División");
            System.out.println("5. Raíz cuadrada");
            System.out.println("6. Factorial");
            System.out.println("7. Salir");
            System.out.println("\nIntroduzca una opción: ");

            opcion = leer.nextInt();

            switch(opcion){
                case 1: System.out.println("Introduce el 1er número: ");
                        num1 = leer.nextInt();
                        System.out.println("Introduce el 2º número: ");
                        num2 = leer.nextInt();

                        System.out.println("La suma es: " + (num1+num2));
                        break;

                case 2: System.out.println("Introduce el 1er número: ");
                        num1 = leer.nextInt();
                        System.out.println("Introduce el 2º número: ");
                        num2 = leer.nextInt();

                        resultado = num1 - num2;  //Otra forma diferente de hacerlo respecto a la suma
                        System.out.println("La resta vale: " + resultado);
                        break;

                case 3: System.out.println("Introduce el 1er número: ");
                        num1 = leer.nextInt();
                        System.out.println("Introduce el 2º número: ");
                        num2 = leer.nextInt();

                        resultado = num1 * num2; 
                        System.out.println("El producto de " + num1 + " y " + num2 + "es: " + resultado);
                        break;

                case 4: System.out.println("Introduce el 1er número: ");
                        n1 = leer.nextFloat();
                        System.out.println("Introduce el 2º número: ");
                        n2 = leer.nextFloat();

                        sol = n1 / n2; 
                        System.out.println("Resultado de la división: " + sol);
                        break;

                case 5: System.out.println("Introduce el valor a calcular:");
                        valor = leer.nextInt();

                        if(valor>=0)
                            System.out.println("Raíz: " + Math.sqrt(valor));
                        else
                            System.out.println("No se puede calcular la raiz cuadrada de números negativos!");

                        break;

                case 6: System.out.println("Introduce el valor a calcular su factorial:");
                        valor = leer.nextInt();

                        if (valor < 0)
                            System.out.print("No se puede calcular.");
                        
                        else if (valor == 0)
                            System.out.print("El factorial es: 1");
                        
                        else{
                            long factorial = 1;
                            while (valor > 1) {
                                factorial = factorial * valor;
                                valor--;
                            }
                            System.out.print("El factorial es: " + factorial);
                        }

                        break;
                
                case 7: System.out.println("Saliendo... hasta pronto!");
                        break;            
                
                default: System.out.println("Opción no válida!\n");
                        break;
            }

        }while(opcion <1 || opcion >7);
    }
}