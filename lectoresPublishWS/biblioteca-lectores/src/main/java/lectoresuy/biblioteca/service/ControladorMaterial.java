package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.entidades.Libro;

import java.util.Date;

import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.service.ManejadorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.datatypes.DtMaterial;
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

	@Override
	public void agregarMaterial(/* parámetros */) throws MaterialRepetidoExcepcion {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			Material material = mM.buscarMaterial(/* parámetros clave */);
			if (material != null)
				throw new MaterialRepetidoExcepcion("El material ya existe");
			material = new Material(/* ... */);
			em.persist(material);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

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
	public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin) {
		ManejadorMaterial mM = ManejadorMaterial.getInstancia();
		return mM.listarDonacionesPorFecha(fechaInicio, fechaFin);
	}

	@Override
	public void actualizarMaterial(/* parámetros */) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorMaterial mM = ManejadorMaterial.getInstancia();
			Material material = mM.buscarMaterial(/* parámetros clave */);
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

	// ...otros métodos similares...
}
