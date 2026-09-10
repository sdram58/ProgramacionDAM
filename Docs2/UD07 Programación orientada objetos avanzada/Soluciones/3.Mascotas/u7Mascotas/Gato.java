package u7Mascotas;
public class Gato extends Mascota {

    private String color;
    private boolean peloLargo;
    
    public Gato(String nombre, int edad, String estado, String fechaNac, String color, boolean peloLargo) {
        super(nombre, edad, estado, fechaNac);
        this.color = color;
        this.peloLargo = peloLargo;
    }

    @Override
    public void muestra() {
        System.out.println("Nombre   : " + this.getNombre());
        System.out.println("Edad     : " + this.getEdad());
        System.out.println("Estado   : " + this.getEstado());
        System.out.println("Nacido   : " + this.getFechaNac());
        System.out.println("Color    : " + this.getColor());
        System.out.println("PeloLargo: " + this.getPeloLargo());
    }

    @Override
    public void habla() {
        System.out.println("MIAU MIAU");
    }

    // Getters y setters
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getPeloLargo() {
        return peloLargo;
    }

    public void setPeloLargo(boolean peloLargo) {
        this.peloLargo = peloLargo;
    }
}
