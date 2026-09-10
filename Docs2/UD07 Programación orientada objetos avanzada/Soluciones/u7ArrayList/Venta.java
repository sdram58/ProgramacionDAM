package u7ArrayList;
import java.util.ArrayList;

public class Venta {
    private int id;
    private String fecha;
    private float precioFinal;
    private ArrayList<Articulo> items;
    private ArrayList<Integer> cantidades;
    private static int identificadores = 0;

    public Venta(String fecha){
        this.id = identificadores++;
        this.fecha = fecha;
        this.precioFinal = 0;
        items = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public float getPrecioFinal() {
        return precioFinal;
    }

    public ArrayList<Articulo> getArticulos() {
        return items;
    }

    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }

    public void agregarArticulo(Articulo art, int cantidad) {
        if (art != null && cantidad > 0) {
            items.add(art);
            cantidades.add(cantidad);
            actualizarPrecioFinal();
        }
    }

    private void actualizarPrecioFinal() {
        precioFinal = 0.0f;
        for (int i = 0; i < items.size(); i++) {
            precioFinal += items.get(i).getPVP() * cantidades.get(i);
        }
    }

}
