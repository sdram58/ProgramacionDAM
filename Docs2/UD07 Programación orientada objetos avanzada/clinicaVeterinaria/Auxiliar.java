package clinicaVeterinaria;
import java.util.*;

public class Auxiliar extends Empleado {

    private String cargo;

    public Auxiliar(int id, String nombre, String fechaNacimiento, String direccion, String telefono,
            String fechaContrato, float salarioBrutoMes, String cargo) {
        super(id, nombre, fechaNacimiento, direccion, telefono, fechaContrato, salarioBrutoMes);
        this.cargo = cargo;
    }

}