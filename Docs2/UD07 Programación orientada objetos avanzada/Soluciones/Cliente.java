public class Cliente {

    private String nombre;
    private String apellido;
    private FechaNacimiento fecha;

    public Cliente(String nombre, String apellido, FechaNacimiento fecha) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public void setFecha(FechaNacimiento fecha) {
        this.fecha = fecha;
    }

    public FechaNacimiento getFecha() {
        return fecha;
    }

    public String toString() {
        return ("Nombre: " + this.nombre + " " + this.apellido + " " +
            "\nFecha de Nacimiento: " + this.fecha);
    }

}