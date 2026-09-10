import java.util.*;

public class Ejercicio1 {

    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);
        
        int A;       
        
        try
        {
            System.out.print("Introduce un número entero: ");
            A = in.nextInt();
            
            System.out.println("Valor introducido: " + A); 

        }
        catch(InputMismatchException e)
        {
            System.out.println("Valor introducido incorrecto");
            e.printStackTrace();            
        }
        
        System.out.println("Fin del programa");
        
    }    

}

public class Ejercicio2 {
    
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        
        int A,B, result;       
        
        try
        {
            System.out.println("Introduce el numerador: ");
            A = in.nextInt();
            
            System.out.println("Introduce el denominador: ");
            B = in.nextInt();
            
            result = A / B;
            
            System.out.println(A + " / " + B + " = " + result);         
            
        }
        catch (InputMismatchException e)
        {
            System.out.println("Valor introducido incorrecto");
            e.printStackTrace();             
        }
        catch (ArithmeticException e) 
        {
            System.out.println("División entre cero: " + e); 
            e.printStackTrace(); 
        }
        
        System.out.println("Fin del programa");
    }
}


public class Ejercicio3 {
    
    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);
        
        double[] vector = new double[5];       
        
        
        for(int i = 0; i < vector.length; i++)
        {
            try
            {            
                System.out.println("Introduce el valor de la posición " + i);
                vector[i] = in.nextDouble();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Valor introducido incorrecto. Vuelve a introducir el valor...");
                e.printStackTrace(); 
                in.nextLine();
                i--;
            }             
        }
        
        mostrarVector(vector);
        
    }
    
    public static void mostrarVector(double[] v)
    {
       System.out.print("Datos del vector [ ");

        for(int j = 0; j < v.length; j++)
        {
            System.out.print(v[j] + ", ");
        }

        System.out.println("\b\b ]"); 
    }
    
}


public class Ejercicio4 {

    public static void main(String[] args) {
        
        Scanner in = new Scanner (System.in);
        
        int N = (int)(Math.random() * 100 + 1);
        int[] vector = new int[N];
        
        for(int i = 0; i < N; i++)
        {
            vector[i] = (int)(Math.random() * 10 + 1);
        }
        
        int valor = 0;
        
        do{
            try
            {
                System.out.println("Introduce la posición del vector para mostrar: (número negativo para salir)");
                valor = in.nextInt();
                System.out.println("Valor en el vector: " + vector[valor]);  
                in.nextLine();                
            }
            catch(InputMismatchException e)
            {
                System.out.println("Valor introducido incorrecto");
                in.nextLine();                
            }
            catch(ArrayIndexOutOfBoundsException e) 
            {
                if(valor >= 0)
                {
                    System.out.println("Posición fuera de los límites del vector");   
                }                        
            }                    
            
        } while(valor >= 0);
        
        System.out.println("Salida del programa");
             
    }    
}


public class Ejercicio5 {

    public static void main(String[] args) throws Exception{
        
        Scanner in = new Scanner(System.in);
        int num;        

        for(int i = 0; i < 5; i++)        
        {
            try
            {
                System.out.print("Introduce un entero positivo: ");
                num = in.nextInt();
                imprimePositivo(num);
                
                System.out.print("Introduce un entero negativo: ");
                num = in.nextInt();
                imprimeNegativo(num);
            }
            catch(InputMismatchException e)
            {
                System.out.println("Valor introducido incorrecto");
                in.nextLine();                
            }
            catch(Exception e)
            {
                System.out.println(e);            
            } 
        }
    }
    
    static void imprimePositivo(int p) throws Exception
    {
        if(p < 0)
        {
            throw new Exception ("Error: número negativo");
        }
        
        System.out.println("El número positivo es el: " + p);        
    }
    
    static void imprimeNegativo(int n) throws Exception
    {
        if(n >= 0)
        {
            throw new Exception("Error: número positivo");
        }
        
        System.out.println("El número negativo es el: " + n);
    }
}


public class Gato { //Clase necesaria para el ejercicio 6
    
    // Atributos
    private String nombre;
    private int edad;
    
    // Constructor
    public Gato(String nombre, int edad) throws Exception
    {
        this.setNombre(nombre);
        this.setEdad(edad);       
    }
    
    // Getters y setters
    public String getNombre() 
    {
        return this.nombre;
    }
    
    public void setNombre(String nombre) throws Exception 
    {
        if (nombre.length() < 3) 
        {
            throw new Exception("ERROR: El nombre debe tener al menos 3 carácteres");
        } 
        
        this.nombre = nombre;        
    }
    
    public int getEdad() 
    {
        return this.edad;
    }
   
    public void setEdad(int edad) throws Exception 
    {
        if (edad < 0) 
        {
            throw new Exception("ERROR: La edad no puede ser negativa");
        } 
        
        this.edad = edad;        
    }
    
    // Método imprimir
    public void imprimir()
    {
        System.out.println("El gato se llama " + this.getNombre() + " y tiene " + this.getEdad() + " años de edad");
    }
    
}

public class Ejercicio6 {
   
    public static void main(String[] args) {
        
        try
        {            
            // Creamos varios objetos de tipo Gato
            Gato g1 = new Gato("Cati", 5);
            Gato g2 = new Gato("Miau",3);
            Gato g3 = new Gato("Milú", 2);
            
            // Modificamos los datos de los objetos
            g1.imprimir();
            g1.setNombre("Gatito");
            g1.setEdad(10);
            g1.imprimir();
            
            g2.imprimir();
            //g2.setNombre("Do");
            //g2.imprimir();
            
            g3.imprimir();
            //g3.setEdad(-5);
            //g3.imprimir();
            
        }
        catch(Exception e)
        {
            System.out.println(e);            
        }
        
        System.out.println("Fin del programa");            
        
    }    
}

public class Gato { //Clase para el ejercicio 7
    
    // Atributos
    private String nombre;
    private int edad;
    
    // Constructor
    public Gato(String nombre, int edad) throws Exception
    {
        this.setNombre(nombre);
        this.setEdad(edad);       
    }
    
    // Getters y setters
    public String getNombre() 
    {
        return this.nombre;
    }
    
    public void setNombre(String nombre) throws Exception 
    {
        if (nombre.length() < 3) 
        {
            throw new Exception("ERROR: El nombre debe tener al menos 3 carácteres");
        } 
        
        this.nombre = nombre;        
    }
    
    public int getEdad() 
    {
        return this.edad;
    }
   
    public void setEdad(int edad) throws Exception 
    {
        if (edad < 0) 
        {
            throw new Exception("ERROR: La edad no puede ser negativa");
        } 
        
        this.edad = edad;        
    }
    
    // Método imprimir
    public void imprimir()
    {
        System.out.println("El gato se llama " + this.getNombre() + " y tiene " + this.getEdad() + " años de edad");
    }    
    
}


public class Ejercicio7 {
  
    public static void main(String[] args) {
        
        // Creamos el ArrayList de tipo Gato
        ArrayList<Gato> listaGatos = new ArrayList<Gato>();
        
        Scanner in = new Scanner(System.in);
        
        String nombre = "";
        int edad = 0;
        
        do
        {            
            try
            {
                // Pedimos por teclado el nombre y la edad del gato
                System.out.print("Introduce el nombre del gato: ");
                nombre = in.nextLine();
                
                System.out.print("Introduce la edad del gato: ");
                edad = in.nextInt();
                in.nextLine();
                
                // Creamos el objeto Gato
                Gato g = new Gato(nombre, edad);
                
                // Añadimos el objeto Gato al ArrayList
                listaGatos.add(g);
                
            }
            catch(Exception e)
            {
                System.out.println(e);                
            }
            
        }while(listaGatos.size() < 5);
        
        // Recorre el ArrayList de tipo Gato e imprime por pantalla la información del Gato
        for(int i = 0; i < listaGatos.size(); i++)
        {
            System.out.println("Gato " + i);
            Gato g = listaGatos.get(i);
            g.imprimir();
        }        
    }    
}

public class AvisarHaciendaAndSaldoException extends Exception { //Clase para ejercicio 8
     // Atributos
    private String iban;
    private String titular;
    private String tipoOperacion;
    private double cantidad;
    private String msjSaldo;
    
    // Constructor
    public AvisarHaciendaAndSaldoException(String iban, String titular, String tipoOperacion, double cantidad, String msjSaldo) 
    {
        this.iban = iban;
        this.titular = titular;
        this.tipoOperacion = tipoOperacion;
        this.cantidad = cantidad;
        this.msjSaldo = msjSaldo;
    }
    
    @Override
    public String toString()
    {
        String msj = "Aviso: El titular " + this.titular + " de la cuenta " + this.iban + " ha realizado un/a " + this.tipoOperacion + " de " + this.cantidad + "\n";
        msj += this.msjSaldo;
        return "Excepción Avisar Hacienda y Saldo Negativo: \n" + msj;
    }  
}

import java.lang.*;
import java.util.ArrayList;

public class CuentaBancaria { //Clase para ejercicio 8
    
    // Atributos constantes (final)
    private final String iban;
    private final String titular;

    // Atributos variables
    private double saldo;
    private ArrayList movimientos;
    
    // Atributos constantes de clase (static final)    
    private static final double SALDO_MINIMO = -50.0;
    private static final double AVISAR_HACIENDA = 3000.0;

    // Cuenta válida
    private boolean valida;

    // Constructor
    public CuentaBancaria(String iban, String titular) throws CuentaException {

        // Guardamos iban y titular
        this.iban = iban;
        this.titular = titular;

        // Inicilizamos saldo y movimientos
        this.saldo = 0.0;
        this.movimientos = new ArrayList();        

        // Comprobamos IBAN y registramos si es una cuenta válida o no
        if (!iban.matches("^[A-Z]{2}\\d{22}")) {
            this.valida = false;
            throw new CuentaException("El formato del IBAN no es correcto");            
        } else {
            this.valida = true;
        }
    }

    public String getIban() {
        return iban;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean getValida() {
        return valida;
    }

    // Intenta ingresar una cantidad. Devuelve true si pudo hacerlo y lanza una excepción si no
    public boolean ingresar(double cantidad) throws CuentaException, AvisarHaciendaException, Exception{
        if (cantidad <= 0) {
            throw new CuentaException("La cantidad a ingresar debe ser mayor que 0");
        }
        return operar(cantidad);
    }

    // Intenta retirar una cantidad. Devuelve true si pudo hacerlo y lanza una excepción si no
    public boolean retirar(double cantidad) throws CuentaException, AvisarHaciendaException, Exception{
        if (cantidad <= 0) {
            throw new CuentaException("La cantidad a retirar debe ser mayor que 0");
        }
        return operar(-cantidad);
    }

    // Intenta operar sobre la cuenta (ingresos +vo y retiradas -vo)
    // Devuelve true si fue posible y lanza una excepción si no
    private boolean operar(double cantidad) throws CuentaException, AvisarHaciendaException, AvisarHaciendaAndSaldoException, Exception
    {
        // No se permite llegar a un saldo inferior a SALDO_MINIMO        
        if (saldo + cantidad < SALDO_MINIMO) {
            throw new CuentaException("La operación no se puede realizar porque tendríamos un saldo inferior a SALDO_MINIMO");
        }

        // Realizamos la operación: actualizamos saldo y movimientos
        saldo += cantidad;
        movimientos.add(cantidad);       

        // Avisos
        // Lanzará una excepción avisando de que el saldo es negativo y de que la cantidad a ingresar/retirar es mayor que AVISAR_HACIENDA después de hacer la operación 
        if((saldo < 0.0) && (Math.abs(cantidad) > AVISAR_HACIENDA)){
            throw new AvisarHaciendaAndSaldoException(this.getIban(), this.getTitular(), tipoOperacion(cantidad), Math.abs(cantidad), "Aviso: Saldo en cuenta negativo"); 
        }
        else if (saldo < 0.0){ // Lanzará una excepción avisando de que el saldo es negativo después de hacer la operación            
            throw new Exception("Aviso: Saldo en cuenta negativo");
        } 
        else if (Math.abs(cantidad) > AVISAR_HACIENDA){ // Lanzará una excepción avisando de que la cantidad a ingresar/retirar es mayor que AVISAR_HACIENDA
            throw new AvisarHaciendaException(this.getIban(), this.getTitular(), tipoOperacion(cantidad), Math.abs(cantidad));
        }
        // La operación fué posible
        return true;
    }
    
    private String tipoOperacion(double cantidad)
    {
        String tipoOperacion = "";
            
        if(cantidad>0)
        {
            tipoOperacion = "INGRESO";
        }
        else if(cantidad<0)
        {
            tipoOperacion = "RETIRADA";
        }
        
        return tipoOperacion;
        
    }

    // Imprimir informacion de la cuenta
    public void imprimir() {
        imprimirDatos();
        imprimirMovimientos();
    }

    // Imprimir datos de la cuenta
    public void imprimirDatos() {
        System.out.println("IBAN: " + getIban() + " - Titular: " + getTitular() + " - Saldo: " + getSaldo());
    }

    // Imprimir movimientos realizados
    public void imprimirMovimientos() {
        System.out.println("Movimientos: " + movimientos.size());
        for (int i = 0; i < movimientos.size(); i++) {
            System.out.println("#" + (i + 1) + ": " + movimientos.get(i));
        }
    }
}

public class CuentaException extends Exception{ //Clase para ejercicio 8
    
    private String msj;
    
    public CuentaException(String msj)
    {
        this.msj = msj;
    }
    
    @Override
    public String toString()
    {
        return "Excepción Cuenta: " + this.msj;
    }  
    
}

public class Ejercicio8 { //Clase del programa principal ejercicio 8

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        CuentaBancaria cuenta = null; 
        
        boolean errorCuenta;
             
        // Pedimos datos y creamos la cuenta. Repetimos mientras haya un error en la cuenta
        do {
            try
            {
                System.out.print("Introduzca numero IBAN: ");
                String iban = sc.nextLine();
                System.out.print("Introduzca titular de cuenta: ");
                String titular = sc.nextLine();
                cuenta = new CuentaBancaria(iban, titular);
                errorCuenta = false;
            }
            catch (CuentaException e)
            {
                System.out.println(e);
                e.printStackTrace();
                errorCuenta = true;
            }     

        } while (errorCuenta);

        int opcion;
        double cantidad;

        do {
            System.out.println("Elija una opción");
            System.out.println("1. Datos de la cuenta.");
            System.out.println("2. IBAN.");
            System.out.println("3. Titular.");
            System.out.println("4. Saldo.");
            System.out.println("5. Ingreso.");
            System.out.println("6. Retirada.");
            System.out.println("7. Movimientos.");
            System.out.println("8. Salir.");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    cuenta.imprimirDatos();
                    break;
                case 2:
                    System.out.println("IBAN: " + cuenta.getIban());
                    break;
                case 3:
                    System.out.println("Titular: " + cuenta.getTitular());
                    break;
                case 4:
                    System.out.println("Saldo: " + cuenta.getSaldo());
                    break;
                case 5:
                    System.out.print("Inserte cantidad a ingresar: ");
                    cantidad = sc.nextDouble();                    
                    try
                    {
                        cuenta.ingresar(cantidad);
                    }
                    catch(CuentaException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    catch(AvisarHaciendaException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    catch(AvisarHaciendaAndSaldoException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();                        
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    System.out.print("Inserte cantidad a retirar:");
                    cantidad = sc.nextDouble();                    
                    try
                    {
                        cuenta.retirar(cantidad);
                    }
                    catch(CuentaException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    catch(AvisarHaciendaException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    catch(AvisarHaciendaAndSaldoException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();                        
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    cuenta.imprimirMovimientos();
                    break;
                case 8:
                    System.out.println("GRACIAS POR USAR NUESTRA APLICACION");
                    break;
                default:
                    System.out.println("Opción elegida incorrecta.");
                    break;
            }
        } while (opcion != 8);  

        sc.close();           
    }    
}