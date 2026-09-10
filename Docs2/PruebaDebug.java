import java.util.ArrayList;

public class PruebaDebug {
    public static void main(String[] args) {
        int a = 5;
        int b = 7;

        int suma = a + b;
        System.out.println("Operación 1 - Suma: " + suma);

        int resta = suma - 3;
        System.out.println("Operación 2 - Resta: " + resta);

        int multiplicacion = resta * 2;
        System.out.println("Operación 3 - Multiplicación: " + multiplicacion);

        double division = (double) multiplicacion / 4;
        System.out.println("Operación 4 - División: " + division);

        int[] numeros = {100, 200, 300, 400, 500};
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] += 70;
            System.out.print(numeros[i] + " ");
        }
        System.out.println();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(6);
        arrayList.add(9);
        for (int valor : arrayList) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }
}
