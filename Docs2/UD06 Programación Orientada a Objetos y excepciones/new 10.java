public static int contador(int x, int y){
	Scanner entrada = new Scanner(System.in);
	int num, c = 0;
	if(x > 0 && y > 0){
		System.out.println("\tEscribe un número:");
		num = entrada.nextInt();
		if( num >= x && num <= y){
			System.out.println("\tNúmero en el rango.");
			c++;
		}
		else
			System.out.println("\tNúmero fuera de rango.");
	}
	else
		c = -1;
	entrada.close();
	return c;
} 


public class Coche{
	
	String matricula;
	
	public Coche(String mat){
		matricula = mat;
	}
	
	public void setMatricula(String mat){
		if(validar(mat))
			matricula = mat;
		else
			System.out.println("Error");		
	}
}


public class Calculadora {

	public double raizcuad(double x) {
		// implementación desconocida
		return resultado;
	}
	//Más métodos que no es necesario conocer
} // fin clase

@Test
public void testRaizcuad() {
	Calculadora calc = new Calculadora();
	double valor = calc.raizcuad(9); 
	
}

/**
 * Una clase para representar círculos sobre el plano.
 * Cada círculo queda determinado el radio y su centro.
 * @version 1.0, 25/02/2022
 * @author Antonio
 */

public class Círculo {
    protected double x,y; // coordenadas del centro
    protected double r;  // radio del círculo
    
    /** 
     * Crea un círculo a partir de su origen su radio.
     * @param x La coordenada x del centro del círculo.
     */
    public Circulo(double x, double y, double r) {
        this.x=x; this.y = y; this.r = r;
    }   
    
    /** 
     * Cálculo del área de este círculo.
     */
     public double area() {
		 try{
			return Math.PI*r*r;
		 }catch(NumberFormatException e){
			 e.printStackTrace();
		 }
     }
     
     /** 
      * Muestra el volumen de la esfera equivalente
      * @param px componente x del punto
      * @param py componente y del punto
      * @return el volumen de la esfera equivalente
      */
     public void mostrarVolumen(){
		double vol = 4/3.0f*Math.PI*Math.pow(r,3);
        System.out.println("El volumen es: " + vol);
		
     }
}

assertEquals(3,valor);




Coche miCoche1 = new Coche();
Coche miCoche2 = new Coche();
 
// Escribir objetos en el fichero

// Puedo almacenar objetos String
serializador.writeObject("Guardo 2 objetos Coche");

// u objetos tipo Coche
serializador.writeObject(miCoche1);
serializador.writeObject(miCoche2);
. . .

serializador.close(); 
// Finalmente se cierran los flujos de salida, cerrando el más externo.


File fichero = new File("fichero.bin");
FileOutputStream flujoFichero = new FileOutputStream(fichero);
ObjectOutputStream serializador = new ObjectOutputStream(flujoFichero);




// Leemos el contenido del fichero
System.out.println("... Leemos el contenido del fichero ...");
s = new Scanner(fichero);

// Leemos linea a linea el fichero
while (s.hasNextLine()) {
	String linea = s.nextLine();    // Guardamos la linea en un String
	System.out.println(linea);      // Imprimimos la linea
}



//Indicando true en el constructor de FileWriter, añadimos texto
File fichero = new File("fichero.txt");
FileWriter filewriter = new FileWriter(fichero, true);
PrintWriter escritor = new PrintWriter(filewriter);
 
//o sin el objeto File
FileWriter filewriter = new FileWriter("fichero.txt", true);
PrintWriter escritor = new PrintWriter(filewriter);



String sentenciaSql = "UPDATE productos SET precio = ? WHERE nombre = ?";
PreparedStatement sentencia = null;
 
try {
  sentencia = conexion.prepareStatement(sentenciaSql);
  sentencia.setFloat(1, precioProducto);
  sentencia.setString(2, nombreProducto);
  sentencia.executeUpdate();
} catch (SQLException sqle) {
  sqle.printStackTrace();
} finally {
  if (sentencia != null)
    try {
      sentencia.close();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
}
























	
	
