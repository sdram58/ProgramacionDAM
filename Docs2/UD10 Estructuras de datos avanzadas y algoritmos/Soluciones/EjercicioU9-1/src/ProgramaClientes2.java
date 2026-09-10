import java.util.ArrayList;
import java.util.Scanner;

public class ProgramaClientes {
	
	public static String buscarClientePorTelefono(String telefono, ArrayList<Cliente> listado) {
		for (Cliente cli: listado) {
			for(int i=0;i<cli.cantidadtelefonos();i++)
				if(cli.getTelefono(i).equals(telefono))
					return cli.getNombre();
		}
		
		return "Ningún cliente coincide con ese teléfono...";
	}

	public static void main(String[] args) {
		ArrayList<Cliente> clientes = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String telef, nom;
		
		clientes.add(new Cliente("Paco garcía","paco@garcia.com","652335405"));
		clientes.add(new Cliente("Ana López","ana@lopez.com","123456789"));
		clientes.add(new Cliente("Janderpeiser","jander@peiser.com","909009999"));
		
		System.out.println("Introduzca el teléfono del cliente a buscar...");
		
		telef = sc.nextLine();
		
		nom = buscarClientePorTelefono(telef, clientes);
		System.out.println(nom);
		sc.close();

	}

}
