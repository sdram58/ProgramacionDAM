import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;

public class MainSolucion {

	public static void main(String[] args) {
		List<Curso> cursos = new ArrayList<>();
		cursos.add(new Curso("Cursos profesional de Java", 6.5f, 50, 200 ));
		cursos.add(new Curso("Cursos profesional de Python", 8.5f, 60, 800 ));
		cursos.add(new Curso("Cursos profesional de Oracle", 4.5f, 70, 700 ));
		cursos.add(new Curso("Cursos profesional de Android", 7.5f, 10, 400 ));
		cursos.add(new Curso("Cursos profesional de Procrastinación", 1.5f, 10, 300 ));
		
		//Ejercicio 1: obtener la cantidad de cursos con una duración mayor a 5 horas.
		long cant = cursos.stream().filter(c -> c.getDuracion() > 5.0f).count();
		System.out.println(cant);
		
		//Ejercicio 2: obtener la cantidad de cursos con una duración menor a 2 horas.
		long cant2 = cursos.stream().filter(c -> c.getDuracion() < 2.0f).count();
		System.out.println(cant2);
		
		//Ejercicio 3: listar el título de todos aquellos cursos con una cantidad de vídeos mayor a 50
		List<String> titulosvidmay50 = cursos.stream()
				.filter(c -> c.getVideos() > 50)
				.map(c -> c.getTitulo())
				.collect(Collectors.toList());
		
		for(String tit: titulosvidmay50)
			System.out.println(tit);

		//Otra solución del 3. Versión SUPER ÓPTIMA sin generar estructura de datos adicional
		cursos.stream()
				.filter(c -> c.getVideos() > 50)
				.map(c -> c.getTitulo())
				.forEach(t -> System.out.println(t));
		
		//Ejercicio 4: mostrar en consola el título de los 3 cursos con mayor duración.
		cursos.stream()
				.sorted(Comparator.comparing(Curso::getDuracion).reversed())
				.limit(3)
				.forEach(c -> System.out.println(c.getTitulo()));
		
		//Ejercicio 4: otra versión mapeando sólo los nombres
		cursos.stream()
				.sorted(Comparator.comparing(Curso::getDuracion).reversed())
				.map(c -> c.getTitulo())
				.limit(3)
				.forEach(c -> System.out.println(c));
			
		//Ejercicio 5: mostrar en consola la duración total de todos los cursos. 
		double suma = cursos.stream()
				.mapToDouble(c -> c.getDuracion())
				.sum();
	
		System.out.println(suma);
		
		//Ejercicio 6: mostrar en consola la duración de todos aquellos cursos que tengan una cantidad de alumnos inscritos menor a 500.
		cursos.stream()
				.filter(c -> c.getAlumnos()<500)
				.map(c -> c.getDuracion())
				.forEach(d -> System.out.println(d));
				
		//Ejercicio 7: obtener el curso con mayor duración.
		cursos.stream()
				.sorted(Comparator.comparing(Curso::getDuracion).reversed())
				.limit(1)
				.map(c -> c.getTitulo())
				.forEach(c -> System.out.println(c));
		
		//Ejercicio 8: crear una lista de Strings con todos los titulos de los cursos.
		List<String> titulos = cursos.stream()
				.map(c -> c.getTitulo())
				.collect(Collectors.toList());
		
		for(String titulo: titulos)
			System.out.println(titulo);
		
	}

}
