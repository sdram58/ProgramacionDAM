package u7Vehiculos;
public class Avion extends Aereo{
    
    private double tiempoMaxVuelo;
    
    public Avion(String matricula, String modelo, int numeroAsientos, double tiempoMaxVuelo){
        super(matricula, modelo, numeroAsientos);
        this.tiempoMaxVuelo = tiempoMaxVuelo;
    }
    
    public double getTiempoMaxVuelo(){
        return this.tiempoMaxVuelo;
    }
    
    public void setTiempoMaxVuelo(double tiempoMaxVuelo){
        this.tiempoMaxVuelo = tiempoMaxVuelo;
    }
    
    @Override
    public void imprimir(){
        super.imprimir();
        System.out.println("Avión tiene un tiempo máximo de vuelo de: " + this.getTiempoMaxVuelo());
        
    }
}
