package u7Mascotas;
public abstract class Mascota {

    protected String nombre; //Podrían ser private, en este caso. Piensa porqué...
    protected int edad;
    protected String estado;
    protected String fechaNac;

    public Mascota(String nombre, int edad, String estado, String fechaNac) {
        this.nombre = nombre;
        this.edad = edad;
        this.estado = estado;
        this.fechaNac = fechaNac;
    }

    public abstract void muestra();

    public void habla(){}
    
    public void cumpleaños() {
        this.edad = this.edad + 1;
    }

    public void morir() {
        this.estado = "Muerto";
    }

    // Getters y setters
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

}
