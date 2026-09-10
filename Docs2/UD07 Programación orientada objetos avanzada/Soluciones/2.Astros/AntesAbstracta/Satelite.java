public class Satelite extends Astro {

    private double distPlaneta;
    private double orbPlaneta;
    private Planeta planeta;

    public Satelite(String nombre, double radio, double rotEje, double masa, double tempMedia, double gravedad, double distPlaneta, double orbPlaneta, Planeta planeta) {
        super(nombre, radio, rotEje, masa, tempMedia, gravedad);
        this.distPlaneta = distPlaneta;
        this.orbPlaneta = orbPlaneta;
        this.planeta = planeta;
    }

    public void muestra() {
        super.muestra();
		System.out.println("Tipo de astro    : Satélite");
        System.out.println("Distancia planeta: " + getDistPlaneta());
        System.out.println("Órbita al planeta: " + getOrbPlaneta());
        System.out.println("Pertenece planeta: " + planeta.getNombre());
    }
    
    public double getDistPlaneta() {
        return distPlaneta;
    }

    public void setDistPlaneta(double distPlaneta) {
        this.distPlaneta = distPlaneta;
    }

    public double getOrbPlaneta() {
        return orbPlaneta;
    }

    public void setOrbPlaneta(double orbPlaneta) {
        this.orbPlaneta = orbPlaneta;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

}