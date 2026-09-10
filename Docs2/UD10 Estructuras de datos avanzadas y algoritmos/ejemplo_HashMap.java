import java.util.*;

class Persona
{
  private String nombre;
  private String DNI;
  private int edad;

  public Persona(String nombre, String DNI, int edad)
  {
    this.nombre = nombre;
    this.DNI = DNI;
    this.edad = edad;
  }

  public String getNombre()
  {
    return nombre;
  }

  public String getDNI()
  {
    return DNI;
  }
  
  public int getEdad()
  {
    return edad;
  }

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public void setDNI(String DNI)
  {
    this.DNI = DNI;
  }

  public void setEdad(int edad)
  {
    this.edad = edad;
  }
}

class Main {
  public static void main(String[] args) {
    
	HashMap<String,Persona> personas = new HashMap<>();
    
    Persona p1 = new Persona("Antonio","24908124F",25);
    Persona p2 = new Persona("Ana","15452001H",22);
    Persona p3 = new Persona("Pablo","17996241B",28);
    Persona p4 = new Persona("María","54212569T",30);
    
	personas.put("24908124F",p1);
    personas.put("15452001H",p2);
    personas.put("17996241B",p3);
    personas.put("54212569T",p4);

    int edad = personas.get("24908124F").getEdad(); //tomar edad de la 1ª persona
    Persona p = personas.get("24908124F"); //tomar el objeto de la 1ª persona 

    for (HashMap.Entry<String, Persona> elemento: personas.entrySet()) {
      if(elemento.getEdad()>edad) //Si la persona es más mayor, la cojo
        p = personas.get(elemento.getDNI());  
    }
    
    System.out.println("Datos de la persona de mayor edad");
    System.out.println("Nombre: " + p.getNombre() + " - edad: " + elemento.getEdad());
    
  }
}