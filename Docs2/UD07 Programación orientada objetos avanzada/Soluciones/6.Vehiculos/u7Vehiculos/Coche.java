package u7Vehiculos;

public class Coche extends Terrestre {
    
    private boolean tieneAA;
    
    public Coche (String matricula, String modelo, int numeroRuedas, boolean tieneAA){
        super(matricula, modelo, numeroRuedas);
        this.tieneAA = tieneAA;
    }
    
    public boolean getTieneAA(){
        return this.tieneAA;
    }
    
    public void setTieneAA(boolean tieneAA){
        this.tieneAA = tieneAA;
    }
    
    @Override
    public void imprimir(){
        super.imprimir();
        
        if(this.tieneAA){
            System.out.println("Coche con aire accondicionado");
        }else{
            System.out.println("Coche sin aire acondicionado");
        }       
    }    
}
