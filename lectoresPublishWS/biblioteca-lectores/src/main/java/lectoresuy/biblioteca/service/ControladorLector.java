package lectoresuy.biblioteca.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.service.ManejadorLector;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.util.HibernateUtil;
import lectoresuy.biblioteca.excepciones.BibliotecarioRepetidoExcepcion;
import lectoresuy.biblioteca.excepciones.LectorRepetidoExcepcion;

public class ControladorLector implements IControladorLector {
	
	private static EntityManager em;
	private static EntityManagerFactory emf;

	public ControladorLector() {
		super();
	}

	@Override
	public void agregarLector(String nombre, String email, String password, String direccion, Date fechaRegistro, EstadoLector estado, String zona) throws LectorRepetidoExcepcion{

		//Configuramos el EMF a través de la unidad de persistencia
		emf = Persistence.createEntityManagerFactory("Conexion");
		
		//Generamos un EntityManager
		em = emf.createEntityManager();
		
		//Iniciamos una transacción
		em.getTransaction().begin();
		
		ManejadorLector mL = ManejadorLector.getInstancia();
        Lector lector = mL.buscarLector(email);
        if (lector != null)
            throw new LectorRepetidoExcepcion("El email " + email + " ya esta registrado");
		
		//Construimos el objeto a persistir CON password
        lector = new Lector(nombre, email, password, direccion, fechaRegistro, estado, zona);
		
		//Persistimos el objeto
		em.persist(lector);
		
		//Commmiteamos la transacción
		em.getTransaction().commit();
		
		//Cerramos el EntityManager
		em.close();
	}

	@Override
	public List<DtLector> listarLectores() {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		List<DtLector> resultado;
		try {
			ManejadorLector mL = ManejadorLector.getInstancia();
			resultado = mL.listarLectores();
		} finally {
			em.close();
		}
		return resultado;
	}

	@Override
	public void suspenderLector(String email, EstadoLector estado) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorLector mL = ManejadorLector.getInstancia();
			Lector lector = mL.buscarLector(email);
			if (lector != null) {
				lector.setEstado(estado);
				mL.actualizarLector(lector);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void cambiarZonaLector(String email, String nuevaZona) {
		emf = Persistence.createEntityManagerFactory("Conexion");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			ManejadorLector mL = ManejadorLector.getInstancia();
			Lector lector = mL.buscarLector(email);
			if (lector != null) {
				lector.setZona(nuevaZona);
				mL.actualizarLector(lector);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean lectorEstaSuspendido(String emailLector) {
		ManejadorLector manejadorLector = ManejadorLector.getInstancia();
		return manejadorLector.lectorEstaSuspendido(emailLector);
	}

}
