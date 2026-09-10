import java.util.ArrayList;

public class Planeta extends Astro {

    private double distSol;
    private double orbSol;
    private boolean tieneSat;
    private ArrayList<Satelite> satelites;


    public Planeta(String nombre, double radio, double rotEje, double masa, double tempMedia, double gravedad, double distSol, double orbSol, boolean tieneSat, ArrayList<Satelite> satelites) {
        super(nombre, radio, rotEje, masa, tempMedia, gravedad);
        this.distSol = distSol;
        this.orbSol = orbSol;
        this.tieneSat = tieneSat;
        this.satelites = satelites;
    }

    // Constructor sin satélites (añadir satelites posteriormente)
    public Planeta(String nombre, double radio, double rotEje, double masa, double tempMedia, double gravedad, double distSol, double orbSol) {
        super(nombre, radio, rotEje, masa, tempMedia, gravedad);
        this.distSol = distSol;
        this.orbSol = orbSol;
        this.tieneSat = false;
        this.satelites = new ArrayList();
    }

    // Añadir un satélite
    public void añadirSatelite(Satelite s) {
        satelites.add(s);
        this.tieneSat = true;
    }

    @Override
    public void muestra() {
        super.muestra();
		System.out.println("Tipo de astro    : Planeta");
        System.out.println("Distancia al sol : " + distSol);
        System.out.println("Órbita al sol    : " + orbSol);
        System.out.println("Tiene satélites  : " + tieneSat);
        if (tieneSat) {
            for (int i = 0; i < getSatelites().size(); i++) {
                System.out.println("  - Satélite " + i + ": " + satelites.get(i).getNombre());
            }
        }
    }

    public double getDistSol() {
        return distSol;
    }

    public void setDistSol(double distSol) {
        this.distSol = distSol;
    }

    public double getOrbSol() {
        return orbSol;
    }

    public void setOrbSol(double orbSol) {
        this.orbSol = orbSol;
    }

    public boolean getTieneSat() {
        return tieneSat;
    }

    public void setTieneSat(boolean tieneSat) {
        this.tieneSat = tieneSat;
    }

    public ArrayList<Satelite> getSatelites() {
        return satelites;
    }

    public void setSatelites(ArrayList<Satelite> satelites) {
        this.satelites = satelites;
    }
}