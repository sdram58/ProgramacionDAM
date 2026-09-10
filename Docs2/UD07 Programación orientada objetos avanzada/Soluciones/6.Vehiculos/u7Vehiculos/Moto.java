package u7Vehiculos;
public class Moto extends Terrestre{
    
    private String color;
    
    public Moto (String matricula, String modelo, int numeroRuedas, String color){
        super(matricula, modelo, numeroRuedas);
        this.color = color;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void setTieneAA(String color){
        this.color = color;
    }
    
    @Override
    public void imprimir(){
        super.imprimir();
        System.out.println("Moto de color " + this.getColor());               
    }
}
