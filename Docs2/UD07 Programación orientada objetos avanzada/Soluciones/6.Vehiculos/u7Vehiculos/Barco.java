package u7Vehiculos;

public class Barco extends Acuatico {
    
    private boolean tieneMotor;
    
    public Barco(String matricula, String modelo, double eslora, boolean tieneMotor){
        super(matricula, modelo, eslora);
        this.tieneMotor = tieneMotor;
    }
    
    public boolean getTieneMotor(){
        return this.tieneMotor;
    }
    
    public void setTieneMotor(boolean tieneMotor){
        this.tieneMotor = tieneMotor;
    }    
    
    @Override
    public void imprimir(){
        super.imprimir();
        
        if(this.tieneMotor){
            System.out.println("Barco con motor");
        }else{
            System.out.println("Barco sin motor");
        }        
    }      
}
