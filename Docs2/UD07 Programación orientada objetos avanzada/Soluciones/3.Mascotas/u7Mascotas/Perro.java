package u7Mascotas;
public class Perro extends Mascota {

    private String raza;
    private boolean pulgas;

    public Perro(String nombre, int edad, String estado, String fechaNac, String raza, boolean pulgas) {
        super(nombre, edad, estado, fechaNac);
        this.raza = raza;
        this.pulgas = pulgas;
    }

    @Override
    public void muestra() {
        System.out.println("Nombre : " + this.getNombre());
        System.out.println("Edad   : " + this.getEdad());
        System.out.println("Estado : " + this.getEstado());
        System.out.println("Nacido : " + this.getFechaNac());
        System.out.println("Raza   : " + this.getRaza());
        System.out.println("Pulgas : " + this.getPulgas());
    }

    @Override
    public void habla() {
        System.out.println("GUAU GUAU");
    }

    // Getters y setters

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean getPulgas() {
        return pulgas;
    }

    public void setPulgas(boolean pulgas) {
        this.pulgas = pulgas;
    }
}
