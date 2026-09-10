package clinicaVeterinaria;
import java.util.*;

public class Factura {

    private int idFactura;
    private float importe;
    private boolean pagada;
    private String fechaFactura;
    private Actuacion actuacion;
    private Cliente cliente;

    public Factura(int idFactura, float importe, boolean pagada, String fechaFactura, Actuacion actuacion,
            Cliente cliente) {
        this.idFactura = idFactura;
        this.importe = importe;
        this.pagada = pagada;
        this.fechaFactura = fechaFactura;
        this.actuacion = actuacion;
        this.cliente = cliente;
    }


}