package fp.tipos.cine;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import fp.tools.FileTools;

public class ListaSeguimientoI extends ListaSeguimientoA implements ListaSeguimiento {

	//obsérvese que el constructor es privado
	private ListaSeguimientoI(List<PeliculaSeguimiento> peliculas) {
		super(peliculas);
	}

	//Factorías
	public static ListaSeguimiento of(List<PeliculaSeguimiento> peliculas) {
		return new ListaSeguimientoI(peliculas);
	}

	 public static ListaSeguimiento leeDeFichero(String fichero) {
		List<PeliculaSeguimiento>l=new ArrayList<>();
		for(String linea:FileTools.linesFromFile(fichero)) {
			l.add(PeliculaSeguimiento.parse(linea));
		}
		return of(l);
	}

	/*************************** métodos ********************/

	@Override
	public Double marcaMedia() {
		//devuelve la duración media en minutos de visionado (la marca) en las películas de la lista
		long total=0L;
		for(PeliculaSeguimiento p:peliculas) {
			total+=p.marca().toMinutes();
		}
		if(peliculas.size()>0)
			return total*1.0/peliculas.size();
		else
			return 0.;
	}
	@Override
	public Integer numPelicDuranMasQue(Integer n) {
		//devuelve el número de películas que duran más que n minutos
		int res=0;
		for(PeliculaSeguimiento p:peliculas) {
			if(p.pelicula().duracion().toMinutes()>n) {
				res++;
			}
		}
		return res;
	}
	@Override
	public Integer numPelicVistasEnDia(DayOfWeek d) {
		//devuelve el número de películas que se han visto por última vez un día de la semana concreto
		int res=0;
		for(PeliculaSeguimiento p:peliculas) {
			if(p.ultimaVez().getDayOfWeek().equals(d)) {
				res++;
			}
		}
		return res;
	}
	@Override
	public Double duracionMediaPelicMasAntiguasQue(LocalDate fecha) {
		//devuelve la duración media (en minutos) de las películas estrenadas antes que fecha
		long res=0L;
		int n=0;
		for(PeliculaSeguimiento p:peliculas) {
			if(p.pelicula().fechaEstreno().isBefore(fecha)) {
				res+=p.pelicula().duracion().toMinutes();
				n++;
			}
		}
		if(n>0)
			return res*1.0/n;
		else
			return 0.;
	}
	@Override
	public Integer duracionRestantePelicDejadasAntesQue(LocalDate fecha) {
		//Devuelve el tiempo restante total (en minutos) de las películas vistas por última vez antes de fecha
		Long res=0L;
		for(PeliculaSeguimiento p:peliculas) {
			if(p.ultimaVez().toLocalDate().isBefore(fecha)) {
				Duration restante=p.pelicula().duracion().minus(p.marca());
				res+=restante.toMinutes();
			}
		}
		return res.intValue();
	}
	@Override
	public Integer numPelicFinalizanAntes(LocalTime hora) {
		//Devuelve el número de películas que finalizan antes que hora, tomando como referencia el momento actual
		int res=0;
		for(PeliculaSeguimiento p:peliculas) {
			Duration restante=p.pelicula().duracion().minus(p.marca());
			if(LocalTime.now().plus(restante).isBefore(hora)) {
				res++;
			}
		}
		return res;
	}
	
	
	/********************************adicional*******************************************************/
	/*
	 * Guardar mayor de n y guardarlo en un sorted
	 * */
	public SortedSet<PeliculaSeguimiento>getPelicDuraMasQue(Integer n){
		
		/** criterio alternativo de orden en PeliculasSeguimiento : por Marca*/
		//criterio alternativo
		Comparator<PeliculaSeguimiento> cmp = Comparator.comparing(p -> p.marca());
		//cmp.thenComparing(p -> p.ultimaVez()); //varias condiciones 
		SortedSet<PeliculaSeguimiento>res = new TreeSet<>(cmp);
		for(PeliculaSeguimiento p:peliculas) {
			if(p.pelicula().duracion().toMinutes()>n){
				res.add(p);}
			}
		return res ;
	}
	
	//estilo funcional 
	
	public SortedSet<PeliculaSeguimiento>getPelicDuraMasQue1(Integer n){
		Comparator<PeliculaSeguimiento> cmp = Comparator.comparing(p -> p.marca());
		return this.peliculas.stream().filter(p -> p.pelicula().duracion().toMinutes()>n).collect(Collectors.toCollection(() -> new TreeSet<>(cmp)));} // () parametros de entrada 
	
	
	
	
	/*
	 * Diccionario 
	 * */
	
	public Map<LocalDate,List<PeliculaSeguimiento>> agruparPorFechaAcceso(){
		Map<LocalDate,List<PeliculaSeguimiento>> m = new HashMap<>();
		for(PeliculaSeguimiento p : this.peliculas) {
			LocalDate clave = p.ultimaVez().toLocalDate();
			if(!m.containsKey(clave)){
				List<PeliculaSeguimiento> valorAsociado = new ArrayList<>();
				m.put(clave, valorAsociado);}
			m.get(clave).add(p);}
		return m;
	}
	
	//numero de peliculas por fecha de estreno 
	
	public Map<LocalDate , Integer>getNumPelPorFecha(){
	Map<LocalDate,Integer> m = new HashMap<>();
	for(PeliculaSeguimiento p : this.peliculas) {
		LocalDate clave1 = p.pelicula().fechaEstreno();
		if(!m.containsKey(clave1));{
			List<PeliculaSeguimiento>valorAsociado = new ArrayList<>();}
		}
	return m;
	}
}





