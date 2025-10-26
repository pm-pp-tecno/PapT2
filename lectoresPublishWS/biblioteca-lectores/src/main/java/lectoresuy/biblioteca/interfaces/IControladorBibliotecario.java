package lectoresuy.biblioteca.interfaces;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import lectoresuy.biblioteca.excepciones.BibliotecarioRepetidoExcepcion;

import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import java.util.List;

//import lectoresuy.biblioteca.datatypes.DtClase;
//import lectoresuy.biblioteca.datatypes.DtSocio;
//import excepciones.ClaseRepetidaException;
//import excepciones.SocioInscriptoException;
//import excepciones.SocioNoInscriptoException;
//import excepciones.SocioRepetidoExcepcion;

public interface IControladorBibliotecario {
	
	public void agregarBibliotecario(String nombre, String email, String password, String numeroEmpleado) throws BibliotecarioRepetidoExcepcion;

	public List<DtBibliotecario> listarBibliotecarios();
	
	//public List<DtPrestamo> listarBibliotecariosPrestamos(String numeroEmpleado);
	
}
