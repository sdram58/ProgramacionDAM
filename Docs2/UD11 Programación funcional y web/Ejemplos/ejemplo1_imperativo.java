import java.util.List;

public class ejemplo2{

    public static void main(String[] args) {

        List<Integer> numeros = List.of(18, 6, 4, 15, 55, 78, 12, 9, 8);

        int result = numeros.stream().filter(num -> num > 10).count();
        System.out.println(result);
    
    }
}