package fp.tipos.cine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ListaSeguimiento {

	/*
	 *  Otra opción es poner la selección de implementación en la interfaz, y poner dos factorías of
	 *  una con una implementación por defecto, y la otra con un segundo parámetro con el tipo
	 */
	public static enum TipoImplementacion {
		IMPERATIVA, FUNCIONAL
	}


	public static ListaSeguimiento of(List<PeliculaSeguimiento> peliculas, TipoImplementacion tipo) {
		return switch (tipo) {
		case IMPERATIVA -> ListaSeguimientoI.of(peliculas);
		default -> throw new IllegalArgumentException("Unexpected value: " + tipo);
		};
	}

	public static ListaSeguimiento of(List<PeliculaSeguimiento> peliculas) {
		return of(peliculas,TipoImplementacion.FUNCIONAL);
	}


	public static ListaSeguimiento leeDeFichero(String fichero, TipoImplementacion tipo) {
		return switch(tipo) {
		case IMPERATIVA -> ListaSeguimientoI.leeDeFichero(fichero);
		default -> throw new IllegalArgumentException("Unexpected value: " + tipo);
		};
	}
	public static ListaSeguimiento leeDeFichero(String fichero) {
		return leeDeFichero(fichero, TipoImplementacion.FUNCIONAL);
	}
	/**************************  métodos ********************************/
	/**devuelve la duración media en minutos de visionado (la marca) en las películas de la lista*/
	public Double marcaMedia();
	/** devuelve el número de películas que duran más que n minutos */
	public Integer numPelicDuranMasQue(Integer n);
	/** devuelve el número de películas que se han visto por última vez un día de la semana concreto*/
	public Integer numPelicVistasEnDia(DayOfWeek d);
	/**devuelve la duración media (en minutos) de las películas estrenadas antes que fecha*/
	public Double duracionMediaPelicMasAntiguasQue(LocalDate fecha);
	/**Devuelve el tiempo restante total (en minutos) de las películas vistas por última vez antes de fecha*/
	public Integer duracionRestantePelicDejadasAntesQue(LocalDate fecha);
	/**Devuelve el número de películas que finalizan antes que hora, tomando como referencia el momento actual*/
	public Integer numPelicFinalizanAntes(LocalTime hora);
	
	
	

}
