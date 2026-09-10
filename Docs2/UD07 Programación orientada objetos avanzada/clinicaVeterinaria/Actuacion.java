package clinicaVeterinaria;
import java.util.*;

public class Actuacion {

    private int idActuacion;
    private String diagnostico;
    private String tratamiento;
    private String estado;
    private List<Empleado> empleadosAsignados;
    private Animal animal;
    private Factura factura;
      
    public Actuacion(int idActuacion, String diagnostico, String tratamiento, String estado, Animal animal) {
        this.idActuacion = idActuacion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estado = estado;
        this.empleadosAsignados = new ArrayList<>();
        this.animal = animal;
    }

    public int getIdActuacion() {
        return idActuacion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getEstado() {
        return estado;
    }

    public List<Empleado> getEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Factura getFactura() {
        return factura;
    }

    public void finalizarActuacion() {
    }
}