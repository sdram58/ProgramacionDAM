import java.util.Scanner;

public class Persona {
	
	private String nombre;
	private String apellidos;
	private int edad;
	private char sexo;
	private int peso;
	private int altura;
	private String dni;
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setApellidos(String ape) {
		apellidos = ape;
	}
	
	public void setEdad(int age) {
		edad = age;
	}
	
	public void setSexo(char gender) {
		sexo = gender;
	}	
	
	public void setPeso(int pes) {
		peso = pes;
	}
	
	public void setAltura(int alt) {
		altura = alt;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public int getEdad() {
		return edad; 
	}
	
	public char getSexo() {
		return sexo;
	}	
	
	public int getPeso() {
		return peso;
	}
	
	public int getAltura() {
		return altura;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void mostrar() {
		System.out.println("------------ DATOS PERSONALES -----------");
		System.out.println("Nombre: " + getNombre());
		System.out.println("Apellidos: " + apellidos);
		System.out.println("Edad: " + edad);
		System.out.println("Sexo: " + ((sexo=='H')?"Hombre":"Mujer"));
		System.out.println("Peso: " + peso);
		System.out.println("Altura: " + altura);
		System.out.println("DNI: " + dni);
		System.out.println("-----------------------------------------");
	}
	
	public boolean esMayorEdad() {
		return (edad>=18);
	}
	
	public void saludar() {
		System.out.println("Hola, me llamo: " + nombre + " " + apellidos);
	}
	
	public int calcularIMC(){
		double imc = peso/Math.pow((altura*0.01), 2);
		
		if(imc<20)
			return -1;
		else if(imc<=25)
			return 0;
		else return 1;		
	}
	
	public void generarDNI(){
		int numdni = (int)Math.floor((Math.random()*99999999));
		char[] letraDni = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};  		
		String newdni = String.valueOf(numdni)+letraDni[numdni%23];
		dni = newdni;		
	}

	public static void main(String[] args) {
		Scanner leer = new Scanner(System.in);
		Persona personas[] = new Persona[4];
		
		for(int i=0;i<4;i++){
			personas[i] = new Persona();
			System.out.println("Persona: " + (i+1));
			System.out.println("Introduce el nombre: ");
			personas[i].setNombre(leer.nextLine());
			System.out.println("Introduce apellidos: ");
			personas[i].setApellidos(leer.nextLine());
			System.out.println("Introduce edad: ");
			personas[i].setEdad(Integer.parseInt(leer.nextLine()));
			System.out.println("Introduce sexo: ");
			personas[i].setSexo(leer.nextLine().charAt(0));
			System.out.println("Introduce peso: ");
			personas[i].setPeso(Integer.parseInt(leer.nextLine()));
			System.out.println("Introduce altura: ");
			personas[i].setAltura(Integer.parseInt(leer.nextLine()));
			System.out.println("Generando DNI válido...");
			personas[i].generarDNI();			
			System.out.println("-----------------------");
		}
		
		for(int i=0;i<4;i++) {
			personas[i].mostrar();
		}
		
		for(int i=0;i<4;i++) {
			personas[i].saludar();
			
			int imc = personas[i].calcularIMC();
			System.out.print("Cálculo del Índice de Masa Corporal: ");
			if(imc==-1)
				System.out.println("ERROR en IMC");
			else if(imc==0)
				System.out.println("Por debajo del peso ideal.");
			else
				System.out.println("Sobrepeso");
		}
		
	leer.close();
	}
	
}



