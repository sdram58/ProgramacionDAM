public class Astro {

    // Atributos
    protected String nombre;
    protected double radio;
    protected double rotEje;
    protected double masa;
    protected double tempMedia;
    protected double gravedad;

    // Constructor
    public Astro(String nombre, double radio, double rotEje, double masa, double tempMedia, double gravedad) {
        this.nombre = nombre;
        this.radio = radio;
        this.rotEje = rotEje;
        this.masa = masa;
        this.tempMedia = tempMedia;
        this.gravedad = gravedad;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double getRotEje() {
        return rotEje;
    }

    public void setRotEje(double rotEje) {
        this.rotEje = rotEje;
    }

    public double getMasa() {
        return masa;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public double getTempMedia() {
        return tempMedia;
    }

    public void setTempMedia(double tempMedia) {
        this.tempMedia = tempMedia;
    }

    public double getGravedad() {
        return gravedad;
    }

    public void setGravedad(double gravedad) {
        this.gravedad = gravedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	
	public void muestra(){
		System.out.println("*** ASTRO " + nombre + " ***");
        System.out.println("Radio            : " + radio);
        System.out.println("Rotación eje     : " + rotEje);
        System.out.println("Masa:            : " + masa);
        System.out.println("Temperatura media: " + tempMedia);
        System.out.println("Gravedad         : " + gravedad);
	}
}