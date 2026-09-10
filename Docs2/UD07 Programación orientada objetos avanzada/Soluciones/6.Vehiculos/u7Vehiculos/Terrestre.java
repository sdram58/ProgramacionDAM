package u7Vehiculos;
public class Terrestre extends Vehiculo {
    
    private final int numeroRuedas;
    
    public Terrestre(String matricula, String modelo, int numeroRuedas){                
        super(matricula, modelo);
        
        if(matricula.matches("[0-9]{4}[a-zA-Z]{3}")) {
           this.numeroRuedas = numeroRuedas;  
        }else{
            this.numeroRuedas = 0;
            System.out.println("ERROR: Matrícula no válida");
        }     
    }
    
    public int getNumeroRuedas(){
        return this.numeroRuedas;
    }  
    
    @Override
    public void imprimir(){
        System.out.println("Vehículo terrestre --> Matrícula: " + this.getMatricula() + " - Modelo: " + this.getModelo() + " - Nº ruedas: " + this.getNumeroRuedas());
    }
    
}
