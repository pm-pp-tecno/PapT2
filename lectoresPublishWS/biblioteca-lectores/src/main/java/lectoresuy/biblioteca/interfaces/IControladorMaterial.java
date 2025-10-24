package lectoresuy.biblioteca.interfaces;

import java.util.Date;

// Pasamos al lector o cada campo?
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtMaterial;

import java.util.List;

//import lectoresuy.biblioteca.datatypes.DtClase;
//import lectoresuy.biblioteca.datatypes.DtSocio;
//import excepciones.ClaseRepetidaException;
//import excepciones.SocioInscriptoException;
//import excepciones.SocioNoInscriptoException;
//import excepciones.SocioRepetidoExcepcion;

public interface IControladorMaterial {	

	public void agregarLibro(String titulo, String cPag, Date fechaRegistro);
	
	public void agregarArticulo(String descripcion, float peso, String dimensiones, Date fechaRegistro);

	public List<DtMaterial> listarMateriales();

	public List<DtLibro> listarLibros();

	public List<DtArticulo> listarArticulos();

	public void actualizarLibro(Long id, String titulo, Integer cantidadPaginas);

	public void actualizarArticulo(Long id, String descripcion, Double peso, String dimensiones);

	public void agregarLibro(String titulo, Integer cantidadPaginas, Date fechaIngreso);

	public void agregarArticulo(String descripcion, Double peso, String dimensiones, Date fechaIngreso);

	public List<DtMaterial> filtrarMaterialesPorFecha(Date fechaDesde, Date fechaHasta);

	public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin);
}
