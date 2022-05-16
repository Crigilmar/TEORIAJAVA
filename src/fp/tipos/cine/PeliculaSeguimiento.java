package fp.tipos.cine;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fp.tools.Preconditions;

public record PeliculaSeguimiento(Pelicula pelicula, LocalDateTime ultimaVez, Duration marca) {
	/*
	 * Propiedades:
	 * - pelicula
	 * - ultimaVez que se accedió a la película
	 * - marca, por dónde va la película
	 */

	public static PeliculaSeguimiento of(Pelicula pelicula, LocalDateTime ultimaVez, Duration marca) {
		Preconditions.checkArgument(ultimaVez.isBefore(LocalDateTime.now()),
				"La fecha y la hora del último acceso debe ser anterior al momento actual");
		Preconditions.checkArgument(marca.toSeconds()<=pelicula.duracion().toSeconds(),
				"La marca debe ser menor o igual que la duración");
		return new PeliculaSeguimiento(pelicula, ultimaVez, marca);
	}
	public static PeliculaSeguimiento parse(String txt) {
		//titulo,fecha de estreno (día-mes-año),duración (minutos),ultimo acceso (día-mes-año hora:minutos),marca (horas:minutos:segundos)
		String[] partes = txt.split(",");
		if(partes.length!=5) {
			throw new IllegalArgumentException("formato: titulo,fecha de estreno (día-mes-año),duración (minutos),ultimo acceso (día-mes-año hora:minutos),marca (horas:minutos:segundos)");
		}
		Pelicula pelicula=Pelicula.parse(partes[0]+","+partes[1]+","+partes[2]);
		LocalDateTime ultimaVez=LocalDateTime.parse(partes[3], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		Duration marca=formateaMarca(partes[4]);
		return of(pelicula, ultimaVez, marca);
	}

	
	//esta es el parseo del formato Marca.
	private static Duration formateaMarca(String txt) {
		String[] partes=txt.split(":");
		if(partes.length!=3) {
			throw new IllegalArgumentException("Formato: H:m:s");
		}
		Integer h=Integer.parseInt(partes[0]);
		Integer m=Integer.parseInt(partes[1]);
		Integer s=Integer.parseInt(partes[2]);
		return Duration.ofSeconds(h*3600+m*60+s);
	}
	@Override
	public String toString() {
		//titulo,fecha de estreno (día-mes-año),duración (minutos)
		//,ultimo acceso (día-mes-año hora:minutos),marca (horas:minutos:segundos)
		return pelicula.titulo()+","+pelicula.fechaEstreno().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				+","+pelicula.duracion().toMinutes()+","+ultimaVez.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
				+","+marca.toHours()+":"+marca.toMinutesPart()+":"+marca.toSecondsPart();
	}
	public static void main(String[] args) {
		//titulo,fecha de estreno (día-mes-año),duración (minutos),ultimo acceso (día-mes-año hora:minutos),marca (horas:minutos:segundos)
		System.out.println(PeliculaSeguimiento.parse(""));
	}
}
