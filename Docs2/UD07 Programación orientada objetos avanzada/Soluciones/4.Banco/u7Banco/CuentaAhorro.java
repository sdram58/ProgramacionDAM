package u7Banco;
public class CuentaAhorro extends CuentaBancaria {

    final double saldoMinimo = 3000;

    public CuentaAhorro(String iban, double saldo) {
        super(iban, saldo);
    }

    // Calcula intereses y los ingresa en la cuenta
    @Override
    public void calcularIntereses() {
        double interes;
        if (this.getSaldo() < saldoMinimo) {
            interes = this.getSaldo() * ((interesAnualBasico / 2.0) / 100.0);
        } else {
            interes = this.getSaldo() * ((interesAnualBasico * 2.0) / 100.0);
        }
        this.ingresar(interes);
    }

    public void mostrar() {
        System.out.println("Cuenta Ahorro - IBAN: " + this.getIban() + " Saldo: " + this.getSaldo());
    }
    
}
