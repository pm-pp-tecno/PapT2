package lectoresuy.biblioteca.service;


import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;

import lectoresuy.biblioteca.entidades.Bibliotecario;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import java.util.List;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.excepciones.BibliotecarioRepetidoExcepcion;

public class ControladorBibliotecario implements IControladorBibliotecario {
	
	public ControladorBibliotecario() {
		super();
	}


	@Override
	public void agregarBibliotecario(String nombre, String email, String numeroEmpleado)
			throws BibliotecarioRepetidoExcepcion {
		ManejadorBibliotecario mB = ManejadorBibliotecario.getInstancia();
        Bibliotecario bibliotecario = mB.buscarBibliotecario(numeroEmpleado);
        if (bibliotecario != null)
            throw new BibliotecarioRepetidoExcepcion("El número de empleado " + numeroEmpleado + " ya esta registrado");
        bibliotecario = new Bibliotecario(nombre, email, numeroEmpleado);
		mB.guardarBibliotecario(bibliotecario);
	}

	@Override
	public List<DtBibliotecario> listarBibliotecarios() {
		ManejadorBibliotecario mB = ManejadorBibliotecario.getInstancia();
		return mB.listarBibliotecarios();
	}


}
