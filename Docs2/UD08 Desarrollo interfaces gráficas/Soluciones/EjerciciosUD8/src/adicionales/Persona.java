package adicionales;

public abstract class Persona {

    private String dni;
    private String nombre;
    private String apellidos;
    private String departamento;
    private String fechaIncorporacion;
    private String nombreUsuario;
    private String password;

    public Persona(String dni, String nombre, String apellidos, String departamento, String fechaIncorporacion, String nombreUsuario, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
        this.fechaIncorporacion = fechaIncorporacion;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getDNI() {
        return this.dni;
    }

    public String getUsername() {
        return this.nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public String getPassword() {
        return password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setFechaIncorporacion(String fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

}
