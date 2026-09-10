package clinicaVeterinaria;
import java.util.*;

public class Veterinario extends Empleado {

    private int numColegiado;
    private String especialidad;

    public Veterinario(int id, String nombre, String fechaNacimiento, String direccion, String telefono,
            String fechaContrato, float salarioBrutoMes, int numColegiado, String especialidad) {
        super(id, nombre, fechaNacimiento, direccion, telefono, fechaContrato, salarioBrutoMes);
        this.numColegiado = numColegiado;
        this.especialidad = especialidad;
    }

}