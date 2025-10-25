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
	public void actualizarLibroConFecha(long id, String titulo, int cantidadPaginas, String fechaIngreso) {
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
			
			iconM.actualizarLibroConFecha(id, titulo, cantidadPaginas, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar libro: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void actualizarArticuloConFecha(long id, String descripcion, double peso, String dimensiones, String fechaIngreso) {
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
			
			iconM.actualizarArticuloConFecha(id, descripcion, peso, dimensiones, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar artículo: " + ex.getMessage(), ex);
		}
	}

	@WebMethod
	public void actualizarFechaMaterial(long id, String fechaIngreso) {
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
			
			iconM.actualizarFechaMaterial(id, fecha);
		} catch (Exception ex) {
			throw new WebServiceException("Error al actualizar fecha del material: " + ex.getMessage(), ex);
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
			System.out.println("=== CONTROLADOR PUBLISH - REGISTRAR PRESTAMO ===");
			System.out.println("ID Material recibido: " + idMaterial);
			System.out.println("Email Lector recibido: " + emailLector);
			System.out.println("Numero Bibliotecario recibido: " + numeroBibliotecario);
			System.out.println("Fecha Devolucion recibida: " + fechaDevolucion);
			
			// Convertir String a Date usando Calendar para evitar problemas de zona horaria
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String[] partes = fechaDevolucion.split("-");
			int anio = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]) - 1; // Calendar usa 0-based months
			int dia = Integer.parseInt(partes[2]);
			cal.set(anio, mes, dia, 0, 0, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			Date fecha = cal.getTime();
			
			System.out.println("Fecha parseada exitosamente: " + fecha);
			System.out.println("Llamando a iconP.agregarPrestamo...");
			
			iconP.agregarPrestamo((Long) idMaterial, emailLector, numeroBibliotecario, fecha);
			
			System.out.println("Préstamo agregado exitosamente en el controlador.");
		} catch (Exception ex) {
			System.out.println("ERROR en ControladorPublish.registrarPrestamo: " + ex.getMessage());
			System.out.println("Tipo de excepción: " + ex.getClass().getSimpleName());
			ex.printStackTrace();
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
		// No verificar disponibilidad - todos los materiales se pueden seleccionar
		// Los nuevos préstamos siempre empiezan como PENDIENTE
		return lista.toArray(new DtLibro[0]);
	}

	@WebMethod
	public DtArticulo[] listarArticulosParaSelect() {
		List<DtArticulo> lista = iconM.listarArticulos();
		// No verificar disponibilidad - todos los materiales se pueden seleccionar
		// Los nuevos préstamos siempre empiezan como PENDIENTE
		return lista.toArray(new DtArticulo[0]);
	}

	@WebMethod
	public DtLector[] listarLectoresParaSelect() {
		List<DtLector> lista = iconL.listarLectores();
		// Agregar información de estado solo si está suspendido
		for (DtLector lector : lista) {
			boolean suspendido = iconL.lectorEstaSuspendido(lector.getEmail());
			if (suspendido) {
				// Solo agregar el estado si está suspendido
				String nombreOriginal = lector.getNombre();
				lector.setNombre(nombreOriginal + " (SUSPENDIDO)");
			}
			// Si está habilitado, mantener el nombre original sin modificaciones
		}
		return lista.toArray(new DtLector[0]);
	}

	@WebMethod
	public DtBibliotecario[] listarBibliotecariosParaSelect() {
		List<DtBibliotecario> lista = iconB.listarBibliotecarios();
		return lista.toArray(new DtBibliotecario[0]);
	}

	@WebMethod
	public boolean materialEstaDisponible(long idMaterial) {
		return iconP.materialEstaDisponible(idMaterial);
	}

	@WebMethod
	public boolean lectorEstaSuspendido(String emailLector) {
		return iconL.lectorEstaSuspendido(emailLector);
	}

}
