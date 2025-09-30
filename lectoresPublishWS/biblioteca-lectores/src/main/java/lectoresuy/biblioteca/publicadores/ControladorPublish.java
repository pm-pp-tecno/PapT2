package publicadores;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtClase;
import datatypes.DtEntrenamiento;
import datatypes.DtSocio;
import datatypes.DtSpinning;
import interfaces.Fabrica;
import interfaces.IControladorBiblioetecario;
import interfaces.IControladorLector;
import interfaces.IControladorMaterial;
import interfaces.IControladorPrestamo;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ControladorPublish {
	private Fabrica fabrica;
	private IControladorBiblioetecario iconB;
	private IControladorBiblioetecario iconL;
	private IControladorBiblioetecario iconM;
	private IControladorBiblioetecario iconP;
	private WebServiceConfiguracion configuracion;
	private Endpoint endpoint;

	public ControladorPublish() {
		fabrica = Fabrica.getInstancia();
		iconB = fabrica.getIControladorBibliotecario();
		iconL = fabrica.getIControladorLector();
		iconM = fabrica.getIControladorMaterial();
		iconP = fabrica.getIControladorP();
		try {
			configuracion = new WebServiceConfiguracion();
		} catch (Exception ex) {
			
		}
	}

	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://" + configuracion.getConfigOf("#WS_IP") + ":" + configuracion.getConfigOf("#WS_PORT") + "/controlador", this);
		System.out.println("http://" + configuracion.getConfigOf("#WS_IP") + ":" + configuracion.getConfigOf("#WS_PORT") + "/controlador");
	}
	
	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
        return endpoint;
	}

	// Agregar los metodos de Biblioteca con el formato de abajo.
	//LOS MÉTODOS QUE VAMOS A PUBLICAR
	@WebMethod
	public void agregarBibliotecario(String nombre, String email, String numeroEmpleado){
		iconB.agregarBibliotecario(nombre, email, numeroEmpleado);
	}

	// Métodos de Lector
	@WebMethod
	public void agregarLector(String nombre, String email, String direccion, Date fechaRegistro, String estado, String zona) throws Exception {
		iconL.agregarLector(nombre, email, direccion, fechaRegistro, EstadoLector.valueOf(estado), zona);
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

	// Métodos de Material
	@WebMethod
	public void agregarMaterial(String titulo, String autor, String tipo, String zona) throws Exception {
		iconM.agregarMaterial(titulo, autor, tipo, zona);
	}

	@WebMethod
	public DtMaterial[] listarMateriales() {
		List<DtMaterial> lista = iconM.listarMateriales();
		return lista.toArray(new DtMaterial[0]);
	}

	@WebMethod
	public void actualizarMaterial(String id, String nuevoTitulo, String nuevoAutor, String nuevoTipo, String nuevaZona) {
		iconM.actualizarMaterial(id, nuevoTitulo, nuevoAutor, nuevoTipo, nuevaZona);
	}

	// Métodos de Prestamo
	@WebMethod
	public void registrarPrestamo(String emailLector, String idMaterial, Date fechaPrestamo, Date fechaDevolucion) throws Exception {
		iconP.registrarPrestamo(emailLector, idMaterial, fechaPrestamo, fechaDevolucion);
	}

	@WebMethod
	public DtPrestamo[] listarPrestamos() {
		List<DtPrestamo> lista = iconP.listarPrestamos();
		return lista.toArray(new DtPrestamo[0]);
	}

	@WebMethod
	public void finalizarPrestamo(String idPrestamo, Date fechaDevolucionReal) {
		iconP.finalizarPrestamo(idPrestamo, fechaDevolucionReal);
	}

	/*
	//LOS MÉTODOS QUE VAMOS A PUBLICAR
	@WebMethod
	public void agregarSocio(String ci, String nombre) {
		icon.agregarSocio(ci, nombre);
	}
	
	@WebMethod
	public void agregarDtSpinning(DtSpinning clase){
		icon.agregarDtSpinning(clase);;
	}
	
	@WebMethod
	public void agregarDtEntrenamiento(DtEntrenamiento clase){
		icon.agregarDtEntrenamiento(clase);
	}
	
	@WebMethod
	public void agregarInscripcion(String ciSocio, int idClase, Calendar fecha){
		icon.agregarInscripcion(ciSocio, idClase, fecha.getTime());
	}
	
	@WebMethod
	public void borrarInscripcion(String ciSocio, int idClase){
		icon.borrarInscripcion(ciSocio, idClase);
	}
	
	@WebMethod
	public DtSocio[] obtenerInfoSociosPorClase (int idClase){
		List<DtSocio> dtsocio = icon.obtenerInfoSociosPorClase(idClase);
		int i = 0;
        DtSocio[] ret = new DtSocio[dtsocio.size()];
        for(DtSocio s : dtsocio) {
            ret[i]=s;
            i++;
        }
        return ret;
	}
	
	@WebMethod
	public DtClase obtenerClase(int idClase){
		return icon.obtenerClase(idClase);
	}
	*/

}
