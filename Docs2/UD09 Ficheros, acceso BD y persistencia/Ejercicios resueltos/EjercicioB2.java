import java.io.*;
import java.util.*;

public class EjercicioB2 {

    public static void main(String[] args) {
        try {
            File f = new File("./Documentos/alumnos_notas.txt");
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> nombresAlumnos = new ArrayList<String>();
            ArrayList<Float> notasMedias = new ArrayList<Float>();

            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] trozosLinea = linea.split(" ");

                String nombre = trozosLinea[0] + " " + trozosLinea[1];
                nombresAlumnos.add(nombre);

                int suma = 0;
                for (int j = 2; j < trozosLinea.length; j++) {
                    suma += Integer.valueOf(trozosLinea[j]);
                }
                float media = (float) suma / (trozosLinea.length - 2);
                notasMedias.add(media);
            }

            // Ordenar las notas medias utilizando el algoritmo de la burbuja
            int n = notasMedias.size();
            for (int i = 0; i < n-1; i++) {
                for (int j = 0; j < n-i-1; j++) {
                    if (notasMedias.get(j) < notasMedias.get(j+1)) {
                        // Intercambiar notas medias
                        float tempNota = notasMedias.get(j);
                        notasMedias.set(j, notasMedias.get(j+1));
                        notasMedias.set(j+1, tempNota);

                        // Intercambiar también nombres de alumnos
                        String tempNombre = nombresAlumnos.get(j);
                        nombresAlumnos.set(j, nombresAlumnos.get(j+1));
                        nombresAlumnos.set(j+1, tempNombre);
                    }
                }
            }

            System.out.println("LISTADO DE NOTAS MEDIAS DE LOS ALUMNOS");
            System.out.println("--------------------------------------");

            // Mostrar los alumnos ordenados por notas medias
            for (int i = 0; i < nombresAlumnos.size(); i++) {
                System.out.println(String.format("%.2f %s", notasMedias.get(i), nombresAlumnos.get(i)));
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: El fichero no existe");
        } catch (IOException e) {
            System.out.println("Error de lectura del fichero: " + e);
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e);
        }
    }
}
