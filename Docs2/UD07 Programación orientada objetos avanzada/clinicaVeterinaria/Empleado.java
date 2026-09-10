package clinicaVeterinaria;
import java.util.*;

public class Empleado extends Persona {

    private String fechaContrato;
    private float salarioBrutoMes;
    private List<Actuacion> actuaciones;

    public Empleado(int id, String nombre, String fechaNacimiento, String direccion, String telefono,
            String fechaContrato, float salarioBrutoMes) {
        super(id, nombre, fechaNacimiento, direccion, telefono);
        this.fechaContrato = fechaContrato;
        this.salarioBrutoMes = salarioBrutoMes;
        this.actuaciones = new ArrayList<>();
    }
}