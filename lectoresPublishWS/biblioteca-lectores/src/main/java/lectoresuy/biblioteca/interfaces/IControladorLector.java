package lectoresuy.biblioteca.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;

import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.excepciones.LectorRepetidoExcepcion;

import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;


public interface IControladorLector {
	
	public void agregarLector(String nombre, String email, String password, String direccion, Date fechaRegistro, EstadoLector estado, String zona) throws LectorRepetidoExcepcion;
	
	public List<DtLector> listarLectores();
	
	public void suspenderLector(String email, EstadoLector estado);
	
	public void cambiarZonaLector(String email, String nuevaZona);
	
	public boolean lectorEstaSuspendido(String emailLector);
	
}
