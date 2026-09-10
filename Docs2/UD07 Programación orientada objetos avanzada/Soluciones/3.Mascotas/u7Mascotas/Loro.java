package u7Mascotas;
public class Loro extends Ave {

    private String origen;
    private boolean habla;

    public Loro(String nombre, int edad, String estado, String fechaNac, String pico, boolean vuela, String origen, boolean habla) {
        super(nombre, edad, estado, fechaNac, pico, vuela);
        this.origen = origen;
        this.habla = habla;
    }
    
    @Override
    public void muestra() {
        System.out.println("Nombre : " + this.getNombre());
        System.out.println("Edad   : " + this.getEdad());
        System.out.println("Estado : " + this.getEstado());
        System.out.println("Nacido : " + this.getFechaNac());
        System.out.println("Pico   : " + this.getPico());
        System.out.println("Vuela  : " + this.getVuela());
        System.out.println("Origen : " + this.getOrigen());
        System.out.println("Habla  : " + this.getHabla());
    }

    public void saluda() {
        System.out.println("Buenos días!");
    }

    @Override
    public void volar() {
        this.setEstado("Volando");
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean getHabla() {
        return habla;
    }

    public void setHabla(boolean habla) {
        this.habla = habla;
    }

}
