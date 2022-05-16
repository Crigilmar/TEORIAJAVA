package fp.tipos.cine;

import java.util.List;

public abstract class ListaSeguimientoA implements ListaSeguimiento {

	protected List<PeliculaSeguimiento> peliculas;

	protected ListaSeguimientoA(List<PeliculaSeguimiento> peliculas) {
		super();
		this.peliculas = peliculas; //
	}

	@Override
	public String toString() {
		String res="[";
		for (PeliculaSeguimiento element : peliculas) {
			res+=element+"\n";
		}
		return res.substring(0,res.length()-1)+"]";
	}


}
