// Ejercicio 1
public static void main(String[] args) {
        double valores[] = new double[10];
        Scanner entrada = new Scanner(System.in);

        // Introducimos todos los valores en el array
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Introduce valor " + i + ": ");
            valores[i] = entrada.nextDouble();
        }

        // Mostramos todos los valores del array
        for (int i = 0; i < valores.length; i++) {
            System.out.println("Valor " + i + ": " + valores[i]);
        }
    }
	
// Ejercicio 2
public static void main(String[] args) {
        double valores[] = new double[10];
        Scanner entrada = new Scanner(System.in);
        
        // Introducimos todos los valores en el array
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Introduce valor " + i + ": ");
            valores[i] = entrada.nextDouble();
        }
        
        // Recorremos el array para calcular la suma
        double suma = 0;
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i];
        }
        
        System.out.println("Suma total: " + suma);
    }

// Ejercicio 3
public static void main(String[] args) {
        double valores[] = new double[10];
        Scanner entrada = new Scanner(System.in);

        // Introducimos todos los valores en el array
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Introduce valor " + i + ": ");
            valores[i] = entrada.nextDouble();
        }

        // Averiguamos máximo y mínimo
        double max = valores[0], min = valores[0];
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > max)
                max = valores[i];
            else if (valores[i] < min)
                min = valores[i];
        }
        
        System.out.println("Máximo: " + max);
        System.out.println("Mínimo: " + min);
    }


// Ejercicio 4
public static void main(String[] args) {
        int pos=0, neg=0;
        int valores[] = new int[20];
        Scanner entrada = new Scanner(System.in);
        
        // Introducimos todos los valores en el array
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Introduce valor " + i + ": ");
            valores[i] = entrada.nextInt();
        }
        
        // Sumamos positivos y negativos por separado
        for (int i = 0; i < valores.length; i++) {
               if (valores[i] > 0)
                   pos += valores[i];
               else
                   neg += valores[i];
        }
        
        System.out.println("Suma de Positivos: " + pos);
        System.out.println("Suma de Negativos: " + neg);

    }
	
// Ejercicio 5
public static void main(String[] args) {
        double valores[] = new double[10];
        Scanner entrada = new Scanner(System.in);
        
        // Introducimos todos los valores en el array
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Introduce valor " + i + ": ");
            valores[i] = entrada.nextDouble();
        }
        
        // Recorremos el array para calcular la suma
        double suma = 0;
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i];
        }
        
        // Calculamos y mostramos la media
        double media = suma / valores.length;
        System.out.println("Media: " + media);
    }
	
// Ejercicio 6
 public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Tamaño del vector: ");
        int n = entrada.nextInt();
        System.out.print("Valor a introducir: ");
        int m = entrada.nextInt();

        // Creamos el array e inserta m en todas sus posiciones
        int vector[] = new int[n];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = m;
        }

        // Mostramos el array
        System.out.print("Array: ");
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
    }
	

// Ejercicio 7
public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Valor inicial P: ");
        int p = entrada.nextInt();
        System.out.print("Valor final Q: ");
        int q = entrada.nextInt();

        // Creamos el array de tamaño (Q - P + 1)
        int vector[] = new int[q - p + 1];
        
        // En cada posición (i) insertamos (P + i)
        for (int i = 0; i < vector.length; i++) {
            vector[i] = p + i;
        }

        // Mostramos el array
        System.out.print("Array: ");
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
    }
	
// Ejercicio 8
public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Creamos array con 100 números [0.0, 1.0[
        double vector[] = new double[100];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Math.random();
        }

        // Pedimos valor R al usuario
        System.out.print("Introduce un número real de 0.0 a 1.0: ");
        double r = entrada.nextDouble();

        // Calculamos cuantos números son mayores o igual a R
        int cuantos = 0;
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] >= r)
                cuantos++;
        }

        System.out.println("Cantidad de números mayores o iguales: " + cuantos);
    }
	
// Ejercicio 9
public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Creamos array con 100 números [1, 10]
        int vector[] = new int[100];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (1 + Math.random() * 10);
        }

        // Pedimos valor N al usuario
        System.out.print("Introduce valor N a buscar: ");
        double n = entrada.nextInt();

        // Mostramos las posiciones en las que aparece N
        System.out.print("Posiciones donde aparece N:");
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == n)
                System.out.print(" " + i);
        }
        
        System.out.println();
    }
	
// Ejercicio 10
public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Pedimos nº de alturas y creamos vector
        System.out.print("¿Cuántas alturas a introducir?: ");
        int n = entrada.nextInt();
        double alturas[] = new double[n];

        // Pedimos alturas al usuario e introducimos en array
        System.out.print("Introduce las alturas de una en una: ");
        for (int i = 0; i < alturas.length; i++) {
            alturas[i] = entrada.nextDouble();
        }
        
        // Calculamos media, máximo y mínimo
        double suma = 0, media, max = alturas[0], min = alturas[0];
        for (int i = 0; i < alturas.length; i++) {
            suma += alturas[i];
            if (alturas[i] > max) max = alturas[i];
            if (alturas[i] < min) min = alturas[i];
        }
        media = suma / n;

        // Calculamos cuantas alturas por encima y debajo de la media
        int encima = 0, debajo = 0;
        for (int i = 0; i < alturas.length; i++) {
            if (alturas[i] > media) encima++;
            if (alturas[i] < media) debajo++;
        }

        // Mostramos los datos
        System.out.println("Altura media:  " + media);
        System.out.println("Altura máxima: " + max);
        System.out.println("Altura mínima: " + min);
        System.out.println("Alturas por encima de la media: " + encima);
        System.out.println("Alturas por debajo de la media: " + debajo);
    }
	
// Ejercicio 11
 public static void main(String[] args) {
        int length = 100;
        int v1[] = new int[length];
        int v2[] = new int[length];

        // V1: Valores del 1 al 100
        for (int i = 0; i < length; i++)
            v1[i] = i + 1;
        
        // Copiamos V1 en V2 en orden inverso
        for (int i = 0; i < length; i++)
            v2[i] = v1[length - i - 1];

        // Mostramos Vector 1
        System.out.print("Vector 1: ");
        for (int i = 0; i < length; i++) {
            System.out.print(v1[i] + " ");
        }
        System.out.println();
        
        // Mostramos Vector 2
        System.out.print("Vector 2: ");
        for (int i = 0; i < length; i++) {
            System.out.print(v2[i] + " ");
        }

    }
	
// Ejercicio 12
public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        int vector[] = new int[10];
        boolean continuar = true;
        String opcion;

        while (continuar) {
            System.out.println();
            System.out.println("MENU PRINCIPAL");
            System.out.println("a. Mostrar valores");
            System.out.println("b. Introducir valor");
            System.out.println("c. Salir");
            System.out.print("Elige una opción y presiona intro: ");
            opcion = entrada.nextLine();
            
            switch (opcion) {
                
                case "a":
                    for (int i = 0; i < vector.length; i++)
                        System.out.print(vector[i] + " ");
                    System.out.println();
                    break;
                    
                case "b":
                    int v, p;
                    System.out.print("Valor: ");
                    v = entrada.nextInt();
                    System.out.print("Posición: ");
                    p = entrada.nextInt();
                    if (p < 0 || p >= vector.length)
                        System.out.println("Posición fuera del límite");
                    else
                        vector[p] = v;
                    break;
                    
                case "c":
                    System.out.println("Fin del programa");
                    continuar = false;
                    break;
                    
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            entrada.nextLine();
        }
    }
	
// Ejercicio 13
public static void main(String[] args) {
        int vini, inc, n;
        int secuencia[];

        // Pedimos valor inicial, incremento y cuántos números
        Scanner entrada = new Scanner(System.in);
        System.out.print("Valor inicial: ");
        vini = entrada.nextInt();
        System.out.print("Incremento: ");
        inc = entrada.nextInt();
        System.out.print("Cuántos números: ");
        n = entrada.nextInt();

        // Creamos el vector con la secuencia
        secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = vini + (i * inc);
        }

        // Mostramos la secuencia
        System.out.print("Secuencia: ");
        for (int i = 0; i < n; i++) {
            System.out.print(secuencia[i] + " ");
        }
    }

// Ejercicio 14
public static void main(String[] args) {
        
        // Calculamos cuantos números tiene la secuencia
        int cuantos = 0;
        for (int i = 1; i <= 10; i++) {
            cuantos += i;
        }

        // Creamos el vector
        int valores[] = new int[cuantos];
        
        // Índice para recorrer el vector
        int posicion = 0;
        
        // Para cada número i del 1 al 10
        for (int i = 1; i <= 10; i++) {
            
            // Repetimos i veces
            for (int j = 0; j < i; j++) {
                
                // Guardar el valor en 'posición'
                valores[posicion] = i;
                
                // Actualizamos posición
                posicion++;
            }
        }

        // Mostramos el array
        System.out.print("Array: ");
        for (int i = 0; i < valores.length; i++) {
            System.out.print(valores[i] + " ");
        }

    }
	
// Ejercicio 15
public static void main(String[] args) {
        
        // Pedimos tamaño (n) y valor (m)
        Scanner entrada = new Scanner(System.in);
        System.out.print("Tamaño del vector: ");
        int n = entrada.nextInt();
        System.out.print("Valor a introducir: ");
        int m = entrada.nextInt();

        // Creamos el array
        int vector[] = new int[n];
        
        // Insertamos m en todas sus posiciones
        Arrays.fill(vector, m);

        // Mostramos el array
        String arrayStr = Arrays.toString(vector);
        System.out.println("Array: " + arrayStr);
    }

// Ejercicio 16
public static void main(String[] args) {
        
        // Calculamos cuantos números tiene la secuencia
        int cuantos = 0;
        for (int i = 1; i <= 10; i++) {
            cuantos += i;
        }

        // Creamos el vector
        int valores[] = new int[cuantos];

        // Posición donde insertar los números
        int pos = 0;
        
        // Para cada número n del 1 al 10
        for (int n = 1; n <= 10; n++) {
            
            // Introducimos n veces 'n' en la posición 'pos'
            Arrays.fill(valores, pos, pos + n, n);
            
            // Actualizamos pos
            pos += n;
        }

        // Mostramos el array
        String arrayStr = Arrays.toString(valores);
        System.out.println("Array: " + arrayStr);
    }


// Ejercicio 17
 public static void main(String[] args) {

        // Creamos los vectores
        int v1[] = new int[10];
        int v2[] = new int[10];

        // Pedimos los 20 valores y los guardamos
        Scanner entrada = new Scanner(System.in);
        for (int i = 0; i < v1.length; i++) {
            System.out.print("Introduce valor: ");
            v1[i] = entrada.nextInt();
        }
        for (int i = 0; i < v2.length; i++) {
            System.out.print("Introduce valor: ");
            v2[i] = entrada.nextInt();
        }

        // Comparamos si son iguales
        if (Arrays.equals(v1, v2)) {
            System.out.println("Son iguales");
        } else {
            System.out.println("No son iguales");
        }

    }
	
// Ejercicio 18
public static void main(String[] args) {

        // Creamos el vector
        int vector[] = new int[30];

        // Introducimos valores aleatorios en el rango [0,9]
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (Math.random() * 10);
        }
        
        // Ordenamos el vector
        Arrays.sort(vector);
        
        // Mostramos el vector
        String vectorStr = Arrays.toString(vector);
        System.out.println(vectorStr);

    }
	
// Ejercicio 19
public static void main(String[] args) {

        // Creamos el vector con las puntuaciones
        int p[] = new int[8];

        // Pedimos por teclado las puntuaciones
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduzca las 8 puntuaciones: ");
        for (int i = 0; i < p.length; i++) {
            p[i] = entrada.nextInt();
        }

        // Ordenamos el array (de menor a mayor)
        Arrays.sort(p);

        // Mostramos el array (de mayoir a menor)
        System.out.print("Puntuaciones ordenadas de mayor a menor: ");
        for (int i = p.length - 1; i >= 0; i--) {
            System.out.print(p[i] + " ");
        }
    }


// Ejercicio 20
public static void main(String[] args) {

        // Creamos vector
        int vector[] = new int[1000];

        // Introducimos valores aleatorios en el rango [0,99]
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (Math.random() * 100);
        }

        // Pedimos valor N a buscar en el vector
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduzca valor entre 0 y 99 a buscar en el vector: ");
        int n = entrada.nextInt();

        // Buscamos la posición de N en el vector (primero debe estar ordenado)
        Arrays.sort(vector);
        int pos = Arrays.binarySearch(vector, n);
        
        // Si N existe
        if (pos >= 0) {
            // Buscamos cuantas veces aparece
            int veces = 0;
            for (int i = pos; i < vector.length; i++) {
                if (vector[i] == n) veces++;
                else break;
            }
            System.out.println("Sí existe en el vector. Aparece " + veces + " veces.");
        }
        // Si N no existe
        else {
            System.out.println("No existe en el vector.");
        }
    }