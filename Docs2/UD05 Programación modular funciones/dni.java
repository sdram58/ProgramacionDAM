import java.util.Scanner;

class Main {

  public static boolean ValidarNIF(String nif){
    char comprobacion[] = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};

    if(nif.length()!=9)
      return false;

    int numero = Integer.parseInt(nif.substring(0,8));
    char letra = nif.charAt(8);

    if(numero < 0)
      return false;
    
    if(comprobacion[numero%23]==letra)
      return true;
    else
      return false;
  }
  
  /*
  public static char letraDni(String num){
	  char letras[] = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
	  int numero = Integer.parseInt(nif.substring(0,8));
	  
	  return letras[numero%23];  
  }*/

  public static void main(String[] args) {
    String nif;
    Scanner sc = new Scanner(System.in);
    System.out.println("Introduce DNI: ");
    nif = sc.nextLine();

    System.out.println("El DNI introducido es: " + ValidarNIF(nif));
	//System.out.println("La letra es: " + letraDni(nif));
    
    sc.close();
  }
}