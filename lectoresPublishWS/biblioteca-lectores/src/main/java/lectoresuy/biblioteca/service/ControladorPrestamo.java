package lectoresuy.biblioteca.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Bibliotecario;
import lectoresuy.biblioteca.entidades.Material;
import lectoresuy.biblioteca.entidades.Prestamo;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.excepciones.BibliotecarioNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.MaterialNoDisponibleExcepcion;
import lectoresuy.biblioteca.excepciones.LectorNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.PrestamoRepetidoExcepcion;

public class ControladorPrestamo implements IControladorPrestamo {
	private static EntityManager em;
	private static EntityManagerFactory emf;

    private ManejadorPrestamo manejadorPrestamo;
    private ManejadorLector manejadorLector;
    private ManejadorBibliotecario manejadorBibliotecario;
    private ManejadorMaterial manejadorMaterial;

    public ControladorPrestamo() {
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
        this.manejadorLector = ManejadorLector.getInstancia();
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
        this.manejadorMaterial = ManejadorMaterial.getInstancia();
    }

    @Override
    public void agregarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario, Date fechaDevolucion) throws Exception {
        if (!materialEstaDisponible(idMaterial)) {
            throw new MaterialNoDisponibleExcepcion("El material con ID " + idMaterial + " no está disponible para préstamo");
        }

        Lector lector = manejadorLector.buscarLector(emailLector);
        if (lector == null) {
            throw new LectorNoEncontradoExcepcion("No se encontró un lector con el email: " + emailLector);
        }

        Bibliotecario bibliotecario = manejadorBibliotecario.buscarBibliotecario(numeroBibliotecario);
        if (bibliotecario == null) {
            throw new BibliotecarioNoEncontradoExcepcion("No se encontró un bibliotecario con el número: " + numeroBibliotecario);
        }

        Material material = manejadorMaterial.buscarMaterial(idMaterial);
        if (material == null) {
            throw new Exception("No se encontró el material con ID: " + idMaterial);
        }

        Date fechaSolicitud = new Date();
        Prestamo nuevoPrestamo = new Prestamo(lector, bibliotecario, material, fechaSolicitud, fechaDevolucion, EstadoPrestamo.PENDIENTE);

        manejadorPrestamo.guardarPrestamo(nuevoPrestamo);
    }

    public boolean materialEstaDisponible(Long idMaterial) {
        return manejadorPrestamo.verificarDisponibilidadMaterial(idMaterial);
    }

	public void registrarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario, Date fechaDevolucion) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			Prestamo prestamo = manejadorPrestamo.buscarPrestamo(idMaterial, emailLector, numeroBibliotecario);
			if (prestamo != null)
				throw new PrestamoRepetidoExcepcion("El préstamo ya existe");
			Lector lector = manejadorLector.buscarLector(emailLector);
			Bibliotecario bibliotecario = manejadorBibliotecario.buscarBibliotecario(numeroBibliotecario);
			Material material = manejadorMaterial.buscarMaterial(idMaterial);
			prestamo = new Prestamo(lector, bibliotecario, material, new Date(), fechaDevolucion, EstadoPrestamo.PENDIENTE);
			em.persist(prestamo);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

    @Override
    public List<DtPrestamo> listarPrestamos() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtPrestamo> resultado;
		try {
			resultado = manejadorPrestamo.listarPrestamos();
		} finally {
			em.close();
		}
		return resultado;
    }

    /*
    @Override
    public void finalizarPrestamo(Long idPrestamo, Date fechaDevolucion) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			Prestamo prestamo = manejadorPrestamo.buscarPrestamoPorId(idPrestamo);
			if (prestamo != null) {
				prestamo.setFechaDevolucion(fechaDevolucion);
				prestamo.setEstado(EstadoPrestamo.FINALIZADO);
				manejadorPrestamo.actualizarPrestamo(prestamo);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
    }
    */

    @Override
    public void actualizarEstadoPrestamo(Long id, EstadoPrestamo nuevoEstado) {
        manejadorPrestamo.actualizarEstadoPrestamo(id, nuevoEstado);
    }

    @Override
    public void actualizarInformacionPrestamo(DtPrestamo prestamo) {
        manejadorPrestamo.actualizarInformacionPrestamo(prestamo);
    }

    @Override
    public List<DtPrestamo> listarPrestamosLector(DtLector lector) {
        return manejadorPrestamo.prestamosLector(lector);
    }

    @Override
    public List<DtPrestamo> listarPrestamosBibliotecario(DtBibliotecario bibliotecario) {
        return manejadorPrestamo.prestamosBibliotecario(bibliotecario);
    }

    @Override
    public List<DtPrestamo> listarPrestamosZona(String zona) {
        return manejadorPrestamo.prestamosZona(zona);
    }

    @Override
    public List<DtMaterial> listarMaterialesConteoPrestamosPendientes() {
        return manejadorPrestamo.listarMaterialesConteoPrestamosPendientes();
    }
    
}
