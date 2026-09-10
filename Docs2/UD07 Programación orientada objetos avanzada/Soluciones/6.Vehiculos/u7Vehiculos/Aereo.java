package u7Vehiculos;
public class Aereo extends Vehiculo {
    
    private final int numeroAsientos;
    
    public Aereo(String matricula, String modelo, int numeroAsientos){
        super(matricula, modelo);
        
        if(matricula.matches("[a-zA-Z]{4}[0-9]{6}")){
            this.numeroAsientos = numeroAsientos;
        }else{
            this.numeroAsientos = 0;
            System.out.println("ERROR: Matrícula no válida");
        }
    }
    
    public int getNumeroAsientos(){
        return this.numeroAsientos;
    }
         
    @Override
    public void imprimir(){
        System.out.println("Vehículo aéreo --> Matrícula: " + this.getMatricula() + " - Modelo: " + this.getModelo() + " - Nº asientos: " + this.getNumeroAsientos());        
    }
    
}
