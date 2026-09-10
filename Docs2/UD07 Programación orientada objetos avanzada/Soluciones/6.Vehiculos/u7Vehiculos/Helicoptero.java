package u7Vehiculos;

public class Helicoptero extends Aereo{
    
    private int numeroHelices;
    
    public Helicoptero(String matricula, String modelo, int numeroAsientos, int numeroHelices){
        super(matricula, modelo, numeroAsientos);
        this.numeroHelices = numeroHelices;
    }
    
    public int getNumeroHelices(){
        return this.numeroHelices;
    }
    
    public void setNumeroHelices(int numeroHelices){
        this.numeroHelices = numeroHelices;
    }
    
    @Override
    public void imprimir(){
        super.imprimir();  
        System.out.println("Helicóptero tiene " + this.getNumeroHelices() + " hélices");
    }
}
