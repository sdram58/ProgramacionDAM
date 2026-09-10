package adicionales;

public class Usuario extends Persona {
    private int cuotaDisco;
    
    public Usuario(String dni, String nombre, String apellidos, String departamento, String fechaIncorporacion, String nombreUsuario, String contraseña, int cuota){
        super(dni, nombre, apellidos, departamento, fechaIncorporacion, nombreUsuario, contraseña);
        this.cuotaDisco = cuotaDisco;
    }
    
    public int getCuotaDisco(){
        return this.cuotaDisco;
    }
    
    public void setCuotaDisco(int cuota){
        this.cuotaDisco = cuota;
    }
    
}
