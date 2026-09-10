import java.util.List;

public class ejemplo2{

    public static void main(String[] args) {

        List<Integer> numeros = List.of(18, 6, 4, 15, 55, 78, 12, 9, 8);
   
        numeros.stream().filter(p -> p >= 18).map(p -> p).forEach(p -> System.out.println(p));
    }
}