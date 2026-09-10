public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Juan", "juan@example.com");
        cliente1.guardarTelefono("123456789");
        cliente1.guardarTelefono("987654321");

        Cliente cliente2 = new Cliente("María", "maria@example.com");
        cliente2.guardarTelefono("555555555");

        System.out.println("Teléfono de cliente1 en índice 0: " + cliente1.getTelefono(0));
        System.out.println("Teléfono de cliente2 en índice 0: " + cliente2.getTelefono(0));

        System.out.println("Nombre de cliente con teléfono 987654321: " + buscarClientePorTelefono(cliente1, "987654321"));
    }

    public static String buscarClientePorTelefono(Cliente cliente, String telefono) {
        return cliente.buscarClientePorTelefono(telefono);
    }
}