package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.entidades.Libro;

import java.util.Date;

import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.service.ManejadorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;

import java.util.List;


public class ControladorMaterial implements IControladorMaterial {

	public ControladorMaterial() {
		super();
	}

	@Override
	public void agregarLibro(String titulo, String cPag, Date fechaRegistro) {
		// TODO Auto-generated method stub
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		Libro nuevoLibro = new Libro(fechaRegistro, titulo, cPag);
		mM.registrarNuevoLibro(nuevoLibro);
	}

	@Override
	public void agregarArticulo(String descripcion, float peso, String dimensiones, Date fechaRegistro) {
		// TODO Auto-generated method stub
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		Articulo nuevoArticulo = new Articulo(fechaRegistro, descripcion, peso, dimensiones);
		mM.registrarNuevoArticulo(nuevoArticulo);
	}

	@Override
	public List<DtMaterial> listarMateriales() {
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		return mM.listarMateriales();
	}

	@Override
	public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin) {
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		return mM.listarDonacionesPorFecha(fechaInicio, fechaFin);
	}
}
