package fp.vacunas;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vacunados {
	private Set<Vacunado> vacunados;

	public Vacunados(Set<Vacunado> vacunados) {
		this.vacunados = new HashSet<>(vacunados);
	}

	public Vacunados(Stream<Vacunado> str) {
		this.vacunados = str.collect(Collectors.toSet());
	}

	public Set<Vacunado> getVacunados() {
		return new HashSet<>(vacunados);
	}

	/********************************
	 * Ejercicios
	 ****************************************/
	/*
	 * Dado un conjunto de provincias, ¿existe algún usuario residente en alguna de
	 * esas provincias que se haya vacunado el día de su cumpleaños? (1 punto)
	 */
	public Boolean existeUsuarioResidente(Set<String> provincias) {
		return null;
		return this.vacunados.stream().filter(v -> provincias.contains(v.provincia())).anyMatch(v -> v.fechaAdministracion().getDayOfMonth() == v.fechaNacimiento().getMonth().equals(v.fechaNacimiento().getMonth())))
		//return vacunados.stream().filter(p -> p.provincia() == provincias).collect(Collectors.groupingBy(p -> p.fechaNacimiento()));
	}

	/*
	 * Calcular la edad media de vacunación de los usuarios de una provincia dada
	 * que tienen la pauta completa de vacunación. Si no se puede calcular la media,
	 * se debe lanzar la excepción NoSuchElementException (1 punto)
	 */

	public Double edadMedia(String provincia) {
		return this.vacunados.stream().filter(v -> v.provincia().equals(provincia) && v.pautaCompleta()).mapToInt(Vacunado::edad).average().orElseThrow(()-> new NoSuchElementException());
		//return this.vacunados.stream().collect(Collectors.groupingBy(p -> p.provincia(),Collectors.averagingLong(p -> p.pautaCompleta())))
	}

	/*
	 * Obtener un Map que relacione cada provincia con el conjunto de las marcas de
	 * vacunas administradas en la provincia. Realizar este ejercicio con bucles (1
	 * punto)
	 */
	public Map<String, Set<Marca>> marcasPorProvincia() {
		Map<String,Set<Marca>> m = new HashMap();
		for(Vacunado v:this.vacunados){
			String k = v.provincia();
			if(!m.containsKey(k)){m.put(k, new HashSet<>());
			}
			m.get(k).add(v.marca());
		}
		return m;
		//return this.vacunados.stream().collect(Collectors.groupingBy(Vacunado :: provincia,Collectors.mapping(Vacunado::marca,Collectors,toset()));
	}

	/*
	 * Obtener una lista con los nombres de los n usuarios de mayor edad vacunados
	 * completamente con una marca dada, de mayor a menor edad. En caso de haber dos
	 * usuarios con la misma edad, se tomará primero el que haya sido vacunado más
	 * recientemente (1,5 puntos)
	 */
	public List<String> UsuariosMayorEdad(Marca m, Integer n) {
		return this.vacunados.stream().filter(v -> v.marca().equals(m) && v.pautaCompleta()).sorted(Comparator.comparing(Vacunado::edad).reversed().thenComparing(Comparator.comparing(Vacunado::fechaAdministracion).reversed())).limit(n).map(Vacunado::usuario).toList();
		//return (List<String>) this.vacunados.stream().filter(p -> p.pautaCompleta());
	}

	/*
	 * Obtener un Map que relacione cada provincia con la edad del usuario más joven
	 * vacunado completamente en dicha provincia. Si hay dos usuarios con la misma
	 * fecha de nacimiento, se tomará el anterior según el orden natural del tipo
	 * base Vacunado (1,5 puntos)
	 */
	public Map<String, Optional<Vacunado>> edadMasJovenPorProvincia() {
		return null;
		//return this.vacunados.stream().collect(Collectors.groupingBy(Vacunado::provincia,Collectors.minBy(Comparator.comparing(Vacunados::edad)), opt -> opt.get().edad());
		//return this.vacunados.stream().min(Comparator.comparing(Provincia::Edad));
	}

	/*
	 * Dados dos enteros que representan un rango de edad (se suponen correctamente
	 * ordenados, por lo que no es necesario chequearlos), obtener un Map que
	 * relacione cada marca de vacuna con el porcentaje de usuarios de dicho rango
	 * de edad (incluidos los extremos del rango) que han sido vacunados con dicha
	 * marca respecto al total de usuarios de ese rango de edad que han sido
	 * vacunados (sin importar si lo han sido completamente o no). Nota: debe evitar
	 * que se produzca una división por cero, devolviendo null en tal caso (2
	 * puntos)
	 */
	public Map<Marca, Double> porcentajeVacunadosPorMarca(Integer min, Integer max){
		Integer total = (int) this.vacunados.stream().filter(v -> min <= v.edad() && v.edad() <= max).count();
		return vacunados.stream().filter(v -> min <= v.edad() &&  v.edad() <= max).collect(Collectors.groupingBy(Vacunado::marca,Collectors.collectingAndThen(Collectors.counting(), r -> r*100.0/ total)));
	}
	/*
	 * Obtener la fecha en la que se administraron más dosis de una marca dada en una provincia dada. Si no 
		existe esta fecha, se lanzará una excepción de tipo NoSuchElementException (2 puntos)
	 */
	public LocalDate fechaConMasDosis(Marca m, String provincia) {
		return null;
	}

	

}
