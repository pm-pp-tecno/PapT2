package lectoresuy.biblioteca.interfaces;

import java.util.ArrayList;
import java.util.Date;


import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.excepciones.LectorRepetidoExcepcion;
import lectoresuy.biblioteca.excepciones.BibliotecarioRepetidoExcepcion;

// Pasamos al lector o cada campo?
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;

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

	public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin);
}
