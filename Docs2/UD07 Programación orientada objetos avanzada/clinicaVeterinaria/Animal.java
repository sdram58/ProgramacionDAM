package clinicaVeterinaria;
import java.util.*;

public class Animal {

    private String especie;
    private String raza;
    private String nombre;
    private String fechaNac;
    private String sexo;
    private Cliente duenyo;
    private List<Actuacion> actuaciones;

    public Animal(String especie, String raza, String nombre, String fechaNac, String sexo, Cliente duenyo) {
        this.especie = especie;
        this.raza = raza;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.duenyo = duenyo;
        this.actuaciones = new ArrayList<>();
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public Cliente getDuenyo() {
        return duenyo;
    }

    public List<Actuacion> getActuaciones() {
        return actuaciones;
    }

    

}