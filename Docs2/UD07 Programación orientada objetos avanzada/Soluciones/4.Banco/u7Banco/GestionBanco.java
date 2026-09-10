package u7Banco;
public class GestionBanco {

    public static void main(String arg[]) {

        // Creamos una cuenta de cada tipo
        CuentaCorriente cc = new CuentaCorriente("ES0085123221321", 1500);
        CuentaAhorro ca = new CuentaAhorro("ES99213982132156", 6000);

        cc.mostrar();
        ca.mostrar();
        System.out.println("");

        // Ingresamos, retiramos y mostramos
        cc.ingresar(1000);
        ca.retirar(500);
        cc.mostrar();
        ca.mostrar();
        System.out.println("");

        // Traspasamos 1000 de cc a ca y mostramos
        cc.traspaso(1000, ca);
        cc.mostrar();
        ca.mostrar();
        System.out.println("");

        // Calculamos intereses y mostramos
        cc.calcularIntereses();
        ca.calcularIntereses();
        cc.mostrar();
        ca.mostrar();
    }
}
