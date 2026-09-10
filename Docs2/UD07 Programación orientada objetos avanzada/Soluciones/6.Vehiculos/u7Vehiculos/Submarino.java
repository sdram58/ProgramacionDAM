package u7Vehiculos;
public class Submarino extends Acuatico {
    
    private double profundidadMax;
    
    public Submarino(String matricula, String modelo, double eslora, double profundidadMax) {
        super(matricula, modelo, eslora);       
        this.profundidadMax = profundidadMax;       
    }
    
    public double getProfundidadMax() {
        return profundidadMax;
    }
    
    public void setProfundidadMax(double profundidadMax) {
        this.profundidadMax = profundidadMax;
    }    
    
    @Override
    public void imprimir() {
        super.imprimir();
        System.out.println("Submarino tiene una profundidad máxima de: " + this.getProfundidadMax());
    }
    
}
