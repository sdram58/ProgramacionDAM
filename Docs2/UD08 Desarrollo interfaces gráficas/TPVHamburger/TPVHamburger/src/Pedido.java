public class Pedido {
    private int idpedido;
    private boolean ternera;
    private boolean vegana;
    private boolean centeno;
    private boolean gajo;
    private boolean hdoble;
    private boolean queso;
    private boolean patatas;
    private int ketchup;
    private int barbacoa;
    private int mostaza;
    private float adicional;
    private boolean domicilio;
    private boolean descuento;
    private float pbruto;
    private float iva;
    private float pvp;
    
    public Pedido(int idpedido, boolean ternera, boolean vegana, boolean centeno, boolean gajo, boolean hdoble, boolean queso, boolean patatas, int ketchup, int barbacoa,
            int mostaza, float adicional, boolean domicilio, boolean descuento, float pbruto, float iva, float pvp){
        this.idpedido = idpedido;
        this.ternera = ternera;
        this.vegana = vegana;
        this.centeno = centeno;
        this.gajo = gajo;
        this.hdoble = hdoble;
        this.queso = queso;
        this.patatas = patatas;
        this.ketchup = ketchup;
        this.barbacoa = barbacoa;
        this.mostaza = mostaza;
        this.adicional = adicional;
        this.domicilio = domicilio;
        this.descuento = descuento;
        this.pbruto = pbruto;
        this.iva = iva;
        this.pvp = pvp;        
    }
}
