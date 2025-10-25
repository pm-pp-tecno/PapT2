package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.entidades.Libro;

import java.util.Date;

import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.service.ManejadorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtPrestamo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ControladorMaterial implements IControladorMaterial {
	private static EntityManager em;
	private static EntityManagerFactory emf;

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

	/*
	@Override
	public void agregarMaterial( parámetros) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			Material material = mM.buscarMaterial(parámetros clave);
			if (material != null)
				throw new MaterialRepetidoExcepcion("El material ya existe");
			material = new Material();
			em.persist(material);
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
	public List<DtMaterial> listarMateriales() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtMaterial> resultado;
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			resultado = mM.listarMateriales();
		} finally {
			em.close();
		}
		return resultado;
	}

	@Override
	public List<DtLibro> listarLibros() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtLibro> resultado;
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			resultado = mM.listarLibros();
		} finally {
			em.close();
		}
		return resultado;
	}

	@Override
	public List<DtArticulo> listarArticulos() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtArticulo> resultado;
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			resultado = mM.listarArticulos();
		} finally {
			em.close();
		}
		return resultado;
	}

	@Override
	public void actualizarLibro(Long id, String titulo, Integer cantidadPaginas) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.actualizarLibro(id, titulo, cantidadPaginas);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void actualizarArticulo(Long id, String descripcion, Double peso, String dimensiones) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.actualizarArticulo(id, descripcion, peso, dimensiones);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void actualizarLibroConFecha(Long id, String titulo, Integer cantidadPaginas, Date fechaIngreso) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.actualizarLibroConFecha(id, titulo, cantidadPaginas, fechaIngreso);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void actualizarArticuloConFecha(Long id, String descripcion, Double peso, String dimensiones, Date fechaIngreso) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.actualizarArticuloConFecha(id, descripcion, peso, dimensiones, fechaIngreso);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void actualizarFechaMaterial(Long id, Date fechaIngreso) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.actualizarFechaMaterial(id, fechaIngreso);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}


	@Override
	public void agregarLibro(String titulo, Integer cantidadPaginas, Date fechaIngreso) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.agregarLibro(titulo, cantidadPaginas, fechaIngreso);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void agregarArticulo(String descripcion, Double peso, String dimensiones, Date fechaIngreso) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			mM.agregarArticulo(descripcion, peso, dimensiones, fechaIngreso);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public List<DtMaterial> filtrarMaterialesPorFecha(Date fechaDesde, Date fechaHasta) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtMaterial> resultado;
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			resultado = mM.filtrarMaterialesPorFecha(fechaDesde, fechaHasta);
		} finally {
			em.close();
		}
		return resultado;
	}

	@Override
	public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin) {
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		return mM.listarDonacionesPorFecha(fechaInicio, fechaFin);
	}

/*	
	@Override
	public void actualizarMaterial() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			Material material = mM.buscarMaterial();
			if (material != null) {
				// ...actualizar campos...
				mM.actualizarMaterial(material);
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
	
	// ...otros métodos similares...
}
