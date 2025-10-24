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
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
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
	public DtBibliotecario[] listarBibliotecarios() {
		List<DtBibliotecario> lista = iconB.listarBibliotecarios();
		return lista.toArray(new DtBibliotecario[0]);
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

	@WebMethod
	public DtLibro[] listarLibros() {
		List<DtLibro> lista = iconM.listarLibros();
		return lista.toArray(new DtLibro[0]);
	}

	@WebMethod
	public DtArticulo[] listarArticulos() {
		List<DtArticulo> lista = iconM.listarArticulos();
		return lista.toArray(new DtArticulo[0]);
	}

	@WebMethod
	public void actualizarLibro(long id, String titulo, int cantidadPaginas) {
		try {
			iconM.actualizarLibro(id, titulo, cantidadPaginas);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar libro: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void actualizarArticulo(long id, String descripcion, double peso, String dimensiones) {
		try {
			iconM.actualizarArticulo(id, descripcion, peso, dimensiones);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar artículo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void agregarLibro(String titulo, int cantidadPaginas, String fechaIngreso) {
		try {
			// Convertir String a Date usando Calendar para evitar problemas de zona horaria
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String[] partes = fechaIngreso.split("-");
			int anio = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]) - 1; // Calendar usa 0-based months
			int dia = Integer.parseInt(partes[2]);
			cal.set(anio, mes, dia, 0, 0, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			Date fecha = cal.getTime();
			
			iconM.agregarLibro(titulo, cantidadPaginas, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al agregar libro: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void agregarArticulo(String descripcion, double peso, String dimensiones, String fechaIngreso) {
		try {
			// Convertir String a Date usando Calendar para evitar problemas de zona horaria
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String[] partes = fechaIngreso.split("-");
			int anio = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]) - 1; // Calendar usa 0-based months
			int dia = Integer.parseInt(partes[2]);
			cal.set(anio, mes, dia, 0, 0, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			Date fecha = cal.getTime();
			
			iconM.agregarArticulo(descripcion, peso, dimensiones, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al agregar artículo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public DtMaterial[] filtrarMaterialesPorFecha(String fechaDesde, String fechaHasta) {
		try {
			// Convertir String a Date usando Calendar para evitar problemas de zona horaria
			java.util.Calendar cal = java.util.Calendar.getInstance();
			
			// Parsear fechaDesde
			String[] partesDesde = fechaDesde.split("-");
			int anioDesde = Integer.parseInt(partesDesde[0]);
			int mesDesde = Integer.parseInt(partesDesde[1]) - 1;
			int diaDesde = Integer.parseInt(partesDesde[2]);
			cal.set(anioDesde, mesDesde, diaDesde, 0, 0, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			Date fechaInicio = cal.getTime();
			
			// Parsear fechaHasta
			String[] partesHasta = fechaHasta.split("-");
			int anioHasta = Integer.parseInt(partesHasta[0]);
			int mesHasta = Integer.parseInt(partesHasta[1]) - 1;
			int diaHasta = Integer.parseInt(partesHasta[2]);
			cal.set(anioHasta, mesHasta, diaHasta, 23, 59, 59);
			cal.set(java.util.Calendar.MILLISECOND, 999);
			Date fechaFin = cal.getTime();
			
			List<DtMaterial> lista = iconM.filtrarMaterialesPorFecha(fechaInicio, fechaFin);
			return lista.toArray(new DtMaterial[0]);
		} catch (Exception ex) {
			throw new WebServiceException("Error al filtrar materiales: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public DtPrestamo[] listarPrestamos() {
		try {
			List<DtPrestamo> lista = iconP.listarPrestamos();
			return lista.toArray(new DtPrestamo[0]);
		} catch (Exception ex) {
			throw new WebServiceException("Error al listar préstamos: " + ex.getMessage(), ex);
		}
	}

	/*
	@WebMethod
	public void actualizarMaterial(String id, String nuevoTitulo, String nuevoAutor, String nuevoTipo, String nuevaZona) {
		iconM.actualizarMaterial(id, nuevoTitulo, nuevoAutor, nuevoTipo, nuevaZona);
	}
	*/

	// Métodos de Prestamo
	@WebMethod
	public void registrarPrestamo(long idMaterial, String emailLector, String numeroBibliotecario, String fechaDevolucion) {
		try {
		// Convertir String a Date usando Calendar para evitar problemas de zona horaria
		java.util.Calendar cal = java.util.Calendar.getInstance();
		String[] partes = fechaDevolucion.split("-");
		int anio = Integer.parseInt(partes[0]);
		int mes = Integer.parseInt(partes[1]) - 1; // Calendar usa 0-based months
		int dia = Integer.parseInt(partes[2]);
		cal.set(anio, mes, dia, 0, 0, 0);
		cal.set(java.util.Calendar.MILLISECOND, 0);
		Date fecha = cal.getTime();
			
			iconP.agregarPrestamo((Long) idMaterial, emailLector, numeroBibliotecario, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al registrar préstamo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void actualizarEstadoPrestamo(long id, String estado) {
		try {
			iconP.actualizarEstadoPrestamo(id, lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo.valueOf(estado));
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar estado del préstamo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void actualizarPrestamo(long id, String estado, String fechaDevolucionEstimada) {
		try {
			// Convertir String a Date usando Calendar para evitar problemas de zona horaria
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String[] partes = fechaDevolucionEstimada.split("-");
			int anio = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]) - 1; // Calendar usa 0-based months
			int dia = Integer.parseInt(partes[2]);
			cal.set(anio, mes, dia, 0, 0, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			Date fecha = cal.getTime();
			
			iconP.actualizarPrestamo(id, lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo.valueOf(estado), fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar préstamo: " + ex.getMessage(), ex);
		}
	}


	/*
	 * Cambiar por actualizarEstadoPrestamo????
	@WebMethod
	public void finalizarPrestamo(Long idPrestamo, Date fechaDevolucionReal) {
		iconP.finalizarPrestamo(idPrestamo, fechaDevolucionReal);
	}
	*/

	// Métodos para obtener listas para los selects del formulario de nuevo préstamo
	@WebMethod
	public DtLibro[] listarLibrosParaSelect() {
		List<DtLibro> lista = iconM.listarLibros();
		return lista.toArray(new DtLibro[0]);
	}

	@WebMethod
	public DtArticulo[] listarArticulosParaSelect() {
		List<DtArticulo> lista = iconM.listarArticulos();
		return lista.toArray(new DtArticulo[0]);
	}

	@WebMethod
	public DtLector[] listarLectoresParaSelect() {
		List<DtLector> lista = iconL.listarLectores();
		return lista.toArray(new DtLector[0]);
	}

	@WebMethod
	public DtBibliotecario[] listarBibliotecariosParaSelect() {
		List<DtBibliotecario> lista = iconB.listarBibliotecarios();
		return lista.toArray(new DtBibliotecario[0]);
	}

}
