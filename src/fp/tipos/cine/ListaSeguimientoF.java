package fp.tipos.cine;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import fp.tools.FileTools;

public class ListaSeguimientoF extends ListaSeguimientoA implements ListaSeguimiento {

	// obsérvese que el constructor es privado
	private ListaSeguimientoF(List<PeliculaSeguimiento> peliculas) {
		super(peliculas);
	}

	// Factorías
	public static ListaSeguimiento of(List<PeliculaSeguimiento> peliculas) {
		return new ListaSeguimientoF(peliculas);
	}

	static ListaSeguimiento leeDeFichero(String fichero) {
		return of(FileTools.streamFromFile(fichero).map(linea -> PeliculaSeguimiento.parse(linea)).toList());
	}

	@Override
	public Double marcaMedia() {
		return peliculas.stream().mapToLong(p -> p.marca().toMinutes()).average().orElse(0);
	}

	@Override
	public Integer numPelicDuranMasQue(Integer n) {
		return (int) peliculas.stream().filter(p -> p.pelicula().duracion().toMinutes() > n).count();
	}

	@Override
	public Integer numPelicVistasEnDia(DayOfWeek d) {
		return (int) peliculas.stream().filter(p -> p.ultimaVez().getDayOfWeek().equals(d)).count();
	}

	@Override
	public Double duracionMediaPelicMasAntiguasQue(LocalDate fecha) {
		return peliculas.stream().filter(p -> p.pelicula().fechaEstreno().isBefore(fecha))
				.mapToLong(p -> p.pelicula().duracion().toMinutes()).average().orElse(0);
	}

	@Override
	public Integer duracionRestantePelicDejadasAntesQue(LocalDate fecha) {
		// La idea es: el tiempo restante de una pelicula es la duración - marca
		return (int) peliculas.stream().filter(p -> p.ultimaVez().toLocalDate().isBefore(fecha))
				.mapToLong(p -> p.pelicula().duracion().minus(p.marca()).toMinutes()).sum();
	}

	@Override
	public Integer numPelicFinalizanAntes(LocalTime hora) {
		// La idea es: el instante actual + el tiempo restante de la película debe ser
		// anterior a hora
		return (int) peliculas.stream()
				.filter(p -> LocalTime.now().plus(p.pelicula().duracion().minus(p.marca())).isBefore(hora)).count();
	}
	
	/*************************** Ejercicios finales ******************************************/
	
	//marca media por año de estreno
	public Map<Integer, Double> marcaMediaPorA�oEstreno(){
		return peliculas.stream().collect(Collectors.groupingBy(p -> p.pelicula().fechaEstreno().getYear(), 
				Collectors.averagingLong(p -> p.marca().toMinutes())));
	}
	//num. películas que duran más que n minutos por tipo de metraje
	public Map<TipoMetraje, Integer> numPelicDuranMasQuePorTipo(Integer n){
		return peliculas.stream()
				.filter(p -> p.pelicula().duracion().toMinutes()>n)
				.collect(Collectors.groupingBy(p -> p.pelicula().tipoMetraje(), 
						Collectors.collectingAndThen(Collectors.counting(), l -> l.intValue())
							));
	}
	//lista de películas accedidas por última vez en cada día de la semana
	public Map<DayOfWeek, List<PeliculaSeguimiento>> peliculasPorDiaSemana(){
		return peliculas.stream()
				.collect(Collectors.groupingBy(p -> p.ultimaVez().getDayOfWeek()));
	}
	
	//película más recientemente estrenada por antigüedad
	public Map<Integer, PeliculaSeguimiento> peliculaMasRecientePorAntiguedad(){
		return peliculas.stream()
				.collect(Collectors.groupingBy(p -> p.pelicula().antiguedad(), 
						Collectors.collectingAndThen(Collectors.minBy(Comparator
								.comparing(p -> p.pelicula().fechaEstreno())), 
								Optional::get)
						));
	}
	//duración restante total por día de la semana. 
	//Recuerda duración restante: p -> p.pelicula().duracion().minus(p.marca())
	public Map<DayOfWeek, Duration> duracionRestantePorDia(){ 
		return peliculas.stream()
				.collect(Collectors.groupingBy(p -> p.ultimaVez().getDayOfWeek(), 
						Collectors.reducing(Duration.ZERO, 
								p -> p.pelicula().duracion().minus(p.marca()), (d1,d2)->d1.plus(d2))));
	}
	//Día de la semana con menor duración restante
	public DayOfWeek diaConMenorDuracionRestante() {
		return duracionRestantePorDia().entrySet().stream()
				.min(Entry.comparingByValue())
				.get()
				.getKey();
	}

}

