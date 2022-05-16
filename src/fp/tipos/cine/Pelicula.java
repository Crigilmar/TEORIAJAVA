package fp.tipos.cine;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import fp.tools.Preconditions;

public record Pelicula(String titulo, LocalDate fechaEstreno, Duration duracion) implements Comparable<Pelicula> {

	public LocalTime finalizacion() {
		//a la hora actual, le sumamos la duración. Eso da como resultado otro LocalTime con la hora resultante
		return LocalTime.now().plus(this.duracion);
	}

	public Integer antiguedad() {
		//Calculamos el periodo que hay desde la fecha de estreno, hasta la actualidad (until).
		//Eso da como resultado un objeto de tipo Period (en forma de años en este caso)
		return this.fechaEstreno.until(LocalDate.now()).getYears();
	}
	public Boolean esEnColor() {
		switch (this.tipoMetraje()) {
		case CORTOMETRAJE:
			return this.fechaEstreno.getYear() >= 1932;
		default:
			return this.fechaEstreno.getYear() >= 1935;
		}
	}

	public TipoMetraje tipoMetraje() {
		TipoMetraje res = null;

		res = TipoMetraje.LARGOMETRAJE;
		if (this.duracion.toMinutes() < 30) {
			res = TipoMetraje.CORTOMETRAJE;
		} else if (this.duracion.toMinutes() >= 30 && this.duracion.toMinutes() <= 60) {
			res = TipoMetraje.MEDIOMETRAJE;
		}
		return res;
	}

	public String formatoCorto() {
		return this.titulo + " (" + this.fechaEstreno.getYear() + ")";
	}

	// Factorías
	public static Pelicula of(String titulo, LocalDate fechaEstreno, Duration duracion) {
		Preconditions.checkArgument(!titulo.isBlank(), "El título no puede estar vacío");
		Preconditions.checkArgument(!fechaEstreno.isAfter(LocalDate.now()),
				String.format("La fecha de estreno es %s, y no puede ser posterior a hoy", fechaEstreno));
		Preconditions.checkArgument(!(duracion.isZero() || duracion.isNegative()),
				String.format("La duración es %s, y no puede ser negativa o cero", duracion));
		return new Pelicula(titulo, fechaEstreno, duracion);
	}

	public static Pelicula parse(String txt) {
		String[] partes = txt.split(",");
		if (partes.length != 3) {
			throw new IllegalArgumentException(
					"El formato de entrada debe ser: titulo,fecha de estreno (día-mes-año),duración (minutos)");

		}
		String titulo = partes[0];
		LocalDate fechaEstreno = LocalDate.parse(partes[1], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		Duration duracion = parseDuracion(partes[2]);

		return Pelicula.of(titulo, fechaEstreno, duracion);
	}

	private static Duration parseDuracion(String string) {
		long minutos = Long.parseLong(string);
		return Duration.ofMinutes(minutos);
	}

	// orden natural
	@Override
	public int compareTo(Pelicula p) {
		// orden de evaluación: titulo, fechaEstreno, duracion
		int res = this.titulo.compareTo(p.titulo);
		if (res == 0) {
			res = this.fechaEstreno.compareTo(p.fechaEstreno);
			if (res == 0) {
				res = this.duracion.compareTo(p.duracion);
			}
		}
		return res;
	}

	/******************************************************
	 * tests
	 ********************************************************/
	public static void main(String[] args) {
		Pelicula p = Pelicula.parse("El buen patrón,15-10-2021,120");
		Pelicula p2=Pelicula.parse("El buen patrón,15-10-2021,120");
		System.out.println(p.equals(p2));
		System.out.println(p==p2);

	}

}
