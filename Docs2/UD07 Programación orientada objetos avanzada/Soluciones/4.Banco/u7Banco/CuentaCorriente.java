package u7Banco;
public class CuentaCorriente extends CuentaBancaria {

    public CuentaCorriente(String num, double saldo) {
        super(num, saldo);
    }

    // Calcula intereses y los ingresa en la cuenta
    @Override
    public void calcularIntereses() {
        double interes = this.getSaldo() * (interesAnualBasico / 100);
        this.ingresar(interes);
    }

    public void mostrar() {
        System.out.println("Cuenta Corriente - IBAN: " + this.getIban() + " Saldo: " + this.getSaldo());
    }
}
