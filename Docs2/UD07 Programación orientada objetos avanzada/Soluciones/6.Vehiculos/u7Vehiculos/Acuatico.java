package u7Vehiculos;
public class Acuatico extends Vehiculo {
    
    private final double eslora;
       
    public Acuatico(String matricula, String modelo, double eslora){
        super(matricula, modelo);
        
        if(matricula.matches("[a-zA-Z]{3,10}")) {
            this.eslora = eslora;
        }else{
            this.eslora = 0;
            System.out.println("ERROR: Matrícula no válida");
        }        
    }
    
    public double getEslora(){
        return eslora;
    }
    
    @Override
    public void imprimir(){
        System.out.println("Vehículo acuático --> Matrícula: " + this.getMatricula() + " - Modelo: " + this.getModelo() + " - Eslora: " + this.getEslora());        
    }
    
}
