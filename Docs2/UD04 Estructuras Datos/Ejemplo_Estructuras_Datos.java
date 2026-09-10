import java.util.Scanner;

public class Ejemplo_Estructuras_Datos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

		int[][] tabla = new int[4][6];
		for(int i=0; i<tabla.length; i++)
			for(int j=0;j<tabla[i].length;j++){
				tabla[i][j] = 5;
				System.println("Almacenando en" + i + "-" + j);
			}
        }

    }
}
