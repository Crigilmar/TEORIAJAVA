package fp.vacunas;

import java.time.LocalDate;

public record Vacunado(String usuario, 
		LocalDate fechaNacimiento, 
		String provincia, 
		Marca marca, 
		LocalDate fechaAdministracion, 
		Boolean pautaCompleta) implements Comparable<Vacunado>{

	public Integer edad() {
		return fechaNacimiento.until(LocalDate.now()).getYears();
	}
	@Override
	public int compareTo(Vacunado v) {
		int res=this.fechaNacimiento.compareTo(v.fechaNacimiento);
		if(res==0) {
			res=this.usuario.compareTo(v.usuario);
			if(res==0) {
				res=this.provincia.compareTo(v.provincia);
				if(res==0) {
					res=this.marca.compareTo(v.marca);
					if(res==0) {
						res=this.fechaAdministracion.compareTo(v.fechaAdministracion);
						if(res==0) {
							res=this.pautaCompleta.compareTo(v.pautaCompleta);
						}
					}
				}
			}
		}
		return res;
	}
	
}
