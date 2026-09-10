package clinicaVeterinaria;
import java.util.*;

public class Cliente extends Persona {

    private List<Animal> animales;

    public Cliente(int id, String nombre, String fechaNacimiento, String direccion, String telefono) {
        super(id, nombre, fechaNacimiento, direccion, telefono);
        this.animales = new ArrayList<>();
    }

    public List<Animal> getAnimales() {
        return animales;
    }

    public void pagarFactura() {
    }
}