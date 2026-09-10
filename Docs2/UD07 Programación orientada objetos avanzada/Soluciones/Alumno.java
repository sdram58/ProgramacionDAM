package u7Alumnos;
import java.util.ArrayList;

public class Alumno {
    private String nombre;
    private int edad;
    private ArrayList<Float> notas;

    public Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.notas = new ArrayList<>();
    }
	
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void insertarNota(float nota) {
        notas.add(nota);
    }

    public float calcularNotaMedia() {
        if (notas.isEmpty()) {
            return -1f;
        }

        float suma = 0;
        for (float nota : notas) {
            suma += nota;
        }

        return suma / notas.size();
    }

    public boolean eliminarNota(float nota) {
        return notas.remove(nota);
    }
}
