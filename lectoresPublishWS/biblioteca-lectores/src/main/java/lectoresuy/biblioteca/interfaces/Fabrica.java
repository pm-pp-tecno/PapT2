package lectoresuy.biblioteca.interfaces;

import lectoresuy.biblioteca.service.ControladorLector;
import lectoresuy.biblioteca.service.ControladorBibliotecario;
import lectoresuy.biblioteca.service.ControladorMaterial;
import lectoresuy.biblioteca.service.ControladorPrestamo;

public class Fabrica {
	private static Fabrica instancia = null;
	
	private Fabrica(){}
	
	public static Fabrica getInstancia() {
		if (instancia == null)
			instancia = new Fabrica();
		return instancia;
	}
	
	public IControladorLector getIControladorLector() {
		return new ControladorLector();
	}

	
	public IControladorBibliotecario getIControladorBibliotecario() {
		return new ControladorBibliotecario();
	}
	
	public IControladorMaterial getIControladorMaterial() {
		return new ControladorMaterial();
	}
	
	public IControladorPrestamo getIControladorPrestamo() {
	    return new ControladorPrestamo();
	}
}




