package lectoresuy.biblioteca.publicadores;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceException;

import lectoresuy.biblioteca.configuraciones.WebServiceConfiguracion;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.interfaces.Fabrica;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ControladorPublish {
	private Fabrica fabrica;
	private IControladorBibliotecario iconB;
	private IControladorLector iconL;
	private IControladorMaterial iconM;
	private IControladorPrestamo iconP;
	private WebServiceConfiguracion configuracion;
	private Endpoint endpoint;

	public ControladorPublish() {
		fabrica = Fabrica.getInstancia();
		iconB = fabrica.getIControladorBibliotecario();
		iconL = fabrica.getIControladorLector();
		iconM = fabrica.getIControladorMaterial();
		iconP = fabrica.getIControladorPrestamo();
		try {
			configuracion = new WebServiceConfiguracion();
		} catch (Exception ex) {
			
		}
	}

	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://" + configuracion.getConfigOf("#WS_IP") + ":" + configuracion.getConfigOf("#WS_PORT") + "/lectoresuy", this);
		System.out.println("http://" + configuracion.getConfigOf("#WS_IP") + ":" + configuracion.getConfigOf("#WS_PORT") + "/lectoresuy");
	}
	
	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
        return endpoint;
	}

	// Agregar los metodos de Biblioteca con el formato de abajo.
	//LOS MÉTODOS QUE VAMOS A PUBLICAR
	@WebMethod
	public void agregarBibliotecario(String nombre, String email, String numeroEmpleado){
		try {
			iconB.agregarBibliotecario(nombre, email, numeroEmpleado);
		} catch (Exception ex) {
			throw new WebServiceException("Error al agregar bibliotecario: " + ex.getMessage(), ex);
		}
	}

	// Métodos de Lector
	@WebMethod
	public void agregarLector(String nombre, String email, String direccion, Date fechaRegistro, String estado, String zona) {
		try {
			iconL.agregarLector(nombre, email, direccion, fechaRegistro, EstadoLector.valueOf(estado), zona);
		} catch (Exception ex) {
			throw new WebServiceException("Error al agregar lector: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public DtLector[] listarLectores() {
		List<DtLector> lista = iconL.listarLectores();
		return lista.toArray(new DtLector[0]);
	}

	@WebMethod
	public void suspenderLector(String email, String estado) {
		iconL.suspenderLector(email, EstadoLector.valueOf(estado));
	}

	@WebMethod
	public void cambiarZonaLector(String email, String nuevaZona) {
		iconL.cambiarZonaLector(email, nuevaZona);
	}

	/*
	// Métodos de Material
	@WebMethod
	public void agregarMaterial(String titulo, String autor, String tipo, String zona) {
		iconM.agregarMaterial(titulo, autor, tipo, zona);
	}
	*/

	@WebMethod
	public DtMaterial[] listarMateriales() {
		List<DtMaterial> lista = iconM.listarMateriales();
		return lista.toArray(new DtMaterial[0]);
	}

	/*
	@WebMethod
	public void actualizarMaterial(String id, String nuevoTitulo, String nuevoAutor, String nuevoTipo, String nuevaZona) {
		iconM.actualizarMaterial(id, nuevoTitulo, nuevoAutor, nuevoTipo, nuevaZona);
	}
	*/

	// Métodos de Prestamo
	@WebMethod
	public void registrarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario, Date fechaDevolucion) {
		try {
			iconP.agregarPrestamo(idMaterial, emailLector, numeroBibliotecario, fechaDevolucion);
		} catch (Exception ex) {
			throw new WebServiceException("Error al registrar préstamo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public DtPrestamo[] listarPrestamos() {
		List<DtPrestamo> lista = iconP.listarPrestamos();
		return lista.toArray(new DtPrestamo[0]);
	}

	/*
	 * Cambiar por actualizarEstadoPrestamo????
	@WebMethod
	public void finalizarPrestamo(Long idPrestamo, Date fechaDevolucionReal) {
		iconP.finalizarPrestamo(idPrestamo, fechaDevolucionReal);
	}
	*/

}
