package lectoresuy.biblioteca.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.service.ManejadorLector;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.excepciones.BibliotecarioRepetidoExcepcion;
import lectoresuy.biblioteca.excepciones.LectorRepetidoExcepcion;

public class ControladorLector implements IControladorLector {
	
	public ControladorLector() {
		super();
	}

	@Override
	public void agregarLector(String nombre, String email, String direccion, Date fechaRegistro, EstadoLector estado, String zona) throws LectorRepetidoExcepcion{
		ManejadorLector mL = ManejadorLector.getInstancia();
        Lector lector = mL.buscarLector(email);
        if (lector != null)
            throw new LectorRepetidoExcepcion("El email " + email + " ya esta registrado");
        lector = new Lector(nombre, email, direccion, fechaRegistro, estado, zona);
		mL.guardarLector(lector);
	}

	@Override
	public List<DtLector> listarLectores() {
		ManejadorLector mL = ManejadorLector.getInstancia();
		return mL.listarLectores();
	}

	@Override
	public void suspenderLector(String email, EstadoLector estado) {
		ManejadorLector mL = ManejadorLector.getInstancia();
		Lector lector = mL.buscarLector(email);
		if (lector != null) {
			lector.setEstado(estado);
			mL.actualizarLector(lector);
		}
	}

	@Override
	public void cambiarZonaLector(String email, String nuevaZona) {
		ManejadorLector mL = ManejadorLector.getInstancia();
		Lector lector = mL.buscarLector(email);
		if (lector != null) {
			lector.setZona(nuevaZona);
			mL.actualizarLector(lector);
		}
	}


}
