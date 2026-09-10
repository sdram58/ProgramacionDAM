import java.util.ArrayList;

public class Cliente {
	
	private String nombre;
	private String email;
	private ArrayList<String> telefonos = new ArrayList<>();
	
	public Cliente(String nombre, String email, String telefono){
		this.nombre = nombre;
		this.email = email;
		this.telefonos.add(telefono);
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void guardarTelefono(String telefono) {
		if(telefonos.contains(telefono))
			System.out.println("ERROR: el teléfono ya existe.");
		else
			telefonos.add(telefono);
	}
	
	public String getTelefono(int indice) {
		return telefonos.get(indice);
	}
	
	public int cantidadtelefonos() {
		return telefonos.size();
	}

}
