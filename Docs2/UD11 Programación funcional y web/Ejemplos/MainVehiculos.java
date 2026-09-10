import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
public class MainVehiculos {
 
    public static void main(String[] args) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo("1111AAA", "BMW", 10000));
        vehiculos.add(new Vehiculo("2222BBB", "AUDI", 20000));
        vehiculos.add(new Vehiculo("3333CCC", "OPEL", 30000));
        vehiculos.add(new Vehiculo("4444DDD", "BMW", 100000));
        vehiculos.add(new Vehiculo("5555EEE", "AUDI", 200000));
        vehiculos.add(new Vehiculo("6666FFF", "OPEL", 300000));
        vehiculos.add(new Vehiculo("7777GGG", "CITROEN", 0));
    }
 
}