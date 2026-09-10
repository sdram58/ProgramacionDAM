import java.util.Scanner;

public class Ejercicio_01 {

    public static double multiplica(double a, double b) {
        return a * b;
    }

    public static void main(String[] args) {
        double x, y, res;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce un número real: ");
        x = entrada.nextDouble();
        System.out.print("Introduce un número real: ");
        y = entrada.nextDouble();
        res = multiplica(x, y);
        System.out.println("Resultado: " + res);
    }

}


public class Ejercicio_02 {

    public static boolean esMayorEdad(int a) {
        if (a >= 18) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        int x;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce tu edad: ");
        x = entrada.nextInt();

        if (esMayorEdad(x)) {
            System.out.println("Eres mayor de edad");

        } else {
            System.out.println("No eres mayor de edad");
        }
    }

}

public class Ejercicio_03 {

    public static int minimo(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public static void main(String[] args) {
        int x, y, min;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce un número: ");
        x = entrada.nextInt();
        System.out.print("Introduce un número: ");
        y = entrada.nextInt();
        min = minimo(x, y);
        System.out.println("El minimo es: " + min);

    }
}


public class Ejercicio_04 {

    public static int dimeSigno(int a) {
        int r;
        if (a < 0) {
            r = -1;
        } else if (a == 0) {
            r = 0;
        } else {
            r = 1;
        }
        return r;
    }

    public static void main(String[] args) {
        int x, y, min;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce un número: ");
        x = entrada.nextInt();

        switch (dimeSigno(x)) {
            case -1:
                System.out.println("Es negativo");
                break;
            case 0:
                System.out.println("Es cero");
                break;
            case 1:
                System.out.println("Es positivo");
                break;

        }

    }

}



public class Ejercicio_05 {

    public static double millas_a_kilometros(double millas) {
        return 1.60934 * millas;
    }

    public static void main(String[] args) {
        double millas, km;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce las millas: ");
        millas = entrada.nextDouble();
        km = millas_a_kilometros(millas);
        System.out.println("Kilómetros: " + km);

    }
}

public class Ejercicio_06 {

    public static double precioConIVA(double precio) {
        return (precio * 1.21);
    }

    public static void main(String[] args) {
        double precioSin, precioCon;
        Scanner entrada = new Scanner(System.in);
        
        for (int i = 0; i < 5; i++) {
            System.out.print("Precio sin IVA: ");
            precioSin = entrada.nextDouble();
            precioCon = precioConIVA(precioSin);
            System.out.println("Precio con IVA: " + precioCon);
        }
    }
}


public class Ejercicio_07 {

    public static double perimetroRectangulo(double ancho, double alto) {
        return (2 * ancho + 2 * alto);
    }

    public static double areaRectangulo(double ancho, double alto) {
        return ancho * alto;
    }

    public static void main(String[] args) {
        double ancho, alto, perimetro, area;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce ancho: ");
        ancho = entrada.nextDouble();
        System.out.print("Introduce alto: ");
        alto = entrada.nextDouble();

        perimetro = perimetroRectangulo(ancho, alto);
        area = areaRectangulo(ancho, alto);

        System.out.println("Perímetro: " + perimetro);
        System.out.println("Área: " + area);

    }
}

public class Ejercicio_08 {

    public static int suma1aN(int n) {
        int suma = 0;
        for (int i = 1; i <= n; i++) {
            suma += i;
        }
        return suma;
    }

    public static int producto1aN(int n) {
        int prod = 1;
        for (int i = 1; i <= n; i++) {
            prod *= i;
        }
        return prod;
    }

    public static double intermedio1aN(int n) {
        return (1.0 + (double) n) / 2.0;
    }

    public static void main(String[] args) {
        int n;
        double suma, prod, inter;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce N: ");
        n = entrada.nextInt();
        suma = suma1aN(n);
        prod = producto1aN(n);
        inter = intermedio1aN(n);

        System.out.println("Suma de 1 a N: " + suma);
        System.out.println("Producto de 1 a N: " + prod);
        System.out.println("Intermedio de 1 a N: " + inter);
    }
}

public class Ejercicio_09 {

    public static int maximo(int a, int b) {
        if (a > b) {
            return a;
        }
        else {
            return b;
        }
        // También se puede hacer con el operador ternario '?' así:
        // return (a > b) ? a : b;
    }

    public static void main(String[] args) {
        int a, b, c, max;
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Valor 1: ");
        a = entrada.nextInt();
        System.out.print("Valor 2: ");
        b = entrada.nextInt();
        System.out.print("Valor 3: ");
        c = entrada.nextInt();
        
        max = maximo(a, b);
        max = maximo(max, c);

        System.out.println("Máximo: " + max);
    }
}


public class Ejercicio_10 {

    public static boolean fechacorrecta(int dia, int mes, int anyo) {
        if (dia >= 1 && dia <= 30 && mes >= 1 && mes <= 12) {
            return true;
        }
        else {
            return false;
        }
        // También se puede hacer directamente así
        // return (dia >= 1 && dia <= 30 && mes >= 1 && mes <= 12);
    }

    public static void main(String[] args) {
        int dia, mes, año;
        boolean escorrecta;
        
        Scanner entrada = new Scanner(System.in);
        System.out.print("Valor del dia: ");
        dia = entrada.nextInt();
        System.out.print("Valor del mes: ");
        mes = entrada.nextInt();
        System.out.print("Valor del año: ");
        año = entrada.nextInt();
        
        escorrecta = fechacorrecta(dia, mes, año);

        if (escorrecta) {
            System.out.println("La fecha es correcta");
        } else {
            System.out.println("La fecha no es correcta");
        }
    }
}

public class Ejercicio_11 {

    public static void tabla_multiplicar(int n) {
        System.out.println("Tabla de multiplicar del " + n);
        for (int i = 1; i <= 10; i++) {
            System.out.println(n + " x " + i + " = " + (n * i));
        }
    }

    public static void main(String[] args) {
        int n;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce un número N: ");
        n = entrada.nextInt();
        tabla_multiplicar(n);
    }
}

public class Ejercicio_12 {

    public static double kilometros_a_millas(double km) {
        return (km / 1.60934);
    }

    public static void main(String[] args) {
        double millas, km;
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce las millas: ");
        millas = sc.nextDouble();
        
        km = kilometros_a_millas(millas);
        
        System.out.println("Kilómetros: " + km);
    }
}

public class Ejercicio_13 {

    public static double calcula_porc_dto(double precio, double pvp) {
        return 100 * (1.0 - (pvp / precio));
    }

    public static void main(String[] args) {
        double precio, pvp, porc_dto;
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Precio original: ");
        precio = sc.nextDouble();
        System.out.print("Precio tras descuento: ");
        pvp = sc.nextDouble();
        
        porc_dto = calcula_porc_dto(precio, pvp);
        
        System.out.printf("Descuento : %.2f%%", porc_dto);
    }
}


public class Ejercicio_14 {

    static void imprimetriangulo(char a, int linea) {

        // Para cada línea
        for (int i = 1; i <= linea; i++) {

            // Imprimimos espacios
            for (int j = i - 1; j < linea - 1; j++) {
                System.out.print(" ");
            }

            // Imprimimos el char pasado como argumento
            for (int j = 0; j < i * 2 - 1; j++) {
                System.out.print(a);
            }

            // Nueva línea
            System.out.println("");
        }
    }

    public static void main(String args[]) {

        int lineas;
        char car;

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el caracter:");
        car = sc.next().charAt(0);
        System.out.print("Introduce el número de lineas:");
        lineas = sc.nextInt();
        
        imprimetriangulo(car, lineas);
        
        System.out.println();

    }
}


public class Ejercicio_15 {

    public static int suma_vector(int[] v) {
        int suma = 0;
        for (int i = 0; i < v.length; i++) {
            suma += v[i];
        }
        return suma;
    }

    public static double media_vector(int[] v) {
        return (double)suma_vector(v) / (double)v.length;
    }

    public static void main(String[] args) {
        int array[] = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i + 1;
        }
        System.out.println("Suma: " + suma_vector(array));
        System.out.println("Media: " + media_vector(array));
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class Ejercicio_16 {
    
    public static void rellenaRandom(double[] v) {
        for (int i = 0; i < v.length; i++) {
            v[i] = Math.random();
        }
    }
    
    public static void main(String[] args) {
        double vector[];
        int n;
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce N: ");
        n = sc.nextInt();

        vector = new double[n];
        rellenaRandom(vector);
        
        System.out.println(Arrays.toString(vector));
    }
}


public class Ejercicio_17 {

    public static boolean esPrimo(int n) {
        // Si es 1, 0 o negativo => NO ES PRIMO
        if (n <= 1) {
            return false;
        }

        // Desde 2 hasta < n/2
        for (int i = 2; i <= n / 2; i++) {
            // Si n es divisible por i => NO ES PRIMO
            if (n % i == 0) {
                return false;
            }
        }

        // Si la función llega aquí es porque n>1 y en el for
        // no se ha encontrado un nº divisible => SÍ ES PRIMO
        return true;
    }

    public static void main(String[] args) {

        int numero;
        boolean primo;

        Scanner in = new Scanner(System.in);
        
        // Bucle infinito
        while (true) {

            // Pedimos número
            System.out.print("Introduce un número (0 para terminar): ");
            numero = in.nextInt();

            // Si es cero terminamos el bucle (si no, continuamos)
            if (numero == 0) {
                break;
            }

            // Calculamos si es primo
            primo = esPrimo(numero);
            
            // Mostramos mensaje
            if (primo) {
                System.out.println("Es primo.");
            } else {
                System.out.println("No es primo.");
            }
        }
    }
}


public class Ejercicio_18 {

    public static char calculaDNI(int dni) {
        String letra = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letra.charAt(dni % 23);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Introduce nº de DNI: ");
        int dni = in.nextInt();
        char letra = calculaDNI(dni);
        System.out.println("La letra del DNI es: " + letra);
    }

}

public class Ejercicio_19 {

    public static boolean esPitagorica(int x, int y, int z) {
        return Math.pow(x, 2) + Math.pow(y, 2) == Math.pow(z, 2);
    }

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Vamos a comprobar si X² + Y² = Z²");
        System.out.print("Valor X: ");
        int x = teclado.nextInt();
        System.out.print("Valor Y: ");
        int y = teclado.nextInt();
        System.out.print("Valor Z: ");
        int z = teclado.nextInt();
        
        boolean pitagorica = esPitagorica(x, y, z);
        
        if (pitagorica) {
            System.out.println("Sí es pitagórica");
        }
        else {
            System.out.println("No es pitagórica");
        }
    }
    
}


public class Ejercicio_20 {

    public static void tabla_multiplicar(int n) {
        System.out.println("Tabla de multiplicar del " + n);
        for (int i = 1; i <= 10; i++) {
            System.out.println(n + " x " + i + " = " + (n * i));
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            tabla_multiplicar(i);
        }
    }
}


public class Ejercicio_21 {

    public static void main(String args[]) {
        int opcion;
        double radio;
        do {
            opcion = menu();
            switch (opcion) {
                case 0:
                    System.out.println("Terminamos.");
                    break;
                case 1:
                    radio = pideRadio();
                    System.out.println("Circunferencia: " + circunferencia(radio));
                    break;
                case 2:
                    radio = pideRadio();
                    System.out.println("Área: " + area(radio));
                    break;
                case 3:
                    radio = pideRadio();
                    System.out.println("Volumen: " + volumen(radio));
                    break;
                case 4:
                    radio = pideRadio();
                    System.out.println("Circunferencia: " + circunferencia(radio));
                    System.out.println("Área: " + area(radio));
                    System.out.println("Volumen: " + volumen(radio));
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
            System.out.println();
        } while (opcion != 0); // se repite hasta que la opción elegida sea 0, salir
    }

    public static int menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("ELIGE UNA OPCIÓN:");
        System.out.println("1. Circunferencia");
        System.out.println("2. Área");
        System.out.println("3. Volumen");
        System.out.println("4. Todas");
        System.out.println("0. Salir");
        return in.nextInt();
    }

    public static double pideRadio() {
        Scanner in = new Scanner(System.in);
        System.out.print("Introduce el radio en cm: ");
        return in.nextDouble();
    }

    public static double circunferencia(double r) {
        return r * Math.PI;
    }

    public static double area(double r) {
        return r * r * Math.PI;
    }

    public static double volumen(double r) {
        return r * r * r * Math.PI * 4 / 3;
    }
}










