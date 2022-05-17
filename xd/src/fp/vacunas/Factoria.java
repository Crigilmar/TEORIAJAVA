package fp.vacunas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class Factoria {
	public static Vacunados leeFichero(String nombreFichero) {

		Stream<Vacunado> str = Stream.empty();
		try {
			str = Files.lines(Paths.get(nombreFichero), StandardCharsets.UTF_8).skip(1)
					.map(linea -> parse(linea));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Vacunados(str);
	
	}

	private static Vacunado parse(String linea) {
		// usuario	fechaNacimiento	provincia	marca	fechaAdministracion	pautaCompleta
		String[] partes=linea.split(",");
		String usuario=partes[0].trim();
		LocalDate fechaNacimiento=LocalDate.parse(partes[1].trim(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		String provincia=partes[2].trim();
		Marca marca=Marca.valueOf(partes[3].trim());
		LocalDate fechaAdministracion = LocalDate.parse(partes[4].trim(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		Boolean pautaCompleta=partes[5].trim().equals("true");
		
		return new Vacunado(usuario, fechaNacimiento, provincia, marca, fechaAdministracion, pautaCompleta);
	}
}
