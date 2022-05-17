package fp.vacunas.test;

import java.util.Map;
import java.util.Set;

import fp.vacunas.Factoria;
import fp.vacunas.Marca;
import fp.vacunas.Vacunado;
import fp.vacunas.Vacunados;

public class Test {

	public static void main(String[] args) {
		Vacunados vacunados=Factoria.leeFichero("./data/datos covid.txt");
		System.out.println("Mostrando los 5 primeros");
		int i=1;
		for(Vacunado v: vacunados.getVacunados()) {
			System.out.println(v);
			i++;
			if(i==5)
				break;
		}
		//System.out.println("\nTest ejercicio 1:\n"+vacunados.existeUsuarioResidente(Set.of("Sevilla", "Albacete", "Ceuta", "Cuenca")));
		//System.out.println("\nTest ejercicio 2:\n"+vacunados.edadMedia("Sevilla"));
		//System.out.println("\nTest ejercicio 3:\n"+vacunados.marcasPorProvincia());
		System.out.println("\nTest ejercicio 4:\n"+vacunados.UsuariosMayorEdad(Marca.ASTRAZENECA, 5));
		//System.out.println("\nTest ejercicio 5:\n"+vacunados.edadMasJovenPorProvincia());
		//System.out.println("\nTest ejercicio 6:\n");testPorcentajeVacunadosPorMarca(vacunados, 15, 30);
		//System.out.println("\nTest ejercicio 7:\n"+vacunados.fechaConMasDosis(Marca.ASTRAZENECA, "Sevilla"));
	}

	private static void testPorcentajeVacunadosPorMarca(Vacunados vacunados, int i, int j) {
		Map<Marca, Double>res=vacunados.porcentajeVacunadosPorMarca(i, j);
		System.out.println(res);
		Double tot=0.;
		for(Marca m:res.keySet()) {
			tot+=res.get(m);
		}
		System.out.println("Los porcentajes suman: "+tot);
	}

}
