import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private String email;
    private List<String> telefonos;

    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.telefonos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void guardarTelefono(String telefono) {
        if (!telefonos.contains(telefono)) {
            telefonos.add(telefono);
        }
    }

    public String getTelefono(int indice) {
        if (indice >= 0 && indice < telefonos.size()) {
            return telefonos.get(indice);
        }
        return null;
    }

    public String buscarClientePorTelefono(String telefono) {
        if (telefonos.contains(telefono)) {
            return nombre;
        }
        return null;
    }
}