package fp.tipos.cine;

public class TestPeliculaSeguimiento {
	public static void main(String[] args) {

		PeliculaSeguimiento pelicula = PeliculaSeguimiento.parse("El buen patrón,15-10-2021,120,");		
		System.out.println(pelicula.toString());
		
	}

}
