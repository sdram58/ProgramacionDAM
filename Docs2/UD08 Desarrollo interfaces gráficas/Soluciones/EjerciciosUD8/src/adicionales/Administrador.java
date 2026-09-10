package adicionales;

public class Administrador extends Persona {
    String nivelAcceso;
    
    public Administrador(String dni, String nombre, String apellidos, String departamento, String fechaIncorporacion, String nombreUsuario, String contraseña, String nivelAcceso){
        super(dni, nombre, apellidos, departamento, fechaIncorporacion, nombreUsuario, contraseña);
        this.nivelAcceso = nivelAcceso;
    }
    
    public String getAccessLevel(){
        return this.nivelAcceso;
    }
    
    public void setAccessLevel(String nivelAcceso){
        this.nivelAcceso = nivelAcceso;
    }
    
}